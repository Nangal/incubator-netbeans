/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package org.netbeans.lib.profiler.results.cpu.marking;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import org.netbeans.lib.profiler.marker.Mark;
import org.netbeans.lib.profiler.client.ClientUtils;
import org.netbeans.lib.profiler.global.ProfilingSessionStatus;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.lib.profiler.marker.Marker;


/**
 *
 * @author Jaroslav Bachorik
 */
public class MarkingEngine {
    private static String INVALID_MID = ResourceBundle.getBundle("org.netbeans.lib.profiler.results.cpu.Bundle").getString("MSG_INVALID_METHODID"); // NOI18N
    
    private static Logger LOGGER = Logger.getLogger(MarkingEngine.class.getName());
    
    //~ Inner Interfaces ---------------------------------------------------------------------------------------------------------

    public static interface StateObserver {
        //~ Methods --------------------------------------------------------------------------------------------------------------

        void stateChanged(MarkingEngine instance);
    }

    //~ Static fields/initializers -----------------------------------------------------------------------------------------------

    private static MarkingEngine instance;

    //~ Instance fields ----------------------------------------------------------------------------------------------------------

    private final Object markGuard = new Object();

    final private MarkMapper mapper;

    // @GuardedBy markGuard
    private MarkMapping[] marks;

    private Set observers = new HashSet();

    //~ Constructors -------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance of MarkingEngine
     */
    private MarkingEngine() {
        mapper = new MarkMapper();
    }

    //~ Methods ------------------------------------------------------------------------------------------------------------------

    public static synchronized MarkingEngine getDefault() {
        if (instance == null) {
            instance = new MarkingEngine();
        }

        return instance;
    }

    // configure the engine for a given set of {@linkplain MarkMapping}
    public synchronized void configure(MarkMapping[] mappings, Collection observers) {
        setMarks(mappings != null ? mappings : Marker.DEFAULT.getMappings());
        this.observers.clear();
        this.observers.add( mapper );
        this.observers.addAll(observers);
    }

    public synchronized void deconfigure() {
        setMarks(Marker.DEFAULT.getMappings());
    }

    public ClientUtils.SourceCodeSelection[] getMarkerMethods() {
        synchronized (markGuard) {
            if (marks == null) {
                return new ClientUtils.SourceCodeSelection[0];
            }

            ClientUtils.SourceCodeSelection[] methods = new ClientUtils.SourceCodeSelection[marks.length];

            for (int i = 0; i < marks.length; i++) {
                methods[i] = marks[i].markMask;
            }

            return methods;
        }
    }

    public int getNMarks() {
        synchronized (markGuard) {
            return (marks != null) ? marks.length : 0;
        }
    }

    public Mark markMethod(int methodId, ProfilingSessionStatus status) {
        synchronized(mapper) {
            return mapper.getMark(methodId, status);
        }
    }

    Mark mark(int methodId, ProfilingSessionStatus status) {
        ClientUtils.SourceCodeSelection method = null;

        synchronized (markGuard) {
            if (marks == null || marks.length == 0 || status == null) {
                return Mark.DEFAULT;
            }

            status.beginTrans(false);

            try {
                String[] cNames = status.getInstrMethodClasses();
                String[] mNames = status.getInstrMethodNames();
                String[] sigs = status.getInstrMethodSignatures();
                
                if (mNames.length <= methodId || cNames.length <= methodId || sigs.length <= methodId) {
                    int maxMid = Math.min(Math.min(mNames.length, cNames.length), sigs.length);
                    LOGGER.log(Level.WARNING, INVALID_MID, new Object[]{methodId, maxMid});
                } else {
                    method = new ClientUtils.SourceCodeSelection(cNames[methodId],
                                                                 mNames[methodId],
                                                                 sigs[methodId]);
                }
            } finally {
                status.endTrans();
            }

            if (method != null) {
                String methodSig = method.toFlattened();

                for (int i = 0; i < marks.length; i++) {
                    if (methodSig.startsWith(marks[i].markSig)) {
                        return marks[i].mark;
                    }
                }
            }

            return Mark.DEFAULT;
        }
    }

    private void setMarks(MarkMapping[] marks) {
        boolean stateChange = false;

        synchronized (markGuard) {
            stateChange = !Arrays.equals(this.marks,marks);
            this.marks = marks;
        }
        if (stateChange) {
            fireStateChanged();
        }
    }

    private void fireStateChanged() {
        for (Iterator iter = observers.iterator(); iter.hasNext();) {
            ((StateObserver) iter.next()).stateChanged(this);
        }
    }
}

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Oracle and/or its affiliates. All rights reserved.
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
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2011 Sun Microsystems, Inc.
 */
package org.netbeans.api.whitelist;

import java.util.Collections;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.annotations.common.NullAllowed;
import org.netbeans.api.java.source.ElementHandle;
import org.netbeans.api.project.Project;
import org.netbeans.modules.whitelist.WhiteListQueryImplementationMerged;
import org.netbeans.modules.whitelist.project.WhiteListLookupProvider;
import org.netbeans.spi.whitelist.WhiteListQueryImplementation;
import org.netbeans.spi.whitelist.WhiteListQueryImplementation.WhiteListImplementation;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;
import org.openide.util.Parameters;

/**
 * Query for finding white lists used to emit errors for usages of non allowed
 * types or methods. The project types supporting a runtime with class usage
 * restriction implement the {@link WhiteListQueryImplementation} to provide a
 * white list of allowed types (methods).
 * @author Tomas Zezula
 * @author David Konecny
 */
public final class WhiteListQuery {

    private static final WhiteListQueryImplementation mergedGlobalWhiteLists = 
        new WhiteListQueryImplementationMerged(Lookup.getDefault());

    private WhiteListQuery(){}

    /**
     * Returns a white list for given file.
     * @param file the file for which the white list should be obtained.
     * @return the {@link WhiteList} for given file or null if no white list
     * is associated with given file.
     */
    @CheckForNull
    public static WhiteList getWhiteList(@NonNull final FileObject file) {
        Parameters.notNull("file", file);   //NOI18N
        final WhiteListImplementation whiteListImpl = mergedGlobalWhiteLists.getWhiteList(file);
        if (whiteListImpl != null) {
            return new WhiteList(whiteListImpl);
        }
        return null;
    }

    /**
     * Enables the given white list in the project.
     * At the moment assumption is that if a project want to automatically enable
     * some white list then they likely know whitelistId.
     * @param project the project in  which the white list should be enabled
     * @param whiteListId the white list identifier
     * @param enable if true the white list is enabled if false white list is disabled
     */
    public static void enableWhiteListInProject(@NonNull Project project, @NonNull String whiteListId, boolean enable) {
        WhiteListLookupProvider.enableWhiteListInProject(project, whiteListId, enable);
    }

    /**
     * Tests whether the given whitelist is enabled in the project or not.
     * @param project the project in to be tested for whitelist presence
     * @param whiteListId the white list identifier
     * @return true if whitelist is enabled in the project
     * @since 1.1
     */
    public static boolean isWhiteListEnabledInProject(@NonNull Project project, @NonNull String whiteListId) {
        return WhiteListLookupProvider.isWhiteListEnabledInProject(project, whiteListId);
    }

    /**
     * The white list used to emit errors for usages of non allowed
     * types or methods.
     */
    public static final class WhiteList {

        private final WhiteListImplementation impl;

        private WhiteList(
            @NonNull final WhiteListImplementation impl) {
            Parameters.notNull("impl", impl);   //NOI18N
            this.impl = impl;
        }

        /**
         * Checks if given method (type) can be invoked (accessed).
         * @param element to check
         * @param operation the operation which should be tested
         * @return a {@link Result} holding the details.
         */
        @NonNull
        public final Result check(
            @NonNull final ElementHandle<?> element,
            @NonNull final Operation operation) {
            Parameters.notNull("element", element); //NOI18N;
            Parameters.notNull("operation", operation); //NOI18N;
            return impl.check(element, operation);
        }

        /**
         * Adds {@link ChangeListener} to white list. The listener is
         * notified when the white list is changed.
         * @param listener to be added
         */
        public void addChangeListener(@NonNull final ChangeListener listener) {
            Parameters.notNull("listener", listener);   //NOI18N
            impl.addChangeListener(listener);
        }

        /**
         * Removes {@link ChangeListener} from white list.
         * @param listener to be removed
         */
        public void removeChangeListener(@NonNull final ChangeListener listener) {
            Parameters.notNull("listener", listener);   //NOI18N
            impl.removeChangeListener(listener);
        }
    }

    /**
     * Operation on element to be tested (usage, subclassing)
     */
    public enum Operation {
        /**
         * Checks the white list if usage of given element is supported.
         */
        USAGE
    }

    /**
     * Result of the white list check.
     */
    public static final class Result {
        private final boolean allowed;
        private final List<? extends RuleDescription> violatedRules;

        /**
         * Creates result which allows operation in question.
         */
        public Result() {
            this.allowed = true;
            this.violatedRules = Collections.<RuleDescription>emptyList();
        }

        /**
         * Creates result which disallows operation in question and lists rules
         * which forbid such operation.
         */
        public Result(@NonNull List<? extends RuleDescription> violatedRules) {
            this.allowed = false;
            this.violatedRules = violatedRules;
        }

        /**
         * Returns true if the operation on given element is allowed by white list.
         * @return true if the operation on given element is allowed
         */
        public boolean isAllowed() {
            return allowed;
        }

        /**
         * Returns rules which forbid this operation.
         * @return list of violated rules
         */
        @NonNull
        public List<? extends RuleDescription> getViolatedRules() {
            return violatedRules;
        }
        
    }
    
    /**
     * Description of a white listing rule.
     * 
     * @since 1.3
     */
    public static final class RuleDescription {
        
        private final String ruleName;
        private final String ruleDescription;
        private final String whiteListID;

        /**
         * @param ruleName rule name
         * @param ruleDescription rule description
         * @param whiteListID whitelist ID; can be null if whitelist does not have ID
         */
        public RuleDescription(@NonNull String ruleName, @NonNull String ruleDescription, @NullAllowed String whiteListID) {
            this.ruleName = ruleName;
            this.ruleDescription = ruleDescription;
            this.whiteListID = whiteListID;
        }

        /**
         * Returns description of rule.
         * @return the description of rule
         */
        @NonNull
        public String getRuleDescription() {
            return ruleDescription;
        }

        /**
         * Returns name of rule.
         * @return the name of rule
         */
        @NonNull
        public String getRuleName() {
            return ruleName;
        }

        /**
         * Returns ID of whitelist which owns this rule.
         * @return  whitelist ID or null if whitelist is not user selectable and does not have an ID
         */
        @CheckForNull
        public String getWhiteListID() {
            return whiteListID;
        }
        
    }
}

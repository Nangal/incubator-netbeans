/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2010 Sun Microsystems, Inc.
 */

package org.netbeans.modules.parsing.lucene;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Scorer;
import org.netbeans.api.annotations.common.NonNull;

/**
 * This class serves on two places. It is called by {@link TermCollecting} class
 * to add documents. It is also used as a proxy to the real Collector passed to the searcher,
 * it intercepts and <b>remembers</b> document base indexes associated with IndexReaders
 * from the {@link #setNextReader(org.apache.lucene.index.IndexReader, int)} call.
 *
 * @author Tomas Zezula
 */
public final class TermCollector extends Collector {
    private final Collector delegate;
    private final Map<Integer, Set<Term>> doc2Terms;
    private int indexOffset;
    
    TermCollector(Collector collector) {
        this.delegate = collector;
        doc2Terms = new HashMap<Integer, Set<Term>>();
    }
    
    public void add (final int docId, final @NonNull Term term) {
        final int realId = docId + indexOffset;
        Set<Term> slot = doc2Terms.get(realId);
        if (slot == null) {
            slot = new HashSet<Term>();
            doc2Terms.put(realId, slot);
        }
        slot.add(term);
    }
    
    Set<Term> get(final int docId) {
        return doc2Terms.get(docId);
    }
    
    Set<? extends Integer> docs() {
        return Collections.unmodifiableSet(doc2Terms.keySet());
    }    
    
    public static interface TermCollecting {
        void attach (TermCollector collector);
    }

    public void setScorer(Scorer scorer) throws IOException {
        delegate.setScorer(scorer);
    }

    public void setNextReader(IndexReader reader, int i) throws IOException {
        delegate.setNextReader(reader, i);
        indexOffset = i;
    }

    public void collect(int i) throws IOException {
        delegate.collect(i);
    }

    public boolean acceptsDocsOutOfOrder() {
        return delegate.acceptsDocsOutOfOrder();
    }

}

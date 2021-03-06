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
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */
package org.netbeans.modules.html.editor.api.gsf;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.text.Document;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.modules.csl.api.ColoringAttributes;
import org.netbeans.modules.csl.api.DeclarationFinder.DeclarationLocation;
import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.api.Hint;
import org.netbeans.modules.csl.api.HintsProvider.HintsManager;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.api.RuleContext;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.html.editor.lib.api.HtmlSource;
import org.netbeans.modules.html.editor.lib.api.elements.Attribute;
import org.netbeans.modules.html.editor.lib.api.foreign.UndeclaredContentResolver;
import org.netbeans.modules.html.editor.lib.api.elements.Element;
import org.netbeans.modules.html.editor.lib.api.elements.Named;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.completion.CompletionItem;

/**
 * An extension of the html editor.
 * 
 * Allows to very simply build a custom html like editor on top of the default one.
 * 
 * Implementation of this class should be registered in the mime lookup by
 * {@link MimeRegistration} annotation for the requested mimetype/s.
 *
 * @author mfukala@netbeans.org
 */
public class HtmlExtension {

    /**
     * Gets custom highlights for the given parser result.
     * 
     * @param result an instance of {@link HtmlParserResult}
     * @param event an instance of {@link SchedulerEvent}
     * @return non null map of range to set of colorings.
     */
    public Map<OffsetRange, Set<ColoringAttributes>> getHighlights(HtmlParserResult result, SchedulerEvent event) {
        return Collections.emptyMap();
    }

    /**
     * Gets a list of custom open tags for the given context.
     * 
     * @param context an instance of {@link CompletionContext}
     * @return non null list of completion items
     */
    public List<CompletionItem> completeOpenTags(CompletionContext context) {
        return Collections.emptyList();
    }
    
    /**
     * Gets a list of custom tag's attributes for the given context.
     * 
     * @param context an instance of {@link CompletionContext}
     * @return non null list of completion items
     */
    public List<CompletionItem> completeAttributes(CompletionContext context) {
        return Collections.emptyList();
    }

    /**
     * Gets a list of possible values of the given tag/attribute.
     * 
     * @param context an instance of {@link CompletionContext}
     * @return non null list of completion items
     */
    public List<CompletionItem> completeAttributeValue(CompletionContext context) {
        return Collections.emptyList();
    }

    /**
     * Gets a reference span for the given location.
     * 
     * The implementation should be very quick as called in EDT, 
     * should be plain document's text or lexer based.
     * 
     * @param doc instance of document where the span is searched
     * @param caretOffset offset where the span is searched.
     * @return non null instance of {@link OffsetRange} representing the reference span.
     */
    public OffsetRange getReferenceSpan(Document doc, int caretOffset) {
        return OffsetRange.NONE;
    }

    /**
     * Finds a declaration location for the previously returned 
     * and activated reference span.
     * 
     * @param info instance of {@link ParserResult}
     * @param caretOffset caret offset
     * @return instance of {@link DeclarationLocation}
     */
    public DeclarationLocation findDeclaration(ParserResult info, int caretOffset) {
        return null;
    }

    /**
     * Creates a list of custom errors for the given context.
     * 
     * @param manager instanceof {@link HintsManager}
     * @param context instance of {@link RuleContext}
     * @param hints list of {@link Hint}s - add the custom error hints to this list
     * @param unhandled list of unhandled errors
     */
    public void computeErrors(HintsManager manager, RuleContext context, List<Hint> hints, List<Error> unhandled) {
        //no-op
    }

    /**
     * Creates a list of selection hints for the given context.
     * 
     * @param manager instanceof {@link HintsManager}
     * @param context instance of {@link RuleContext}
     * @param hints list of {@link Hint}s - add the custom error hints to this list
     * @param start selection start
     * @param end selection end
     */
    public void computeSelectionHints(HintsManager manager, RuleContext context, List<Hint> hints, int start, int end) {
        //no-op
    }
    
     /**
     * Creates a list of suggestion hints for the given context.
     * 
     * @since 2.45
     * 
     * @param manager instanceof {@link HintsManager}
     * @param context instance of {@link RuleContext}
     * @param hints list of {@link Hint}s - add the custom error hints to this list
     * @param caretOffset caret offset
     */
    public void computeSuggestions(HintsManager manager, RuleContext context, List<Hint> hints, int caretOffset) {
        //no-op
    }
 
     /**
     * This method allows to bind some prefixed html source 
     * elements and attributes to a physically undeclared namespace.
     * 
     * @param the html source which is being processed
     * @return a map of namespace to prefix collection
     */
    public Map<String, List<String>> getUndeclaredNamespaces(HtmlSource source) {
        return Collections.emptyMap();
    }
    
    @NonNull
    public Collection<CustomTag> getCustomTags() {
        return Collections.emptyList();
    }
    
    @NonNull
    public Collection<CustomAttribute> getCustomAttributes(String elementName) {
        return Collections.emptyList();
    }
  
    /**
     * Returns true if the given element is a custom tag known to this resolver.
     * 
     * @since 2.44
     * 
     * @param element
     * @param source 
     * @return 
     */
    public boolean isCustomTag(Named element, HtmlSource source) {
        return false;
    }
   
    /**
     * Returns true if the given element's attribute is a custom attribute known to this resolver.
     * 
     * @since 2.44
     * 
     * @param attribute
     * @param source 
     * @return 
     */
    public boolean isCustomAttribute(Attribute attribute, HtmlSource source) {
        return false;
    }
    
    /**
     * Determines whether the given HTML source represents a piece of web javascript-html application (Knockout, AngularJS,...).
     * 
     * TODO: Possibly refactor out along with isCustomXXX() and getCustomXXX() methods to some separate HTML/JS framework API/SPI.
     * 
     * @since 2.26
     * @return true if the html source is a piece a the web application.
     */
    public boolean isApplicationPiece(HtmlParserResult result) {
        return false;
    }
    
    /**
     * Context object for code completion related stuff.
     */
    public static class CompletionContext {

        private HtmlParserResult result;
        private int originalOffset;
        private int ccItemStartOffset;
        private int astoffset;
        private String preText;
        private String itemText;
        private Element currentNode;
        private String attributeName; //for attribute value completion
        private boolean valueQuoted;

        public CompletionContext(HtmlParserResult result, int originalOffset, int astoffset, int ccItemStartOffset, String preText, String itemText) {
            this(result, originalOffset, astoffset, ccItemStartOffset, preText, itemText, null);
        }

        public CompletionContext(HtmlParserResult result, int originalOffset, int astoffset, int ccItemStartOffset, String preText, String itemText, Element currentNode) {
            this(result, originalOffset, astoffset, ccItemStartOffset, preText, itemText, currentNode, null, false);
        }

        public CompletionContext(HtmlParserResult result, int originalOffset, int astoffset, int ccItemStartOffset, String preText, String itemText, Element currentNode, String attributeName, boolean valueQuoted) {
            this.result = result;
            this.originalOffset = originalOffset;
            this.astoffset = astoffset;
            this.preText = preText;
            this.ccItemStartOffset = ccItemStartOffset;
            this.currentNode = currentNode;
            this.itemText = itemText;
            this.attributeName = attributeName;
            this.valueQuoted = valueQuoted;
        }

        /**
         * Returns the completion prefix.
         */
        public String getPrefix() {
            return preText;
        }

        /** 
         * Returns the whole word under cursor.
         */
        public String getItemText() {
            return itemText;
        }

        /**
         * Returns the embedded caret offset.
         */
        public int getAstoffset() {
            return astoffset;
        }

        /**
         * Returns the document caret offset.
         */
        public int getOriginalOffset() {
            return originalOffset;
        }

        /**
         * Returns the completion anchor offset.
         */
        public int getCCItemStartOffset() {
            return ccItemStartOffset;
        }

        /**
         * Returns an instance of the {@link HtmlParserResult}
         */
        public HtmlParserResult getResult() {
            return result;
        }

        /**
         * Returns a node of the html parse tree found at the caret location.
         */
        public Element getCurrentNode() {
            return currentNode;
        }

        /**
         * Returns an attribute name if completion invoked in an open tag.
         */
        public String getAttributeName() {
            return attributeName;
        }

        /**
         * Returns true if completion invoked in tags' attribute value and
         * the value is quoted.
         */
        public boolean isValueQuoted() {
            return valueQuoted;
        }
    }
}

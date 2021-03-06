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
 *
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

package org.netbeans.modules.spring.beans.completion;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.api.xml.lexer.XMLTokenId;
import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.modules.spring.beans.editor.DocumentContext;
import org.netbeans.modules.xml.text.api.dom.SyntaxElement;
import org.netbeans.modules.xml.text.api.dom.XMLSyntaxSupport;
import org.netbeans.modules.xml.text.syntax.XMLKit;
import org.netbeans.spi.editor.completion.CompletionProvider;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.w3c.dom.Node;

/**
 * Tracks context information for a code completion scenario
 *
 * @author Rohan Ranade (Rohan.Ranade@Sun.COM)
 */
public class CompletionContext {
    private List<String> existingAttributes;

    public static enum CompletionType {
        TAG,
        VALUE,
        ATTRIBUTE,
        ATTRIBUTE_VALUE,
        NONE
    };

    private CompletionType completionType = CompletionType.NONE;
    private int caretOffset;
    private DocumentContext documentContext;
    private String typedChars = "";
    private char lastTypedChar;
    private XMLSyntaxSupport support;
    private FileObject fileObject;
    private BaseDocument internalDoc = new BaseDocument(true, XMLKit.MIME_TYPE);
    private int queryType;

    public CompletionContext(Document doc, int caretOffset, int queryType) {
        this.caretOffset = caretOffset;
        this.fileObject = NbEditorUtilities.getFileObject(doc);
        this.queryType = queryType;
        try {
            initContext((BaseDocument) doc);
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void initContext(BaseDocument bDoc) throws BadLocationException {
        boolean copyResult = copyDocument(bDoc, internalDoc);
        if(!copyResult) {
            return;
        }

        Object sdp = bDoc.getProperty(Document.StreamDescriptionProperty);
        internalDoc.putProperty(Document.StreamDescriptionProperty, sdp);
        this.support = (XMLSyntaxSupport)XMLSyntaxSupport.getSyntaxSupport(internalDoc);
        this.documentContext = DocumentContext.create(internalDoc, caretOffset);

        // get last inserted character from the actual document
        this.lastTypedChar = support.lastTypedChar();

        if(documentContext == null) {
            return;
        }

        Token<XMLTokenId> token = documentContext.getCurrentToken();
        if(token == null) {
            return;
        }

        int tOffset = documentContext.getCurrentTokenOffset();
        String tokenText = token.text().toString();
        XMLTokenId id = token.id();
        int tlen = token.length();
        
        // see issue #191651
        // ExtSyntaxSupport returns token on base of caretoffset and caretoffset+1 which
        //  returns ERROR token at line ending (just "/" means error). In that cases
        //  is fake WS token created with position between previous and current error
        //  token for purposes of CC. Possibly will be extended about more characters.
        if (token.id() == XMLTokenId.ERROR
                && token.text().toString().equals("/")) {
            tokenText = " ";
            id = XMLTokenId.WS;
            tlen = 1;
            tOffset = caretOffset;
        }
        boolean tokenBoundary = (tOffset == caretOffset)
                || ((tOffset + tlen) == caretOffset);

        SyntaxElement element = documentContext.getCurrentElement();
        switch (id) {
            //user enters < character
            case TEXT:
                String chars = tokenText.trim();
                Token<XMLTokenId> previousTokenItem = support.getPreviousToken(tOffset);
                if (previousTokenItem == null) {
                    completionType = CompletionType.NONE;
                    break;
                }
                String text = previousTokenItem.text().toString().trim();
                if (chars != null && chars.equals("") &&
                        text.equals("/>")) { // NOI18N
                    completionType = CompletionType.NONE;
                    break;
                }
                if (chars != null && chars.equals("") &&
                        text.trim().equals(">")) { // NOI18N
                    completionType = CompletionType.VALUE;
                    break;
                }
                if (chars != null && !chars.equals("<") &&
                        text.trim().equals(">")) { // NOI18N
                    completionType = CompletionType.NONE;
                    break;
                }
                if (chars != null && chars.startsWith("<")) { // NOI18N
                    typedChars = chars.substring(1);
                }
                completionType = CompletionType.TAG;
                break;

            //start tag of an element
            case TAG:
                if (support.isEndTag(element)) {
                    completionType = CompletionType.NONE;
                    break;
                }
                if (support.isEmptyTag(element)) {
                    if (tokenText.trim().equals("/>")) { // NOI18N
                        Token<XMLTokenId> prevToken = support.getPreviousToken(tOffset);
                        if(prevToken != null && prevToken.id() == XMLTokenId.WS
                                && caretOffset == tOffset) {
                            completionType = CompletionType.ATTRIBUTE;
                        } else {
                            completionType = CompletionType.NONE;
                        }
                        break;
                    }
                    if (element.getElementOffset() + 1 == this.caretOffset) {
                        completionType = CompletionType.TAG;
                        break;
                    }
                    String tagName = element.getNode().getNodeName();
                    if (caretOffset > element.getElementOffset() + 1 &&
                            caretOffset <= element.getElementOffset() + 1 + tagName.length()) {
                        completionType = CompletionType.TAG;
                        typedChars = tagName;
                        break;
                    }
                    completionType = CompletionType.ATTRIBUTE;
                    break;
                }

                if (support.isStartTag(element)) {
                    if (tokenText.toString().trim().equals(">")) { // NOI18N
                        Token<XMLTokenId> prevToken = support.getPreviousToken(tOffset);
                        if(prevToken != null && prevToken.id() == XMLTokenId.WS
                                && caretOffset == tOffset) {
                            completionType = CompletionType.ATTRIBUTE;
                        } else {
                            completionType = CompletionType.NONE;
                        }
                        break;
                    }
                    if (element.getElementOffset() + 1 != this.caretOffset) {
                        typedChars = element.getNode().getNodeName();
                    }
                }
                if (lastTypedChar == '>') {
                    completionType = CompletionType.VALUE;
                    break;
                }
                completionType = CompletionType.TAG;
                break;

            //user enters an attribute name
            case ARGUMENT:
                completionType = CompletionType.ATTRIBUTE;
                typedChars = tokenText.substring(0, caretOffset - tOffset);
                break;

            //some random character
            case CHARACTER:
            //user enters = character, we should ignore all other operators
            case OPERATOR:
                completionType = CompletionType.NONE;
                break;
            //user enters either ' or "
            case VALUE:
                if(!tokenBoundary) {
                    completionType = CompletionType.ATTRIBUTE_VALUE;
                    typedChars = tokenText.subSequence(1, caretOffset - tOffset).toString();
                } else {
                    completionType = CompletionType.NONE;
                }
                break;

            //user enters white-space character
            case WS:
                completionType = CompletionType.NONE;
                int[] offset = new int[1];
                Token<XMLTokenId> prev = support.runWithSequence(tOffset,
                    (TokenSequence ts) -> {
                        Token<XMLTokenId> t  = null;
                        boolean ok;
                        while ((ok = ts.movePrevious())) {
                            t = ts.token();
                            if (t.id() != XMLTokenId.WS) {
                                break;
                            }
                        }
                        if (ok) {
                            offset[0] = ts.offset();
                            return t;
                        } else {
                            return null;
                        }
                    }
                );
                if (prev == null) {
                    completionType = CompletionType.NONE;
                    break;
                }
                int prevOffset = offset[0];
                if(prev.id() == XMLTokenId.ARGUMENT
                        && prevOffset + prev.length() == caretOffset) {
                    typedChars = prev.text().toString();
                    completionType = CompletionType.ATTRIBUTE;
                } else if (((prev.id() == XMLTokenId.VALUE) ||
                        (prev.id() == XMLTokenId.TAG))
                        && !tokenBoundary) {
                    completionType = CompletionType.ATTRIBUTE;
                }
                break;

            default:
                completionType = CompletionType.NONE;
                break;
        }
    }

    private boolean copyDocument(final BaseDocument src, final BaseDocument dest) {
        final boolean[] retVal = new boolean[]{true};

        src.readLock();
        try{
            dest.runAtomic(new Runnable() {

                @Override
                public void run() {
                    try {
                        String docText = src.getText(0, src.getLength());
                        dest.insertString(0, docText, null);
//                        dest.putProperty(Language.class, src.getProperty(Language.class));
                    } catch(BadLocationException ble) {
                        Exceptions.printStackTrace(ble);
                        retVal[0] = false;
                    }
                }
            });
        } finally {
            src.readUnlock();
        }

        return retVal[0];
    }

    public CompletionType getCompletionType() {
        return completionType;
    }

    public String getTypedPrefix() {
        return typedChars;
    }

    public FileObject getFileObject() {
        return this.fileObject;
    }

    public DocumentContext getDocumentContext() {
        return this.documentContext;
    }

    public int getCaretOffset() {
        return caretOffset;
    }

    public Node getTag() {
        SyntaxElement element = documentContext.getCurrentElement();
        return element.getType() == Node.ELEMENT_NODE ? element.getNode() : null;
    }

    public Token<XMLTokenId> getCurrentToken() {
        return documentContext.getCurrentToken();
    }
    
    public int getCurrentTokenOffset() {
        return documentContext.getCurrentTokenOffset();
    }
    
    private List<String> getExistingAttributesLocked(TokenSequence ts) {
        List<String> existingAttributes = new ArrayList<String>();
        while (ts.movePrevious()) {
            Token<XMLTokenId> item = ts.token();
            XMLTokenId tokenId = item.id();
            if (tokenId == XMLTokenId.TAG) {
                break;
            }
            if (tokenId == XMLTokenId.ARGUMENT) {
                existingAttributes.add(item.text().toString());
            }
        }
        return existingAttributes;
    }

    public List<String> getExistingAttributes() {
        if (existingAttributes == null) {
            try {
                existingAttributes = (List<String>)support.runWithSequence(
                        documentContext.getCurrentTokenOffset(),
                        this::getExistingAttributesLocked
                );
            } catch (BadLocationException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return existingAttributes;
    }

    /**
     * Returns the type of completion query. The returned value is one of
     * the query types defined in <code>CompletionProvider</code>
     *
     * @see CompletionProvider
     *
     * @return completion query type
     */
    public int getQueryType() {
        return queryType;
    }

    public Document getDocument() {
        return internalDoc;
    }
}

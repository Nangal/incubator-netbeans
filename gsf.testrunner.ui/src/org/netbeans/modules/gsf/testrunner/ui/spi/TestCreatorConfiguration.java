/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2014 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2014 Sun Microsystems, Inc.
 */
package org.netbeans.modules.gsf.testrunner.ui.spi;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.project.SourceGroup;
import org.openide.filesystems.FileObject;
import org.openide.util.Pair;

/**
 * SPI to hook to the configuration dialog when creating tests
 * @since 1.5
 * @author Theofanis Oikonomou
 */
public abstract class TestCreatorConfiguration {
    
    /**
     *
     * @param framework the selected framework while creating tests, e.g. JUnit, TestNG or Selenium
     * @return {@code true} if this provider can handle the specific framework, {@code false} otherwise
     */
    public abstract boolean canHandleProject(String framework);
    
    /**
     * Checks if this configuration panel is valid. If the panel is valid, the
     * "OK" button in the "Create Tests" dialog might be enabled.
     * <p>
     * If it returns {@code true}, check
     * {@link #getErrorMessage() error message}, it should be {@code null} for
     * no message and not {@code null} if there is a warning. If it returns
     * {@code false}, check {@link #getErrorMessage() error message}, it should
     * not be {@code null}.
     * <p>
     * Default implementation just returns {@code true}.
     *
     * @return {@code true} if the user has entered satisfactory information and
     * the configuration panel is valid, {@code false} otherwise.
     * @see #getErrorMessage()
     * @since 1.9
     */
    public boolean isValid() {
        return true;
    }

    /**
     * Gets error message or warning message/{@code null} if the configuration panel is
     * {@link #isValid() valid}.
     * <p>
     * Default implementation just returns {@code null}.
     *
     * @return error message or warning message/{@code null} if the configuration panel is
     * {@link #isValid() valid}
     * @see #isValid()
     * @since 1.9
     */
    @CheckForNull
    public String getErrorMessage() {
        return null;
    }
    
    /**
     * Finds the text component that should be presented in the test creator dialog.
     * Default implementation just returns an empty text area
     * 
     * @return the configuration panel
     */
    public JTextComponent getMessagePanel(Context context) {
        return new JTextArea("");
    }
    
    /**
     * Determines whether "Class Name" label and text field should be shown.
     * Default implementation just returns {@code true}
     * 
     * @return {@code true} if this provider should show "Class Name" label and text field, {@code false} otherwise
     */
    public boolean showClassNameInfo() {
        return true;
    }
    
    /**
     * Determines whether "Class to Test" label should be shown.
     * Default implementation just returns {@code true}
     * 
     * @return {@code true} if this provider should show "Class to Test" label, {@code false} otherwise
     * @since 1.6
     */
    public boolean showClassToTestInfo() {
        return true;
    }
    
    /**
     * Finds the configuration panel that should be presented in the test creator dialog.
     * Default implementation just returns an empty panel
     * 
     * @return the configuration panel
     */
    public Component getConfigurationPanel(Context context) {
        JPanel codeGenPanel = new JPanel();
        codeGenPanel.setPreferredSize(new Dimension(447, 344));
        return codeGenPanel;
    }
    
    /**
     * Saves data entered by the user to the configuration panel in the test creator dialog.
     */
    public abstract void persistConfigurationPanel(Context context);
    
    /**
     * Finds <code>SourceGroup</code>s where a test for the given class
     * can be created (so that it can be found by the projects infrastructure
     * when a test for the class is to be opened or run).
     *
     * @param createdSourceRoots
     * @param  fo  <code>FileObject</code> to find target <code>SourceGroup</code>(s) for
     * @return  an array of objects - each of them can be either
     *          a <code>SourceGroup</code> for a possible target folder
     *          or simply a <code>FileObject</code> representing a possible
     *          target folder (if <code>SourceGroup</code>) for the folder
     *          was not found);
     *          the returned array may be empty but not <code>null</code>
     */
    public abstract Object[] getTestSourceRoots(Collection<SourceGroup> createdSourceRoots, FileObject fo);

    /**
     * Finds source and test class names.
     *
     * @param fo <code>FileObject</code> to find Source and Test filenames for
     * @param isTestNG {@code true} if user wants to create TestNG test, {@code false} otherwise
     * @param isSelenium {@code true} if user wants to create Selenium test, {@code false} otherwise
     * @return  a <code>Pair</code> of Strings - the first one being the source class name
     *          and the second being the test class name.
     *          the returned <code>Pair</code> may be empty but not <code>null</code>
     */
    public abstract Pair<String, String> getSourceAndTestClassNames(FileObject fo, boolean isTestNG, boolean isSelenium);
    
    /**
     * Callback used to trigger test creator panel validation, in order
     * to check whether data filled in it are valid
     */
    public interface Callback {
        
        /**
         * Check whether data filled in the test creator panel are valid
         */
        void checkAcceptability();
    }
    
    /**
     * Holds needed information for creating the configuration panel
     */
    public static final class Context {

        private final boolean multipleClasses;
        private final Callback callback;
        private final HashMap<String, Object> properties;
        
        /**
         *
         * @param multipleClasses
         * @param callback
         */
        public Context(boolean multipleClasses, Callback callback) {
            this.multipleClasses = multipleClasses;
            this.callback = callback;
            this.properties = new HashMap<String, Object>();
        }

        /**
         * 
         * @return {@code true} if a test for multiple classes is to be created, {@code false} otherwise
         */
        public boolean isMultipleClasses() {
            return multipleClasses;
        }

        /**
         * 
         * @return Callback used to trigger test creator panel validation,
         * in order to check whether data filled in it are valid
         */
        public Callback getCallback() {
            return callback;
        }

        /**
         * Get properties from configuration panel inside "Create Tests" dialog.
         * The configuration panel is bound to the selected testing framework inside this dialog.
         * These properties will be transfered to the {@link org.netbeans.modules.gsf.testrunner.api.TestCreatorProvider#createTests(org.netbeans.modules.gsf.testrunner.api.TestCreatorProvider.Context)}
         * method once the user approves the dialog by clicking the OK button.
         *
         * @return map of properties from configuration panel inside "Create Tests" dialog
         */
        public Map<String, Object> getProperties() {
            return properties;
        }
        
    }
}

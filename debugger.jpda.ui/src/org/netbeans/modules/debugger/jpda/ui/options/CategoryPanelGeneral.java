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

/*
 * CategoryPanelGeneral.java
 *
 * Created on Jan 20, 2009, 3:30:12 PM
 */

package org.netbeans.modules.debugger.jpda.ui.options;

import java.util.prefs.Preferences;
import org.netbeans.api.debugger.Properties;
import org.netbeans.api.debugger.jpda.JPDABreakpoint;
import org.netbeans.modules.options.java.api.JavaOptions;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.NbPreferences;

/**
 *
 * @author Martin Entlicher
 */
@OptionsPanelController.Keywords(keywords={"#CategoryPanelGeneral.kw1", "#CategoryPanelGeneral.kw2",
                                           "#CategoryPanelGeneral.kw3", "#CategoryPanelGeneral.kw4",
                                           "#CategoryPanelGeneral.kw5", "#CategoryPanelGeneral.kw6"},
                                 location=JavaOptions.JAVA, tabTitle="#LBL_JavaDebugger")
class CategoryPanelGeneral extends StorablePanel {

    /** Creates new form CategoryPanelGeneral */
    public CategoryPanelGeneral() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stopOnExceptionsCheckBox = new javax.swing.JCheckBox();
        applyCodeChangesCheckBox = new javax.swing.JCheckBox();
        breakpointsSuspendLabel = new javax.swing.JLabel();
        stepsResumeLabel = new javax.swing.JLabel();
        breakpointsSuspendComboBox = new javax.swing.JComboBox();
        stepsResumeComboBox = new javax.swing.JComboBox();
        openDebuggerConsoleCheckBox = new javax.swing.JCheckBox();
        reuseTabsCheckBox = new javax.swing.JCheckBox();

        org.openide.awt.Mnemonics.setLocalizedText(stopOnExceptionsCheckBox, org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.stopOnExceptionsCheckBox.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(applyCodeChangesCheckBox, org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.applyCodeChangesCheckBox.text")); // NOI18N

        breakpointsSuspendLabel.setLabelFor(breakpointsSuspendComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(breakpointsSuspendLabel, org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.breakpointsSuspendLabel.text")); // NOI18N

        stepsResumeLabel.setLabelFor(stepsResumeComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(stepsResumeLabel, org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.stepsResumeLabel.text")); // NOI18N

        breakpointsSuspendComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.breakpointsSuspendCB.allThreads"), org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.breakpointsSuspendCB.breakpointThread"), org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.breakpointsSuspendCB.noThread") }));

        stepsResumeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.stepsResumeCB.allThreads"), org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.stepsResumeCB.currentThread") }));

        org.openide.awt.Mnemonics.setLocalizedText(openDebuggerConsoleCheckBox, org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.openDebuggerConsoleCheckBox.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(reuseTabsCheckBox, org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.reuseTabsCheckBox.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reuseTabsCheckBox)
                    .addComponent(stopOnExceptionsCheckBox)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(breakpointsSuspendLabel)
                            .addComponent(stepsResumeLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stepsResumeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(breakpointsSuspendComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(applyCodeChangesCheckBox)
                    .addComponent(openDebuggerConsoleCheckBox))
                .addContainerGap(104, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(stopOnExceptionsCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(applyCodeChangesCheckBox)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(breakpointsSuspendLabel)
                    .addComponent(breakpointsSuspendComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stepsResumeLabel)
                    .addComponent(stepsResumeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(openDebuggerConsoleCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reuseTabsCheckBox)
                .addContainerGap(130, Short.MAX_VALUE))
        );

        stopOnExceptionsCheckBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.stopOnExceptionsCheckBox.AccessibleContext.accessibleDescription")); // NOI18N
        applyCodeChangesCheckBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.applyCodeChangesCheckBox.AccessibleContext.accessibleDescription")); // NOI18N
        breakpointsSuspendLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.breakpointsSuspendLabel.AccessibleContext.accessibleDescription")); // NOI18N
        stepsResumeLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.stepsResumeLabel.AccessibleContext.accessibleDescription")); // NOI18N
        openDebuggerConsoleCheckBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.openDebuggerConsoleCheckBox.AccessibleContext.accessibleDescription")); // NOI18N
        reuseTabsCheckBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CategoryPanelGeneral.class, "CategoryPanelGeneral.reuseTabsCheckBox.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void load() {
        //Preferences p = NbPreferences.root().node("Debugger/JPDA");
        Properties p = Properties.getDefault().getProperties("debugger.options.JPDA");
        applyCodeChangesCheckBox.setSelected(p.getBoolean("ApplyCodeChangesOnSave", false));
        stopOnExceptionsCheckBox.setSelected(p.getBoolean("CatchExceptions", false));
        breakpointsSuspendComboBox.setSelectedIndex(suspendIndex(p.getInt("BreakpointSuspend", JPDABreakpoint.SUSPEND_EVENT_THREAD)));
        stepsResumeComboBox.setSelectedIndex(resumeIndex(p.getInt("StepResume", 1)));
        openDebuggerConsoleCheckBox.setSelected(p.getBoolean("OpenDebuggerConsole", true));
        reuseTabsCheckBox.setSelected(p.getBoolean("ReuseEditorTabs", true));
    }

    @Override
    public void store() {
        //Preferences p = NbPreferences.root().node("Debugger/JPDA");
        Properties p = Properties.getDefault().getProperties("debugger.options.JPDA");
        p.setBoolean("ApplyCodeChangesOnSave", applyCodeChangesCheckBox.isSelected());
        p.setBoolean("CatchExceptions", stopOnExceptionsCheckBox.isSelected());
        p.setInt("BreakpointSuspend", suspendProp(breakpointsSuspendComboBox.getSelectedIndex()));
        p.setInt("StepResume", resumeProp(stepsResumeComboBox.getSelectedIndex()));
        p.setBoolean("OpenDebuggerConsole", openDebuggerConsoleCheckBox.isSelected());
        p.setBoolean("ReuseEditorTabs", reuseTabsCheckBox.isSelected());
    }

    @Override
    public boolean isChanged() {
        Properties p = Properties.getDefault().getProperties("debugger.options.JPDA");
        return applyCodeChangesCheckBox.isSelected() != p.getBoolean("ApplyCodeChangesOnSave", false)
                || stopOnExceptionsCheckBox.isSelected() != p.getBoolean("CatchExceptions", false)
                || breakpointsSuspendComboBox.getSelectedIndex() != suspendIndex(p.getInt("BreakpointSuspend", JPDABreakpoint.SUSPEND_EVENT_THREAD))
                || stepsResumeComboBox.getSelectedIndex() != resumeIndex(p.getInt("StepResume", 1))
                || openDebuggerConsoleCheckBox.isSelected() != p.getBoolean("OpenDebuggerConsole", true)
                || reuseTabsCheckBox.isSelected() != p.getBoolean("ReuseEditorTabs", true);
    }

    private static int suspendIndex(int jpdaBreakpointSuspend) {
        switch (jpdaBreakpointSuspend) {
            case JPDABreakpoint.SUSPEND_ALL: return 0;
            case JPDABreakpoint.SUSPEND_EVENT_THREAD: return 1;
            case JPDABreakpoint.SUSPEND_NONE: return 2;
            default: return 1;
        }
    }

    private static int suspendProp(int index) {
        switch (index) {
            case 0: return JPDABreakpoint.SUSPEND_ALL;
            case 1: return JPDABreakpoint.SUSPEND_EVENT_THREAD;
            case 2: return JPDABreakpoint.SUSPEND_NONE;
            default:
                throw new IllegalArgumentException("Bad index: "+index);
        }
    }

    private static int resumeIndex(int stepResume) {
        // 0 ... resume all threads
        // 1 ... resume current thread
        if (stepResume >= 0 && stepResume <= 1) {
            return stepResume;
        } else {
            return 1;
        }
    }

    private static int resumeProp(int index) {
        // 0 ... resume all threads
        // 1 ... resume current thread
        if (index >= 0 && index <= 1) {
            return index;
        } else {
            throw new IllegalArgumentException("Bad index: "+index);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox applyCodeChangesCheckBox;
    private javax.swing.JComboBox breakpointsSuspendComboBox;
    private javax.swing.JLabel breakpointsSuspendLabel;
    private javax.swing.JCheckBox openDebuggerConsoleCheckBox;
    private javax.swing.JCheckBox reuseTabsCheckBox;
    private javax.swing.JComboBox stepsResumeComboBox;
    private javax.swing.JLabel stepsResumeLabel;
    private javax.swing.JCheckBox stopOnExceptionsCheckBox;
    // End of variables declaration//GEN-END:variables

}

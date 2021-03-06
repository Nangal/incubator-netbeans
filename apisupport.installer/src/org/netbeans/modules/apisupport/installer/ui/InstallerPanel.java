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


package org.netbeans.modules.apisupport.installer.ui;

/**
 *
 * @author Dmitry Lipin
 * @author Alexei Mokeev
 */
public class InstallerPanel extends javax.swing.JPanel {

    private SuiteInstallerProjectProperties installerProps;

    /** Creates new form InstallerPanel */
    public InstallerPanel(SuiteInstallerProjectProperties props) {
        this.installerProps = props;
        initComponents();

        jCheckBox1.setModel(installerProps.windowsModel);
        jCheckBox2.setModel(installerProps.linuxModel);
        jCheckBox3.setModel(installerProps.macModel);
        jCheckBox4.setModel(installerProps.solarisModel);
        jCheckBox5.setModel(installerProps.pack200Model);
        
        licenseComboBox.setModel(installerProps.licenseModel);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        licenseLabel = new javax.swing.JLabel();
        licenseComboBox = new javax.swing.JComboBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jSeparator2 = new javax.swing.JSeparator();
        pack200Info = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.Platforms.Label")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.OSLabelWindows")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox2, org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.OSLabelLinux")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox3, org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.OSLabelMacOS")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox4, org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.OSLabelSolaris")); // NOI18N

        licenseLabel.setLabelFor(licenseComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(licenseLabel, org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.licenseLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox5, org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.pack200checkBox.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(pack200Info, org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.Pack200.Description.Text")); // NOI18N
        pack200Info.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        pack200Info.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 1122, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox1)
                                    .addComponent(jCheckBox2)
                                    .addComponent(jCheckBox3)
                                    .addComponent(jCheckBox4))
                                .addGap(53, 53, 53))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(licenseLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(licenseComboBox, 0, 1027, Short.MAX_VALUE)))
                        .addGap(0, 0, 0))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBox5, javax.swing.GroupLayout.DEFAULT_SIZE, 1116, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 1122, Short.MAX_VALUE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(pack200Info, javax.swing.GroupLayout.DEFAULT_SIZE, 1091, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(licenseLabel)
                    .addComponent(licenseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pack200Info, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addGap(99, 99, 99))
        );

        jLabel2.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.Platforms.Label")); // NOI18N
        jCheckBox1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.OSLabelWindows.AccessibleContext.accessible")); // NOI18N
        jCheckBox2.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.OSLabelLinux.AccessibleContext.accessible")); // NOI18N
        jCheckBox3.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.OSLabelMacOS.AccessibleContext.accessible")); // NOI18N
        jCheckBox4.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.OSLabelSolaris.AccessibleContext.accessible")); // NOI18N
        licenseComboBox.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.licenseComboBox.AccessibleContext.accessibleName")); // NOI18N
        licenseComboBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.licenseComboBox.AccessibleContext.accessibleDescription")); // NOI18N
        jCheckBox5.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InstallerPanel.class, "InstallerPanel.jCheckBox5.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JComboBox licenseComboBox;
    private javax.swing.JLabel licenseLabel;
    private javax.swing.JLabel pack200Info;
    // End of variables declaration//GEN-END:variables

    
}

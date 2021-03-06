/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */
package org.netbeans.spi.editor.hints.projects.support;

import org.netbeans.spi.editor.hints.projects.PerProjectHintsPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JComponent;
import org.netbeans.api.options.OptionsDisplayer;
import org.netbeans.modules.editor.tools.storage.api.ToolPreferences;
import org.netbeans.spi.editor.hints.projects.PerProjectHintsPanel.MimeType2Preferences;
import org.netbeans.spi.editor.hints.projects.ProjectSettings;
import org.netbeans.spi.editor.hints.projects.support.StandardProjectSettings.Standard;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author lahvac
 */
class ProjectHintSettingPanel extends javax.swing.JPanel {

    private static final Logger LOG = Logger.getLogger(ProjectHintSettingPanel.class.getName());
    
    private final PerProjectHintsPanel panel;
    private final JComponent panelUI;
    private final Standard projectSettings;
    private       String settingsFileLocation;
    private       MimeType2Preferences perProjectPreferences;
    private       ToolPreferences toolPreferences;
    
    public ProjectHintSettingPanel(final Standard projectSettings, String customizersFolderLocation) {
        this.projectSettings = projectSettings;
        this.settingsFileLocation = projectSettings.hasLocation() ? projectSettings.getSettingsFileLocation() : null;
        settingsFileLocationChanged();
        FileObject customizersFolder;
        
        try {
            //XXX:
            customizersFolder = FileUtil.createFolder(FileUtil.getConfigRoot(), customizersFolderLocation);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }

        panel = PerProjectHintsPanel.create(customizersFolder);
        initComponents();
        advanced.setVisible(projectSettings.hasLocation());
        if (projectSettings.getUseProjectSettings()) {
            useProjectSettings.setSelected(true);
        } else {
            useGlobalSettings.setSelected(true);
        }
        settingsCustomizer.setLayout(new BorderLayout());
        settingsCustomizer.add(panelUI = panel.getPanel(), BorderLayout.CENTER);
        settingsOriginChanged();
    }

    private void settingsOriginChanged() {
        globalSettings.setEnabled(!useProjectSettings.isSelected());
        advanced.setEnabled(useProjectSettings.isSelected());

        if (useProjectSettings.isSelected()) {
            panel.setPerProjectSettings(perProjectPreferences);
        } else {
            panel.setGlobalSettings();
        }
        enableDisableRecursively(panelUI, useProjectSettings.isSelected());
    }
    
    private void enableDisableRecursively(Component what, boolean enable) {
        what.setEnabled(enable);
        if (what instanceof Container) {
            for (Component c : ((Container) what).getComponents()) {
                enableDisableRecursively(c, enable);
            }
        }
    }
    
    public ToolPreferences commit() {
        projectSettings.setUseProjectSettings(useProjectSettings.isSelected());
        if (projectSettings.hasLocation())
            projectSettings.setSettingsFileLocation(settingsFileLocation);
        panel.applyChanges();
        return useProjectSettings.isSelected() && toolPreferences != null ? toolPreferences : null;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        settingsSourceSelection = new javax.swing.ButtonGroup();
        useGlobalSettings = new javax.swing.JRadioButton();
        useProjectSettings = new javax.swing.JRadioButton();
        advanced = new javax.swing.JButton();
        settingsCustomizer = new javax.swing.JPanel();
        globalSettings = new javax.swing.JButton();

        settingsSourceSelection.add(useGlobalSettings);
        org.openide.awt.Mnemonics.setLocalizedText(useGlobalSettings, org.openide.util.NbBundle.getMessage(ProjectHintSettingPanel.class, "ProjectHintSettingPanel.useGlobalSettings.text_1")); // NOI18N
        useGlobalSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useGlobalSettingsActionPerformed(evt);
            }
        });

        settingsSourceSelection.add(useProjectSettings);
        org.openide.awt.Mnemonics.setLocalizedText(useProjectSettings, org.openide.util.NbBundle.getMessage(ProjectHintSettingPanel.class, "ProjectHintSettingPanel.useProjectSettings.text_1")); // NOI18N
        useProjectSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useProjectSettingsActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(advanced, org.openide.util.NbBundle.getMessage(ProjectHintSettingPanel.class, "ProjectHintSettingPanel.advanced.text_1")); // NOI18N
        advanced.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                advancedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout settingsCustomizerLayout = new javax.swing.GroupLayout(settingsCustomizer);
        settingsCustomizer.setLayout(settingsCustomizerLayout);
        settingsCustomizerLayout.setHorizontalGroup(
            settingsCustomizerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 819, Short.MAX_VALUE)
        );
        settingsCustomizerLayout.setVerticalGroup(
            settingsCustomizerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 208, Short.MAX_VALUE)
        );

        org.openide.awt.Mnemonics.setLocalizedText(globalSettings, org.openide.util.NbBundle.getMessage(ProjectHintSettingPanel.class, "ProjectHintSettingPanel.globalSettings.text")); // NOI18N
        globalSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalSettingsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(settingsCustomizer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(useGlobalSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(useProjectSettings, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(advanced, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(globalSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(useGlobalSettings)
                    .addComponent(globalSettings))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(useProjectSettings)
                    .addComponent(advanced))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(settingsCustomizer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void useGlobalSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useGlobalSettingsActionPerformed
        settingsOriginChanged();
    }//GEN-LAST:event_useGlobalSettingsActionPerformed

    private void useProjectSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useProjectSettingsActionPerformed
        settingsOriginChanged();
    }//GEN-LAST:event_useProjectSettingsActionPerformed

    @Messages("CAP_ProjectSpecificOptions=Project Specific Options Settings")
    private void advancedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_advancedActionPerformed
        AdvancedLocationPanel panel = new AdvancedLocationPanel(settingsFileLocation, projectSettings);
        DialogDescriptor dd = new DialogDescriptor(panel, Bundle.CAP_ProjectSpecificOptions(), true, DialogDescriptor.OK_CANCEL_OPTION, DialogDescriptor.OK_OPTION, null);
        
        if (DialogDisplayer.getDefault().notify(dd) == DialogDescriptor.OK_OPTION) {
            settingsFileLocation = panel.getHintFileLocation();
            settingsFileLocationChanged();
            settingsOriginChanged();
        }
    }//GEN-LAST:event_advancedActionPerformed

    private void globalSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalSettingsActionPerformed
        OptionsDisplayer.getDefault().open("Editor/Hints");
    }//GEN-LAST:event_globalSettingsActionPerformed

    private void settingsFileLocationChanged() {
        if (projectSettings.hasLocation()) {
            final ToolPreferences toolPreferencesFin = toolPreferences = projectSettings.preferencesFrom(settingsFileLocation);
            perProjectPreferences = new MimeType2Preferences() {
                @Override public Preferences getPreferences(String mimeType) {
                    return toolPreferencesFin.getPreferences(ProjectSettings.HINTS_TOOL_ID, mimeType);
                }
            };
        } else {
            perProjectPreferences = new MimeType2Preferences() {
                @Override public Preferences getPreferences(String mimeType) {
                    return projectSettings.getProjectSettings(mimeType);
                }
            };
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton advanced;
    private javax.swing.JButton globalSettings;
    private javax.swing.JPanel settingsCustomizer;
    private javax.swing.ButtonGroup settingsSourceSelection;
    private javax.swing.JRadioButton useGlobalSettings;
    private javax.swing.JRadioButton useProjectSettings;
    // End of variables declaration//GEN-END:variables
}

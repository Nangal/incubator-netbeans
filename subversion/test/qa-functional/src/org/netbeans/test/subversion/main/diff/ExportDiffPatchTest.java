/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2012 Sun Microsystems, Inc.
 */

package org.netbeans.test.subversion.main.diff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Test;
import org.netbeans.jellytools.JellyTestCase;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.jellytools.EditorOperator;
import org.netbeans.jellytools.NbDialogOperator;
import org.netbeans.jellytools.NewProjectWizardOperator;
import org.netbeans.jellytools.ProjectsTabOperator;
import org.netbeans.jellytools.nodes.Node;
import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JRadioButtonOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.Operator;
import org.netbeans.test.subversion.operators.SourcePackagesNode;
import org.netbeans.test.subversion.operators.CheckoutWizardOperator;
import org.netbeans.test.subversion.operators.RepositoryStepOperator;
import org.netbeans.test.subversion.operators.VersioningOperator;
import org.netbeans.test.subversion.operators.WorkDirStepOperator;
import org.netbeans.test.subversion.utils.MessageHandler;
import org.netbeans.test.subversion.utils.RepositoryMaintenance;
import org.netbeans.test.subversion.utils.TestKit;

/**
 *
 * @author pvcs
 */
public class ExportDiffPatchTest extends JellyTestCase {
    
    public static final String TMP_PATH = "/tmp";
    public static final String REPO_PATH = "repo";
    public static final String WORK_PATH = "work";
    public static final String PROJECT_NAME = "JavaApp";
    public File projectPath;
    public PrintStream stream;
    Operator.DefaultStringComparator comOperator;
    Operator.DefaultStringComparator oldOperator;
    static Logger log;
    
    /** Creates a new instance of ExportDiffPatchTest */
    public ExportDiffPatchTest(String name) {
        super(name);
    }
    
    @Override
    protected void setUp() throws Exception {        
        System.out.println("### "+getName()+" ###");
        if (log == null) {
            log = Logger.getLogger(TestKit.LOGGER_NAME);
            log.setLevel(Level.ALL);
            TestKit.removeHandlers(log);
        } else {
            TestKit.removeHandlers(log);
        }
    }
    
    public static Test suite() {
         return NbModuleSuite.create(
                 NbModuleSuite.createConfiguration(ExportDiffPatchTest.class).addTest(
                    "invokeExportDiffPatch"
                 )
                 .enableModules(".*")
                 .clusters(".*")
        );
     }
    
    public void invokeExportDiffPatch() throws Exception {
        try {
            MessageHandler mh = new MessageHandler("Checking out");
            log.addHandler(mh);

            TestKit.closeProject(PROJECT_NAME);
            if (TestKit.getOsName().indexOf("Mac") > -1)
                new NewProjectWizardOperator().invoke().close();
            
            TestKit.TIME_OUT = 25;
            stream = new PrintStream(new File(getWorkDir(), getName() + ".log"));
            //VersioningOperator vo = VersioningOperator.invoke();
            TestKit.showStatusLabels();
            CheckoutWizardOperator.invoke();
            RepositoryStepOperator rso = new RepositoryStepOperator();
            
            //create repository...
            File work = new File(TMP_PATH + File.separator + WORK_PATH + File.separator + "w" + System.currentTimeMillis());
            new File(TMP_PATH).mkdirs();
            work.mkdirs();
            RepositoryMaintenance.deleteFolder(new File(TMP_PATH + File.separator + REPO_PATH));
            RepositoryMaintenance.createRepository(TMP_PATH + File.separator + REPO_PATH);
            RepositoryMaintenance.loadRepositoryFromFile(TMP_PATH + File.separator + REPO_PATH, getDataDir().getCanonicalPath() + File.separator + "repo_dump");
            rso.setRepositoryURL(RepositoryStepOperator.ITEM_FILE + RepositoryMaintenance.changeFileSeparator(TMP_PATH + File.separator + REPO_PATH, false));
            
            rso.next();
            WorkDirStepOperator wdso = new WorkDirStepOperator();
            wdso.setRepositoryFolder("trunk/" + PROJECT_NAME);
            wdso.setLocalFolder(work.getCanonicalPath());
            wdso.checkCheckoutContentOnly(false);
            wdso.finish();
            //open project

            TestKit.waitText(mh);

            NbDialogOperator nbdialog = new NbDialogOperator("Checkout Completed");
            JButtonOperator open = new JButtonOperator(nbdialog, "Open Project");
            open.push();
            TestKit.waitForScanFinishedSimple();
            
            //modify, save file and invoke Diff

            Node node = new Node(new SourcePackagesNode(PROJECT_NAME), "javaapp|Main.java");
            node.performPopupAction("Open");
            EditorOperator eo = new EditorOperator("Main.java");
            eo.deleteLine(2);
            eo.insert(" insert", 5, 1);
            eo.insert("\tSystem.out.println(\"\");\n", 19, 1);
            eo.save();

            mh = new MessageHandler("Refreshing");
            TestKit.removeHandlers(log);
            log.addHandler(mh);

            node.performPopupAction("Subversion|Show Changes");
            TestKit.waitText(mh);
            Thread.sleep(1000);
            VersioningOperator vo = VersioningOperator.invoke();
            vo = VersioningOperator.invoke();
            new EventTool().waitNoEvent(2000);
            //Save action should change the file annotations
            org.openide.nodes.Node nodeIDE = (org.openide.nodes.Node) node.getOpenideNode();
            String color = TestKit.getColor(nodeIDE.getHtmlDisplayName());
            String status = TestKit.getStatus(nodeIDE.getHtmlDisplayName());
            assertEquals("Wrong color of node - file color should be new!!!", TestKit.MODIFIED_COLOR, color);
            assertEquals("Wrong annotation of node - file status should be new!!!", TestKit.MODIFIED_STATUS, status);
            assertEquals("Wrong number of records in Versioning view!!!", 1, vo.tabFiles().getRowCount());
            
            node = new Node(new ProjectsTabOperator().tree(), PROJECT_NAME);
            //comOperator = new Operator.DefaultStringComparator(true, true);
            //oldOperator = (DefaultStringComparator) Operator.getDefaultStringComparator();
            //Operator.setDefaultStringComparator(comOperator);
            new EventTool().waitNoEvent(3000);

            mh = new MessageHandler("Exporting");
            TestKit.removeHandlers(log);
            log.addHandler(mh);
            node.performMenuActionNoBlock("Team|Patches|Export Uncommitted Changes...");
            //Operator.setDefaultStringComparator(oldOperator);
            
            nbdialog = new NbDialogOperator("Export Diff Patch");
            JButtonOperator btn = new JButtonOperator(nbdialog, "OK");


            JRadioButtonOperator rbtno = new JRadioButtonOperator(nbdialog, "Save as File");
            rbtno.push();

            JTextFieldOperator tf = new JTextFieldOperator(nbdialog, 2);

            String patchFile = "/tmp/patch" + System.currentTimeMillis() + ".patch";
            File file = new File(patchFile);
            tf.setText(file.getCanonicalFile().toString()); 

            btn.push();

            TestKit.waitText(mh);
            new EventTool().waitNoEvent(3000);


            System.out.println("After refresh");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            boolean generated = false;
            if (line != null) {
                generated = line.indexOf("# This patch file was generated by NetBeans IDE") != -1 ? true : false;
            }
            
            br.close();
            assertTrue("Diff Patch file is empty!", generated);
            System.setProperty("netbeans.t9y.cvs.connection.CVSROOT", "");
            TestKit.TIME_OUT = 15;
            stream.flush();
            stream.close();
        } catch (Exception e) {
            throw new Exception("Test failed: " + e);
        } finally {
            TestKit.closeProject(PROJECT_NAME);
        }    
    }
    
}

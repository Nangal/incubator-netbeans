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
 * Portions Copyrighted 2007 Sun Microsystems, Inc.
 */

package org.netbeans.nbbuild;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.imageio.ImageIO;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

/**
 *
 * @author Jaroslav Tulach
 */
public class PrintIcon extends Task {
    private FileSet first;
    private FileSet second;
    
    
    
    
    /** Creates a new instance of PrintIcon */
    public PrintIcon() {
    }

    private File duplicates;
    public void setDuplicates(File f) {
        duplicates = f;
    }

    private File difference;
    public void setDifference(File f) {
        difference = f;
    }
    
    /**
     * 
     * @return 
     */
    public FileSet createFirstPool() {
        if (first != null) {
            throw new BuildException();
        }
        first = new FileSet();
        return first;
    }
    
    public FileSet createSecondPool() {
        if (second != null) {
            throw new BuildException();
        }
        second = new FileSet();
        return second;
    }
    
    @Override
    public void execute() throws BuildException {
        if (first == null) {
            throw new BuildException("You need to specify firstpool element for this task!"); // NOI18N
        }
        
        try {
            
            SortedSet<IconInfo> firstSet = new TreeSet<IconInfo>();
            for (String f : first.getDirectoryScanner(getProject()).getIncludedFiles()) {
                File baseDir = first.getDir(getProject());
                File file = new File(baseDir, f);
                firstSet.add(new IconInfo(file.toURI().toURL(), getProject()));
            }

            SortedSet<IconInfo> sndSet = new TreeSet<IconInfo>();
            if (second != null) {
                for (String f : second.getDirectoryScanner(getProject()).getIncludedFiles()) {
                    File baseDir = second.getDir(getProject());
                    File file = new File(baseDir, f);
                    sndSet.add(new IconInfo(file.toURI().toURL(), getProject()));
                }
            }
            
            if (duplicates != null) {
                Set<IconInfo> both = new TreeSet<IconInfo>(firstSet);
                both.addAll(sndSet);
                
                BufferedWriter os = new BufferedWriter(new FileWriter(duplicates));
                IconInfo prev = null;
                boolean prevPrinted = false;
                for (IconInfo info : both) {
                    IconInfo p = prev;
                    prev = info;
                    if (p == null || p.hash != info.hash) {
                        prevPrinted = false;
                        continue;
                    }
                    
                    if (!prevPrinted) {
                        os.write(p.toString());
                        os.newLine();
                        prevPrinted = true;
                    }
                    
                    os.write(info.toString());
                    os.newLine();
                }
                os.close();
            }
            if (difference != null) {
                SortedSet<IconInfo> union = new TreeSet<IconInfo>(firstSet);
                union.addAll(sndSet);
                
                BufferedWriter os = new BufferedWriter(new FileWriter(difference));
                for (IconInfo info : union) {
                    if (!contains(firstSet, info.hash)) {
                        os.write('+');
                        os.write(info.toString());
                        os.newLine();
                        continue;
                    }
                    if (!contains(sndSet, info.hash)) {
                        os.write('-');
                        os.write(info.toString());
                        os.newLine();
                        continue;
                    }
                }
                os.close();
            }
            
        } catch (IOException ex) {
            throw new BuildException(ex);
        }
        
    }
    
    private static boolean contains(SortedSet<IconInfo> set, int hashCode) {
        IconInfo fake = new IconInfo("", "", hashCode);
        Set<IconInfo> greaterOrEqual = set.tailSet(fake);
        if (greaterOrEqual.isEmpty()) {
            return false;
        }
        IconInfo first = greaterOrEqual.iterator().next();
        return hashCode == first.hash;
    }

    static final int hash(Throwable t) {
        String msg = t.getMessage();
        if (msg != null) {
            return 7 + msg.hashCode();
        }
        return 5 + t.getClass().hashCode();
    }
    
    private static final class IconInfo implements Comparable<IconInfo> {
        final String name;
        final String path;
        final int hash;
        
        public IconInfo(URL from, Project p) throws IOException {
            this.path = from.toExternalForm();
            int last = this.path.lastIndexOf('/');
            assert last >= 0;
            this.name = this.path.substring(last + 1);
            
            p.log("Parsing " + from, Project.MSG_VERBOSE);
            BufferedImage image;
            int _hash;
            try {
                InputStream is = from.openStream();
                image = ImageIO.read(is);
                is.close();
                int w = image.getWidth();
                int h = image.getHeight();
                _hash = w * 3 + h * 7;
                
                for (int i = 0; i < w; i++) {
                    for (int j = 0; j < h; j++) {
                        int rgb = image.getRGB(i, j);
                        _hash += (rgb >> 2);
                    }
                }
            } catch (IOException e) {
                p.log("Broken icon at " + from, Project.MSG_WARN);
                _hash = hash(e);
            } catch (IndexOutOfBoundsException ex) {
                p.log("Broken icon at " + from, Project.MSG_WARN);
                _hash = hash(ex);
            }
            
            this.hash = _hash;
        }
        
        public IconInfo(String name, String path, int hash) {
            this.name = name;
            this.path = path;
            this.hash = hash;
        }
        
        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final IconInfo other = (IconInfo) obj;

            if (this.path != other.path &&
                (this.path == null || !this.path.equals(other.path)))
                return false;
            if (this.hash != other.hash)
                return false;
            return true;
        }
    
        public int compareTo(IconInfo another) {
            if (hash != another.hash) {
                return hash - another.hash;
            }
            
            return path.compareTo(another.path);
        }
        
        public @Override String toString() {
            String h = Integer.toHexString(hash);
            if (h.length() < 8) {
                h = "00000000".substring(h.length()) + h;
            }
            String n = name;
            if (n.length() < 30) {
                n = n + "                              ".substring(n.length());
            }
            
            return MessageFormat.format("{0} {1} {2}", h, n, path);
        }
    } // end of IconInfo
}

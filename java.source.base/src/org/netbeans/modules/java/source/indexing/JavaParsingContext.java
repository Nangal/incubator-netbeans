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

package org.netbeans.modules.java.source.indexing;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.api.JavacTaskImpl;
import com.sun.tools.javac.api.JavacTrees;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ModuleElement;

import javax.lang.model.element.TypeElement;

import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.editor.mimelookup.MimeLookup;
import org.netbeans.api.editor.mimelookup.MimePath;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.classpath.JavaClassPathConstants;
import org.netbeans.api.java.platform.JavaPlatformManager;
import org.netbeans.api.java.queries.SourceLevelQuery;
import org.netbeans.api.java.source.ClasspathInfo;
import org.netbeans.api.java.source.ElementHandle;
import org.netbeans.api.queries.FileEncodingQuery;
import org.netbeans.modules.java.preprocessorbridge.spi.JavaFileFilterImplementation;
import org.netbeans.modules.java.preprocessorbridge.spi.JavaIndexerPlugin;
import org.netbeans.modules.java.source.JavaFileFilterQuery;
import org.netbeans.modules.java.source.JavaSourceAccessor;
import org.netbeans.modules.java.source.indexing.JavaCustomIndexer.CompileTuple;
import org.netbeans.modules.java.source.parsing.JavacParser;
import org.netbeans.modules.java.source.parsing.ProcessorGenerated;
import org.netbeans.modules.java.source.usages.ClassIndexImpl;
import org.netbeans.modules.java.source.usages.ClassIndexManager;
import org.netbeans.modules.java.source.usages.ClasspathInfoAccessor;
import org.netbeans.modules.java.source.usages.SourceAnalyzerFactory;
import org.netbeans.modules.parsing.spi.indexing.Context;
import org.netbeans.modules.parsing.spi.indexing.Indexable;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;
import org.openide.util.Pair;
import org.openide.util.lookup.Lookups;

//@NotThreadSafe
final class JavaParsingContext {

    private final Context ctx;
    private final boolean rootNotNeeded;    
    private final ClasspathInfo cpInfo;
    private final ClassIndexImpl uq;
    private SourceLevelQuery.Result sourceLevel;
    private boolean sourceLevelInitialized;
    private JavaFileFilterImplementation filter;
    private boolean filterInitialized;
    private Charset encoding;
    private boolean encodingInitialized;    
    private SourceAnalyzerFactory.StorableAnalyzer sa;
    private CheckSums checkSums;
    private FQN2Files fqn2Files;    
    private Iterable<? extends JavaIndexerPlugin> pluginsCache;
    private ProcessorGenerated processorGenerated;

    JavaParsingContext(final Context context, final boolean allowNonExistentRoot) throws IOException {
        ctx = context;
        rootNotNeeded = allowNonExistentRoot && context.getRoot() == null;
        uq = ClassIndexManager.getDefault().createUsagesQuery(context.getRootURI(), true);
        if (!rootNotNeeded) {
            ClassPath bootPath = ClassPath.getClassPath(ctx.getRoot(), ClassPath.BOOT);
            if (bootPath == null) {
                bootPath = JavaPlatformManager.getDefault().getDefaultPlatform().getBootstrapLibraries();
            }
            ClassPath moduleBootPath = ClassPath.getClassPath(ctx.getRoot(), JavaClassPathConstants.MODULE_BOOT_PATH);
            if (moduleBootPath == null) {
                moduleBootPath = JavaPlatformManager.getDefault().getDefaultPlatform().getBootstrapLibraries();
            }
            ClassPath compilePath = ClassPath.getClassPath(ctx.getRoot(), ClassPath.COMPILE);
            if (compilePath == null) {
                compilePath = ClassPath.EMPTY;
            }
            ClassPath moduleCompilePath = ClassPath.getClassPath(ctx.getRoot(), JavaClassPathConstants.MODULE_COMPILE_PATH);
            if (moduleCompilePath == null) {
                moduleCompilePath = ClassPath.EMPTY;
            }
            ClassPath moduleClassPath = ClassPath.getClassPath(ctx.getRoot(), JavaClassPathConstants.MODULE_CLASS_PATH);
            if (moduleClassPath == null) {
                moduleClassPath = ClassPath.EMPTY;
            }
            ClassPath srcPath = ClassPath.getClassPath(ctx.getRoot(), ClassPath.SOURCE);
            if (srcPath == null) {
                srcPath = ClassPath.EMPTY;
            }
            ClassPath moduleSrcPath = ClassPath.getClassPath(ctx.getRoot(), JavaClassPathConstants.MODULE_SOURCE_PATH);
            if (moduleSrcPath == null) {
                moduleSrcPath = ClassPath.EMPTY;
            }
            cpInfo = ClasspathInfoAccessor.getINSTANCE().create(
                bootPath,
                moduleBootPath,
                compilePath,
                moduleCompilePath,
                moduleClassPath,
                srcPath,
                moduleSrcPath,
                null,
                true,
                context.isSourceForBinaryRootIndexing(),
                false,
                context.checkForEditorModifications(),
                null);
        } else {
            cpInfo = null;
        }
    }

    public JavaParsingContext(final Context context, final ClassPath bootPath, final ClassPath moduleBootPath, final ClassPath compilePath, final ClassPath moduleCompilePath, final ClassPath moduleClassPath, final ClassPath sourcePath, final ClassPath moduleSourcePath,
            final Collection<? extends CompileTuple> virtualSources) throws IOException {
        ctx = context;
        rootNotNeeded = false;
        uq = ClassIndexManager.getDefault().createUsagesQuery(context.getRootURI(), true);
        cpInfo = ClasspathInfoAccessor.getINSTANCE().create(bootPath, moduleBootPath, compilePath, moduleCompilePath, moduleClassPath, sourcePath, moduleSourcePath,
                filter, true, context.isSourceForBinaryRootIndexing(),
                !virtualSources.isEmpty(), context.checkForEditorModifications(), null);
        registerVirtualSources(cpInfo, virtualSources);
    }
    
    @CheckForNull
    ClasspathInfo getClasspathInfo() {
        return cpInfo;
    }
    
    @CheckForNull
    ClassIndexImpl getClassIndexImpl() throws IOException {
        return uq;
    }
    
    @CheckForNull
    String getSourceLevel() {
        final SourceLevelQuery.Result sl = initSourceLevel();
        return sl == null ? null : sl.getSourceLevel();
    }

    @CheckForNull
    SourceLevelQuery.Profile getProfile() {
        final SourceLevelQuery.Result sl = initSourceLevel();
        return sl == null ? null : sl.getProfile();
    }
    
    @CheckForNull
    JavaFileFilterImplementation getJavaFileFilter() {
        if (!filterInitialized) {
            filter = rootNotNeeded ? null : JavaFileFilterQuery.getFilter(ctx.getRoot());
            filterInitialized = true;
        }
        return filter;
    }
    
    @CheckForNull
    Charset getEncoding() {
        if (!encodingInitialized) {
            encoding = rootNotNeeded ? null : FileEncodingQuery.getEncoding(ctx.getRoot());
            encodingInitialized = true;
        }
        return encoding;
    }
    
    @CheckForNull
    SourceAnalyzerFactory.StorableAnalyzer getSourceAnalyzer() throws IOException {
        if (sa == null) {
            sa = uq == null ? null : uq.getSourceAnalyser();
        }
        return sa;
    }
    
    @NonNull
    CheckSums getCheckSums() throws IOException {
        if (checkSums == null) {
            try {
                checkSums = CheckSums.forContext(ctx);
            } catch (NoSuchAlgorithmException e) {
                throw new IOException(e);
            }
        }
        return checkSums;
    }
    
    @NonNull
    FQN2Files getFQNs() throws IOException {
        if (fqn2Files == null) {
            fqn2Files = FQN2Files.forRoot(ctx.getRootURI());
        }
        return fqn2Files;
    }
    
    @NonNull
    ProcessorGenerated getProcessorGeneratedFiles() {
        if (processorGenerated == null) {
            processorGenerated = TransactionContext.get().get(ProcessorGenerated.class);
        }
        return processorGenerated;
    }

    @CheckForNull
    String getModuleName() {
        try {
            return JavaIndex.getAttribute(ctx.getRootURI(), JavaIndex.ATTR_MODULE_NAME, null);
        } catch (IOException ioe) {
            return null;
        }
    }

    void analyze(
            @NonNull final Iterable<? extends CompilationUnitTree> trees,
            @NonNull final JavacTaskImpl jt,
            @NonNull final CompileTuple active,
            @NonNull final Set<? super ElementHandle<TypeElement>> newTypes,
            @NonNull final Set<? super ElementHandle<ModuleElement>> newModules,
            @NonNull /*@Out*/ final boolean[] mainMethod) throws IOException {
        final SourceAnalyzerFactory.StorableAnalyzer analyzer = getSourceAnalyzer();
        assert analyzer != null;
        analyzer.analyse(trees, jt, active, newTypes, newModules, mainMethod);
        final Lookup pluginServices = getPluginServices(jt);
        for (CompilationUnitTree cu : trees) {
            for (JavaIndexerPlugin plugin : getPlugins()) {
                plugin.process(cu, active.indexable, pluginServices);
            }
        }
    }

    void delete(
            @NonNull final Indexable indexable,
            @NonNull final List<Pair<String,String>> toDelete) throws IOException {
        for (Pair<String,String> pair : toDelete) {
            final SourceAnalyzerFactory.StorableAnalyzer analyzer = getSourceAnalyzer();
            assert analyzer != null;
            analyzer.delete(pair);
        }
        for (JavaIndexerPlugin plugin : getPlugins()) {
            plugin.delete(indexable);
        }
    }
    
    void store() throws IOException {
        if (checkSums != null) {
            checkSums.store();
        }
        if (fqn2Files != null) {
            fqn2Files.store();
        }
        if (sa != null) {
            try {
                sa.store();
            } catch (IOException ioe) {
                throw new BrokenIndexException(ioe);
            }
        }
    }

    void finish() {
        for (JavaIndexerPlugin plugin : getPlugins()) {
            plugin.finish();
        }
    }

    @NonNull
    private Iterable<? extends JavaIndexerPlugin> getPlugins() {
        if (pluginsCache == null) {
            pluginsCache = createPlugins(ctx.getRootURI(), ctx.getIndexFolder());
        }
        return pluginsCache;
    }

    private static Iterable<? extends JavaIndexerPlugin> createPlugins(
            @NonNull final URL root,
            @NonNull final FileObject cacheFolder) {
        final List<JavaIndexerPlugin> plugins = new ArrayList<JavaIndexerPlugin>();
        for (JavaIndexerPlugin.Factory factory : MimeLookup.getLookup(
            MimePath.parse(JavacParser.MIME_TYPE)).lookupAll(JavaIndexerPlugin.Factory.class)) {
            final JavaIndexerPlugin plugin = factory.create(root, cacheFolder);
            if (plugin != null) {
                plugins.add(plugin);
            }
        }
        return plugins;
    }

    @NonNull
    private Lookup getPluginServices(final JavacTaskImpl jt) {
        return Lookups.fixed(
            jt.getElements(),
            jt.getTypes(),
            JavacTrees.instance(jt.getContext()),
            JavaSourceAccessor.getINSTANCE().createElementUtilities(jt));
    }

    private SourceLevelQuery.Result initSourceLevel() {
        if (!sourceLevelInitialized) {
            sourceLevel = rootNotNeeded ? null : SourceLevelQuery.getSourceLevel2(ctx.getRoot());
            sourceLevelInitialized = true;
        }
        return sourceLevel;
    }

    private static void registerVirtualSources(final ClasspathInfo cpInfo, final Collection<? extends CompileTuple> virtualSources) {
        for (CompileTuple compileTuple : virtualSources) {
            ClasspathInfoAccessor.getINSTANCE().registerVirtualSource(cpInfo, compileTuple.jfo);
        }
    }

    static class BrokenIndexException extends IOException {
        private BrokenIndexException (final IOException cause) {
            super(cause);
        }
    }
}

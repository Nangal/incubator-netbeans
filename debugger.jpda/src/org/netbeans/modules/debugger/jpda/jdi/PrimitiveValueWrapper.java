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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2009 Sun
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

package org.netbeans.modules.debugger.jpda.jdi;

// DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
// Generated by org.netbeans.modules.debugger.jpda.jdi.Generate class located in 'gensrc' folder,
// perform the desired modifications there and re-generate by "ant generate".

/**
 * Wrapper for PrimitiveValue JDI class.
 * Use methods of this class instead of direct calls on JDI objects.
 * These methods assure that exceptions thrown from JDI calls are handled appropriately.
 *
 * @author Martin Entlicher
 */
public final class PrimitiveValueWrapper {

    private PrimitiveValueWrapper() {}

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static boolean booleanValue0(com.sun.jdi.PrimitiveValue a) {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.PrimitiveValue",
                    "booleanValue",
                    "JDI CALL: com.sun.jdi.PrimitiveValue({0}).booleanValue()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            boolean ret;
            ret = a.booleanValue();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            return false;
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            return false;
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.PrimitiveValue",
                        "booleanValue",
                        retValue);
            }
        }
    }

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static boolean booleanValue(com.sun.jdi.PrimitiveValue a) throws org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper, org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.PrimitiveValue",
                    "booleanValue",
                    "JDI CALL: com.sun.jdi.PrimitiveValue({0}).booleanValue()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            boolean ret;
            ret = a.booleanValue();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            throw new org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper(ex);
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            throw new org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper(ex);
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.PrimitiveValue",
                        "booleanValue",
                        retValue);
            }
        }
    }

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static byte byteValue(com.sun.jdi.PrimitiveValue a) throws org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper, org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.PrimitiveValue",
                    "byteValue",
                    "JDI CALL: com.sun.jdi.PrimitiveValue({0}).byteValue()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            byte ret;
            ret = a.byteValue();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            throw new org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper(ex);
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            throw new org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper(ex);
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.PrimitiveValue",
                        "byteValue",
                        retValue);
            }
        }
    }

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static char charValue(com.sun.jdi.PrimitiveValue a) throws org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper, org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.PrimitiveValue",
                    "charValue",
                    "JDI CALL: com.sun.jdi.PrimitiveValue({0}).charValue()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            char ret;
            ret = a.charValue();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            throw new org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper(ex);
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            throw new org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper(ex);
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.PrimitiveValue",
                        "charValue",
                        retValue);
            }
        }
    }

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static double doubleValue(com.sun.jdi.PrimitiveValue a) throws org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper, org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.PrimitiveValue",
                    "doubleValue",
                    "JDI CALL: com.sun.jdi.PrimitiveValue({0}).doubleValue()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            double ret;
            ret = a.doubleValue();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            throw new org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper(ex);
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            throw new org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper(ex);
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.PrimitiveValue",
                        "doubleValue",
                        retValue);
            }
        }
    }

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static float floatValue(com.sun.jdi.PrimitiveValue a) throws org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper, org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.PrimitiveValue",
                    "floatValue",
                    "JDI CALL: com.sun.jdi.PrimitiveValue({0}).floatValue()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            float ret;
            ret = a.floatValue();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            throw new org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper(ex);
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            throw new org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper(ex);
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.PrimitiveValue",
                        "floatValue",
                        retValue);
            }
        }
    }

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static int intValue0(com.sun.jdi.PrimitiveValue a) {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.PrimitiveValue",
                    "intValue",
                    "JDI CALL: com.sun.jdi.PrimitiveValue({0}).intValue()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            int ret;
            ret = a.intValue();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            return 0;
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            return 0;
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.PrimitiveValue",
                        "intValue",
                        retValue);
            }
        }
    }

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static int intValue(com.sun.jdi.PrimitiveValue a) throws org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper, org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.PrimitiveValue",
                    "intValue",
                    "JDI CALL: com.sun.jdi.PrimitiveValue({0}).intValue()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            int ret;
            ret = a.intValue();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            throw new org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper(ex);
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            throw new org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper(ex);
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.PrimitiveValue",
                        "intValue",
                        retValue);
            }
        }
    }

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static long longValue(com.sun.jdi.PrimitiveValue a) throws org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper, org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.PrimitiveValue",
                    "longValue",
                    "JDI CALL: com.sun.jdi.PrimitiveValue({0}).longValue()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            long ret;
            ret = a.longValue();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            throw new org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper(ex);
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            throw new org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper(ex);
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.PrimitiveValue",
                        "longValue",
                        retValue);
            }
        }
    }

    // DO NOT MODIFY THIS CODE, GENERATED AUTOMATICALLY
    public static short shortValue(com.sun.jdi.PrimitiveValue a) throws org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper, org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper {
        if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallStart(
                    "com.sun.jdi.PrimitiveValue",
                    "shortValue",
                    "JDI CALL: com.sun.jdi.PrimitiveValue({0}).shortValue()",
                    new Object[] {a});
        }
        Object retValue = null;
        try {
            short ret;
            ret = a.shortValue();
            retValue = ret;
            return ret;
        } catch (com.sun.jdi.InternalException ex) {
            retValue = ex;
            org.netbeans.modules.debugger.jpda.JDIExceptionReporter.report(ex);
            throw new org.netbeans.modules.debugger.jpda.jdi.InternalExceptionWrapper(ex);
        } catch (com.sun.jdi.VMDisconnectedException ex) {
            retValue = ex;
            if (a instanceof com.sun.jdi.Mirror) {
                com.sun.jdi.VirtualMachine vm = ((com.sun.jdi.Mirror) a).virtualMachine();
                try {
                    vm.dispose();
                } catch (com.sun.jdi.VMDisconnectedException vmdex) {}
            }
            throw new org.netbeans.modules.debugger.jpda.jdi.VMDisconnectedExceptionWrapper(ex);
        } catch (Error err) {
            retValue = err;
            throw err;
        } catch (RuntimeException rex) {
            retValue = rex;
            throw rex;
        } finally {
            if (org.netbeans.modules.debugger.jpda.JDIExceptionReporter.isLoggable()) {
                org.netbeans.modules.debugger.jpda.JDIExceptionReporter.logCallEnd(
                        "com.sun.jdi.PrimitiveValue",
                        "shortValue",
                        retValue);
            }
        }
    }

}

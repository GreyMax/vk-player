package com.javafx.main;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander
 * Date: 30.10.12
 * Time: 13:13
 * To change this template use File | Settings | File Templates.
 */

import sun.misc.BASE64Decoder;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class Main
{
    private static boolean verbose = false;
    private static final String fxApplicationClassName = "javafx.application.Application";
    private static final String fxLaunchClassName = "com.sun.javafx.application.LauncherImpl";
    private static final String manifestAppClass = "JavaFX-Application-Class";
    private static final String manifestPreloaderClass = "JavaFX-Preloader-Class";
    private static final String manifestFallbackClass = "JavaFX-Fallback-Class";
    private static final String manifestClassPath = "JavaFX-Class-Path";
    private static final String manifestUpdateHook = "X-JavaFX-Update-Hook";
    private static final String JAVAFX_FAMILY_VERSION = "2.";
    private static final String JAVAFX_REQUIRED_VERSION = "2.1.0";
    private static final String ZERO_VERSION = "0.0.0";
    private static Attributes attrs = null;

    private static URL fileToURL(File file) throws IOException {
        return file.getCanonicalFile().toURI().toURL();
    }

    private static Method findLaunchMethod(File jfxRtPath, String fxClassPath) {
        Class[] argTypes = { Class.class, Class.class, new String[0].getClass() };
        try
        {
            ArrayList urlList = new ArrayList();

            String cp = System.getProperty("java.class.path");
            if (cp != null) {
                while (cp.length() > 0) {
                    int pathSepIdx = cp.indexOf(File.pathSeparatorChar);
                    if (pathSepIdx < 0) {
                        String pathElem = cp;
                        urlList.add(fileToURL(new File(pathElem)));
                        break;
                    }if (pathSepIdx > 0) {
                        String pathElem = cp.substring(0, pathSepIdx);
                        urlList.add(fileToURL(new File(pathElem)));
                    }
                    cp = cp.substring(pathSepIdx + 1);
                }

            }

            cp = fxClassPath;
            if (cp != null)
            {
                File baseDir = null;
                try {
                    String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();

                    String decodedPath = URLDecoder.decode(path, "UTF-8");
                    baseDir = new File(decodedPath).getParentFile();
                    if (!baseDir.exists())
                        baseDir = null;
                } catch (Exception e) {
                }
                while (cp.length() > 0) {
                    int pathSepIdx = cp.indexOf(" ");
                    if (pathSepIdx < 0) {
                        String pathElem = cp;
                        File f = baseDir == null ? new File(pathElem) : new File(baseDir, pathElem);

                        urlList.add(fileToURL(f));
                        break;
                    }if (pathSepIdx > 0) {
                        String pathElem = cp.substring(0, pathSepIdx);
                        File f = baseDir == null ? new File(pathElem) : new File(baseDir, pathElem);

                        urlList.add(fileToURL(f));
                    }
                    cp = cp.substring(pathSepIdx + 1);
                }

            }

            if (jfxRtPath != null) {
                File jfxRtLibPath = new File(jfxRtPath, "lib");
                urlList.add(fileToURL(new File(jfxRtLibPath, "jfxrt.jar")));
                urlList.add(fileToURL(new File(jfxRtLibPath, "deploy.jar")));
                urlList.add(fileToURL(new File(jfxRtLibPath, "plugin.jar")));
                urlList.add(fileToURL(new File(jfxRtLibPath, "javaws.jar")));
            }

            URL[] urls = (URL[])urlList.toArray(new URL[0]);
            if (verbose) {
                System.err.println("===== URL list");
                for (int i = 0; i < urls.length; i++) {
                    System.err.println("" + urls[i]);
                }
                System.err.println("=====");
            }

            ClassLoader urlClassLoader = new URLClassLoader(urls, null);
            Class launchClass = Class.forName("com.sun.javafx.application.LauncherImpl", true, urlClassLoader);

            Method m = launchClass.getMethod("launchApplication", argTypes);
            if (m != null) {
                Thread.currentThread().setContextClassLoader(urlClassLoader);
                return m;
            }
        } catch (Exception ex) {
            if (jfxRtPath != null) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    private static Method findLaunchMethodInClasspath(String fxClassPath) {
        return findLaunchMethod(null, fxClassPath);
    }

    private static Method findLaunchMethodInJar(String jfxRtPathName, String fxClassPath) {
        File jfxRtPath = new File(jfxRtPathName);

        File jfxRtLibPath = new File(jfxRtPath, "lib");
        File jfxRtJar = new File(jfxRtLibPath, "jfxrt.jar");
        if (!jfxRtJar.canRead()) {
            if (verbose) {
                System.err.println("Unable to read " + jfxRtJar.toString() + ". Path = " + jfxRtLibPath.getAbsolutePath());
            }
            return null;
        }

        return findLaunchMethod(jfxRtPath, fxClassPath);
    }

    private static int[] convertVersionStringtoArray(String version)
    {
        int[] v = new int[3];
        if (version == null) return null;

        String[] s = version.split("\\.");
        if (s.length == 3) {
            v[0] = Integer.parseInt(s[0]);
            v[1] = Integer.parseInt(s[1]);
            v[2] = Integer.parseInt(s[2]);
            return v;
        }

        return null;
    }

    private static int compareVersionArray(int[] a1, int[] a2)
    {
        boolean isValid1 = (a1 != null) && (a1.length == 3);
        boolean isValid2 = (a2 != null) && (a2.length == 3);

        if ((!isValid1) && (!isValid2)) {
            return 0;
        }

        if (!isValid2) {
            return -1;
        }

        if (!isValid1) {
            return 1;
        }

        for (int i = 0; i < a1.length; i++) {
            if (a2[i] > a1[i]) return 1;
            if (a2[i] < a1[i]) return -1;
        }

        return 0;
    }

    private static String lookupRegistry()
    {
        if (!System.getProperty("os.name").startsWith("Win")) {
            return null;
        }

        String javaHome = System.getProperty("java.home");
        if (verbose) {
            System.err.println("java.home = " + javaHome);
        }
        if ((javaHome == null) || (javaHome.equals(""))) {
            return null;
        }

        try
        {
            File jreLibPath = new File(javaHome, "lib");
            File deployJar = new File(jreLibPath, "deploy.jar");

            URL[] urls = { fileToURL(deployJar) };
            if (verbose) {
                System.err.println(">>>> URL to deploy.jar = " + urls[0]);
            }

            ClassLoader deployClassLoader = new URLClassLoader(urls, null);
            try
            {
                String configClassName = "com.sun.deploy.config.Config";
                Class configClass = Class.forName(configClassName, true, deployClassLoader);

                Method m = configClass.getMethod("getInstance", null);
                Object config = m.invoke(null, null);
                m = configClass.getMethod("loadDeployNativeLib", null);
                m.invoke(config, null);
            }
            catch (Exception ex)
            {
            }
            String winRegistryWrapperClassName = "com.sun.deploy.association.utility.WinRegistryWrapper";

            Class winRegistryWrapperClass = Class.forName(winRegistryWrapperClassName, true, deployClassLoader);

            Method mGetSubKeys = winRegistryWrapperClass.getMethod("WinRegGetSubKeys", new Class[] { Integer.TYPE, String.class, Integer.TYPE });

            Field HKEY_LOCAL_MACHINE_Field2 = winRegistryWrapperClass.getField("HKEY_LOCAL_MACHINE");

            int HKEY_LOCAL_MACHINE2 = HKEY_LOCAL_MACHINE_Field2.getInt(null);
            String registryKey = "Software\\Oracle\\JavaFX\\";

            String[] fxVersions = (String[])mGetSubKeys.invoke(null, new Object[] { new Integer(HKEY_LOCAL_MACHINE2), "Software\\Oracle\\JavaFX\\", new Integer(255) });

            if (fxVersions == null)
            {
                return null;
            }
            String version = "0.0.0";

            for (int i = 0; i < fxVersions.length; i++)
            {
                if ((fxVersions[i].startsWith("2.")) && (fxVersions[i].compareTo("2.1.0") >= 0))
                {
                    int[] v1Array = convertVersionStringtoArray(version);
                    int[] v2Array = convertVersionStringtoArray(fxVersions[i]);
                    if (compareVersionArray(v1Array, v2Array) > 0) {
                        version = fxVersions[i];
                    }
                }
                else if (verbose) {
                    System.err.println("  Skip version " + fxVersions[i] + " (required=" + "2.1.0" + ")");
                }

            }

            if (version.equals("0.0.0"))
            {
                return null;
            }

            String winRegistryClassName = "com.sun.deploy.util.WinRegistry";
            Class winRegistryClass = Class.forName(winRegistryClassName, true, deployClassLoader);

            Method mGet = winRegistryClass.getMethod("getString", new Class[] { Integer.TYPE, String.class, String.class });

            Field HKEY_LOCAL_MACHINE_Field = winRegistryClass.getField("HKEY_LOCAL_MACHINE");
            int HKEY_LOCAL_MACHINE = HKEY_LOCAL_MACHINE_Field.getInt(null);
            String path = (String)mGet.invoke(null, new Object[] { new Integer(HKEY_LOCAL_MACHINE), "Software\\Oracle\\JavaFX\\" + version, "Path" });

            if (verbose) {
                System.err.println("FOUND KEY: Software\\Oracle\\JavaFX\\" + version + " = " + path);
            }
            return path;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private static Attributes getJarAttributes() throws Exception {
        String theClassFile = "Main.class";
        Class theClass = Main.class;
        String classUrlString = theClass.getResource(theClassFile).toString();
        if ((!classUrlString.startsWith("jar:file:")) || (classUrlString.indexOf("!") == -1)) {
            return null; }
        String urlString = classUrlString.substring(4, classUrlString.lastIndexOf("!"));
        File jarFile = new File(new URI(urlString).getPath());
        String jarName = jarFile.getCanonicalPath();

        JarFile jf = null;
        Attributes attr;
        try { jf = new JarFile(jarName);
            Manifest mf = jf.getManifest();
            attr = mf.getMainAttributes();
        } finally {
            if (jf != null)
                try {
                    jf.close();
                }
                catch (Exception ex)
                {
                }
        }
        return attr;
    }

    private static String decodeBase64(String inp) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodedBytes = decoder.decodeBuffer(inp);
        return new String(decodedBytes);
    }

    private static String[] getAppArguments(Attributes attrs) {
        List args = new LinkedList();
        try
        {
            int idx = 1;
            String argNamePrefix = "JavaFX-Argument-";
            while (attrs.getValue(argNamePrefix + idx) != null) {
                args.add(decodeBase64(attrs.getValue(argNamePrefix + idx)));
                idx++;
            }

            String paramNamePrefix = "JavaFX-Parameter-Name-";
            String paramValuePrefix = "JavaFX-Parameter-Value-";
            idx = 1;
            while (attrs.getValue(paramNamePrefix + idx) != null) {
                String k = decodeBase64(attrs.getValue(paramNamePrefix + idx));
                String v = null;
                if (attrs.getValue(paramValuePrefix + idx) != null) {
                    v = decodeBase64(attrs.getValue(paramValuePrefix + idx));
                }
                args.add("--" + k + "=" + (v != null ? v : ""));
                idx++;
            }
        } catch (IOException ioe) {
            System.err.println("Failed to extract application parameters");
            ioe.printStackTrace();
        }

        return (String[])args.toArray(new String[0]);
    }

    private static String getAppName(Attributes attrs, boolean preloader)
    {
        String propName = preloader ? "javafx.preloader.class" : "javafx.application.class";

        String className = System.getProperty(propName);
        if ((className != null) && (className.length() != 0)) {
            return className;
        }

        if (attrs == null) {
            return "TEST";
        }

        if (preloader) {
            String appName = attrs.getValue("JavaFX-Preloader-Class");
            if ((appName == null) || (appName.length() == 0)) {
                if (verbose) {
                    System.err.println("Unable to find preloader class name");
                }
                return null;
            }
            return appName;
        }
        String appName = attrs.getValue("JavaFX-Application-Class");
        if ((appName == null) || (appName.length() == 0)) {
            System.err.println("Unable to find application class name");
            return null;
        }
        return appName;
    }

    private static Class getAppClass(String appName)
    {
        try
        {
            if (verbose) {
                System.err.println("Try calling Class.forName(" + appName + ") using classLoader = " + Thread.currentThread().getContextClassLoader());
            }

            Class appClass = Class.forName(appName, false, Thread.currentThread().getContextClassLoader());

            if (verbose) {
                System.err.println("found class: " + appClass);
            }
            return appClass;
        } catch (NoClassDefFoundError ncdfe) {
            ncdfe.printStackTrace();
            errorExit("Unable to find class: " + appName);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            errorExit("Unable to find class: " + appName);
        }

        return null;
    }

    private static void tryToSetProxy()
    {
        try {
            if ((System.getProperty("http.proxyHost") != null) || (System.getProperty("https.proxyHost") != null) || (System.getProperty("ftp.proxyHost") != null))
            {
                if (verbose) {
                    System.out.println("Explicit proxy settings detected. Skip autoconfig.");
                    System.out.println("  http.proxyHost=" + System.getProperty("http.proxyHost"));
                    System.out.println("  https.proxyHost=" + System.getProperty("https.proxyHost"));
                    System.out.println("  ftp.proxyHost=" + System.getProperty("ftp.proxyHost"));
                }
                return;
            }
            if (System.getProperty("javafx.autoproxy.disable") != null) {
                if (verbose) {
                    System.out.println("Disable autoproxy on request.");
                }
                return;
            }

            Class sm = Class.forName("com.sun.deploy.services.ServiceManager", true, Thread.currentThread().getContextClassLoader());

            Class[] params = { Integer.TYPE };
            Method setservice = sm.getDeclaredMethod("setService", params);
            String osname = System.getProperty("os.name");

            String servicename = null;
            if (osname.startsWith("Win")) {
                servicename = "STANDALONE_TIGER_WIN32";
            }
            else if (osname.contains("Mac"))
                servicename = "STANDALONE_TIGER_MACOSX";
            else {
                servicename = "STANDALONE_TIGER_UNIX";
            }
            Object[] values = new Object[1];
            Class pt = Class.forName("com.sun.deploy.services.PlatformType", true, Thread.currentThread().getContextClassLoader());

            values[0] = pt.getField(servicename).get(null);
            setservice.invoke(null, values);

            Class dps = Class.forName("com.sun.deploy.net.proxy.DeployProxySelector", true, Thread.currentThread().getContextClassLoader());

            Method m = dps.getDeclaredMethod("reset", new Class[0]);
            m.invoke(null, new Object[0]);
            if (verbose)
                System.out.println("Autoconfig of proxy is completed.");
        }
        catch (Exception e) {
            if (verbose)
                System.out.println("Failed to autoconfig proxy due to " + e);
        }
    }

    private static void processUpdateHook(String updateHookName)
    {
        if (updateHookName == null) return;

        try
        {
            if (verbose) {
                System.err.println("Try calling Class.forName(" + updateHookName + ") using classLoader = " + Thread.currentThread().getContextClassLoader());
            }

            Class hookClass = Class.forName(updateHookName, false, Thread.currentThread().getContextClassLoader());

            if (verbose) {
                System.err.println("found class: " + hookClass.getCanonicalName());
            }

            Method mainMethod = hookClass.getMethod("main", new Class[] { new String[0].getClass() });

            String[] args = null;
            mainMethod.invoke(null, new Object[] { args });
        }
        catch (Exception ex) {
            if (verbose) {
                System.err.println("Failed to run update hook: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private static void launchApp(Method launchMethod, String appName, String preloaderName, String updateHookName, String[] args)
    {
        Class preloaderClass = null;
        if (preloaderName != null) {
            preloaderClass = getAppClass(preloaderName);
        }
        Class appClass = getAppClass(appName);
        Class fxApplicationClass = null;
        try {
            fxApplicationClass = Class.forName("javafx.application.Application", true, Thread.currentThread().getContextClassLoader());
        }
        catch (NoClassDefFoundError ex) {
            errorExit("Cannot find javafx.application.Application");
        } catch (ClassNotFoundException ex) {
            errorExit("Cannot find javafx.application.Application");
        }

        if (fxApplicationClass.isAssignableFrom(appClass))
            try {
                if (verbose) {
                    System.err.println("launchApp: Try calling " + launchMethod.getDeclaringClass().getName() + "." + launchMethod.getName());
                }

                tryToSetProxy();
                processUpdateHook(updateHookName);
                launchMethod.invoke(null, new Object[] { appClass, preloaderClass, args });
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
                errorExit("Exception while running Application");
            } catch (Exception ex) {
                ex.printStackTrace();
                errorExit("Unable to invoke launch method");
            }
        else
            try {
                if (verbose) {
                    System.err.println("Try calling " + appClass.getName() + ".main(String[])");
                }

                Method mainMethod = appClass.getMethod("main", new Class[] { new String[0].getClass() });

                mainMethod.invoke(null, new Object[] { args });
            } catch (Exception ex) {
                ex.printStackTrace();
                errorExit("Unable to invoke main method");
            }
    }

    private static void checkJre()
    {
        String javaVersion = System.getProperty("java.version");
        if (verbose) {
            System.err.println("java.version = " + javaVersion);
            System.err.println("java.runtime.version = " + System.getProperty("java.runtime.version"));
        }

        if ((!javaVersion.startsWith("1.6")) && (!javaVersion.startsWith("1.7")) && (!javaVersion.startsWith("1.8")) && (!javaVersion.startsWith("1.9")))
        {
            showFallback(true);
        }
    }

    private static Method findLaunchMethod(String fxClassPath) {
        Method launchMethod = null;

        if (verbose) {
            System.err.println("1) Try existing classpath...");
        }
        launchMethod = findLaunchMethodInClasspath(fxClassPath);
        if (launchMethod != null) return launchMethod;

        if (verbose) {
            System.err.println("2) Try javafx.runtime.path property...");
        }
        String javafxRuntimePath = System.getProperty("javafx.runtime.path");
        if (javafxRuntimePath != null) {
            if (verbose) {
                System.err.println("    javafx.runtime.path = " + javafxRuntimePath);
            }
            launchMethod = findLaunchMethodInJar(javafxRuntimePath, fxClassPath);
        }
        if (launchMethod != null) return launchMethod;

        if (verbose) {
            System.err.println("3) Look for cobundled JavaFX ... [java.home=" + System.getProperty("java.home"));
        }

        launchMethod = findLaunchMethodInJar(System.getProperty("java.home"), fxClassPath);

        if (launchMethod != null) return launchMethod;

        if (verbose) {
            System.err.println("4) Look in the OS platform registry...");
        }
        javafxRuntimePath = lookupRegistry();
        if (javafxRuntimePath != null) {
            if (verbose) {
                System.err.println("    Installed JavaFX runtime found in: " + javafxRuntimePath);
            }

            launchMethod = findLaunchMethodInJar(javafxRuntimePath, fxClassPath);
            if (launchMethod != null) return launchMethod;

        }

        if (verbose) {
            System.err.println("5) Look in hardcoded paths");
        }
        String[] hardCodedPaths = { "../rt", "../../../../rt", "../../sdk/rt", "../../../artifacts/sdk/rt" };

        for (int i = 0; i < hardCodedPaths.length; i++) {
            javafxRuntimePath = hardCodedPaths[i];
            launchMethod = findLaunchMethodInJar(javafxRuntimePath, fxClassPath);
            if (launchMethod != null) return launchMethod;
        }

        return launchMethod;
    }

    public static void main(String[] args)
    {
        verbose = Boolean.getBoolean("javafx.verbose");


        checkJre();
        try
        {
            attrs = getJarAttributes();
        } catch (Exception ex) {
            ex.printStackTrace();
            errorExit("Unable to load jar manifest");
        }

        String appName = getAppName(attrs, false);
        if (verbose) {
            System.err.println("appName = " + appName);
        }
        if (appName == null) {
            errorExit("Unable to find application class name");
        }

        String preloaderName = getAppName(attrs, true);
        if (verbose) {
            System.err.println("preloaderName = " + preloaderName);
        }

        String[] embeddedArgs = getAppArguments(attrs);
        if (verbose) {
            System.err.println("embeddedArgs = " + Arrays.toString(embeddedArgs));
            System.err.println("commandLineArgs = " + Arrays.toString(args));
        }

        String updateHook = attrs.getValue("X-JavaFX-Update-Hook");
        if ((verbose) && (updateHook != null))
            System.err.println("updateHook = " + updateHook);
        String fxClassPath;
        if (attrs != null)
            fxClassPath = attrs.getValue("JavaFX-Class-Path");
        else {
            fxClassPath = "";
        }

        Method launchMethod = findLaunchMethod(fxClassPath);
        if (launchMethod != null) {
            launchApp(launchMethod, appName, preloaderName, updateHook, args.length > 0 ? args : embeddedArgs);

            return;
        }

        showFallback(false);
    }

    private static void showFallback(final boolean jreError) {
        SwingUtilities.invokeLater(new Runnable() {

            private final boolean valjreError = jreError;

            public void run() { JFrame f = new JFrame("JavaFX Launcher");
                f.setDefaultCloseOperation(3);
                JApplet japp = null;

                if (Main.attrs.getValue("JavaFX-Fallback-Class") != null) {
                    Class customFallback = null;
                    try {
                        customFallback = Class.forName(Main.attrs.getValue("JavaFX-Fallback-Class"), false, Thread.currentThread().getContextClassLoader());
                    }
                    catch (ClassNotFoundException ce)
                    {
                        System.err.println("Custom fallback class is not found: " + Main.attrs.getValue("JavaFX-Fallback-Class"));
                    }

                    if ((customFallback != null) && (!NoJavaFXFallback.class.getName().equals(customFallback))) {
                        try
                        {
                            japp = (JApplet)customFallback.newInstance();
                        } catch (Exception e) {
                            System.err.println("Failed to instantiate custom fallback " + customFallback.getName() + " due to " + e);
                        }

                    }

                }

                if (japp == null)
                {
                    japp = new NoJavaFXFallback(this.valjreError, !this.valjreError, "2.1.0");

                    f.getContentPane().add(japp);
                    f.pack();
                    f.setVisible(true);
                } else {
                    japp.init();
                    f.getContentPane().add(japp);
                    japp.start();
                    f.pack();
                    f.setVisible(true);
                } }
        });
    }

    private static void errorExit(final String string)
    {
        try
        {
            Runnable runnable = new Runnable() {
                private final String valstring = string;

                public void run() { try { Class componentClass = Class.forName("java.awt.Component");
                    Class jOptionPaneClass = Class.forName("javax.swing.JOptionPane");
                    Field ERROR_MESSAGE_Field = jOptionPaneClass.getField("ERROR_MESSAGE");
                    int ERROR_MESSAGE = ERROR_MESSAGE_Field.getInt(null);
                    Method showMessageDialogMethod = jOptionPaneClass.getMethod("showMessageDialog", new Class[] { componentClass, Object.class, String.class, Integer.TYPE });

                    showMessageDialogMethod.invoke(null, new Object[] { null, this.valstring, "JavaFX Launcher Error", new Integer(ERROR_MESSAGE) });
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                }
            };
            Class swingUtilsClass = Class.forName("javax.swing.SwingUtilities");
            Method invokeAndWaitMethod = swingUtilsClass.getMethod("invokeAndWait", new Class[] { Runnable.class });

            invokeAndWaitMethod.invoke(null, new Object[] { runnable });
            if (verbose)
                System.err.println("Done with invoke and wait");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        System.exit(1);
    }
}

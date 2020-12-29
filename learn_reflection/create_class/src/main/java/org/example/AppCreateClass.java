// https://stackoverflow.com/questions/2320404/creating-classes-dynamically-with-java

package org.example;

import java.io.*;
import java.util.*;

import com.sun.tools.javac.Main;

import java.net.URL;
import java.net.URLClassLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.PrintWriter;

import org.example.functor.IFunctor;

import java.lang.reflect.*;

public class AppCreateClass {
    static String className;
    static Path sourceFilePath;
    static Path classFilePath;
    static final String packageName = "org.example.functor";
    static final String dynamicGenDir = "./dynamic_gen";

    static void setClassName() {
        // String fileNameSuffix = Long.toString(new Date().getTime());
        String fileNameSuffix = "AA";
        className = "Functor" + fileNameSuffix;
    }

    static void createDirs() throws Exception {
        sourceFilePath = Paths.get(Paths.get(dynamicGenDir, packageName.split("\\.")).toString(), className + ".java");
        classFilePath = Paths.get(Paths.get(dynamicGenDir, packageName.split("\\.")).toString(), className + ".class");

        Files.createDirectories(sourceFilePath.getParent());
        Files.createDirectories(classFilePath.getParent());
    }

    static void createSource() throws Exception {

        try {
            FileWriter fileWriter = new FileWriter(sourceFilePath.toString(), false);
            fileWriter.write("package " + packageName + ";\n");
            fileWriter.write("import org.example.utils.Utils;\n");
            fileWriter.write("public class " + className + " implements IFunctor {\n");
            fileWriter.write("    public " + className + "() {}\n");
            fileWriter.write("    @Override\n");
            fileWriter.write("    public String call(String param1, String param2) {\n");
            fileWriter.write("        return Utils.add(param1, param2);\n");
            fileWriter.write("    }\n");
            fileWriter.write("}\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void compile() throws Exception {
        String[] sources = { new String(sourceFilePath.toString()) };

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        if (Main.compile(sources, new PrintWriter(bs)) != 0) {
            System.out.println("compiler output: \n" + new String(bs.toByteArray()));
            throw new RuntimeException("failed to compile");
        }
        System.out.println("compiler output: \n" + new String(bs.toByteArray()));
    }

    static void call() throws Exception {
        URL url = new File(dynamicGenDir).toURI().toURL();
        URL[] urls = new URL[]{ url };
        System.out.println(url.toString());
        ClassLoader classLoader = new URLClassLoader(urls);
        Class functorClass = classLoader.loadClass(packageName + "." + className);

        IFunctor functor = (IFunctor)functorClass.getConstructor().newInstance(null);
        System.out.println(functor.call("a", "b"));
    }

    public static void main(String[] args) {
        try {
            setClassName();
            createDirs();
            createSource();
            compile();
            call();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

// https://stackoverflow.com/questions/2320404/creating-classes-dynamically-with-java

package org.example;

import java.io.*;
import java.util.*;
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
    static String sourceFilePath;
    static final String packageName = "org.example.functor";
    static final String dynamicGenDir = "./dynamic_gen";

    static void createSource() throws Exception{
        //String fileNameSuffix = Long.toString(new Date().getTime());
        String fileNameSuffix = "AA";
        className = "Functor" + fileNameSuffix;
        String packageDir = dynamicGenDir + "/" + packageName.replaceAll("\\.", "/");
        sourceFilePath = packageDir + "/" + className + ".java";

        Files.createDirectories(Paths.get(dynamicGenDir));

        try {
            FileWriter fileWriter = new FileWriter(sourceFilePath, false);
            fileWriter.write("package " + packageName + ";\n");
            fileWriter.write("import org.example.utils.Utils;\n");
            fileWriter.write("public class "+ className + " implements IFunctor {\n");
            fileWriter.write("    public " + className + "() {}\n");
            fileWriter.write("    @Override\n");
            fileWriter.write("    public String call(String param1, String param2) {\n");
            fileWriter.write("        return Utils.add(param1, param2);\n");
            fileWriter.write("    }\n");
            fileWriter.write("}\n");
            fileWriter.flush();      
            fileWriter.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    static void compile() throws Exception {
        String [] sources = { new String(sourceFilePath) };

        String packageDir = dynamicGenDir + "/" + packageName.replaceAll("\\.", "/");
        Files.createDirectories(Paths.get(packageDir));
        String classFilePath = packageDir + "/" + className + ".class";

        com.sun.tools.javac.Main m = new com.sun.tools.javac.Main();
        if (m.compile(sources, new PrintWriter(classFilePath)) != 0) {
            throw new RuntimeException("failed to compile");
        }
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
            createSource();
            compile();
            call();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

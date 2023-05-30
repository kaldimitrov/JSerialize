package org.toxicsdev.JSerialize.Utils;

import lombok.SneakyThrows;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PackageUtils {
    @SneakyThrows
    public static List<Class<?>> getClassesInPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();

        String packagePath = packageName.replace('.', '/');
        File packageDirectory = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(packagePath)).getFile());

        if (!packageDirectory.exists()) {
            throw new ClassNotFoundException("Package " + packageName + " does not exist.");
        }

        File[] files = packageDirectory.listFiles();
        assert files != null;

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            } else if (file.isDirectory()) {
                String subPackageName = packageName + '.' + file.getName();
                classes.addAll(getClassesInPackage(subPackageName));
            }
        }

        return classes;
    }
}

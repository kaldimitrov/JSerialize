package org.toxicsdev.JSerialize.Utils;

import lombok.SneakyThrows;
import org.toxicsdev.JSerialize.Compressors.Compressor;

import java.util.List;

public class CompressorUtils {
    @SneakyThrows
    public static Compressor getCompressorByName(String name) {
        String packageName = "org.toxicsdev.JSerialize.Compressors";

        List<Class<?>> classes = PackageUtils.getClassesInPackage(packageName);

        for(Class<?> clazz : classes) {
            if(clazz.getSimpleName().equals(name)) {
                return (Compressor) clazz.cast(clazz.newInstance());
            }
        }

        return null;
    }
}

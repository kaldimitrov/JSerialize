package org.toxicsdev.JSerialize.Utils;

import lombok.SneakyThrows;
import org.toxicsdev.JSerialize.Compressors.Compressor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompressorUtils {
    private static final String packageName = "org.toxicsdev.JSerialize.Compressors";

    @SneakyThrows
    public static Compressor getCompressorByName(String name) {
        List<Class<?>> classes = PackageUtils.getClassesInPackage(packageName);

        for (Class<?> clazz : classes) {
            if (clazz.getSimpleName().equals(name + "Compressor")) {
                return (Compressor) clazz.cast(clazz.newInstance());
            }
        }

        return null;
    }

    @SneakyThrows
    public static Map<String, Compressor> getCompressorDictionary() {
        Map<String, Compressor> compressorDictionary = new HashMap<>();
        List<Class<?>> classes = PackageUtils.getClassesInPackage(packageName);

        for (Class<?> clazz : classes) {
            if (clazz.getSimpleName().equals("Compressor")) continue;

            compressorDictionary.put(clazz.getSimpleName(), (Compressor) clazz.getDeclaredConstructor().newInstance());
        }

        return compressorDictionary;
    }

    @SneakyThrows
    public static Compressor getBestCompressor(Object obj) {
        Map<String, Compressor> compressors = CompressorUtils.getCompressorDictionary();

        int minBytes = Integer.MAX_VALUE;
        String minCompressor = "";

        for (Map.Entry<String, Compressor> entry : compressors.entrySet()) {
            Compressor compressor = entry.getValue();
            byte[] bytes = compressor.compress(obj);
            if (bytes.length < minBytes) {
                minBytes = bytes.length;
                minCompressor = entry.getKey();
            }
        }

        return compressors.get(minCompressor);
    }
}

package org.toxicsdev.JSerialize;

import lombok.SneakyThrows;
import org.toxicsdev.JSerialize.Compressors.Compressor;
import org.toxicsdev.JSerialize.Utils.CompressorUtils;
import org.toxicsdev.JSerialize.Deserializers.Deserializer;

import java.util.List;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        Compressor comp = CompressorUtils.getCompressorByName("Test");
        comp.compress();
    }
}
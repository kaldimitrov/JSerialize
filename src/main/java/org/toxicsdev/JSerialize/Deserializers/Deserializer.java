package org.toxicsdev.JSerialize.Deserializers;

import lombok.SneakyThrows;
import org.toxicsdev.JSerialize.Compressors.Compressor;
import org.toxicsdev.JSerialize.Utils.PackageUtils;

import java.util.List;

public abstract class Deserializer {
    Compressor compressor = null;
    boolean compact;
}

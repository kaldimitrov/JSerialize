package org.toxicsdev.JSerialize.Deserializers;

import org.toxicsdev.JSerialize.Compressors.Compressor;

public abstract class Deserializer {
    Compressor compressor = null;
    boolean compact;
}

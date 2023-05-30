package org.toxicsdev.JSerialize.Serializers;

import org.toxicsdev.JSerialize.Compressors.Compressor;

public abstract class Serializer {
    Compressor compressor;
    boolean compact;


    public Serializer() {
        compressor = null;
        compact = false;
    }
}

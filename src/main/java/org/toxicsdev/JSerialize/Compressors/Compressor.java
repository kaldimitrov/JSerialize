package org.toxicsdev.JSerialize.Compressors;

public interface Compressor {
    String getName();

    byte[] compress(Object obj);

    Object decompress(byte[] bytes);
}

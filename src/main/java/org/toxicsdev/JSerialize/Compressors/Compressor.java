package org.toxicsdev.JSerialize.Compressors;

public interface Compressor {
    byte[] compress(Object obj);

    Object decompress(byte[] bytes);
}

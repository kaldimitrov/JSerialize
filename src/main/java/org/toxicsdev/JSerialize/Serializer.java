package org.toxicsdev.JSerialize;

import org.toxicsdev.JSerialize.Compressors.Compressor;
import org.toxicsdev.JSerialize.Utils.CompressorUtils;

import java.io.File;

public class Serializer {
    Compressor compressor;
    boolean compress;
    boolean bestCompression;

    public void setCompressor(Compressor compressor) {
        this.compressor = compressor;
    }

    public void compress(boolean compress) {
        this.compress = compress;
    }

    public void bestCompression(boolean bestCompression) {
        this.bestCompression = bestCompression;
    }

    public void serialize(Object obj, File file) {
        if (obj == null) {
            throw new IllegalArgumentException("Error: Object is null or invalid");
        }

        if (compress && bestCompression) {
            compressor = CompressorUtils.getBestCompressor(obj);
        }

        if (compressor == null) {
            throw new IllegalArgumentException("Error: Compressor is null or invalid");
        }


    }
}

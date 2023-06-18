package org.toxicsdev.JSerialize;

import org.toxicsdev.JSerialize.Compressors.Compressor;
import org.toxicsdev.JSerialize.FileFormats.DatFormat;
import org.toxicsdev.JSerialize.FileFormats.JsonFormat;
import org.toxicsdev.JSerialize.FileFormats.SerializableFormat;
import org.toxicsdev.JSerialize.Utils.CompressorUtils;

public class Serializer {
    private Compressor compressor = null;
    private SerializableFormat serializer = new JsonFormat();
    private boolean compress = false;
    private boolean bestCompression = true;

    public void setCompressor(Compressor compressor) {
        this.compressor = compressor;
    }

    public void enableCompress(boolean compress) {
        this.compress = compress;
    }

    public void enableBestCompression(boolean bestCompression) {
        this.bestCompression = bestCompression;
    }

    public void setSerializer(SerializableFormat ser) {
        serializer = ser;
    }

    public void serialize(Object obj, String fileName) {
        if (obj == null) {
            throw new IllegalArgumentException("Error: Object is null or invalid");
        }

        if (compress) {
            if (bestCompression) {
                compressor = CompressorUtils.getBestCompressor(obj);
            }

            if (compressor == null) {
                throw new IllegalArgumentException("Error: Compressor is null or invalid");
            }

            serializer = new DatFormat();
            ((DatFormat) serializer).setCompressor(compressor);
        }

        serializer.serialize(obj, fileName);
    }
}

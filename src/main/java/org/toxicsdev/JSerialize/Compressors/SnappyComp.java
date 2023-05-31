package org.toxicsdev.JSerialize.Compressors;

import org.xerial.snappy.Snappy;
import org.toxicsdev.JSerialize.Utils.ByteUtils;

public class SnappyComp implements Compressor {

    @Override
    public byte[] compress(Object obj) {
        try {
            byte[] serializedData = ByteUtils.convertToBytes(obj);
            return Snappy.compress(serializedData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object decompress(byte[] bytes) {
        try {
            byte[] decompressedData = Snappy.uncompress(bytes);
            return ByteUtils.convertToObject(decompressedData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

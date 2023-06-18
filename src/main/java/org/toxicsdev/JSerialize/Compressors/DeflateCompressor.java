package org.toxicsdev.JSerialize.Compressors;

import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorOutputStream;
import org.toxicsdev.JSerialize.Utils.ByteUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class DeflateCompressor implements Compressor {
    public String getName() {
        return "Deflate";
    }

    @Override
    public byte[] compress(Object obj) {
        byte[] serializedBytes = ByteUtils.convertToBytes(obj);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DeflateCompressorOutputStream compressorOutputStream = new DeflateCompressorOutputStream(outputStream);

            compressorOutputStream.write(serializedBytes);
            compressorOutputStream.close();

            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object decompress(byte[] bytes) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            DeflateCompressorInputStream compressorInputStream = new DeflateCompressorInputStream(inputStream);

            byte[] compressedBytes = compressorInputStream.readAllBytes();
            compressorInputStream.close();

            return ByteUtils.convertToObject(compressedBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

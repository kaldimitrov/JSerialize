package org.toxicsdev.JSerialize.Compressors;

import org.apache.commons.compress.compressors.lzma.LZMACompressorInputStream;
import org.apache.commons.compress.compressors.lzma.LZMACompressorOutputStream;
import org.toxicsdev.JSerialize.Utils.ByteUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LZMACompressor implements Compressor {
    public LZMACompressor() {
    }

    @Override
    public byte[] compress(Object obj) {
        byte[] serializedBytes = ByteUtils.convertToBytes(obj);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            LZMACompressorOutputStream compressorOutputStream = new LZMACompressorOutputStream(outputStream);

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
            LZMACompressorInputStream compressorInputStream = new LZMACompressorInputStream(inputStream);

            byte[] compressedBytes = compressorInputStream.readAllBytes();
            compressorInputStream.close();

            return ByteUtils.convertToObject(compressedBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

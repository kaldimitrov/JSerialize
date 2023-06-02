package org.toxicsdev.JSerialize.Compressors;

import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorInputStream;
import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorOutputStream;
import org.toxicsdev.JSerialize.Utils.ByteUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LZ4Compressor implements Compressor {
    public LZ4Compressor() {
    }

    @Override
    public byte[] compress(Object obj) {
        byte[] serializedBytes = ByteUtils.convertToBytes(obj);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BlockLZ4CompressorOutputStream compressorOutputStream = new BlockLZ4CompressorOutputStream(outputStream);

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
            BlockLZ4CompressorInputStream compressorInputStream = new BlockLZ4CompressorInputStream(inputStream);

            byte[] compressedBytes = compressorInputStream.readAllBytes();
            compressorInputStream.close();

            return ByteUtils.convertToObject(compressedBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package org.toxicsdev.JSerialize.Compressors;

import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.toxicsdev.JSerialize.Utils.ByteUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class XZCompressor implements Compressor {

    public String getName() {
        return "XZ";
    }

    @Override
    public byte[] compress(Object obj) {
        byte[] serializedBytes = ByteUtils.convertToBytes(obj);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            XZCompressorOutputStream compressorOutputStream = new XZCompressorOutputStream(outputStream);

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
            XZCompressorInputStream compressorInputStream = new XZCompressorInputStream(inputStream);

            byte[] compressedBytes = compressorInputStream.readAllBytes();
            compressorInputStream.close();

            return ByteUtils.convertToObject(compressedBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
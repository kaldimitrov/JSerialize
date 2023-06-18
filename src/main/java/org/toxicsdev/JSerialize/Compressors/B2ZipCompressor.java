package org.toxicsdev.JSerialize.Compressors;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.toxicsdev.JSerialize.Utils.ByteUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class B2ZipCompressor implements Compressor {

    public String getName() {
        return "B2Zip";
    }

    @Override
    public byte[] compress(Object obj) {
        byte[] serializedBytes = ByteUtils.convertToBytes(obj);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BZip2CompressorOutputStream compressorOutputStream = new BZip2CompressorOutputStream(outputStream);

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
            BZip2CompressorInputStream compressorInputStream = new BZip2CompressorInputStream(inputStream);

            byte[] compressedBytes = compressorInputStream.readAllBytes();
            compressorInputStream.close();

            return ByteUtils.convertToObject(compressedBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package org.toxicsdev.JSerialize.FileFormats;

import lombok.SneakyThrows;
import org.toxicsdev.JSerialize.Compressors.Compressor;
import org.toxicsdev.JSerialize.Utils.CompressorUtils;

import java.io.*;
import java.util.*;

public class DatFormat implements SerializableFormat {
    private static final int COMPRESSOR_NAME_LENGTH = 7;
    private static final char END_OBJECT_CHAR = '\\';

    private Compressor compressor;

    @SneakyThrows
    @Override
    public void serialize(Object object, String fileName) {
        byte[] compressedBytes = compressor.compress(object);
        String compressorName = compressor.getName();

        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw")) {
            raf.writeBytes(formatCompressorName(compressorName));
            raf.write(compressedBytes);
            raf.writeChar(END_OBJECT_CHAR);
        }
    }

    @SneakyThrows
    @Override
    public List<Object> deserialize(String fileName) {
        List<Object> objects = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                objects.add(deserializeObject(raf));
            }
        }
        return objects;
    }

    @SneakyThrows
    @Override
    public Object deserialize(String fileName, int index) {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
            for (int i = 0; i < index; i++) {
                deserializeObject(raf);
            }

            return deserializeObject(raf);
        }
    }

    @SneakyThrows
    private Object deserializeObject(RandomAccessFile raf) {
        byte[] compressorNameBytes = new byte[COMPRESSOR_NAME_LENGTH];
        raf.readFully(compressorNameBytes);
        String compressorName = new String(compressorNameBytes).trim();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte b;
        while (raf.getFilePointer() < raf.length() && (b = raf.readByte()) != END_OBJECT_CHAR) {
            baos.write(b);
        }
        byte[] objectBytes = baos.toByteArray();

        compressor = CompressorUtils.getCompressorByName(compressorName);

        if (compressor == null) {
            throw new IllegalArgumentException("Invalid compressor name: " + compressorName);
        }

        return compressor.decompress(objectBytes);
    }

    private String formatCompressorName(String name) {
        return String.format("%1$-" + COMPRESSOR_NAME_LENGTH + "s", name);
    }

    public void setCompressor(Compressor compressor) {
        this.compressor = compressor;
    }
}
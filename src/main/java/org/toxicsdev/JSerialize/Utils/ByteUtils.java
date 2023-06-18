package org.toxicsdev.JSerialize.Utils;

import lombok.SneakyThrows;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class ByteUtils {
    @SneakyThrows
    public static byte[] convertToBytes(Object object) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(object);
        objectStream.flush();
        objectStream.close();

        return byteStream.toByteArray();
    }

    public static Object convertToObject(byte[] bytes) {
        Object object = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;

        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            object = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return object;
    }

    public static String getSHA256(String filePath) {
        String sha256 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            byte[] hash = md.digest(bytes);
            sha256 = bytesToHex(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sha256;
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
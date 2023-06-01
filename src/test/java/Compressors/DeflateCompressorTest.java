package Compressors;

import TestObjects.Person;
import org.junit.jupiter.api.*;
import org.toxicsdev.JSerialize.Compressors.DeflateCompressor;

import static org.junit.jupiter.api.Assertions.*;

class DeflateCompressorTest {

    @Test
    void compressObject() {
        Person p = new Person();
        DeflateCompressor compressor = new DeflateCompressor();

        byte[] bytes = compressor.compress(p);
        assertEquals(116, bytes.length);
    }

    @Test
    void decompressObject() {
        Person per = new Person();
        DeflateCompressor compressor = new DeflateCompressor();

        byte[] bytes = compressor.compress(per);

        Person decompressed = (Person) compressor.decompress(bytes);
        assertAll(() -> assertEquals(decompressed.hashCode(), per.hashCode()),
                () -> assertEquals(per, decompressed),
                () -> assertEquals(per.getName(), decompressed.getName()),
                () -> assertEquals(per.getGender(), decompressed.getGender()),
                () -> assertEquals(per.getAge(), decompressed.getAge()));
    }
}
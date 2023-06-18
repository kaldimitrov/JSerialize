package Compressors;

import TestObjects.Person;
import org.junit.jupiter.api.Test;
import org.toxicsdev.JSerialize.Compressors.XZCompressor;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class XZCompressorTest {

    @Test
    void compressObject() {
        Person p = new Person();
        XZCompressor compressor = new XZCompressor();

        byte[] bytes = compressor.compress(p);
        assertEquals(308, bytes.length);
    }

    @Test
    void decompressObject() {
        Person per = new Person();
        XZCompressor compressor = new XZCompressor();

        byte[] bytes = compressor.compress(per);

        Person decompressed = (Person) compressor.decompress(bytes);
        assertAll(() -> assertEquals(decompressed.hashCode(), per.hashCode()),
                () -> assertEquals(per, decompressed),
                () -> assertEquals(per.getName(), decompressed.getName()),
                () -> assertEquals(per.getGender(), decompressed.getGender()),
                () -> assertEquals(per.getAge(), decompressed.getAge()));
    }
}
package Compressors;

import TestObjects.Person;
import org.junit.jupiter.api.Test;
import org.toxicsdev.JSerialize.Compressors.B2ZipCompressor;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class B2ZipCompressorTest {

    @Test
    void compressObject() {
        Person p = new Person();
        B2ZipCompressor compressor = new B2ZipCompressor();

        byte[] bytes = compressor.compress(p);
        assertEquals(309, bytes.length);
    }

    @Test
    void decompressObject() {
        Person per = new Person();
        B2ZipCompressor compressor = new B2ZipCompressor();

        byte[] bytes = compressor.compress(per);

        Person decompressed = (Person) compressor.decompress(bytes);
        assertAll(() -> assertEquals(decompressed.hashCode(), per.hashCode()),
                () -> assertEquals(per, decompressed),
                () -> assertEquals(per.getName(), decompressed.getName()),
                () -> assertEquals(per.getGender(), decompressed.getGender()),
                () -> assertEquals(per.getAge(), decompressed.getAge()));
    }
}

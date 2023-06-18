package FileFormats;

import TestObjects.Person;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.toxicsdev.JSerialize.Deserializer;
import org.toxicsdev.JSerialize.FileFormats.JsonFormat;
import org.toxicsdev.JSerialize.Serializer;
import org.toxicsdev.JSerialize.Utils.ByteUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonFormatTest {

    Person p = new Person();
    Person p1 = new Person("Name", 19, "Female");

    @SneakyThrows
    @Test
    void serializeObject() {
        Serializer serializer = new Serializer();

        String fileName = "serializeObject.json";
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        serializer.serialize(p, fileName);
        String sha256 = ByteUtils.getSHA256(fileName);

        assertEquals("2a4dc5c7aec487048bc61d4130897a75b7ce41048d1853b4f80d57b55133f9d3", sha256);
    }

    @SneakyThrows
    @Test
    void serializeObjects() {
        Serializer serializer = new Serializer();

        String fileName = "serializeObjects.json";
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        serializer.serialize(p, fileName);
        serializer.serialize(p1, fileName);
        String sha256 = ByteUtils.getSHA256(fileName);

        assertEquals("f955b2e99025e8d8bbb7f189cce0143d70f445c1700593d264b0e7bbda5a3584", sha256);
    }

    @SneakyThrows
    @Test
    void serializeComplexObjects() {
        Serializer serializer = new Serializer();

        String fileName = "serializeComplexObjects.json";
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        serializer.serialize(p, fileName);

        p1.addToList(p);
        p1.addToList(new Person("ListPerson", 1, "List"));

        p1.addToMap(p);
        p1.addToMap(new Person("MapPerson", 2, "Map"));

        serializer.serialize(p1, fileName);
        String sha256 = ByteUtils.getSHA256(fileName);

        assertEquals("23d3382f7d63ebf23f5cef540c425811386cd70ee2a6989eaebf6d97b7c7799f", sha256);
    }

    @SneakyThrows
    @Test
    void deserializeObject() {
        Serializer serializer = new Serializer();

        String fileName = "deserializeObject.json";
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        serializer.serialize(p, fileName);

        Deserializer deserializer = new Deserializer();
        deserializer.setDeserializer(new JsonFormat());

        List<Object> people = deserializer.deserialize(fileName);
        Person deserialized = (Person) people.get(0);

        assertAll(() -> assertEquals(p.hashCode(), deserialized.hashCode()),
                () -> assertEquals(p, deserialized),
                () -> assertEquals(p.getName(), deserialized.getName()),
                () -> assertEquals(p.getGender(), deserialized.getGender()),
                () -> assertEquals(p.getAge(), deserialized.getAge()));
    }

    @SneakyThrows
    @Test
    void deserializeObjects() {
        Serializer serializer = new Serializer();

        String fileName = "deserializeObjects.json";
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        serializer.serialize(p, fileName);
        serializer.serialize(p1, fileName);

        Deserializer deserializer = new Deserializer();
        deserializer.setDeserializer(new JsonFormat());

        List<Object> people = deserializer.deserialize(fileName);
        Person deserialized = (Person) people.get(0);
        Person deserialized1 = (Person) people.get(1);

        assertAll(() -> assertEquals(p.hashCode(), deserialized.hashCode()),
                () -> assertEquals(p, deserialized),
                () -> assertEquals(p.getName(), deserialized.getName()),
                () -> assertEquals(p.getGender(), deserialized.getGender()),
                () -> assertEquals(p.getAge(), deserialized.getAge()),
                () -> assertEquals(p1.hashCode(), deserialized1.hashCode()),
                () -> assertEquals(p1, deserialized1),
                () -> assertEquals(p1.getName(), deserialized1.getName()),
                () -> assertEquals(p1.getGender(), deserialized1.getGender()),
                () -> assertEquals(p1.getAge(), deserialized1.getAge()));
    }

    @SneakyThrows
    @Test
    void deserializeComplexObjects() {
        Serializer serializer = new Serializer();

        String fileName = "deserializeComplexObjects.json";
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        serializer.serialize(p, fileName);

        p1.addToList(p);
        p1.addToList(new Person("ListPerson", 1, "List"));

        p1.addToMap(p);
        p1.addToMap(new Person("MapPerson", 2, "Map"));

        serializer.serialize(p1, fileName);

        Deserializer deserializer = new Deserializer();
        deserializer.setDeserializer(new JsonFormat());

        List<Object> people = deserializer.deserialize(fileName);
        Person deserialized = (Person) people.get(0);
        Person deserialized1 = (Person) people.get(1);

        assertAll(() -> assertEquals(p.hashCode(), deserialized.hashCode()),
                () -> assertEquals(p, deserialized),
                () -> assertEquals(p.getName(), deserialized.getName()),
                () -> assertEquals(p.getGender(), deserialized.getGender()),
                () -> assertEquals(p.getAge(), deserialized.getAge()),
                () -> assertEquals(p1.getName(), deserialized1.getName()),
                () -> assertEquals(p1.getGender(), deserialized1.getGender()),
                () -> assertEquals(p1.getAge(), deserialized1.getAge()));
    }
}

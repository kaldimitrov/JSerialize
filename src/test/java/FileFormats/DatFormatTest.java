package FileFormats;

import TestObjects.Person;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.toxicsdev.JSerialize.Deserializer;
import org.toxicsdev.JSerialize.FileFormats.DatFormat;
import org.toxicsdev.JSerialize.Serializer;
import org.toxicsdev.JSerialize.Utils.ByteUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatFormatTest {

    Person p = new Person();
    Person p1 = new Person("Name", 19, "Female");

    @SneakyThrows
    @Test
    void serializeObject() {
        Serializer serializer = new Serializer();
        serializer.enableCompress(true);

        String fileName = "serializeObject.dat";
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        serializer.serialize(p, fileName);
        String sha256 = ByteUtils.getSHA256(fileName);

        assertEquals("5705803ac55ea53938600aa430e730773e93afceced8d9f6f0871c55d2f888d8", sha256);
    }

    @SneakyThrows
    @Test
    void serializeObjects() {
        Serializer serializer = new Serializer();
        serializer.enableCompress(true);

        String fileName = "serializeObjects.dat";
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        serializer.serialize(p, fileName);
        serializer.serialize(p1, fileName);
        String sha256 = ByteUtils.getSHA256(fileName);

        assertEquals("3a7bff14cba32c07101c9365b8fc3da811ec962542c8ec2bc603571b56c6bf11", sha256);
    }

    @SneakyThrows
    @Test
    void serializeComplexObjects() {
        Serializer serializer = new Serializer();
        serializer.enableCompress(true);

        String fileName = "serializeComplexObjects.dat";
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

        assertEquals("0e3ecce4b77fc57aa8c0d61b567aa580afe97ba620c97d5df63a684c4ad74542", sha256);
    }

    @SneakyThrows
    @Test
    void deserializeObject() {
        Serializer serializer = new Serializer();
        serializer.enableCompress(true);

        String fileName = "deserializeObject.dat";
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        serializer.serialize(p, fileName);

        Deserializer deserializer = new Deserializer();
        deserializer.setDeserializer(new DatFormat());

        List<Object> people = deserializer.deserialize(fileName);
        Person deserialized = (Person) people.get(0);

        assertAll(() -> assertEquals(p.hashCode(), deserialized.hashCode()),
                () -> assertEquals(p, deserialized),
                () -> assertEquals(p.getName(), deserialized.getName()),
                () -> assertEquals(p.getGender(), deserialized.getGender()),
                () -> assertEquals(p.getAge(), deserialized.getAge()));
    }
}

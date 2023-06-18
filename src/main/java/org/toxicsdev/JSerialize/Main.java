package org.toxicsdev.JSerialize;

import lombok.SneakyThrows;
import org.toxicsdev.JSerialize.FileFormats.DatFormat;
import org.toxicsdev.JSerialize.FileFormats.JsonFormat;
import org.toxicsdev.JSerialize.FileFormats.YamlFormat;
import org.toxicsdev.JSerialize.Utils.CompressorUtils;

import java.util.List;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        Person p = new Person();
        p.addFriend(new Person());
        p.addFriend(new Person());
        p.addFriend(new Person());
        p.addFriend(new Person());

        Serializer serializer = new Serializer();
        serializer.enableBestCompression(true);
        serializer.enableCompress(true);

        serializer.serialize(p, "test1.dat");

        Deserializer deserializer = new Deserializer();
        deserializer.setDeserializer(new DatFormat());
        System.out.println(deserializer.deserialize("test1.dat"));
        Person p1 = (Person) deserializer.deserialize("test1.dat", 0);
        System.out.println(p1);

        serializer.enableCompress(false);

        serializer.setSerializer(new JsonFormat());
        serializer.serialize(p, "test.json");
        serializer.serialize(p, "test.json");
        serializer.serialize(p, "test.json");
        serializer.serialize(p, "test.json");
        serializer.serialize(p, "test.json");
    }
}
package org.toxicsdev.JSerialize;

import lombok.SneakyThrows;
import org.toxicsdev.JSerialize.FileFormats.JsonFormat;
import org.toxicsdev.JSerialize.FileFormats.YamlFormat;
import org.toxicsdev.JSerialize.Utils.CompressorUtils;

import java.util.List;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        Person p = new Person();
        System.out.println(CompressorUtils.getBestCompressor(p));

        JsonFormat json = new JsonFormat();
        json.serialize(p, "test.json");
        json.serialize(p, "test.json");
        json.serialize(p, "test.json");
        json.serialize(p, "test.json");
        json.serialize(p, "test.json");

        List<Object> objects = json.deserialize("test.json");
        Person p1 = (Person) objects.get(0);
        p1.printFriends();

        YamlFormat yaml = new YamlFormat();
        yaml.serialize(p, "test.yml");
        yaml.serialize(p, "test.yml");
        yaml.serialize(p, "test.yml");
        yaml.serialize(p, "test.yml");
        yaml.serialize(p, "test.yml");

        List<Object> objects1 = yaml.deserialize("test.yml");
        Person p2 = (Person) objects1.get(0);
        p2.printFriends();

    }
}
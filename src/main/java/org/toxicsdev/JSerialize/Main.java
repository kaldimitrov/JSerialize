package org.toxicsdev.JSerialize;

import lombok.SneakyThrows;
import org.toxicsdev.JSerialize.FileFormats.Json;
import org.toxicsdev.JSerialize.Utils.CompressorUtils;

import java.util.List;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        Person p = new Person();
        System.out.println(CompressorUtils.getBestCompressor(p));

        Json json = new Json();
        json.serialize(p, "test.json");
        json.serialize(p, "test.json");
        json.serialize(p, "test.json");
        json.serialize(p, "test.json");
        json.serialize(p, "test.json");


        List<Object> objects = json.deserialize("test.json");
        Person p1 = (Person) objects.get(0);
    }
}
package org.toxicsdev.JSerialize.FileFormats;

import lombok.SneakyThrows;

import java.util.List;

public interface SerializableFormat {

    @SneakyThrows
    void serialize(Object object, String fileName);

    @SneakyThrows
    List<Object> deserialize(String fileName);

    @SneakyThrows
    Object deserialize(String fileName, int index);
}

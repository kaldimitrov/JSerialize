package org.toxicsdev.JSerialize.FileFormats;

import lombok.SneakyThrows;

public interface SerializableFormat {

    @SneakyThrows
    void serialize(Object object, String fileName);

    @SneakyThrows
    Object deserialize(String fileName);
}

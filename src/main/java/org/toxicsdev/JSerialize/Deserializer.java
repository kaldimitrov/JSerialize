package org.toxicsdev.JSerialize;

import lombok.SneakyThrows;
import org.toxicsdev.JSerialize.FileFormats.JsonFormat;
import org.toxicsdev.JSerialize.FileFormats.SerializableFormat;

import java.util.List;

public class Deserializer {
    private SerializableFormat deserializer = new JsonFormat();

    public void setDeserializer(SerializableFormat deser) {
        deserializer = deser;
    }

    @SneakyThrows
    public List<Object> deserialize(String fileName) {
        try {
            return deserializer.deserialize(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @SneakyThrows
    public Object deserialize(String fileName, int index) {
        try {
            return deserializer.deserialize(fileName, index);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

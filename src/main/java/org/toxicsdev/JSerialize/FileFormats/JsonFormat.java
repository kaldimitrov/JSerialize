package org.toxicsdev.JSerialize.FileFormats;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JsonFormat implements SerializableFormat {

    @SneakyThrows
    public void serialize(Object object, String fileName) {
        File file = new File(fileName);
        JSONObject mainObject;

        if (file.exists() && file.length() != 0) {
            try (FileReader reader = new FileReader(fileName)) {
                mainObject = new JSONObject(new JSONTokener(reader));
            }
        } else {
            mainObject = new JSONObject();
        }

        JSONObject jsonObject = new JSONObject();
        Class<?> objClass = object.getClass();
        jsonObject.put("class", objClass.getName());
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().isArray()) {
                List<Object> list = new ArrayList<>();
                int length = Array.getLength(field.get(object));
                for (int i = 0; i < length; i++) {
                    list.add(Array.get(field.get(object), i));
                }
                jsonObject.put(field.getName(), new JSONArray(list));
            } else {
                jsonObject.put(field.getName(), field.get(object));
            }
        }

        int index = mainObject.length() + 1;
        mainObject.put(String.valueOf(index), jsonObject);

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(mainObject.toString(4));
        }
    }

    @SneakyThrows
    public List<Object> deserialize(String fileName) {
        List<Object> objects = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            return objects;
        }
        try (FileReader reader = new FileReader(fileName)) {
            JSONObject mainObject = new JSONObject(new JSONTokener(reader));
            for (String key : mainObject.keySet()) {
                JSONObject jsonObject = mainObject.getJSONObject(key);
                Class<?> clazz = Class.forName(jsonObject.getString("class"));
                Object object = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getType().isArray()) {
                        JSONArray jsonArrayField = jsonObject.getJSONArray(field.getName());
                        Object array = Array.newInstance(field.getType().getComponentType(), jsonArrayField.length());
                        for (int i = 0; i < jsonArrayField.length(); i++) {
                            Array.set(array, i, jsonArrayField.get(i));
                        }
                        field.set(object, array);
                    } else {
                        field.set(object, jsonObject.get(field.getName()));
                    }
                }
                objects.add(object);
            }
        }
        return objects;
    }
}

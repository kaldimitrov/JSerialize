package org.toxicsdev.JSerialize.FileFormats;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Json implements SerializableFormat {

    @SneakyThrows
    public void serialize(Object object, String fileName) {
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
                jsonObject.put(field.getName(), list);
            } else {
                jsonObject.put(field.getName(), field.get(object));
            }
        }

        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        long pos = raf.length();
        if (pos == 0) {
            raf.writeBytes("[" + jsonObject.toString() + "]");
        } else {
            pos--;
            raf.seek(pos);
            raf.writeBytes("," + jsonObject.toString() + "]");
        }
        raf.close();
    }


    @SneakyThrows
    public List<Object> deserialize(String fileName) {
        List<Object> objects = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            return objects;
        }
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }

        JSONArray jsonArray = new JSONArray(content.toString());
        for (int j = 0; j < jsonArray.length(); j++) {
            JSONObject jsonObject = jsonArray.getJSONObject(j);
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

        return objects;
    }
}

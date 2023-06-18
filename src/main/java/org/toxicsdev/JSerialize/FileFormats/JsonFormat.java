package org.toxicsdev.JSerialize.FileFormats;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            Object fieldValue = field.get(object);
            if (field.getType().isArray()) {
                List<Object> list = new ArrayList<>();
                int length = Array.getLength(field.get(object));
                for (int i = 0; i < length; i++) {
                    list.add(Array.get(field.get(object), i));
                }
                jsonObject.put(field.getName(), new JSONArray(list));
            } else if (fieldValue instanceof List) {
                JSONArray jsonArray = new JSONArray((List<?>) fieldValue);
                jsonObject.put(field.getName(), jsonArray);
            } else if (fieldValue instanceof Map) {
                JSONObject jsonMap = new JSONObject((Map<?, ?>) fieldValue);
                jsonObject.put(field.getName(), jsonMap);
            } else {
                jsonObject.put(field.getName(), fieldValue);
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
                    if (!jsonObject.has(field.getName())) {
                        field.set(object, null);
                        continue;
                    }
                    if (field.getType().isArray()) {
                        JSONArray jsonArrayField = jsonObject.getJSONArray(field.getName());
                        Object array = Array.newInstance(field.getType().getComponentType(), jsonArrayField.length());
                        for (int i = 0; i < jsonArrayField.length(); i++) {
                            Array.set(array, i, jsonArrayField.get(i));
                        }
                        field.set(object, array);
                    } else if (List.class.isAssignableFrom(field.getType())) {
                        List<Object> list = new ArrayList<>(jsonObject.getJSONArray(field.getName()).toList());
                        field.set(object, list);
                    } else if (Map.class.isAssignableFrom(field.getType())) {
                        Map<String, Object> map = new HashMap<>(jsonObject.getJSONObject(field.getName()).toMap());
                        field.set(object, map);
                    } else {
                        field.set(object, jsonObject.get(field.getName()));
                    }
                }
                objects.add(object);
            }
        }
        return objects;
    }

    @SneakyThrows
    public Object deserialize(String fileName, int index) {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + fileName);
        }
        try (FileReader reader = new FileReader(fileName)) {
            JSONObject mainObject = new JSONObject(new JSONTokener(reader));
            String key = String.valueOf(index);
            if (!mainObject.has(key)) {
                throw new IndexOutOfBoundsException("No object found at index: " + index);
            }
            JSONObject jsonObject = mainObject.getJSONObject(key);
            Class<?> clazz = Class.forName(jsonObject.getString("class"));
            Object object = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (!jsonObject.has(field.getName())) {
                    field.set(object, null);
                    continue;
                }
                if (field.getType().isArray()) {
                    JSONArray jsonArrayField = jsonObject.getJSONArray(field.getName());
                    Object array = Array.newInstance(field.getType().getComponentType(), jsonArrayField.length());
                    for (int i = 0; i < jsonArrayField.length(); i++) {
                        Array.set(array, i, jsonArrayField.get(i));
                    }
                    field.set(object, array);
                } else if (List.class.isAssignableFrom(field.getType())) {
                    List<Object> list = new ArrayList<>(jsonObject.getJSONArray(field.getName()).toList());
                    field.set(object, list);
                } else if (Map.class.isAssignableFrom(field.getType())) {
                    Map<String, Object> map = new HashMap<>(jsonObject.getJSONObject(field.getName()).toMap());
                    field.set(object, map);
                } else {
                    field.set(object, jsonObject.get(field.getName()));
                }
            }
            return object;
        }
    }

}
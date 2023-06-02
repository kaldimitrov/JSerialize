package org.toxicsdev.JSerialize.FileFormats;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Field;

public class Json implements SerializableFormat {

    @Override
    @SneakyThrows
    public void serialize(Object object, String fileName) {
        JSONObject jsonObject = getJsonObject(object);
        appendToFile(jsonObject, fileName);
    }

    @Override
    @SneakyThrows
    public Object deserialize(String fileName) {
        JSONArray jsonArray = readJsonArrayFromFile(fileName);
        if (jsonArray != null && jsonArray.length() > 0) {
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            return getObjectFromJsonObject(jsonObject);
        }
        return null;
    }

    @SneakyThrows
    private JSONObject getJsonObject(Object object) {
        JSONObject jsonObject = new JSONObject();
        String className = object.getClass().getName();
        jsonObject.put("name", className);
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            jsonObject.put(field.getName(), field.get(object));
        }
        return jsonObject;
    }

    @SneakyThrows
    private Object getObjectFromJsonObject(JSONObject jsonObject) {
        String className = jsonObject.optString("name");
        if (className != null && !className.isEmpty()) {
            try {
                Class<?> clazz = Class.forName(className);
                Object instance = clazz.getDeclaredConstructor().newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    if (jsonObject.has(fieldName)) {
                        Object value = jsonObject.get(fieldName);
                        field.set(instance, value);
                    }
                }
                return instance;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @SneakyThrows
    private JSONArray readJsonArrayFromFile(String fileName) {
        JSONArray jsonArray;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            jsonArray = new JSONArray(content.toString());
        }
        return jsonArray;
    }

    private void appendToFile(JSONObject jsonObject, String fileName) throws IOException {
        JSONArray jsonArray = readJsonArrayFromFile(fileName);
        jsonArray.put(jsonObject);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(jsonArray.toString());
        }
    }
}

package org.toxicsdev.JSerialize.FileFormats;

import lombok.SneakyThrows;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class YamlFormat implements SerializableFormat {

    @SneakyThrows
    public void serialize(Object object, String fileName) {
        File file = new File(fileName);

        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.AUTO);
        Yaml yaml = new Yaml(options);
        Map<Integer, Map<String, Object>> yamlObjects;

        if (file.exists() && !isEmpty(file)) {
            InputStream inputStream = new FileInputStream(fileName);
            yamlObjects = yaml.load(inputStream);
        } else {
            yamlObjects = new LinkedHashMap<>();
        }

        Map<String, Object> yamlObject = serializeObject(object);
        int index = yamlObjects.size() + 1;
        yamlObjects.put(index, yamlObject);

        FileWriter writer = new FileWriter(fileName);
        yaml.dump(yamlObjects, writer);
        writer.close();
    }

    @SneakyThrows
    private Map<String, Object> serializeObject(Object object) {
        Map<String, Object> yamlObject = new LinkedHashMap<>();
        Class<?> objClass = object.getClass();
        yamlObject.put("class", objClass.getName());
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().isArray()) {
                List<Object> list = new ArrayList<>();
                int length = Array.getLength(field.get(object));
                for (int i = 0; i < length; i++) {
                    Object arrayItem = Array.get(field.get(object), i);
                    list.add(serializeField(arrayItem));
                }
                yamlObject.put(field.getName(), list);
            } else {
                yamlObject.put(field.getName(), serializeField(field.get(object)));
            }
        }
        return yamlObject;
    }

    private Object serializeField(Object field) {
        if (field instanceof List) {
            List<Object> serializedList = new ArrayList<>();
            for (Object listItem : (List) field) {
                serializedList.add(serializeField(listItem));
            }
            return serializedList;
        } else if (field instanceof Map) {
            Map<Object, Object> serializedMap = new LinkedHashMap<>();
            for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) field).entrySet()) {
                serializedMap.put(serializeField(entry.getKey()), serializeField(entry.getValue()));
            }
            return serializedMap;
        } else if (!field.getClass().isPrimitive() && !(field instanceof String)) {
            return serializeObject(field);
        } else {
            return field;
        }
    }

    private boolean isEmpty(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.readLine() == null;
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
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(fileName);
        Map<Integer, Map<String, Object>> yamlObjects = yaml.load(inputStream);

        for (Map.Entry<Integer, Map<String, Object>> entry : yamlObjects.entrySet()) {
            Map<String, Object> yamlObject = entry.getValue();
            Object object = deserializeObject(yamlObject);
            objects.add(object);
        }

        return objects;
    }

    @Override
    public Object deserialize(String fileName, int index) {
        return null;
    }

    @SneakyThrows
    private Object deserializeObject(Map<String, Object> yamlObject) {
        Class<?> clazz = Class.forName((String) yamlObject.get("class"));
        Object object = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().isArray()) {
                List<?> list = (List<?>) yamlObject.get(field.getName());
                Object array = Array.newInstance(field.getType().getComponentType(), list.size());
                for (int i = 0; i < list.size(); i++) {
                    Array.set(array, i, deserializeField(list.get(i), field.getType().getComponentType()));
                }
                field.set(object, array);
            } else {
                field.set(object, deserializeField(yamlObject.get(field.getName()), field.getType()));
            }
        }
        return object;
    }

    @SneakyThrows
    private Object deserializeField(Object yamlField, Class<?> fieldType) {
        if (List.class.isAssignableFrom(fieldType)) {
            List<Object> deserializedList = new ArrayList<>();
            for (Object listItem : (List) yamlField) {
                deserializedList.add(deserializeField(listItem, listItem.getClass()));
            }
            return deserializedList;
        } else if (Map.class.isAssignableFrom(fieldType)) {
            Map<Object, Object> deserializedMap = new LinkedHashMap<>();
            for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) yamlField).entrySet()) {
                deserializedMap.put(deserializeField(entry.getKey(), entry.getKey().getClass()), deserializeField(entry.getValue(), entry.getValue().getClass()));
            }
            return deserializedMap;
        } else if (!fieldType.isPrimitive() && !(fieldType == String.class)) {
            return deserializeObject((Map<String, Object>) yamlField);
        } else {
            return yamlField;
        }
    }
}
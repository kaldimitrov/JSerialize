package org.toxicsdev.JSerialize;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public class Person implements Serializable {
    private String name;
    private int age;
    private String gender;
    String[] friends = new String[3];
    Map<String, Integer> friendsMap = new HashMap<>();
    Map<Integer, Person> objectsMap = new HashMap<>();


    public Person() {
        name = "Kaloyan";
        age = 18;
        gender = "Male";
        friends[0] = "Test";
        friends[1] = "Test";
        friends[2] = "Test";

        friendsMap.put("John", 1);
        friendsMap.put("Test", 2);
        friendsMap.put("Test2", 3);
        friendsMap.put("Test3", 4);
    }

    public Person(String name, int age, String gender) {
        this.age = age;
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void printFriends() {
        for(int i = 0; i < 3; i++) {
            System.out.println(friends[i]);
        }
    }

    public void addFriend(Person p) {
        objectsMap.put(objectsMap.size(), p);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return getAge() == person.getAge() && getName().equals(person.getName()) && getGender().equals(person.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge(), getGender());
    }
}

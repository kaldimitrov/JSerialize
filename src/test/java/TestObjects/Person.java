package TestObjects;

import java.io.Serializable;
import java.util.*;

public class Person implements Serializable {
    private final Map<Person, Integer> friendsMap = new HashMap<>();
    private final List<Person> friendsList = new ArrayList<>();
    private String name;
    private int age;
    private String gender;


    public Person() {
        name = "Kaloyan";
        age = 18;
        gender = "Male";
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

    public void addToMap(Person p) {
        friendsMap.put(p, friendsMap.size());
    }

    public void addToList(Person p) {
        friendsList.add(p);
    }

    public Map<Person, Integer> getMap() {
        return friendsMap;
    }

    public List<Person> getList() {
        return friendsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return getAge() == person.getAge() && getName().equals(person.getName()) && getGender().equals(person.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge(), getGender(), getMap(), getList());
    }
}

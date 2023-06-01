package TestObjects;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {
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

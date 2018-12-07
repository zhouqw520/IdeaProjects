package com.spring.beans;

import java.util.List;

public class Person {
    private String name;
    private int age;
    private List<Car> car;
    private Dog dog;
    public Person(){}

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

    public List<Car> getCar() {
        return car;
    }

    public void setCar(List<Car> car) {
        this.car = car;
    }

    public Person(String name, int age, List<Car> car, Dog dog) {
        this.name = name;
        this.age = age;
        this.car = car;
        this.dog = dog;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", car=" + car +
                ", dog=" + dog +
                '}';
    }
}

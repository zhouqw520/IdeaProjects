package com.spring.beans;

public class Person {
    private String name;
    private int age;
    private Car car;

    public Person() {
        System.out.println("构造器....");
    }

    public Person(String name, int age, Car car) {
        this.name = name;
        this.age = age;
        this.car = car;
    }
    public void a(){

        System.out.println("初始化方法....");
    }
    public void b(){

        System.out.println("销毁方法....");
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("setName方法...");
        this.name = name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
        System.out.println("setCar方法...");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        System.out.println("setAge方法...");
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", car=" + car +
                '}';
    }
}

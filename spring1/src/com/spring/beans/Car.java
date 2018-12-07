package com.spring.beans;

public class Car {
    private String type;

    public Car() {
        System.out.println("创建一个Car实例");
    }
    public Car(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Car{" +
                "type='" + type + '\'' +
                '}';
    }
}

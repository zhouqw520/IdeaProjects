package com.spring.beans;

public class Car {
    private String type;
    private String factory;
    private double price;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Car(){}
    public Car(String type, String factory, double price) {
        this.type = type;
        this.factory = factory;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "type='" + type + '\'' +
                ", factory='" + factory + '\'' +
                ", price=" + price +
                '}';
    }
}

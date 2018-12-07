package com.spring.beans;

import java.util.List;
import java.util.Map;

public class Cat {
    private List<String> name;
    private Map<String ,Car> carMap;
    public Cat() {
    }

    public Map<String, Car> getCarMap() {
        return carMap;
    }

    public void setCarMap(Map<String, Car> carMap) {
        this.carMap = carMap;
    }

    public Cat(List<String> name, Map<String, Car> carMap) {
        this.name = name;
        this.carMap = carMap;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name=" + name +
                ", carMap=" + carMap +
                '}';
    }
}

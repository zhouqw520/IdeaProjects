package com.spring.beans;

public class Dog {
    private String type;

    //JavaBean IOC容器创建对象时需要无参构造器
    public Dog(){

    }
    public Dog(String type) {
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
        return "Dog{" +
                "type='" + type + '\'' +
                '}';
    }
}

package com.spring.beans;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        Car car = (Car) ac.getBean("car");
        System.out.println(car);
        System.out.println("********************************1**************************");

        Person person1 = (Person) ac.getBean("person1");
        System.out.println(person1);
        System.out.println("********************************2**************************");

        Person person2 = (Person) ac.getBean("person2");
        System.out.println(person2);
        System.out.println("********************************3**************************");

        Person person3 = (Person) ac.getBean("person3");
        System.out.println(person3);
        System.out.println("********************************4**************************");

        Person person4 = (Person) ac.getBean("person4");
        System.out.println(person4);
        System.out.println("********************************5**************************");

        Person person5 = (Person) ac.getBean("person5");
        System.out.println(person5);
        System.out.println("********************************5**************************");

        Car car1 = (Car) ac.getBean("car");
        Car car2 = (Car) ac.getBean("car");
        System.out.println(car1==car2);

        System.out.println("********************************6**************************");
        Car car21 = (Car) ac.getBean("car2");
        Car car22 = (Car) ac.getBean("car2");
        System.out.println(car21==car22);

        System.out.println("********************************6**************************");
        Person person6 = (Person) ac.getBean("person6");
        System.out.println(person6);

        System.out.println("********************************7**************************");
        Person person7 = (Person) ac.getBean("person7");
        System.out.println(person7);

//        System.out.println("********************************8**************************");
//        Person person8 = (Person) ac.getBean("person8");
//        System.out.println(person8);

        System.out.println("********************************9**************************");
        Person person9 = (Person) ac.getBean("person9");
        System.out.println(person9);
        /**
         * IOC管理bean的生命周期：
         * 1、通过构造器或者是工厂方法创建对象
         * 2、需要将属性进行注入
         * 后置处理器的前置处理
         * 3、调用初始化方法
         * 后置处理器的后置处理
         * 4、正常的去使用bean
         * 5、调用销毁方法
         */
    }

}

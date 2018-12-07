package com.spring.beans;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");

        System.out.println("****************************1****************************\n");
        Person person1 = (Person) ac.getBean("person1");
        System.out.println(person1);

        System.out.println("****************************2****************************\n");
        Person person2 = (Person) ac.getBean("person2");
        System.out.println(person2);

        System.out.println("****************************3****************************\n");
        Person person3 = (Person) ac.getBean("person3");
        System.out.println(person3);

        System.out.println("****************************4****************************\n");
        Person person4 = (Person) ac.getBean("person4");
        System.out.println(person4);

        System.out.println("****************************5****************************\n");
        Person person5 = (Person) ac.getBean("person5");
        System.out.println(person5);

        System.out.println("****************************6****************************\n");
        Person person6 = (Person) ac.getBean("person6");
        System.out.println(person6);

        System.out.println("****************************7****************************\n");
        Person person7 = (Person) ac.getBean("person7");
        System.out.println(person7);

        System.out.println("****************************8****************************\n");
        Person person8 = (Person) ac.getBean("person8");
        System.out.println(person8);

        System.out.println("****************************9****************************\n");
        Person person9 = (Person) ac.getBean("person9");
        System.out.println(person9);

        System.out.println("****************************10****************************\n");
        Cat cat1 = (Cat) ac.getBean("cat1");
        System.out.println(cat1);

        System.out.println("****************************11****************************\n");
        Cat cat2 = (Cat) ac.getBean("cat2");
        System.out.println(cat2);

        System.out.println("****************************12****************************\n");
        Person person10 = (Person) ac.getBean("person10");
        System.out.println(person10);

        System.out.println("****************************12****************************\n");
        Person person11 = (Person) ac.getBean("person11");
        System.out.println(person11);
    }
}

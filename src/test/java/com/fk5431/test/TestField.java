package com.fk5431.test;

import java.lang.reflect.Field;

/**
 * Created by FK on 2018/8/29.
 */
public class TestField {
        public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Person p = new Person();
        p.setName("a");
        Person p1 = new Person();
        p1.setName("b");

        Field field = Person.class.getDeclaredField("name");
        System.out.println(field.isAccessible());
//        field.setAccessible(true);
        System.out.println(field.get(p));
        System.out.println(field.get(p1));
    }
}

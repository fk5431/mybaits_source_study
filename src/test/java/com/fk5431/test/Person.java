package com.fk5431.test;

import org.junit.Test;

import java.lang.reflect.Modifier;

/**
 * Created by FK on 2018/8/29.
 */
public class Person {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Test
    public void test(){
        System.out.println((Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)
        & Modifier.PUBLIC);
    }
}

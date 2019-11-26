package com.cmonbaby.eventbus.demo.model;

import android.support.annotation.NonNull;

/**
 * EventBean
 */
public class UserInfo {

    private String name;
    private int age;

    public UserInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    @NonNull
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

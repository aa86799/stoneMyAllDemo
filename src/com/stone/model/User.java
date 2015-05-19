package com.stone.model;

import android.view.View;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/5/5 19 52
 */
public class User {

    private int age;
    private String id;
    private String name;

    public User() {

    }

    public User(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



}

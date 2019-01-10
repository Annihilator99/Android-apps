package com.sri.android.taskapp;



public class Post {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public Post() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public Post(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

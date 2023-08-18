package com.example.sqlite_mvvm.model;

public class UserData {

    private String str_name;
    private String str_age;
    private int id;

    public UserData(String str_name, String str_age, int id) {
        this.str_name = str_name;
        this.str_age = str_age;
        this.id = id;
    }

    public UserData(int id) {

    }

    public String getStr_name() {
        return str_name;
    }

    public void setStr_name(String str_name) {
        this.str_name = str_name;
    }

    public String getStr_age() {
        return str_age;
    }

    public void setStr_age(String str_age) {
        this.str_age = str_age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

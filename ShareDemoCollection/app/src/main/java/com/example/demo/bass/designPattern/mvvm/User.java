package com.example.demo.bass.designPattern.mvvm;

/**
 * Created by bass on 16/4/8.
 */
public class User {
    private String name;
    private String pwd;
    private boolean is;

    public User(String name, String pwd, boolean is) {
        this.name = name;
        this.pwd = pwd;
        this.is = is;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean is() {
        return is;
    }

    public void setIs(boolean is) {
        this.is = is;
    }
}

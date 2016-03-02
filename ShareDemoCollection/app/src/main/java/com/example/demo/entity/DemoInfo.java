package com.example.demo.entity;

/**
 * Created by Eric on 16/2/24.
 */

public class DemoInfo {
    private String title;//标题
    private String author;//作者
    private String date;//日期

    public DemoInfo(String title, String author, String date) {
        this.title = title;
        this.author = author;
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

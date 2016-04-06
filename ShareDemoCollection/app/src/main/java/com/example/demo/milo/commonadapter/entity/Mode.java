package com.example.demo.milo.commonadapter.entity;

/**
 * Created by milo on 16/3/23.
 */
public class Mode {
    private String no;
    private String content;
    private String status;// 0 , 1

    public Mode(String no, String content, String status) {
        this.no = no;
        this.content = content;
        this.status = status;
    }

    public Mode() {
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

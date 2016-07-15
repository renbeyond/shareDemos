package com.example.demo.peng.retrofitwithokhttp.bean;

/**
 * Created by pengwu on 16/7/5.
 */
public class Photo {
    String data;
    String fileType;

    public Photo(String data, String fileType) {
        this.data = data;
        this.fileType = fileType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}

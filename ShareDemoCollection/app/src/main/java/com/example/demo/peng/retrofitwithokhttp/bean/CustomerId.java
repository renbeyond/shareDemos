package com.example.demo.peng.retrofitwithokhttp.bean;

/**
 * Created by pengwu on 16/7/4.
 */
public class CustomerId {
    String code;
    String cType;
    String bindOn;

    public CustomerId(String code, String cType, String bindOn) {
        this.code = code;
        this.cType = cType;
        this.bindOn = bindOn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public String getBindOn() {
        return bindOn;
    }

    public void setBindOn(String bindOn) {
        this.bindOn = bindOn;
    }
}

package com.example.demo.peng.retrofitwithokhttp.http;

/**
 * Created by pengwu on 16/7/5.
 */
public abstract class HttpCallback<T> {

    public void onSuccess(T t){

    }

    public void onFailure(int code){

    }
}

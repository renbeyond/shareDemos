package com.pictureworks.www.retrofitwithokhttp.http;

import com.pictureworks.www.retrofitwithokhttp.bean.BasicResult;

import okhttp3.ResponseBody;

/**
 * Created by pengwu on 16/7/5.
 */
public abstract class HttpCallback<T> {

    public void onSuccess(T t){

    }

    public void onFailure(int code){

    }
}

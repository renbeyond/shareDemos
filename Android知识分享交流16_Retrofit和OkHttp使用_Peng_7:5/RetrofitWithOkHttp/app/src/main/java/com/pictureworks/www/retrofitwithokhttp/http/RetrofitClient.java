package com.pictureworks.www.retrofitwithokhttp.http;

import android.text.TextUtils;

import com.pictureworks.www.retrofitwithokhttp.http.download.FileDownInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * 配置Retrofit
 * Created by pengwu on 16/7/4.
 */
public enum  RetrofitClient {
    INSTANCE;
    private OkHttpClient client;
    private DynamicBaseUrlInterceptor interceptor;
    private Retrofit retrofit;
    private FileDownInterceptor fileDownInterceptor;

    RetrofitClient(){
        interceptor = new DynamicBaseUrlInterceptor();
        fileDownInterceptor = new FileDownInterceptor();
        fileDownInterceptor.setDownLoad(false);
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(fileDownInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)// 连接超时时间设置
                .readTimeout(20, TimeUnit.SECONDS)// 读取超时时间设置
                .retryOnConnectionFailure(true)// 失败重试
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Common.BASE_URL_TEST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
    public Retrofit getRetrofit(){
        return  retrofit;
    }

    public FileDownInterceptor getFileDownInterceptor() {
        return fileDownInterceptor;
    }

    public void setFileDownInterceptor(FileDownInterceptor fileDownInterceptor) {
        this.fileDownInterceptor = fileDownInterceptor;
    }

    public DynamicBaseUrlInterceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(DynamicBaseUrlInterceptor interceptor) {
        this.interceptor = interceptor;
    }
}

package com.example.demo.peng.retrofitwithokhttp.http;


import com.example.demo.peng.retrofitwithokhttp.bean.BasicResult;
import com.example.demo.peng.retrofitwithokhttp.bean.TokenId;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 *
 * API接口，定义请求函数和规则
 *
 * Created by pengwu on 16/7/4.
 */
public interface PhotoPassAuthApi {

    /**
     * 异步Get请求，有请求参数
     * @param path 子路径
     * */
    @GET("auth/{path}")
    Call<ResponseBody> get_no_params(@Path("path") String path);

    /**
     * 异步Get请求，有请求参数
     * @param options 键值对保存请求参数
     * */
    @GET("auth/{path}")
    Call<BasicResult<TokenId>> get_with_param(@Path("path") String path, @QueryMap Map<String, String> options);

    /**
     * 异步Get请求，有请求参数
     * @param path 用@Url标注 与 @GET("auth/{path}")标注有区别
     * 如果使用 @GET("auth/path") 穿进路径 “/”符号会被编码成一个其他字符
     *         @Url 标注则不会，并且 如果传入的是一个完整的路径比如http://192.168.8.3:4000/media/b1b983e5cd2cfafefcb948b6bd9829746a6b4b1f539bd6077b80630377baf922764175e6657819a8758537870f3c263e
     *         则不会和retrofit初始化的baseUrl拼接
     *
     * */
    @GET
    Call<BasicResult<TokenId>> getTokenId(@Url String path, @QueryMap Map<String, String> options);

    /**
     * 异步post请求，有请求参数
     * */
    @POST("auth/{path}")
    Call<ResponseBody> post_no_params(@Path("path") String path);

    /**
     * 异步post请求，有请求参数
     * */
    @FormUrlEncoded
    @POST("auth/{path}")
    Call<ResponseBody> post_one_param(@Path("path") String path, @Field("name") String name);

   /**
    * 异步post请求，有请求参数
    * */
    @FormUrlEncoded
    @POST("auth/{path}")
    Call<BasicResult<TokenId>> login(@Path("path") String path, @FieldMap Map<String, String> options);
    /**
     * 文件下载
     * */
    @GET
    Call<ResponseBody> downLoadImage(@Url String fileUrl, @QueryMap Map<String, String> options);


}

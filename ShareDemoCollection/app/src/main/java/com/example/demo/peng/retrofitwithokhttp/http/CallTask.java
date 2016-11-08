package com.example.demo.peng.retrofitwithokhttp.http;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pengwu on 16/7/5.
 */
public class CallTask<T>{
    private static final String Tag = "CallTask";
    private Call<T> mCall;

    public CallTask(Call call){
        this.mCall = call;
    }

    public void handleResponse(final HttpCallback httpCallback) {
        mCall.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                getAPISuccess(response,httpCallback);
            }

            @Override
            public void onFailure(Call<T> call, Throwable throwable) {
                Log.e(Tag,throwable.toString());
            }
        });
    }

    private static void getAPISuccess(Response response, HttpCallback httpCallback){
        if (response.isSuccessful() && response.errorBody() == null) {
            Log.d("tag", "getAPISuccess --->" + response.body().toString());
            int status = response.code();
            Log.d("tag", "getAPISuccess status --->" + status);
            if (response.code() == 200) {
                httpCallback.onSuccess(response.body());
            } else {
                //失败返回错误码
                switch (response.code()) {
                    case 6035://Current certification has expired, please login again
                    case 6079://Current certification has expired, please login again
                    case 6080://token已经过期
                    case 6074://get token error
                    case 6075://set token error
                    case 6151://query token error
                    case 6153://未授权
                    case 6034://please login
                    case 5030://not login
                    case 5011://not login
                        httpCallback.onFailure(response.code());
                        break;
                    default:
                        httpCallback.onFailure(response.code());
                        break;
                }
            }
        }
    }

    public void Cancle(){
        if (mCall != null){
            mCall.cancel();
        }
    }

}

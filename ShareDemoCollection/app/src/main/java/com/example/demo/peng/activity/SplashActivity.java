package com.example.demo.peng.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.demo.R;
import com.example.demo.peng.retrofitwithokhttp.http.Common;
import com.example.demo.peng.retrofitwithokhttp.http.HttpUtils;
import com.example.demo.peng.retrofitwithokhttp.pictureworks.Installation;
import com.pictureair.jni.keygenerator.PWJniUtil;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pengwu on 16/7/15.
 */
public class SplashActivity extends Activity {
    final MyHandler myHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<SplashActivity> mActivity;

        public MyHandler(SplashActivity activity) {
            this.mActivity = new WeakReference<SplashActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mActivity.get() == null) {
                return;
            }

            mActivity.get().dealHandler(msg);
        }
    }

    private void dealHandler(Message msg){
        switch (msg.what) {
            case HttpUtils.GET_TOKEN_MSG:
                Log.d("tag", "get tokenid success");
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Map<String,String> map = new ConcurrentHashMap<String,String>();
        map.put(Common.TERMINAL,"android");
        map.put(Common.UUID, Installation.id(SplashActivity.this));
        map.put(Common.APP_ID,md5(PWJniUtil.getAPPKey(Common.APP_TYPE_SHDRPP)+PWJniUtil.getAppSecret(Common.APP_TYPE_SHDRPP)));
        HttpUtils.getTokenId(this,map,myHandler);
    }

    public ProgressDialog getProgressDialog(String msg) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(true);
        return progressDialog;
    }

    public static String md5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
//            PictureAirLog.out("NoSuchAlgorithmException caught!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        // 16位加密，从第9位到25位
//		return md5StrBuff.substring(8, 24).toString().toUpperCase();
//        PictureAirLog.out("md5 result------->" + md5StrBuff.toString());
        //32位
        return md5StrBuff.toString();
    }
}

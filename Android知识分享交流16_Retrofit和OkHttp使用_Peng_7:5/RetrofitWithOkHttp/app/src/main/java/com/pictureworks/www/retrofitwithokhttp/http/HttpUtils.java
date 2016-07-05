package com.pictureworks.www.retrofitwithokhttp.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.pictureair.jni.keygenerator.PWJniUtil;
import com.pictureworks.www.retrofitwithokhttp.AESKeyHelper;
import com.pictureworks.www.retrofitwithokhttp.bean.BasicResult;
import com.pictureworks.www.retrofitwithokhttp.bean.Photo;
import com.pictureworks.www.retrofitwithokhttp.bean.TokenId;
import com.pictureworks.www.retrofitwithokhttp.http.download.DownloadProgresshandler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pengwu on 16/6/29.
 */
public class HttpUtils {

    public static final int GET_TOKEN_MSG = 0x0001;
    public static final int LOGIN_SUCCESS = 0x0002;

    public static void getTokenId(final Context context,Map<String,String> map, final Handler handler){

        PhotoPassAuthApi request = ApiFactory.INSTANCE.getPhotoPassAuthApi();
        CallTask task = new CallTask<BasicResult<TokenId>>(request.getTokenId(Common.GET_TOKENID,map));
        task.handleResponse(new HttpCallback<BasicResult<TokenId>>() {
            @Override
            public void onSuccess(BasicResult<TokenId> result) {
                SharedPreferences sp = context.getSharedPreferences(Common.SHARED_PREFERENCE_USERINFO_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.putString(Common.USERINFO_TOKENID, AESKeyHelper.encryptString(result.getResult().getTokenId(), PWJniUtil.getAESKey(Common.APP_TYPE_SHDRPP)));
                e.commit();
                handler.sendEmptyMessage(GET_TOKEN_MSG);
            }

            @Override
            public void onFailure(int code) {

            }
        });
    }

    public static void Login(final Context context, Map<String,String> map, final Handler handler){
        PhotoPassAuthApi request = ApiFactory.INSTANCE.getPhotoPassAuthApi();
        CallTask task = new CallTask<BasicResult<TokenId>>(request.login(Common.LOGIN,map));
        task.handleResponse(new HttpCallback<BasicResult<TokenId>>() {
            @Override
            public void onSuccess(BasicResult<TokenId> res) {
                handler.obtainMessage(LOGIN_SUCCESS,res).sendToTarget();
            }

            @Override
            public void onFailure(int code) {
                super.onFailure(code);
            }
        });
    }

    public static void downLoadImage(Context context, String url, Map<String,String> map, final ImageView img) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressNumberFormat("%1d KB/%2d KB");
        dialog.setTitle("下载");
        dialog.setMessage("正在下载，请稍后...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();

        PhotoPassAuthApi request = ApiFactory.INSTANCE.getPhotoPassAuthApi();
        RetrofitClient.INSTANCE.getFileDownInterceptor().setDownLoad(true);

        RetrofitClient.INSTANCE.getFileDownInterceptor().setProgressHandler(new DownloadProgresshandler() {
            @Override
            protected void onProgress(long progress, long total, boolean done) {
                Log.e("是否在主线程中运行", String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
                Log.e("onProgress",String.format("%d%% done\n",(100 * progress) / total));
                Log.e("done","--->" + String.valueOf(done));
                dialog.setMax((int) (total/1024));
                dialog.setProgress((int) (progress/1024));

                if(done){
                    dialog.dismiss();
                }
            }
        });
        CallTask task = new CallTask<ResponseBody>(request.downLoadImage(url,map));
        task.handleResponse(new HttpCallback<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody response) {
                RetrofitClient.INSTANCE.getFileDownInterceptor().setDownLoad(false);
                try {
                    InputStream is = response.byteStream();
                    Bitmap bmp = BitmapFactory.decodeStream(is);
                    is.close();
                    img.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code) {
                RetrofitClient.INSTANCE.getFileDownInterceptor().setDownLoad(false);
                super.onFailure(code);
            }
        });

    }

    public static String md5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
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
        //32位
        return md5StrBuff.toString();
    }
}

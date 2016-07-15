package com.example.demo.peng.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.demo.R;
import com.example.demo.peng.retrofitwithokhttp.bean.BasicResult;
import com.example.demo.peng.retrofitwithokhttp.bean.TokenId;
import com.example.demo.peng.retrofitwithokhttp.http.Common;
import com.example.demo.peng.retrofitwithokhttp.http.HttpUtils;
import com.example.demo.peng.retrofitwithokhttp.pictureworks.AESKeyHelper;
import com.pictureair.jni.keygenerator.PWJniUtil;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pengwu on 16/7/15.
 */
public class LoginActivity extends Activity implements View.OnClickListener{

    EditText editName;
    EditText editPass;
    Button submit;


    final MyHandler myHandler = new MyHandler(LoginActivity.this);

    private static class MyHandler extends Handler {
        private final WeakReference<LoginActivity> mActivity;

        public MyHandler(LoginActivity activity) {
            this.mActivity = new WeakReference<LoginActivity>(activity);
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
            case HttpUtils.LOGIN_SUCCESS:
                BasicResult<TokenId> res = (BasicResult<TokenId>)msg.obj;
                Intent intent = new Intent(LoginActivity.this,DetailActivity.class);
                Bundle bundle = new Bundle();
                if (res.getResult().getUser().getEmail() != null) {
                    bundle.putString("email", res.getResult().getUser().getEmail());
                }
                if (res.getResult().getUser().getMobile() != null) {
                    bundle.putString("mobile", res.getResult().getUser().getMobile());
                }
                if (res.getResult().getUser().getBirthday() != null) {
                    bundle.putString("birthday", res.getResult().getUser().getBirthday());
                }
                intent.putExtra("bundle",bundle);
                startActivity(intent);
                finish();
                break;
            default:
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editName = (EditText) findViewById(R.id.name);
        editPass = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String userName = editName.getText().toString();
        String password = editPass.getText().toString();
        if (userName.isEmpty()){
            return;
        }else if (password.isEmpty()){
            return;
        }
        Map<String,String> map = new ConcurrentHashMap<>();
        map.put(Common.USERINFO_TOKENID,getTokenId());
        map.put(Common.USERINFO_USERNAME, userName);
        map.put(Common.USERINFO_PASSWORD, SplashActivity.md5(password));

        HttpUtils.Login(getApplicationContext(),map,myHandler);
    }

    public String getTokenId() {
        String tokenId = null;
        if (tokenId == null) {
            SharedPreferences sp =  LoginActivity.this.getApplicationContext().getSharedPreferences(Common.SHARED_PREFERENCE_USERINFO_NAME, Context.MODE_PRIVATE);
            tokenId = AESKeyHelper.decryptString(sp.getString(Common.USERINFO_TOKENID, null), PWJniUtil.getAESKey(Common.APP_TYPE_SHDRPP));
        }
        return tokenId;
    }
}

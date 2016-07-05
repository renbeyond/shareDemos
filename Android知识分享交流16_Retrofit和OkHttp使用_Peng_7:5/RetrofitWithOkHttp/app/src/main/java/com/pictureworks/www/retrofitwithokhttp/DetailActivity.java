package com.pictureworks.www.retrofitwithokhttp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pictureair.jni.keygenerator.PWJniUtil;
import com.pictureworks.www.retrofitwithokhttp.http.Common;
import com.pictureworks.www.retrofitwithokhttp.http.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView birthday;
    TextView email;
    TextView mobile;
    Button button;
    ImageView img;
    static final String url = "http://192.168.8.3:4000/media/b1b983e5cd2cfafefcb948b6bd9829746a6b4b1f539bd6077b80630377baf922764175e6657819a8758537870f3c263e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        birthday = (TextView) findViewById(R.id.bitrhday);
        email = (TextView) findViewById(R.id.email);
        mobile = (TextView) findViewById(R.id.mobile);
        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(this);
        img = (ImageView) findViewById(R.id.img);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            String eml = bundle.getString("email");
            String mob = bundle.getString("mobile");
            String bir = bundle.getString("birthday");
            birthday.setText(bir);
            email.setText(eml);
            mobile.setText(mob);
        }
    }

    @Override
    public void onClick(View v) {
        Map<String,String> map = new HashMap();
        map.put(Common.USERINFO_TOKENID,getTokenId());
        map.put(Common.PHOTOIDS, "575e32e7e6a6f558120003aa");
        HttpUtils.downLoadImage(DetailActivity.this,url,map,img);
    }

    public String getTokenId() {
        String tokenId = null;
        if (tokenId == null) {
            SharedPreferences sp = getApplicationContext().getSharedPreferences(Common.SHARED_PREFERENCE_USERINFO_NAME, Context.MODE_PRIVATE);
            tokenId = AESKeyHelper.decryptString(sp.getString(Common.USERINFO_TOKENID, null), PWJniUtil.getAESKey(Common.APP_TYPE_SHDRPP));
        }
        return tokenId;
    }
}

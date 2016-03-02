package com.example.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demo.R;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by Eric on 16/2/25.
 */
public class FinalMainActivity extends FinalActivity {
    @ViewInject(id = R.id.btnDB, click = "clickDB")
    Button btn1;
    @ViewInject(id = R.id.btnBitmap, click = "clickBitmap") Button btn2;
    @ViewInject(id = R.id.btnHttp, click = "clickHttp") Button btn3;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_main);
    }

    public void clickDB(View v) {
        startActivity(new Intent(FinalMainActivity.this, FinalDBTest.class));
    }
    public void clickBitmap(View v) {
        startActivity(new Intent(FinalMainActivity.this, FinalBitmapTest.class));
    }
    public void clickHttp(View v) {
        startActivity(new Intent(FinalMainActivity.this, FinalHttpTest.class));
    }
}

package com.demo.talon.camerademo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by talon on 15/11/9.
 */
public class BaseActivity extends Activity implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    public void widgetClick(View v){

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}

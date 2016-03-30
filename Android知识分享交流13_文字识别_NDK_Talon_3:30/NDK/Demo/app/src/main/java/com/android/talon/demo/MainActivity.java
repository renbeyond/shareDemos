package com.android.talon.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.talon.test.JniUtils;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(new JniUtils().getString());
    }
}

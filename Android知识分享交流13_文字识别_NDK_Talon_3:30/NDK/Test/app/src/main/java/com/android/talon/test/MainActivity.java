package com.android.talon.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JniUtils jniUtils = new JniUtils();
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(jniUtils.getString());

    }
}

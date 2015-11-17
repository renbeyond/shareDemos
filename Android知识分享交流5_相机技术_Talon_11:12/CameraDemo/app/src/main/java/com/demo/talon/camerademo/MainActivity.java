package com.demo.talon.camerademo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void widgetClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btn_system_camera:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  // android.media.action.IMAGE_CAPTURE
                startActivity(intent);
//              startActivityForResult(intent, 1);
                break;
            case R.id.btn_expand_camera:
                intent = new Intent(MainActivity.this,CameraActivity.class);  // android.media.action.IMAGE_CAPTURE
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}

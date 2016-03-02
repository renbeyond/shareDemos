package com.example.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.example.demo.R;

/**
 * Created by Eric on 16/2/26.
 */
public class CameraMainActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_main);
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
                intent = new Intent(CameraMainActivity.this,CameraActivity.class);  // android.media.action.IMAGE_CAPTURE
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}

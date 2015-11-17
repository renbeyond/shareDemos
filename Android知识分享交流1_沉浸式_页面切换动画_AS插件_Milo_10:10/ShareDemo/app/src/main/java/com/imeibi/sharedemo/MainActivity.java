package com.imeibi.sharedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends Activity {


    @InjectView(R.id.btn_1)
    Button btn1;
    @InjectView(R.id.btn_2)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        ButterKnife.inject(this);

    }

    public void viewOnclick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                //沉浸式状态栏
                startActivity(new Intent(this, TransparentTopBar.class));
//                overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);

                break;
            case R.id.btn_2:
                //界面切换
                startActivity(new Intent(this, TestActivity.class));
                overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);

                break;

        }

    }

    /**
     * 屏蔽返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
//                overridePendingTransition(R.anim.activity_left_in, R.anim.activity_right_out);
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}

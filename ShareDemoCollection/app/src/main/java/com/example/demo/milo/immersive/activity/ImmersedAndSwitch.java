package com.example.demo.milo.immersive.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.demo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Eric on 16/2/25.
 */
public class ImmersedAndSwitch extends Activity {
    @InjectView(R.id.btn_1)
    Button btn1;
    @InjectView(R.id.btn_2)
    Button btn2;
    @InjectView(R.id.btn_3)
    Button btn3;

    private String one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.immersed_switch);
        ButterKnife.inject(this);

        btn3.setVisibility(View.GONE);
        initView();
    }

    // 加载视图组件
    private void initView() {
        // 接收数据
        Intent intent = getIntent();
        one = intent.getStringExtra("one");
        if (one.equalsIgnoreCase("one")) {
            btn3.setVisibility(View.VISIBLE);
        }else if (one.equalsIgnoreCase("two")){
            btn3.setVisibility(View.GONE);
        }
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

            case R.id.btn_3:

                startActivity(new Intent(this, TopBarActivity.class));
                overridePendingTransition(R.anim.activity_right_in, R.anim.activity_left_out);

                break;

        }

    }

//    /**
//     * 屏蔽返回键
//     */
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
////                overridePendingTransition(R.anim.activity_left_in, R.anim.activity_right_out);
//                return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}

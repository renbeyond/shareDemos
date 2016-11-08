package com.example.demo.milo.immersive.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.demo.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by milo on 15/9/6.
 * 透明状态栏--沉浸式状态栏
 */
public class TransparentTopBar extends Activity {

    @BindView(R.id.back_btn)
    TextView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent_top_bar_layout);
        ButterKnife.bind(this);
        //设置半透明状态栏
        initWindow();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initWindow() {
        //在layout中设置,防止状态栏和内容重叠在一起
        //android:fitsSystemWindows="true"
        //android:clipToPadding="true"
        //设置状态栏和导航栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        // 创建系统状态栏和导航栏管理器
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // 设置状态栏可用
        tintManager.setStatusBarTintEnabled(true);
        // 设置导航栏可用
        tintManager.setNavigationBarTintEnabled(true);
        // 设置状态栏和导航栏的样式
        tintManager.setTintColor(getResources().getColor(R.color.app_main_color));
        // 设置状态栏样式
        //tintManager.setStatusBarTintDrawable(MyDrawable);
        // 设置导航栏样式
        //tintManager.setNavigationBarTintResource(R.drawable.my_tint);
    }
}

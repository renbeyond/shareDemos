package com.example.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.view.TopBarView;

/**
 * Created by milo on 15/11/25.
 */
public class TopBarActivity extends Activity {
    TopBarView topBarView;//自定义View

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_bar);
        //绑定TopBar
        topBarView = (TopBarView) findViewById(R.id.top_bar);
        //监听左右TextView点击事件
        topBarView.onClickListener(new TopBarView.onClickListener() {
            @Override
            public void leftOnclick() {
                Toast.makeText(TopBarActivity.this, "111", Toast.LENGTH_LONG).show();
            }

            @Override
            public void rightOnclick() {
                Toast.makeText(TopBarActivity.this, "2222", Toast.LENGTH_LONG).show();

            }
        });


        //实现特殊方法
//        topBarView.setLeftBackground(R.drawable.ic_back);
//        topBarView.setLeftGONE();
        topBarView.setLeftClickable(false);

    }
}

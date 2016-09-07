package com.example.demo.beyond.activity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;

public class VectorDrawableActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgRobot;
    private ImageView imgRect;
    private TextView textView;
    private ImageView imgClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_drawable);
        imgRobot = (ImageView) findViewById(R.id.robot_shrug);
        imgRect = (ImageView) findViewById(R.id.rect_rotate);
        imgClock = (ImageView) findViewById(R.id.clock);
        imgClock.setOnClickListener(this);
        imgRect.setOnClickListener(this);
        imgRobot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Drawable drawable = ((ImageView)v).getDrawable();
        //其实是AnimatedVectorDrawable实现了Animatable2(继承自Animatable)接口
        if (drawable instanceof Animatable){
            ((Animatable) drawable).start();//启动动画
        }
    }
}

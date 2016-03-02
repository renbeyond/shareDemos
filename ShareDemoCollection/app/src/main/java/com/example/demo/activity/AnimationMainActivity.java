package com.example.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demo.R;

/**
 * Created by Eric on 16/2/29.
 */
public class AnimationMainActivity extends Activity implements View.OnClickListener {
    private Button animationButton, animationObjectButton, nineOldButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_main);

        animationButton = (Button) findViewById(R.id.animation_btn);
        animationObjectButton = (Button) findViewById(R.id.animation_object_btn);
        nineOldButton = (Button) findViewById(R.id.nineold_btn);

        animationButton.setOnClickListener(this);
        animationObjectButton.setOnClickListener(this);
        nineOldButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.animation_btn:
                intent.setClass(AnimationMainActivity.this, AnimationActivity.class);
                break;

            case R.id.animation_object_btn:
                intent.setClass(AnimationMainActivity.this, ObjectAnimatorActivity.class);
                break;

            case R.id.nineold_btn:
                intent.setClass(AnimationMainActivity.this, GIFActivity.class);
                break;

            default:
                break;
        }
        startActivity(intent);

    }
}

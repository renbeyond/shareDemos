package com.example.demo.milo.immersive.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by milo on 15/9/7.
 */
public class TestActivity extends Activity {
    @BindView(R.id.back_btn)
    TextView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_layout);
        ButterKnife.bind(this);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.activity_left_in, R.anim.activity_right_out);
            }
        });
    }
}
package com.example.demo.bauer.materialdesign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.example.demo.R;

/**
 * Created by bauer_bao on 16/3/9.
 */
public class RippleViewActivity extends AppCompatActivity implements View.OnClickListener{

    private RippleView rippleView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bauer_rippleview_activity);

        rippleView = (RippleView) findViewById(R.id.rect);
        textView = (TextView) findViewById(R.id.rect_child);

        rippleView.setOnClickListener(this);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rect:
                Toast.makeText(this, "RippleView 被点击", Toast.LENGTH_SHORT).show();
                break;

            case R.id.rect_child:
                Toast.makeText(this, "TextView 被点击", Toast.LENGTH_SHORT).show();
                break;


        }

    }
}

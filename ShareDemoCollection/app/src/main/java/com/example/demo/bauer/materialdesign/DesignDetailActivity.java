package com.example.demo.bauer.materialdesign;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.demo.R;

public class DesignDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private TextInputLayout mTextInputLayout;
    private EditText mEditText;
    private FloatingActionButton floatingActionButton, floatingActionButton2;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bauer_activity_detail);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_input_delete);
        actionBar.setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Design详情");
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.color_0));

        mTextInputLayout = (TextInputLayout) findViewById(R.id.til_pwd);
        mEditText = mTextInputLayout.getEditText();
        mTextInputLayout.setHint("输入4个字母");

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 4) {
                    mTextInputLayout.setError("输入错误");
                    mTextInputLayout.setErrorEnabled(true);
                } else {
                    mTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatAB);
        floatingActionButton.setOnClickListener(this);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.action_button);
        floatingActionButton2.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Snackbar.make(v, "FAB 被点击", Snackbar.LENGTH_SHORT).show();
    }
}

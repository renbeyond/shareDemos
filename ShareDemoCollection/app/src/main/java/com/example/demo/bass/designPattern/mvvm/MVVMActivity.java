package com.example.demo.bass.designPattern.mvvm;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.example.demo.R;
import com.example.demo.databinding.ActivityMvvmBinding;

/**
 * Created by bass on 16/4/8.
 */
public class MVVMActivity extends Activity {
    private ActivityMvvmBinding binding;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_mvvm);
        user = new User("BASS", "123456",true);
        binding.setUser(user);
    }

    //测试非静态的，编译不通过
    public static String md5(String pwd){
        return "md5"+pwd;
    }

    public void clickMvvm(View view){
        user.setPwd("654321");
        user.setIs(false);
        binding.setUser(user);
    }

}

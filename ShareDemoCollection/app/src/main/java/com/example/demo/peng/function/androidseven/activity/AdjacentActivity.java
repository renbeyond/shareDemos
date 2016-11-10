package com.example.demo.peng.function.androidseven.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 分屏：http://fvaryu.github.io/2016/08/24/android-multiwindow/
 * */
public class AdjacentActivity extends AppCompatActivity {

    List<String> list;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjacent);
        Log.e("AdjacentActivity", "onCreate");
        lv = (ListView) findViewById(R.id.lv);
        list = new ArrayList<>();
        for (int i = 0; i< 20; i++) {
            list.add("android-AdjacentActivity" + i);
        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        Toast.makeText(this, "长安菜单键,进入分屏状态", Toast.LENGTH_SHORT).show();
        Log.e("AdjacentActivity", "onMultiWindowModeChanged");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("AdjacentActivity", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("AdjacentActivity", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("AdjacentActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("AdjacentActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("AdjacentActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("AdjacentActivity", "onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("AdjacentActivity", "onConfigurationChanged");
    }
}

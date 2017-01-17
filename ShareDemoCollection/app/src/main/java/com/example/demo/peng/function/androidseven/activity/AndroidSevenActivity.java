package com.example.demo.peng.function.androidseven.activity;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demo.R;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * link http://gold.xitu.io/post/57d8afdd8ac247006141a1cc
 *
 * */
public class AndroidSevenActivity extends AppCompatActivity {
    List<String> list;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_seven);
        Log.e("MainActivity", "onCreate");
        lv = (ListView) findViewById(R.id.lv);
        list = new ArrayList<>();
        for (int i = 0; i< 20; i++) {
            if (i == 0) {
                list.add("在另一个屏幕中分屏");
            } else if (i == 1) {
                list.add("在当前屏幕中分屏");
            } else if (i ==2) {
                list.add("Notification");
            } else if (i ==3) {
                list.add("FileProvider");
            } else {
                list.add("android-multiwindow" + i);
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = null;
                if (i == 0) {
                    intent = new Intent(AndroidSevenActivity.this, AdjacentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (i == 1){
                    intent = new Intent(AndroidSevenActivity.this, AdjacentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT);
                    startActivity(intent);
                } else if (i == 2) {
                    intent = new Intent(AndroidSevenActivity.this, NotificationActivity.class);
                    startActivity(intent);
                } else if (i == 3) {
                    intent = new Intent(AndroidSevenActivity.this, FileProviderActivity.class);
                    startActivity(intent);
                }
            }
        });

        Bundle bundle = RemoteInput.getResultsFromIntent(getIntent());
        String res = null;
        if (bundle != null) {
            res = (String) bundle.getCharSequence(NotificationActivity.KEY_REPLY);
        }
        if (!TextUtils.isEmpty(res)) {
            Toast.makeText(this, "收到消息: "+res,Toast.LENGTH_SHORT).show();
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.cancel(101);
        }
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        Toast.makeText(this, "长安菜单键,进入分屏状态",Toast.LENGTH_SHORT).show();
        Log.e("MainActivity", "onMultiWindowModeChanged");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MainActivity", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("MainActivity", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MainActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MainActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MainActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MainActivity", "onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("AdjacentActivity", "onConfigurationChanged");
    }
}

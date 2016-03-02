package com.example.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.demo.R;
import com.example.demo.adapter.MyAdapter;
import com.example.demo.bauer.animation.AnimationMainActivity;
import com.example.demo.constant.Constants;
import com.example.demo.entity.DemoInfo;
import com.example.demo.eric.ar.UnityPlayerStartActivity;
import com.example.demo.eric.eventbus.activity.EventMainActivity;
import com.example.demo.milo.immersive.activity.ImmersedAndSwitch;
import com.example.demo.bass.afinal.activity.FinalMainActivity;
import com.example.demo.talon.camera.CameraMainActivity;
import com.example.demo.talon.image.PlaceActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnItemClickListener {

    private ListView lvInfo;
    private MyAdapter myAdapter;
    private List<DemoInfo> infoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addData();
        lvInfo = (ListView) findViewById(R.id.lvInfo);
        myAdapter = new MyAdapter(this, infoList);
        lvInfo.setAdapter(myAdapter);
        lvInfo.setOnItemClickListener(this);

    }

    //加载数据
    private void addData() {
        infoList = new ArrayList<DemoInfo>();
        infoList.add(Constants.demoInfo1);
        infoList.add(Constants.demoInfo2);
        infoList.add(Constants.demoInfo3);
        infoList.add(Constants.demoInfo4);
        infoList.add(Constants.demoInfo5);
        infoList.add(Constants.demoInfo6);
        infoList.add(Constants.demoInfo7);
        infoList.add(Constants.demoInfo8);
    }

    //item单击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("========","position" + position);
        switch (position) {
            case 0:
                Log.e("========", "position" + position);
                Intent intent = new Intent();
                intent.putExtra("one", "two");
                intent.setClass(MainActivity.this, ImmersedAndSwitch.class);
                startActivity(intent);
                break;

            case 1:
                Log.e("========","position" + position);
                intent = new Intent();
                intent.setClass(MainActivity.this, FinalMainActivity.class);
                startActivity(intent);
                break;

            case 2:
                Log.e("========","position" + position);
                intent = new Intent();
                intent.setClass(MainActivity.this, UnityPlayerStartActivity.class);
                startActivity(intent);
                break;

            case 3:
                Log.e("========","position" + position);
                intent = new Intent();
                intent.setClass(MainActivity.this, CameraMainActivity.class);
                startActivity(intent);
                break;

            case 4:
                Log.e("========","position" + position);
                intent = new Intent();
                intent.setClass(MainActivity.this, AnimationMainActivity.class);
                startActivity(intent);
                break;

            case 5:
                Log.e("========","position" + position);
                intent = new Intent();
                intent.putExtra("one", "one");
                intent.setClass(MainActivity.this, ImmersedAndSwitch.class);
                startActivity(intent);
                break;

            case 6:
                Log.e("========","position" + position);
                intent = new Intent();
                intent.setClass(MainActivity.this, EventMainActivity.class);
                startActivity(intent);
                break;

            case 7:
                Log.e("========","position" + position);
                intent = new Intent();
                intent.setClass(MainActivity.this, PlaceActivity.class);
                startActivity(intent);
                break;
        }
    }
}

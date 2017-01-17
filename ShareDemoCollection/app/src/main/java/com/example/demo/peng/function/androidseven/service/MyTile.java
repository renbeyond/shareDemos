package com.example.demo.peng.function.androidseven.service;

import android.annotation.TargetApi;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

/**
 * Created by pengwu on 16/11/2.
 * <br/>
 * http://www.tuicool.com/articles/zInqAvB
 * <br/>
 * https://github.com/gavinliu/CustomQuickSettingTile
 */

@TargetApi(Build.VERSION_CODES.N)
public class MyTile extends TileService {

    //添加到快捷栏
    @Override
    public void onStartListening() {
        super.onStartListening();
        Log.e("MyTile","onStartListening");
        getQsTile().setState(Tile.STATE_INACTIVE);
        getQsTile().updateTile();
    }


//移出工具栏
    @Override
    public void onStopListening() {
        super.onStopListening();
        Log.e("MyTile","onStopListening");
    }

    private int num;
    @Override
    public void onClick() {
        super.onClick();
        num++;
        boolean enable = num % 2 == 0;


        //控制图片变化
        if (enable) {
            getQsTile().setState(Tile.STATE_ACTIVE);
        } else {
            getQsTile().setState(Tile.STATE_INACTIVE);
        }
        getQsTile().updateTile();
    }
}

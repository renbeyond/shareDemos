package com.example.demo.bass.designPattern.state;

import android.util.Log;

/**
 * 状态模式
 * Created by bass on 16/3/22.
 */
public class StateDemo {
    public static void test(){
        TvController tvController = new TvController();
        //设置开机
        tvController.powerOn();
        tvController.nextChannel();//下一个频道
        tvController.turnUp();//调高音量
        tvController.powerOff();//关机
        tvController.nextChannel();//因为关机了，所以不会进入下一个频道
    }
}

/**
 * 模仿电视机切换频道，关机了，切换频道无效
 */

//电视状态接口
interface TvState{
    void nextChannel();
    void prevChannel();
    void turnUp();
    void turnDown();
}

/**
 * 关机状态，其他按键无效
 */
class PowerOffState implements TvState{

    @Override
    public void nextChannel() {
    }

    @Override
    public void prevChannel() {
    }

    @Override
    public void turnUp() {
    }

    @Override
    public void turnDown() {
    }
}

/**
 * 开机状态，其他按键无效
 */
class PowerOnState implements TvState {

    @Override
    public void nextChannel() {
        Log.v("","下一个频道");
    }

    @Override
    public void prevChannel() {
        Log.v("","上一个频道");

    }

    @Override
    public void turnUp() {
        Log.v("","音量＋");

    }

    @Override
    public void turnDown() {
        Log.v("","音量 － ");

    }
}

/**
 * 电源接口
 */
interface PowerController{
    void powerOn();
    void powerOff();
}


/**
 * 电视遥控器，类似于经典状态模式中的Context
 */
class TvController implements PowerController{
    TvState tvState;

    public void setTvState(TvState tvState){
        this.tvState = tvState;
    }

    @Override
    public void powerOn() {
        Log.v("","开机啦 ");
        setTvState(new PowerOnState());//重新更换对象
    }

    @Override
    public void powerOff() {
        Log.v("","关机机啦 ");
        setTvState(new PowerOffState());//重新更换对象
    }

    public void nextChannel(){
        tvState.nextChannel();
    }

    public void prevChannel(){
        tvState.prevChannel();
    }

    public void turnUp(){
        tvState.turnUp();
    }

    public void turnDown(){
        tvState.turnDown();
    }

}


/**
 * 客户端
 */
class Client{
    private void main(){

        TvController tvController = new TvController();
        //设置开机
        tvController.powerOn();
        tvController.nextChannel();//下一个频道
        tvController.turnUp();//调高音量
        tvController.powerOff();//关机
        tvController.nextChannel();//因为关机了，所以不会进入下一个频道
    }
}





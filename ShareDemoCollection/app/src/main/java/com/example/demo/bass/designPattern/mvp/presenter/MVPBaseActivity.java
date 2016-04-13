package com.example.demo.bass.designPattern.mvp.presenter;

import android.app.Activity;
import android.os.Bundle;

import com.example.demo.bass.designPattern.mvp.view.ViewInterface;

/**
 * Created by bass on 16/4/11.
 */
public abstract class MVPBaseActivity<V, T extends BasePresenter<V>> extends Activity {
    protected T mPresenter; //Presenter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter(); //创建Presenter
        mPresenter.attachView((V)this); // View 与 Presenter 建立关联
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract T createPresenter();
}

class TestActivity extends MVPBaseActivity<ViewInterface,MvpPresenter> implements ViewInterface{

    @Override
    protected MvpPresenter createPresenter() {
        return new MvpPresenter(this);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void goneDialog() {

    }

    @Override
    public void showData(Object obj) {

    }
}

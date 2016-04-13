package com.example.demo.bass.designPattern.mvp.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by bass on 16/4/11.
 */
public class BasePresenter<T> {
    protected Reference<T> mViewRef; //view接口类型弱引用

    public void attachView(T view){
        mViewRef = new WeakReference<T>(view); //建立关系
    }

    protected T getView(){
        return mViewRef.get();
    }

    public boolean isViewAttached(){
        return  mViewRef != null && mViewRef.get() != null;
    }

    public void detachView(){
        if (mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }
}

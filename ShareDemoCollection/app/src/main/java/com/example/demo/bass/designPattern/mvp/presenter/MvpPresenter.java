package com.example.demo.bass.designPattern.mvp.presenter;

import com.example.demo.bass.designPattern.mvp.model.ModelImpl;
import com.example.demo.bass.designPattern.mvp.model.ModelInterface;
import com.example.demo.bass.designPattern.mvp.view.ViewInterface;
import com.example.demo.bass.designPattern.singleton.Singleton;

/**
 * Created by bass on 16/3/30.
 */
public class MvpPresenter extends BasePresenter<ViewInterface> {

    private ModelInterface modelInterface;
    private ViewInterface viewInterface;

    public MvpPresenter(ViewInterface viewInterface){
        this.viewInterface = viewInterface;
        modelInterface = new ModelImpl();
    }

    public void singletion(){
        Singleton singleton = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();
        viewInterface.showData(singleton == singleton2);
    }
}

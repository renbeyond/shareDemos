package com.example.demo.bass.designPattern.mvp.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.bass.designPattern.Prototype.PrototypeDemo;
import com.example.demo.bass.designPattern.abstractFactor.AbstractFactory2;
import com.example.demo.bass.designPattern.bridge.DridgeDemo;
import com.example.demo.bass.designPattern.builder.BuilderDemo;
import com.example.demo.bass.designPattern.command.CommandDemo;
import com.example.demo.bass.designPattern.composite.ComponentDemo;
import com.example.demo.bass.designPattern.cursor.Cursor;
import com.example.demo.bass.designPattern.decorate.DecorateDemo;
import com.example.demo.bass.designPattern.facade.FacadeDemo;
import com.example.demo.bass.designPattern.factoryMethod.FactoryMethod;
import com.example.demo.bass.designPattern.flyweight.FlyweightDemo;
import com.example.demo.bass.designPattern.iterator.Iterator;
import com.example.demo.bass.designPattern.mediator.MediatorDemo;
import com.example.demo.bass.designPattern.memento.MementoDemo;
import com.example.demo.bass.designPattern.mvp.presenter.MvpPresenter;
import com.example.demo.bass.designPattern.observer.ObserverDemo;
import com.example.demo.bass.designPattern.proxy.ProxyDemo;
import com.example.demo.bass.designPattern.state.StateDemo;
import com.example.demo.bass.designPattern.strategy.StrategyDemo;
import com.example.demo.bass.designPattern.templateMethod.TemplateMethod;
import com.example.demo.bass.designPattern.visitor.VisitorDemo;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by bass on 16/3/30.
 */
public class MvpActivity extends AutoLayoutActivity implements ViewInterface, View.OnClickListener {
    private MvpPresenter mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        mvpPresenter = new MvpPresenter(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_singletion).setOnClickListener(this);
        findViewById(R.id.btn_builder).setOnClickListener(this);
        findViewById(R.id.btn_prototype).setOnClickListener(this);
        findViewById(R.id.btn_factory).setOnClickListener(this);
        findViewById(R.id.btn_flyweight).setOnClickListener(this);
        findViewById(R.id.btn_abstract_factor).setOnClickListener(this);
        findViewById(R.id.btn_strategy).setOnClickListener(this);
        findViewById(R.id.btn_state).setOnClickListener(this);
        findViewById(R.id.btn_iterator).setOnClickListener(this);
        findViewById(R.id.btn_interpreter).setOnClickListener(this);
        findViewById(R.id.btn_command).setOnClickListener(this);
        findViewById(R.id.btn_observer).setOnClickListener(this);
        findViewById(R.id.btn_mement).setOnClickListener(this);
        findViewById(R.id.btn_adapter).setOnClickListener(this);
        findViewById(R.id.btn_template).setOnClickListener(this);
        findViewById(R.id.btn_visitor).setOnClickListener(this);
        findViewById(R.id.btn_mediator).setOnClickListener(this);
        findViewById(R.id.btn_proxy).setOnClickListener(this);
        findViewById(R.id.btn_composite).setOnClickListener(this);
        findViewById(R.id.btn_cursor).setOnClickListener(this);
        findViewById(R.id.btn_decorete).setOnClickListener(this);
        findViewById(R.id.btn_facade).setOnClickListener(this);
        findViewById(R.id.btn_bridge).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_singletion:
                mvpPresenter.singletion();
                break;
            case R.id.btn_builder:
                //这样写避免写多个构造
                BuilderDemo builderDemo = new BuilderDemo.Builder("bass").age(18).address("pudong").build();
                showData("builderDemo");
                break;

            case R.id.btn_prototype:
                PrototypeDemo prototypeDemo = new PrototypeDemo();
                PrototypeDemo prototypeDemo1 = prototypeDemo.clone();
                showData("prototypeDemo");
                break;
            case R.id.btn_factory:
                //创建一个具体工厂
                FactoryMethod.test();
                showData("FactoryMethod");
                break;
            case R.id.btn_flyweight:
                FlyweightDemo.test();//享元模式
                showData("享元模式");

                break;
            case R.id.btn_abstract_factor:
                AbstractFactory2.test();
                showData("抽象工厂");
                break;
            case R.id.btn_strategy:
                StrategyDemo.test();
                showData("策略模式");
                break;
            case R.id.btn_state:
                StateDemo.test();
                showData("状态模式");
                break;
            case R.id.btn_iterator:
                Iterator.test();
                showData("责任链模式");
                break;
            case R.id.btn_interpreter:
                showData("解释器模式（不实用）");
                break;
            case R.id.btn_command:
                CommandDemo.test();
                showData("命令模式）");
                break;
            case R.id.btn_observer:
                ObserverDemo.test();
                showData("观察者模式）");
                break;
            case R.id.btn_mement:
                MementoDemo.test();
                showData("备忘录模式）");
                break;
            case R.id.btn_adapter:
                showData("适配器模式）");
                break;
            case R.id.btn_template:
                TemplateMethod.test();
                showData("模版方法模式）");
                break;
            case R.id.btn_visitor:
                VisitorDemo.test();
                showData("访问者模式）");
                break;
            case R.id.btn_mediator:
                MediatorDemo.test();
                showData("中介者模式）");
                break;
            case R.id.btn_proxy:
                ProxyDemo.test();
                showData("代理模式）");

                break;
            case R.id.btn_composite:
                ComponentDemo.test();
                showData("组合模式）");

                break;
            case R.id.btn_cursor:
                Cursor.test();
                showData("迭代器模式(不实用)");

                break;
            case R.id.btn_decorete:
                DecorateDemo.test();
                showData("装饰模式");

                break;
            case R.id.btn_facade:
                FacadeDemo.test();
                showData("外观模式");

                break;
            case R.id.btn_bridge:
                DridgeDemo.test();
                showData("桥接模式");
                break;
        }
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void goneDialog() {

    }

    @Override
    public void showData(Object obj) {
        Toast.makeText(MvpActivity.this, obj + "", Toast.LENGTH_SHORT).show();
    }


}

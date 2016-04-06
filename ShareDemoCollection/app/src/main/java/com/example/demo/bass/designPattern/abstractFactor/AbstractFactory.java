package com.example.demo.bass.designPattern.abstractFactor;

import android.util.Log;

/**
 *
 * 抽象工厂模式(书中第一组写法)（看不明白者观看AbstractFactory2）
 *
 * Created by bass on 16/3/19.
 */
public abstract class AbstractFactory {//抽象工厂类
    /**
     * 创建产品A
     */
    abstract AbstractProductA creteProductA();
    /**
     * 创建产品B
     */
    abstract AbstractProductB creteProductB();
}

/**
 *  抽象产品A
 */
abstract class AbstractProductA{
    //每个具体子类需要实现的方法
    abstract void method();
}

/**
 *  抽象产品B
 */
abstract class AbstractProductB{
    //每个具体子类需要实现的方法
    abstract void method();
}

/**
 * 具体产品A1
 */
class ConcreteProductA1 extends AbstractProductA{

    @Override
    void method() {
        Log.v("","具体产品A1");
    }
}

/**
 * 具体产品A2
 */
class ConcreteProductA2 extends AbstractProductA{

    @Override
    void method() {
        Log.v("","具体产品A2");
    }
}

/**
 * 具体产品B1
 */
class ConcreteProductB1 extends AbstractProductB{

    @Override
    void method() {
        Log.v("","具体产品B1");
    }
}

/**
 * 具体产品B2
 */
class ConcreteProductB2 extends AbstractProductB{

    @Override
    void method() {
        Log.v("","具体产品B2");
    }
}

/**
 * 具体工厂1
 */
abstract class ConcreateFactory1 extends AbstractFactory{
    @Override
    AbstractProductA creteProductA() {
        return new ConcreteProductA1();
    }

    @Override
    AbstractProductB creteProductB() {
        return new ConcreteProductB1();
    }
}

/**
 * 具体工厂2
 */
abstract class ConcreateFactory2 extends AbstractFactory{
    @Override
    AbstractProductA creteProductA() {
        return new ConcreteProductA2();
    }

    @Override
    AbstractProductB creteProductB() {
        return new ConcreteProductB2();
    }
}

class Test{

}
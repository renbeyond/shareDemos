package com.example.demo.bass.designPattern.abstractFactor;

import android.util.Log;

/**
 * 抽象工厂简单实现
 *
 * 例如： 工厂造车 制造Q3、Q5、Q7，但是他们都有轮胎、发动机、制动系统
 * Created by bass on 16/3/19.
 */
public class AbstractFactory2 {
    public static void test(){//客户选择的
        CarFactory carFactory = new Q3Factory();
        carFactory.createBrake();//因为是Q3的工厂，所以这里都是普通的
        carFactory.createEngine();
        carFactory.createTire();

        CarFactory carFactory7 = new Q7Factory();
        carFactory7.createBrake();//因为是Q7的工厂，所以这里都是高级的
        carFactory7.createEngine();
        carFactory7.createTire();
    }
}

/**
 * 抽象车厂类
 */
abstract class CarFactory{
    /**
     * 轮胎
     */
    public abstract ITire createTire();
    /**
     * 发动机
     */
    public abstract IEngine createEngine();
    /**
     * 制动系统
     */
    public abstract IBrake createBrake();
}

/**
 * 轮胎相关类
 */
interface ITire{
    void tire();//轮胎
}

class NormalTire implements ITire{

    @Override
    public void tire() {
        Log.v("","普通轮胎");
    }
}

class SUVTire implements ITire{

    @Override
    public void tire() {
        Log.v("","越野轮胎");
    }
}

/**
 * 发动机相关类
 */
interface IEngine{
    void engine();//发动机
}

class DomesticEngine implements IEngine{

    @Override
    public void engine() {
        Log.v("","普通发动机");
    }
}

class ImportEngine implements IEngine{

    @Override
    public void engine() {
        Log.v("","进口发动机");
    }
}

/**
 * 制动系统相关类
 */
interface IBrake{
    void brake();//制动系统
}

class NormalBrake implements IBrake{

    @Override
    public void brake() {
        Log.v("","普通制动");
    }
}

class SeniorBrake implements IBrake{

    @Override
    public void brake() {
        Log.v("","高级制动");
    }
}

/**
 *  对于Q3 的工厂，其实用的零件不见不同，而对于Q7工厂，其零件也是不同的
 */
class Q3Factory extends CarFactory{

    @Override
    public ITire createTire() {
        return new NormalTire();//普通轮胎
    }

    @Override
    public IEngine createEngine() {
        return new DomesticEngine();//国产发动机
    }

    @Override
    public IBrake createBrake() {
        return new NormalBrake();//普通制动
    }
}

/**
 * Q7工厂
 */
class Q7Factory extends CarFactory{

    @Override
    public ITire createTire() {
        return new SUVTire();//SUV轮胎
    }

    @Override
    public IEngine createEngine() {
        return new ImportEngine();//进口发动机
    }

    @Override
    public IBrake createBrake() {
        return new SeniorBrake();//高级制动
    }
}

///**
// * 客户类
// */
//class Client{
//    public Client(){//客户选择的
//        CarFactory carFactory = new Q3Factory();
//        carFactory.createBrake();//因为是Q3的工厂，所以这里都是普通的
//        carFactory.createEngine();
//        carFactory.createTire();
//
//        CarFactory carFactory7 = new Q7Factory();
//        carFactory7.createBrake();//因为是Q7的工厂，所以这里都是高级的
//        carFactory7.createEngine();
//        carFactory7.createTire();
//    }
//}









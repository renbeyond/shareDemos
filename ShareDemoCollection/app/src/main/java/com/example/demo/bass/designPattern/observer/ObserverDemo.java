package com.example.demo.bass.designPattern.observer;

import java.util.ArrayList;

/**
 * Created by bass on 16/3/23.
 */
public class ObserverDemo {
    public static void test() {
        HanFeiZi hanFeiZi = new HanFeiZi();
        LiSi liSi = new LiSi();
        DiaoSi diaoSi = new DiaoSi();
        HanFeiZiWife hanFeiZiWife = new HanFeiZiWife();
        hanFeiZi.addObserver(liSi);
        hanFeiZi.addObserver(diaoSi);
        hanFeiZi.addObserver(hanFeiZiWife);

        hanFeiZi.haveBreakfast();
        hanFeiZi.haveFun();
    }
}

interface Observer {

    void update(String context);

}

/**
 *
 */
interface Observerable {

    void addObserver(Observer observer);

    void deleteObserver(Observer observer);

    void notifyObservers(String context);

}

interface IHanFeiZi {

    void haveBreakfast();

    void haveFun();
}

/**
    被观察者代码如下：
 *
 */
class HanFeiZi implements IHanFeiZi, Observerable{

    private ArrayList<Observer> observerList = new ArrayList<Observer>();

    @Override
    public void haveBreakfast() {
        System.out.println("韩非子:正在吃饭");
        notifyObservers("韩非子正在吃饭");
    }

    @Override
    public void haveFun() {
        System.out.println("韩非子:正在娱乐");
        notifyObservers("韩非子正在娱乐");
    }

    @Override
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers(String context) {
        for(Observer observer : observerList){
            observer.update(context);
        }
    }
}

/**
    三个观察者代码如下：
 *
 */
class LiSi implements Observer{

    @Override
    public void update(String context) {
        System.out.println("李斯:发现韩非子有活动");
        reportToQinShiHuang(context);
        System.out.println("报告完毕");
    }

    private void reportToQinShiHuang(String context) {
        System.out.println("李斯:报告老大，发现韩非子有活动" + "<--->" + context);
    }

}

/**
 *
 */
class DiaoSi implements Observer{

    @Override
    public void update(String context) {
        System.out.println("屌丝:发现韩非子有活动" + "<--->" + context);
        System.out.println("屌丝非常伤心");
    }
}

class HanFeiZiWife implements Observer{

    @Override
    public void update(String context) {
        System.out.println("韩非子妻子：这死鬼竟然在吃喝玩乐");
    }
}

/**
    场景类代码如下：
 *
 */
//class Client {
//
//    public static void main(String[] args) {
//        HanFeiZi hanFeiZi = new HanFeiZi();
//        LiSi liSi = new LiSi();
//        DiaoSi diaoSi = new DiaoSi();
//        HanFeiZiWife hanFeiZiWife = new HanFeiZiWife();
//        hanFeiZi.addObserver(liSi);
//        hanFeiZi.addObserver(diaoSi);
//        hanFeiZi.addObserver(hanFeiZiWife);
//
//        hanFeiZi.haveBreakfast();
//        hanFeiZi.haveFun();
//    }
//}
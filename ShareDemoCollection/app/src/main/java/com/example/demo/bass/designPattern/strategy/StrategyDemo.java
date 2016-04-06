package com.example.demo.bass.designPattern.strategy;

/**
 * 策略模式
 *
 * Created by bass on 16/3/21.
 */
public class StrategyDemo {
    public static void test() {
        Context context = new Context(new ConcreteStrategy1());
        context.algorithm();

        context = new Context(new ConcreteStrategy2());
        context.algorithm();
    }
}

/**
 * 某个希望有不同策略实现的算法
 */
abstract class AbstractStrategy {
    public abstract void algorithm();
}

/**
 * 对算法的第一种具体实现策略
 * @author zhanche
 *
 */
class ConcreteStrategy1 extends AbstractStrategy {

    @Override
    public void algorithm() {
        System.out.println("----------------我是策略一算法----------------");
    }
}

/**
 * 对算法的第二种具体实现策略
 * @author zhanche
 *
 */
class ConcreteStrategy2 extends AbstractStrategy {

    @Override
    public void algorithm() {
        System.out.println("----------------我是策略二算法----------------");
    }
}

/**
 * 环境角色，主要完成对特定策略的调用
 * @author zhanche
 */
class Context {
    private AbstractStrategy strategy;

    public Context(AbstractStrategy strategy) {
        this.strategy = strategy;
    }
    public void algorithm() {
        this.strategy.algorithm();
    }
}


///**
// * 策略模式测试类
// * @author zhanche
// */
//class Client {
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        Context context = new Context(new ConcreteStrategy1());
//        context.algorithm();
//
//        context = new Context(new ConcreteStrategy2());
//        context.algorithm();
//    }
//}



package com.example.demo.bass.designPattern.singleton;

/**
 * 单例模式
 * 懒汉，饿汉，双重校验锁，枚举和静态内部类。
 *
 * （静态内部类 推荐使用)
 * Created by bass on 16/3/15.
 */


/**
 * 第一种（懒汉，线程不安全）
 * 这种写法lazy loading很明显，但是致命的是在多线程不能正常工作。
 */
public class Singleton {
    private static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}

/**
 * 第二种 （懒汉，线程安全）：
 * 这种写法能够在多线程中很好的工作，而且看起来它也具备很好的lazy loading，但是，遗憾的是，效率很低，99%情况下不需要同步。
 */
class Singleton2 {
    private static Singleton2 instance;

    private Singleton2() {
    }

    public static synchronized Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }
}

/**
 * 第三种（饿汉）：
 * 这种方式基于classloder机制避免了多线程的同步问题，不过，instance在类装载时就实例化，
 * 虽然导致类装载的原因有很多种，在单例模式中大多数都是调用getInstance方法，
 * 但是也不能确定有其他的方式（或者其他的静态方法）导致类装载，这时候初始化instance显然没有达到lazy loading的效果。
 */
class Singleton3 {
    private static Singleton3 instance = new Singleton3();

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        return instance;
    }
}

/**
 * 第4种（双重校验锁）： Double Check Lock（DCL）
 * 又名：DCL单例
 * 解决资源消耗、多余同步、线程安全
 * <p/>
 * 缺点：可能出现（DCL）失效
 */
class Singleton4 {
    private volatile static Singleton4 singleton4;

    private Singleton4() {
    }

    public static Singleton4 getSingleton() {
        if (singleton4 == null) {
            synchronized (Singleton4.class) {
                if (singleton4 == null) {
                    singleton4 = new Singleton4();
                }
            }
        }
        return singleton4;
    }
}

/**
 * 第五种（静态内部类）：(推荐使用)
 */
class Singleton5 {
    private Singleton5() {
    }

    public static final Singleton5 getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 静态内部类
     */
    private static class SingletonHolder {
        private static final Singleton5 INSTANCE = new Singleton5();
    }
}

/**
 * 枚举单例
 */
enum Singleton6 {
    INSTANCE;
    public void whateverMethod() {
    }
}












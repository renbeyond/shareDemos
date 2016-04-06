package com.example.demo.bass.designPattern.facade;

/**
 * Created by bass on 16/3/29.
 */
public class FacadeDemo {
    public static void test() {
        Facade f = new Facade();
        f.methodA();
        f.methodB();
        f.methodC();
    }
}


interface ServiceA {
    void methodA();
}

class ServiceAImpl implements ServiceA {
    @Override
    public void methodA() {
        System.out.println("methodA--> is runing");
    }

}

interface ServiceB {
    void methodB();
}

class ServiceBImpl implements ServiceB {
    @Override
    public void methodB() {
        System.out.println("methodB--> is runing");
    }
}

interface ServiceC {
    void methodC();
}

class ServiceCImpl implements ServiceC {
    @Override
    public void methodC() {
        System.out.println("methodC--> is runing");
    }

}

class Facade {
    ServiceA sa;
    ServiceB sb;
    ServiceC sc;
    public Facade() {
        sa = new ServiceAImpl();
        sb = new ServiceBImpl();
        sc = new ServiceCImpl();
    }
    public void methodA() {
        sa.methodA();
        sb.methodB();
    }
    public void methodB() {
        sb.methodB();
        sc.methodC();
    }
    public void methodC() {
        sc.methodC();
        sa.methodA();
    }
}

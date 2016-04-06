package com.example.demo.bass.designPattern.iterator;

/**
 * 责任链模式
 * Created by bass on 16/3/22.
 */
public class Iterator {
    public static void test(){
        //处理者
        ConcreteHandler1 concreteHandler1 = new ConcreteHandler1();
        ConcreteHandler2 concreteHandler2 = new ConcreteHandler2();

        //为处理者设置下一个处理者的节点
        concreteHandler1.successor = concreteHandler2;
        concreteHandler2.successor = concreteHandler1;

        //处理请求
        concreteHandler1.handleRequest("ConcreteHandler2");
    }
}


abstract class Handler {
    protected Handler successor;//下一节点处理者
    public abstract void handleRequest(String str);
}

class ConcreteHandler1 extends Handler {
    @Override
    public void handleRequest(String str) {
        if (str.equals("ConcreteHandler1")) {
            System.out.println("处理事件");
            return;
        } else {
            successor.handleRequest(str);
        }

    }
}

class ConcreteHandler2 extends Handler {
    @Override
    public void handleRequest(String str) {
        if (str.equals("ConcreteHandler2")) {
            System.out.println("处理事件");
            return;
        } else {
            successor.handleRequest(str);
        }
    }
}

///**
// * 调用者
// */
//class Client{
//    private void main(){
//        //处理者
//        ConcreteHandler1 concreteHandler1 = new ConcreteHandler1();
//        ConcreteHandler2 concreteHandler2 = new ConcreteHandler2();
//
//        //为处理者设置下一个处理者的节点
//        concreteHandler1.successor = concreteHandler2;
//        concreteHandler2.successor = concreteHandler1;
//
//        //处理请求
//        concreteHandler1.handleRequest("ConcreteHandler2");
//    }
//}
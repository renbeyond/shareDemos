package com.example.demo.bass.designPattern.adapter;

/**
 * Created by bass on 16/3/25.
 */
public class AdapterDemo {
}

/**
 * 源角色
 */
 class Adaptee {
    public int get220v(){
        return 220;
    }
}

/**
 * 目标角色
 */
 interface Target {
    int get110v();
    int get220v();
}

/**
 * 适配器角色:扩展源角色，实现目标角色，从而使得目标角色改动时候，不用改动源角色，只要改动适配器
 */
 class Adapter extends Adaptee implements Target{
    public int get110v(){
        return 110;
    }
}

/**
 * 客户端
 */
 class Client {
    public static void main(String rags[]) {
        new Client().test();
    }

    public void test() {
        Target target = new Adapter();
        int v1 = target.get110v();
        int v2 = target.get220v();
    }
}
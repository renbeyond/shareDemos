package com.example.demo.bass.designPattern.Prototype;

import java.util.ArrayList;

/**
 * 原型模式
 * Created by bass on 16/3/19.
 */
public class PrototypeDemo {
    public PrototypeDemo clone(){
        PrototypeDemo prototype = null;
        try{
            prototype = (PrototypeDemo)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return prototype;
    }
}

class ConcretePrototype extends PrototypeDemo{
    public void show(){
        System.out.println("原型模式实现类");
    }
}

/**
 * 调用方法
 * 如果clone对象里面出现复杂类型的对象，需要做深拷贝处理。上面的是浅拷贝
 */
class Client {
    public static void main(String[] args){
        ConcretePrototype cp = new ConcretePrototype();
        for(int i=0; i< 10; i++){
            ConcretePrototype clonecp = (ConcretePrototype)cp.clone();
            clonecp.show();
        }
    }
}

/**
 * 数组、容器对象、引用对象等都不会拷贝，这就是浅拷贝。如果要实现深拷贝，必须将原型模式中的数组、容器对象、引用对象等另行拷贝。例如：
 */
class Prototype implements Cloneable {
    private ArrayList list = new ArrayList();
    public Prototype clone(){
        Prototype prototype = null;
        try{
            prototype = (Prototype)super.clone();
            prototype.list = (ArrayList) this.list.clone();//这里就是深拷贝
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return prototype;
    }
}
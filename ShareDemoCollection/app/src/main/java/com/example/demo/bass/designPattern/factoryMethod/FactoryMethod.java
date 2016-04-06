package com.example.demo.bass.designPattern.factoryMethod;

/**
 * 工厂方法模式
 * Created by bass on 16/3/19.
 */
public class FactoryMethod {
    //看下方代码
    public static void test() {
        //创建一个具体工厂
        Factory factory = new ConcreteFactory();
        //根据参数中具体产品的.class名称来决定创建的产品类型
        IProduct product01 = factory.createProduct(ConcreteProductA.class);
        IProduct product02 = factory.createProduct(ConcreteProductB.class);

        product01.method01();
        product01.method02();
        product02.method01();
        product02.method02();
    }
}


// 产品接口，定义一系列产品应该实现的服务，即产品的共性
interface IProduct {
    void method01();
    void method02();
}

// 具体的产品实现类
class ConcreteProductA implements IProduct {
    public void method01() {
        System.out.println("ConcreteProductA method01() ...");
    }
    public void method02() {
        System.out.println("ConcreteProductA method02() ...");
    }
}

class ConcreteProductB implements IProduct {
    public void method01() {
        System.out.println("ConcreteProductB method01() ...");
    }

    public void method02() {
        System.out.println("ConcreteProductB method02() ...");
    }
}

// 抽象的工厂类，定义了其子类必须实现的createProduct()方法
abstract class Factory {
    //运用了Java 中的泛型和反射技术
    public abstract <T extends IProduct> T createProduct(Class<T> c);
}

class ConcreteFactory extends Factory {
    public <T extends IProduct> T createProduct(Class<T> c) {
        T product = null;
        try {
            product = (T) Class.forName(c.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }
}

//class Client {
//    public static void main(String[] args) {
//        //创建一个具体工厂
//        Factory factory = new ConcreteFactory();
//        //根据参数中具体产品的.class名称来决定创建的产品类型
//        IProduct product01 = factory.createProduct(ConcreteProductA.class);
//        IProduct product02 = factory.createProduct(ConcreteProductB.class);
//
//        product01.method01();
//        product01.method02();
//        product02.method01();
//        product02.method02();
//    }
//}
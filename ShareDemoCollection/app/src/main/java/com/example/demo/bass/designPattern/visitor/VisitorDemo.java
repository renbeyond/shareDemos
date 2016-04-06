package com.example.demo.bass.designPattern.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bass on 16/3/24.
 */
public class VisitorDemo {
    public static void test() {
        //创建一个结构对象
        ObjectStructure os = new ObjectStructure();
        //给结构增加一个节点
        os.add(new NodeA());
        //给结构增加一个节点
        os.add(new NodeB());
        //创建一个访问者
        Visitor visitor = new VisitorA();
        os.action(visitor);
    }
}

/**
 * 　　可以看到，抽象访问者角色为每一个具体节点都准备了一个访问操作。由于有两个节点，因此，对应就有两个访问操作。
 */
interface Visitor {
    /**
     * 对应于NodeA的访问操作
     */
    void visit(NodeA node);

    /**
     * 对应于NodeB的访问操作
     */
    void visit(NodeB node);
}

/**
　　具体访问 者VisitorA类
 *
 */
class VisitorA implements Visitor {
    /**
     * 对应于NodeA的访问操作
     */
    @Override
    public void visit(NodeA node) {
        System.out.println(node.operationA());
    }

    /**
     * 对应于NodeB的访问操作
     */
    @Override
    public void visit(NodeB node) {
        System.out.println(node.operationB());
    }

}

/**
　　具体访问者VisitorB类
 *
 */
class VisitorB implements Visitor {
    /**
     * 对应于NodeA的访问操作
     */
    @Override
    public void visit(NodeA node) {
        System.out.println(node.operationA());
    }

    /**
     * 对应于NodeB的访问操作
     */
    @Override
    public void visit(NodeB node) {
        System.out.println(node.operationB());
    }

}

/**
　　抽象节点类
 *
 */
abstract class Node {
    /**
     * 接受操作
     */
    public abstract void accept(Visitor visitor);
}

/**
　　具体节点 类NodeA
 *
 */
class NodeA extends Node {
    /**
     * 接受操作
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    /**
     * NodeA特有的方法
     */
    public String operationA() {
        return "NodeA";
    }

}

/**
　　 具体节点类NodeB
 *
 */
class NodeB extends Node {
    /**
     * 接受方法
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    /**
     * NodeB特有的方法
     */
    public String operationB() {
        return "NodeB";
    }
}


/**
　　结构对象角色类，这个结构 对象角色持有一个聚集，并向外界提供add()方法作为对聚集的管理操作。通过调用这个方法，可以动态地增加一个新的节点。
 *
 */
class ObjectStructure {

    private List<Node> nodes = new ArrayList<>();

    /**
     * 执行方法操作
     */
    public void action(Visitor visitor) {

        for (Node node : nodes) {
            node.accept(visitor);
        }

    }

    /**
     * 添加一个新元素
     */
    public void add(Node node) {
        nodes.add(node);
    }
}

///**
//    客户端类
// *
// */
//
//
//class Client {
//    public static void main(String[] args) {
//        //创建一个结构对象
//        ObjectStructure os = new ObjectStructure();
//        //给结构增加一个节点
//        os.add(new NodeA());
//        //给结构增加一个节点
//        os.add(new NodeB());
//        //创建一个访问者
//        Visitor visitor = new VisitorA();
//        os.action(visitor);
//    }
//
//}
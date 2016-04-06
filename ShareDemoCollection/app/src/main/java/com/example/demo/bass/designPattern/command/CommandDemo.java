package com.example.demo.bass.designPattern.command;

/**
 * Created by bass on 16/3/23.
 */
public class CommandDemo {
    public static void test() {
        //创建接收者
        Receiver receiver = new Receiver();
        //创建命令对象，设定它的接收者
        Command command = new ConcreteCommand(receiver);
        //创建请求者，把命令对象设置进去

        Invoker invoker = new Invoker(command);//这个就像是事务

        //执行方法
        invoker.action();
    }
}

/**
接收者角色类
 *
 */
class Receiver {
    /**
     * 真正执行命令相应的操作
     */
    public void action(){
        System.out.println("执行操作");
    }
}

/**
　　 抽象命令角色类
 *
 */
interface Command {
    /**
     * 执行方法
     */
    void execute();
}

/**
　　 具体命令角色类
 *
 */
class ConcreteCommand implements Command {
    //持有相应的接收者对象
    private Receiver receiver = null;
    /**
     * 构造方法
     */
    public ConcreteCommand(Receiver receiver){
        this.receiver = receiver;
    }
    @Override
    public void execute() {
        //通常会转调接收者对象的相应方法，让接收者来真正执行功能
        receiver.action();
    }

}

/**
　　请求者角 色类
 *
 */
class Invoker {
    /**
     * 持有命令对象
     */
    private Command command = null;
    /**
     * 构造方法
     */
    public Invoker(Command command){
        this.command = command;
    }
    /**
     * 行动方法
     */
    public void action(){

        command.execute();
    }
}

/**
　　客户端角 色类
 *
 */
class Client {

    public static void main(String[] args) {
        //创建接收者
        Receiver receiver = new Receiver();
        //创建命令对象，设定它的接收者
        Command command = new ConcreteCommand(receiver);
        //创建请求者，把命令对象设置进去

        Invoker invoker = new Invoker(command);//这个就像是事务

        //执行方法
        invoker.action();
    }

}
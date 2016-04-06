package com.example.demo.bass.designPattern.decorate;

/**
 * Created by bass on 16/3/26.
 */
public class DecorateDemo {
    public static void test() {
        Project employe = new Employe();        //代码工人
        Project managerA = new ManagerA(employe); //项目经理
        Project managerB = new ManagerB(employe); //项目经理
        //以经理的名义将编码完成，功劳都是经理的，实际编码的是工人
        managerA.doCoding();
        managerB.doCoding();
    }
}

/**
 * 一、原理图
 * <p/>
 * 其中类的职责如下：
 * <p/>
 * 抽象构件角色（Project）：给出一个接口，以规范准备接收附加责任的对象
 * 具体构件角色（Employe）：定义一个将要接收附加责任的类
 * 装饰角色（Manager）：持有一个构件对象的实例，并定义一个与抽象构件接口一致的接口
 * 具体装饰角色（ManagerA、ManagerB）：负责给构件对象“贴上”附加的责任
 * <p/>
 * 二、下面通过一个软件项目例子来说明装饰模式的使用
 * 过程是这样的：
 * 项目经理接到一个项目，项目最终要完成编码。
 * 项目经理接到项目后，先做些前期的工作（比如需求分析、设计），然后将编码工作委派给代码工人，代码工人干完后，项目经理做项目的收尾工作。
 * <p/>
 * 实现代码如下：
 */
interface Project {

    /**
     * 写代码
     */
    void doCoding();
}

/**
 * 代码工人
 */
class Employe implements Project {
    /**
     * 编码
     */
    public void doCoding() {
        System.out.println("代码工人 在编写代码，加班编啊编啊，终于编完了！");
    }
}

/**
 * 项目经理
 */
class Manager implements Project {
    private Project project;        //实际上存放的是代码工人对象

    public Manager(Project project) {
        this.project = project;
    }

    /**
     * 编码
     */
    public void doCoding() {
        //项目经理开始新的工作
        startNewWork();
    }

    /**
     * 模板：定义项目经理自己的事情
     */
    public void startNewWork() {
        //项目经理在做早期工作
        doEarlyWork();
        //项目经理很牛，做完需求和设计后，直接将编码委派给代码工人干
        project.doCoding();
        //项目经理在做收尾工作
        doEndWork();
    }

    /**
     * 项目经理自己的事情：做早期工作
     */
    public void doEarlyWork() {
    }

    /**
     * 项目经理做收尾工作
     */
    public void doEndWork() {
    }
}

/**
 * 具体的项目经理A
 */
class ManagerA extends Manager {

    public ManagerA(Project project) {
        super(project);
    }

    /**
     * 项目经理自己的事情：做早期工作
     */
    public void doEarlyWork() {
        System.out.println("项目经理A 在做需求分析");
        System.out.println("项目经理A 在做架构设计");
        System.out.println("项目经理A 在做详细设计");
    }
}

/**
 * 具体的项目经理B
 */
class ManagerB extends Manager {

    public ManagerB(Project project) {
        super(project);
    }

    /**
     * 项目经理自己的事情：做早期工作
     */
    public void doEarlyWork() {
        System.out.println("项目经理B 在做需求分析");
        System.out.println("项目经理B 在做详细设计");
    }

    /**
     * 项目经理做收尾工作
     */
    public void doEndWork() {
        System.out.println("项目经理B 在做收尾工作");
    }
}


/**
 * 运行结果：
 * 项目经理A 在做需求分析
 * 项目经理A 在做架构设计
 * 项目经理A 在做详细设计
 * 代码工人 在编写代码，加班编啊编啊，终于编完了！
 * 项目经理B 在做需求分析
 * 项目经理B 在做详细设计
 * 代码工人 在编写代码，加班编啊编啊，终于编完了！
 * 项目经理B 在做收尾工作
 */

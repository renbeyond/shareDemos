package com.example.demo.bass.designPattern.templateMethod;

/**
 * Created by bass on 16/3/23.
 */
public class TemplateMethod {
    public static void test() {
        Student student = new Student();
        student.prepareGotoSchool();

        Teacher teacher  = new Teacher();
        teacher.prepareGotoSchool();
    }
}

/**
 * 抽象类AbstractClass
 */
abstract class AbstractPerson{
    //抽象类定义整个流程骨架
    public void prepareGotoSchool(){
        dressUp();
        eatBreakfast();
        takeThings();
    }
    //以下是不同子类根据自身特性完成的具体步骤
    protected abstract void dressUp();
    protected abstract void eatBreakfast();
    protected abstract void takeThings();
}

/**
 * 具体类ConcreteClass
 */
class Student extends AbstractPerson{
    @Override
    protected void dressUp() {
        System.out.println("穿校服");
    }
    @Override
    protected void eatBreakfast() {
        System.out.println("吃妈妈做好的早饭");
    }

    @Override
    protected void takeThings() {
        System.out.println("背书包，带上家庭作业和红领巾");
    }
}

class Teacher extends AbstractPerson{
    @Override
    protected void dressUp() {
        System.out.println("穿工作服");
    }
    @Override
    protected void eatBreakfast() {
        System.out.println("做早饭，照顾孩子吃早饭");
    }

    @Override
    protected void takeThings() {
        System.out.println("带上昨晚准备的考卷");
    }
}

//class Client {
//    public static void main(String[] args) {
//        Student student = new Student();
//        student.prepareGotoSchool();
//
//        Teacher teacher  = new Teacher();
//        teacher.prepareGotoSchool();
//    }
//}
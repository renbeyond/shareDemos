package com.example.demo.bass.designPattern.bridge;

/**
 * Created by bass on 16/3/29.
 */
public class DridgeDemo {
    public static void test() {
        CpuAbility ability =new IntelCpu();
        AbstractComputer computer =new LenevoComputer(ability);
        computer.checkPcAbility();
        ability =new AdmCpu();//华硕笔记本CPU性能系能比较牛
        computer =new IswComputer(ability);
        computer.checkPcAbility();//IBM笔记本CPU性能系能一般
    }
}

interface CpuAbility{

    String abilityCpu();
}

class AdmCpu implements CpuAbility{

    public String abilityCpu() {
        // TODO Auto-generated method stub
        return "系能一般";
    }
}

class IntelCpu implements CpuAbility{
    public String abilityCpu() {
        // TODO Auto-generated method stub
        return "系能比较牛";
    }
}

abstract class AbstractComputer{

    CpuAbility cpuAbility;

    public AbstractComputer(CpuAbility cpuAbility){
        this.cpuAbility=cpuAbility;

    }


    public abstract void checkPcAbility();

}

class LenevoComputer extends AbstractComputer{

    public LenevoComputer(CpuAbility cpuAbility) {
        super(cpuAbility);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void checkPcAbility() {
        System.out.println("华硕笔记本CPU性能"+super.cpuAbility.abilityCpu());
    }

}

class IswComputer extends AbstractComputer{

    public IswComputer(CpuAbility cpuAbility) {
        super(cpuAbility);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void checkPcAbility() {
        System.out.println("IBM笔记本CPU性能"+super.cpuAbility.abilityCpu());
    }

}

//class test{
//    public void testDridgeDemo() {
//        CpuAbility ability =new IntelCpu();
//        AbstractComputer computer =new LenevoComputer(ability);
//        computer.checkPcAbility();
//        ability =new AdmCpu();//华硕笔记本CPU性能系能比较牛
//        computer =new IswComputer(ability);
//        computer.checkPcAbility();//IBM笔记本CPU性能系能一般
//    }
//}


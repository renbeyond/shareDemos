package com.example.demo.bass.designPattern.proxy;

/**
 * Created by bass on 16/3/25.
 */
public class ProxyDemo {
    public static void test() {
        WangPo wangPo;
        //把王婆叫出来
        wangPo = new WangPo();
        //然后西门庆说，我要和潘金莲Happy,然后王婆就安排了西门庆丢筷子哪出戏：
        wangPo.makeEyesWithMan();
        //看到没有表面是王婆在做，其实爽的是潘金莲
        wangPo.happyWithMan();
    }
}

/**
 * 定义一种类型的女人，王婆和潘金莲都属于这个类型的女人
 */
interface KindWoman {

    //这种女人能做什么事情呢？
    public void makeEyesWithMan();//抛媚眼

    public void happyWithMan();//和男人那个....

}

/**
 * 一种类型嘛，那肯定是接口，定义个潘金莲
 * 定义一个潘金莲是什么样的人
 */
class PanJinLian implements KindWoman {

    @Override
    public void happyWithMan() {
        System.out.println("潘金莲和男人在做那个...");

    }

    @Override
    public void makeEyesWithMan() {
        System.out.println("潘金莲抛媚眼...");

    }

}

/**
 * 王婆这个人老聪明了，她太老了，是个男人都看不上她，
 * 但是她有智慧经验呀，他作为一类女人的代理！
 */
class WangPo implements KindWoman {

    private KindWoman kindWoman;

    public WangPo() {
        //默认的话是潘金莲的代理
        this.kindWoman = new PanJinLian();
    }

    //她可以是KindWomam的任何一个女人的代理，只要你是这一类型
    public WangPo(KindWoman kindWoman) {
        this.kindWoman = kindWoman;
    }

    @Override
    public void happyWithMan() {
        //自己老了，干不了了，但可以叫年轻的代替。
        this.kindWoman.happyWithMan();

    }

    @Override
    public void makeEyesWithMan() {
        //王婆年纪大了，谁看她抛媚眼啊
        this.kindWoman.makeEyesWithMan();

    }

}

//class XiMenQiang {
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        WangPo wangPo;
//        //把王婆叫出来
//        wangPo = new WangPo();
//        //然后西门庆说，我要和潘金莲Happy,然后王婆就安排了西门庆丢筷子哪出戏：
//        wangPo.makeEyesWithMan();
//        //看到没有表面是王婆在做，其实爽的是潘金莲
//        wangPo.happyWithMan();
//    }
//}

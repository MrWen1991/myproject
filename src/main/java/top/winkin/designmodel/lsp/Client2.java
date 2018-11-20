package top.winkin.designmodel.lsp;

import java.util.*;

/**
 * @Description: 前置条件放宽或放窄都没有影响到父类方法的调用 子类可以替换父类对象
 *  只要子类对象调用方法是参数能够区分具体的调用对象即可，因此必须满足重载条件
 *  同时如果实现父类抽象方法也能实现子类对象替换父类对象
 * @Author: wenjiajia
 * @Data: 2018/9/30 下午2:42
 */
public class Client2 {
    public static void invok() {
        Son1 s = new Son1();
        s.doSomething(new HashMap());// 子类参数 Map
    }

    public static void invok2() {
        Son2 s = new Son2();
        s.doSomething(new HashMap());// 子类参数 LinkedHashMap
    }

    public static void main(String[] args) {
        invok();
        invok2();
    }

}

class Father {
    public Iterable doSomething(HashMap map) {
        System.out.println("父类执行了。。。");
        return map.values();
    }
}

class Son1 extends Father {

    /**
     * 重载父类方法，前置条件放宽 替换父类对象不会调用子类方法
     *
     * @param map
     * @return
     */
    public Collection doSomething(Map map) {
        System.out.println("子类1执行了。。。");
        return map.values();
    }
}

class Son2 extends Father {
    /**
     * 重载父类方法，前置条件放窄 替换父类对象不会调用子类方法
     *
     * @param map
     * @return
     */
    public Collection doSomething(LinkedHashMap map) {
        System.out.println("子类2执行了。。。");
        return map.values();
    }
}

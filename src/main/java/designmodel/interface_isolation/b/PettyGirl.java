package designmodel.interface_isolation.b;

import designmodel.interface_isolation.a.IPettyGirl;

/**
 * @Description: 将原有的一个接口拆分成两个接口 接口粒度细化
 * @Author: wenjiajia
 * @Data: 2018/10/8 下午5:14
 */
public class PettyGirl implements IGoodBodyGirl, IGreatParamentGirl {
    private String name;

    public PettyGirl(String name) {
        this.name = name;
    }

    @Override
    public void niceFeature() {
        System.out.println(name + "有好身材");
    }

    @Override
    public void goodLooking() {
        System.out.println(name + "有姣好的面容");
    }

    @Override
    public void greatParament() {
        System.out.println(name + "有好气质");
    }
}

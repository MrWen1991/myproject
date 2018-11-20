package top.winkin.designmodel.interface_isolation.a;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/10/8 下午5:14
 */
public class PettyGirl implements IPettyGirl {
    private String name;

    public PettyGirl(String name) {
        this.name = name;
    }

    @Override
    public void goodLooking() {
        System.out.println(name + "有姣好的面容");
    }

    @Override
    public void greatTemparament() {
        System.out.println(name + "有好气质");
    }

    @Override
    public void niceFeacture() {
        System.out.println(name + "有好身材");
    }
}

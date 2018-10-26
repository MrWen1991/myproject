package designmodel.LoD;

import java.util.Random;

/**
 * @Description: 朋友类也不要随意掺和人家的事，尽量只提供一个公有接口
 * wizard 方法私有化，只在内部调用 高内聚
 * @Author: wenjiajia
 * @Data: 2018/10/9 上午10:27
 */
public class Demo4 {
    public static void main(String[] args) {
        InstallSoftware installSoftware = new InstallSoftware();
        int count = 1;
        while (true) {
            if (installSoftware.installSoftware(new Wizard())) {
                System.out.println("安装成功");
                break;
            }
            System.out.println("time:" + count++);
        }

    }
}

class InstallSoftware2 {
    public boolean install(Wizard2 wizard) {
        return wizard.installSoftware();
    }
}

class Wizard2 {
    Random rnd = new Random();

    //
    private int first() {
        System.out.println("running first ...");
        return rnd.nextInt(100);
    }

    private int second() {
        System.out.println("running second ...");
        return rnd.nextInt(100);
    }

    private int third() {
        System.out.println("running third ...");
        return rnd.nextInt(100);
    }

    public boolean installSoftware() {
        if (this.first() > 50) {
            if (this.second() > 50) {
                if (this.third() > 50) {
                    System.out.println("install success ...");
                    return true;
                }
            }

        }
        return false;
    }
}

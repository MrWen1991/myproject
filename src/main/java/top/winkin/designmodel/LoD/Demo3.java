package top.winkin.designmodel.LoD;

import java.util.Random;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/10/9 上午10:27
 */
public class Demo3 {
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

class InstallSoftware {
    public boolean installSoftware(Wizard wizard) {
        // wizard 的first second third 方法与 InstallSoftware 耦合严重 改进见demo4
        if (wizard.first() > 50) {
            if (wizard.second() > 50) {
                if (wizard.third() > 50) {
                    System.out.println("install success ...");
                    return true;
                }
            }

        }
        return false;
    }
}

class Wizard {
    Random rnd = new Random();

    public int first() {
        System.out.println("running first ...");
        return rnd.nextInt(100);
    }

    public int second() {
        System.out.println("running second ...");
        return rnd.nextInt(100);
    }

    public int third() {
        System.out.println("running third ...");
        return rnd.nextInt(100);
    }
}

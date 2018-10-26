package designmodel.lsp;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/9/30 下午2:03
 */
public class Client1 {
    public static void main(String[] args) {
        AbstractGun gun1 = new HandGun();
        AbstractGun gun2 = new Rifile();
        AbstractGun gun3 = new MachineGun();
        gun3.setToyGun(new ToyGun());

        Soldier soldier = new Soldier();
        soldier.setGun(gun1);
        soldier.killEnemy();

        soldier.setGun(gun2);
        soldier.killEnemy();
        soldier.killDummy();

        soldier.setGun(gun3);
        soldier.killEnemy();
        soldier.killDummy();

    }
}

/**
 *
 */
abstract class AbstractGun {
    /**
     * 因为玩具枪不能实现真实的射击功能，采用依赖的方式放弃继承
     */
    private AbstractToy toyGun;


    public void setToyGun(AbstractToy toyGun) {
        this.toyGun = toyGun;
    }

    public AbstractToy getToyGun() {
        return toyGun;
    }

    /**
     * 真抢射击
     */
    public abstract void shoot();
}

class HandGun extends AbstractGun {

    @Override
    public void shoot() {
        System.out.println("手枪射击。。。");
    }

}

class Rifile extends AbstractGun {

    @Override
    public void shoot() {
        System.out.println("步枪射击。。。");
    }

}

class MachineGun extends AbstractGun {

    @Override
    public void shoot() {
        System.out.println("机枪射击。。。");
    }

}

abstract class AbstractToy {
    /**
     * 玩具抢射击
     */
    public abstract void shoot();
}

class ToyGun extends AbstractToy {

    @Override
    public void shoot() {
        System.out.println("玩具枪射击了。。。");
    }
}

class Soldier {

    private AbstractGun gun;

    public void setGun(AbstractGun gun) {
        this.gun = gun;
    }

    /**
     * 击杀敌人
     */
    public void killEnemy() {
        gun.shoot();
    }

    /**
     * 击杀假人
     */
    public void killDummy() {
        AbstractToy toy = gun.getToyGun();
        if (toy != null) {
            toy.shoot();
        } else {
            System.out.println("玩具枪不存在。。。");
        }
    }
}

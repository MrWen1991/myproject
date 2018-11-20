package top.winkin.designmodel.interface_isolation.b;

import top.winkin.designmodel.interface_isolation.a.IPettyGirl;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/10/8 下午5:17
 */
public abstract class AbstractSearcher {
    public IPettyGirl pettyGirl;

    public AbstractSearcher(IPettyGirl pettyGirl) {
        this.pettyGirl = pettyGirl;
    }

    public abstract void show();
}

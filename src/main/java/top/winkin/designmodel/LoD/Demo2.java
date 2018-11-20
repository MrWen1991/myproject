package top.winkin.designmodel.LoD;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 把girl2作为参数直接传给groupLeader 隔离teacher与girl之间耦合
 * @Author: wenjiajia
 * @Data: 2018/10/9 上午9:07
 */
public class Demo2 {
    public static void main(String[] args) {
        List girlList = new ArrayList<Girl2>();
        for (int i = 0; i < 20; i++) {
            girlList.add(new Girl2());
        }

        Tearcher2 tearcher = new Tearcher2();
        tearcher.commond(new GroupLeader2(girlList));
    }
}

class Tearcher2 {
    public void commond(GroupLeader2 groupLeader) {
        groupLeader.count();
    }
}

class GroupLeader2 {
    private List girlList;

    public GroupLeader2(List girlList) {
        this.girlList = girlList;
    }

    public void count() {
        System.out.println("一共有" + girlList.size() + "个女生");
    }
}

class Girl2 {
}

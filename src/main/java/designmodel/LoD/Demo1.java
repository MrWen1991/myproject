package designmodel.LoD;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/10/9 上午9:07
 */
public class Demo1 {
    public static void main(String[] args) {
        Tearcher tearcher = new Tearcher();
        tearcher.commond(new GroupLeader());
    }
}

class Tearcher {
    public void commond(GroupLeader groupLeader) {
        // 由于girl不是朋友类，但teacher也对其产生了依赖 此处不合理 demo2是改进版
        List girlList = new ArrayList<Girl>();
        for (int i = 0; i < 20; i++) {
            girlList.add(new Girl());
        }
        groupLeader.count(girlList);
    }
}

class GroupLeader {
    public void count(List girlList) {
        System.out.println("一共有" + girlList.size() + "个女生");
    }
}

class Girl {
}

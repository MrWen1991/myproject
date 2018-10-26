package designmodel.interface_isolation.a;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/10/8 下午5:18
 */
public class Searcher extends AbstractSearcher {
    public Searcher(IPettyGirl pettyGirl) {
        super(pettyGirl);
    }

    @Override
    public void show() {
        super.pettyGirl.goodLooking();
        super.pettyGirl.greatTemparament();
        super.pettyGirl.niceFeacture();
    }

    public static void main(String[] args) {
        IPettyGirl pettyGirl = new PettyGirl("嫣嫣");
        AbstractSearcher searcher = new Searcher(pettyGirl);
        searcher.show();
    }
}

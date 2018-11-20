package top.winkin.designmodel.mediator;

import java.util.Random;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/11/11 1:57 PM
 */
public class Sale extends AbstractColleague{

    public Sale(AbstractMediator mediator) {
        super(mediator);
    }
    public void sellIBM(int number){
    }
    public int getSaleStatus(){
        return new Random(100).nextInt();
    }
    public void offSale(){

//        super.mediator.
    }

}

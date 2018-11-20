package top.winkin.designmodel.mediator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/11/11 1:44 PM
 */
public class Stock extends AbstractColleague {

    private static final Logger log = LoggerFactory.getLogger(Stock.class);
    private static int COMPUTER_NUMBER = 100;

    public Stock(AbstractMediator mediator) {
        super(mediator);
    }

    public void increase(int number){
        COMPUTER_NUMBER += number;
        log.info("库存数量：{}",number);
    }

    public int getStockNumber(){
        return COMPUTER_NUMBER;
    }

    public void clearStock(){
        //TODO
    }
}

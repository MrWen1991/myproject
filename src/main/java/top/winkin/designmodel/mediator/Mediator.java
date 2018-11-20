package top.winkin.designmodel.mediator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/11/11 2:03 PM
 */
public class Mediator extends AbstractMediator {

    private static final Logger log = LoggerFactory.getLogger(Mediator.class);

    @Override
    public void excute(Process operation, Object object) {
        switch (operation) {
            case BUY:
                this.buyComputer((int) object);
                break;
            case SELL:
                sale.sellIBM((int)object);
                break;
            case CLEAR:
                stock.clearStock();
                break;
            case OFF_SELL:
                break;
            default:
                log.info("没有当前的匹配项。。。");
        }
    }

    private void buyComputer(int number){
        int saleStatus = super.sale.getSaleStatus();
        if(saleStatus>80){
            log.info("销售状况良好");
        }else{
            log.info("销售状况不好，折半采购");
            number /=2;
        }
        super.stock.increase(number);
    }

    private void sellComputer(int number){
        if(super.stock.getStockNumber()<number){
            super.purchase.buyIBMComputer(number);
        }
        super.sale.sellIBM(number);
    }

    private void offSell(){
        log.info("折价销售{}台电脑",stock.getStockNumber());
    }

    private void clearStock(){
        //折价销售
        //拒绝采购
    }

}

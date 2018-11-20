package top.winkin.designmodel.mediator;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/11/11 1:42 PM
 */
public abstract class AbstractMediator {
    protected Purchase purchase;
    protected Sale sale;
    protected  Stock stock;

    public AbstractMediator() {
        this.purchase = new Purchase(this);
        this.sale = new Sale(this);
        this.stock = new Stock(this);
    }
    public abstract void excute(Process operation, Object object);
}

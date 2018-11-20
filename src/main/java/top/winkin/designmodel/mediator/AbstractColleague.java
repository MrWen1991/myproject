package top.winkin.designmodel.mediator;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/11/11 1:48 PM
 */
public abstract class AbstractColleague {
    protected AbstractMediator mediator;

    public AbstractColleague(AbstractMediator mediator) {
        this.mediator = mediator;
    }
}

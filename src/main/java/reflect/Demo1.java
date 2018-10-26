package reflect;

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/10/25 2:19 PM
 */
public class Demo1 {
    public static void main(String[] args){
        TmsAdjust tmsAdjust = new TmsAdjust();
        tmsAdjust.setAdjustApplyDate("111");
        tmsAdjust.setAdjustApplyUserid("userId");
        tmsAdjust.setAdjustComments("common");
        tmsAdjust.setAdjustDlv("ajustDlv");
        tmsAdjust.setAdjustStatCd("stateCode");
        TmsAdjust clone = ReflectUtil.clone(tmsAdjust,TmsAdjust.class);
        System.out.println(">>>>"+clone.toString());
    }


}

package top.winkin.reflect;

/**
 * @Describle 例外调整实体
 * @author 杨晨宇
 * @date 2018-07-04
 */
public class TmsAdjust {
	
	
	/**
	 * 是否例外调整
	 */
	private String adjustYn;
	/**
	 * 例外调整类型
	 */
	private String adjustType;
	/**
	 * 例外调整备注
	 */
	private String adjustComments;
	/**
	 * 例外调整扣除配送公司编码
	 */
	private String adjustDlv;
	/**
	 * 例外调整状态 20申请 30驳回 40审核
	 */
	private String adjustStatCd;
	/**
	 * 例外调整申请人
	 */
	private String adjustApplyUserid;
	/**
	 * 例外调整申请时间
	 */
	private String adjustApplyDate;
	
	/**
	 * 订单号
	 */
	private String ordId;
	/**
	 * 运单号
	 */
	private String invcId;
	
	
	

	public String getAdjustYn() {
		return adjustYn;
	}

	public void setAdjustYn(String adjustYn) {
		this.adjustYn = adjustYn;
	}

	public String getAdjustType() {
		return adjustType;
	}

	public void setAdjustType(String adjustType) {
		this.adjustType = adjustType;
	}

	public String getAdjustComments() {
		return adjustComments;
	}

	public void setAdjustComments(String adjustComments) {
		this.adjustComments = adjustComments;
	}

	public String getAdjustDlv() {
		return adjustDlv;
	}

	public void setAdjustDlv(String adjustDlv) {
		this.adjustDlv = adjustDlv;
	}

	public String getAdjustStatCd() {
		return adjustStatCd;
	}

	public void setAdjustStatCd(String adjustStatCd) {
		this.adjustStatCd = adjustStatCd;
	}

	public String getAdjustApplyUserid() {
		return adjustApplyUserid;
	}

	public void setAdjustApplyUserid(String adjustApplyUserid) {
		this.adjustApplyUserid = adjustApplyUserid;
	}

	public String getAdjustApplyDate() {
		return adjustApplyDate;
	}

	public void setAdjustApplyDate(String adjustApplyDate) {
		this.adjustApplyDate = adjustApplyDate;
	}

	public String getOrdId() {
		return ordId;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}

	public String getInvcId() {
		return invcId;
	}

	public void setInvcId(String invcId) {
		this.invcId = invcId;
	}

	@Override
	public String toString() {
		return "TmsAdjust{" +
				"adjustYn='" + adjustYn + '\'' +
				", adjustType='" + adjustType + '\'' +
				", adjustComments='" + adjustComments + '\'' +
				", adjustDlv='" + adjustDlv + '\'' +
				", adjustStatCd='" + adjustStatCd + '\'' +
				", adjustApplyUserid='" + adjustApplyUserid + '\'' +
				", adjustApplyDate='" + adjustApplyDate + '\'' +
				", ordId='" + ordId + '\'' +
				", invcId='" + invcId + '\'' +
				'}';
	}
}

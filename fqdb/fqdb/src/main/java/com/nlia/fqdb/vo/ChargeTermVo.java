package com.nlia.fqdb.vo;


import java.io.Serializable;
import java.util.Date;



public class ChargeTermVo implements Serializable{

    private static final long serialVersionUID = 1L;

	
	private String name;
	private Long chargeTypeId;
	private Long flightMateInfoId;
	private Long chargeTargetId;
	private Long chargeSubjectId;
	private double fee;
	private String currency;
	private String currencyUnit;
	private String remark;
	private String removeFlag;
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getChargeTypeId() {
		return chargeTypeId;
	}
	public void setChargeTypeId(Long chargeTypeId) {
		this.chargeTypeId = chargeTypeId;
	}
	public Long getFlightMateInfoId() {
		return flightMateInfoId;
	}
	public void setFlightMateInfoId(Long flightMateInfoId) {
		this.flightMateInfoId = flightMateInfoId;
	}
	public Long getChargeTargetId() {
		return chargeTargetId;
	}
	public void setChargeTargetId(Long chargeTargetId) {
		this.chargeTargetId = chargeTargetId;
	}
	public Long getChargeSubjectId() {
		return chargeSubjectId;
	}
	public void setChargeSubjectId(Long chargeSubjectId) {
		this.chargeSubjectId = chargeSubjectId;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCurrencyUnit() {
		return currencyUnit;
	}
	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemoveFlag() {
		return removeFlag;
	}
	public void setRemoveFlag(String removeFlag) {
		this.removeFlag = removeFlag;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
	
	
}

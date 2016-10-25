package com.wonders.frame.ams.model;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * @author 
 * 收费条目
 */
@Entity
@Table(name = "CHARGE_TERM")
public class ChargeTerm implements ILongIdRemoveFlagModel{
	
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@Column(name = "CHARGE_TYPE_ID")
	private Long chargeType;
	
	@Column(name = "FLIGHT_MATE_INFO_ID")
	private Long flightMateInfoId;
	
	@Column(name = "CHARGE_TARGET_ID")
	private Long chargeTarget;
	
	@Column(name = "CHARGE_SUBJECT_ID")
	private Long chargeSubject;
	
	@Column(name = "FEE")
	private double fee;
	
	@Column(name = "FEE_BEFOR_DISCOUNT")
	private double feeBeforeDiscount;
	
	/**
	 * 货币
	 */
	@Column(length = 10)
	private String currency;

	/**
	 * 货币单位（如：元，万元等）
	 */
	@Column(length = 10, name = "CURRENCY_UNIT")
	private String currencyUnit;
	
	private String remark;
	
	@Column(length = 1, name = "REMOVE_FLAG")
	private String removeFlag;

	@Column(name = "CREATE_USER")
	private String createUser;

	@Temporal(TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "MODIFY_USER")
	private String modifyUser;

	@Column(name = "MODIFY_TIME")
	@Temporal(TIMESTAMP)
	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getChargeType() {
		return chargeType;
	}

	public void setChargeType(Long chargeType) {
		this.chargeType = chargeType;
	}

	public Long getFlightMateInfoId() {
		return flightMateInfoId;
	}

	public void setFlightMateInfoId(Long flightMateInfoId) {
		this.flightMateInfoId = flightMateInfoId;
	}

	public double getFee() {
		return fee;
	}

	public Long getChargeTarget() {
		return chargeTarget;
	}

	public void setChargeTarget(Long chargeTarget) {
		this.chargeTarget = chargeTarget;
	}

	public Long getChargeSubject() {
		return chargeSubject;
	}

	public void setChargeSubject(Long chargeSubject) {
		this.chargeSubject = chargeSubject;
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

	public void setFee(double fee) {
		this.fee = fee;
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

	public double getFeeBeforeDiscount() {
		return feeBeforeDiscount;
	}

	public void setFeeBeforeDiscount(double feeBeforeDiscount) {
		this.feeBeforeDiscount = feeBeforeDiscount;
	}
	
	

}

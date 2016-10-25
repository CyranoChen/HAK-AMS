package com.nlia.fqdb.entity;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.Table;

/**
 * @author shimingjie
 * 收费条目
 */
@Entity
@Table(name = "CHARGE_TERM")
public class ChargeTerm implements Serializable{
	
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	

	@ManyToOne
	@JoinColumn(name = "CHARGE_TYPE_ID", referencedColumnName = "id")
	private ChargeType chargeType;
	
	@ManyToOne
	@JoinColumn(name = "FLIGHT_MATE_INFO_ID", referencedColumnName = "id")
	private FlightMateInfo flightMateInfo;
	
	@ManyToOne
	@JoinColumn(name = "CHARGE_TARGET_ID", referencedColumnName = "id")
	private ChargeTarget chargeTarget;
	
	@ManyToOne
	@JoinColumn(name = "CHARGE_SUBJECT_ID", referencedColumnName = "id")
	private ChargeSubject chargeSubject;
	
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

	public ChargeType getChargeType() {
		return chargeType;
	}

	public void setChargeType(ChargeType chargeType) {
		this.chargeType = chargeType;
	}

	public FlightMateInfo getFlightMateInfo() {
		return flightMateInfo;
	}

	public void setFlightMateInfo(FlightMateInfo flightMateInfo) {
		this.flightMateInfo = flightMateInfo;
	}

	public double getFee() {
		return fee;
	}

	public ChargeTarget getChargeTarget() {
		return chargeTarget;
	}

	public void setChargeTarget(ChargeTarget chargeTarget) {
		this.chargeTarget = chargeTarget;
	}

	public ChargeSubject getChargeSubject() {
		return chargeSubject;
	}

	public void setChargeSubject(ChargeSubject chargeSubject) {
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

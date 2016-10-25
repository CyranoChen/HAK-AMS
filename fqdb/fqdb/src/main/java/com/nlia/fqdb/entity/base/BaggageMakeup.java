package com.nlia.fqdb.entity.base;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 * 行李分拣转盘
 */
@Entity
@Table(name = "BASE_BAGGAGE_MAKEUP")
public class BaggageMakeup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	// adder:zhuhaijian,20130701
	@Column(name = "Basic_Data_ID", length = 20)
	private String basicDataID;
	
	/**
	 * 行李分拣转盘号
	 */
	@Column(name = "BAGGAGE_MAKEUP_CODE", length = 5, unique = true)
	private String baggageMakeupCode;

	/**
	 * 行李分拣转盘所属候机楼
	 */
	@Column(name = "BAGGAGE_TERMINAL", length = 5)
	private String baggageTerminal;

	/**
	 * 分拣转盘描述
	 */
	@Column(name = "BAGGAGE_DESCRIPTION", length = 100)
	private String baggageDescription;

	/**
	 * 分拣转盘容积
	 */
	@Column(name = "BAGGAGE_MAKEUP_CAPACITY", length = 100)
	private String baggageMakeupCapacity;

	@Column(name = "REMOVE_FLAG", length = 1)
	private String removeFlag;

	@Column(name = "CREATE_USER", length = 20)
	private String createUser;

	@Temporal(TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "MODIFY_USER", length = 20)
	private String modifyUser;

	@Temporal(TIMESTAMP)
	@Column(name = "MODIFY_TIME")
	private Date modifyTime;
	@Transient
	private String verifyDescription;
	
	@Transient
	private Map<Integer,List<Integer>> errorMessage;
	
	public String getVerifyDescription() {
		return verifyDescription;
	}

	public void setVerifyDescription(String verifyDescription) {
		this.verifyDescription = verifyDescription;
	}

	public Map<Integer, List<Integer>> getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(Map<Integer, List<Integer>> errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBaggageMakeupCode() {
		return baggageMakeupCode;
	}

	public void setBaggageMakeupCode(String baggageMakeupCode) {
		this.baggageMakeupCode = baggageMakeupCode;
	}

	public String getBaggageTerminal() {
		return baggageTerminal;
	}

	public void setBaggageTerminal(String baggageTerminal) {
		this.baggageTerminal = baggageTerminal;
	}

	public String getBaggageDescription() {
		return baggageDescription;
	}

	public void setBaggageDescription(String baggageDescription) {
		this.baggageDescription = baggageDescription;
	}

	public String getBaggageMakeupCapacity() {
		return baggageMakeupCapacity;
	}

	public void setBaggageMakeupCapacity(String baggageMakeupCapacity) {
		this.baggageMakeupCapacity = baggageMakeupCapacity;
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

	public String getBasicDataID() {
		return basicDataID;
	}

	public void setBasicDataID(String basicDataID) {
		this.basicDataID = basicDataID;
	}
}

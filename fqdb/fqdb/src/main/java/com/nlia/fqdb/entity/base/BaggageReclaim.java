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
 * 行李提取转盘
 */
@Entity
@Table(name = "BASE_BAGGAGE_RECLAIM")
public class BaggageReclaim implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	
	// adder:zhuhaijian,20130701
	@Column(name = "Basic_Data_ID", length = 20)
	private String basicDataID;

	/**
	 * 行李提取转盘号码
	 */
	@Column(name = "BAGGAGE_RECLAIM_CODE", length = 5, unique = true)
	private String baggageReclaimCode;

	/**
	 * 提取转盘所属候机楼
	 */
	@Column(name = "BAGGAGE_RECLAIM_TERMINAL", length = 5)
	private String baggageReclaimTerminal;

	/**
	 * 提取转盘容积
	 */
	@Column(name = "BAGGAGE_RECLAIM_CAPACITY", length = 3)
	private Long baggageReclaimCapacity;

	/**
	 * 提取转盘描述说明
	 */
	@Column(name = "BAGGAGE_RECLAIM_DESCRIPTION", length = 100)
	private String baggageReclaimDescription;

	/**
	 * 提取转盘所在区域
	 */
	@Column(name = "BAGGAGE_MAKEUP_AREA", length = 5)
	private String baggageMakeupArea;

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

	public String getBaggageReclaimCode() {
		return baggageReclaimCode;
	}

	public void setBaggageReclaimCode(String baggageReclaimCode) {
		this.baggageReclaimCode = baggageReclaimCode;
	}

	public String getBaggageReclaimTerminal() {
		return baggageReclaimTerminal;
	}

	public void setBaggageReclaimTerminal(String baggageReclaimTerminal) {
		this.baggageReclaimTerminal = baggageReclaimTerminal;
	}

	public Long getBaggageReclaimCapacity() {
		return baggageReclaimCapacity;
	}

	public void setBaggageReclaimCapacity(Long baggageReclaimCapacity) {
		this.baggageReclaimCapacity = baggageReclaimCapacity;
	}

	public String getBaggageReclaimDescription() {
		return baggageReclaimDescription;
	}

	public void setBaggageReclaimDescription(String baggageReclaimDescription) {
		this.baggageReclaimDescription = baggageReclaimDescription;
	}

	public String getBaggageMakeupArea() {
		return baggageMakeupArea;
	}

	public void setBaggageMakeupArea(String baggageMakeupArea) {
		this.baggageMakeupArea = baggageMakeupArea;
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

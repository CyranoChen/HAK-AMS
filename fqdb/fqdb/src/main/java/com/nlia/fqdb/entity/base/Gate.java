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
 * 登机门
 */
@Entity
@Table(name = "BASE_GATE")
public class Gate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	// adder:zhuhaijian,20130701
	@Column(name = "Basic_Data_ID", length = 20)
	private String basicDataID;
	
	/**
	 * 登机门号码
	 */
	@Column(name = "GATE_CODE", length = 5, unique = true)
	private String gateCode;

	/**
	 * 登机门所属候机楼
	 */
	@Column(name = "GATE_TERMINAL", length = 5)
	private String gateTerminal;

	/**
	 * 登机门类型 Type:T,B
	 */
	@Column(name = "GATE_TYPE", length = 1)
	private String gateType;

	/**
	 * 登机门容积
	 */
	@Column(name = "GATE_CAPACITY", length = 3)
	private Long gateCapacity;

	/**
	 * 登机门描述说明
	 */
	@Column(name = "GATE_DESCRIPTION", length = 100)
	private String gateDescription;

	/**
	 * 是否中转登机门
	 */
	@Column(name = "GATE_IS_TRANSFER", length = 1)
	private String gateIsTransfer;

	/**
	 * 关联登机门的号码
	 */
	@Column(name = "ASSOCIATED_GATE_CODE", length = 5)
	private String associatedGateCode;

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

	public String getGateCode() {
		return gateCode;
	}

	public void setGateCode(String gateCode) {
		this.gateCode = gateCode;
	}

	public String getGateTerminal() {
		return gateTerminal;
	}

	public void setGateTerminal(String gateTerminal) {
		this.gateTerminal = gateTerminal;
	}

	public String getGateType() {
		return gateType;
	}

	public void setGateType(String gateType) {
		this.gateType = gateType;
	}

	public Long getGateCapacity() {
		return gateCapacity;
	}

	public void setGateCapacity(Long gateCapacity) {
		this.gateCapacity = gateCapacity;
	}

	public String getGateDescription() {
		return gateDescription;
	}

	public void setGateDescription(String gateDescription) {
		this.gateDescription = gateDescription;
	}

	public String getGateIsTransfer() {
		return gateIsTransfer;
	}

	public void setGateIsTransfer(String gateIsTransfer) {
		this.gateIsTransfer = gateIsTransfer;
	}

	public String getAssociatedGateCode() {
		return associatedGateCode;
	}

	public void setAssociatedGateCode(String associatedGateCode) {
		this.associatedGateCode = associatedGateCode;
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

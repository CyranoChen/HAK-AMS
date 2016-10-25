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
 * 延迟
 */
@Entity
@Table(name = "BASE_DELAY_CODE")
public class DelayCode implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	// adder:zhuhaijian,20130701
	@Column(name = "Basic_Data_ID", length = 20)
	private String basicDataID;

	/**
	 * 延迟编码
	 */
	@Column(name = "DELAY_CODE", length = 3, unique = true)
	private String delayCode;

	/**
	 * 延迟原因
	 */
	@Column(name = "DELAY_REASON", length = 400)
	private String delayReason;

	/**
	 * 延迟子码
	 */
	@Column(name = "DELAY_SUB_CODE", length = 10)
	private String delaySubCode;

	/**
	 * 延迟子码原因
	 */
	@Column(name = "DELAY_SUB_REASON", length = 400)
	private String delaySubReason;

	@Column(name = "REMOVE_FLAG", length = 1)
	private String removeFlag;

	@Column(name = "CREATE_USER", length = 20)
	private String createUser;

	@Column(name = "CREATE_TIME")
	@Temporal(TIMESTAMP)
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

	public String getDelayCode() {
		return delayCode;
	}

	public void setDelayCode(String delayCode) {
		this.delayCode = delayCode;
	}

	public String getDelayReason() {
		return delayReason;
	}

	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}

	public String getDelaySubCode() {
		return delaySubCode;
	}

	public void setDelaySubCode(String delaySubCode) {
		this.delaySubCode = delaySubCode;
	}

	public String getDelaySubReason() {
		return delaySubReason;
	}

	public void setDelaySubReason(String delaySubReason) {
		this.delaySubReason = delaySubReason;
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

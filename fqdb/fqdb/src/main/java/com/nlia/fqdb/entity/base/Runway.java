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
 * 跑道
 */
@Entity
@Table(name = "BASE_RUNWAY")
public class Runway implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	// adder:zhuhaijian,20130701
	@Column(name = "Basic_Data_ID", length = 20)
	private String basicDataID;
	
	/**
	 * 跑道号
	 */
	@Column(name = "RUNWAY_CODE", length = 5, unique = true)
	private String runwayCode;

	/**
	 * 跑道描述说明
	 */
	@Column(name = "RUNWAY_DESCRIPTION", length = 100)
	private String runwayDescription;

	/**
	 * 跑道长度
	 */
	@Column(name = "RUNWAY_LENGTH", length = 6)
	private Long runwayLength;

	/**
	 * 跑道宽度
	 */
	@Column(name = "RUNWAY_WIDTH", length = 6)
	private Long runwayWidth;

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

	public String getRunwayCode() {
		return runwayCode;
	}

	public void setRunwayCode(String runwayCode) {
		this.runwayCode = runwayCode;
	}

	public String getRunwayDescription() {
		return runwayDescription;
	}

	public void setRunwayDescription(String runwayDescription) {
		this.runwayDescription = runwayDescription;
	}

	public Long getRunwayLength() {
		return runwayLength;
	}

	public void setRunwayLength(Long runwayLength) {
		this.runwayLength = runwayLength;
	}

	public Long getRunwayWidth() {
		return runwayWidth;
	}

	public void setRunwayWidth(Long runwayWidth) {
		this.runwayWidth = runwayWidth;
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

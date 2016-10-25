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
 * 值机柜台
 */
@Entity
@Table(name = "BASE_CHECK_IN_DESK")
public class CheckInDesk implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	

	// adder:zhuhaijian,20130701
	@Column(name = "Basic_Data_ID", length = 20)
	private String basicDataID;

	/**
	 * 值机柜台编码
	 */
	@Column(name = "CHECK_IN_DESK_CODE", length = 5, unique = true)
	private String checkInDeskCode;

	/**
	 * 值机柜台属于候机楼
	 */
	@Column(name = "CHECK_IN_DESK_TERMINAL", length = 5)
	private String checkInDeskTerminal;

	/**
	 * 值机柜台类型
	 */
	@Column(name = "CHECK_IN_DESK_TYPE", length = 10)
	private String checkInDeskType;

	/**
	 * 值机柜台描述
	 */
	@Column(name = "CHECK_IN_DESK_DESCRIPTION", length = 100)
	private String checkInDeskDescription;

	/**
	 * 值机柜台容积
	 */
	@Column(name = "CHECK_IN_DESK_CAPACITY", length = 3)
	private Long checkInDeskCapacity;

	/**
	 * 值机柜台服务类型 Type:F,C
	 */
	@Column(name = "CHECK_IN_DESK_SERVICE_TYPE", length = 1)
	private String checkInDeskServiceType;

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

	public String getCheckInDeskCode() {
		return checkInDeskCode;
	}

	public void setCheckInDeskCode(String checkInDeskCode) {
		this.checkInDeskCode = checkInDeskCode;
	}

	public String getCheckInDeskTerminal() {
		return checkInDeskTerminal;
	}

	public void setCheckInDeskTerminal(String checkInDeskTerminal) {
		this.checkInDeskTerminal = checkInDeskTerminal;
	}

	public String getCheckInDeskType() {
		return checkInDeskType;
	}

	public void setCheckInDeskType(String checkInDeskType) {
		this.checkInDeskType = checkInDeskType;
	}

	public String getCheckInDeskDescription() {
		return checkInDeskDescription;
	}

	public void setCheckInDeskDescription(String checkInDeskDescription) {
		this.checkInDeskDescription = checkInDeskDescription;
	}

	public Long getCheckInDeskCapacity() {
		return checkInDeskCapacity;
	}

	public void setCheckInDeskCapacity(Long checkInDeskCapacity) {
		this.checkInDeskCapacity = checkInDeskCapacity;
	}

	public String getCheckInDeskServiceType() {
		return checkInDeskServiceType;
	}

	public void setCheckInDeskServiceType(String checkInDeskServiceType) {
		this.checkInDeskServiceType = checkInDeskServiceType;
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

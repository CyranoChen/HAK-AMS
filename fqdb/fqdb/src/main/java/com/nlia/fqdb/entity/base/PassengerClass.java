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
 * 客运
 */
@Entity
@Table(name = "BASE_PASSENGER_CLASS")
public class PassengerClass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	// adder:zhuhaijian,20130701
	@Column(name = "Basic_Data_ID", length = 20)
	private String basicDataID;

	/**
	 * 客运编码
	 */
	@Column(name = "PASSENGER_CLASS_CODE", length = 5, unique = true)
	private String passengerClassCode;

	/**
	 * 客运名称
	 */
	@Column(name = "PASSENGER_CLASS_NAME", length = 50)
	private String passengerClassName;

	/**
	 * 客运描述
	 */
	@Column(name = "PASSENGER_CLASS_DESCRIPTION", length = 100)
	private String passengerClassDescription;

	/**
	 * 客运航空
	 */
	@Column(name = "PASSENGER_CLASS_AIRLINE", length = 3)
	private String passengerClassAirline;

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

	public String getPassengerClassCode() {
		return passengerClassCode;
	}

	public void setPassengerClassCode(String passengerClassCode) {
		this.passengerClassCode = passengerClassCode;
	}

	public String getPassengerClassName() {
		return passengerClassName;
	}

	public void setPassengerClassName(String passengerClassName) {
		this.passengerClassName = passengerClassName;
	}

	public String getPassengerClassDescription() {
		return passengerClassDescription;
	}

	public void setPassengerClassDescription(String passengerClassDescription) {
		this.passengerClassDescription = passengerClassDescription;
	}

	public String getPassengerClassAirline() {
		return passengerClassAirline;
	}

	public void setPassengerClassAirline(String passengerClassAirline) {
		this.passengerClassAirline = passengerClassAirline;
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

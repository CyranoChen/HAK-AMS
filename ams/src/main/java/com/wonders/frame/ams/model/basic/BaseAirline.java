package com.wonders.frame.ams.model.basic;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "BASE_AIRLINE")
public class BaseAirline implements ILongIdRemoveFlagModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	// adder:zhuhaijian,20130701
	@Column(name = "Basic_Data_ID", length = 20)
	private String basicDataID;

	/**
	 * 航空公司二字码
	 */
	@Column(name = "AIRLINE_IATA_CODE", length = 2)
	private String airlineIATACode;

	/**
	 * 航空公司三字码
	 */
	@Column(name = "AIRLINE_ICAO_CODE", length = 3)
	private String airlineICAOCode;
	
	/**
	 * 航空公司唯一标识
	 */
	@Column(name = "AIRLINE_UNIQUE_CODE", length = 10, unique = true)
	private String airlineUniqueCode;

	/**
	 * 航空公司名
	 */
	@Column(name = "AIRLINE_NAME", length = 50)
	private String airlineName;

	/**
	 * 航空公司所属国家
	 */
	@Column(name = "AIRLINE_HOME_COUNTRY", length = 50)
	private String airlineHomeCountry;

	/**
	 * 所属联盟
	 */
	@Column(name = "AIRLINE_ALLIANCE_GROUP", length = 50)
	private String airlineAllianceGroup;

	/**
	 * 航空公司描述
	 */
	@Column(name = "AIRLINE_DESCRIPTION", length = 100)
	private String airlineDescription;

	/**
	 * 航空公司代理
	 */
	@Column(name = "AIRLINE_HANDLER", length = 50)
	private String airlineHandler;
	
	@Column(name = "AIRLINE_COUNTRY_TYPE" ,length = 1)
	private String airlineCountryType;
	
	/**
	 * 父航空公司
	 */
	@Column(name = "PARENT_AIRLINE_ID")
	private String parentAirline;

	@Column(name = "REMOVE_FLAG", length = 1)
	private String removeFlag;

	@Column(name = "CREATE_USER", length = 20)
	private String createUser;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@Temporal(TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "MODIFY_USER", length = 20)
	private String modifyUser;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@Temporal(TIMESTAMP)
	@Column(name = "MODIFY_TIME")
	private Date modifyTime;
	@Transient
	private String verifyDescription;
	
//	@Transient
//	private Map<Integer,List<Integer>> errorMessage;
	
	public String getVerifyDescription() {
		return verifyDescription;
	}

	public void setVerifyDescription(String verifyDescription) {
		this.verifyDescription = verifyDescription;
	}

//	public Map<Integer, List<Integer>> getErrorMessage() {
//		return errorMessage;
//	}
//
//	public void setErrorMessage(Map<Integer, List<Integer>> errorMessage) {
//		this.errorMessage = errorMessage;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAirlineIATACode() {
		return airlineIATACode;
	}

	public void setAirlineIATACode(String airlineIATACode) {
		this.airlineIATACode = airlineIATACode;
	}

	public String getAirlineICAOCode() {
		return airlineICAOCode;
	}

	public void setAirlineICAOCode(String airlineICAOCode) {
		this.airlineICAOCode = airlineICAOCode;
	}

	public String getAirlineUniqueCode() {
		return airlineUniqueCode;
	}

	public void setAirlineUniqueCode(String airlineUniqueCode) {
		this.airlineUniqueCode = airlineUniqueCode;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getAirlineHomeCountry() {
		return airlineHomeCountry;
	}

	public void setAirlineHomeCountry(String airlineHomeCountry) {
		this.airlineHomeCountry = airlineHomeCountry;
	}

	public String getAirlineAllianceGroup() {
		return airlineAllianceGroup;
	}

	public void setAirlineAllianceGroup(String airlineAllianceGroup) {
		this.airlineAllianceGroup = airlineAllianceGroup;
	}

	public String getAirlineDescription() {
		return airlineDescription;
	}

	public void setAirlineDescription(String airlineDescription) {
		this.airlineDescription = airlineDescription;
	}

	public String getAirlineHandler() {
		return airlineHandler;
	}

	public void setAirlineHandler(String airlineHandler) {
		this.airlineHandler = airlineHandler;
	}

	public String getParentAirline() {
		return parentAirline;
	}

	public void setParentAirline(String parentAirline) {
		this.parentAirline = parentAirline;
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

	public String getAirlineCountryType() {
		return airlineCountryType;
	}

	public void setAirlineCountryType(String airlineCountryType) {
		this.airlineCountryType = airlineCountryType;
	}
	
}

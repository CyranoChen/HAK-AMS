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

@Entity
@Table(name = "BASE_AIRPORT")
public class Airport implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	// adder:zhuhaijian,20130701
	@Column(name = "Basic_Data_ID", length = 20)
	private String basicDataID;

	/**
	 * 机场三字码
	 */
	@Column(name = "AIRPORT_IATA_CODE", length = 3, unique = true)
	private String airportIATACode;

	/**
	 * 机场四字码
	 */
	@Column(name = "AIRPORT_ICAO_CODE", length = 4, unique = true)
	private String airportICAOCode;

	/**
	 * 机场所在国家
	 */
	@Column(name = "AIRPORT_COUNTRY", length = 5)
	private String airportCountry;

	/**
	 * 机场国家类型
	 */
	@Column(name = "AIRPORT_COUNTRY_TYPE", length = 5)
	private String airportCountryType;

	/**
	 * 机场所在城市
	 */
	@Column(name = "AIRPORT_CITY", length = 30)
	private String airportCity;

	/**
	 * 机场所在区域
	 */
	@Column(name = "AIRPORT_REGION", length = 3)
	private String airportRegion;
	
	/**
	 * 机场所在地区（国内）
	 */
	@Column(name = "AIRPORT_DISTRICT", length = 3)
	private String airportDistrict;

	/**
	 * 机场时区
	 */
	@Column(name = "AIRPORT_TIMEZONE", length = 25)
	private String airportTimezone;

	/**
	 * 机场距离
	 */
	@Column(name = "AIRPORT_DISTANCE", length = 12)
	private Long airportDistance;

	/**
	 * 机场描述
	 */
	@Column(name = "AIRPORT_DESCRIPTION", length = 100)
	private String airportDescription;
	
	/**
	 * 
	 */
	@Column(name = "AIRPORT_ROUTING_NAME", length = 30)
	private String airportRoutingName;

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

	public String getAirportIATACode() {
		return airportIATACode;
	}

	public void setAirportIATACode(String airportIATACode) {
		this.airportIATACode = airportIATACode;
	}

	public String getAirportICAOCode() {
		return airportICAOCode;
	}

	public void setAirportICAOCode(String airportICAOCode) {
		this.airportICAOCode = airportICAOCode;
	}

	public String getAirportCountry() {
		return airportCountry;
	}

	public void setAirportCountry(String airportCountry) {
		this.airportCountry = airportCountry;
	}

	public String getAirportCountryType() {
		return airportCountryType;
	}

	public void setAirportCountryType(String airportCountryType) {
		this.airportCountryType = airportCountryType;
	}

	public String getAirportCity() {
		return airportCity;
	}

	public void setAirportCity(String airportCity) {
		this.airportCity = airportCity;
	}

	public String getAirportRegion() {
		return airportRegion;
	}

	public void setAirportRegion(String airportRegion) {
		this.airportRegion = airportRegion;
	}

	public String getAirportTimezone() {
		return airportTimezone;
	}

	public void setAirportTimezone(String airportTimezone) {
		this.airportTimezone = airportTimezone;
	}

	public Long getAirportDistance() {
		return airportDistance;
	}

	public void setAirportDistance(Long airportDistance) {
		this.airportDistance = airportDistance;
	}

	public String getAirportDescription() {
		return airportDescription;
	}

	public void setAirportDescription(String airportDescription) {
		this.airportDescription = airportDescription;
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

	public String getAirportDistrict() {
		return airportDistrict;
	}

	public void setAirportDistrict(String airportDistrict) {
		this.airportDistrict = airportDistrict;
	}

	public String getAirportRoutingName() {
		return airportRoutingName;
	}

	public void setAirportRoutingName(String airportRoutingName) {
		this.airportRoutingName = airportRoutingName;
	}
	
	/*
	 * add by march 20151124 因HAK而修改
	 */
	@Column(name = "AIRPORT_CHINESE_SHORT_NAME", length = 30)
	private String airportChineseShortName;
	@Column(name = "AIRPORT_CHINESE_FULL_NAME", length = 30)
	private String airportChineseFullName;
	@Column(name = "AIRPORT_ENGLISH_SHORT_NAME", length = 30)
	private String airportEnglishShortName;
	@Column(name = "AIRPORT_ENGLISH_FULL_NAME", length = 30)
	private String airportEnglishFullName;

    public String getAirportChineseShortName() {
        return airportChineseShortName;
    }

    public void setAirportChineseShortName(String airportChineseShortName) {
        this.airportChineseShortName = airportChineseShortName;
    }

    public String getAirportChineseFullName() {
        return airportChineseFullName;
    }

    public void setAirportChineseFullName(String airportChineseFullName) {
        this.airportChineseFullName = airportChineseFullName;
    }

    public String getAirportEnglishShortName() {
        return airportEnglishShortName;
    }

    public void setAirportEnglishShortName(String airportEnglishShortName) {
        this.airportEnglishShortName = airportEnglishShortName;
    }

    public String getAirportEnglishFullName() {
        return airportEnglishFullName;
    }

    public void setAirportEnglishFullName(String airportEnglishFullName) {
        this.airportEnglishFullName = airportEnglishFullName;
    }

    
    /*
     * add by march 20151124 因HAK而修改
     */
}

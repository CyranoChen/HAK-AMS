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
 * 飞机
 */
@Entity
@Table(name = "BASE_AIRCRAFT")
public class Aircraft implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * 租赁航空公司
	 */
	@Column(name = "AIRCRAFT_LEASING_AIRLINE", length = 10)
	private String aircraftLeasingAirline;
	
	/**
	 * 飞机类型三字码
	 */
	@Column(name = "AIRCRAFT_TYPE_ICAO_CODE", length = 10)
	private String aircraftTypeICAOCode;
	
	/**
	 * 飞机类型二字码
	 */
	@Column(name = "AIRCRAFT_TYPE_IATA_CODE", length = 10)
	private String aircraftTypeIATACode;

	/**
	 * 飞机注册号
	 */
	@Column(name = "AIRCRAFT_REGISTRATION", length = 10, unique = true)
	private String aircraftRegistration;

	/**
	 * 飞机类型
	 */
	@Column(name = "AIRCRAFT_TYPE", length = 10)
	private String aircraftType;

	/**
	 * 所属航空公司
	 */
	@Column(name = "AIRCRAFT_AIRLINE", length = 10)
	private String aircraftAirline;

	/**
	 * 飞机描述说明
	 */
	@Column(name = "AIRCRAFT_DESCRIPTION", length = 100)
	private String aircraftDescription;

	/**
	 * 飞机引擎数量
	 */
	@Column(name = "ARICRAFT_ENGINE_NUMBER", length = 2)
	private Long aircraftEngineNumber;

	/**
	 * 最大起飞重量
	 */
	@Column(name = "AIRCRAFT_TAKEOFF_WEIGHT", length = 6)
	private Long aircraftTakeoffWeight;

	/**
	 * 最大落地重量
	 */
	@Column(name = "AIRCRAFT_LANDING_WEIGHT", length = 6)
	private Long aircraftLandingWeight;

	/**
	 * 最大配载数
	 */
	@Column(name = "AIRCRAFT_PAYLOAD", length = 6)
	private Long aircraftPayload;

	/**
	 * 最大客座数
	 */
	@Column(name = "AIRCRAFT_SEAT_CAPACITY", length = 3)
	private Long aircraftSeatCapacity;

	/**
	 * 噪音级别
	 */
	@Column(name = "AIRCRAFT_NOISE_CATEGORY", length = 2)
	private String aircraftNoiseCategory;

	/**
	 * 飞机高度
	 */
	@Column(name = "AIRCRAFT_HEIGHT", length = 12)
	private Long aircraftHeight;

	/**
	 * 飞机长度
	 */
	@Column(name = "AIRCRAFT_LENGHT", length = 12)
	private Long aircraftLength;

	/**
	 * 飞机宽度
	 */
	@Column(name = "AIRCRAFT_WIDTH", length = 12)
	private Long aircraftWidth;

	@Column(name = "REMOVE_FLAG", length = 1)
	private String removeFlag;

	@Column(name = "CREATE_USER", length = 20)
	private String createUser;

	@Temporal(TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "MODIFY_USER", length = 20)
	private String modifyUser;
	
	// adder:zhuhaijian,20130701
	@Column(name = "Basic_Data_ID", length = 20)
	private String basicDataID;

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

	public String getAircraftRegistration() {
		return aircraftRegistration;
	}

	public void setAircraftRegistration(String aircraftRegistration) {
		this.aircraftRegistration = aircraftRegistration;
	}

	public String getAircraftType() {
		return aircraftType;
	}

	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}

	public String getAircraftAirline() {
		return aircraftAirline;
	}

	public void setAircraftAirline(String aircraftAirline) {
		this.aircraftAirline = aircraftAirline;
	}

	public String getAircraftDescription() {
		return aircraftDescription;
	}

	public void setAircraftDescription(String aircraftDescription) {
		this.aircraftDescription = aircraftDescription;
	}

	public Long getAircraftEngineNumber() {
		return aircraftEngineNumber;
	}

	public void setAircraftEngineNumber(Long aircraftEngineNumber) {
		this.aircraftEngineNumber = aircraftEngineNumber;
	}

	public Long getAircraftTakeoffWeight() {
		return aircraftTakeoffWeight;
	}

	public void setAircraftTakeoffWeight(Long aircraftTakeoffWeight) {
		this.aircraftTakeoffWeight = aircraftTakeoffWeight;
	}

	public Long getAircraftLandingWeight() {
		return aircraftLandingWeight;
	}

	public void setAircraftLandingWeight(Long aircraftLandingWeight) {
		this.aircraftLandingWeight = aircraftLandingWeight;
	}

	public Long getAircraftPayload() {
		return aircraftPayload;
	}

	public void setAircraftPayload(Long aircraftPayload) {
		this.aircraftPayload = aircraftPayload;
	}

	public Long getAircraftSeatCapacity() {
		return aircraftSeatCapacity;
	}

	public void setAircraftSeatCapacity(Long aircraftSeatCapacity) {
		this.aircraftSeatCapacity = aircraftSeatCapacity;
	}

	public String getAircraftNoiseCategory() {
		return aircraftNoiseCategory;
	}

	public void setAircraftNoiseCategory(String aircraftNoiseCategory) {
		this.aircraftNoiseCategory = aircraftNoiseCategory;
	}

	public Long getAircraftHeight() {
		return aircraftHeight;
	}

	public void setAircraftHeight(Long aircraftHeight) {
		this.aircraftHeight = aircraftHeight;
	}

	public Long getAircraftLength() {
		return aircraftLength;
	}

	public void setAircraftLength(Long aircraftLength) {
		this.aircraftLength = aircraftLength;
	}

	public Long getAircraftWidth() {
		return aircraftWidth;
	}

	public void setAircraftWidth(Long aircraftWidth) {
		this.aircraftWidth = aircraftWidth;
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
	

	public String getAircraftLeasingAirline() {
		return aircraftLeasingAirline;
	}

	public void setAircraftLeasingAirline(String aircraftLeasingAirline) {
		this.aircraftLeasingAirline = aircraftLeasingAirline;
	}

	public String getAircraftTypeICAOCode() {
		return aircraftTypeICAOCode;
	}

	public void setAircraftTypeICAOCode(String aircraftTypeICAOCode) {
		this.aircraftTypeICAOCode = aircraftTypeICAOCode;
	}

	public String getAircraftTypeIATACode() {
		return aircraftTypeIATACode;
	}

	public void setAircraftTypeIATACode(String aircraftTypeIATACode) {
		this.aircraftTypeIATACode = aircraftTypeIATACode;
	}
	
	 /*
     * 宽窄体标志
     */
    @Column(name = "IS_WIDE_OR_NARROW", length = 20)
    private String isWideOrNarrow;
    
    public String getIsWideOrNarrow() {
        return isWideOrNarrow;
    }

    public void setIsWideOrNarrow(String isWideOrNarrow) {
        this.isWideOrNarrow = isWideOrNarrow;
    }
}

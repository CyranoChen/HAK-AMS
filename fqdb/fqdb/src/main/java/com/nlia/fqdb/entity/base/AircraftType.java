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
 *  机型
 */
@Entity
@Table(name = "BASE_AIRCRAFT_TYPE")
public class AircraftType implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	// adder:zhuhaijian,20130701
	@Column(name = "Basic_Data_ID", length = 20)
	private String basicDataID;
	
	@Column(name = "AIRCRAFT_TYPE_IATA_CODE", length = 3 , unique = true)
	private String aircraftTypeIATACode;
	
	@Column(name = "AIRCRAFT_TYPE_ICAO_CODE", length = 4 )
	private String aircraftTypeICAOCode;
	
	@Column(name = "AIRCRAFT_SUB_TYPE_IATA_CODE", length = 3)
	private String aircraftSubTypeIATACode;
	 
	@Column(name = "AIRCRAFT_MANUFACTORY", length = 50)
	private String aircraftManufactory;
	
	@Column(name = "AIRCRAFT_ENGINE_NUMBER", length = 2)
	private Integer aircraftEngineNumber;
	
	@Column(name = "AIRCRAFT_HEIGHT", length = 9)
	private Integer aircraftHeight;
	
	@Column(name = "AIRCRAFT_LENGHT", length = 9)
	private Integer aircraftLength;
	
	@Column(name = "AIRCRAFT_WIDTH", length = 9)
	private Integer aircraftWidth;
	
	@Column(name = "AIRCRAFT_TAKEOFF_WEIGHT", length = 6)
	private Integer aircraftTakeoffWeight;
	
	

	@Column(name = "AIRCRAFT_SEAT_CAPACITY", length = 3)
	private Integer aircraftSeatCapacity;
	
	@Column(name = "AIRCRAFT_PAYLOAD", length = 6)
	private Integer aircraftPayload;
	
	@Column(name = "AIRCRAFT_LANDING_WEIGHT", length = 6)
	private Integer aircraftLandingWeight;
	
	@Column(name = "AIRCRAFT_TYPE_DESCRIPTION", length = 2000)
	private String aircraftTypeDescription;
	
	@Column(name = "AIRCRAFT_NOISE_CATEGORY", length = 2)
	private String aircraftNoiseCategory;
	
	/*
	 * indicator of aircraft need boarding bridge or not: 'Y'- require board bridge; 'N'- does not require board bridge.
	 */
	@Column(name = "BOARDING_BRIDGE_REQUIRED", length = 1)
	private String boardingBridgeRequired;
	
	@Column(name = "AIRCRAFT_MODE", length = 20)
	private String aircraftMode;
	
	/*
	 * ICAO size category, e.g. A,B,C,D,E. Defined in SY_RANGEDICT "ACTSizeCatogory".
	 */
	@Column(name = "AIRCRAFT_SIZA_CATEGORY", length = 1)
	private String aircraftSizaCategory;
	

	

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

	public Integer getAircraftEngineNumber() {
		return aircraftEngineNumber;
	}

	public Integer getAircraftHeight() {
		return aircraftHeight;
	}

	public Integer getAircraftLength() {
		return aircraftLength;
	}

	public Integer getAircraftWidth() {
		return aircraftWidth;
	}

	public Integer getAircraftTakeoffWeight() {
		return aircraftTakeoffWeight;
	}

	public Integer getAircraftSeatCapacity() {
		return aircraftSeatCapacity;
	}

	public Integer getAircraftPayload() {
		return aircraftPayload;
	}

	public Integer getAircraftLandingWeight() {
		return aircraftLandingWeight;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setAircraftEngineNumber(Integer aircraftEngineNumber) {
		this.aircraftEngineNumber = aircraftEngineNumber;
	}

	public void setAircraftHeight(Integer aircraftHeight) {
		this.aircraftHeight = aircraftHeight;
	}

	public void setAircraftLength(Integer aircraftLength) {
		this.aircraftLength = aircraftLength;
	}

	public void setAircraftWidth(Integer aircraftWidth) {
		this.aircraftWidth = aircraftWidth;
	}

	public void setAircraftTakeoffWeight(Integer aircarftTakeoffWeight) {
		this.aircraftTakeoffWeight = aircarftTakeoffWeight;
	}

	public void setAircraftSeatCapacity(Integer aircraftSeatCapacity) {
		this.aircraftSeatCapacity = aircraftSeatCapacity;
	}

	public void setAircraftPayload(Integer aircarftPayload) {
		this.aircraftPayload = aircarftPayload;
	}

	public void setAircraftLandingWeight(Integer aircarftLandingWeight) {
		this.aircraftLandingWeight = aircarftLandingWeight;
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
	
	public String getAircraftTypeIATACode() {
		return aircraftTypeIATACode;
	}

	public void setAircraftTypeIATACode(String aircraftTypeIATACode) {
		this.aircraftTypeIATACode = aircraftTypeIATACode;
	}

	public String getAircraftTypeICAOCode() {
		return aircraftTypeICAOCode;
	}

	public void setAircraftTypeICAOCode(String aircraftTypeICAOCode) {
		this.aircraftTypeICAOCode = aircraftTypeICAOCode;
	}

	public String getAircraftSubTypeIATACode() {
		return aircraftSubTypeIATACode;
	}

	public void setAircraftSubTypeIATACode(String aircraftSubTypeIATACode) {
		this.aircraftSubTypeIATACode = aircraftSubTypeIATACode;
	}

	public String getAircraftManufactory() {
		return aircraftManufactory;
	}

	public void setAircraftManufactory(String aircraftManufactory) {
		this.aircraftManufactory = aircraftManufactory;
	}


	

	public String getAircraftTypeDescription() {
		return aircraftTypeDescription;
	}

	public void setAircraftTypeDescription(String aircraftTypeDescription) {
		this.aircraftTypeDescription = aircraftTypeDescription;
	}

	public String getAircraftNoiseCategory() {
		return aircraftNoiseCategory;
	}

	public void setAircraftNoiseCategory(String aircraftNoiseCategory) {
		this.aircraftNoiseCategory = aircraftNoiseCategory;
	}

	public String getBoardingBridgeRequired() {
		return boardingBridgeRequired;
	}

	public void setBoardingBridgeRequired(String boardingBridgeRequired) {
		this.boardingBridgeRequired = boardingBridgeRequired;
	}

	public String getAircraftMode() {
		return aircraftMode;
	}

	public void setAircraftMode(String aircraftMode) {
		this.aircraftMode = aircraftMode;
	}

	public String getAircraftSizaCategory() {
		return aircraftSizaCategory;
	}

	public void setAircraftSizaCategory(String aircraftSizaCategory) {
		this.aircraftSizaCategory = aircraftSizaCategory;
	}

	public String getBasicDataID() {
		return basicDataID;
	}

	public void setBasicDataID(String basicDataID) {
		this.basicDataID = basicDataID;
	}
}

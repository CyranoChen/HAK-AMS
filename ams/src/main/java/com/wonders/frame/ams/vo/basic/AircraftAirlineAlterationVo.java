package com.wonders.frame.ams.vo.basic;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AircraftAirlineAlterationVo {
	private Long id;
	
	private Long originalAirline;

	private Long originalSubairline;

	private Long currentAirline;

	private Long currentSubairline;
	
	private String aircraftRegistration;
	
	private String airlineOfFlight;

	private Date startDate;

	private Date endDate;
	
	private String remark;

	private String removeFlag;
	
	private String createUser;

	private Date createTime;

	private String modifyUser;

	private Date modifyTime;
	
	private String aircraftLeasingAirline;
	
	private String aircraftTypeICAOCode;
	
	private String aircraftTypeIATACode;
	
	private String aircraftType;
	
	private String aircraftAirline;
	
	private String aircraftDescription;
	
	private Long aircraftEngineNumber;
	
	private Long aircraftTakeoffWeight;
	
	private Long aircraftLandingWeight;
	
	private Long aircraftPayload;
	
	private Long aircraftSeatCapacity;
	
	private String aircraftNoiseCategory;
	
	private Long aircraftHeight;
	
	private Long aircraftLength;
	
	private Long aircraftWidth;
	
	private String basicDataID;
	
	private String verifyDescription;
	
	private String isWideOrNarrow;
	
	private String isHighDensity;
	
	private String isPackagingFacility;


	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOriginalAirline() {
		return originalAirline;
	}

	public void setOriginalAirline(Long originalAirline) {
		this.originalAirline = originalAirline;
	}

	public Long getOriginalSubairline() {
		return originalSubairline;
	}

	public void setOriginalSubairline(Long originalSubairline) {
		this.originalSubairline = originalSubairline;
	}

	public Long getCurrentAirline() {
		return currentAirline;
	}

	public void setCurrentAirline(Long currentAirline) {
		this.currentAirline = currentAirline;
	}

	public Long getCurrentSubairline() {
		return currentSubairline;
	}

	public void setCurrentSubairline(Long currentSubairline) {
		this.currentSubairline = currentSubairline;
	}

	public String getAircraftRegistration() {
		return aircraftRegistration;
	}

	public void setAircraftRegistration(String aircraftRegistration) {
		this.aircraftRegistration = aircraftRegistration;
	}

	public String getAirlineOfFlight() {
		return airlineOfFlight;
	}

	public void setAirlineOfFlight(String airlineOfFlight) {
		this.airlineOfFlight = airlineOfFlight;
	}
	@JsonFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@Temporal(TIMESTAMP)
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
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@Temporal(TIMESTAMP)
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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

	public String getBasicDataID() {
		return basicDataID;
	}

	public void setBasicDataID(String basicDataID) {
		this.basicDataID = basicDataID;
	}

	public String getVerifyDescription() {
		return verifyDescription;
	}

	public void setVerifyDescription(String verifyDescription) {
		this.verifyDescription = verifyDescription;
	}

	public String getIsWideOrNarrow() {
		return isWideOrNarrow;
	}

	public void setIsWideOrNarrow(String isWideOrNarrow) {
		this.isWideOrNarrow = isWideOrNarrow;
	}

	public String getIsHighDensity() {
		return isHighDensity;
	}

	public void setIsHighDensity(String isHighDensity) {
		this.isHighDensity = isHighDensity;
	}

	public String getIsPackagingFacility() {
		return isPackagingFacility;
	}

	public void setIsPackagingFacility(String isPackagingFacility) {
		this.isPackagingFacility = isPackagingFacility;
	}

}

package com.tsystems.nlia.fqdb.entity;

import java.io.Serializable;
import java.util.Date;

public class FlightResource implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private FlightBase flightBase;
	
	private String flightTerminalID;
	
	private String aircraftTerminalID;
	
	private String runwayID;
	
	private String standID;
	
	private Date scheduledStandStartDateTime;
	
	private Date scheduledStandEndDateTime;
	
	private Date estimatedStandStartDateTime;
	
	private Date estimcatedStandEndDateTime;
	
	private Date actualStandStartDateTime;
	

	private Date actualStandEndDateTime;
	
	private String baggageBeltID;
	
	private String baggageBeltStatus;
	
	private Date scheduledMakeupStartDateTime;
	
	private Date scheduledMakeupEndDateTime;
	
	private Date actualMakeupStartDateTime;
	
	private Date actualMakeupEndDateTime;
	
	private String gateID;
	
	private String gateStatus;
	
	private Date scheduledGateStartDateTime;
	
	private Date scheduledGateEndDateTime;
	
	private Date actualGateStartDateTime;
	
	private Date actualGateEndDateTime;
	
	private String baggageReclaimID;
	
	private Date scheduledFirstBaggageDateTime;
	
	private Date scheduledLastBaggageDateTime;
	
	private Date actualFirstBaggageDateTime;
	
	private Date actualLastBaggageDateTime;
	
	private String overViewCheckInRange;
	
	private String overViewCheckInType;
	
	private Date scheduledCheckInFirstBeginDateTime;
	
	private Date scheduledCheckInLastEndDateTime;
	
	private Date actualCheckInFirstBeginDateTime;
	
	private Date acutalCheckInLastEndDateTime;
	
	private String checkInID;
	
	private String checkInType;
	
	private String checkInStatus;
	
	private String checkInClassService;
	
	private Date scheduledCheckInBeginDateTime;
	
	private Date scheduledCheckInEndDateTime;
	
	private Date actualCheckInBeginDateTime;
	
	private Date acutalCheckInEndDateTime;
	
	private Date closingScheduledFromDateTime;
	
	private Date closingScheduledEndDateTime;
	
	private Date closingActualEndDateTime;
	
	private String commentFreeText;
	
	private Long id;

	private String removeFlag;
	
	private String createUser;
	
	private Date createTime;
	
	private String modifyUser;

	private Date modifyTime;

	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public FlightBase getFlightBase() {
		return flightBase;
	}

	public void setFlightBase(FlightBase flightBase) {
		this.flightBase = flightBase;
	}

	public String getFlightTerminalID() {
		return flightTerminalID;
	}

	public void setFlightTerminalID(String flightTerminalID) {
		this.flightTerminalID = flightTerminalID;
	}

	public String getAircraftTerminalID() {
		return aircraftTerminalID;
	}

	public void setAircraftTerminalID(String aircraftTerminalID) {
		this.aircraftTerminalID = aircraftTerminalID;
	}

	public String getRunwayID() {
		return runwayID;
	}

	public void setRunwayID(String runwayID) {
		this.runwayID = runwayID;
	}

	public String getStandID() {
		return standID;
	}

	public void setStandID(String standID) {
		this.standID = standID;
	}

	public Date getScheduledStandStartDateTime() {
		return scheduledStandStartDateTime;
	}

	public void setScheduledStandStartDateTime(Date scheduledStandStartDateTime) {
		this.scheduledStandStartDateTime = scheduledStandStartDateTime;
	}

	public Date getScheduledStandEndDateTime() {
		return scheduledStandEndDateTime;
	}

	public void setScheduledStandEndDateTime(Date scheduledStandEndDateTime) {
		this.scheduledStandEndDateTime = scheduledStandEndDateTime;
	}

	public Date getEstimatedStandStartDateTime() {
		return estimatedStandStartDateTime;
	}

	public void setEstimatedStandStartDateTime(Date estimatedStandStartDateTime) {
		this.estimatedStandStartDateTime = estimatedStandStartDateTime;
	}

	public Date getEstimcatedStandEndDateTime() {
		return estimcatedStandEndDateTime;
	}

	public void setEstimcatedStandEndDateTime(Date estimcatedStandEndDateTime) {
		this.estimcatedStandEndDateTime = estimcatedStandEndDateTime;
	}

	public Date getActualStandStartDateTime() {
		return actualStandStartDateTime;
	}

	public void setActualStandStartDateTime(Date actualStandStartDateTime) {
		this.actualStandStartDateTime = actualStandStartDateTime;
	}

	public Date getActualStandEndDateTime() {
		return actualStandEndDateTime;
	}

	public void setActualStandEndDateTime(Date actualStandEndDateTime) {
		this.actualStandEndDateTime = actualStandEndDateTime;
	}

	public String getBaggageBeltID() {
		return baggageBeltID;
	}

	public void setBaggageBeltID(String baggageBeltID) {
		this.baggageBeltID = baggageBeltID;
	}

	public String getBaggageBeltStatus() {
		return baggageBeltStatus;
	}

	public void setBaggageBeltStatus(String baggageBeltStatus) {
		this.baggageBeltStatus = baggageBeltStatus;
	}

	public Date getScheduledMakeupStartDateTime() {
		return scheduledMakeupStartDateTime;
	}

	public void setScheduledMakeupStartDateTime(Date scheduledMakeupStartDateTime) {
		this.scheduledMakeupStartDateTime = scheduledMakeupStartDateTime;
	}

	public Date getScheduledMakeupEndDateTime() {
		return scheduledMakeupEndDateTime;
	}

	public void setScheduledMakeupEndDateTime(Date scheduledMakeupEndDateTime) {
		this.scheduledMakeupEndDateTime = scheduledMakeupEndDateTime;
	}

	public Date getActualMakeupStartDateTime() {
		return actualMakeupStartDateTime;
	}

	public void setActualMakeupStartDateTime(Date actualMakeupStartDateTime) {
		this.actualMakeupStartDateTime = actualMakeupStartDateTime;
	}

	public Date getActualMakeupEndDateTime() {
		return actualMakeupEndDateTime;
	}

	public void setActualMakeupEndDateTime(Date actualMakeupEndDateTime) {
		this.actualMakeupEndDateTime = actualMakeupEndDateTime;
	}

	public String getGateID() {
		return gateID;
	}

	public void setGateID(String gateID) {
		this.gateID = gateID;
	}

	public String getGateStatus() {
		return gateStatus;
	}

	public void setGateStatus(String gateStatus) {
		this.gateStatus = gateStatus;
	}

	public Date getScheduledGateStartDateTime() {
		return scheduledGateStartDateTime;
	}

	public void setScheduledGateStartDateTime(Date scheduledGateStartDateTime) {
		this.scheduledGateStartDateTime = scheduledGateStartDateTime;
	}

	public Date getScheduledGateEndDateTime() {
		return scheduledGateEndDateTime;
	}

	public void setScheduledGateEndDateTime(Date scheduledGateEndDateTime) {
		this.scheduledGateEndDateTime = scheduledGateEndDateTime;
	}

	public Date getActualGateStartDateTime() {
		return actualGateStartDateTime;
	}

	public void setActualGateStartDateTime(Date actualGateStartDateTime) {
		this.actualGateStartDateTime = actualGateStartDateTime;
	}

	public Date getActualGateEndDateTime() {
		return actualGateEndDateTime;
	}

	public void setActualGateEndDateTime(Date actualGateEndDateTime) {
		this.actualGateEndDateTime = actualGateEndDateTime;
	}

	public String getBaggageReclaimID() {
		return baggageReclaimID;
	}

	public void setBaggageReclaimID(String baggageReclaimID) {
		this.baggageReclaimID = baggageReclaimID;
	}

	public Date getScheduledFirstBaggageDateTime() {
		return scheduledFirstBaggageDateTime;
	}

	public void setScheduledFirstBaggageDateTime(Date scheduledFirstBaggageDateTime) {
		this.scheduledFirstBaggageDateTime = scheduledFirstBaggageDateTime;
	}

	public Date getScheduledLastBaggageDateTime() {
		return scheduledLastBaggageDateTime;
	}

	public void setScheduledLastBaggageDateTime(Date scheduledLastBaggageDateTime) {
		this.scheduledLastBaggageDateTime = scheduledLastBaggageDateTime;
	}

	public Date getActualFirstBaggageDateTime() {
		return actualFirstBaggageDateTime;
	}

	public void setActualFirstBaggageDateTime(Date actualFirstBaggageDateTime) {
		this.actualFirstBaggageDateTime = actualFirstBaggageDateTime;
	}

	public Date getActualLastBaggageDateTime() {
		return actualLastBaggageDateTime;
	}

	public void setActualLastBaggageDateTime(Date actualLastBaggageDateTime) {
		this.actualLastBaggageDateTime = actualLastBaggageDateTime;
	}

	public String getOverViewCheckInRange() {
		return overViewCheckInRange;
	}

	public void setOverViewCheckInRange(String overViewCheckInRange) {
		this.overViewCheckInRange = overViewCheckInRange;
	}

	public String getOverViewCheckInType() {
		return overViewCheckInType;
	}

	public void setOverViewCheckInType(String overViewCheckInType) {
		this.overViewCheckInType = overViewCheckInType;
	}

	public Date getScheduledCheckInFirstBeginDateTime() {
		return scheduledCheckInFirstBeginDateTime;
	}

	public void setScheduledCheckInFirstBeginDateTime(
			Date scheduledCheckInFirstBeginDateTime) {
		this.scheduledCheckInFirstBeginDateTime = scheduledCheckInFirstBeginDateTime;
	}

	public Date getScheduledCheckInLastEndDateTime() {
		return scheduledCheckInLastEndDateTime;
	}

	public void setScheduledCheckInLastEndDateTime(
			Date scheduledCheckInLastEndDateTime) {
		this.scheduledCheckInLastEndDateTime = scheduledCheckInLastEndDateTime;
	}

	public Date getActualCheckInFirstBeginDateTime() {
		return actualCheckInFirstBeginDateTime;
	}

	public void setActualCheckInFirstBeginDateTime(
			Date actualCheckInFirstBeginDateTime) {
		this.actualCheckInFirstBeginDateTime = actualCheckInFirstBeginDateTime;
	}

	public Date getAcutalCheckInLastEndDateTime() {
		return acutalCheckInLastEndDateTime;
	}

	public void setAcutalCheckInLastEndDateTime(Date acutalCheckInLastEndDateTime) {
		this.acutalCheckInLastEndDateTime = acutalCheckInLastEndDateTime;
	}

	public String getCheckInID() {
		return checkInID;
	}

	public void setCheckInID(String checkInID) {
		this.checkInID = checkInID;
	}

	public String getCheckInType() {
		return checkInType;
	}

	public void setCheckInType(String checkInType) {
		this.checkInType = checkInType;
	}

	public String getCheckInStatus() {
		return checkInStatus;
	}

	public void setCheckInStatus(String checkInStatus) {
		this.checkInStatus = checkInStatus;
	}

	public String getCheckInClassService() {
		return checkInClassService;
	}

	public void setCheckInClassService(String checkInClassService) {
		this.checkInClassService = checkInClassService;
	}

	public Date getScheduledCheckInBeginDateTime() {
		return scheduledCheckInBeginDateTime;
	}

	public void setScheduledCheckInBeginDateTime(Date scheduledCheckInBeginDateTime) {
		this.scheduledCheckInBeginDateTime = scheduledCheckInBeginDateTime;
	}

	public Date getScheduledCheckInEndDateTime() {
		return scheduledCheckInEndDateTime;
	}

	public void setScheduledCheckInEndDateTime(Date scheduledCheckInEndDateTime) {
		this.scheduledCheckInEndDateTime = scheduledCheckInEndDateTime;
	}

	public Date getActualCheckInBeginDateTime() {
		return actualCheckInBeginDateTime;
	}

	public void setActualCheckInBeginDateTime(Date actualCheckInBeginDateTime) {
		this.actualCheckInBeginDateTime = actualCheckInBeginDateTime;
	}

	public Date getAcutalCheckInEndDateTime() {
		return acutalCheckInEndDateTime;
	}

	public void setAcutalCheckInEndDateTime(Date acutalCheckInEndDateTime) {
		this.acutalCheckInEndDateTime = acutalCheckInEndDateTime;
	}

	public Date getClosingScheduledFromDateTime() {
		return closingScheduledFromDateTime;
	}

	public void setClosingScheduledFromDateTime(Date closingScheduledFromDateTime) {
		this.closingScheduledFromDateTime = closingScheduledFromDateTime;
	}

	public Date getClosingScheduledEndDateTime() {
		return closingScheduledEndDateTime;
	}

	public void setClosingScheduledEndDateTime(Date closingScheduledEndDateTime) {
		this.closingScheduledEndDateTime = closingScheduledEndDateTime;
	}

	public Date getClosingActualEndDateTime() {
		return closingActualEndDateTime;
	}

	public void setClosingActualEndDateTime(Date closingActualEndDateTime) {
		this.closingActualEndDateTime = closingActualEndDateTime;
	}

	public String getCommentFreeText() {
		return commentFreeText;
	}

	public void setCommentFreeText(String commentFreeText) {
		this.commentFreeText = commentFreeText;
	}

}

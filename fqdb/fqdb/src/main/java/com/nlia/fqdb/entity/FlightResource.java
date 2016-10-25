package com.nlia.fqdb.entity;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "FLIGHT_RESOURCE")
public class FlightResource implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/*航班实体主键*/
	@OneToOne
	@JoinColumn(name = "FLIGHT_BASE_ID")
	private FlightBase flightBase;

	//2013-09-18  IDD修改 begin
	/**
	 * 第一件行李时间
	 */
	@Column(name = "FIRST_BAGGAGE_DATE_TIME")
	@Temporal(TIMESTAMP)
	private Date firstBaggageDateTime;
	
	/**
	 * 最后一件行李时间
	 */
	@Column(name = "LAST_BAGGAGE_DATE_TIME")
	@Temporal(TIMESTAMP)
	private Date lastBaggageDateTime;
	
	//2013-09-18  IDD修改 end
	
	@Column(name = "FLIGHT_TERMINAL_ID", length = 20)
	private String flightTerminalID;
	
	public Date getFirstBaggageDateTime() {
		return firstBaggageDateTime;
	}
	public void setFirstBaggageDateTime(Date firstBaggageDateTime) {
		this.firstBaggageDateTime = firstBaggageDateTime;
	}
	public Date getLastBaggageDateTime() {
		return lastBaggageDateTime;
	}
	public void setLastBaggageDateTime(Date lastBaggageDateTime) {
		this.lastBaggageDateTime = lastBaggageDateTime;
	}

	@Column(name = "AIRCRAFT_TERMINAL_ID", length = 20)
	private String aircraftTerminalID;
	
	@Column(name = "RUNWAY_ID", length = 20)
	private String runwayID;
	

	@Column(name = "PREVIOUS_STAND_ID",length = 20)
	private String previousStandID;
	
	@Column(name = "STAND_ID", length = 20)
	private String standID;
	
	@Column(name = "SCHEDULED_STAND_START_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledStandStartDateTime;
	
	@Column(name = "SCHEDULED_STAND_END_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledStandEndDateTime;
	
	@Column(name = "ESTIMATED_STAND_START_TIME")
	@Temporal(TIMESTAMP)
	private Date estimatedStandStartDateTime;
	
	@Column(name = "ESTIMCATED_STAND_END_TIME")
	@Temporal(TIMESTAMP)
	private Date estimcatedStandEndDateTime;
	
	@Column(name = "ACTUAL_STAND_START_TIME")
	@Temporal(TIMESTAMP)
	private Date actualStandStartDateTime;
	
	@Column(name = "ACTUAL_STAND_END_TIME")
	@Temporal(TIMESTAMP)
	private Date actualStandEndDateTime;
	
	@Column(name = "BAGGAGE_BELT_ID", length = 20)
	private String baggageBeltID;
	
	@Column(name = "BAGGAGE_BELT_STATUS", length = 20)
	private String baggageBeltStatus;
	
	@Column(name = "SCHEDULED_MAKEUP_START_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledMakeupStartDateTime;
	
	@Column(name = "SCHEDULED_MAKEUP_END_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledMakeupEndDateTime;
	
	@Column(name = "ACTUAL_MAKEUP_START_TIME")
	@Temporal(TIMESTAMP)
	private Date actualMakeupStartDateTime;
	
	@Column(name = "ACTUAL_MAKEUP_END_TIME")
	@Temporal(TIMESTAMP)
	private Date actualMakeupEndDateTime;
	
	@Column(name = "PREVIOUS_GATE_ID", length = 20)
	private String previousgateID;
	@Column(name = "GATE_ID", length = 20)
	private String gateID;
	
	@Column(name = "GATE_STATUS", length = 20)
	private String gateStatus;
	
	@Column(name = "SCHEDULED_GATE_START_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledGateStartDateTime;
	
	@Column(name = "SCHEDULED_GATE_END_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledGateEndDateTime;
	
	@Column(name = "ACTUAL_GATE_START_TIME")
	@Temporal(TIMESTAMP)
	private Date actualGateStartDateTime;
	
	@Column(name = "ACTUAL_GATE_END_TIME")
	@Temporal(TIMESTAMP)
	private Date actualGateEndDateTime;
	
	@Column(name = "BAGGAGE_RECLAIM_ID", length = 20)
	private String baggageReclaimID;
	
	@Column(name = "SCHEDULED_FIRST_BAGGAGE_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledFirstBaggageDateTime;
	
	@Column(name = "SCHEDULED_LAST_BAGGAGE_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledLastBaggageDateTime;
	
	@Column(name = "ACTUAL_FIRST_BAGGAGE_TIME")
	@Temporal(TIMESTAMP)
	private Date actualFirstBaggageDateTime;
	
	@Column(name = "ACTUAL_LAST_BAGGAGE_TIME")
	@Temporal(TIMESTAMP)
	private Date actualLastBaggageDateTime;
	
	@Column(name = "OVER_VIEW_CHECK_IN_RANGE", length = 20)
	private String overViewCheckInRange;
	
	@Column(name = "OVER_VIEW_CHECK_IN_TYPE", length = 20)
	private String overViewCheckInType;
	
	@Column(name = "SCHEDULED_CIF_BEGIN_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledCheckInFirstBeginDateTime;
	
	@Column(name = "SCHEDULED_CIL_END_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledCheckInLastEndDateTime;
	
	@Column(name = "ACTUAL_CIF_BEGIN_TIME")
	@Temporal(TIMESTAMP)
	private Date actualCheckInFirstBeginDateTime;
	
	@Column(name = "ACUTAL_CIL_END_TIME")
	@Temporal(TIMESTAMP)
	private Date acutalCheckInLastEndDateTime;
	
	@Column(name = "CHECK_IN_ID", length = 20)
	private String checkInID;
	
	@Column(name = "CHECK_IN_TYPE", length = 20)
	private String checkInType;
	
	@Column(name = "CHECK_IN_STATUS", length = 20)
	private String checkInStatus;
	
	@Column(name = "CHECK_IN_CLASS_SERVICE", length = 20)
	private String checkInClassService;
	
	@Column(name = "SCHEDULED_CHECK_IN_BEGIN_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledCheckInBeginDateTime;
	
	@Column(name = "SCHEDULED_CHECK_IN_END_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledCheckInEndDateTime;
	
	@Column(name = "ACTUAL_CHECK_IN_BEGIN_TIME")
	@Temporal(TIMESTAMP)
	private Date actualCheckInBeginDateTime;
	
	@Column(name = "ACUTAL_CHECK_IN_END_TIME")
	@Temporal(TIMESTAMP)
	private Date acutalCheckInEndDateTime;
	
	@Column(name = "CLOSING_SCHEDULED_FROM_TIME")
	@Temporal(TIMESTAMP)
	private Date closingScheduledFromDateTime;
	
	@Column(name = "CLOSING_SCHEDULED_END_TIME")
	@Temporal(TIMESTAMP)
	private Date closingScheduledEndDateTime;
	
	@Column(name = "CLOSING_ACTUAL_END_TIME")
	@Temporal(TIMESTAMP)
	private Date closingActualEndDateTime;
	
	@Column(name = "COMMENT_FREE_TEXT", length = 200)
	private String commentFreeText;
	
	@Id
	@GeneratedValue
	private Long id;

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
	
	public FlightBase getFlightBase() {
		return flightBase;
	}
	public void setFlightBase(FlightBase flightBase) {
		this.flightBase = flightBase;
	}
	public String getPreviousStandID() {
		return previousStandID;
	}
	public void setPreviousStandID(String previousStandID) {
		this.previousStandID = previousStandID;
	}
	public String getPreviousgateID() {
		return previousgateID;
	}
	public void setPreviousgateID(String previousgateID) {
		this.previousgateID = previousgateID;
	}

}

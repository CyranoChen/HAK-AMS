package com.tsystems.api.model.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MC_API_LOG")
public class McApiLog implements java.io.Serializable {
	// Fields
	private String id;
	private String apiId;
	private String caller;
	private String callWay;
	private String callMethod;
	private Date callTime;	
	private Date finishTime;	
	private String callResult;
	private String backInfo;
	private String exception;
	private Long removed;

	// Constructors

	/** default constructor */
	public McApiLog() {
	}

	public McApiLog(String caller,Date callTime, Date finishTime, String callWay, String callMethod) {
		this.caller = caller;
		this.callWay = callWay;
		this.callMethod = callMethod;
		this.callTime = callTime;
		this.finishTime = finishTime;
		this.removed = Long.parseLong("0");
	}
	
	public McApiLog(String apiId,String caller,Date callTime,Date finishTime,  String callWay, String callMethod) {
		this.apiId = apiId;
		this.caller = caller;
		this.callWay = callWay;
		this.callMethod = callMethod;
		this.callTime = callTime;
		this.finishTime = finishTime;
		this.removed = Long.parseLong("0");
	}
	
	public McApiLog(String apiId,String caller,Date callTime,  String callWay, String callMethod) {
		this.apiId = apiId;
		this.caller = caller;
		this.callWay = callWay;
		this.callMethod = callMethod;
		this.callTime = callTime;
		this.removed = Long.parseLong("0");
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 40)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "API_ID", length = 40)
	public String getApiId() {
		return this.apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	
	@Column(name = "CALLER", length = 50)
	public String getCaller() {
		return this.caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}
	
	@Column(name = "CALL_WAY", length = 50)
	public String getCallWay() {
		return this.callWay;
	}

	public void setCallWay(String callWay) {
		this.callWay = callWay;
	}

	@Column(name = "CALL_METHOD", length = 50)
	public String getCallMethod() {
		return this.callMethod;
	}

	public void setCallMethod(String callMethod) {
		this.callMethod = callMethod;
	}
	
	@Column(name = "CALL_RESULT", length = 10)
	public String getCallResult() {
		return this.callResult;
	}

	public void setCallResult(String callResult) {
		this.callResult = callResult;
	}

	@Column(name = "BACK_INFO")
	public String getBackInfo() {
		return this.backInfo;
	}

	public void setBackInfo(String backInfo) {
		this.backInfo = backInfo;
	}
	
	@Column(name = "EXCEPTION")
	public String getException() {
		return this.exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
	
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CALL_TIME")
	public Date getCallTime() {
		return this.callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FINISH_TIME")
	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	@Column(name = "REMOVED")
	public Long getremoved() {
		return this.removed;
	}

	public void setremoved(Long removed) {
		this.removed = removed;
	}

}
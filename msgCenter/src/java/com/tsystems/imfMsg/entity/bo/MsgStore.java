package com.tsystems.imfMsg.entity.bo;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * DwDataExchangeStore entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MC_MSG_STORE")
public class MsgStore implements java.io.Serializable {

	// Fields
	private String id;
	private String msgType;
	private String msgContent;	
	private String receiver;	
	private Long keyStatus;	
	private String keyContent;		
	private Long status;
	private Date createTime;
	private Date operateTime;	
	private Long removed;
	private String messageSequenceId;
	private String messageType;
	private String serviceType;	
	private String operationMode;	
	private String flightScheduledDate;
	private String flightIdentity;
	private String flightDirection;	
	
	// Constructors
	/** default constructor */
	public MsgStore() {
	}

	/** full constructor */
	public MsgStore(String msgType,String msgContent, String receiver, 
			Date createTime,String status,String keyStatus) {
		this.msgType = msgType;	
		this.msgContent = msgContent;		
		this.receiver = receiver;
		this.createTime = createTime;
		this.status = Long.parseLong(status);
		this.keyStatus = Long.parseLong(keyStatus);
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

	@Column(name = "MSG_TYPE")
	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@Column(name = "MSG_CONTENT")
	public String getMsgContent() {
		return this.msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
		
	@Column(name = "RECEIVER")
	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	@Column(name = "STATUS")
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	@Column(name = "KEY_STATUS")
	public Long getKeyStatus() {
		return this.keyStatus;
	}

	public void setKeyStatus(Long keyStatus) {
		this.keyStatus = keyStatus;
	}	
	
	@Column(name = "KEY_CONTENT")
	public String getKeyContent() {
		return this.keyContent;
	}

	public void setKeyContent(String keyContent) {
		this.keyContent = keyContent;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}
    
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPERATE_TIME")
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "REMOVED")
	public Long getRemoved() {
		return this.removed;
	}

	public void setRemoved(Long removed) {
		this.removed = removed;
	}
	
	@Column(name = "MESSAGESEQUENCEID")
	public String getMessageSequenceId() {
		return this.messageSequenceId;
	}

	public void setMessageSequenceId(String messageSequenceId) {
		this.messageSequenceId = messageSequenceId;
	}
	
	@Column(name = "MESSAGETYPE")
	public String getMessageType() {
		return this.messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	@Column(name = "SERVICETYPE")
	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	@Column(name = "OPERATIONMODE")
	public String getOperationMode() {
		return this.operationMode;
	}

	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}
	@Column(name = "FLIGHTSCHEDULEDDATE")
	public String getFlightScheduledDate() {
		return this.flightScheduledDate;
	}

	public void setFlightScheduledDate(String flightScheduledDate) {
		this.flightScheduledDate = flightScheduledDate;
	}
	
	@Column(name = "FLIGHTIDENTITY")
	public String getFlightIdentity() {
		return this.flightIdentity;
	}

	public void setFlightIdentity(String flightIdentity) {
		this.flightIdentity = flightIdentity;
	}	
	
	@Column(name = "FLIGHTDIRECTION")
	public String getFlightDirection() {
		return this.flightDirection;
	}

	public void setFlightDirection(String flightDirection) {
		this.flightDirection = flightDirection;
	}		
}
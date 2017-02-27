package com.tsystems.imfMsg.entity.vo;

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
 * MsgStore entity. @author MyEclipse Persistence Tools
 */
public class MsgStoreVo implements java.io.Serializable {
	// Fields
	private String id;
	private Date operateTime;	
	private String flightScheduledDate;
	private String flightIdentity;
	private String flightDirection;	
	private String keyField;
	private String oldValue;
	private String newValue;		
	
	// Constructors
	/** default constructor */
	public MsgStoreVo() {
	}

	/** full constructor */
	public MsgStoreVo(String id, Date operateTime, String flightScheduledDate,
			String flightIdentity, String flightDirection, String keyField,
			String oldValue, String newValue) {
		this.id = id;
		this.operateTime = operateTime;
		this.flightScheduledDate = flightScheduledDate;
		this.flightIdentity = flightIdentity;
		this.flightDirection = flightDirection;
		this.keyField = keyField;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getFlightScheduledDate() {
		return flightScheduledDate;
	}

	public void setFlightScheduledDate(String flightScheduledDate) {
		this.flightScheduledDate = flightScheduledDate;
	}

	public String getFlightIdentity() {
		return flightIdentity;
	}

	public void setFlightIdentity(String flightIdentity) {
		this.flightIdentity = flightIdentity;
	}

	public String getFlightDirection() {
		return flightDirection;
	}

	public void setFlightDirection(String flightDirection) {
		this.flightDirection = flightDirection;
	}

	public String getKeyField() {
		return keyField;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	
}
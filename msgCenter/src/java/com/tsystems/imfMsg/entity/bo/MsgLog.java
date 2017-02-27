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
 * DwDataExchangeLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MC_MSG_LOG")
public class MsgLog implements java.io.Serializable {

	// Fields
	private String id;
	private String storeId;
	private String operateType;
	private Date operateTime;	
	private String operateInfo;	
	private String returnMsg;		
	// Constructors
	/** default constructor */
	public MsgLog() {
	}

	/** full constructor */
	public MsgLog(String storeId, String operateType,Date operateTime,
			String operateInfo,String returnMsg) {
		this.storeId = storeId;	
		this.operateType = operateType;	
		this.operateTime = operateTime;
		this.operateInfo = operateInfo;
		this.returnMsg = returnMsg;				
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

	@Column(name = "STORE_ID")
	public String getStoreId() {
		return this.storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@Column(name = "OPERATE_TYPE")
	public String getOperateType() {
		return this.operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPERATE_TIME")
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
	@Column(name = "OPERATE_INFO")
	public String getOperateInfo() {
		return this.operateInfo;
	}

	public void setOperateInfo(String operateInfo) {
		this.operateInfo = operateInfo;
	}
	
	@Column(name = "RETURN_MSG")
	public String getReturnMsg() {
		return this.returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
}
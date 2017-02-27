package com.tsystems.api.model.vo;

import java.sql.Timestamp;

import net.sf.json.JSONObject;

import com.tsystems.api.model.bo.McApiInfo;

public class McApiInfoVo implements java.io.Serializable {

	// Fields

	private String id;
	private String userName;
	private String password;
	private String methodName;
	private String dataType;
	private String company;
	private String dataName;
	private String clientIP;
	private JSONObject params;	
	private Timestamp operateTime;
	private Long removed;

	// Constructors

	/** default constructor */
	public McApiInfoVo() {
	}

	/** full constructor */
	public McApiInfoVo(McApiInfo mcApiInfo) {
		this.id = mcApiInfo.getId();		
		this.userName = mcApiInfo.getUserName();
		this.password = mcApiInfo.getPassword();
		this.methodName = mcApiInfo.getMethodName();
		this.dataType = mcApiInfo.getDataType();		
		this.company = mcApiInfo.getCompany();
		this.dataName = mcApiInfo.getDataName();
		this.clientIP = mcApiInfo.getClientIP();	
		this.params = JSONObject.fromObject(mcApiInfo.getParams());			
		this.operateTime = mcApiInfo.getOperateTime();
		this.removed = mcApiInfo.getremoved();
	}


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getDataName() {
		return this.dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	
	public String getClientIP() {
		return this.clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	
	public JSONObject getParams() {
		return this.params;
	}

	public void setParams(JSONObject params) {
		this.params = params;
	}
	
	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	public Long getremoved() {
		return this.removed;
	}

	public void setremoved(Long removed) {
		this.removed = removed;
	}

}
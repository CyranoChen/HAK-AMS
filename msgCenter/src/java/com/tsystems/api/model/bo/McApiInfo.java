package com.tsystems.api.model.bo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MC_API_INFO")
public class McApiInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String userName;
	private String password;
	private String methodName;
	private String dataType;
	private String company;
	private String dataName;
	private String clientIP;
	private String params;	
	private Timestamp operateTime;
	private Long removed;

	// Constructors

	/** default constructor */
	public McApiInfo() {
	}

	/** full constructor */
	public McApiInfo(String userName, String password, String methodName, String dataType,
			String company, String dataName,String clientIP,String params,Timestamp operateTime, Long removed) {
		this.userName = userName;
		this.password = password;
		this.methodName = methodName;
		this.dataType = dataType;		
		this.company = company;
		this.dataName = dataName;
		this.clientIP = clientIP;	
		this.params = params;			
		this.operateTime = operateTime;
		this.removed = removed;
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

	@Column(name = "USER_NAME", length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "PASSWORD", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "METHOD_NAME", length = 50)
	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	@Column(name = "DATA_TYPE", length = 100)
	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name = "COMPANY",length = 100)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@Column(name = "DATA_NAME",length = 100)
	public String getDataName() {
		return this.dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	
	
	@Column(name = "CLIENT_IP",length = 50)
	public String getClientIP() {
		return this.clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
		
	@Column(name = "PARAMS",length = 1000)
	public String getParams() {
		return this.params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	@Column(name = "OPERATE_TIME", length = 11)
	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "REMOVED")
	public Long getremoved() {
		return this.removed;
	}

	public void setremoved(Long removed) {
		this.removed = removed;
	}

}
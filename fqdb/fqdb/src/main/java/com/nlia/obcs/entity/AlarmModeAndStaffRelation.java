package com.nlia.obcs.entity;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import com.nlia.obcs.vo.AlarmMode;

/**
 * Entity implementation class for Entity: AlarmModeAndStaffRelation
 *
 */
@Entity
@Table(name="ALARM_MODE_AND_STAFF_RELATION")
public class AlarmModeAndStaffRelation implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue
	@Column(name = "ID" )
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "MODE_ID")
	private AlarmMode mode;
	
	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "REMOVE_FLAG", length = 1)
	private String removeFlag;
	
	@Column(name = "CREATE_USER")
	private String createUser;
	
	@Temporal(TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	@Column(name = "MODIFY_USER")
	private String modifyUser;
	
	@Column(name = "MODIFY_TIME")
	@Temporal(TIMESTAMP)
	private Date modifyTime;
	
	//是否订阅
	@Column(name="SUBSCRIPTION")
	private String subscription;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public AlarmMode getMode() {
		return this.mode;
	}

	public void setMode(AlarmMode mode) {
		this.mode = mode;
	}   
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getRemoveFlag() {
		return removeFlag;
	}

	public void setRemoveFlag(String removeFlag) {
		this.removeFlag = removeFlag;
	}

	public String getSubscription() {
		return subscription;
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}
   
}

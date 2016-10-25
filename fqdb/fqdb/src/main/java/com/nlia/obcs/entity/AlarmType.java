package com.nlia.obcs.entity;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

import com.nlia.obcs.vo.AlarmMode;

@Entity
@Table(name = "ALARM_TYPE")
public class AlarmType implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue
	@Column(name = "ID" )
	private Long id;
	
	@Column(name = "TYPE")
	//告警类型
	private String type;
	
	@Column(name = "ALARM_CODE", length = 30)
	@Enumerated(value=EnumType.STRING)
	private AlarmCode alarmCode;
	
	@Column(name = "STATUS" ,length = 1)
	//有效标志
	private String status;
	
	@Column(name = "REMOVE_FLAG" , length = 1)
	//删除标志（逻辑删除）
	private String removeFlag;
	
	@OneToMany(fetch = EAGER, cascade = { REFRESH, PERSIST, MERGE }, mappedBy = "type")
	private Set<AlarmMode> modes;
	
	@Column(name = "REMK")
	private String remk;
	
	public static enum AlarmCode{
		FLIGHT_ADD, FLIGHT_MODIFY, FLIGHT_REMOVE, FLIGHT_DELAY, FLIGHT_REDO,
		FLIGHT_BUSINESSTIME_CHANGE, 
		FLIGHT_CNCL, FLIGHT_CNLAD, FLIGHT_DELETET, FLIGHT_DELNT, FLIGHT_DIVAL,
		FLIGHT_DIVCH, FLIGHT_RETRN, FLIGHT_SLIBK , FLIGHT_STAND_CHANGE,
		FLIGHT_GATE_CHANGE, FLIGHT_CHECKIN_CHANGE, FLIGHT_BAGGAGE_CHANGE,
		SERVICEPLAN_ADD, SERVICEPLAN_MODIFY, SERVICEPLAN_REMOVE,
		TASK_ADD, TASK_MODIFY, TASK_REMOVE,
		FLIGHTSERVICEINFO_ADD, FLIGHTSERVICEINFO_MODIFY, FLIGHTSERVICEINFO_REMOVE;
	}
	
	
	@Column(name = "CREATE_USER")
	private String createUser;
	
	@Column(name = "CREATE_TIME")
	@Temporal(TIMESTAMP)
	private Date createTime;
	
	@Column(name = "MODIFY_USER")
	private String modifyUser;
	
	@Column(name = "MODIFY_TIME")
	@Temporal(TIMESTAMP)
	private Date modifyTime;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}   
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}   
	public String getRemoveFlag() {
		return this.removeFlag;
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
	public Set<AlarmMode> getModes() {
		return modes;
	}
	public void setModes(Set<AlarmMode> modes) {
		this.modes = modes;
	}


	public String getRemk() {
		return remk;
	}

	public void setRemk(String remk) {
		this.remk = remk;
	}

	public AlarmCode getAlarmCode() {
		return alarmCode;
	}

	public void setAlarmCode(AlarmCode alarmCode) {
		this.alarmCode = alarmCode;
	}   

}

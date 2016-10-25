package com.nlia.obcs.entity;

import static javax.persistence.TemporalType.TIMESTAMP;

import com.nlia.obcs.entity.AlarmInformation;
import com.nlia.obcs.vo.AlarmMode;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: AlarmModeAndInfoRelation
 *
 */
@Entity
@Table(name="ALARM_MODE_AND_INFO_RELATION")
public class AlarmModeAndInfoRelation implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue
	@Column(name = "ID" )
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "MODE_ID")
	private AlarmMode mode;
	
	@ManyToOne
	@JoinColumn(name = "ALARM_INFORMATION_ID")
	private AlarmInformation alarmInformation;
	
	@Column(name = "IS_ALREADY_READ")
	//是否已读
	private IS_ALREADY_READ isAlreadyRead;
	
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
	
	public AlarmModeAndInfoRelation() {
		super();
	}   
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
	public AlarmInformation getAlarmInformation() {
		return this.alarmInformation;
	}

	public void setAlarmInformation(AlarmInformation alarmInformation) {
		this.alarmInformation = alarmInformation;
	}
	public String getRemoveFlag() {
		return removeFlag;
	}
	public void setRemoveFlag(String removeFlag) {
		this.removeFlag = removeFlag;
	}
   
	public IS_ALREADY_READ getIsAlreadyRead() {
		return isAlreadyRead;
	}
	public void setIsAlreadyRead(IS_ALREADY_READ isAlreadyRead) {
		this.isAlreadyRead = isAlreadyRead;
	}

	public static enum IS_ALREADY_READ {
		UNREAD("unread"), READ("read");

		private final String value;

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		IS_ALREADY_READ(String value) {
			this.value = value;
		}
	}
}

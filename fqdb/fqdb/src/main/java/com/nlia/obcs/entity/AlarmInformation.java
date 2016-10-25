package com.nlia.obcs.entity;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;


@Entity
@Table(name = "ALARM_INFORMATION")
public class AlarmInformation implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "STATUS",length = 1)
	//有效标志，1为有效
	private String status;
	
	@Column(name = "REMOVE_FLAG",length = 1)
	//逻辑删除
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
	
	@Column(name = "ALARM_INFO_SCHEDULED_DATE_TIME")
	@Temporal(TIMESTAMP)
	private Date alarmInfoScheduledDateTime;
	
/*	@Column(name = "IS_ALREADY_READ",length = 1)
	//是否已读
	private String isAlreadyRead;*/
	
	@Column(name = "INFORMATION_ENTITY")
	//告警信息对应实体名称
	private String informationEntity;
	
	@Column(name = "MESSAGE_CONTENT")
	//告警信息内容
	private String messageContent;
	
	@Column(name = "ENTITY_ID")
	//实体的ID
	private String entityId;
	
	@OneToMany(fetch = EAGER,mappedBy = "alarmInformation")
	private Set<AlarmModeAndInfoRelation> alarmModeAndInfoRelations;
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getInformationEntity() {
		return informationEntity;
	}
	public void setInformationEntity(String informationEntity) {
		this.informationEntity = informationEntity;
	}

	public Date getAlarmInfoScheduledDateTime() {
		return alarmInfoScheduledDateTime;
	}

	public void setAlarmInfoScheduledDateTime(Date alarmInfoScheduledDateTime) {
		this.alarmInfoScheduledDateTime = alarmInfoScheduledDateTime;
	}

	public Set<AlarmModeAndInfoRelation> getAlarmModeAndInfoRelations() {
		return alarmModeAndInfoRelations;
	}

	public void setAlarmModeAndInfoRelations(Set<AlarmModeAndInfoRelation> alarmModeAndInfoRelations) {
		this.alarmModeAndInfoRelations = alarmModeAndInfoRelations;
	}
	public static enum ALARM_INFORMATION_STATUS {
		DISABLED("0"), ENABLED("1");

		private final String value;

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		ALARM_INFORMATION_STATUS(String value) {
			this.value = value;
		}
	}
}

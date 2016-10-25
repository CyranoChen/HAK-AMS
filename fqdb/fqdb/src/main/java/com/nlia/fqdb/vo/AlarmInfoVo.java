package com.nlia.fqdb.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.nlia.fqdb.util.DateUtils;

import net.sf.json.JSONObject;

/**
 * @author ninja_chen
 */
public class AlarmInfoVo {
	
	//关联表的id
	private long alarmModeAndInfoId ;
	//告警信息对应实体名称
	private String informationEntity;
	//告警信息内容
	private String messageContent;
	//实体的ID
	private String entityId;
	//告警类型
	private String alarmType;
	//告警时间
	private String alarmTime;
	
	
	public AlarmInfoVo(BigDecimal alarmModeAndInfoId, String informationEntity, String messageContent, String entityId, String alarmType, Date alarmTime){
		this.alarmModeAndInfoId =alarmModeAndInfoId.longValue();
		this.informationEntity = informationEntity;
		this.messageContent = messageContent;
		this.entityId = entityId;
		this.alarmType = alarmType;
		this.alarmTime = DateUtils.Date2String(alarmTime,"yyyy-MM-dd HH:mm");
	}
	
	public String getInformationEntity() {
		return informationEntity;
	}
	public void setInformationEntity(String informationEntity) {
		this.informationEntity = informationEntity;
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
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public long getAlarmModeAndInfoId() {
		return alarmModeAndInfoId;
	}
	public void setAlarmModeAndInfoId(long alarmModeAndInfoId) {
		this.alarmModeAndInfoId = alarmModeAndInfoId;
	}
	
	@Override
	public String toString(){
		JSONObject json = JSONObject.fromObject(this);
		return json.toString();
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	
}

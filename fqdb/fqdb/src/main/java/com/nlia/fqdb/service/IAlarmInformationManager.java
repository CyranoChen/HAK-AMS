package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.FlightBase;
import com.nlia.obcs.entity.AlarmInformation;
import com.nlia.obcs.entity.AlarmType.AlarmCode;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IAlarmInformationManager extends AbstractBaseService<AlarmInformation, Long>{

	public AlarmInformation addAlarmInformation(AlarmInformation alarmInformation);
	public AlarmInformation modifyAlarmInformation(AlarmInformation alarmInformation);
	public AlarmInformation removeAlarmInformation(AlarmInformation alarmInformation);
	public List<AlarmInformation> queryAlarmInformation(List<AlarmInformation> alarmInformations);
	public String receiveAlarmMsg(String flightId,FlightBase flightBaseNew,String status);
	/**
	 * 通用方法：(航班/服务的)告警信息入库, 出错则返回null
	 * @param obj（必须有getId方法）
	 * @param alarmCode
	 * @return AlarmInformation
	 */
	public AlarmInformation saveAlarmInformation(Object obj, AlarmCode alarmCode);
	
	/**
	 * 获得实体对象
	 * @param ai
	 * @return
	 */
	public Object getEntity(AlarmInformation ai);
}

package com.nlia.fqdb.service;

import java.util.List;
import java.util.Map;

import com.nlia.obcs.entity.AlarmType;
import com.nlia.obcs.vo.AlarmMode;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IAlarmTypeManager extends AbstractBaseService<AlarmType, Long> {

	public AlarmType addAlarmType(AlarmType alarmType);
	public AlarmType modifyAlarmType(AlarmType alarmType);
	public AlarmType removeAlarmType(AlarmType alarmType);
	public List<AlarmType> queryAlarmType(Map<String, Object> filters, Map<String, String> sorts);
	public List<AlarmType> enableAlarmType(List<AlarmType> alarmTypes);
	public List<AlarmType> disableAlarmType(List<AlarmType> alarmTypes);
	public AlarmMode addAlarmMode(AlarmMode alarmMode);
	public AlarmMode modifyAlarmMode(AlarmMode alarmMode);
	public AlarmMode removeAlarmMode(AlarmMode alarmMode);
	public List<AlarmMode> removeAlarModes(List<AlarmMode> alarmModes);
	public List<AlarmMode> queryAlarmMode(Map<String, Object> filters);
	public List<AlarmMode> queryAlarmMode(Map<String, Object> filters , List<String> sorters);
	public List<AlarmMode> enableAlarmMode(List<AlarmMode> alarmModes);
	public List<AlarmMode> disableAlarmMode(List<AlarmMode> alarmModes);
}

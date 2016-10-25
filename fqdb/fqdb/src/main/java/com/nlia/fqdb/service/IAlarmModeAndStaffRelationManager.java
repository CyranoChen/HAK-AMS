package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.obcs.entity.AlarmModeAndStaffRelation;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IAlarmModeAndStaffRelationManager extends AbstractBaseService<AlarmModeAndStaffRelation, Long>{
	public AlarmModeAndStaffRelation addAlarmModeAndStaffRelation(AlarmModeAndStaffRelation alarmModeAndStaffRelation);
	public AlarmModeAndStaffRelation modifyAlarmModeAndStaffRelation(AlarmModeAndStaffRelation alarmModeAndStaffRelation);
	public AlarmModeAndStaffRelation removeAlarmModeAndStaffRelation(AlarmModeAndStaffRelation alarmModeAndStaffRelation);
	public List<AlarmModeAndStaffRelation> queryAlarmModeAndStaffRelation(List<AlarmModeAndStaffRelation> alarmModeAndStaffRelations);
	public boolean deleteAlarmModeAndStaffRelations(List<AlarmModeAndStaffRelation> alarmModeAndStaffRelations);
	public List<AlarmModeAndStaffRelation> subscription(List<AlarmModeAndStaffRelation> addAlarmModeAndStaffRelations , List<AlarmModeAndStaffRelation> deleteAlarmModeAndStaffRelations);
}

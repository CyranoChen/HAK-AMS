package com.nlia.fqdb.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.vo.AlarmInfoVo;
import com.nlia.obcs.entity.AlarmModeAndInfoRelation;
import com.nlia.obcs.vo.AlarmMode;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IAlarmModeAndInfoRelationManager extends AbstractBaseService<AlarmModeAndInfoRelation, Long>{
	public AlarmModeAndInfoRelation addAlarmModeAndInfoRelation(AlarmModeAndInfoRelation alarmModeAndInfoRelation);
	public AlarmModeAndInfoRelation modifyAlarmModeAndInfoRelation(AlarmModeAndInfoRelation alarmModeAndInfoRelation);
	public AlarmModeAndInfoRelation removeAlarmModeAndInfoRelation(AlarmModeAndInfoRelation alarmModeAndInfoRelation);
	public List<AlarmModeAndInfoRelation> queryAlarmInformation(List<AlarmModeAndInfoRelation> alarmModeAndInfoRelations);
	
	/*add by Ninja
	 * 返回loginname对应的5条告警类别
	 */
	public List<AlarmMode> 	getSubscribedAlarmModeList(long userId);
	
	/*add by Ninja
	 * 返回loginname对应的5条告警信息
	 */
	public List<AlarmInfoVo> getSubscribedAlarmInfoVoList(long userId);
	
	public List<AlarmInfoVo> getNewestSubscribedAlarmModeAndInfoRelationList(long userId, Long size, Long alarmInfoId);
	
	/*
	 *　把告警设置为已读
	 */
	public boolean setAlarmModeReaded(Long entityId,String entityName); 
	
	public List<AlarmModeAndInfoRelation> findInfoByUserId(Long userId);
	
	public List<AlarmModeAndInfoRelation> findInfoByUserId(Long userId, Date start, Date end, Map<?, ?> filters);
}

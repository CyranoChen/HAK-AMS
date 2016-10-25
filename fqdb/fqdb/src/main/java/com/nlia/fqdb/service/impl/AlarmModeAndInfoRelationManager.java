package com.nlia.fqdb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.obcs.entity.AlarmInformation;
import com.nlia.obcs.entity.AlarmModeAndInfoRelation;
import com.nlia.obcs.vo.AlarmMode;
import com.nlia.fqdb.dao.AlarmModeAndInfoRelationDao;
import com.nlia.fqdb.service.IAlarmModeAndInfoRelationManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.vo.AlarmInfoVo;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class AlarmModeAndInfoRelationManager extends AbstractCrudService<AlarmModeAndInfoRelation, Long> implements IAlarmModeAndInfoRelationManager {
	@Resource
	private AlarmModeAndInfoRelationDao modeAndInfoRelationDao;
	@Resource
	private AlarmInformationManager alarmInformationManager;
	
	@Override
	public AlarmModeAndInfoRelation addAlarmModeAndInfoRelation(AlarmModeAndInfoRelation alarmModeAndInfoRelation) {
		return modeAndInfoRelationDao.save(alarmModeAndInfoRelation);
	}

	@Override
	public AlarmModeAndInfoRelation modifyAlarmModeAndInfoRelation(AlarmModeAndInfoRelation alarmModeAndInfoRelation) {
		return modeAndInfoRelationDao.save(alarmModeAndInfoRelation);
	}

	@Override
	public AlarmModeAndInfoRelation removeAlarmModeAndInfoRelation(AlarmModeAndInfoRelation alarmModeAndInfoRelation) {
		return null;
	}

	@Override
	public List<AlarmModeAndInfoRelation> queryAlarmInformation(List<AlarmModeAndInfoRelation> alarmModeAndInfoRelations) {
		return null;
	}

	@Override
	protected GenericDao<AlarmModeAndInfoRelation, Long> getDao() {
		return modeAndInfoRelationDao;
	}
	
	
	/*add by Ninja
	 * 返回loginname对应的5条告警类别
	 */
	@Override
	public List<AlarmMode> 	getSubscribedAlarmModeList(long userId){
		 return modeAndInfoRelationDao.getSubscribedAlarmModeList(userId, 5l);
	}
	
	/*add by Ninja
	 * 返回loginname对应的5条告警信息
	 */
	@Override
	public List<AlarmInfoVo>	getSubscribedAlarmInfoVoList(long userId){
		return modeAndInfoRelationDao.getSubscribedAlarmModeAndInfoRelationList(userId, 5l);
	}
	
	public List<AlarmInfoVo> getNewestSubscribedAlarmModeAndInfoRelationList(long userId, Long size, Long alarmInfoId){
		return modeAndInfoRelationDao.getNewestSubscribedAlarmModeAndInfoRelationList(userId, size, alarmInfoId);
	}
	
	/**
	 * 把告警设置为已读
	 * 暂时不用
	 */
	public boolean setAlarmModeReaded(Long entityId,String entityName){/*
		List<AlarmModeAndInfoRelation> alarmModeAndInfoRelations = new ArrayList<AlarmModeAndInfoRelation>();
		HashMap<String,Object> filters = new HashMap<String, Object>();
		filters.put("entityId_equal", String.valueOf(entityId));
		filters.put("status_equal", AlarmInformation.ALARM_INFORMATION_STATUS.ENABLED.getValue());
		filters.put("informationEntity_equal", entityName);
//		filters.put("isAlreadyRead_equal", AlarmModeAndInfoRelation.IS_ALREADY_READ.UNREAD.getValue());
		filters.put("removeFlag_equal",CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		//先去查找告警
		List<AlarmInformation>alarmInformationList = alarmInformationManager.findBy(filters);
		if(null == alarmInformationList || alarmInformationList.size()==0){
			System.out.println("Error in AlarmModeAndInfoRelationManager.java 83 ,Can Not find alarmInfo from entityId!");
			return false;
		}
		//在去查找告警和告警方式关联表
		for(AlarmInformation alarmInfo:alarmInformationList){
			
			filters.clear();
			filters.put("alarmInformation.id_equal", alarmInfo.getId());
			filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
			alarmModeAndInfoRelations.addAll(findBy(filters));
		}
		//遍历添加告警方式
		for(AlarmModeAndInfoRelation alarmModeAndInfoRelation:alarmModeAndInfoRelations){
			//假如告警未读,设为已读。减少数据量
			if(alarmModeAndInfoRelation.getIsAlreadyRead().equals(AlarmModeAndInfoRelation.IS_ALREADY_READ.UNREAD)){
				alarmModeAndInfoRelation.setIsAlreadyRead(AlarmModeAndInfoRelation.IS_ALREADY_READ.READ);
			}
		}
		save(alarmModeAndInfoRelations);*/
		return true;
	}
	
	
	/**
	 * 把一个告警信息设为已读
	 */
	public boolean setAlarmModeReaded(Long alarmInfomationId){
		List<AlarmModeAndInfoRelation> alarmModeAndInfoRelations = new ArrayList<AlarmModeAndInfoRelation>();
		HashMap<String,Object> filters = new HashMap<String, Object>();
		filters.put("id_equal", alarmInfomationId);
//		filters.put("isAlreadyRead_equal", AlarmModeAndInfoRelation.IS_ALREADY_READ.UNREAD.getValue());
		filters.put("removeFlag_equal",CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		//先去查找告警
		List<AlarmInformation>alarmInformationList = alarmInformationManager.findBy(filters);
		if(null == alarmInformationList || alarmInformationList.size()==0){
			System.out.println("Error in AlarmModeAndInfoRelationManager.java 83 ,Can Not find alarmInfo from entityId!");
			return false;
		}
		//在去查找告警和告警方式关联表
		for(AlarmInformation alarmInfo:alarmInformationList){
			
			filters.clear();
			filters.put("alarmInformation.id_equal", alarmInfo.getId());
			filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
			alarmModeAndInfoRelations.addAll(findBy(filters));
		}
		//遍历添加告警方式
		for(AlarmModeAndInfoRelation alarmModeAndInfoRelation:alarmModeAndInfoRelations){
			//假如告警未读,设为已读。减少数据量
			if(alarmModeAndInfoRelation.getIsAlreadyRead().equals(AlarmModeAndInfoRelation.IS_ALREADY_READ.UNREAD)){
				alarmModeAndInfoRelation.setIsAlreadyRead(AlarmModeAndInfoRelation.IS_ALREADY_READ.READ);
			}
		}
		save(alarmModeAndInfoRelations);
		return true;
	}
	
	@Override
	public List<AlarmModeAndInfoRelation> findInfoByUserId(Long userId){
		return modeAndInfoRelationDao.findByAcountId(userId);
	}
	
	@Override
	public List<AlarmModeAndInfoRelation> findInfoByUserId(Long userId, Date start, Date end, Map<?, ?> filters){
		return modeAndInfoRelationDao.findByAcountId(userId, start, end, filters);
	}

}

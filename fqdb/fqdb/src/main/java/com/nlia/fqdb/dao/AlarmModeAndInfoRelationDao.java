package com.nlia.fqdb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;
import com.nlia.fqdb.vo.AlarmInfoVo;
import com.nlia.obcs.entity.AlarmModeAndInfoRelation;
import com.nlia.obcs.vo.AlarmMode;
import com.wonders.aiis.core.dao.GenericDao;

@Repository
public class AlarmModeAndInfoRelationDao extends GenericDao<AlarmModeAndInfoRelation, Long>{
	
	/**
	 * @param userId 用户id
	 * @param rownum　最新的N条
	 * @return　指定用户最新的N条告警
	 */
	public List<AlarmMode>	getSubscribedAlarmModeList(long userId,long rownum){
		List<AlarmMode>alarmModeList = new ArrayList<AlarmMode>();
		String sqlString = "select * from (select t.id, " +
							"	t.mode_para, " +
							"	t.pattern, " +
							"	t.status, " +
							"	t.type_Id " +
							"from alarm_mode t "+
							"left outer join alarm_mode_and_info_relation s on t.id = s.mode_id "+
							"left outer join alarm_mode_and_staff_relation u on t.id = u.mode_id "+
							"where u.user_id = :userId "+
							"and s.remove_flag = :isRemove "+
							"and u.remove_flag = :isRemove "+
							"order by s.create_time desc ) where rownum <= :rownum  ";
		Query query = entityManager.createNativeQuery(sqlString)
				.setParameter("userId", userId)
				.setParameter("isRemove", CommonData.REMOVEFLAG.NOT_REMOVED.getValue())
				.setParameter("rownum", rownum);
				@SuppressWarnings("unchecked")
				List<Object[]>objects = query.getResultList();
				AlarmMode alarmMode = null;
				for(Object[] object:objects){
					alarmMode = new AlarmMode((BigDecimal)object[0], (String)object[1], (String)object[2], (String)object[3]);
					alarmModeList.add(alarmMode);
				}
				return alarmModeList;
	}
	
	/**
	 * @param userId 用户id
	 * @param rownum　最新的N条
	 * @return　指定用户最新的N条告警信息
	 */
	@SuppressWarnings("unchecked")
	public List<AlarmInfoVo>	getSubscribedAlarmModeAndInfoRelationList(long userId,long rownum){
		if(userId <1 ) return null;
		if(rownum <1 ) return null;
		List <AlarmInfoVo> alarmInfoVoList = new ArrayList<AlarmInfoVo>();
		
		String sqlString = 
//				"select * from (select amir.id, " + 
//				"ai.information_entity, " + 
//				"ai.message_content, ai.entity_id, " + 
//				"atp.type, ai.alarm_info_scheduled_date_time " +
//				
//				"from alarm_mode_and_info_relation amir " +
//				"left outer join  alarm_information ai " +
//				" on ai.id = amir.alarm_information_id " +
//				"left outer join alarm_mode am  " +
//				"on amir.mode_id = am.id " +
//				"left outer join alarm_type atp " +
//				"on atp.id = am.type_id " +
//				"left outer join alarm_mode_and_staff_relation amsr " + 
//				"on amsr.mode_id = am.id " + 
//				
//				"where amsr.user_id = :userId " +
//				"and ai.remove_flag = :isRemove " +
//				"order by ai.create_time desc) where rownum < :rownum ";
				"select * from ( select t.id, " +
							"	ai.information_entity, " +
							"	ai.message_content, " +
							"	ai.entity_id, " +
							"	at.type, " +
							"	ai.ALARM_INFO_SCHEDULED_DATE_TIME "+
		
							"from alarm_mode_and_info_relation t "+
							"left outer join alarm_information ai "+ 
							"on t.alarm_information_id = ai.id, "+ 
							"alarm_mode_and_staff_relation s "+
							"left outer join alarm_mode am "+
							"on s.mode_id = am.id "+
							"left outer join alarm_type at "+
							"on am.type_id = at.id "+
							
							"where t.mode_id = s.mode_id "+
//							"and am.pattern = 'color' "+
							"and s.user_id = :userId "+
							"and ai.remove_flag = :isRemove "+
							"order by ai.create_time desc ) where  rownum < :rownum";
		
		Query query = entityManager.createNativeQuery(sqlString)
				.setParameter("userId", userId)
				.setParameter("isRemove", CommonData.REMOVEFLAG.NOT_REMOVED.getValue())
				.setParameter("rownum", ++rownum);
		List<Object[]>objects = query.getResultList();
		
		AlarmInfoVo alarmInfoVo = null;
		for(Object[] object:objects){
			alarmInfoVo = new AlarmInfoVo((BigDecimal)object[0], (String)object[1], (String)object[2], (String)object[3], (String)object[4], (Date)object[5]);
			alarmInfoVoList.add(alarmInfoVo);
		}
				return alarmInfoVoList;
	}
	
	/**
	 * @param userId 用户id
	 * @param rownum　最新的N条
	 * @param alarmInfoId 当前最新的告警信息ID
	 * @return　指定用户最新的N条告警信息
	 */
	@SuppressWarnings("unchecked")
	public List<AlarmInfoVo> getNewestSubscribedAlarmModeAndInfoRelationList(long userId,long rownum,Long alarmInfoId){
		if(userId <1 ) return null;
		if(rownum <1 ) return null;
		List <AlarmInfoVo> alarmInfoVoList = new ArrayList<AlarmInfoVo>();
		
		String sqlString = "select * from ( select t.id, " +
							"	ai.information_entity, " +
							"	ai.message_content, " +
							"	ai.entity_id, " +
							"	at.type, " +
							"	ai.ALARM_INFO_SCHEDULED_DATE_TIME "+
		
							"from alarm_mode_and_info_relation t "+
							"left outer join alarm_information ai "+ 
							"on t.alarm_information_id = ai.id, "+ 
							"alarm_mode_and_staff_relation s "+
							"left outer join alarm_mode am "+
							"on s.mode_id = am.id "+
							"left outer join alarm_type at "+
							"on am.type_id = at.id "+
							
							"where t.mode_id = s.mode_id "+
							"and t.id > :alarmInfoId " + 
							"and s.user_id = :userId "+
							"and ai.remove_flag = :isRemove "+
							"order by ai.create_time desc ) where  rownum < :rownum";
		
		Query query = entityManager.createNativeQuery(sqlString)
				.setParameter("userId", userId)
				.setParameter("alarmInfoId", alarmInfoId)
				.setParameter("isRemove", CommonData.REMOVEFLAG.NOT_REMOVED.getValue())
				.setParameter("rownum", ++rownum);
		List<Object[]>objects = query.getResultList();
		
		AlarmInfoVo alarmInfoVo = null;
		for(Object[] object:objects){
			alarmInfoVo = new AlarmInfoVo((BigDecimal)object[0], (String)object[1], (String)object[2], (String)object[3], (String)object[4], (Date)object[5]);
			alarmInfoVoList.add(alarmInfoVo);
		}
				return alarmInfoVoList;
	}
	
	/**
	 * 查询账号对应两天的告警信息
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AlarmModeAndInfoRelation> findByAcountId(Long userId){
		StringBuilder hql = new StringBuilder();
		hql.append("from AlarmModeAndInfoRelation ai where ai.mode in (select asr.mode from AlarmModeAndStaffRelation  asr where " );
		hql.append("asr.userId = :userId and ");
		hql.append("asr.removeFlag = :removeFlag) and ");
		hql.append("ai.mode.status = :status and ");
		hql.append("(ai.alarmInformation.alarmInfoScheduledDateTime between :Start and :End) and ");
		hql.append("ai.removeFlag = :removeFlag ");
		
		hql.append("order by ai.alarmInformation.alarmInfoScheduledDateTime desc");
		
		
		Query query = entityManager.createQuery(hql.toString()).setParameter("userId", userId.toString())
				.setParameter("removeFlag", CommonData.REMOVEFLAG.NOT_REMOVED.getValue()).
				setParameter("status", AlarmMode.MODE_STATUS.ENABLED.getValue()).
				setParameter("Start", DateUtils.getZeroOfToday()).
				setParameter("End", DateUtils.getLatestTimeOfTomorrow()).
				setParameter("removeFlag", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		return query.getResultList();
	}
	
	/**
	 * 根据条件查询账号对应告警信息
	 * @param userId
	 * @param start
	 * @param end
	 * @param filters
	 * @return AlarmModeAndInfoRelations
	 */
	@SuppressWarnings("unchecked")
	public List<AlarmModeAndInfoRelation> findByAcountId(Long userId, Date start, Date end, Map<?, ?> filters){
		StringBuilder hql = new StringBuilder();
		hql.append("from AlarmModeAndInfoRelation ai where ai.mode in (select asr.mode from AlarmModeAndStaffRelation  asr where " );
		hql.append("asr.userId = :userId and ");
		hql.append("asr.removeFlag = :removeFlag) and ");
		hql.append("ai.mode.status = :status and ");
		hql.append("(ai.alarmInformation.alarmInfoScheduledDateTime between :Start and :End) and ");
		
		for (Entry<?, ?> entry : filters.entrySet()){
			if ("type".equals((String)entry.getKey())) {
				hql.append("ai.mode.type = :type and ");
			}
			if ("pattern".equals((String)entry.getKey())) {
				hql.append("ai.mode.pattern = :pattern and ");
			}
		}
		hql.append("ai.removeFlag = :removeFlag ");
		
		hql.append("order by ai.alarmInformation.alarmInfoScheduledDateTime desc");
		
		
		Query query = entityManager.createQuery(hql.toString()).setParameter("userId", userId.toString())
				.setParameter("removeFlag", CommonData.REMOVEFLAG.NOT_REMOVED.getValue()).
				setParameter("status", AlarmMode.MODE_STATUS.ENABLED.getValue()).
				setParameter("Start", start).
				setParameter("End", end);
		for (Entry<?, ?> entry : filters.entrySet()){
			if ("type".equals((String)entry.getKey())) {
				query.setParameter("type", entry.getValue());
			}
			if ("pattern".equals((String)entry.getKey())) {
				query.setParameter("pattern", entry.getValue());
			}
		}
		query.setParameter("removeFlag", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		return query.getResultList();
	}
}

package com.nlia.fqdb.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nlia.obcs.entity.AlarmType.AlarmCode;
import com.nlia.obcs.vo.AlarmMode;
import com.wonders.aiis.core.dao.GenericDao;

@Repository
public class AlarmModeDao extends GenericDao<AlarmMode, Long> {

	@SuppressWarnings("unchecked")
	public List<AlarmMode> getAlarmModesByType(List<String> types) {
		String hql = "from AlarmMode t where t.type.removeFlag = 1 and t.type.type in (:types) order by t.type.type desc";
		Query query = entityManager.createQuery(hql).setParameter("types", types);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<AlarmMode> getAlarmModesBySpecial(String type){
		String hql = "from AlarmMode t where t.type.removeFlag = 1 and t.type.type =:type order by t.type.type desc";
		Query query = entityManager.createQuery(hql).setParameter("type", type);
		return query.getResultList();
	}
	
	/**
	 * 通过alarmtype的code来找alarmmode
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AlarmMode> getAlarmModesByAlarmTypeCode(AlarmCode code){
		String hql = "from AlarmMode t where t.type.removeFlag = 1 and t.status = 1 and t.type.alarmCode =:alarmCode";
		Query query = entityManager.createQuery(hql).setParameter("alarmCode", code);
		return query.getResultList();
	}
}

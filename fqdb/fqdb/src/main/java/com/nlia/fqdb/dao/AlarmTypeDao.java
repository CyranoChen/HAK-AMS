package com.nlia.fqdb.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nlia.obcs.entity.AlarmType;
import com.nlia.obcs.entity.AlarmType.AlarmCode;
import com.wonders.aiis.core.dao.GenericDao;

@Repository
public class AlarmTypeDao extends GenericDao<AlarmType, Long> {
	
	@SuppressWarnings("unchecked")
	public AlarmType find(AlarmCode alarm_code){
		String hql = "from AlarmType t where t.removeFlag = 1 and t.alarmCode = :alarmCode";
		Query query = entityManager.createQuery(hql).setParameter("alarmCode", alarm_code);
		List<AlarmType> types = query.getResultList();
		if(types != null && types.size() > 0)
			return types.get(0);
		return null;
	}
}

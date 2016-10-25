package com.nlia.fqdb.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nlia.obcs.entity.AlarmInformation;
import com.wonders.aiis.core.dao.GenericDao;

@Repository
public class AlarmInformationDao extends GenericDao<AlarmInformation, Long>{

	
	@SuppressWarnings("unchecked")
	public Object getEntity(AlarmInformation ai){
		String entityName = ai.getInformationEntity();
		String entityId = ai.getEntityId();
		StringBuffer hql = new StringBuffer();
		hql.append("from ").append(entityName).append(" t  where t.id = :entityId");
		//不能用下面句话，因为数据同步的时候要找删除过数据
//		hql.append("from ").append(entityName).append(" t  where t.removeFlag = :isRemove and t.id = :entityId");
		
		Query query = entityManager.createQuery(hql.toString())
				.setParameter("entityId", Long.valueOf(entityId));
//				.setParameter("isRemove", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<Object>objects = query.getResultList();
		if(objects.size() > 0)
			return objects.get(0);
		return null;
	}
}

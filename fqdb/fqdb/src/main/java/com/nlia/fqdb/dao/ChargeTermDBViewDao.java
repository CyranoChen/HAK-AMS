package com.nlia.fqdb.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nlia.fqdb.entity.ChargeTermDBView;
import com.wonders.aiis.core.dao.GenericDao;
@Repository
public class ChargeTermDBViewDao extends GenericDao<ChargeTermDBView, Long>{
	public List<ChargeTermDBView> findBySql(Date start, Date end, String chargeTermName,String airlineName,String chargeTypeName){
		String sql = " select * from CHARGE_TERM_VIEW t  where t.Remove_Flag=1  ";
		int index = 1;
		Map<Integer, Object> map = new HashMap<Integer, Object>();
	    if (start != null) {
	        sql += " and t.EXECUTE_TIME>? ";
	        map.put(index, start);
	        index++;
	    }
        if (end != null) {
	        sql += " and t.EXECUTE_TIME<=? ";
	        map.put(index, end);
	        index++;
	    }
        if (chargeTermName != null && !chargeTermName.isEmpty()) {
            sql += " and t.NAME like '%" + chargeTermName + "%' ";
        }
        if (airlineName != null && !airlineName.isEmpty()) {
            sql += " and t.AIRLINE_NAME like '%" + airlineName + "%' ";
        }
        if (chargeTypeName != null && !chargeTypeName.isEmpty()) {
            sql += " and t.CHARGE_TYPE_NAME like '%" + chargeTypeName + "%' ";
        }
        Query query = entityManager
                .createNativeQuery(sql, ChargeTermDBView.class);
        for (Integer key : map.keySet()) {
        	query.setParameter(key, map.get(key));
        }
		return query.getResultList();
	}
	
	public List<ChargeTermDBView> findBySqlWithId(Long id){
		String sql = " select * from CHARGE_TERM_VIEW t  where t.Remove_Flag=1  and t.id=" + Long.toString(id);
		Query query = entityManager.createNativeQuery(sql, ChargeTermDBView.class);
		return query.getResultList();
	}
}


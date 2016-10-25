package com.nlia.fqdb.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nlia.fqdb.entity.ChargeTermDBView;
import com.nlia.fqdb.entity.SyntheticChargeTermDBView;
import com.wonders.aiis.core.dao.GenericDao;
@Repository
public class SyntheticChargeTermDBViewDao extends GenericDao<SyntheticChargeTermDBView, Long>{
	public List<SyntheticChargeTermDBView> findBySql(Date start, Date end,String airlineName,
			String flightCountryType,String aFlightIdentity,String dFlightIdentity,String flightDircetion){
		String sql = " select * from SYNTHETIC_CHARGE_TERM_VIEW t  where t.REMOVE_FLAG= 1 ";
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
        if (flightCountryType != null && !flightCountryType.isEmpty()) {
            sql += " and t.FLIGHT_COUNTRY_TYPE like '%" + flightCountryType + "%' ";
        }
        if (airlineName != null && !airlineName.isEmpty()) {
            sql += " and t.AIRLINE_NAME like '%" + airlineName + "%' ";
        }
        if (aFlightIdentity != null && !aFlightIdentity.isEmpty()) {
            sql += " and t.A_FLIGHT_IDENTITY like '%" + aFlightIdentity + "%' ";
        }
        if (dFlightIdentity != null && !dFlightIdentity.isEmpty()) {
            sql += " and t.D_FLIGHT_IDENTITY like'%" + dFlightIdentity + "%' ";
        }
        if (flightDircetion != null && !flightDircetion.isEmpty()) {
            sql += " and t.FLIGHT_DIRECTION ='" + flightDircetion + "' ";
        }
        Query query = entityManager
                .createNativeQuery(sql, SyntheticChargeTermDBView.class);
        for (Integer key : map.keySet()) {
        	query.setParameter(key, map.get(key));
        }
		return query.getResultList();
	}
}

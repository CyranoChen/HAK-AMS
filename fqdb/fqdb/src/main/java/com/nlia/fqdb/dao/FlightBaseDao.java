package com.nlia.fqdb.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.util.DateUtils;
import com.wonders.aiis.core.dao.GenericDao;

@Repository
public class FlightBaseDao extends GenericDao<FlightBase, Long> {

	/**
	 * 查询日期在时间段之内的连班航班且是离港航班
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FlightBase> findFlightBasesByTime(String startTime, String endTime) {
//		String hql = "from FlightBase t where t.flightScheduledDate >=:startTime and t.flightScheduledDate <=:endTime and t.removeFlag = 1 and t.flightDirection = 1 and t.linkFlight is null"; //测试
		String hql = "from FlightBase t where t.flightScheduledDate >=:startTime and t.flightScheduledDate <=:endTime and t.removeFlag = 1 and t.flightDirection = 1 and t.linkFlight is not null";
		Query query = entityManager.createQuery(hql)
				.setParameter("startTime", DateUtils.String2Date(startTime, "yyyy-MM-dd"))
				.setParameter("endTime", DateUtils.String2Date(endTime, "yyyy-MM-dd"));
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<FlightBase> getPeriodFlightByPage(Date startdate, Date enddate, int size, int round) {
		int startPosition = 0, maxResult = 0;
		startPosition = size * (round - 1);
		maxResult = size;
		
		String hql = " from FlightBase t " +
				" where t.flightData.flightScheduledDateTime >=:startTime " +
				"	and t.flightData.flightScheduledDateTime <=:endTime " +
				"	and t.removeFlag = 1 " +
				"order by t.flightData.flightScheduledDateTime asc desc";
		Query query = entityManager.createQuery(hql)
				.setParameter("startTime", startdate)
				.setParameter("endTime", enddate)
				.setFirstResult(startPosition)
				.setMaxResults(maxResult);
		return query.getResultList();
	}

	public long getPeriodFlightCount(Date startdate, Date enddate) {
		String hql = "select count(t.id) from FlightBase t " +
				"where t.flightData.flightScheduledDateTime >=:startTime " +
				"	and t.flightData.flightScheduledDateTime <=:endTime " +
				"	and t.removeFlag = 1 " +
				"order by t.flightData.flightScheduledDateTime";
		Query query = entityManager.createQuery(hql)
				.setParameter("startTime", startdate)
				.setParameter("endTime", enddate);
		return (long) query.getSingleResult();
	}
	
	public int updateCascade(Long fbid, Long fdid,Long frid) {
		String hql = "update FlightBase t set t.flightData.id =:fdid,t.flightResource.id =:frid where t.id=:fbid";
		Query query = entityManager.createQuery(hql)
				.setParameter("fdid", fdid)
				.setParameter("frid", frid)
				.setParameter("fbid", fbid);
		return query.executeUpdate();
	}
}

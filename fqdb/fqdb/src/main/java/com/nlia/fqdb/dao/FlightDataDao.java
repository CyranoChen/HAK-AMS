package com.nlia.fqdb.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightData;
import com.nlia.fqdb.util.DateUtils;
import com.wonders.aiis.core.dao.GenericDao;

@Repository
public class FlightDataDao extends GenericDao<FlightData, Long> {
	public List<FlightData> findFlightBasesByTime(String startTime, String endTime) {
		String hql = "from FlightBase t where t.flightScheduledDate >=:startTime and t.flightScheduledDate <=:endTime and t.removeFlag = 1 and t.flightDirection = 1 and t.linkFlight is null"; //测试
//		String hql = "from FlightBase t where t.flightScheduledDate >=:startTime and t.flightScheduledDate <=:endTime";
		Query query = entityManager.createQuery(hql)
				.setParameter("startTime", DateUtils.String2Date(startTime, "yyyy-MM-dd"))
				.setParameter("endTime", DateUtils.String2Date(endTime, "yyyy-MM-dd"));
		return query.getResultList();
	}
		public List<FlightBase> getPeriodFlightByPage(Date startdate, Date enddate, int size, int round) {
			int startPosition = 0, maxResult = 0;
			startPosition = size * (round - 1);
			maxResult = size;
			
			String hql = " from FlightBase t " +
					" where t.flightData.flightScheduledDateTime >=:startTime " +
					"	and t.flightData.flightScheduledDateTime <=:endTime " +
					"	and t.removeFlag = 1 " +
					"order by t.flightData.flightScheduledDateTime";
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

}

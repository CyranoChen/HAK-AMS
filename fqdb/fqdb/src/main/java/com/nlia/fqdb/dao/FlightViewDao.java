package com.nlia.fqdb.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.stereotype.Repository;

import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightView;
import com.nlia.fqdb.util.CommonData;
import com.wonders.aiis.core.dao.GenericDao;

@Repository
public class FlightViewDao extends GenericDao<FlightView, Long> {

	public List<FlightView> findBySql(Date start, Date end, String flightIdentity, FlightBase.TYPE flightDirection, String aircraftIATACode, String flightRegisteration){
		
	    String sql1 = " select * from FLIGHT_VIEW t  where t.Remove_Flag=1  ";
//      String sql2 = " union all select t2.* from FLIGHT_VIEW ft, FLIGHT_VIEW t2 where  t2.id = ft.LINK_FLIGHT_ID and ft.Remove_Flag=1 ";
      String sql2 = " union select ft.* from FLIGHT_VIEW ft where  ft.Remove_Flag=1 and ft.id in (select t.LINK_FLIGHT_ID from FLIGHT_VIEW t  where t.Remove_Flag=1 "; 
      Map<Integer, Object> map1 = new HashMap<Integer, Object>();
      int index = 1;
      if (start != null) {
          sql1 += " and t.Flight_Scheduled_Date_Time>? ";
          sql2 += " and t.Flight_Scheduled_Date_Time>? ";
          map1.put(index, start);
          index++;
      }
      if (end != null) {
          sql1 += " and t.Flight_Scheduled_Date_Time<=? ";
          sql2 += " and t.Flight_Scheduled_Date_Time<=? ";
          map1.put(index, end);
          index++;
      }
      if (flightIdentity != null && !flightIdentity.isEmpty()) {
          sql1 += " and t.FLIGHT_IDENTITY like '%" + flightIdentity + "%' ";
          sql2 += " and t.FLIGHT_IDENTITY like '%" + flightIdentity + "%' ";
      }
      if (flightDirection != null) {
          sql1 += " and t.FLIGHT_DIRECTION='" + flightDirection.getValue()
                  + "'";
          sql2 += " and t.FLIGHT_DIRECTION='" + flightDirection.getValue()
                  + "'";
      }
      if (aircraftIATACode != null && !aircraftIATACode.isEmpty()) {
          sql1 += " and t.AIRCRAFT_IATA_CODE like '%" + aircraftIATACode
                  + "%' ";
          sql2 += " and t.AIRCRAFT_IATA_CODE like '%" + aircraftIATACode
                  + "%' ";
      }
      if (flightRegisteration != null && !flightRegisteration.isEmpty()) {
          sql1 += " and t.REGISTERATION like '%" + flightRegisteration
                  + "%' ";
          sql2 += " and t.REGISTERATION like '%" + flightRegisteration
                  + "%' ";
      }
      sql2 += ")";
      Query q = entityManager
              .createNativeQuery(sql1 + sql2, FlightView.class);
      for (Integer key : map1.keySet()) {
          q.setParameter(key, map1.get(key));
      }
      for (Integer key : map1.keySet()) {
          q.setParameter(key + index - 1, map1.get(key));
      }

      return q.getResultList();
	}
	public void buildWhereString(String question,String start_time,String end_time){
		String hql="from FLIGHT_VIEW t where 1 = 1";
		
		List<Object> p = new ArrayList<Object>();
		
	/*	if(){
			hql += " and t.Flight_Scheduled_Date_Time>to_date('yyyy-mm-dd hh24:mi:ss' ) ";
			hql += " and t.Flight_Scheduled_Date_Time<=to_date('yyyy-mm-dd hh24:mi:ss' ) ";
			hql += " and t.Remove_Flag=1 union all ";
			hql += " and from FLIGHT_VIEW t1, FLIGHT_VIEW t2 where t2.id=t1.LINK_FLIGHT_ID";
			hql += " and  t1.Flight_Scheduled_Date_Time>to_date('yyyy-mm-dd hh24:mi:ss')";
			hql += " and t1.Flight_Scheduled_Date_Time<=to_date('yyyy-mm-dd hh24:mi:ss')";
			hql += " t1.Remove_Flag=1";
			
		}*/
		
	}

	protected void buildWhereString(Map<?, ?> filters, CriteriaBuilder cb,
			CriteriaQuery<?> cq, Root<FlightView> root) {
		
		Predicate p = cb.conjunction();
		for (Entry<?, ?> entry : filters.entrySet()) {

			String key = (String) entry.getKey();
			String prop = StringUtils.substringBefore(key, "_");
			String oper = StringUtils.substringAfter(key, "_");
			Object value = entry.getValue();

			

			try {
				Path<Object> path = buildPath(root, prop);
				if (StringUtils.equals(oper, "like")) {
					p = cb.and(p, (Predicate) MethodUtils.invokeMethod(cb,
							oper, path, "%" + value + "%"));
				} else if (StringUtils.startsWith(oper, "is")) {
					
					p = cb.and(p, (Predicate) MethodUtils.invokeMethod(cb,
							oper, path));
				} else if (StringUtils.startsWith(oper, "lessThan")
						|| StringUtils.startsWith(oper, "greaterThan")) {
					
					if (value instanceof String) {
						value = buildPath(root, (String) value);
					}
					
					p = cb.and(p, (Predicate) MethodUtils.invokeMethod(cb,
							oper, path, value));
				} else {
					p = cb.and(p, (Predicate) MethodUtils.invokeMethod(cb,
							oper, path, value));
				}
			} catch (Exception e) {	
				
				e.printStackTrace();
			}

			cq.where(p);
		}
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@"+p.toString());
	}
	
	//修改为使用计划时间
    public List<FlightView> findBySqlWithActualTime(Date start, Date end) {
       StringBuffer sql=new StringBuffer();
       sql.append("select * from flight_view t where t.Remove_Flag='1' and t.Is_Master_Flight='Y' ");
       sql.append("and ((t.SCHEDULED_LANDING_TIME >:startTime and t.SCHEDULED_LANDING_TIME<=:endTime ) or (t.SCHEDULED_TAKE_OFF_TIME>:startTime and t.SCHEDULED_TAKE_OFF_TIME<=:endTime )) ");
       sql.append("union ");
       sql.append("select ft.* from flight_view ft ,flight_view t where t.Remove_Flag='1' and t.Is_Master_Flight='Y' ");
       sql.append("and ((t.SCHEDULED_LANDING_TIME >:startTime and t.SCHEDULED_LANDING_TIME<=:endTime ) or (t.SCHEDULED_TAKE_OFF_TIME>:startTime and t.SCHEDULED_TAKE_OFF_TIME<=:endTime )) ");
       sql.append("and  ft.Remove_Flag='1' and ft.Is_Master_Flight='Y' and ft.id=t.LINK_FLIGHT_ID");
       
       List<Date> paramList  = new ArrayList<Date>();
      
        Query q = entityManager.createNativeQuery(sql.toString(),FlightView.class).setParameter("startTime", start).setParameter("endTime", end);
        return q.getResultList();
    
    }
    
	//修改为使用计划时间
    public List<FlightView> findBySqlWithId(Long id) {
       StringBuffer sql=new StringBuffer();
       sql.append("select * from flight_view t where t.Remove_Flag='1' and t.Is_Master_Flight='Y' and t.id= :flightBaseId ");       
       sql.append("union ");
       sql.append("select ft.* from flight_view ft ,flight_view t where t.Remove_Flag='1' and t.Is_Master_Flight='Y' and t.id= :flightBaseId ");       
       sql.append("and  ft.Remove_Flag='1' and ft.Is_Master_Flight='Y' and ft.id=t.LINK_FLIGHT_ID");
       
       List<Date> paramList  = new ArrayList<Date>();
      
        Query q = entityManager.createNativeQuery(sql.toString(),FlightView.class).setParameter("flightBaseId", id);
        return q.getResultList();
    
    }
    
    public List<FlightView> findFlightBaseMisFlightMateInfoByActualTime(Date start, Date end) {
        StringBuffer sql=new StringBuffer();
        sql.append("select * from flight_view t where t.Remove_Flag='1' ");
        sql.append("and ((t.Actual_Landing_Time >:startTime and t.Actual_Landing_Time<=:endTime ) or (t.Actual_Take_Off_Time>:startTime and t.Actual_Take_Off_Time<=:endTime )) ");
        sql.append("and t.id not in (select fmi.flight_base_id from flight_mate_info fmi where fmi.stat!='0' and fmi.remove_flag='1' ) ");
        sql.append("union ");
        sql.append("select ft.* from flight_view ft ,flight_view t where t.Remove_Flag='1' and t.Is_Master_Flight='Y' ");
        sql.append("and ((t.Actual_Landing_Time >:startTime and t.Actual_Landing_Time<=:endTime ) or (t.Actual_Take_Off_Time>:startTime and t.Actual_Take_Off_Time<=:endTime )) ");
        sql.append("and t.id not in (select fmi.flight_base_id from flight_mate_info fmi where fmi.stat!='0' and fmi.remove_flag='1' ) ");
        sql.append("and  ft.Remove_Flag='1' and ft.Is_Master_Flight='Y' and ft.id=t.LINK_FLIGHT_ID");
        
        List<Date> paramList  = new ArrayList<Date>();
       
         Query q = entityManager.createNativeQuery(sql.toString(),FlightView.class).setParameter("startTime", start).setParameter("endTime", end);
         return q.getResultList();
     
     }
    
    public List<FlightView> findFlightBaseByNativeQuery(String sql){
        Query q = entityManager.createNativeQuery(sql,FlightView.class);
        return q.getResultList();
    }
}

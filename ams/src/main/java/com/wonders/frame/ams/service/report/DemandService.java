package com.wonders.frame.ams.service.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DemandService {
	
	@Resource
	private JdbcTemplate jt;
	
    public List<Map<String,Object>> query(String startTime,String endTime,String sqlWhere){
    	String sql="select * from v_demand t where 1 = 1 " + sqlWhere;
    	if((startTime!=null&&!("").equals(startTime))&&(endTime!=null&&!("").equals(endTime))){
			sql = sql + " and to_date(t.scheduled_time,'yyyy-MM-dd') between to_date(?,'yyyy-MM-dd') and to_date(?,'yyyy-MM-dd') order by t.scheduled_time";
		}
    	List<Map<String,Object>> list = jt.queryForList(sql,startTime,endTime);
    	return list;
    }
}

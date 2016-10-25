package com.wonders.frame.ams.service.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class LiquidationIncomeCalculationService {
	
	@Resource
	private JdbcTemplate jt;
	
    public List<Map<String,Object>> query(String startTime,String endTime,String sqlWhere){
		String sql="select * from v_liquidationincomecalculation t where 1 = 1 "+ sqlWhere;
		if((startTime!=null&&!("").equals(startTime))&&(endTime!=null&&!("").equals(endTime))){
			sql = sql + " and to_date(t.scheduled_time,'yyyy-MM-dd') between to_date(?,'yyyy-MM-dd') and to_date(?,'yyyy-MM-dd') ";
		}



		List<Map<String,Object>> list = jt.queryForList(sql,startTime,endTime);
		//change(list);
    	return list;
    }
    
    public void change(List<Map<String,Object>> list){
    	for(int i=0;i<list.size();i++){
    		list.get(i).put("FLIGHT_ROUTE",(""+list.get(i).get("FLIGHT_ROUTE")).replaceAll("SIA","XIY"));
    		list.get(i).put("FLIGHT_ROUTE",(""+list.get(i).get("FLIGHT_ROUTE")).replaceAll("LHW","ZGC"));
    		list.get(i).put("FLIGHT_ROUTE",(""+list.get(i).get("FLIGHT_ROUTE")).replaceAll("CSX","HHA"));
    		list.get(i).put("FLIGHT_ROUTE",(""+list.get(i).get("FLIGHT_ROUTE")).replaceAll("YZO","YTY"));
    		
    		list.get(i).put("LEG",(""+list.get(i).get("LEG")).replaceAll("SIA","XIY"));
    		list.get(i).put("LEG",(""+list.get(i).get("LEG")).replaceAll("LHW","ZGC"));
    		list.get(i).put("LEG",(""+list.get(i).get("LEG")).replaceAll("CSX","HHA"));
    		list.get(i).put("LEG",(""+list.get(i).get("LEG")).replaceAll("YZO","YTY"));
    	}
    }
}

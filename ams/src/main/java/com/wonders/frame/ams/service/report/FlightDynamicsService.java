package com.wonders.frame.ams.service.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.wonders.frame.ams.dto.report.SearchDto;

@Service
public class FlightDynamicsService {
	
	@Resource
	private JdbcTemplate jt;

	
    public List<Map<String,Object>> query(SearchDto dto){
    	List<String> p = new ArrayList<String>();
    	String sql="select * from v_flightDynamics t where 1 = 1 ";
    	if(dto.getFlightDirection()!=null&&!("").equals(dto.getFlightDirection())){
    		sql = sql + " and t.flight_direction = ? ";
    		p.add(dto.getFlightDirection());
    	}
    	if((dto.getStartTime()!=null&&!("").equals(dto.getStartTime()))&&(dto.getEndTime()!=null&&!("").equals(dto.getEndTime()))){
    		sql = sql + " and to_date(t.scheduled_time,'yyyy-MM-dd') between to_date(?,'yyyy-MM-dd') and to_date(?,'yyyy-MM-dd') ";
    		p.add(dto.getStartTime());
    		p.add(dto.getEndTime());
		}
    	if(dto.getFlightIdentity()!=null&&!("").equals(dto.getFlightIdentity())){
    		sql = sql + " and t.flight_identity = ? ";
    		p.add(dto.getFlightIdentity());
    	}
    	if(dto.getRegisteration()!=null&&!("").equals(dto.getRegisteration())){
    		sql = sql + " and t.registeration = ? ";
    		p.add(dto.getRegisteration());
    	}
    	if(dto.getAirLine()!=null&&!("").equals(dto.getAirLine())){
    		sql = sql + " and t.airline = ? ";
    		p.add(dto.getAirLine());
    	}
    	if(dto.getAgent()!=null&&!("").equals(dto.getAgent())){
			sql = sql + " and t.agent_code = ? ";
    		p.add(dto.getAgent());
    	}
    	Object[] obj = new Object[p.size()];
    	for(int i=0;i<p.size();i++){
    		obj[i]=p.get(i);
    	}
    	List<Map<String,Object>> list = jt.queryForList(sql,obj);
    	return list;
    }
}

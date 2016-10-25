package com.wonders.frame.ams.service.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.wonders.frame.ams.dto.report.SearchDto;

@Service
public class ManifestService {
	
	@Resource
	private JdbcTemplate jt;

	
    public List<Map<String,Object>> query(SearchDto dto){
    	List<String> p = new ArrayList<String>();
    	String sql="select * from v_manifest t where 1 = 1 ";
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
    	if(dto.getFlightDirection()!=null&&!("").equals(dto.getFlightDirection())){
			sql = sql + " and t.flight_direction = ? ";
    		p.add(dto.getFlightDirection());
    	}
    	Object[] obj = new Object[p.size()];
    	for(int i=0;i<p.size();i++){
    		obj[i]=p.get(i);
    	}
    	List<Map<String,Object>> list = jt.queryForList(sql,obj);
    	addTotal(list);
    	
    	return list;
    }
    
    public void addTotal(List<Map<String,Object>> list){
    	if(list.size()>0){
    		Map<String,Object> map=new HashMap<String,Object>();
        	map.put("SCHEDULED_TIME", "总计");
        	map.put("AIRLINE", "");
        	map.put("FLIGHT_IDENTITY", "");
        	map.put("REGISTERATION", "");
        	map.put("AGENT", "");
        	map.put("AGENT_CODE", "");
        	int d_all=0;
        	int d_adult=0;
        	int d_child=0;
        	int d_baby=0;
        	int cargo_num=0;
        	double cargo_weight=0;
        	int mail_num=0;
        	double mail_weight=0;
        	int luggage_num=0;
        	double luggage_weight=0;
        	int vip_count=0;
        	int first_class_count=0;
        	int economic_count=0;
        	int via_all=0;
        	int via_adult=0;
        	int via_child=0;
        	int via_baby=0;
    		for(int i=0;i<list.size();i++){
    			d_all+=("".equals(list.get(i).get("D_ALL"))||list.get(i).get("D_ALL")==null)?0:Integer.parseInt(""+list.get(i).get("D_ALL"));
    			d_adult+=("".equals(list.get(i).get("D_ADULT"))||list.get(i).get("D_ADULT")==null)?0:Integer.parseInt(""+list.get(i).get("D_ADULT"));
    	    	d_child+=("".equals(list.get(i).get("D_CHILD"))||list.get(i).get("D_CHILD")==null)?0:Integer.parseInt(""+list.get(i).get("D_CHILD"));
    	    	d_baby+=("".equals(list.get(i).get("D_BABY"))||list.get(i).get("D_BABY")==null)?0:Integer.parseInt(""+list.get(i).get("D_BABY"));
    	    	cargo_num+=("".equals(list.get(i).get("CARGO_NUM"))||list.get(i).get("CARGO_NUM")==null)?0:Integer.parseInt(""+list.get(i).get("CARGO_NUM"));
    	    	cargo_weight=doubleAdd(cargo_weight,("".equals(list.get(i).get("CARGO_WEIGHT"))||list.get(i).get("CARGO_WEIGHT")==null)?0:Double.parseDouble(""+list.get(i).get("CARGO_WEIGHT")));
    	    	mail_num+=("".equals(list.get(i).get("MAIL_NUM"))||list.get(i).get("MAIL_NUM")==null)?0:Integer.parseInt(""+list.get(i).get("MAIL_NUM"));
    	    	mail_weight=doubleAdd(mail_weight,("".equals(list.get(i).get("MAIL_WEIGHT"))||list.get(i).get("MAIL_WEIGHT")==null)?0:Double.parseDouble(""+list.get(i).get("MAIL_WEIGHT")));
    	    	luggage_num+=("".equals(list.get(i).get("LUGGAGE_NUM"))||list.get(i).get("LUGGAGE_NUM")==null)?0:Integer.parseInt(""+list.get(i).get("LUGGAGE_NUM"));
    	    	luggage_weight=doubleAdd(luggage_weight,("".equals(list.get(i).get("LUGGAGE_WEIGHT"))||list.get(i).get("LUGGAGE_WEIGHT")==null)?0:Double.parseDouble(""+list.get(i).get("LUGGAGE_WEIGHT")));
    	    	vip_count+=("".equals(list.get(i).get("VIP_COUNT"))||list.get(i).get("VIP_COUNT")==null)?0:Integer.parseInt(""+list.get(i).get("VIP_COUNT"));
    	    	first_class_count+=("".equals(list.get(i).get("FIRST_CLASS_COUNT"))||list.get(i).get("FIRST_CLASS_COUNT")==null)?0:Integer.parseInt(""+list.get(i).get("FIRST_CLASS_COUNT"));
    	    	economic_count+=("".equals(list.get(i).get("ECONOMIC_COUNT"))||list.get(i).get("ECONOMIC_COUNT")==null)?0:Integer.parseInt(""+list.get(i).get("ECONOMIC_COUNT"));
    	    	via_all+=("".equals(list.get(i).get("VIA_ALL"))||list.get(i).get("VIA_ALL")==null)?0:Integer.parseInt(""+list.get(i).get("VIA_ALL"));
    	    	via_adult+=("".equals(list.get(i).get("VIA_ADULT"))||list.get(i).get("VIA_ADULT")==null)?0:Integer.parseInt(""+list.get(i).get("VIA_ADULT"));
    	    	via_child+=("".equals(list.get(i).get("VIA_CHILD"))||list.get(i).get("VIA_CHILD")==null)?0:Integer.parseInt(""+list.get(i).get("VIA_CHILD"));
    	    	via_baby+=("".equals(list.get(i).get("VIA_BABY"))||list.get(i).get("VIA_BABY")==null)?0:Integer.parseInt(""+list.get(i).get("VIA_BABY"));
    		}
    		map.put("D_ALL", d_all);
    		map.put("D_ADULT",d_adult );
    		map.put("D_CHILD", d_child);
    		map.put("D_BABY",d_baby );
    		map.put("CARGO_NUM", cargo_num);
    		map.put("CARGO_WEIGHT", cargo_weight);
    		map.put("MAIL_NUM", mail_num);
    		map.put("MAIL_WEIGHT", mail_weight);
    		map.put("LUGGAGE_NUM", luggage_num);
    		map.put("LUGGAGE_WEIGHT", luggage_weight);
    		map.put("VIP_COUNT", vip_count);
    		map.put("FIRST_CLASS_COUNT", first_class_count);
    		map.put("ECONOMIC_COUNT", economic_count);
    		map.put("VIA_ALL", via_all);
    		map.put("VIA_ADULT", via_adult);
    		map.put("VIA_CHILD", via_child);
    		map.put("VIA_BABY", via_baby);
    		list.add(map);
    		
    	}
    }
    
    public double doubleAdd(double v1, double v2) {  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.add(b2).doubleValue();  
    } 
    
}

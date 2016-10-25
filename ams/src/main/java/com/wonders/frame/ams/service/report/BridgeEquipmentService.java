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
public class BridgeEquipmentService {
	
	@Resource
	private JdbcTemplate jt;

	
    public List<Map<String,Object>> query(SearchDto dto){
    	List<String> p = new ArrayList<String>();
    	String sql="select t.scheduled_time,"
    			+ "       t.scheduled_time2,"
    			+ "       t.airline,"
    			+ "		  t.type,"
    			+ "       sum(t.bridge_electric_count) as bridge_electric_count,"
    			+ "       sum(t.bridge_electric_used_time) as bridge_electric_used_time,"
    			+ "       sum(t.bridge_electric_fee) as  bridge_electric_fee,"
    			+ "       sum(t.bridge_aircondition_count) as  bridge_aircondition_count,"
    			+ "       sum(t.bridge_aircondition_used_time) as  bridge_aircondition_used_time,"
    			+ "       sum(t.bridge_aircondition_fee) as bridge_aircondition_fee "
    			+ "from v_bridge_equipment t where 1=1 ";
    	if((dto.getStartTime()!=null&&!("").equals(dto.getStartTime()))&&(dto.getEndTime()!=null&&!("").equals(dto.getEndTime()))){
    		sql = sql + " and to_date(t.scheduled_time,'yyyy-MM-dd') between to_date(?,'yyyy-MM-dd') and to_date(?,'yyyy-MM-dd') ";
    		p.add(dto.getStartTime());
    		p.add(dto.getEndTime());
		}
    	if(dto.getStandNum()!=null&&!("").equals(dto.getStandNum())){
    		sql = sql + " and t.stand_num = ? ";
    		p.add(dto.getStandNum());
    	}
    	
    	sql = sql + "  group by t.scheduled_time,t.scheduled_time2,t.airline,t.type "
    			+ "  having sum(t.bridge_electric_count)>0 or sum(t.bridge_electric_used_time)>0 or sum(t.bridge_electric_fee)>0 or "
    			+ "    		sum(t.bridge_aircondition_count)>0 or  sum(t.bridge_aircondition_used_time)>0 or sum(t.bridge_aircondition_fee)>0"
    			+ "  order by t.scheduled_time,t.airline,t.type";
    	Object[] obj = new Object[p.size()];
    	for(int i=0;i<p.size();i++){
    		obj[i]=p.get(i);
    	}
    	List<Map<String,Object>> list = jt.queryForList(sql,obj);
    	List<Map<String,Object>> l=addTotal(list);
    	
    	return l;
    }
    
    public List<Map<String,Object>> addTotal(List<Map<String,Object>> list){
    	List<Map<String,Object>> l=new ArrayList<Map<String,Object>>();
    	if(list.size()>0){
        	double bridge_electric_count=0;
        	double bridge_electric_used_time=0;
        	double bridge_electric_fee=0;
        	double bridge_aircondition_count=0;
        	double bridge_aircondition_used_time=0;
        	double bridge_aircondition_fee=0;
        	int count=0;//记录同一天数据数
        	if(list.size()>1){//有多条数据时
        		int i=0;
	    		for(;i<list.size()-1;i++){
	    			listAdd(l,list.get(i).get("SCHEDULED_TIME2"),list.get(i).get("AIRLINE"),list.get(i).get("TYPE"),
	    					list.get(i).get("BRIDGE_ELECTRIC_COUNT"),list.get(i).get("BRIDGE_ELECTRIC_USED_TIME"),list.get(i).get("BRIDGE_ELECTRIC_FEE"),
	    					list.get(i).get("BRIDGE_AIRCONDITION_COUNT"),list.get(i).get("BRIDGE_AIRCONDITION_USED_TIME"),list.get(i).get("BRIDGE_AIRCONDITION_FEE"));
	    			bridge_electric_count=doubleAdd(bridge_electric_count,("".equals(list.get(i).get("BRIDGE_ELECTRIC_COUNT"))||list.get(i).get("BRIDGE_ELECTRIC_COUNT")==null)?0:Double.parseDouble(""+list.get(i).get("BRIDGE_ELECTRIC_COUNT")));
    				bridge_electric_used_time=doubleAdd(bridge_electric_used_time,("".equals(list.get(i).get("BRIDGE_ELECTRIC_USED_TIME"))||list.get(i).get("BRIDGE_ELECTRIC_USED_TIME")==null)?0:Double.parseDouble(""+list.get(i).get("BRIDGE_ELECTRIC_USED_TIME")));
    				bridge_electric_fee=doubleAdd(bridge_electric_fee,("".equals(list.get(i).get("BRIDGE_ELECTRIC_FEE"))||list.get(i).get("BRIDGE_ELECTRIC_FEE")==null)?0:Double.parseDouble(""+list.get(i).get("BRIDGE_ELECTRIC_FEE")));
    				bridge_aircondition_count=doubleAdd(bridge_aircondition_count,("".equals(list.get(i).get("BRIDGE_AIRCONDITION_COUNT"))||list.get(i).get("BRIDGE_AIRCONDITION_COUNT")==null)?0:Double.parseDouble(""+list.get(i).get("BRIDGE_AIRCONDITION_COUNT")));
    				bridge_aircondition_used_time=doubleAdd(bridge_aircondition_used_time,("".equals(list.get(i).get("BRIDGE_AIRCONDITION_USED_TIME"))||list.get(i).get("BRIDGE_AIRCONDITION_USED_TIME")==null)?0:Double.parseDouble(""+list.get(i).get("BRIDGE_AIRCONDITION_USED_TIME")));
    				bridge_aircondition_fee=doubleAdd(bridge_aircondition_fee,("".equals(list.get(i).get("BRIDGE_AIRCONDITION_FEE"))||list.get(i).get("BRIDGE_AIRCONDITION_FEE")==null)?0:Double.parseDouble(""+list.get(i).get("BRIDGE_AIRCONDITION_FEE")));
	    			//判断相邻的两条数据是否是同一天的
	    			if(list.get(i).get("SCHEDULED_TIME").equals(list.get(i+1).get("SCHEDULED_TIME"))){
	    				count++;
		    			if(i==list.size()-2){//最后一条数据
		    				listAdd(l,list.get(i+1).get("SCHEDULED_TIME2"),list.get(i+1).get("AIRLINE"),list.get(i+1).get("TYPE"),
		    						list.get(i+1).get("BRIDGE_ELECTRIC_COUNT"),list.get(i+1).get("BRIDGE_ELECTRIC_USED_TIME"),list.get(i+1).get("BRIDGE_ELECTRIC_FEE"),
		    						list.get(i+1).get("BRIDGE_AIRCONDITION_COUNT"),list.get(i+1).get("BRIDGE_AIRCONDITION_USED_TIME"),list.get(i+1).get("BRIDGE_AIRCONDITION_FEE"));
				    		//判断最后两条数据是否是同一天的
		    				if(list.get(i).get("SCHEDULED_TIME").equals(list.get(i+1).get("SCHEDULED_TIME"))){
	    						bridge_electric_count=doubleAdd(bridge_electric_count,("".equals(list.get(i+1).get("BRIDGE_ELECTRIC_COUNT"))||list.get(i+1).get("BRIDGE_ELECTRIC_COUNT")==null)?0:Double.parseDouble(""+list.get(i+1).get("BRIDGE_ELECTRIC_COUNT")));
		    		            bridge_electric_used_time=doubleAdd(bridge_electric_used_time,("".equals(list.get(i+1).get("BRIDGE_ELECTRIC_USED_TIME"))||list.get(i+1).get("BRIDGE_ELECTRIC_USED_TIME")==null)?0:Double.parseDouble(""+list.get(i+1).get("BRIDGE_ELECTRIC_USED_TIME")));
		    		            bridge_electric_fee=doubleAdd(bridge_electric_fee,("".equals(list.get(i+1).get("BRIDGE_ELECTRIC_FEE"))||list.get(i+1).get("BRIDGE_ELECTRIC_FEE")==null)?0:Double.parseDouble(""+list.get(i+1).get("BRIDGE_ELECTRIC_FEE")));
		    		            bridge_aircondition_count=doubleAdd(bridge_aircondition_count,("".equals(list.get(i+1).get("BRIDGE_AIRCONDITION_COUNT"))||list.get(i+1).get("BRIDGE_AIRCONDITION_COUNT")==null)?0:Double.parseDouble(""+list.get(i+1).get("BRIDGE_AIRCONDITION_COUNT")));
		    		            bridge_aircondition_used_time=doubleAdd(bridge_aircondition_used_time,("".equals(list.get(i+1).get("BRIDGE_AIRCONDITION_USED_TIME"))||list.get(i+1).get("BRIDGE_AIRCONDITION_USED_TIME")==null)?0:Double.parseDouble(""+list.get(i+1).get("BRIDGE_AIRCONDITION_USED_TIME")));
		    		            bridge_aircondition_fee=doubleAdd(bridge_aircondition_fee,("".equals(list.get(i+1).get("BRIDGE_AIRCONDITION_FEE"))||list.get(i+1).get("BRIDGE_AIRCONDITION_FEE")==null)?0:Double.parseDouble(""+list.get(i+1).get("BRIDGE_AIRCONDITION_FEE")));
				    			listAdd(l,"小计","","",bridge_electric_count,bridge_electric_used_time,bridge_electric_fee,bridge_aircondition_count,bridge_aircondition_used_time,bridge_aircondition_fee);
				    			bridge_electric_count=0;
		    		        	bridge_electric_used_time=0;
		    		        	bridge_electric_fee=0;
		    		        	bridge_aircondition_count=0;
		    		        	bridge_aircondition_used_time=0;
		    		        	bridge_aircondition_fee=0;
			    	    		count=0;
		    				}else{
		    					listAdd(l,"小计","","",list.get(i+1).get("BRIDGE_ELECTRIC_COUNT"),list.get(i+1).get("BRIDGE_ELECTRIC_USED_TIME"),list.get(i+1).get("BRIDGE_ELECTRIC_FEE"),
			    						list.get(i+1).get("BRIDGE_AIRCONDITION_COUNT"),list.get(i+1).get("BRIDGE_AIRCONDITION_USED_TIME"),list.get(i+1).get("BRIDGE_AIRCONDITION_FEE"));
		    					bridge_electric_count=0;
		    		        	bridge_electric_used_time=0;
		    		        	bridge_electric_fee=0;
		    		        	bridge_aircondition_count=0;
		    		        	bridge_aircondition_used_time=0;
		    		        	bridge_aircondition_fee=0;
			    	    		count=0;
		    				}
		    			}
	    			}else{
	    				if(count>0){
	    					listAdd(l,"小计","","",bridge_electric_count,bridge_electric_used_time,bridge_electric_fee,bridge_aircondition_count,bridge_aircondition_used_time,bridge_aircondition_fee);
	    					bridge_electric_count=0;
	    		        	bridge_electric_used_time=0;
	    		        	bridge_electric_fee=0;
	    		        	bridge_aircondition_count=0;
	    		        	bridge_aircondition_used_time=0;
	    		        	bridge_aircondition_fee=0;
		    	    		count=0;
	    				}else{
	    					listAdd(l,"小计","","",list.get(i).get("BRIDGE_ELECTRIC_COUNT"),list.get(i).get("BRIDGE_ELECTRIC_USED_TIME"),list.get(i).get("BRIDGE_ELECTRIC_FEE"),
	    		                      list.get(i).get("BRIDGE_AIRCONDITION_COUNT"),list.get(i).get("BRIDGE_AIRCONDITION_USED_TIME"),list.get(i).get("BRIDGE_AIRCONDITION_FEE"));
	    					bridge_electric_count=0;
	    		        	bridge_electric_used_time=0;
	    		        	bridge_electric_fee=0;
	    		        	bridge_aircondition_count=0;
	    		        	bridge_aircondition_used_time=0;
	    		        	bridge_aircondition_fee=0;
		    	    		count=0;
	    				}
	    				if(i==list.size()-2){//最后一条数据
	    					listAdd(l,list.get(i+1).get("SCHEDULED_TIME2"),list.get(i+1).get("AIRLINE"),list.get(i+1).get("TYPE"),
		    						list.get(i+1).get("BRIDGE_ELECTRIC_COUNT"),list.get(i+1).get("BRIDGE_ELECTRIC_USED_TIME"),list.get(i+1).get("BRIDGE_ELECTRIC_FEE"),
		    						list.get(i+1).get("BRIDGE_AIRCONDITION_COUNT"),list.get(i+1).get("BRIDGE_AIRCONDITION_USED_TIME"),list.get(i+1).get("BRIDGE_AIRCONDITION_FEE"));
				    		//判断最后两条数据是否是同一天的
		    				if(list.get(i).get("SCHEDULED_TIME").equals(list.get(i+1).get("SCHEDULED_TIME"))){
		    					bridge_electric_count=doubleAdd(bridge_electric_count,("".equals(list.get(i+1).get("BRIDGE_ELECTRIC_COUNT"))||list.get(i+1).get("BRIDGE_ELECTRIC_COUNT")==null)?0:Double.parseDouble(""+list.get(i+1).get("BRIDGE_ELECTRIC_COUNT")));
		    		            bridge_electric_used_time=doubleAdd(bridge_electric_used_time,("".equals(list.get(i+1).get("BRIDGE_ELECTRIC_USED_TIME"))||list.get(i+1).get("BRIDGE_ELECTRIC_USED_TIME")==null)?0:Double.parseDouble(""+list.get(i+1).get("BRIDGE_ELECTRIC_USED_TIME")));
		    		            bridge_electric_fee=doubleAdd(bridge_electric_fee,("".equals(list.get(i+1).get("BRIDGE_ELECTRIC_FEE"))||list.get(i+1).get("BRIDGE_ELECTRIC_FEE")==null)?0:Double.parseDouble(""+list.get(i+1).get("BRIDGE_ELECTRIC_FEE")));
		    		            bridge_aircondition_count=doubleAdd(bridge_aircondition_count,("".equals(list.get(i+1).get("BRIDGE_AIRCONDITION_COUNT"))||list.get(i+1).get("BRIDGE_AIRCONDITION_COUNT")==null)?0:Double.parseDouble(""+list.get(i+1).get("BRIDGE_AIRCONDITION_COUNT")));
		    		            bridge_aircondition_used_time=doubleAdd(bridge_aircondition_used_time,("".equals(list.get(i+1).get("BRIDGE_AIRCONDITION_USED_TIME"))||list.get(i+1).get("BRIDGE_AIRCONDITION_USED_TIME")==null)?0:Double.parseDouble(""+list.get(i+1).get("BRIDGE_AIRCONDITION_USED_TIME")));
		    		            bridge_aircondition_fee=doubleAdd(bridge_aircondition_fee,("".equals(list.get(i+1).get("BRIDGE_AIRCONDITION_FEE"))||list.get(i+1).get("BRIDGE_AIRCONDITION_FEE")==null)?0:Double.parseDouble(""+list.get(i+1).get("BRIDGE_AIRCONDITION_FEE")));
		    		            listAdd(l,"小计","","",bridge_electric_count,bridge_electric_used_time,bridge_electric_fee,bridge_aircondition_count,bridge_aircondition_used_time,bridge_aircondition_fee);
		    		            bridge_electric_count=0;
		    		        	bridge_electric_used_time=0;
		    		        	bridge_electric_fee=0;
		    		        	bridge_aircondition_count=0;
		    		        	bridge_aircondition_used_time=0;
		    		        	bridge_aircondition_fee=0;
			    	    		count=0;
		    				}else{
		    					listAdd(l,"小计","","",list.get(i+1).get("BRIDGE_ELECTRIC_COUNT"),list.get(i+1).get("BRIDGE_ELECTRIC_USED_TIME"),list.get(i+1).get("BRIDGE_ELECTRIC_FEE"),
			    						list.get(i+1).get("BRIDGE_AIRCONDITION_COUNT"),list.get(i+1).get("BRIDGE_AIRCONDITION_USED_TIME"),list.get(i+1).get("BRIDGE_AIRCONDITION_FEE"));
		    					bridge_electric_count=0;
		    		        	bridge_electric_used_time=0;
		    		        	bridge_electric_fee=0;
		    		        	bridge_aircondition_count=0;
		    		        	bridge_aircondition_used_time=0;
		    		        	bridge_aircondition_fee=0;
			    	    		count=0;
		    				}
		    			}
	    				
	    			}
	    		}
        	}else{//当只有一条数据时
        		listAdd(l,list.get(0).get("SCHEDULED_TIME2"),list.get(0).get("AIRLINE"),list.get(0).get("TYPE"),
                        list.get(0).get("BRIDGE_ELECTRIC_COUNT"),list.get(0).get("BRIDGE_ELECTRIC_USED_TIME"),list.get(0).get("BRIDGE_ELECTRIC_FEE"),
                        list.get(0).get("BRIDGE_AIRCONDITION_COUNT"),list.get(0).get("BRIDGE_AIRCONDITION_USED_TIME"),list.get(0).get("BRIDGE_AIRCONDITION_FEE"));
	    		listAdd(l,"小计","","",list.get(0).get("BRIDGE_ELECTRIC_COUNT"),list.get(0).get("BRIDGE_ELECTRIC_USED_TIME"),list.get(0).get("BRIDGE_ELECTRIC_FEE"),
	                      list.get(0).get("BRIDGE_AIRCONDITION_COUNT"),list.get(0).get("BRIDGE_AIRCONDITION_USED_TIME"),list.get(0).get("BRIDGE_AIRCONDITION_FEE"));
        	}
    	}
    	return l;
    }
    
    
    public void listAdd(List<Map<String,Object>> list,Object obj1,Object obj2,Object obj3,Object obj4,Object obj5,Object obj6,Object obj7,Object obj8,Object obj9){
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("SCHEDULED_TIME2", obj1);
    	map.put("AIRLINE", obj2);
    	map.put("TYPE", obj3);
		map.put("BRIDGE_ELECTRIC_COUNT", obj4);
		map.put("BRIDGE_ELECTRIC_USED_TIME", obj5);
		map.put("BRIDGE_ELECTRIC_FEE", obj6);
		map.put("BRIDGE_AIRCONDITION_COUNT", obj7);
		map.put("BRIDGE_AIRCONDITION_USED_TIME", obj8);
		map.put("BRIDGE_AIRCONDITION_FEE", obj9);
		list.add(map);
    }
    
    
    public double doubleAdd(double v1, double v2) {  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.add(b2).doubleValue();  
    } 
    
}

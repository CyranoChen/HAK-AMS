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
public class BridgeUsedService {
	
	@Resource
	private JdbcTemplate jt;

	
    public List<Map<String,Object>> query(SearchDto dto){
    	List<String> p = new ArrayList<String>();
    	String sql="select t.scheduled_time,"
    			+ "       t.scheduled_time2,"
    			+ "       t.airline,"
    			+ "       sum(t.twin_time) as twin_time,"
    			+ "       sum(t.twin_fee) as twin_fee,"
    			+ "       sum(t.single_time) as  single_time,"
    			+ "       sum(t.single_fee) as single_fee "
    			+ "from v_bridge_used t where 1=1 ";
    	if((dto.getStartTime()!=null&&!("").equals(dto.getStartTime()))&&(dto.getEndTime()!=null&&!("").equals(dto.getEndTime()))){
    		sql = sql + " and to_date(t.scheduled_time,'yyyy-MM-dd') between to_date(?,'yyyy-MM-dd') and to_date(?,'yyyy-MM-dd') ";
    		p.add(dto.getStartTime());
    		p.add(dto.getEndTime());
		}
    	if(dto.getStandNum()!=null&&!("").equals(dto.getStandNum())){
    		sql = sql + " and t.stand_num = ? ";
    		p.add(dto.getStandNum());
    	}
    	
    	sql = sql + "  group by t.scheduled_time,t.scheduled_time2,t.airline "
    			+ "  having sum(t.twin_time)>0 or sum(t.twin_fee)>0 or sum(t.single_time)>0 or sum(t.single_fee)>0 "
    			+ "  order by t.scheduled_time";
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
        	double twin_time=0;
        	double twin_fee=0;
        	double single_time=0;
        	double single_fee=0;
        	int count=0;//记录同一天数据数
        	if(list.size()>1){//有多条数据时
        		int i=0;
	    		for(;i<list.size()-1;i++){
	    			listAdd(l,list.get(i).get("SCHEDULED_TIME2"),list.get(i).get("AIRLINE"),list.get(i).get("TWIN_TIME"),list.get(i).get("TWIN_FEE"),list.get(i).get("SINGLE_TIME"),list.get(i).get("SINGLE_FEE"));
	    			twin_time=doubleAdd(twin_time,("".equals(list.get(i).get("TWIN_TIME"))||list.get(i).get("TWIN_TIME")==null)?0:Double.parseDouble(""+list.get(i).get("TWIN_TIME")));
	    			twin_fee=doubleAdd(twin_fee,("".equals(list.get(i).get("TWIN_FEE"))||list.get(i).get("TWIN_FEE")==null)?0:Double.parseDouble(""+list.get(i).get("TWIN_FEE")));
	    			single_time=doubleAdd(single_time,("".equals(list.get(i).get("SINGLE_TIME"))||list.get(i).get("SINGLE_TIME")==null)?0:Double.parseDouble(""+list.get(i).get("SINGLE_TIME")));
	    			single_fee=doubleAdd(single_fee,("".equals(list.get(i).get("SINGLE_FEE"))||list.get(i).get("SINGLE_FEE")==null)?0:Double.parseDouble(""+list.get(i).get("SINGLE_FEE")));
	    			//判断相邻的两条数据是否是同一天的
	    			if(list.get(i).get("SCHEDULED_TIME").equals(list.get(i+1).get("SCHEDULED_TIME"))){
	    				count++;
		    			if(i==list.size()-2){//最后一条数据
		    				listAdd(l,list.get(i+1).get("SCHEDULED_TIME2"),list.get(i+1).get("AIRLINE"),list.get(i+1).get("TWIN_TIME"),list.get(i+1).get("TWIN_FEE"),list.get(i+1).get("SINGLE_TIME"),list.get(i+1).get("SINGLE_FEE"));
				    		//判断最后两条数据是否是同一天的
		    				if(list.get(i).get("SCHEDULED_TIME").equals(list.get(i+1).get("SCHEDULED_TIME"))){
		    					twin_time=doubleAdd(twin_time,("".equals(list.get(i+1).get("TWIN_TIME"))||list.get(i+1).get("TWIN_TIME")==null)?0:Double.parseDouble(""+list.get(i+1).get("TWIN_TIME")));
				    			twin_fee=doubleAdd(twin_fee,("".equals(list.get(i+1).get("TWIN_FEE"))||list.get(i+1).get("TWIN_FEE")==null)?0:Double.parseDouble(""+list.get(i+1).get("TWIN_FEE")));
				    			single_time=doubleAdd(single_time,("".equals(list.get(i+1).get("SINGLE_TIME"))||list.get(i+1).get("SINGLE_TIME")==null)?0:Double.parseDouble(""+list.get(i+1).get("SINGLE_TIME")));
				    			single_fee=doubleAdd(single_fee,("".equals(list.get(i+1).get("SINGLE_FEE"))||list.get(i+1).get("SINGLE_FEE")==null)?0:Double.parseDouble(""+list.get(i+1).get("SINGLE_FEE")));
				    			listAdd(l,"小计","",twin_time,twin_fee,single_time,single_fee);
				    			twin_time=0;
			    	        	twin_fee=0;
			    	        	single_time=0;
			    	        	single_fee=0;
			    	    		count=0;
		    				}else{
		    					listAdd(l,"小计","",list.get(i+1).get("TWIN_TIME"),list.get(i+1).get("TWIN_FEE"),list.get(i+1).get("SINGLE_TIME"),list.get(i+1).get("SINGLE_FEE"));
		    					twin_time=0;
			    	        	twin_fee=0;
			    	        	single_time=0;
			    	        	single_fee=0;
			    	    		count=0;
		    				}
		    			}
	    			}else{
	    				if(count>0){
	    					listAdd(l,"小计","",twin_time,twin_fee,single_time,single_fee);
		    	    		twin_time=0;
		    	        	twin_fee=0;
		    	        	single_time=0;
		    	        	single_fee=0;
		    	    		count=0;
	    				}else{
	    					listAdd(l,"小计","",list.get(i).get("TWIN_TIME"),list.get(i).get("TWIN_FEE"),list.get(i).get("SINGLE_TIME"),list.get(i).get("SINGLE_FEE"));
	    					twin_time=0;
		    	        	twin_fee=0;
		    	        	single_time=0;
		    	        	single_fee=0;
		    	    		count=0;
    					}
	    				if(i==list.size()-2){//最后一条数据
				    		listAdd(l,list.get(i+1).get("SCHEDULED_TIME2"),list.get(i+1).get("AIRLINE"),list.get(i+1).get("TWIN_TIME"),list.get(i+1).get("TWIN_FEE"),list.get(i+1).get("SINGLE_TIME"),list.get(i+1).get("SINGLE_FEE"));
				    		//判断最后两条数据是否是同一天的
		    				if(list.get(i).get("SCHEDULED_TIME").equals(list.get(i+1).get("SCHEDULED_TIME"))){
		    					twin_time=doubleAdd(twin_time,("".equals(list.get(i+1).get("TWIN_TIME"))||list.get(i+1).get("TWIN_TIME")==null)?0:Double.parseDouble(""+list.get(i+1).get("TWIN_TIME")));
				    			twin_fee=doubleAdd(twin_fee,("".equals(list.get(i+1).get("TWIN_FEE"))||list.get(i+1).get("TWIN_FEE")==null)?0:Double.parseDouble(""+list.get(i+1).get("TWIN_FEE")));
				    			single_time=doubleAdd(single_time,("".equals(list.get(i+1).get("SINGLE_TIME"))||list.get(i+1).get("SINGLE_TIME")==null)?0:Double.parseDouble(""+list.get(i+1).get("SINGLE_TIME")));
				    			single_fee=doubleAdd(single_fee,("".equals(list.get(i+1).get("SINGLE_FEE"))||list.get(i+1).get("SINGLE_FEE")==null)?0:Double.parseDouble(""+list.get(i+1).get("SINGLE_FEE")));
				    			listAdd(l,"小计","",twin_time,twin_fee,single_time,single_fee);
				    			twin_time=0;
			    	        	twin_fee=0;
			    	        	single_time=0;
			    	        	single_fee=0;
			    	    		count=0;
		    				}else{
		    					listAdd(l,"小计","",list.get(i+1).get("TWIN_TIME"),list.get(i+1).get("TWIN_FEE"),list.get(i+1).get("SINGLE_TIME"),list.get(i+1).get("SINGLE_FEE"));
		    					twin_time=0;
			    	        	twin_fee=0;
			    	        	single_time=0;
			    	        	single_fee=0;
			    	    		count=0;
		    				}
		    			}
	    				
	    			}
	    		}
        	}else{//当只有一条数据时
	    		listAdd(l,list.get(0).get("SCHEDULED_TIME2"),list.get(0).get("AIRLINE"),list.get(0).get("TWIN_TIME"),list.get(0).get("TWIN_FEE"),list.get(0).get("SINGLE_TIME"),list.get(0).get("SINGLE_FEE"));
	    		listAdd(l,"小计","",list.get(0).get("TWIN_TIME"),list.get(0).get("TWIN_FEE"),list.get(0).get("SINGLE_TIME"),list.get(0).get("SINGLE_FEE"));
        	}
    	}
    	return l;
    }
    
    
    public void listAdd(List<Map<String,Object>> list,Object obj1,Object obj2,Object obj3,Object obj4,Object obj5,Object obj6){
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("SCHEDULED_TIME2", obj1);
    	map.put("AIRLINE", obj2);
    	map.put("TWIN_TIME", obj3);
		map.put("TWIN_FEE", obj4);
		map.put("SINGLE_TIME", obj5);
		map.put("SINGLE_FEE", obj6);
		list.add(map);
    }
    
    
    public double doubleAdd(double v1, double v2) {  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.add(b2).doubleValue();  
    } 
    
}

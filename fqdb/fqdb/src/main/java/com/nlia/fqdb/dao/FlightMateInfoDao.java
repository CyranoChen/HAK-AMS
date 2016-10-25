package com.nlia.fqdb.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightMateInfo;
import com.wonders.aiis.core.dao.GenericDao;

@Repository
public class FlightMateInfoDao extends GenericDao<FlightMateInfo, Long>{
	public List<FlightMateInfo> findByFlightBases(List<Long> ids,String stat){
    	List<FlightMateInfo> fmis=new ArrayList<FlightMateInfo>();
    	
    	int loop=ids.size()/999;
	   	int ys=ids.size()%999;
	   	if(ys==0){
	   		loop--;
	   	}
	   	
	   	for(int i=0;i<=loop;i++){
	       	int toIndex=(i+1)*999;
	       	if(i==loop){
	       		toIndex=ids.size();
	       	}       
	       	
	        StringBuffer sb = new StringBuffer();
	        sb.append("from FlightMateInfo t  where t.removeFlag='1' and t.flightBaseId in :ids");
	        if(stat!=null && !stat.isEmpty()){
	            sb.append(" and t.stat='");
	            sb.append(stat);
	            sb.append("'");
	        }
	
	        Query query = entityManager.createQuery(sb.toString());      
	        
         	query.setParameter("ids", ids.subList(i*999, toIndex));
         	
         	List<FlightMateInfo> fmisTmp=query.getResultList();
         	
         	fmis.addAll(fmisTmp);
         	
	   	}
         
        return fmis;
        
    }

    /**
     * add by wangsq 20151224
     * @param ids
     * @return
     */
    public List<FlightMateInfo> findByIds(List<Long> ids){
       /*
    	StringBuffer sb = new StringBuffer();
        sb.append("from FlightMateInfo t  where ");
        sb.append("  (1<>1 ");
        for(Long id : ids){
            sb.append(" or t.id = ").append(id);
        }
        sb.append(")");
        Query query = entityManager.createQuery(sb.toString());
        return query.getResultList();
        */
    	List<FlightMateInfo> fmis=new ArrayList<FlightMateInfo>();
    	
    	int loop=ids.size()/999;
	   	int ys=ids.size()%999;
	   	if(ys==0){
	   		loop--;
	   	}
	   	
	   	for(int i=0;i<=loop;i++){
	       	int toIndex=(i+1)*999;
	       	if(i==loop){
	       		toIndex=ids.size();
	       	}       
	       	
	        Query query = entityManager.createQuery("from FlightMateInfo t  where t.removeFlag='1' and t.id in :ids");      
	        
         	query.setParameter("ids", ids.subList(i*999, toIndex));
         	
         	List<FlightMateInfo> fmisTmp=query.getResultList();
         	
         	fmis.addAll(fmisTmp);
         	
	   	}
	   	
	   	List<Long> idss=new ArrayList<Long>();
	   	for(FlightMateInfo fmi:fmis){
	   		if(!idss.contains(fmi.getId())){
	   			idss.add(fmi.getId());
	   		}
	   	}
	   	
	   	System.out.println("+++++++++++++ids size:"+ids.size());
	   	System.out.println("+++++++++++++fmis size:"+fmis.size());
        System.out.println("+++++++++++++not same fmis ids size:"+idss.size()); 
        return fmis;
        
    }
    
    public int clear(Date startTime,Date endTime){
    	
    	String sql="update flight_mate_info set remove_flag = 0 "
    			+ "where id in( "
    			+ "select f.id from flight_mate_info f , flight_base b "
    			+ "where b.id = f.flight_base_id and b.remove_flag = 0 "
    			+ "and f.scheduled_time between :startTime and :endTime"
    			+ ")";
    	
    	Query query = entityManager.createNativeQuery(sql).setParameter("startTime", startTime).setParameter("endTime", endTime);
    	
    	int result = query.executeUpdate(); //影响的记录数
    	
    	return result;
    }
}

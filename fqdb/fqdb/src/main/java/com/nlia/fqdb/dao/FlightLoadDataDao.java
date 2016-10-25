package com.nlia.fqdb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nlia.fqdb.entity.FlightLoadData;
import com.nlia.fqdb.util.DateUtils;
import com.wonders.aiis.core.dao.GenericDao;

@Repository
public class FlightLoadDataDao extends GenericDao<FlightLoadData, Long>{
    /*
     * 通过FlightLoadData.flight_data_id去更新flight_data表的客货行邮数据数据 
     */
    public void updateFlightDataByFdids(List<Long> ids) {
        StringBuffer hql = new StringBuffer();
        hql.append("update flight_data fd set(TOTAL_ADULT_PASSGER_NUM,TOTAL_CHILDREN_NUM,total_infant_passger_num,TRANSIT_ADULT_PASSGER_NUM,");
        hql.append("transit_child_passger_num,transit_infant_passger_num,TOTALL_BAGGAGE_WEIGHT,TOTALL_MAIL_WEIGHT,total_cargo_weight)=");
        hql.append("(select sum(cr),sum(et),sum(ye),sum(crwh),sum(etwh),sum(yewh),sum(xl),sum(yj),sum(hw) from FLIGHT_LOAD_DATA fld ");
        //hql.append(" where fld.flight_data_id=fd.id and fld.flight_data_id in (:ids) group by flight_data_id)where fd.id in (:ids2)");
       //        Query query = entityManager.createNativeQuery(hql.toString())
//                .setParameter("ids", ids).setParameter("ids2", ids);
        hql.append(" where fld.flight_data_id=fd.id group by flight_data_id)where fd.id in (:ids2)");
        Query query = entityManager.createNativeQuery(hql.toString()).setParameter("ids2", ids);
        query.executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
    public List<FlightLoadData> findFlightLoadDataFromView(String startTime,String endTime){
        StringBuffer hql=new StringBuffer();                                                                              
        hql.append("  select * from v_fqdb_export_data   where sjrq >=:startTime and sjrq <=:endTime  order by flight_base_id asc,hd asc ");
    Query query = entityManager.createNativeQuery(hql.toString())
            .setParameter("startTime", startTime)
            .setParameter("endTime", endTime);

//    //返回MAP方式 
//    query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
//    List rows = query.getResultList();  
//    for (Object obj : rows) {  
//        Map row = (Map) obj;  
//        System.out.println("id = " + row.get("ID"));  
//        System.out.println("name = " + row.get("NAME"));  
//        System.out.println("age = " + row.get("AGE"));  
//    }  
    
        List<Object[]> list = query.getResultList();            
        List<FlightLoadData> resultList = new ArrayList<FlightLoadData>();
        for (Object[] objects : list) {
            FlightLoadData flightLoadData= new FlightLoadData();
            flightLoadData.setJhrq(objects[0] != null ? DateUtils.String2Date((String)objects[0] + " " + (String)objects[21]): null);
            flightLoadData.setSjrq(objects[1] != null ? DateUtils.String2Date((String)objects[1] + " " + (String)objects[22]): null);
            flightLoadData.setCyr( objects[2] != null ? (String)objects[2]: null);
            flightLoadData.setDm(  objects[3] != null ? (String)objects[3]: null);
            flightLoadData.setSs(  objects[4] != null ? (String)objects[4]: null);
            flightLoadData.setHbh( objects[5] != null ? (String)objects[5]: null);
            flightLoadData.setJx(  objects[6] != null ? (String)objects[6]: null);
            flightLoadData.setHbxz(objects[7] != null ? (String)objects[7]: null);
            flightLoadData.setHx(  objects[8] != null ? (String)objects[8]: null);
            flightLoadData.setHx0( objects[9] != null ? (String)objects[9]: null);
            flightLoadData.setHxfl(objects[10] != null ? (String)objects[10]: null);
            flightLoadData.setHd(  objects[11] != null ? (String)objects[11]: null);
            flightLoadData.setHdfl(objects[12] != null ? (String)objects[12]: null);
            flightLoadData.setZdyz(objects[13] != null ? ((BigDecimal)objects[13]).longValue(): null);
            flightLoadData.setZdzw(objects[14] != null ? ((BigDecimal)objects[14]).longValue(): null);
            flightLoadData.setPeyz(objects[15] != null ? ((BigDecimal)objects[15]).longValue(): null);
            flightLoadData.setPezw(objects[16] != null ? ((BigDecimal)objects[16]).longValue(): null);
            flightLoadData.setKgyz(objects[17] != null ? ((BigDecimal)objects[17]).longValue(): null);
            flightLoadData.setKgzw(objects[18] != null ? ((BigDecimal)objects[18]).longValue(): null);
            flightLoadData.setIo(  objects[19] != null ? (String)objects[19]: null);
            flightLoadData.setJcn( objects[20] != null ? ((BigDecimal)objects[20]).longValue(): null);
            flightLoadData.setJqjd(objects[21] != null ? (String)objects[21]: null);
            flightLoadData.setQjsj(objects[22] != null ? (String)objects[22]: null);
            flightLoadData.setCr(  objects[23] != null ? ((BigDecimal)objects[23]).longValue(): null);
            flightLoadData.setEt(  objects[24] != null ? ((BigDecimal)objects[24]).longValue(): null);
            flightLoadData.setYe(  objects[25] != null ? ((BigDecimal)objects[25]).longValue(): null);
            flightLoadData.setCrwh(objects[26] != null ? ((BigDecimal)objects[26]).longValue(): null);
            flightLoadData.setEtwh(objects[27] != null ? ((BigDecimal)objects[27]).longValue(): null);
            flightLoadData.setYewh(objects[28] != null ? ((BigDecimal)objects[28]).longValue(): null);
            flightLoadData.setXl(  objects[29] != null ? ((BigDecimal)objects[29]).longValue(): null);
            flightLoadData.setYj(  objects[30] != null ? ((BigDecimal)objects[30]).longValue(): null);
            flightLoadData.setHw(  objects[31] != null ? ((BigDecimal)objects[31]).longValue(): null);
            flightLoadData.setPbm( objects[32] != null ? (String)objects[32]: null);
            flightLoadData.setXg(  objects[33] != null ? (String)objects[33]: null);
            flightLoadData.setBc(  objects[34] != null ? (String)objects[34]: null);
            flightLoadData.setJh(  objects[35] != null ? (String)objects[35]: null);
            flightLoadData.setWjhz(objects[36] != null ? ((BigDecimal)objects[36]).longValue(): null);
            flightLoadData.setXljs(objects[37] != null ? ((BigDecimal)objects[37]).longValue(): null);
            flightLoadData.setOperationstatus( objects[38] != null ? (String)objects[38]: null );
            flightLoadData.setFlightstatus(    objects[39] != null ? (String)objects[39]: null);
            flightLoadData.setFlightbaseid(   objects[40] != null ? ((BigDecimal)objects[40]).longValue(): null);
            flightLoadData.setFlightdataid(   objects[41] != null ? ((BigDecimal)objects[41]).longValue(): null );
            flightLoadData.setFlightIdentity(  objects[42] != null ? (String)objects[42]: null);
            resultList.add(flightLoadData);
        }
        return resultList;
    }

    public List<FlightLoadData> findByFbIds(List<Long> ids) {
    	
        StringBuffer sb = new StringBuffer();
        sb.append("from FlightLoadData  where ");
        StringBuffer sbCondition = new StringBuffer();
       // sb.append("  (1<>1 ");
        int loop=ids.size()/999;
	   	int ys=ids.size()%999;
	   	if(ys==0){
	   		loop--;
	   	}
        for(int i=0;i<=loop;i++){
        	if(sbCondition.length()>0){
        		sbCondition.append(" or ");
        	}
            sbCondition.append(" flightbaseid in :ids"+i);
        }
//        sb.append(")");
       // System.out.println(sb.toString());
        Query query = entityManager.createQuery(sb.append(sbCondition).toString());
        for(int i=0;i<=loop;i++){
        	int toIndex=(i+1)*999;
        	if(i==loop){
        		toIndex=ids.size();
        	}
        	query.setParameter("ids"+i, ids.subList(i*999, toIndex));
        }
        
        return query.getResultList();
    }
    
    /*
    public List<FlightLoadData> findByFbIds(List<Long> ids) {
   	 List<FlightLoadData> result=new ArrayList<FlightLoadData>();
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

       	Query query = entityManager.createQuery("from FlightLoadData  where flightbaseid in :ids");

       	query.setParameter("ids", ids.subList(i*999, toIndex));
       	
            List<FlightLoadData> tmp=query.getResultList();
            
            result.addAll(tmp);
       }
       
       return result;
   }
   */
    
    public int clear(Date startTime,Date endTime){
    	
    	String sql="delete flight_load_data "
    			+ "where flight_base_id in( "
    			+ "select id from flight_base b "
    			+ "where b.remove_flag = 0 "
    			+ "and b.flight_scheduled_date between :startTime and :endTime"
    			+ ")";
    	
    	Query query = entityManager.createNativeQuery(sql).setParameter("startTime", startTime).setParameter("endTime", endTime);
    	
    	int result = query.executeUpdate(); //影响的记录数
    	
    	return result;
    }
    public static void main(String[] args){
    	List<Integer> ids= new ArrayList<Integer>();
    	for(int i=1;i<=9999;i++){
    		ids.add(i);
    	}
    	 StringBuffer sbCondition = new StringBuffer();
    	 int loop=ids.size()/999;
    	 int ys=ids.size()%999;
    	 if(ys==0){
    		 loop--;
    	 }
    	 for(int i=0;i<=loop;i++){
         	if(sbCondition.length()>0){
         		sbCondition.append(" or ");
         	}
            sbCondition.append(" flightbaseid in :ids"+i);
            
         	int toIndex=(i+1)*999;
         	if(i==loop){
         		toIndex=ids.size();
         	}
         	List<Integer> nids=ids.subList(i*999, toIndex);

         	System.out.println("params of ids"+i+":"+nids.get(0)+"~"+nids.get(nids.size()-1));

         }
    	 System.out.println(sbCondition.toString());
    		
    }
}

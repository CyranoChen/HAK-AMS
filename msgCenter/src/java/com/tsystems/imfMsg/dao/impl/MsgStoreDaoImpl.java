/** 
 * Copyright (c) 1995-2011 Tsystems Information Co.,Ltd. 
 * 1518 Lianhang Rd,Shanghai 201112.P.R.C.
 * All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Tsystems.
 * You shall not disclose such Confidential Information and shall use it only in accordance 
 * with the terms of the license agreement you entered into with Tsystems. 
 *
 */

package com.tsystems.imfMsg.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.tsystems.imfMsg.dao.MsgStoreDao;
import com.tsystems.imfMsg.entity.bo.MsgStore;
import com.tsystems.imfMsg.entity.vo.Page;
import com.tsystems.imfMsg.util.Constant;


@Repository("msgStoreDao")
public class MsgStoreDaoImpl implements MsgStoreDao {
	private static final Logger log = LoggerFactory.getLogger(MsgStoreDaoImpl.class);
	private HibernateTemplate hibernateTemplate;
	
	public void save(MsgStore msgStore){
		getHibernateTemplate().save(msgStore);
	}
	public void delete(MsgStore msgStore){
		getHibernateTemplate().delete(msgStore);
	}
	public void update(MsgStore msgStore){
		getHibernateTemplate().update(msgStore);
	}
	
	public void updateKeyValueByHql(MsgStore msgStore){
		String  hql = "update MsgStore t set t.messageSequenceId=:messageSequenceId,t.messageType=:messageType," +
		"t.serviceType=:serviceType,t.operationMode=:operationMode,t.flightScheduledDate=:flightScheduledDate," +
		"t.flightIdentity=:flightIdentity,t.flightDirection=:flightDirection,t.keyContent=:keyContent" +
		",t.keyStatus=:keyStatus where t.id=:id";
		Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction tran=session.beginTransaction();
		try{
			Query query=session.createQuery(hql);
			query.setParameter("messageSequenceId", msgStore.getMessageSequenceId());
			query.setParameter("messageType", msgStore.getMessageType());
			query.setParameter("serviceType", msgStore.getServiceType());
			query.setParameter("operationMode", msgStore.getOperationMode());
			query.setParameter("flightScheduledDate", msgStore.getFlightScheduledDate());
			query.setParameter("flightIdentity", msgStore.getFlightIdentity());
			query.setParameter("flightDirection", msgStore.getFlightDirection());
			query.setParameter("keyContent", msgStore.getKeyContent());
			query.setParameter("keyStatus", msgStore.getKeyStatus());
			query.setParameter("id", msgStore.getId());
			query.executeUpdate();			
			tran.commit();
		}catch(Exception e){
			tran.rollback();
			e.printStackTrace();
		}finally{			
	        session.flush(); 
	        session.close();
		}
	}

	public void updateStatusByHql(MsgStore msgStore){
		String  hql = "update MsgStore t set t.status=:status,t.operateTime=:operateTime where t.id=:id";
		Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction tran=session.beginTransaction();
		try{
			Query query=session.createQuery(hql);
			query.setParameter("status", msgStore.getStatus());
			query.setParameter("operateTime",msgStore.getOperateTime());
			query.setParameter("id", msgStore.getId());
			query.executeUpdate();			
			tran.commit();
		}catch(Exception e){
			tran.rollback();
			e.printStackTrace();
		}finally{			
	        session.flush(); 
	        session.close();
		}
	}
	public MsgStore loadById(String id){
		MsgStore instance=null;
		List<MsgStore> list=this.getHibernateTemplate().find("from MsgStore a where a.removed=0 and a.id=?",id); 
	    for(int i=0;i<list.size();i++)
	    {
	    	instance=(MsgStore)list.get(i);
	    }
        return instance; 		
	}	
	
	public MsgStore findLatestMsgStoreWithSamePK(String receiver,String scheduledDate,String identity,String direction){
		List<MsgStore> list= new ArrayList<MsgStore>();
		String hql="from MsgStore a where a.removed=0 and a.receiver=? and a.flightScheduledDate=? and a.flightIdentity=? and a.flightDirection=? order by a.createTime desc";
		list=this.getHibernateTemplate().find(hql,new Object[] {receiver,scheduledDate,identity,direction}); 
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}			
	
	
	public Page findListByPage(HashMap<String, String> filter,String sortWay){
		Page page=null;
		List<MsgStore> list = new ArrayList<MsgStore>();	
		Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction tran=session.beginTransaction();
		try{
			Criteria query=session.createCriteria(MsgStore.class);
			query.add( Restrictions.eq("removed", Long.parseLong("0")));
			if (!filter.isEmpty()) {
				for (Iterator<String> i = filter.keySet().iterator(); i.hasNext();) {

					String paramName = i.next();

					if(paramName.equalsIgnoreCase("id")||paramName.equalsIgnoreCase("serviceType")||paramName.equalsIgnoreCase("messageSequenceId")
							||paramName.equalsIgnoreCase("messageType")||paramName.equalsIgnoreCase("operationMode")
							||paramName.equalsIgnoreCase("flightIdentity")||paramName.equalsIgnoreCase("flightDirection")){
						query.add( Restrictions.eq(paramName, filter.get(paramName)));
						
					}else if(paramName.equalsIgnoreCase("status")||paramName.equalsIgnoreCase("removed")||paramName.equalsIgnoreCase("keyStatus")){
						query.add( Restrictions.eq(paramName, Long.parseLong(filter.get(paramName))));
						
					}else if(paramName.contains("Time")){
						
						SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						
						String fieldName=paramName.substring(0, paramName.length()-1);
						String sDate=filter.get(paramName);
						
						boolean isStartTime=paramName.endsWith("s");				
						if(isStartTime){
							query.add( Restrictions.ge(fieldName,dateFormat.parse(sDate+" 00:00:00")));
						}else{
							query.add( Restrictions.le(fieldName,dateFormat.parse(sDate+" 23:59:59")));
						}												
						
					}else if(paramName.contains("Date")){						
						String fieldName=paramName.substring(0, paramName.length()-1);
						String sDate=filter.get(paramName);
						
						boolean isStartTime=paramName.endsWith("s");				
						if(isStartTime){
							query.add( Restrictions.ge(fieldName,sDate));
						}else{
							query.add( Restrictions.le(fieldName,sDate));
						}							
												
					}else{
						if(!paramName.equalsIgnoreCase("pageNo") && !paramName.equalsIgnoreCase("pageSize")){
							query.add( Restrictions.like(paramName, filter.get(paramName),MatchMode.ANYWHERE));
						}
					}

				}
			}
			

	
			int totalCount=((Integer) query.setProjection(Projections.rowCount()).uniqueResult()).intValue(); 
			query.setProjection(null);
			query.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
			
			if(sortWay.equalsIgnoreCase("asc")){
				query.addOrder( Order.asc("createTime") );
			}else{
				query.addOrder( Order.desc("createTime") );
			}
			
			int pageNo=0;
			int pageSize=0;
			if(filter.get("pageNo")!=null && Integer.parseInt(filter.get("pageNo"))>0){
				pageNo=Integer.parseInt(filter.get("pageNo"));
				pageSize=filter.get("pageSize")==null?Constant.PAGE_SIZE:Integer.parseInt(filter.get("pageSize"));
				query.setFirstResult(pageSize*(pageNo-1));
				query.setMaxResults(pageSize);
			}else{
				query.setMaxResults(500);
			}
			
			list=query.list(); 

			if(pageNo==0){
				page=new Page(totalCount);
			}else{
				page=new Page(totalCount,pageSize);
				page.refresh(pageNo);
			}			
		
			page.setResult(list);
			
			tran.commit();
		}catch(Exception e){
			tran.rollback();
			log.error("unexcepted Exception when {}","findListByPage",e);
			//e.printStackTrace();
		}finally{
	        session.flush(); 
	        session.close();
		}
        
        return page;

	}	
	
	
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
			
}

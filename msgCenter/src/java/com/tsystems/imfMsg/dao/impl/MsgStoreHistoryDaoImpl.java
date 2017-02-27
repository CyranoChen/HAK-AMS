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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.tsystems.imfMsg.dao.MsgStoreHistoryDao;
import com.tsystems.imfMsg.entity.bo.MsgStoreHistory;
import com.tsystems.imfMsg.entity.vo.Page;
import com.tsystems.imfMsg.util.Constant;


@Repository("msgStoreHistoryDao")
public class MsgStoreHistoryDaoImpl implements MsgStoreHistoryDao {
	private static final Logger log = LoggerFactory.getLogger(MsgStoreHistoryDaoImpl.class);	
	private HibernateTemplate hibernateTemplate;
	
	public void save(MsgStoreHistory msgStore){
		getHibernateTemplate().save(msgStore);
	}
	public void delete(MsgStoreHistory msgStore){
		getHibernateTemplate().delete(msgStore);
	}
	public void update(MsgStoreHistory msgStore){
		getHibernateTemplate().update(msgStore);
	}
	public MsgStoreHistory loadById(String id){
		MsgStoreHistory instance=null;
		List<MsgStoreHistory> list=this.getHibernateTemplate().find("from MsgStoreHistory a where a.removed=0 and a.id=?",id); 
	    for(int i=0;i<list.size();i++)
	    {
	    	instance=(MsgStoreHistory)list.get(i);
	    }
        return instance; 		
	}	
	
	
	public Page findListByPage(HashMap<String, String> filter,String sortWay){
		Page page=null;
		List<MsgStoreHistory> list = new ArrayList<MsgStoreHistory>();	
		Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction tran=session.beginTransaction();
		try{
			Criteria query=session.createCriteria(MsgStoreHistory.class);
			query.add( Restrictions.eq("removed", Long.parseLong("0")));
			if (!filter.isEmpty()) {
				for (Iterator<String> i = filter.keySet().iterator(); i.hasNext();) {

					String paramName = i.next();

					if(paramName.equalsIgnoreCase("id")){
						query.add( Restrictions.eq(paramName, filter.get(paramName)));
						
					}else if(paramName.equalsIgnoreCase("status")||paramName.equalsIgnoreCase("removed")){
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
						
					}else{
						if(!paramName.equalsIgnoreCase("pageNo") && !paramName.equalsIgnoreCase("pageSize")){
							query.add( Restrictions.like(paramName, filter.get(paramName),MatchMode.ANYWHERE));
						}
					}

				}
			}
			
			if(sortWay.equalsIgnoreCase("asc")){
				query.addOrder( Order.asc("createTime") );
			}else{
				query.addOrder( Order.desc("createTime") );
			}
			
			int totalCount=((Integer) query.setProjection(Projections.rowCount()).uniqueResult()).intValue(); 
			query.setProjection(null);
			query.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
			
			int pageNo=0;
			int pageSize=0;
			if(filter.get("pageNo")!=null && Integer.parseInt(filter.get("pageNo"))>0){
				pageNo=Integer.parseInt(filter.get("pageNo"));
				pageSize=filter.get("pageSize")==null?Constant.PAGE_SIZE:Integer.parseInt(filter.get("pageSize"));
				query.setFirstResult(pageSize*(pageNo-1));
				query.setMaxResults(pageSize);
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

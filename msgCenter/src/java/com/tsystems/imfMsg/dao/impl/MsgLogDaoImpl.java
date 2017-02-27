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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.tsystems.imfMsg.dao.MsgLogDao;
import com.tsystems.imfMsg.entity.bo.MsgLog;
import com.tsystems.imfMsg.entity.vo.Page;
import com.tsystems.imfMsg.util.Constant;

@Repository("msgLogDao")
public class MsgLogDaoImpl implements MsgLogDao {
	private static final Logger log = LoggerFactory.getLogger(MsgLogDaoImpl.class);
	private HibernateTemplate hibernateTemplate;
	
	public void save(MsgLog msgLog){
		getHibernateTemplate().save(msgLog);
	}
	public void delete(MsgLog msgLog){
		getHibernateTemplate().delete(msgLog);
	}	
	public void update(MsgLog msgLog){
		getHibernateTemplate().update(msgLog);
	}
	public MsgLog loadById(String id){
		MsgLog instance=null;
		List<MsgLog> list=this.getHibernateTemplate().find("from MsgLog a where a.removed=0 and a.id=?",id); 
	    for(int i=0;i<list.size();i++)
	    {
	    	instance=(MsgLog)list.get(i);
	    }
        return instance;        
	}

	
	public Page findListByPage(HashMap<String, String> filter,String sortWay){
		Page page=null;
		List<MsgLog> list = new ArrayList<MsgLog>();	
		Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction tran=session.beginTransaction();
		try{
			Criteria query=session.createCriteria(MsgLog.class);
			query.add( Restrictions.eq("removed", 0));
			if (!filter.isEmpty()) {
				for (Iterator<String> i = filter.keySet().iterator(); i.hasNext();) {

					String paramName = i.next();

					if(paramName.equalsIgnoreCase("id")||paramName.equalsIgnoreCase("storeId")){
						query.add( Restrictions.eq(paramName, filter.get(paramName)));
						
					}else if(paramName.equalsIgnoreCase("operateTime")){
						SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mi:ss");
						String[] dateParams=filter.get(paramName).split(",");
						if(dateParams.length>1){
							if(!dateParams[0].equals("")){
								query.add( Restrictions.ge(paramName,dateFormat.parse(dateParams[0])));
							}
							if(!dateParams[1].equals("")){
								query.add( Restrictions.ge(paramName,dateFormat.parse(dateParams[1])));
							}							
						}
						
					}else{
						if(!paramName.equalsIgnoreCase("pageNo") && !paramName.equalsIgnoreCase("pageSize")){
							query.add( Restrictions.like(paramName, filter.get(paramName)));
						}
					}

				}
			}
			 
			if(sortWay.equalsIgnoreCase("asc")){
				query.addOrder( Order.asc("operateTime") );
			}else{
				query.addOrder( Order.desc("operateTime") );
			}
			
			int pageNo=0;
			int pageSize=0;
			if(filter.get("pageNo")!=null && Integer.parseInt(filter.get("pageNo"))>0){
				pageNo=Integer.parseInt(filter.get("pageNo"));
				pageSize=filter.get("pageSize")==null?Constant.PAGE_SIZE:Integer.parseInt(filter.get("pageSize"));
				query.setFirstResult(pageSize*(pageNo-1));
				query.setMaxResults(pageSize);
			}
			
			list=query.list(); 
			
			int totalCount=((Integer) query.setProjection(Projections.rowCount()).uniqueResult()).intValue(); 

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

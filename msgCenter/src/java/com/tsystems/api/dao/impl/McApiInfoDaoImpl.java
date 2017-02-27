package com.tsystems.api.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.tsystems.api.dao.McApiInfoDao;
import com.tsystems.api.model.bo.McApiInfo;

@Repository("mcApiInfoDao")
public class McApiInfoDaoImpl implements McApiInfoDao {
	private HibernateTemplate hibernateTemplate;

	
	public List<McApiInfo> findByUserName(String userName) {
    	List<McApiInfo> list=this.getHibernateTemplate().find("from McApiInfo a where a.removed=0 and a.userName=?",userName);        
        return list;

	}
	
	public List<McApiInfo> findAllApiInfo() {
    	List<McApiInfo> list=this.getHibernateTemplate().find("from McApiInfo a where a.removed=0");        
        return list;

	}	
	public List<McApiInfo> findByNextStep(String id){
		List<McApiInfo> list=this.getHibernateTemplate().find("from McApiInfo a where a.removed=0 and a.params like '%nextStep%' and a.params like '%"+id+"%'");        
        return list;
	}	
	
	public List<McApiInfo> findAllTriggerApiInfo() {
    	List<McApiInfo> list=this.getHibernateTemplate().find("from McApiInfo a where a.removed=0 and a.params like '%\"autoWay\":\"trigger\"%'");        
        return list;

	}	
	
	public List<McApiInfo> findByUserMethodType(String userName,String methodName,String dataType) {
    	List<McApiInfo> list=this.getHibernateTemplate().find("from McApiInfo a where a.removed=0 and a.userName=? and a.methodName=? and a.dataType=?",new String[]{userName,methodName,dataType});        
        return list;

	}	
	
	public List<McApiInfo> findByUserMethod(String userName,String methodName) {
    	List<McApiInfo> list=this.getHibernateTemplate().find("from McApiInfo a where a.removed=0 and a.userName=? and a.methodName=?",new String[]{userName,methodName});        
        return list;

	}			
	
	public McApiInfo loadById(String id) {
        return this.getHibernateTemplate().get(McApiInfo.class, id);        
	}
	
	public void update(McApiInfo mcApiInfo){
		getHibernateTemplate().update(mcApiInfo);
	}	

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
}
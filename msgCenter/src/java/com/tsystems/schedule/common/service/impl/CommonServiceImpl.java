package com.tsystems.schedule.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tsystems.schedule.common.dao.CommonDao;
import com.tsystems.schedule.common.service.CommonService;

@Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Repository("commonService")
public class CommonServiceImpl implements CommonService {

	private CommonDao dao;

	@SuppressWarnings("unchecked")
	public List ListByHql(String hql) {
		return dao.ListByHql(hql);
	}

	@SuppressWarnings("unchecked")
	public List ListByHql(final String hql, Object[] params) {
		return dao.ListByHql(hql, params);
	}

	public void UpdateByHql(String hql) {
		dao.UpdateByHql(hql);
	}

	@SuppressWarnings("unchecked")
	public Object load(String id, Class clazz) {
		return dao.load(id, clazz);
	}

	public void remove(Object obj) {
		dao.remove(obj);
	}

	public void save(Object obj) {
		dao.save(obj);
	}

	public void update(Object obj) {
		dao.update(obj);
	}

	public CommonDao getDao() {
		return dao;
	}

	@Autowired(required = false)
	public void setDao(@Qualifier(value = "commonDao") CommonDao dao) {
		this.dao = dao;
	}
}

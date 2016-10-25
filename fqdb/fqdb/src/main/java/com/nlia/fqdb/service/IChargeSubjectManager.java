package com.nlia.fqdb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.ChargeSubject;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IChargeSubjectManager extends AbstractBaseService<ChargeSubject, Long>{
	
	/**
	 * add by wangsq 20151201
	 * 查询所有chargeSubject对象
	 * @return
	 */
	public List<ChargeSubject> findAll();
	
	
	public List<ChargeSubject> findWithOutRuleGroup();


}

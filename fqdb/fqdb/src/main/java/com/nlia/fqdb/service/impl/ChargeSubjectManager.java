package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.ChargeSubjectDao;
import com.nlia.fqdb.entity.ChargeSubject;
import com.nlia.fqdb.service.IChargeSubjectManager;
import com.nlia.fqdb.util.CommonData;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class ChargeSubjectManager extends AbstractCrudService<ChargeSubject, Long> implements IChargeSubjectManager{

	@Resource
	private ChargeSubjectDao chargeSubjectDao;
	
	@Override
	protected GenericDao<ChargeSubject, Long> getDao() {
		return chargeSubjectDao;
	}
	
	
	
	public List<ChargeSubject> findAll(){
		Map<String,Object> filters = new HashMap<String,Object>();
		filters.put("removeFlag_equal",CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		return chargeSubjectDao.findBy(filters, null, -1, -1);
	}



    @Override
    public List<ChargeSubject> findWithOutRuleGroup() {
        return chargeSubjectDao.findWithOutRuleGroup();
    }


}

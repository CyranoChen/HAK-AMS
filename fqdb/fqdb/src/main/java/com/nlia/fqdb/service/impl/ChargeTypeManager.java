package com.nlia.fqdb.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.ChargeTypeDao;
import com.nlia.fqdb.entity.ChargeType;
import com.nlia.fqdb.service.IChargeTypeManager;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class ChargeTypeManager extends AbstractCrudService<ChargeType, Long> implements IChargeTypeManager{

	@Resource
	private ChargeTypeDao chargeTypeDao;
	
	@Override
	protected GenericDao<ChargeType, Long> getDao() {
		return chargeTypeDao;
	}

}

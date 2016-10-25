package com.nlia.fqdb.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.ChargeTargetDao;
import com.nlia.fqdb.entity.ChargeTarget;
import com.nlia.fqdb.service.IChargeTargetManager;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class ChargeTargetManager extends AbstractCrudService<ChargeTarget, Long> implements IChargeTargetManager {

	@Resource
	private ChargeTargetDao chargeTargetDao;
	
	@Override
	protected GenericDao<ChargeTarget, Long> getDao() {
		return chargeTargetDao;
	}


}

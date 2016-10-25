package com.nlia.fqdb.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.ChargeObjectDao;
import com.nlia.fqdb.entity.ChargeObject;
import com.nlia.fqdb.service.IChargeObjectManager;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class ChargeObjectManager extends
		AbstractCrudService<ChargeObject, Long> implements IChargeObjectManager {

	@Resource
	private ChargeObjectDao ChargeObjectDao;


	@Override
	public ChargeObject addchargeObject(ChargeObject chargeObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChargeObject modifychargeObject(ChargeObject chargeObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChargeObject removechargeObject(ChargeObject chargeObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChargeObject> queryChargeObject(Map<String, Object> filters,
			Map<String, String> sorts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericDao<ChargeObject, Long> getDao() {
		// TODO Auto-generated method stub
		return ChargeObjectDao;
	}

}

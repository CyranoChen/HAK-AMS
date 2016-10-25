package com.nlia.fqdb.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.ChargeUnitDao;
import com.nlia.fqdb.entity.ChargeUnit;
import com.nlia.fqdb.service.IChargeUnitManager;
import com.nlia.fqdb.util.ImportChargeUnitByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;


@Service
public class ChargeUnitManager extends AbstractCrudService<ChargeUnit, Long>
		implements IChargeUnitManager {

	@Resource
	private ChargeUnitDao chargeUnitDao;
	
	@Resource
	private ImportChargeUnitByExcel importChargeUnitByExcel;


	@Override
	public ChargeUnit addChargeUnit(ChargeUnit chargeUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChargeUnit modifyChargeUnit(ChargeUnit chargeUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChargeUnit removeChargeUnit(ChargeUnit chargeUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChargeUnit> queryChargeUnit(Map<String, Object> filters,
			Map<String, String> sorts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericDao<ChargeUnit, Long> getDao() {
		return chargeUnitDao;
	}

	@Override
	public List<ChargeUnit> importByExcel2003(byte[] bytes) {
		List<ChargeUnit> chargeUnitList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			chargeUnitList = importChargeUnitByExcel.ImportChargeUnitByExcel2003(input);
		} catch (IOException e) {
			
		}
		return chargeUnitList;
	}

	@Override
	public List<ChargeUnit> importByExcel2007(byte[] bytes) {
		List<ChargeUnit> chargeUnitList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			chargeUnitList = importChargeUnitByExcel.ImportChargeUnitByExcel2007(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chargeUnitList;
	}

}

package com.nlia.fqdb.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.PassengerClassDao;
import com.nlia.fqdb.entity.base.PassengerClass;
import com.nlia.fqdb.service.IPassengerClassManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.base.ImportPassengerClassByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class PassengerClassManager extends AbstractCrudService<PassengerClass, Long>
		implements IPassengerClassManager {

	@Resource
	private PassengerClassDao passengerClassDao;

	@Override
	protected GenericDao<PassengerClass, Long> getDao() {
		return passengerClassDao;
	}

	/*
	 * 查指定
	 * 参数：passengerClassCode
	 */
	@Override
	public List<PassengerClass> findByPassengerClassCode(String passengerClassCode) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("passengerClassCode_equal", passengerClassCode);
		List<PassengerClass> passengerClassList = passengerClassDao.findBy(filters, null, -1, -1);
		return passengerClassList;
	}

	/*
	 * 查询全部
	 */
	@Override
	public List<PassengerClass> findAllPassengerClass() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<PassengerClass> passengerClassList = passengerClassDao.findBy(filters, null, -1, -1);
		return passengerClassList;
	}
	
	@Resource
	private ImportPassengerClassByExcel importPassengerClassByExcel;
	
	@Override
	public List<PassengerClass> importByExcel2003(byte[] bytes) {
		List<PassengerClass> passengerClassList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			passengerClassList = importPassengerClassByExcel.ImportPassengerClassByExcel2003(input);
		} catch (IOException e) {
		}
		return passengerClassList;
	}

	@Override
	public List<PassengerClass> importByExcel2007(byte[] bytes) {
		List<PassengerClass> passengerClassList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			passengerClassList = importPassengerClassByExcel.ImportPassengerClassByExcel2007(input);
		} catch (IOException e) {
		}
		return passengerClassList;
	}
}

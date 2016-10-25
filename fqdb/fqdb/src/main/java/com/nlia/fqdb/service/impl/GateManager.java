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

import com.nlia.fqdb.dao.GateDao;
import com.nlia.fqdb.entity.base.Gate;
import com.nlia.fqdb.service.IGateManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.base.ImportGateByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class GateManager extends AbstractCrudService<Gate, Long>
		implements IGateManager {

	@Resource
	private GateDao gateDao;

	@Override
	protected GenericDao<Gate, Long> getDao() {
		return gateDao;
	}

	/*
	 * 查指定
	 * 参数：gateCode
	 */
	@Override
	public List<Gate> findByGateCode(String gateCode) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("gateCode_equal", gateCode);
		List<Gate> gateList = gateDao.findBy(filters, null, -1, -1);
		return gateList;
	}

	/*
	 * 查询
	 */
	@Override
	public List<Gate> findAllGate() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<Gate> gateList = gateDao.findBy(filters, null, -1, -1);
		return gateList;
	}
	
	@Resource
	private ImportGateByExcel importGateByExcel;
	
	@Override
	public List<Gate> importByExcel2003(byte[] bytes) {
		List<Gate> gateList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			gateList = importGateByExcel.ImportGateByExcel2003(input);
		} catch (IOException e) {
		}
		return gateList;
	}

	@Override
	public List<Gate> importByExcel2007(byte[] bytes) {
		List<Gate> gateList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			gateList = importGateByExcel.ImportGateByExcel2007(input);
		} catch (IOException e) {
		}
		return gateList;
	}
}

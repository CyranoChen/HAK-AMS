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

import com.nlia.fqdb.dao.StandDao;
import com.nlia.fqdb.entity.base.Stand;
import com.nlia.fqdb.service.IStandManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.base.ImportStandByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class StandManager extends AbstractCrudService<Stand, Long>
		implements IStandManager {

	@Resource
	private StandDao standDao;

	@Override
	protected GenericDao<Stand, Long> getDao() {
		return standDao;
	}

	/*
	 * 查指定
	 * 参数：standCode
	 */
	@Override
	public List<Stand> findByStandCode(String standCode) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("standCode_equal", standCode);
		List<Stand> standList = standDao.findBy(filters, null, -1, -1);
		return standList;
	}

	/*
	 * 查询
	 */
	@Override
	public List<Stand> findAllStand() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<Stand> standList = standDao.findBy(filters, null, -1, -1);
		return standList;
	}
	
	@Resource
	private ImportStandByExcel importStandByExcel;
	
	@Override
	public List<Stand> importByExcel2003(byte[] bytes) {
		List<Stand> standList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			standList = importStandByExcel.ImportStandByExcel2003(input);
		} catch (IOException e) {
		}
		return standList;
	}

	@Override
	public List<Stand> importByExcel2007(byte[] bytes) {
		List<Stand> standList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			standList = importStandByExcel.ImportStandByExcel2007(input);
		} catch (IOException e) {
		}
		return standList;
	}
}

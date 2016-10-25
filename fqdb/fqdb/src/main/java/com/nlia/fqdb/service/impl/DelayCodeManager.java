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

import com.nlia.fqdb.dao.DelayCodeDao;
import com.nlia.fqdb.entity.base.DelayCode;
import com.nlia.fqdb.service.IDelayCodeManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.base.ImportDelayCodeByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class DelayCodeManager extends AbstractCrudService<DelayCode, Long>
		implements IDelayCodeManager {
	@Resource
	private DelayCodeDao DelayCodeDao;

	@Override
	protected GenericDao<DelayCode, Long> getDao() {
		return DelayCodeDao;
	}

	/*
	 * 查指定延迟编码
	 * 参数：delayCode
	 */
	@Override
	public List<DelayCode> findByDelayCode(String delayCode) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("delayCode_equal", delayCode);
		List<DelayCode> delayCodeList = DelayCodeDao.findBy(filters, null, -1, -1);
		return delayCodeList;
	}

	/*
	 * 查询全部延迟编码
	 */
	@Override
	public List<DelayCode> findAllDelayCode() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<DelayCode> delayCodeList = DelayCodeDao.findBy(filters, null, -1, -1);
		return delayCodeList;
	}
	
	@Resource
	private ImportDelayCodeByExcel importDelayCodeByExcel;
	
	@Override
	public List<DelayCode> importByExcel2003(byte[] bytes) {
		List<DelayCode> delayCodeList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			delayCodeList = importDelayCodeByExcel.ImportDelayCodeByExcel2003(input);
		} catch (IOException e) {
		}
		return delayCodeList;
	}

	@Override
	public List<DelayCode> importByExcel2007(byte[] bytes) {
		List<DelayCode> delayCodeList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			delayCodeList = importDelayCodeByExcel.ImportDelayCodeByExcel2007(input);
		} catch (IOException e) {
		}
		return delayCodeList;
	}
}

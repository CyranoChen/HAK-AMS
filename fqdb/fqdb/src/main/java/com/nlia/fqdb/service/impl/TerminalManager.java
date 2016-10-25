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

import com.nlia.fqdb.dao.TerminalDao;
import com.nlia.fqdb.entity.base.Terminal;
import com.nlia.fqdb.service.ITerminalManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.base.ImportTerminalByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class TerminalManager extends AbstractCrudService<Terminal, Long>
		implements ITerminalManager {

	@Resource
	private TerminalDao terminalDao;
	
	@Override
	protected GenericDao<Terminal, Long> getDao() {
		return terminalDao;
	}

	/*
	 * 查指定
	 * 参数：terminalCode
	 */
	@Override
	public List<Terminal> findByTerminalCode(String terminalCode) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("terminalCode_equal", terminalCode);
		List<Terminal> terminalList = terminalDao.findBy(filters, null, -1, -1);
		return terminalList;
	}

	/*
	 * 查询
	 */
	@Override
	public List<Terminal> findAllTerminal() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<Terminal> terminalList = terminalDao.findBy(filters, null, -1, -1);
		return terminalList;
	}

	@Resource
	private ImportTerminalByExcel importTerminalByExcel;
	@Override
	public List<Terminal> importByExcel2003(byte[] bytes) {
		List<Terminal> terminalList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			terminalList = importTerminalByExcel.ImportTerminalByExcel2003(input);
		} catch (IOException e) {
		}
		return terminalList;
	}

	@Override
	public List<Terminal> importByExcel2007(byte[] bytes) {
		List<Terminal> terminalList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			terminalList = importTerminalByExcel.ImportTerminalByExcel2007(input);
		} catch (IOException e) {
		}
		return terminalList;
	}
	
}

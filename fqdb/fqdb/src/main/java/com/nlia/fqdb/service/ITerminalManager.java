package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.Terminal;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface ITerminalManager extends
		AbstractBaseService<Terminal, Long> {

	

	/*
	 * 查指定
	 * 参数：terminalCode
	 */
	public List<Terminal> findByTerminalCode(String terminalCode);

	/*
	 * 查询全部
	 */
	public List<Terminal> findAllTerminal();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<Terminal> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<Terminal> importByExcel2007(byte[] bytes);
	
}

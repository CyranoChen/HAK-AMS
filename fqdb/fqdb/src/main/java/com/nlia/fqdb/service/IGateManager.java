package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.Gate;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IGateManager extends
		AbstractBaseService<Gate, Long> {

	

	/*
	 * 查指定
	 * 参数：gateCode
	 */
	public List<Gate> findByGateCode(String gateCode);

	/*
	 * 查询全部
	 */
	public List<Gate> findAllGate();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<Gate> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<Gate> importByExcel2007(byte[] bytes);
}

package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.Stand;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IStandManager extends
		AbstractBaseService<Stand, Long> {

	

	/*
	 * 查指定
	 * 参数：standCode
	 */
	public List<Stand> findByStandCode(String standCode);

	/*
	 * 查询全部
	 */
	public List<Stand> findAllStand();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<Stand> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<Stand> importByExcel2007(byte[] bytes);
}

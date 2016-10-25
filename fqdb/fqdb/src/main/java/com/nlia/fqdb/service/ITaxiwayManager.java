package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.Taxiway;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface ITaxiwayManager extends
		AbstractBaseService<Taxiway, Long> {

	

	/*
	 * 查指定
	 * 参数：taxiwayCode
	 */
	public List<Taxiway> findByTaxiwayCode(String taxiwayCode);

	/*
	 * 查询全部
	 */
	public List<Taxiway> findAllTaxiway();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<Taxiway> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<Taxiway> importByExcel2007(byte[] bytes);
}

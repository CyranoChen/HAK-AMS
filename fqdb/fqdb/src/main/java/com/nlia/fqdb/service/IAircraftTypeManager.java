package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.AircraftType;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IAircraftTypeManager extends
		AbstractBaseService<AircraftType, Long> {

	

	/*
	 * 查指定机型
	 * 参数：IATACode
	 */
	public List<AircraftType> findByAircraftTypeIATACode(String IATACode);

	/*
	 * 查询全部机型
	 */
	public List<AircraftType> findAllAircraftType();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<AircraftType> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<AircraftType> importByExcel2007(byte[] bytes);
}

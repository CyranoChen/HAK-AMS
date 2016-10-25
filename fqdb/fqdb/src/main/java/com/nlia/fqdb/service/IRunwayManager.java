package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.Runway;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IRunwayManager extends
		AbstractBaseService<Runway, Long> {

	

	/*
	 * 查指定
	 * 参数：runwayCode
	 */
	public List<Runway> findByRunwayCode(String runwayCode);

	/*
	 * 查询全部
	 */
	public List<Runway> findAllRunway();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<Runway> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<Runway> importByExcel2007(byte[] bytes);
}

package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.BaggageMakeup;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IBaggageMakeupManager extends
		AbstractBaseService<BaggageMakeup, Long> {

	

	/*
	 * 查行李分拣盘
	 * 参数：BaggageMakeupCode
	 */
	public List<BaggageMakeup> findByBaggageMakeupCode (String baggageMakeupCode );

	/*
	 * 查询全部行李分拣盘
	 */
	public List<BaggageMakeup> findAllBaggageMakeup();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<BaggageMakeup> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<BaggageMakeup> importByExcel2007(byte[] bytes);
}

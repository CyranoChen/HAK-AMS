package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.BaggageReclaim;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IBaggageReclaimManager extends
		AbstractBaseService<BaggageReclaim, Long> {

	

	/*
	 * 查指定行李提取转盘
	 * 参数：baggageReclaimCode
	 */
	public List<BaggageReclaim> findByBaggageReclaimCode(String baggageReclaimCode );

	/*
	 * 查询全部行李提取转盘
	 */
	public List<BaggageReclaim> findAllBaggageReclaim();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<BaggageReclaim> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<BaggageReclaim> importByExcel2007(byte[] bytes);
}

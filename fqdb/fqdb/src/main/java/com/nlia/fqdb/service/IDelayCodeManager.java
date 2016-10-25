package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.DelayCode;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IDelayCodeManager extends
		AbstractBaseService<DelayCode, Long> {

	

	/*
	 * 查询指定延迟编码
	 * 参数：DelayCode
	 */
	public List<DelayCode> findByDelayCode(String delayCode );

	/*
	 * 查询延迟编码
	 */
	public List<DelayCode> findAllDelayCode();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<DelayCode> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<DelayCode> importByExcel2007(byte[] bytes);
}

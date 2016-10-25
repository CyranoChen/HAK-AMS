package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.Handler;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IHandlerManager extends
		AbstractBaseService<Handler, Long> {

	

	/*
	 * 查指定代理
	 * 参数：handlerCode
	 */
	public List<Handler> findByHandlerCode(String handlerCode);

	/*
	 * 查询全部代理
	 */
	public List<Handler> findAllHandler();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<Handler> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<Handler> importByExcel2007(byte[] bytes);
}

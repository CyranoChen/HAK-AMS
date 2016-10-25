package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.CheckInDesk;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface ICheckInDeskManager extends
		AbstractBaseService<CheckInDesk, Long> {

	

	/*
	 * 查指定值机柜台
	 * 参数：checkInDeskCode
	 */
	public List<CheckInDesk> findByCheckInDeskCode(String checkInDeskCode  );

	/*
	 * 查询全部值机柜台
	 */
	public List<CheckInDesk> findAllCheckInDesk();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<CheckInDesk> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<CheckInDesk> importByExcel2007(byte[] bytes);
}

package com.nlia.fqdb.service;

import com.nlia.fqdb.entity.FlightBase;
import com.wonders.aiis.core.service.AbstractBaseService;

/**
 * @author zhuhaijian
 * @time 2013-06-19
 * @decription 接收德电的xml格式的航班信息解析并对数据库进行增、删和改的操作
 */
public interface IFlightDataResolveOperateServiceOfFqdb {

	public String parseFlightDataOfXmlString(String flightDataOfXmlString);
}

package com.tsystems.nlia.fqdb.service;

import com.tsystems.aiis.core.service.AbstractBaseService;
import com.tsystems.nlia.fqdb.entity.FlightBase;

/**
 * @author zhuhaijian
 * @time 2013-06-19
 * @decription 接收德电的xml格式的航班信息解析并对数据库进行增、删和改的操作
 */
public interface IFlightDataResolveOperateServiceOfFqdb extends AbstractBaseService<FlightBase, Long> {

	public String parseFlightDataOfXmlString(String flightDataOfXmlString);
}

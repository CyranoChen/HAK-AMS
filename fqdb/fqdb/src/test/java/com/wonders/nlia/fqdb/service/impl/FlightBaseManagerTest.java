package com.wonders.nlia.fqdb.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.service.impl.FlightBaseManager;
import com.nlia.fqdb.util.DateUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:spring-mvc.xml"})
public class FlightBaseManagerTest {
	
	@Resource
	private FlightBaseManager flightBaseManager;
	
	
	//因为内存查询，航班查询的测试用例都会失败。
	@Test
	public void testGetPeriodFlightByPage(){
		Date startDate = DateUtils.String2Date("2013-09-09","yyyy-MM-dd");
		Date endDate = DateUtils.String2Date("2013-09-10","yyyy-MM-dd");
		List<FlightBase> flights = flightBaseManager.getPeriodFlightByPage(startDate, endDate, 1, 1110);
		Assert.assertEquals(1, flights.size());
		System.out.println(flights.size());
	}
}

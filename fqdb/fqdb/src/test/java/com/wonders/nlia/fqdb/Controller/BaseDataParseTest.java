package com.wonders.nlia.fqdb.Controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nlia.fqdb.service.impl.BaseDataParse;
import com.nlia.fqdb.service.impl.FlightDataResolveOperateServiceOfFqdb;
import com.nlia.fqdb.util.FileUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:spring-mvc.xml"})
public class BaseDataParseTest {
	private final String SERVER_IP = "http://localhost:8080/fqdb";
	@Resource
	private BaseDataParse baseDataParse;
	@Resource
    private FlightDataResolveOperateServiceOfFqdb flightDataResolveOperateServiceOfFqdb;
	@Test
	public void testbaseDataParse(){
		String filePath = "D:\\work\\test\\bss.xml";
//		String filePath = "D:\\work\\test\\FSS.xml";
		String xml = null;
		try {
			xml = FileUtil.readFileAsString(filePath );
		} catch (IOException e) {
			e.printStackTrace();
		}
		baseDataParse.changeParseOfBaseData(xml);
//		flightDataResolveOperateServiceOfFqdb.parseFlightDataOfXmlString(xml);
	}
	
	@Test
	public void testflightDataParse(){
		String filePath = "D:\\work\\test\\FSS10.xml";
		String xml = null;
		try {
			xml = FileUtil.readFileAsString(filePath );
		} catch (IOException e) {
			e.printStackTrace();
		}
		flightDataResolveOperateServiceOfFqdb.parseFlightDataOfXmlString(xml);
	}
}

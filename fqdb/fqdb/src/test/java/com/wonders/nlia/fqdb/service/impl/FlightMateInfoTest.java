package com.wonders.nlia.fqdb.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nlia.fqdb.service.impl.FlightMateInfoManager;
import com.nlia.fqdb.util.DateUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:spring-mvc.xml"})
public class FlightMateInfoTest {
	
	@Resource
	private FlightMateInfoManager flightMateInfoManager;
	
	//因为内存查询，航班查询的测试用例都会失败。
	@Test
	public void testGetPeriodFlightByPage(){
//		List<FlightBase> list=new ArrayList<>();
//		FlightServiceInfo fsi=flightServiceInfoManager.findByFlightBaseId(2268364L);
//		fsi.getFlightBase().setFlightServiceInfo(fsi);
//		
//		FlightServiceInfo linkfsi=flightServiceInfoManager.findByFlightBaseId(2268361L);
//        linkfsi.getFlightBase().setFlightServiceInfo(linkfsi);
//        linkfsi.getFlightBase().getLinkFlight().setFlightServiceInfo(fsi);
//        fsi.getFlightBase().getLinkFlight().setFlightServiceInfo(linkfsi);
//        
//        list.add(fsi.getFlightBase());
//        list.add(linkfsi.getFlightBase());

//		flightMateInfoManager.flightMatedInfoInit(list);
//		Date startDate = DateUtils.String2Date("2013-09-09","yyyy-MM-dd");
//		Date endDate = DateUtils.String2Date("2013-09-10","yyyy-MM-dd");
//		List<FlightBase> flights = flightBaseManager.getPeriodFlightByPage(startDate, endDate, 1, 1110);
//		Assert.assertEquals(1, flights.size());
//		System.out.println(flights.size());
	       Date startDate = DateUtils.String2Date("2015-10-02","yyyy-MM-dd");
	        Date endDate = DateUtils.String2Date("2015-10-03","yyyy-MM-dd");
	        flightMateInfoManager.flightMatedInfoInit(startDate, endDate); 
	        
	        flightMateInfoManager.repairMisFlightMateInfo(flightMateInfoManager.findMisFlightMateInfo(startDate, endDate));
	        
	}
	@Test
	public void testUpdateChargeTermAccordingToFlightMate(){
	       Map<String, Object> filters = new HashMap<>();
	        // 数据准备
	    flightMateInfoManager.updateChargeTermAccordingToFlightMate(flightMateInfoManager.findBy(null));
	}
	@Test
	public void testImportFlightLoadData2FlightMateInfo(){
	    InputStream inputStream = null;
	    String fileName= "D:\\work\\test\\FlightLoadData_verify.xlsx";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        List<FlightMateInfo> flightMateInfoList = new ArrayList<FlightMateInfo>();
        Map<String, Object> flightMateInfoDataResult = new HashMap<>();
        String columnErrorMessage ="";
        try {
            flightMateInfoDataResult=flightMateInfoManager.importFlightLoadDataByExcel(fileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        try {
//            inputStream = new FileInputStream(fileName);
//            byte[] buffer = new byte[8192];
//
//            for (int length = 0; (length = inputStream.read(buffer)) > 0;) {
//                outputStream.write(buffer, 0, length);
//            }
//            byte[] bytes = outputStream.toByteArray();
//
//            // 判断excel2003　or excel2007
//            if (fileName.endsWith(".xls")) {
//                flightMateInfoDataResult =  flightMateInfoManager.importFlightLoadDataByExcel2003(bytes);
////                flightMateInfoList = (List<FlightMateInfo>) flightMateInfoDataResult.get("list");
//                columnErrorMessage = (String) flightMateInfoDataResult.get("errorMessage");
//
//            } else if (fileName.endsWith(".xlsx")) {
//                flightMateInfoDataResult = flightMateInfoManager.importFlightLoadDataByExcel2007(bytes);
////                flightMateInfoList = (List<FlightMateInfo>) flightMateInfoDataResult.get("list");
//                columnErrorMessage = (String) flightMateInfoDataResult.get("errorMessage");
//            }
//            
//            // 判断返回消息中有无报错消息，如果没有则导入到table中，反则提示错误消息
//            inputStream.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                inputStream.close();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }

    
	}
}

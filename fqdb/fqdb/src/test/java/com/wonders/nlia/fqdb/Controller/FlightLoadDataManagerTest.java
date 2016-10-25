package com.wonders.nlia.fqdb.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightLoadData;
import com.nlia.fqdb.entity.FlightMateInfo;
import com.nlia.fqdb.service.impl.FlightBaseManager;
import com.nlia.fqdb.service.impl.FlightLoadDataManager;
import com.nlia.fqdb.service.impl.FlightMateInfoManager;
import com.nlia.fqdb.util.DateUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:spring-mvc.xml"})
public class FlightLoadDataManagerTest {
	private final String SERVER_IP = "http://localhost:8080/fqdb";
	@Resource
	private FlightBaseManager flightBaseManager;
	@Resource
    private FlightLoadDataManager flightLoadDataManager;
	@Resource
	private FlightMateInfoManager flightMateInfoManager;
	@Test
	public void test(){
//	    List<FlightBase> bs=new ArrayList<FlightBase>();
//	    bs.add(flightBaseManager.find(3359818L));
//	    List<FlightLoadData>list= flightLoadDataManager.generate(bs);
//	    List<Long> fmiIds=new ArrayList<>();
//	       fmiIds.add(3360136L);
//	    flightMateInfoManager.saveFlightLoadData2FlightMateInfoByIds(fmiIds);
	    
	    
//	    List<FlightMateInfo> list=new ArrayList<>();
//	    list.add(flightMateInfoManager.find(3448606L));
	    
	    
	    Map<String, Object> filters = new HashMap<>();
	    filters.put("flightBaseId_is", null);
	    filters.put("scheduledTime_lessThan", DateUtils.String2Date("2016-07-02 00:00"));
	    filters.put("scheduledTime_greaterThan",DateUtils.String2Date("2016-06-30 00:00"));
	    filters.put("removeFlag_equal", "1");
	    List<FlightMateInfo> list=flightMateInfoManager.findBy(filters);
	    flightLoadDataManager.generateByFMI(list);
	    
//	    filters.clear();
//        filters.put("scheduledTime_lessThan", DateUtils.String2Date("2016-07-07 00:00"));
//        filters.put("scheduledTime_greaterThan",DateUtils.String2Date("2016-07-04 00:00"));
//        filters.put("removeFlag_equal", "1");
//        list=flightMateInfoManager.findBy(filters);
//        flightLoadDataManager.generateByFMI(list);
//        
//        filters.clear();
//        filters.put("scheduledTime_lessThan", DateUtils.String2Date("2016-07-10 00:00"));
//        filters.put("scheduledTime_greaterThan",DateUtils.String2Date("2016-07-07 00:00"));
//        filters.put("removeFlag_equal", "1");
//        list=flightMateInfoManager.findBy(filters);
//        flightLoadDataManager.generateByFMI(list);
//        
//        filters.clear();
//        filters.put("scheduledTime_lessThan", DateUtils.String2Date("2016-07-13 00:00"));
//        filters.put("scheduledTime_greaterThan",DateUtils.String2Date("2016-07-10 00:00"));
//        filters.put("removeFlag_equal", "1");
//        list=flightMateInfoManager.findBy(filters);
//        flightLoadDataManager.generateByFMI(list);
//        
//        filters.clear();
//        filters.put("scheduledTime_lessThan", DateUtils.String2Date("2016-07-16 00:00"));
//        filters.put("scheduledTime_greaterThan",DateUtils.String2Date("2016-07-13 00:00"));
//        filters.put("removeFlag_equal", "1");
//        list=flightMateInfoManager.findBy(filters);
//        flightLoadDataManager.generateByFMI(list);
//        
//        filters.clear();
//        filters.put("scheduledTime_lessThan", DateUtils.String2Date("2016-07-19 00:00"));
//        filters.put("scheduledTime_greaterThan",DateUtils.String2Date("2016-07-16 00:00"));
//        filters.put("removeFlag_equal", "1");
//        list=flightMateInfoManager.findBy(filters);
//        flightLoadDataManager.generateByFMI(list);
//        
//        filters.clear();
//        filters.put("scheduledTime_lessThan", DateUtils.String2Date("2016-07-22 00:00"));
//        filters.put("scheduledTime_greaterThan",DateUtils.String2Date("2016-07-19 00:00"));
//        filters.put("removeFlag_equal", "1");
//        list=flightMateInfoManager.findBy(filters);
//        flightLoadDataManager.generateByFMI(list);
//        
//        filters.clear();
//        filters.put("scheduledTime_lessThan", DateUtils.String2Date("2016-07-24 00:00"));
//        filters.put("scheduledTime_greaterThan",DateUtils.String2Date("2016-07-22 00:00"));
//        filters.put("removeFlag_equal", "1");
//        list=flightMateInfoManager.findBy(filters);
//        flightLoadDataManager.generateByFMI(list);
//        
//	    filters.clear();
//	    filters.put("scheduledTime_lessThan", DateUtils.String2Date("2016-07-27 00:00"));
//        filters.put("scheduledTime_greaterThan",DateUtils.String2Date("2016-07-24 00:00"));
//        filters.put("removeFlag_equal", "1");
//        list=flightMateInfoManager.findBy(filters);
//	    flightLoadDataManager.generateByFMI(list);
//	    
//	    filters.clear();
//        filters.put("scheduledTime_lessThan", DateUtils.String2Date("2016-07-30 00:00"));
//        filters.put("scheduledTime_greaterThan",DateUtils.String2Date("2016-07-27 00:00"));
//        filters.put("removeFlag_equal", "1");
//        list=flightMateInfoManager.findBy(filters);
//        flightLoadDataManager.generateByFMI(list);
//        
//        filters.clear();
//        filters.put("scheduledTime_lessThan", DateUtils.String2Date("2016-08-01 00:00"));
//        filters.put("scheduledTime_greaterThan",DateUtils.String2Date("2016-07-30 00:00"));
//        filters.put("removeFlag_equal", "1");
//        list=flightMateInfoManager.findBy(filters);
//        flightLoadDataManager.generateByFMI(list);
	    
	    
	}
	
}

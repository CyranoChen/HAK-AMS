package com.wonders.nlia.fqdb.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.nlia.fqdb.dao.FlightBaseDao;
import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightData;
import com.nlia.fqdb.entity.FlightResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:spring-mvc.xml"})
@TransactionConfiguration(defaultRollback=false)
public class FlightBaseDaoTest {
	@Resource
	private FlightBaseDao dao;
	
	@Test
	public void addFlightBase(){
		Map<String, Object> filters = new HashMap<>();
		Date d = new Date();
		filters.put("flightData.flightScheduledDateTime_lessThan", d);
		//		String startTime  = DateUtils.String2Date(date, pattern);
		@SuppressWarnings("unused")
		List<FlightBase> flights = dao.findBy(filters, null, 0, 10);
		int i =0;
		for(FlightBase fb : flights){
		fb.setId(null);
		fb.setFlightScheduledDate(d);
		fb.setCreateUser("NinjaTest");
		fb.setFlightIdentity("NinjaTest"+ ++i);
		FlightData fd = fb.getFlightData();
		fd.setId(null);
//		fd.setFlightScheduledDateTime(d);
//		fd.setEstimatedLandingDateTime(d);
//		fd.setActualLandingDateTime(d);
		fb.getFlightResource().setId(null);
			
		dao.save(fb);
		}
//		fb.setFlightScheduledDate(d);
//		fb.setCreateUser("NinjaTest");
//		fb.
//		fb.setFlightIdentity("NinjaTest1");
//		FlightData fd = new FlightData();
//		fb.setFlightData(fd);
//		fd.setFlightScheduledDateTime(d);
//		fd.setEstimatedLandingDateTime(d);
//		fd.setActualLandingDateTime(d);
//		fb.setFlightResource(new FlightResource());
	}
	
}

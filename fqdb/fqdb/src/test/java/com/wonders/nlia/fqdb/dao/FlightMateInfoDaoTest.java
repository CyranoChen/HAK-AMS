package com.wonders.nlia.fqdb.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.nlia.fqdb.dao.FlightBaseDao;
import com.nlia.fqdb.dao.FlightMateInfoDao;
import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightMateInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:spring-mvc.xml"})
@TransactionConfiguration(defaultRollback=false)
public class FlightMateInfoDaoTest {

	@Resource
	private FlightMateInfoDao dao;
	
	@Resource
	private FlightBaseDao baseDao;
	
	private long currentTime;
	
	private long fmiId;
	//@Before
	public void init(){
		currentTime=System.currentTimeMillis();
		FlightBase fb=new FlightBase();
		fb.setCreateUser("lu");
		fb.setFlightIdentity("MU1111");
		fb.setFlightScheduledDate(new Date(currentTime));
		fb.setRemoveFlag("1");
		baseDao.save(fb);
		
		System.out.println("1.flightBase:"+fb.getId());
		
		FlightMateInfo fmi=new FlightMateInfo();
		
		fmi.setFlightBaseId(fb.getId());
		fmi.setRemoveFlag("1");
		
		dao.save(fmi);
		

		fmiId=fmi.getId();
		
		System.out.println("2.flightMateInfo:"+fmiId);
		
		fb.setRemoveFlag("0");
		baseDao.save(fb);
		
	}
	
	@Test
	public void testClear(){
		Date date = new Date();

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -4);
			date = calendar.getTime();
		
		System.out.println(new Date(currentTime));
		//dao.clear(new Date(currentTime-1000*60*60*24*15), new Date(currentTime+1000));
		
//		FlightMateInfo fmi=dao.find(fmiId);
//		
//		System.out.println("3.flightMateInfo:"+fmiId);
//		
//		Assert.assertNotNull(fmi);
//		
//		Assert.assertEquals("0", fmi.getRemoveFlag());

		
	}
}

package com.wonders.frame.ams.service.fqs;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wonders.frame.ams.dao.basic.FlightMateInfoDao;
import com.wonders.frame.ams.model.FlightMateInfo;

@Service
public class FlightMateInfoService {
	@Resource
	private FlightMateInfoDao flightMateInfoDao;
	public FlightMateInfo findById(Long id){
		return flightMateInfoDao.findById(id);
	}
}

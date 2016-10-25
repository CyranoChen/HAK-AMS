package com.nlia.fqdb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.base.Aircraft;
import com.nlia.fqdb.service.impl.AircraftManager;

@Controller
@RequestMapping("aircraft")
public class AircraftController {

	@Resource
	private AircraftManager aircraftManager;

	@RequestMapping(value = "findByAircraftRegistration/{aircraftRegistration}", method = RequestMethod.GET)
	protected @ResponseBody
	List<Aircraft> findByAircraftRegistration(@PathVariable String aircraftRegistration) {
		return aircraftManager.findByAircraftRegistration(aircraftRegistration);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	protected @ResponseBody
	List<Aircraft> findAllAircraft() {
		return aircraftManager.findAllAircraft();
	}

}
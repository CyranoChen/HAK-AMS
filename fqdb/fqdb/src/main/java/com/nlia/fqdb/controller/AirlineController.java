package com.nlia.fqdb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.base.Airline;
import com.nlia.fqdb.service.impl.AirlineManager;

@Controller
@RequestMapping("airline")
public class AirlineController{

	@Resource
	private AirlineManager airlineManager;
	
	@RequestMapping(value = "findByAirlineIATACode/{IATACode}", method = RequestMethod.GET)
	protected @ResponseBody
	List<Airline> findByAirlineIATACode(@PathVariable String IATACode) {
		return airlineManager.findByAirlineIATACode(IATACode);
	}
	@RequestMapping(value = "", method = RequestMethod.GET)
	protected @ResponseBody
	List<Airline> findAllAirline() {
		return airlineManager.findAllAirline();
	}

}
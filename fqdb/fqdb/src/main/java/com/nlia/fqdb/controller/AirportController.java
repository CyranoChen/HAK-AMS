package com.nlia.fqdb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.base.Airport;
import com.nlia.fqdb.service.impl.AirportManager;

@Controller
@RequestMapping("airport")
public class AirportController {

	@Resource
	private AirportManager airportManager;

	@RequestMapping(value = "findByAirportIATACode/{IATACode}", method = RequestMethod.GET)
	protected @ResponseBody
	List<Airport> findByAirportIATACode(@PathVariable String IATACode) {
		return airportManager.findByAirportIATACode(IATACode);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	protected @ResponseBody
	List<Airport> findAllAirport() {
		return airportManager.findAllAirport();
	}

}
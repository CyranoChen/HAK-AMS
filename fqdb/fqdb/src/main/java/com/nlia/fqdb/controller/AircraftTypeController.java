package com.nlia.fqdb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.base.AircraftType;
import com.nlia.fqdb.service.impl.AircraftTypeManager;

@Controller
@RequestMapping("aircraftType")
public class AircraftTypeController {

	@Resource
	private AircraftTypeManager aircraftTypeManager;

	@RequestMapping(value = "findByAircraftTypeIATACode/{IATACode}", method = RequestMethod.GET)
	protected @ResponseBody
	List<AircraftType> findByAircraftTypeIATACode(@PathVariable String IATACode) {
		return aircraftTypeManager.findByAircraftTypeIATACode(IATACode);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	protected @ResponseBody
	List<AircraftType> findAllAircraftType() {
		return aircraftTypeManager.findAllAircraftType();
	}

}
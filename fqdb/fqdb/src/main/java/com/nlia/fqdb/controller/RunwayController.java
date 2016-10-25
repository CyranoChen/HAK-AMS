package com.nlia.fqdb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.base.Runway;
import com.nlia.fqdb.service.impl.RunwayManager;

@Controller
@RequestMapping("runway")
public class RunwayController{

	@Resource
	private RunwayManager runwayManager;
	
	@RequestMapping(value = "findByRunwayCode/{runwayCode}", method = RequestMethod.GET)
	protected @ResponseBody
	List<Runway> findByRunwayCode(@PathVariable String runwayCode) {
		return runwayManager.findByRunwayCode(runwayCode);
	}
	@RequestMapping(value = "", method = RequestMethod.GET)
	protected @ResponseBody
	List<Runway> findAllRunway() {
		return runwayManager.findAllRunway();
	}

}
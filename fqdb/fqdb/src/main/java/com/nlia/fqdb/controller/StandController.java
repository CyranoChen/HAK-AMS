package com.nlia.fqdb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.base.Stand;
import com.nlia.fqdb.service.impl.StandManager;

@Controller
@RequestMapping("stand")
public class StandController{

	@Resource
	private StandManager standManager;
	
	@RequestMapping(value = "findByStandCode/{standCode}", method = RequestMethod.GET)
	protected @ResponseBody
	List<Stand> findByStandCode(@PathVariable String standCode) {
		return standManager.findByStandCode(standCode);
	}
	@RequestMapping(value = "", method = RequestMethod.GET)
	protected @ResponseBody
	List<Stand> findAllStand() {
		return standManager.findAllStand();
	}

}
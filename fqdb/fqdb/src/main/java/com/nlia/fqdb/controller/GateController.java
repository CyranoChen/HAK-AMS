package com.nlia.fqdb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.base.Gate;
import com.nlia.fqdb.service.impl.GateManager;

@Controller
@RequestMapping("gate")
public class GateController{

	@Resource
	private GateManager gateManager;
	
	@RequestMapping(value = "findByGateCode/{gateCode}", method = RequestMethod.GET)
	protected @ResponseBody
	List<Gate> findByGateCode(@PathVariable String gateCode) {
		return gateManager.findByGateCode(gateCode);
	}
	@RequestMapping(value = "", method = RequestMethod.GET)
	protected @ResponseBody
	List<Gate> findAllGate() {
		return gateManager.findAllGate();
	}

}
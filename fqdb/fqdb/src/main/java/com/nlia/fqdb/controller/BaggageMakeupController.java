package com.nlia.fqdb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.base.BaggageMakeup;
import com.nlia.fqdb.service.impl.BaggageMakeupManager;

@Controller
@RequestMapping("baggageMakeup")
public class BaggageMakeupController {

	@Resource
	private BaggageMakeupManager baggageMakeupManager;

	@RequestMapping(value = "findByBaggageMakeupCode/{baggageMakeupCode}", method = RequestMethod.GET)
	protected @ResponseBody
	List<BaggageMakeup> findByBaggageMakeupCode(
			@PathVariable String baggageMakeupCode) {
		return baggageMakeupManager.findByBaggageMakeupCode(baggageMakeupCode);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	protected @ResponseBody
	List<BaggageMakeup> findAllBaggageMakeup() {
		return baggageMakeupManager.findAllBaggageMakeup();
	}

}
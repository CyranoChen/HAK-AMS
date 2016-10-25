package com.nlia.fqdb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.base.DelayCode;
import com.nlia.fqdb.service.impl.DelayCodeManager;

@Controller
@RequestMapping("delayCode")
public class DelayCodeController {

	@Resource
	private DelayCodeManager delayCodeManager;

	@RequestMapping(value = "findByDelayCode/{delayCode}", method = RequestMethod.GET)
	protected @ResponseBody
	List<DelayCode> findByDelayCode(@PathVariable String delayCode) {
		return delayCodeManager.findByDelayCode(delayCode);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	protected @ResponseBody
	List<DelayCode> findAllDelayCode() {
		return delayCodeManager.findAllDelayCode();
	}

}
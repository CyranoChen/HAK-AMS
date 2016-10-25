package com.nlia.fqdb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.base.Handler;
import com.nlia.fqdb.service.impl.HandlerManager;

@Controller
@RequestMapping("handler")
public class HandlerController{

	@Resource
	private HandlerManager handlerManager;
	
	@RequestMapping(value = "findByHandlerCode/{handlerCode}", method = RequestMethod.GET)
	protected @ResponseBody
	List<Handler> findByHandlerCode(@PathVariable String handlerCode) {
		return handlerManager.findByHandlerCode(handlerCode);
	}
	@RequestMapping(value = "", method = RequestMethod.GET)
	protected @ResponseBody
	List<Handler> findAllHandler() {
		return handlerManager.findAllHandler();
	}

}
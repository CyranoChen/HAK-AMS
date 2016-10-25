package com.nlia.fqdb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.base.PassengerClass;
import com.nlia.fqdb.service.impl.PassengerClassManager;

@Controller
@RequestMapping("passengerClass")
public class PassengerClassController{

	@Resource
	private PassengerClassManager passengerClassManager;
	
	@RequestMapping(value = "findByPassengerClassCode/{passengerClassCode}", method = RequestMethod.GET)
	protected @ResponseBody
	List<PassengerClass> findByPassengerClassCode(@PathVariable String passengerClassCode) {
		return passengerClassManager.findByPassengerClassCode(passengerClassCode);
	}
	@RequestMapping(value = "", method = RequestMethod.GET)
	protected @ResponseBody
	List<PassengerClass> findAllPassengerClass() {
		return passengerClassManager.findAllPassengerClass();
	}

}
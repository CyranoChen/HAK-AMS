package com.nlia.fqdb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.base.CheckInDesk;
import com.nlia.fqdb.service.impl.CheckInDeskManager;

@Controller
@RequestMapping("checkInDesk")
public class CheckInDeskController {

	@Resource
	private CheckInDeskManager checkInDeskManager;

	@RequestMapping(value = "findByCheckInDeskCode/{checkInDeskCode}", method = RequestMethod.GET)
	protected @ResponseBody
	List<CheckInDesk> findByCheckInDeskCode(@PathVariable String checkInDeskCode) {
		return checkInDeskManager.findByCheckInDeskCode(checkInDeskCode);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	protected @ResponseBody
	List<CheckInDesk> findAllCheckInDesk() {
		return checkInDeskManager.findAllCheckInDesk();
	}

}
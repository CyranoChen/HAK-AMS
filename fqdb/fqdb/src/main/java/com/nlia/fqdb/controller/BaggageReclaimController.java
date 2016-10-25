package com.nlia.fqdb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.base.BaggageReclaim;
import com.nlia.fqdb.service.impl.BaggageReclaimManager;

@Controller
@RequestMapping("baggageReclaim")
public class BaggageReclaimController {

    @Resource
    private BaggageReclaimManager baggageReclaimManager;

    @RequestMapping(value = "findByBaggageReclaimCode/{baggageReclaimCode}", method = RequestMethod.GET)
    protected @ResponseBody
    List<BaggageReclaim> findByBaggageReclaimCode(@PathVariable String baggageReclaimCode) {
	return baggageReclaimManager
		.findByBaggageReclaimCode(baggageReclaimCode);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    protected @ResponseBody
    List<BaggageReclaim> findAllBaggageReclaim() {
	return baggageReclaimManager.findAllBaggageReclaim();
    }

}
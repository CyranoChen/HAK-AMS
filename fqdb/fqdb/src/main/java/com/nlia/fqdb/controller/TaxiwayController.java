package com.nlia.fqdb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.base.Taxiway;
import com.nlia.fqdb.service.impl.TaxiwayManager;

@Controller
@RequestMapping("taxiway")
public class TaxiwayController{

	@Resource
	private TaxiwayManager taxiwayManager;
	
	@RequestMapping(value = "findByTaxiwayCode/{taxiwayCode}", method = RequestMethod.GET)
	protected @ResponseBody
	List<Taxiway> findByTaxiwayCode(@PathVariable String taxiwayCode) {
		return taxiwayManager.findByTaxiwayCode(taxiwayCode);
	}
	@RequestMapping(value = "", method = RequestMethod.GET)
	protected @ResponseBody
	List<Taxiway> findAllTaxiway() {
		return taxiwayManager.findAllTaxiway();
	}

}
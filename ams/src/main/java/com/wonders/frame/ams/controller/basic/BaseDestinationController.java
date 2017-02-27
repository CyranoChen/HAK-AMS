package com.wonders.frame.ams.controller.basic;


import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;
import com.wonders.frame.ams.model.basic.BaseDestination;
import com.wonders.frame.ams.service.basic.BaseDestinationService;

import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SimplePage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("jsp/basic")
public class BaseDestinationController {

    @Resource
    private BaseDestinationService baseDestinationService;


    @RequestMapping("baseDestination")
    public String toBaseDestination() {
        return "/basic/baseDestination";
    }

    @RequestMapping("queryDestination")
    @ResponseBody
    public SimplePage<BaseDestination> queryDestination(String airportIATACode, String airportICAOCode, String pagenum, String pagesize) {
       return baseDestinationService.query(airportIATACode, airportICAOCode, pagenum, pagesize);
    }

    @RequestMapping("queryDestinationById")
    @ResponseBody
    public BaseDestination queryAirportById(long id) {
        return baseDestinationService.findBaseDestinationById(id);
    }

    @RequestMapping("removeDestination")
    @ResponseBody
    public ReturnObj<Integer> removeAirport(HttpServletRequest req) {
        return baseDestinationService.remove(req);
    }

    @RequestMapping("updateDestination")
    @ResponseBody
    public ReturnObj<ILongIdRemoveFlagModel> updateAircraft(HttpServletRequest req) {
        return baseDestinationService.update(req);
    }

    @RequestMapping("saveDestination")
    @ResponseBody
    public ReturnObj<ILongIdRemoveFlagModel> saveAirport(HttpServletRequest req) {
        return baseDestinationService.save(req);
    }

}
































package com.wonders.frame.ams.controller.basic;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;
import com.wonders.frame.ams.model.basic.BaseAircraft;
import com.wonders.frame.ams.service.basic.BaseAircraftService;
import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SimplePage;


@Controller
@RequestMapping("jsp/basic")
public class BaseAircraftController {
	
	@Resource
	private BaseAircraftService baseAircraftService;
	
	@RequestMapping("baseAircraft")
    public String index(){
        return "/basic/baseAircraft";
    }
	
	@ResponseBody
	@RequestMapping("queryAircraft")
	public SimplePage<BaseAircraft> queryAircraft(String aircraftRegistration,String aircraftTypeICAOCode,String pagenum,String pagesize){
		return baseAircraftService.query(aircraftRegistration, aircraftTypeICAOCode,pagenum,pagesize);
	}
	
	@ResponseBody
	@RequestMapping("queryAircraftById")
	public BaseAircraft queryAircraftById(Long id){
		return baseAircraftService.findById(id);
	}
	
	@ResponseBody
	@RequestMapping("removeAircraft")
	public ReturnObj<Integer> remove(HttpServletRequest req) {
		return baseAircraftService.remove(req);
	}
	
	@ResponseBody
	@RequestMapping("updateAircraft")
	public ReturnObj<ILongIdRemoveFlagModel> updateAircraft(HttpServletRequest req) {
		return baseAircraftService.update(req);
	}
	
	@ResponseBody
	@RequestMapping("saveAircraft")
	public ReturnObj<ILongIdRemoveFlagModel> saveAircraft(HttpServletRequest req) {
		return baseAircraftService.save(req);
	}
}

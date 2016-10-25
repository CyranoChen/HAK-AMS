package com.wonders.frame.ams.controller.basic;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;
import com.wonders.frame.ams.model.basic.BaseAirline;
import com.wonders.frame.ams.service.basic.BaseAirlineService;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SimplePage;


@Controller
@RequestMapping("jsp/basic")
public class BaseAirlineController {
	
	@Resource
	private BaseAirlineService baseAirlineService;
	
	@RequestMapping("baseAirline")
    public String index(){
        return "/basic/baseAirline";
    }
	
	@ResponseBody
	@RequestMapping("queryAirline")
	public SimplePage<BaseAirline> queryAirline(String airlineIATACode,String airlineICAOCode,String airlineName,String pagenum,String pagesize){
		return baseAirlineService.query(airlineIATACode, airlineICAOCode,airlineName,pagenum,pagesize);
	}
	
	@ResponseBody
	@RequestMapping("removeAirline")
	public ReturnObj<Integer> remove(HttpServletRequest req) {
		return baseAirlineService.remove(req);
	}
	

	@ResponseBody
	@RequestMapping("queryAirlineById")
	public BaseAirline queryAircraftById(Long id){
		return baseAirlineService.findById(id);
	}
	
	
	@ResponseBody
	@RequestMapping("updateAirline")
	public ReturnObj<ILongIdRemoveFlagModel> updateAircraft(HttpServletRequest req) {
		return baseAirlineService.update(req);
	}
	
	@ResponseBody
	@RequestMapping("saveAirline")
	public ReturnObj<ILongIdRemoveFlagModel> saveAircraft(HttpServletRequest req) {
		return baseAirlineService.save(req);
	}
	
	@ResponseBody
	@RequestMapping("findAllAirline")
	public List<BaseAirline> findAllAirline(){
		return baseAirlineService.findAllAirline();
	}
	
}

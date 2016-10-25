package com.wonders.frame.ams.controller.basic;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;
import com.wonders.frame.ams.model.basic.BaseAirline;
import com.wonders.frame.ams.model.basic.BaseStand;
import com.wonders.frame.ams.service.basic.BaseStandService;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SimplePage;


@Controller
@RequestMapping("jsp/basic")
public class BaseStandController {
	
	@Resource
	private BaseStandService baseStandService;
	
	@RequestMapping("baseStand")
    public String index(){
        return "/basic/baseStand";
    }
	
	@ResponseBody
	@RequestMapping("queryStand")
	public SimplePage<BaseStand> queryStand(ServletRequest req,String standCode,String standTerminal,String pagenum,String pagesize){
		System.out.println(pagenum);
		System.out.println(pagesize);
		return baseStandService.query(standCode, standTerminal, pagesize, pagenum);
	}
	
	@ResponseBody
	@RequestMapping("removeStand")
	public ReturnObj<Integer> remove(HttpServletRequest req) {
		return baseStandService.remove(req);
	}
	

	@ResponseBody
	@RequestMapping("queryStandById")
	public BaseStand queryAircraftById(Long id){
		return baseStandService.findById(id);
	}
	
	
	@ResponseBody
	@RequestMapping("updateStand")
	public ReturnObj<ILongIdRemoveFlagModel> updateAircraft(HttpServletRequest req) {
		return baseStandService.update(req);
	}
	
	@ResponseBody
	@RequestMapping("saveStand")
	public ReturnObj<ILongIdRemoveFlagModel> saveAircraft(HttpServletRequest req) {
		return baseStandService.save(req);
	}

}

package com.wonders.frame.ams.controller.fqs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.ams.model.ChargeTerm;
import com.wonders.frame.ams.model.FlightMateInfo;
import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;
import com.wonders.frame.ams.service.fqs.ChargeTermService;
import com.wonders.frame.ams.service.fqs.FlightMateInfoService;
import com.wonders.frame.ams.utils.Chk;
import com.wonders.frame.core.model.vo.ReturnObj;

@Controller
@RequestMapping("fqs")
public class FeeController2 {

	@Resource
	private ChargeTermService chargeTermService;
	
	@Resource
    private FlightMateInfoService flightMateInfoService;
	
	@ResponseBody
	@RequestMapping(value = "/fee2/search")
	public Map<String,Object> findFeeByFlightMate(String flightMateInfoId,ServletRequest request){
		HashMap<String,Object> result = new HashMap<String,Object>();
		FlightMateInfo linkedFlightMateInfo=null;
		if(null!=flightMateInfoId){
			List<ChargeTerm> chargeTerms = chargeTermService.findAll(Long.parseLong(flightMateInfoId));
			FlightMateInfo currentFlightMateInfo = flightMateInfoService.findById(Long.parseLong(flightMateInfoId));
			result.put("chargeTerms", chargeTerms);
			result.put("currentFlightMateInfo",currentFlightMateInfo);
			if(null != currentFlightMateInfo && null != currentFlightMateInfo.getLinkFlightMateInfoId()){
				linkedFlightMateInfo = flightMateInfoService.findById(currentFlightMateInfo.getLinkFlightMateInfoId());
				result.put("linkedFlightMateInfo",linkedFlightMateInfo);
			}
			return result;
		}else{
			return null;
		}
	}
	
	@RequestMapping("fee2/index")
    public String index(HttpServletRequest request) {
		String id = request.getParameter("id");
		request.setAttribute("id",id);
        return "../fee2";     
    }
	
	@ResponseBody
	@RequestMapping("/fee2/updateChargeTerm")
	public ReturnObj<ILongIdRemoveFlagModel> updateChargeTerm(HttpServletRequest req) {
		return chargeTermService.update(req);
	}
	
	@ResponseBody
	@RequestMapping("findChargeTermByFmiId")
	public List<ChargeTerm> findChargeTermByFmiId(String flightMateInfoId){
		if(Chk.spaceCheck(flightMateInfoId)){
			List<ChargeTerm> chargeTerms = chargeTermService.findAll(Long.parseLong(flightMateInfoId));
			return chargeTerms;
		}
		return null;
	}

}

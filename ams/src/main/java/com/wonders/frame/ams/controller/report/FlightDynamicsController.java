package com.wonders.frame.ams.controller.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.ams.dto.report.SearchDto;
import com.wonders.frame.ams.service.report.FlightDynamicsService;
import com.wonders.frame.ams.service.report.ExportService;


@Controller
@RequestMapping("report")
public class FlightDynamicsController {
	
	@Resource
	private FlightDynamicsService s;
	
	@Resource
	private ExportService es;
	
	
	@RequestMapping("flightDynamics")
    public String index(){
        return "/report/flightDynamics";
    }
	
	@ResponseBody
    @RequestMapping("queryflightDynamics")
	public List<Map<String,Object>> query(HttpServletRequest request,SearchDto dto){
        List<Map<String,Object>> list = s.query(dto);
        HttpSession session = request.getSession();
        session.setAttribute("fddto", dto);
        return list;
    }
	
	@ResponseBody
	@RequestMapping("fdExport")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		SearchDto dto = (SearchDto)session.getAttribute("fddto");
		List<Map<String,Object>> list = s.query(dto) ;
		String time = dto.getStartTime().equals(dto.getEndTime()) ? dto.getStartTime() : dto.getStartTime() + "到" + dto.getEndTime();
		List l=new ArrayList();
		l.add(list);
		if("0".equals(dto.getFlightDirection())){
			es.exportExcel(response, l, "flightDynamicsA", time);
		}else{
			es.exportExcel(response, l, "flightDynamicsD", time);
		}
	}
}

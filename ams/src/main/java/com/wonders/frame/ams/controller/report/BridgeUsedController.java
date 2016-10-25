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
import com.wonders.frame.ams.service.report.BridgeUsedService;
import com.wonders.frame.ams.service.report.ExportService;


@Controller
@RequestMapping("report")
public class BridgeUsedController {
	
	@Resource
	private BridgeUsedService s;
	
	@Resource
	private ExportService es;
	
	
	@RequestMapping("bridgeUsed")
    public String index(){
        return "/report/bridgeUsed";
    }
	
	@ResponseBody
    @RequestMapping("querybridgeUsed")
	public List<Map<String,Object>> query(HttpServletRequest request,SearchDto dto){
        List<Map<String,Object>> list = s.query(dto);
        HttpSession session = request.getSession();
        session.setAttribute("budto", dto);
        return list;
    }
	
	@ResponseBody
	@RequestMapping("buExport")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		SearchDto dto = (SearchDto)session.getAttribute("budto");
		List<Map<String,Object>> list = s.query(dto) ;
		String time = dto.getStartTime().equals(dto.getEndTime()) ? dto.getStartTime() : dto.getStartTime() + "到" + dto.getEndTime();
		List l=new ArrayList();
		l.add(list);
		es.exportExcel(response, l, "bridgeUsed", time);
	}
}

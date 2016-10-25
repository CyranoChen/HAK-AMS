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

import com.wonders.frame.ams.service.report.AirlineEarningsService;
import com.wonders.frame.ams.service.report.ExportService;


@Controller
@RequestMapping("report")
public class AirlineEarningsController {
	
	@Resource
	private AirlineEarningsService s;
	
	@Resource
	private ExportService es;
	
	
	@RequestMapping("airlineEarnings")
    public String index(){
        return "/report/airlineEarnings";
    }
	
	@ResponseBody
    @RequestMapping("queryAirlineEarnings")
	public List<Map<String,Object>> query(HttpServletRequest request,String startTime,String endTime){
        List<Map<String,Object>> list = s.query(startTime,endTime);
        HttpSession session = request.getSession();
        session.setAttribute("astartTime", startTime);
        session.setAttribute("aendTime", endTime);
        return list;
    }
	
	@ResponseBody
	@RequestMapping("aExport")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		String startTime = session.getAttribute("astartTime").toString();
		String endTime = session.getAttribute("aendTime").toString();
		List<Map<String,Object>> list = s.query(startTime,endTime) ;
		String time = startTime.equals(endTime) ? startTime : startTime + "到" + endTime;
		List l=new ArrayList();
		l.add(list);
		es.exportExcel(response, l, "airlineEarnings", time);
	}
}

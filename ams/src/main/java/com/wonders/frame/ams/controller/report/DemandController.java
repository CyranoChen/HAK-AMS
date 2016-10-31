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

import com.wonders.frame.ams.service.report.DemandService;
import com.wonders.frame.ams.service.report.ExportService;


@Controller
@RequestMapping("report")
public class DemandController {
	
	@Resource
	private DemandService s;
	
	@Resource
	private ExportService es;
	
	
	@RequestMapping("demand")
    public String index(){
        return "/report/demand";
    }
	
	@ResponseBody
    @RequestMapping("queryDemand")
	public List<Map<String,Object>> query(HttpServletRequest request,String startTime,String endTime){
        List<Map<String,Object>> list = s.query(startTime,endTime,"");
        HttpSession session = request.getSession();
        session.setAttribute("dstartTime", startTime);
        session.setAttribute("dendTime", endTime);
        return list;
    }
	
	@ResponseBody
	@RequestMapping("dExport")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		List l = new ArrayList();
		String startTime = session.getAttribute("dstartTime").toString();
		String endTime = session.getAttribute("dendTime").toString();
		String time = startTime.equals(endTime) ? startTime : startTime + "到" + endTime;
		String sqlWhere1=" AND T.AIRLINE_HOME_COUNTRY = 'CN' ";

//		String sqlWhere1=" AND T.AIRLINE_HOME_COUNTRY = 'CN' "
//				+ " AND (T.FLIGHT_PROPERTY = 'B/J'"
//				+ " OR T.FLIGHT_PROPERTY = 'B/W'"
//				+ " OR T.FLIGHT_PROPERTY = 'C/B'"
//				+ " OR T.FLIGHT_PROPERTY = 'E/T'"
//				+ " OR T.FLIGHT_PROPERTY = 'F/H'"
//				+ " OR T.FLIGHT_PROPERTY = 'J/B'"
//				+ " OR T.FLIGHT_PROPERTY = 'L/W'"
//				+ " OR T.FLIGHT_PROPERTY = 'W/Z'"
//				+ " OR T.FLIGHT_PROPERTY = 'Z/P'"
//				+ " OR T.FLIGHT_PROPERTY = 'N/M'" q
//				+ " OR T.FLIGHT_PROPERTY = 'Z/X')";
		List<Map<String,Object>> l1 = s.query(startTime,endTime,sqlWhere1);
		l.add(l1);
//		String sqlWhere2=" AND (T.AIRLINE_HOME_COUNTRY IS NULL OR T.AIRLINE_HOME_COUNTRY<> 'CN') ";
		String sqlWhere2=" AND ( T.AIRLINE_HOME_COUNTRY NOT IN ('CN','GW')  OR T.AIRLINE_HOME_COUNTRY IS NULL ) ";

		List<Map<String,Object>> l2 = s.query(startTime,endTime,sqlWhere2);
		l.add(l2);

		String sqlWhere3=" AND T.AIRLINE_HOME_COUNTRY = 'GW' ";

//		String sqlWhere3=" AND T.AIRLINE_HOME_COUNTRY = 'CN' "
//				+ " AND (T.FLIGHT_PROPERTY <> 'B/J'"
//				+ " AND T.FLIGHT_PROPERTY <> 'B/W'"
//				+ " AND T.FLIGHT_PROPERTY <> 'C/B'"
//				+ " AND T.FLIGHT_PROPERTY <> 'E/T'"
//				+ " AND T.FLIGHT_PROPERTY <> 'F/H'"
//				+ " AND T.FLIGHT_PROPERTY <> 'J/B'"
//				+ " AND T.FLIGHT_PROPERTY <> 'L/W'"
//				+ " AND T.FLIGHT_PROPERTY <> 'W/Z'"
//				+ " AND T.FLIGHT_PROPERTY <> 'Z/P'"
//				+ " AND T.FLIGHT_PROPERTY <> 'N/M'"
//				+ " AND T.FLIGHT_PROPERTY <> 'Z/X')";
		List<Map<String,Object>> l3 = s.query(startTime,endTime,sqlWhere3);
		l.add(l3);
		es.exportExcel(response, l, "demand", time);
	}
}

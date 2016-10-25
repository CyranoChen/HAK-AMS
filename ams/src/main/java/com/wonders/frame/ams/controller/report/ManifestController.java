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
import com.wonders.frame.ams.service.report.ManifestService;
import com.wonders.frame.ams.service.report.ExportService;


@Controller
@RequestMapping("report")
public class ManifestController {
	
	@Resource
	private ManifestService s;
	
	@Resource
	private ExportService es;
	
	
	@RequestMapping("manifest")
    public String index(){
        return "/report/manifest";
    }
	
	@ResponseBody
    @RequestMapping("querymanifest")
	public List<Map<String,Object>> query(HttpServletRequest request,SearchDto dto){
        List<Map<String,Object>> list = s.query(dto);
        HttpSession session = request.getSession();
        session.setAttribute("mdto", dto);
        return list;
    }
	
	@ResponseBody
	@RequestMapping("mExport")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		SearchDto dto = (SearchDto)session.getAttribute("mdto");
		List<Map<String,Object>> list = s.query(dto) ;
		String time = dto.getStartTime().equals(dto.getEndTime()) ? dto.getStartTime() : dto.getStartTime() + "到" + dto.getEndTime();
		List l=new ArrayList();
		l.add(list);
		es.exportExcel(response, l, "manifest", time);
	}
}

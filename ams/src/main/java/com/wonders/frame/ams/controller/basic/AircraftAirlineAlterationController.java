package com.wonders.frame.ams.controller.basic;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wonders.frame.ams.rmi.IFlightMateInfoManager;
import com.wonders.frame.ams.utils.Chk;
import com.wonders.frame.ams.utils.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.ams.dto.basic.AircraftAirlineAlterationDto;
import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;
import com.wonders.frame.ams.model.basic.AircraftAirlineAlteration;
import com.wonders.frame.ams.service.basic.AircraftAirlineAlterationService;
import com.wonders.frame.ams.vo.basic.AircraftAirlineAlterationVo;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SimplePage;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("jsp/basic")
public class AircraftAirlineAlterationController {
	
	@Resource
	private AircraftAirlineAlterationService aircraftAirlineAlterationService;

	@Resource
	private IFlightMateInfoManager flightMateInfoManager;



	@ResponseBody
	@RequestMapping("registrationImport")
	public String upload(HttpServletRequest request) throws IOException, InterruptedException {
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		HttpSession session = request.getSession();

//		session.removeAttribute("manifest_import_back_file");

		Map<String, MultipartFile> fileMap = req.getFileMap();

//        synchronized (SYNC){
		try{
			for(Iterator<String> it = fileMap.keySet().iterator() ; it.hasNext() ; ){
				String key = it.next();
				MultipartFile f = fileMap.get(key);


				String filename = f.getOriginalFilename().toLowerCase();

				if(Chk.spaceCheck(filename) && ( filename.endsWith(".xlsx") || filename.endsWith(".xls") ) ){
					Map<String,Object> rtn = null;

					String fileSaveDirPath = session.getServletContext().getRealPath("/") + "registration_file";// + File.separator + filename;

					File dir = new File(fileSaveDirPath);
					if(! dir.exists()){
						dir.mkdirs();
					}

					String fileSavePath = fileSaveDirPath + File.separator + System.currentTimeMillis() + "_" + filename;


					System.out.println(fileSavePath);


					f.transferTo(new File(fileSavePath));

//					fileSavePath = "/root/Download/apache-tomcat-7.0.57/webapps/ams/registration_file/456.xls";


					rtn = flightMateInfoManager.importAircraftAirlineAlterationDataByExcel(fileSavePath);

					String error = StringUtil.nullToEmptyString(rtn.get("error"));


					StringBuffer sb = new StringBuffer();
//					String globalError = (String)rtn.get("globalError");
//
//					if(Chk.spaceCheck(globalError)){
//						return globalError;
//					}
//
//
//
					String totalCount = StringUtil.nullToEmptyString(rtn.get("totalCount"));
					String successCount = StringUtil.nullToEmptyString(rtn.get("successCount"));
					String errorCount = StringUtil.nullToEmptyString(rtn.get("errorCount"));
//					List<String> noFound = ( List<String> )rtn.get("noFound");
//					if(noFound == null){
//						noFound = new ArrayList<String>();
//					}
//					List<String> error = ( List<String> )rtn.get("error");
//					if(error == null){
//						error = new ArrayList<String>();
//					}

//					String backFile = StringUtil.nullToEmptyString(rtn.get("File"));
//					if(Chk.spaceCheck(backFile)){
//						session.setAttribute("manifest_import_back_file",backFile);
//						sb.append("manifest_import_back_file");
//					}




					sb.append("<执行完成> \n");
					sb.append(" 总共上传" + totalCount + "条数据 \n");
					sb.append(" 成功更新" + successCount + "条\n");
					sb.append(" 执行失败" + errorCount + "条\n");

					if(Chk.spaceCheck(error)){
						sb.append(" 出现异常" + error + "\n");
					}

//					noFound.addAll(error);
//					if(Chk.emptyCheck(noFound)){
//						sb.append("<执行失败>\n");
//						for (String s : noFound){
//							sb.append(" " + s + "\n");
//						}
//					}



					return sb.toString();


				}else{
					return "文件的类型错误!";
				}
//                    break;
			}
		}catch (Exception e){
			e.printStackTrace();
			return  "上传失败";
		}
//        }
		return "上传失败";
	}



	
	@RequestMapping("aircraftAirlineAlteration")
    public String index(){
        return "/basic/aircraftAirlineAlteration";
    }
	
	@ResponseBody
	@RequestMapping("queryAircraftAirlineAlteration")
	public SimplePage<AircraftAirlineAlterationVo> queryAircraftAirlineAlteration(AircraftAirlineAlterationDto aaaform,String pagenum,String pagesize){
		
		SimplePage<AircraftAirlineAlterationVo> rs = aircraftAirlineAlterationService.query(aaaform,pagenum,pagesize);
		return rs;
	}
	
	@ResponseBody
	@RequestMapping("removeAircraftAirlineAlteration")
	public ReturnObj<Integer> remove(HttpServletRequest req) {
		return aircraftAirlineAlterationService.remove(req);
	}
	
	@ResponseBody
	@RequestMapping("queryAircraftAirlineAlterationById")
	public AircraftAirlineAlteration queryAircraftById(Long id){
		return aircraftAirlineAlterationService.findById(id);
	}
	
	
	@ResponseBody
	@RequestMapping("updateAircraftAirlineAlteration")
	public ReturnObj<ILongIdRemoveFlagModel> updateAircraftAirlineAlteration(HttpServletRequest req) {
		return aircraftAirlineAlterationService.update(req);
	}
	
	@ResponseBody
	@RequestMapping("saveAircraftAirlineAlteration")
	public ReturnObj<ILongIdRemoveFlagModel> saveAircraftAirlineAlteration(HttpServletRequest req) {
		return aircraftAirlineAlterationService.save(req);
	}
}

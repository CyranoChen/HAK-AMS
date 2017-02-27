package com.wonders.frame.ams.controller.fqs;

import com.wonders.frame.ams.bean.FlightLoadDataBean;
import com.wonders.frame.ams.bean.FlightMateDetailBean;
import com.wonders.frame.ams.bean.FlightMateFormBean;
import com.wonders.frame.ams.bean.FlightMateListBean;
import com.wonders.frame.ams.rmi.IFlightMateInfoManager;
import com.wonders.frame.ams.service.fqs.FlightMateSearchService;
import com.wonders.frame.ams.utils.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * Created by 3701 on 2015/12/10.
 */

@Controller
@RequestMapping("fqs/flightMateSearch")
public class FlightMateSearchController {

    public static final String FLIGHT_MATE_DETAIL_BEAN = "flightMateDetailBean";
    public static final String ROLE_ID = "role_id";


    @Resource
    private FlightMateSearchService flightMateSearchService;

    @Resource
    private IFlightMateInfoManager flightMateInfoManager;

    @ResponseBody
    @RequestMapping("addLoadData")
    public Object addLoadData(){


        return flightMateSearchService.getInsertSeq();
    }

    @RequestMapping("index")
    public String index(HttpServletRequest request,String role) throws IOException {
//        Map<String, Object>  rtn = flightMateInfoManager.flightMatedInfoInit(1000000l);
//        Map<String, Object>  rtn2 = flightMateInfoManager.flightMatedInfoInit(1000001l);


        if( ! Chk.integerCheck(role,"+") || Integer.parseInt(role) > 5){
            role = "1";
        }

        request.getSession().setAttribute(ROLE_ID, role);


        return "/fqs/flightMateSearch";
    }



    @ResponseBody
    @RequestMapping("calLoadData")
    public String calLoadData(String id){

        if(Chk.integerCheck(id)){
            List<Long> list = new ArrayList<Long>();
            list.add(Long.parseLong(id));
            flightMateInfoManager.saveFlightLoadData2FlightMateInfoByIds(list);
            return "success";
        }else{
            return "非法的航班ID";
        }

    }


    @ResponseBody
    @RequestMapping("getGridCols")
    public Map<String,List<Map<String,String>>> getGridCols(){
        Map<String,List<Map<String,String>>> rtn = new HashMap<String,List<Map<String,String>>>();

        LinkedHashMap<String,String> pros = new LinkedHashMap<String,String>((Map)ResourceUtil.getProperties("flightMate/gridCols",true));

        List<Map<String,String>> columns = new ArrayList<Map<String,String>>();
        List<Map<String,String>> datafields = new ArrayList<Map<String,String>>();

        if(Chk.emptyCheck(pros)){
            for(Iterator<String> it = pros.keySet().iterator(); it.hasNext() ;){
                String key = (String)it.next();
                String value = pros.get(key);
                if(Chk.spaceCheck(key) && Chk.spaceCheck(value)){
                    //构造jqxGrid - columns 属性
                    if(! "0".equals(value)){
                        Map<String,String> col = new LinkedHashMap<String,String>();
                        col.put("datafield",key);
//                        if("scheduledTime".equals(key)){
////                            col.put("cellsformat","yyyy/MM/dd");
//                            col.put("filtertype", "list");
//                        }
                        col.put("text",value);
                        col.put("align","center");
                        col.put("cellsalign","center");
//                    col.put("cellclassname","cellclass");
                        columns.add(col);
                    }

                    //构造jqxGrid - datafields 属性
                    Map<String,String> datafield = new LinkedHashMap<String,String>();
                    datafield.put("name", key);
                    datafield.put("type", "string");

                    datafields.add(datafield);
                }
            }
        }
        rtn.put("columns", columns);
        rtn.put("datafields", datafields);


        return rtn;
    }

    @ResponseBody
    @RequestMapping("query")
    public List<FlightMateListBean> query(HttpServletRequest request,FlightMateFormBean form,String error) {
        List<FlightMateListBean> list = flightMateSearchService.list(form);
        request.getSession().setAttribute("flightMateListForm", form);

        List<FlightMateListBean> result = new ArrayList<FlightMateListBean>();
        if(Chk.spaceCheck(error)){

            String [] errors = error.split(",");



            for(FlightMateListBean b : list){
                b.getAlarmMessage();
                for(String e : errors){
                    if(b.getAlarmMessageCode().contains(e)){
                        result.add(b);
                        break;
                    }
                }
            }
            return result;
        }else{
            return list;
        }

    }

    @ResponseBody
    @RequestMapping("detail")
    public FlightMateDetailBean detail(HttpServletRequest request,Long id) {
        FlightMateDetailBean f = flightMateSearchService.queryById(id);
        if(f != null){
            request.getSession().setAttribute(FLIGHT_MATE_DETAIL_BEAN,f);
        }
        return f;
    }


    @ResponseBody
    @RequestMapping("getLoadData")
    public List<FlightLoadDataBean> getLoadData(Long id){
        return flightMateSearchService.queryByFlightMateInfoId(id);
    }


    @ResponseBody
    @RequestMapping("deleteLoadData")
    public Object updateLoadData(HttpServletRequest request,Long id,String role){
        return null;
    }


        @ResponseBody
    @RequestMapping("updateLoadData")
    public Object updateLoadData(HttpServletRequest request,Long id,Long loadDataId,String colname,String value,String role){

        FlightMateDetailBean f = (FlightMateDetailBean) request.getSession().getAttribute(FLIGHT_MATE_DETAIL_BEAN);
        if(f == null){
            f = flightMateSearchService.queryById(id);
            if( f == null ){
                return "该航班数据可能已经被修改或删除,请重新查询";
            }
            request.getSession().setAttribute(FLIGHT_MATE_DETAIL_BEAN,f);
        }

        if( ! Chk.spaceCheck(role)){
            return "请登录后修改";
        }


        String rtn = flightMateSearchService.update(loadDataId,"flight_load_data",colname,value);
        if("success".equals(rtn)){
            flightMateSearchService.update(loadDataId,"flight_load_data","generate_method","1");
            Map<String,String> m = new HashMap<String,String>();
            /**
             * 记录修改者的角色
             */
            String ur = f.getUpdateRole();
            if( ! Chk.spaceCheck(ur)){
                ur = role;
            }else{
                if( ! ur.equals(role)){
                    ur = ur.replace("," + role + ",",",");
                    ur = ur.replaceAll("," + role, "");
                    ur = ur.replaceAll(role + ",","");
                    ur += ( "," + role);
                }
            }
            flightMateSearchService.update(id,"flight_mate_info", "updateRole", ur);
            f.setUpdateRole(ur);
            m.put("updateRole",ur);
            return m;
        }else{
            return rtn;
        }

    }



    @ResponseBody
    @RequestMapping("resource")
    public List<Map<String,String>> resource(String type) {

        if( ! Chk.spaceCheck(type)){
            return null;
        }

        List<Map<String,String>> rtn = new ArrayList<Map<String, String>>();

        LinkedHashMap<String,String> pros = new LinkedHashMap<String,String>((Map)ResourceUtil.getProperties("flightMate/resourceService",true));

        for(Iterator<String> it = pros.keySet().iterator() ; it.hasNext() ; ){
            String key = it.next();
            String value = pros.get(key);
            if(Chk.spaceCheck(key) && Chk.spaceCheck(value) && key.startsWith(type + "_")){
                String [] res = value.split(",");
                if (res.length != 3){
                    continue;
                }
                String resourceName = res[0];
                String unit = res[1];
                String unitPrice = res[2];

                Map<String,String> m  = new HashMap<String, String>();
                m.put("resourceName",resourceName);
                m.put("colname",key.split("_")[1]);
                m.put("startTime","");
                m.put("endTime","");
                m.put("endTime","");
                m.put("quantity","");
                m.put("unit",unit);
                m.put("unitPrice",unitPrice);

                rtn.add(m);

            }
        }
        return rtn;

    }

    @ResponseBody
    @RequestMapping("resourceUpdate")
    public Object resourceUpdate(HttpServletRequest request,Long id,String colname,String value,String role){

        FlightMateDetailBean f = (FlightMateDetailBean) request.getSession().getAttribute(FLIGHT_MATE_DETAIL_BEAN);
        if(f == null){
            f = flightMateSearchService.queryById(id);
            if( f == null ){
                return "该航班数据可能已经被修改或删除,请重新查询";
            }
            request.getSession().setAttribute(FLIGHT_MATE_DETAIL_BEAN,f);
        }

        if( ! Chk.spaceCheck(role)){
            return "请登录后修改";
        }

        String rtn = "success";
        String formatColname = "";
        try{
            rtn = flightMateSearchService.update(id,"flight_mate_info",colname,value);
        }catch (Exception e){
            try{
                formatColname =  ( "0".equals(f.flightDirection) ? "a" : "d" ) + (new StringBuilder()).append(Character.toUpperCase(colname.charAt(0))).append(colname.substring(1)).toString();
                rtn = flightMateSearchService.update(id,"flight_mate_info",formatColname,value);
            }catch (Exception e1){
                e1.printStackTrace();
                rtn = "无效的列名";
            }
        }


        /**
         * 执行成功后,进行告警检查
         * 生成最新数据 推送到前端
         */
        if("success".equals(rtn)){

            ReflectUtil.setValue(colname, f, value);
            Map<String,String> m = new HashMap<String,String>();
            if(Chk.spaceCheck(formatColname)){
                m.put(formatColname,value);
            }
            m.put(colname,value);

            /**
             * 修改机位时需要 重新计算近远机位标识
             */
            if("standNum".equals(colname)){
                String standFlag;
                if( ! Chk.spaceCheck(value) || "HOLD".equalsIgnoreCase(value)){
                    standFlag = "";
                }else{
                    standFlag = flightMateInfoManager.isBridgeStand(value) + "";
                }


                flightMateSearchService.update(id,"flight_mate_info", "0".equals(f.flightDirection) ? "a_stand_flag" : "d_stand_flag",standFlag);

                ReflectUtil.setValue("standFlag", f, standFlag);
                FlightMateListBean o = new FlightMateListBean();
                o.setStandFlag(standFlag);
                m.put("standFlag", o.getStandFlag());
            }
            /**
             * 修改旅客数量时需要重新计算客座率
             */
            else if("passengerInternal".equals(colname) || "childInternal".equals(colname)  || "babyInternal".equals(colname)  || "passengerInternational".equals(colname)  || "childInternational".equals(colname)  || "babyInternational".equals(colname) ){
                String passengerloadFactor = flightMateSearchService.updatePassengerloadFactor(id);
                m.put("passengerloadFactor", passengerloadFactor);
            }







            /**
             * 记录修改者的角色
             */
            String ur = f.getUpdateRole();
            if( ! Chk.spaceCheck(ur)){
                ur = role;
            }else{
                if( ! ur.equals(role)){
                    ur = ur.replace("," + role + ",",",");
                    ur = ur.replaceAll("," + role, "");
                    ur = ur.replaceAll(role + ",","");
                    ur += ( "," + role);
                }
            }

            flightMateSearchService.update(id,"flight_mate_info", "updateRole", ur);
            f.setUpdateRole(ur);
            m.put("updateRole",ur);


            m.put("alarmMessage",f.getAlarmMessage());
            return m;
        }

        return rtn;
    }



    @RequestMapping("backfile")
    public void backfile(HttpServletRequest request,HttpServletResponse response){

        String fileName = (String) request.getSession().getAttribute("manifest_import_back_file");

//        fileName = "D:\\flightloaddata_verify.xls";

        System.out.println("feedback_file_path : " + fileName);
        File file = new File(fileName);
        String [] pathArr = fileName.split("\\.");

        response.setContentType("application/x-msdownloadoctet-stream");
        response.setHeader("Content-disposition", "attachment;filename=verify_feedback." + pathArr[pathArr.length - 1]);
        response.setContentType("application/octet-stream");

        OutputStream out = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte [] b = new byte[fis.available()];
            out = response.getOutputStream();

            if( fis.read(b) != -1 ){
                out.write(b);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }





    }


    @ResponseBody
    @RequestMapping("manifestImport")
    public String upload(HttpServletRequest request) throws IOException, InterruptedException {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        HttpSession session = request.getSession();

        session.removeAttribute("manifest_import_back_file");

        Map<String, MultipartFile> fileMap = req.getFileMap();

//        synchronized (SYNC){
        try{
            for(Iterator<String> it = fileMap.keySet().iterator() ; it.hasNext() ; ){
                String key = it.next();
                MultipartFile f = fileMap.get(key);


                String filename = f.getOriginalFilename().toLowerCase();

                if(Chk.spaceCheck(filename) && ( filename.endsWith(".xlsx") || filename.endsWith(".xls") ) ){
                    Map<String,Object> rtn = null;

                    String fileSaveDirPath = session.getServletContext().getRealPath("/") + "manifest_file";// + File.separator + filename;

                    File dir = new File(fileSaveDirPath);
                    if(! dir.exists()){
                        dir.mkdirs();
                    }

                    String fileSavePath = fileSaveDirPath + File.separator + System.currentTimeMillis() + "_" + filename;
//                        String fileSavePath = fileSaveDirPath + File.separator + filename;

                    f.transferTo(new File(fileSavePath));


//edit by wangsq 20160105
                    rtn = flightMateInfoManager.importFlightLoadDataByExcel(fileSavePath);
//                        if(filename.endsWith(".xlsx")){
//                            rtn =  flightMateInfoManager.importFlightLoadDataByExcel2007(f.getBytes());
//                        }else{
//                            rtn = flightMateInfoManager.importFlightLoadDataByExcel2003(f.getBytes());
//                        }

                    StringBuffer sb = new StringBuffer();
                    String globalError = (String)rtn.get("globalError");

                    if(Chk.spaceCheck(globalError)){
                        return globalError;
                    }



                    String totalCount = StringUtil.nullToEmptyString(rtn.get("totalCount"));
                    String successCount = StringUtil.nullToEmptyString(rtn.get("successCount"));
                    List<String> noFound = ( List<String> )rtn.get("noFound");
                    if(noFound == null){
                        noFound = new ArrayList<String>();
                    }
                    List<String> error = ( List<String> )rtn.get("error");
                    if(error == null){
                        error = new ArrayList<String>();
                    }

                    String backFile = StringUtil.nullToEmptyString(rtn.get("File"));
                    if(Chk.spaceCheck(backFile)){
                        session.setAttribute("manifest_import_back_file",backFile);
                        sb.append("manifest_import_back_file");
                    }




                    sb.append("<执行完成> \n");
                    sb.append(" 总共上传" + totalCount + "条数据 \n");
                    sb.append(" 成功更新" + successCount + "条航班\n");

                    noFound.addAll(error);
                    if(Chk.emptyCheck(noFound)){
                        sb.append("<执行失败>\n");
                        for (String s : noFound){
                            sb.append(" " + s + "\n");
                        }
                    }



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


    @RequestMapping("/export")
    public void export(HttpServletRequest request,HttpServletResponse response){
        LinkedHashMap<String,String> pros = new LinkedHashMap<String,String>((Map)ResourceUtil.getProperties("flightMate/gridCols",true));
        FlightMateFormBean form = (FlightMateFormBean)request.getSession().getAttribute("flightMateListForm");

        List<FlightMateListBean> list = flightMateSearchService.list(form);
        String xml = flightMateSearchService.getXmlToExport(pros,list);
        ExcelUtil.output(response,xml,System.currentTimeMillis()+"");
    }

    @ResponseBody
    @RequestMapping("flightMatch")
    public Object flightMatch(String baseId){

        if( ! Chk.integerCheck(baseId)){
            return "无效的航班BASE ID";
        }


//        result.put("total",resultMap.get("leftFlightBases") );
//        result.put("done",((List)resultMap.get("flightMateInfos")).size());
//        result.put("noLink",resultMap.get("fbNoLinkFlightMes"));
//        result.put("noReg",resultMap.get("fbNoRegisterationMes") );
//        result.put("noLinkReg",resultMap.get("fbLinkNoRegisterationMes"));
//        result.put("notComplete", resultMap.get("fbNotComplate"));
//
//
//        ResultMessages += "总计航班 ：" + mapResult.get("leftFlightBases") +";";
//        ResultMessages += "生成：" +  ((List)mapResult.get("flightMateInfos")).size() +";";
//        ResultMessages += "缺少对应连班：" +  mapResult.get("fbNoLinkFlightMes")+";";
//        ResultMessages += "缺少机号：" + mapResult.get("fbNoRegisterationMes") +";";
//        ResultMessages += "对应连班缺少机号：" + mapResult.get("fbLinkNoRegisterationMes")+";";
//        ResultMessages += "数据不完整：" + mapResult.get("fbNotComplate")+";";


        //返回map键值对，数据类似："总计航班 ：347;生成：300;缺少对应连班：47;缺少机号：0;对应连班缺少机号：0;数据不完整：57;"
        try{
            Map<String,Object> rtn = flightMateInfoManager.flightMatedInfoInit(Long.parseLong(baseId));

            System.out.println(rtn);
        }catch (Exception e){
            e.printStackTrace();
            return "配对异常";
        }

        return "success";



    }
    
    
    @ResponseBody
    @RequestMapping("flightMatchByDate")
    public Object flightMatchByDate(String startTime, String endTime){
    	
    	Map<String, Object> source = new HashMap<String, Object>();

    	if( ! Chk.dateCheck2(startTime) || ! Chk.dateCheck2(endTime)){
    		source.put("success", false);
    		source.put("msg", "无效的日期");
    		return source;
        }
    	
        try{
        	
        	startTime = DateUtil.parseDate(startTime + " 05:00:00");
        	
        	endTime = DateUtil.addDay(DateUtil.parseDate(endTime + " 04:59:59"), 1, "yyyy-MM-dd HH:mm:ss");
        	
        	
        	Map<String, Object> map = new HashMap<String,Object>(); 
        	
        	map.put("startTime", startTime);
        	map.put("endTime", endTime);
        	
        	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        	
        	int days = DateUtil.getDaysBetween(startTime, endTime) + 1;
        	if(days > 8){
        		
        		
        		while(days > 8){
        			
        			Map<String, String> m = new HashMap<String, String>();
            		m.put("startTime", startTime);
            		m.put("endTime", DateUtil.addDay(startTime.split(" ")[0]+ " 04:59:59", 8, "yyyy-MM-dd HH:mm:ss"));
            		list.add(m);
            		
            		startTime = DateUtil.addDay(startTime, 8, "yyyy-MM-dd HH:mm:ss");
            		days = DateUtil.getDaysBetween(startTime, endTime) + 1;
            		
            		if(days <= 8 && days > 1){
            			
            			m = new HashMap<String, String>();
                		m.put("startTime", startTime);
                		m.put("endTime", endTime);
                		list.add(m);
            		}
            		
            	}
        		
        	}else{
        		
        		Map<String, String> m = new HashMap<String, String>();
        		m.put("startTime", startTime);
        		m.put("endTime", endTime);
        		list.add(m);
        		
        	}
    		
    		//System.out.println(ArrayUtil.join(list.toArray()));
    		
    		
    		
    		for(int i = 0; i < list.size(); i++){
    			
    			Map<String, String> m = list.get(i);
    			
    			//返回map键值对，数据类似："总计航班 ：347;生成：300;缺少对应连班：47;缺少机号：0;对应连班缺少机号：0;数据不完整：57;"
    			Map<String,Object> rtn = flightMateInfoManager.flightMatedInfoInitByPeriod(DateUtil.parse(m.get("startTime"), "yyyy-MM-dd HH:mm:ss"), DateUtil.parse(m.get("endTime"), "yyyy-MM-dd HH:mm:ss"));
                
                Set<String> keys = rtn.keySet();

                for(String key :keys){
                	
                	if(! Chk.spaceCheck(String.valueOf(map.get(key) == null ? "" : map.get(key)))){
                		map.put(key, 0);
                	}
                	map.put(key, Integer.parseInt(String.valueOf(rtn.get(key))) +  Integer.parseInt(String.valueOf(map.get(key))));

                }
                
    		}

    		
    		source.put("success", true);
    		source.put("msg", map);
    		return source;
        }catch (Exception e){
            e.printStackTrace();
            source.put("success", false);
    		source.put("msg", "配对异常");
    		return source;
        }

    }
    
    
    @ResponseBody
    @RequestMapping("flightLoad")
    public Object flightLoad(String baseId){

        if( ! Chk.integerCheck(baseId)){
            return "无效的航班BASE ID";
        }

//        接口方法定义：public Map<String, Object> flightLoadDataInit(Long id);
//        入参id：flightBase的主键ID
//        返回的map：key为“total”，value是生成的配载记录数量；
        try{
            Map<String,Object> rtn = flightMateInfoManager.flightLoadDataInit(Long.parseLong(baseId));
            System.out.println(rtn);
        }catch (Exception e){
            e.printStackTrace();
            return "配对异常";
        }

        return "success";



    }


}

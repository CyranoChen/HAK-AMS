package com.wonders.frame.ams.controller.basic;

import com.wonders.frame.ams.component.JqxColumn;
import com.wonders.frame.ams.component.JqxColumnGroup;
import com.wonders.frame.ams.component.JqxDatafield;
import com.wonders.frame.ams.component.JqxGrid;
import com.wonders.frame.ams.dto.basic.DiscountDto;
import com.wonders.frame.ams.service.basic.DiscountManagerService;
import com.wonders.frame.ams.utils.Chk;
import com.wonders.frame.ams.utils.DateUtil;
import com.wonders.frame.ams.utils.ExcelUtil;
import com.wonders.frame.ams.utils.StringUtil;
import com.wonders.frame.core.utils.DateFormatUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by wangsq on 2016/9/14.
 */
@Controller
@RequestMapping("basic/discountManager")
public class DiscountManagerController {
    @Autowired
    private DiscountManagerService discountManagerService;



    @RequestMapping("/index")
    public String index(){
        return "/basic/discountManager";
    }



    @ResponseBody
    @RequestMapping("/query")
    public Object queryByGroup(String registration,String airlineOfFlight,HttpServletRequest request){


        Map<String,String> subject = discountManagerService.queryChargeSubjectName();

        Map<String,String> type =  discountManagerService.queryChargeTypeName();

        List<DiscountDto> list = discountManagerService.queryByGroup(registration, airlineOfFlight);


        JqxGrid g = new JqxGrid();

        List<Map<String,String>> data = new ArrayList<Map<String,String>>();

        String [] keys = new String [] {};


        List<String> reportTtile = new ArrayList<String>();
        List<String> reportTtileName = new ArrayList<String>();

        for(int i = 0 ; i < list.size() ; i ++){
            DiscountDto d = list.get(i);

            if(i == 0){
                g.getDatafields().add(new JqxDatafield("registration","string"));
                g.getDatafields().add(new JqxDatafield("airlineDescription","string"));
                g.getDatafields().add(new JqxDatafield("airlineOfFlight","string"));
                g.getDatafields().add(new JqxDatafield("aircraftTypeIataCode","string"));
                g.getDatafields().add(new JqxDatafield("aircraftSeatCapacity","string"));

                g.getColumns().add(new JqxColumn("registration", "机号", null, "75", true));
                g.getColumns().add(new JqxColumn("airlineDescription", "航空公司", null, "90", true));
                g.getColumns().add(new JqxColumn("airlineOfFlight", "二字码", null, "60", true));
                g.getColumns().add(new JqxColumn("aircraftTypeIataCode", "机型", null, "90", true));
                g.getColumns().add(new JqxColumn("aircraftSeatCapacity", "座位数", null, "60", true));

                Set<String> groupSet = new HashSet<String>();

               keys = d.getChargetypeId().split("-");

                for(String key : keys ){
                    String [] t = key.split(",");
                    String typeId = t[0];
                    String typeName = Chk.spaceCheck(typeId) ? type.get(typeId) : "";
                    String [] typeNames = typeName.split("］");
                    if(typeNames.length > 1){
                        typeName = typeNames[typeNames.length - 1];
                    }

                    String subjectId = t.length > 1 ? t[1] : "";
                    String subjectName =  Chk.spaceCheck(subjectId) ? subject.get(subjectId) : "";
                    g.getDatafields().add(new JqxDatafield(key,"string"));

                    boolean isSubject = Chk.spaceCheck(subjectId);
                    JqxColumn jc = new JqxColumn(key, isSubject ? subjectName : typeName, isSubject ? typeId : null, "100", false);


                    if(isSubject){
                        reportTtile.add(typeId + "," + subjectId);
                        reportTtileName.add(typeName + "," + subjectName);
                    }else{
                        reportTtile.add(typeId + ",");
                        reportTtileName.add(typeName);
                    }


                    jc.setFiltercondition("equal");
                    g.getColumns().add(jc);
                    if( ! groupSet.contains(typeId)){
                        JqxColumnGroup gp = new JqxColumnGroup();
                        gp.setText(typeName);
                        gp.setName(typeId);
                        groupSet.add(typeId);
                        g.getGroups().add(gp);
                    }
                }
            }

            Map<String,String> m = new LinkedHashMap<String,String>();

            m.put("registration",d.getRegistration());
            m.put("airlineOfFlight",d.getAirlineOfFlight());
            m.put("aircraftTypeIataCode",d.getAircraftTypeIataCode());
            m.put("aircraftSeatCapacity",d.getAircraftSeatCapacity());
            m.put("airlineDescription",d.getAirlineDescription());


            String [] discounts = d.getDiscount().split("-");
            for(int j = 0 ; j < discounts.length ; j ++){
                String discount = discounts[j];
                if( ! Chk.spaceCheck(discount) || "$".equals(discount)){
                    discount = "";
                }

                if(keys.length != discounts.length){
                    System.out.println();
                }

                m.put(keys[j],discount);
            }
            data.add(m);
        }


        Map<String,Object> rtn = new HashMap<String,Object>();




        rtn.put("g",g);
        rtn.put("data", data);

        HttpSession session = request.getSession();




        session.setAttribute("discount_report_title", reportTtile);
        session.setAttribute("discount_report_title_name", reportTtileName);


        return rtn;
    }






    @ResponseBody
    @RequestMapping("/update")
    public boolean update(String registration,String key,String discount){


        if( ! Chk.spaceCheck(registration)){
            return false;
        }

        if( ! Chk.spaceCheck(key)){
            return false;
        }

        String [] keys = key.split(",");
        for(String k : keys){
            if( !Chk.integerCheck(k)){
                return false;
            }
        }

        if(!Chk.numberCheck(discount)){
            return false;
        }

        String [] discounts = discount.split("\\.");
        if( ! Chk.maxCheck(discounts[0],5)){
            return false;
        }

        if(discounts.length > 1 &&  ! Chk.maxCheck(discounts[1],4)){
            return false;
        }


        return discountManagerService.updateDiscount(registration,key,discount);
    }




    @ResponseBody
    @RequestMapping("/imp")
    public String imp(HttpServletRequest request){

        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        HttpSession session = request.getSession();

        Map<String, MultipartFile> fileMap = req.getFileMap();


        try{
            for(Iterator<String> it = fileMap.keySet().iterator() ; it.hasNext() ; ){
                String key = it.next();
                MultipartFile f = fileMap.get(key);


                String filename = f.getOriginalFilename().toLowerCase();

                if(Chk.spaceCheck(filename) &&  filename.endsWith(".xls")  ){
                    HSSFWorkbook wb = new HSSFWorkbook(f.getInputStream());
                    String rtn = discountManagerService.saveDiscountFromExcel(request,wb);
                    return rtn;
                }else{
                    return "文件的类型错误!";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return  "上传失败";
        }






        return "";
    }




    @ResponseBody
    @RequestMapping("/exp")
    public void print(String registration,String airlineOfFlight,HttpServletRequest request,HttpServletResponse response){

        Map<String,Object> rtn = (Map<String, Object>) queryByGroup(registration, airlineOfFlight, request);


        HSSFWorkbook wb = discountManagerService.exp(rtn,request);


        ExcelUtil.output(response, wb, "折扣表");

    }




}

package com.wonders.frame.ams.controller.report;

import com.wonders.frame.ams.controller.basic.DiscountManagerController;
import com.wonders.frame.ams.service.basic.DiscountManagerService;
import com.wonders.frame.ams.service.report.NewRegistrationService;
import com.wonders.frame.ams.utils.Chk;
import com.wonders.frame.ams.utils.ExcelUtil;
import com.wonders.frame.ams.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by 3701 on 2016/10/19.
 */
@Controller
@RequestMapping("report")
public class NewRegistrationController {


    @Autowired
    private NewRegistrationService newRegistrationService;

    @Autowired
    private DiscountManagerService discountManagerService;

    @Autowired
    private DiscountManagerController discountManagerController;





    @RequestMapping("newRegistration")
    public String index(){
        return "/report/newRegistration";
    }

    @ResponseBody
    @RequestMapping("query")
    public Object query(HttpServletRequest request,String registration,String airlineOfFlight){



        List<Map<String,Object>> newRegList = newRegistrationService.queryNewRegistration(registration,airlineOfFlight);

        Map<String,Object> rtn = (Map<String, Object>) discountManagerController.queryByGroup(registration, airlineOfFlight, request);

        if( ! Chk.emptyCheck(newRegList)){
            rtn.put("data",null);
            return rtn;
        }


        //用于折扣表中的分组 以 航空公司 + 机型 &  航空公司 分组
        Map<String,Map<String,String>> groupMap = new HashMap<String,Map<String,String>>();

        List<Map<String,String>> data = (List<Map<String, String>>) rtn.get("data");

        for(Map<String,String> m : data){
            //分别航空公司+机型 和 航空公司 为key存入map
            String key1 = StringUtil.nullToEmptyString(m.get("airlineOfFlight"));
            String key2 = key1 + "," + StringUtil.nullToEmptyString(m.get("aircraftTypeIataCode"));
            groupMap.put(key1,m);
            groupMap.put(key2,m);
        }



        List<Map<String,String>> result = new ArrayList<Map<String, String>>();

        for(Map<String,Object> newReg : newRegList){
            String key1 = StringUtil.nullToEmptyString(newReg.get("AIRLINE_IATA_CODE"));
            String key2 = key1 + "," + StringUtil.nullToEmptyString("AIRCRAFT_TYPE_IATA_CODE");

            //先取 相同航空公司 相同机型的
            Map<String,String> defaultReg = groupMap.get(key2);
            //无法取得时 获取相同航空公司
            if(defaultReg == null){
                defaultReg = groupMap.get(key1);
            }


            String reg = StringUtil.nullToEmptyString(newReg.get("AIRCRAFT_REGISTRATION"));

            if(defaultReg == null){
                defaultReg = new HashMap<String, String>();
            }

            Map<String,String> nm = new HashMap<String, String>();
            for(Iterator<String> it = defaultReg.keySet().iterator() ; it.hasNext() ;){
                String key = it.next();
                String value = defaultReg.get(key);

                nm.put(key,value);
            }


            nm.put("registration",reg);
            nm.put("airlineOfFlight",StringUtil.nullToEmptyString(newReg.get("AIRLINE_IATA_CODE")));
            nm.put("aircraftTypeIataCode",StringUtil.nullToEmptyString(newReg.get("AIRCRAFT_TYPE_IATA_CODE")));
            nm.put("airlineDescription",StringUtil.nullToEmptyString(newReg.get("AIRLINE_DESCRIPTION")));
            nm.put("aircraftSeatCapacity",StringUtil.nullToEmptyString(newReg.get("AIRCRAFT_SEAT_CAPACITY")));


            result.add(nm);




        }


        rtn.put("data",result);
        return rtn;
    }




    @ResponseBody
    @RequestMapping("/exp")
    public void print(String registration,String airlineOfFlight,HttpServletRequest request,HttpServletResponse response){

        Map<String,Object> rtn = (Map<String, Object>) query(request,registration,airlineOfFlight);

        HSSFWorkbook wb = discountManagerService.exp(rtn,request);

        ExcelUtil.output(response, wb, "新增机号-折扣表");

    }
}

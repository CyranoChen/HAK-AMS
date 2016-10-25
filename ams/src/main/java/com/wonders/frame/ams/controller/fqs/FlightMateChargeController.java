package com.wonders.frame.ams.controller.fqs;

import com.wonders.frame.ams.bean.FlightMateFormBean;
import com.wonders.frame.ams.bean.FlightMateListBean;
import com.wonders.frame.ams.rmi.IFlightMateInfoManager;
import com.wonders.frame.ams.service.fqs.FlightMateSearchService;
import com.wonders.frame.ams.utils.Chk;
import com.wonders.frame.ams.utils.ResourceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by 3701 on 2015/12/24.
 */
@Controller
@RequestMapping("fqs/flightMateCharge")
public class FlightMateChargeController {

    @Resource
    private IFlightMateInfoManager flightMateInfoManager;

    @Resource
    private FlightMateSearchService flightMateSearchService;

    @RequestMapping("index")
    public String index(HttpServletRequest request,String role) throws IOException {
        return "/fqs/flightMateCharge";
    }

    @ResponseBody
    @RequestMapping("getGridCols")
    public Map<String,List<Map<String,String>>> getGridCols(){
        Map<String,List<Map<String,String>>> rtn = new HashMap<String,List<Map<String,String>>>();

        LinkedHashMap<String,String> pros = new LinkedHashMap<String,String>((Map) ResourceUtil.getProperties("flightMate/chargeGridCols", true));

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
    public Map<String,List<FlightMateListBean>> query(FlightMateFormBean form) {
        List<FlightMateListBean> list = flightMateSearchService.list(form);

        List<FlightMateListBean> notcal = new ArrayList<FlightMateListBean>();
        List<FlightMateListBean> cal = new ArrayList<FlightMateListBean>();

        for(FlightMateListBean b : list){
            String stat = b.getStat();
            if("0".equals(stat)){
                continue;
            }

            if(Chk.numberCheck(stat) && Integer.parseInt(stat) > 10){
                cal.add(b);
            }else{
                notcal.add(b);
            }
        }

        Map<String,List<FlightMateListBean>> rtn = new HashMap<String, List<FlightMateListBean>>();
        rtn.put("0",notcal);
        rtn.put("1",cal);
        return rtn;
    }

    @RequestMapping("toErrorPage")
    public String toErrorPage(HttpServletRequest request){
        return "/fqs/flightMateChargeErrorMsg";
    }




    @ResponseBody
    @RequestMapping("cal")
    public Object cal(String ids,HttpServletRequest request) {
        int resultNum = 0;
        if(ids == null){
            return resultNum;
        }

        String [] idArr = ids.split(",");
        List<Long> list = new ArrayList<Long>();
        for(String id : idArr){
            if(Chk.numberCheck(id)){
                list.add(Long.parseLong(id));
            }
        }


        if(list.isEmpty()){
            return null;
        }

        try{

            Map<String,String> rtn = flightMateInfoManager.updateChargeTermById(list);

            request.getSession().setAttribute("calErrorMsg",rtn.get("msg"));

            return rtn;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}

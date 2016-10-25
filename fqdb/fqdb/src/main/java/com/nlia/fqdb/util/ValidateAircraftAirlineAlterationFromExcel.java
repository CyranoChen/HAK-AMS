package com.nlia.fqdb.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.nlia.fqdb.configuration.DataHandler;
import com.nlia.fqdb.entity.AircraftAirlineAlteration;
import com.nlia.fqdb.entity.AircraftAirlineAlteration.VALIDFLAG;
import com.nlia.fqdb.entity.base.AircraftType;
import com.nlia.fqdb.entity.base.Airline;
import com.nlia.fqdb.message.AircraftAirlineAlterationMessages;
import com.nlia.fqdb.service.impl.AirlineManager;

@Component
public class ValidateAircraftAirlineAlterationFromExcel {
    
       
	@Resource
    private AirlineManager airlineManager;
	
    private List<Airline> airlineAllList ;
    public void getairlineAllList(){
        airlineAllList = airlineManager.findAllAirline();
    }
    

	/**
     * 校验单行记录数据，返回实体对象[返回的实体中带有校验信息]
     * @param rowValue
     * @return
     */
    public AircraftAirlineAlteration validateSingleData(
            Map<String, String> rowValue, int rowNumber) {

        AircraftAirlineAlteration aircraftAirlineAlteration = new AircraftAirlineAlteration();
        aircraftAirlineAlteration.setRemoveFlag("1");
        String verifyDescription = "";
        Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
        ArrayList<Integer> errorCells = new ArrayList<>();

        DataHandler<Airline> dataHandler = new DataHandler<>();
//        DataHandler<AircraftType> dataHandlerAircraftType = new DataHandler<>();
        Map<String, Object> filters = new HashMap<>();

        // 验证字段是否符合规则
        // validateEntityProperty(rowValue,verifyDescription,errorCells);

        // 判断机号是否为空
        if (rowValue.get("飞机注册号") != null && !rowValue.get("飞机注册号").isEmpty()) {
            aircraftAirlineAlteration.setAircraftRegistration(rowValue
                    .get("飞机注册号"));
        } else {
            errorCells.add(1);
            verifyDescription += "[机号]不能为空;";
            verifyDescription += " ";
        }

        // 判断机型是否为空
        List<AircraftType> aircraftTypes = null;
        if (rowValue.get("机型ICAO") != null && !rowValue.get("机型ICAO").isEmpty()) {
//            filters.clear();
//            filters.put("aircraftTypeICAOCode_equal", rowValue.get("机型ICAO"));
//            aircraftTypes = dataHandlerAircraftType.findBy(
//                    aircraftTypeAllinList, filters);
//            if (aircraftTypes.size() == 1) {
                aircraftAirlineAlteration.setAircraftTypeICAOCode(rowValue
                        .get("机型ICAO"));
//            } else {
//                errorCells.add(2);
//                verifyDescription += "[机型ICAO]与数据库中机型不匹配;";
//                verifyDescription += " ";
//            }
        } else {
            aircraftAirlineAlteration.setAircraftTypeICAOCode("");
//            errorCells.add(2);
//            verifyDescription += "[机型ICAO]不能为空;";
//            verifyDescription += " ";
        }

        if (rowValue.get("机型IATA") != null && !rowValue.get("机型IATA").isEmpty()) {
//            if (aircraftTypes != null
//                    && aircraftTypes.size() == 1
//                    && rowValue.get("机型IATA").equals(
//                            aircraftTypes.get(0).getAircraftTypeIATACode())) {
                aircraftAirlineAlteration.setAircraftTypeIATACode(rowValue
                        .get("机型IATA"));
//            } else {
//                errorCells.add(3);
//                verifyDescription += "[机型IATA]与数据库中机型不匹配;";
//                verifyDescription += " ";
//            }

        } else {
            aircraftAirlineAlteration.setAircraftTypeIATACode("");
//            errorCells.add(3);
//            verifyDescription += "[机型IATA]不能为空;";
//            verifyDescription += " ";
        }
        // 判断描述说明是否为空
        if (rowValue.get("描述说明") != null && !rowValue.get("描述说明").isEmpty()) {
            aircraftAirlineAlteration.setAircraftDescription(rowValue
                    .get("描述说明"));
        }/*
          * else{ errorCells.add(4); verifyDescription +="[描述说明]不能为空;";
          * verifyDescription += " "; }
          */
        // 判断引擎数量是否符合规则
        if (rowValue.get("引擎数量") != null && !rowValue.get("引擎数量").isEmpty()) {
            if (isNumeric(rowValue.get("引擎数量"))) {
                aircraftAirlineAlteration.setAircraftEngineNumber(Long
                        .valueOf(rowValue.get("引擎数量")));
            } else {
                errorCells.add(5);
                verifyDescription += "列\"" + LoadColumnName.get(5) + "\"必须是数字;";
                verifyDescription += " ";
            }
        }

        // 判断最大起飞重量是否符合规则
        if (rowValue.get("最大起飞重量") != null && !rowValue.get("最大起飞重量").isEmpty()) {
            if (isNumeric(rowValue.get("最大起飞重量"))) {
                aircraftAirlineAlteration.setAircraftTakeoffWeight(Long
                        .valueOf(rowValue.get("最大起飞重量")));
            } else {
                errorCells.add(6);
                verifyDescription += "列\"" + LoadColumnName.get(6) + "\"必须是数字;";
                verifyDescription += " ";
            }
        }
        // 判断最大落地重量是否符合规则
        if (rowValue.get("最大落地重量") != null && !rowValue.get("最大落地重量").isEmpty()) {
            if (isNumeric(rowValue.get("最大落地重量"))) {
                aircraftAirlineAlteration.setAircraftLandingWeight(Long
                        .valueOf(rowValue.get("最大落地重量")));
            } else {
                errorCells.add(7);
                verifyDescription += "列\"" + LoadColumnName.get(7) + "\"必须是数字;";
                verifyDescription += " ";
            }
        }
        // 判断最大配载数是否符合规则
        if (rowValue.get("最大配载数") != null && !rowValue.get("最大配载数").isEmpty()) {
            if (isNumeric(rowValue.get("最大配载数"))) {
                aircraftAirlineAlteration.setAircraftPayload(Long
                        .valueOf(rowValue.get("最大配载数")));
            } else {
                errorCells.add(8);
                verifyDescription += "列\"" + LoadColumnName.get(8) + "\"必须是数字;";
                verifyDescription += " ";
            }
        }
        // 判断最大客座率是否符合规则
        if (rowValue.get("最大客座数") != null && !rowValue.get("最大客座数").isEmpty()) {
            if (isNumeric(rowValue.get("最大客座数"))) {
                aircraftAirlineAlteration.setAircraftSeatCapacity(Long
                        .valueOf(rowValue.get("最大客座数")));
            } else {
                errorCells.add(9);
                verifyDescription += "列\"" + LoadColumnName.get(9) + "\"必须是数字;";
                verifyDescription += " ";
            }
        }
        // 判断噪音级别是否符合规则
        if (rowValue.get("噪音级别") != null && !rowValue.get("噪音级别").isEmpty()) {
            if (isNumeric(rowValue.get("噪音级别"))) {
                aircraftAirlineAlteration.setAircraftNoiseCategory(rowValue
                        .get("噪音级别"));
            } else {
                errorCells.add(10);
                verifyDescription += "列\"" + LoadColumnName.get(10)
                        + "\"必须是数字;";
                verifyDescription += " ";
            }
        }
        // 判断高度是否符合规则
        if (rowValue.get("高度") != null && !rowValue.get("高度").isEmpty()) {
            if (isNumeric(rowValue.get("高度"))) {
                aircraftAirlineAlteration.setAircraftHeight(Long
                        .valueOf(rowValue.get("高度")));
            } else {
                errorCells.add(11);
                verifyDescription += "列\"" + LoadColumnName.get(11)
                        + "\"必须是数字;";
                verifyDescription += " ";
            }
        }
        // 判断长度是否符合规则
        if (rowValue.get("长度") != null && !rowValue.get("长度").isEmpty()) {
            if (isNumeric(rowValue.get("长度"))) {
                aircraftAirlineAlteration.setAircraftLength(Long
                        .valueOf(rowValue.get("长度")));
            } else {
                errorCells.add(12);
                verifyDescription += "列\"" + LoadColumnName.get(12)
                        + "\"必须是数字;";
                verifyDescription += " ";
            }
        }
        // 判断宽度是否符合规则
        if (rowValue.get("宽度") != null && !rowValue.get("宽度").isEmpty()) {
            if (isNumeric(rowValue.get("宽度"))) {
                aircraftAirlineAlteration.setAircraftWidth(Long
                        .valueOf(rowValue.get("宽度")));
            } else {
                errorCells.add(13);
                verifyDescription += "列\"" + LoadColumnName.get(13)
                        + "\"必须是数字;";
                verifyDescription += " ";
            }
        }
        // 判断现属航空主公司标识是否存在
        if (rowValue.get("现属主航空公司") != null
                && !rowValue.get("现属主航空公司").isEmpty()) {
            filters.clear();
            filters.put("airlineDescription_equal", rowValue.get("现属主航空公司"));
            List<Airline> airlines = dataHandler
                    .findBy(airlineAllList, filters);
            if (airlines.size() == 1) {
                aircraftAirlineAlteration.setCurrentAirline(airlines.get(0));
                aircraftAirlineAlteration.setOriginalAirline(airlines.get(0));
            } else {
                errorCells.add(14);
                verifyDescription += "列\"" + LoadColumnName.get(14)
                        + "\"现属主航空公司错误;";
                verifyDescription += " ";
            }
        }
        // 判断现属航空公司标识是否存在
        if (rowValue.get("现属航空公司") != null
                && !rowValue.get("现属航空公司").isEmpty()) {
            filters.clear();
            filters.put("airlineDescription_equal", rowValue.get("现属航空公司"));
            List<Airline> airlines = dataHandler
                    .findBy(airlineAllList, filters);
            if (airlines.size() == 1) {
                aircraftAirlineAlteration.setCurrentSubairline(airlines.get(0));
                aircraftAirlineAlteration
                        .setOriginalSubairline(airlines.get(0));
            } else {
                errorCells.add(15);
                verifyDescription += "列\"" + LoadColumnName.get(15)
                        + "\"现属航空公司错误;";
                verifyDescription += " ";
            }
        }
        // 判断航空公司二字码是否为空
        if (rowValue.get("航空公司二字码") != null
                && !rowValue.get("航空公司二字码").isEmpty()) {
            aircraftAirlineAlteration.setAirlineOfFlight(rowValue
                    .get("航空公司二字码"));
        } else {
            errorCells.add(16);
            verifyDescription += "[航空公司二字码]不能为空;";
            verifyDescription += " ";
        }
        // 判断开始日期是否符合规则
        if (DateUtils.isValidDate(rowValue.get("开始日期"), "yyyy-MM-dd")) {
            aircraftAirlineAlteration.setStartDate(DateUtils.String2Date(
                    rowValue.get("开始日期"), "yyyy-MM-dd"));
        } else {
            errorCells.add(17);
            verifyDescription += "[开始日期]不符合格式yyyy-MM-dd;";
            verifyDescription += " ";
        }
        // 判断结束日期是否符合规则
        if (DateUtils.isValidDate(rowValue.get("结束日期"), "yyyy-MM-dd")) {
            aircraftAirlineAlteration.setEndDate(DateUtils.String2Date(
                    rowValue.get("结束日期"), "yyyy-MM-dd"));
        } else {
            errorCells.add(18);
            verifyDescription += "[结束日期]不符合格式yyyy-MM-dd;";
            verifyDescription += " ";
        }
        // 判断是否有效
        if (rowValue.get("有效标识") != null && !rowValue.get("有效标识").isEmpty()) {
            if (rowValue.get("有效标识").equals("有效")
                    || rowValue.get("有效标识").equals("无效")) {
                aircraftAirlineAlteration.setValidFlag((rowValue.get("有效标识")
                        .equals("有效")) ? VALIDFLAG.VALID : VALIDFLAG.INVALID);
            } else {
                errorCells.add(19);
                verifyDescription += "列\"" + LoadColumnName.get(18)
                        + "\"必须是有效或者无效;";
                verifyDescription += " ";
            }
        }
        // 判断宽体、窄体
        if (rowValue.get("宽体、窄体") != null
                && !rowValue.get("宽体、窄体").isEmpty()) {
            if (rowValue.get("宽体、窄体").equals("L")
                    || rowValue.get("宽体、窄体").equals("P")) {
            aircraftAirlineAlteration.setIsWideOrNarrow(rowValue
                    .get("宽体、窄体"));
            }
        } else {
            aircraftAirlineAlteration.setIsWideOrNarrow("P");
        }
        
        // 判断高密度
        if (rowValue.get("高密度") != null
                && !rowValue.get("高密度").isEmpty()) {
            if (rowValue.get("高密度").equals("Y")
                    || rowValue.get("高密度").equals("N")) {
            aircraftAirlineAlteration.setIsHighDensity(rowValue
                    .get("高密度"));
            }
        } else {
            aircraftAirlineAlteration.setIsHighDensity("N");
        }
        //集装设备
        if (rowValue.get("集装设备") != null
                && !rowValue.get("集装设备").isEmpty()) {
            if (rowValue.get("集装设备").equals("Y")
                    || rowValue.get("集装设备").equals("N")) {
            aircraftAirlineAlteration.setIsPackagingFacility(rowValue
                    .get("集装设备"));
            }
        } else {
            aircraftAirlineAlteration.setIsPackagingFacility("N");
        }
        // 验证结束

        if (!verifyDescription.equals("")) {
            verifyDescription = AircraftAirlineAlterationMessages
                    .getString("SEQUENCE") + (rowNumber + 1) + AircraftAirlineAlterationMessages.getString("ROW") + verifyDescription; //$NON-NLS-2$
            aircraftAirlineAlteration.setVerifyDescription(verifyDescription);
        }

        errorMessage.put(rowNumber, errorCells);
        aircraftAirlineAlteration.setErrorMessage(errorMessage);
        return aircraftAirlineAlteration;
    }
     

    /**
     * 校验对应的列名是否相同
     * 
     * @param index,单元格的序号
     * @param name,单元格额值
     * @return  相同返回true
     */
    public static boolean verifyColumnName(int index, String name) {
        boolean isCorrectColumnName = true;
    	if(name==null || name.isEmpty()||!name.equals(LoadColumnName.get(index))){
    	    isCorrectColumnName = false;
    	}
    	return isCorrectColumnName;
    }
    //add by march 20140826 start 导入配载信息
    private static Map<Integer,String> LoadColumnName =new HashMap<Integer,String>(){
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        { 
            put(    1   ,   "飞机注册号");
            put(    2   ,   "机型ICAO");
            put(    3   ,   "机型IATA");
            put(    4   ,   "描述说明");
            put(    5   ,   "引擎数量");
            put(    6   ,   "最大起飞重量");
            put(    7   ,   "最大落地重量");
            put(    8   ,   "最大配载数");
            put(    9   ,   "最大客座数");
            put(    10  ,   "噪音级别");
            put(    11  ,   "高度");
            put(    12  ,   "长度");
            put(    13  ,   "宽度");
            put(    14  ,   "现属主航空公司");
            put(    15  ,   "现属航空公司");
            put(    16  ,   "航空公司二字码");
            put(    17  ,   "开始日期");
            put(    18  ,   "结束日期");
            put(    19  ,   "有效标识");
            put(    20  ,   "宽体、窄体");
            put(    21  ,   "高密度");
            put(    22  ,   "集装设备");
        }           
    };              
    public static boolean isNumeric(String str){ 
        for (int i = str.length();--i>=0;){   
            if (!Character.isDigit(str.charAt(i))){
             return false;
            }
           }
           return true;
//        str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
//        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+)))$");
     } 
  //add by march 20140826 end 导入配载信息
    
  //导入CAACSC格式,即清算中心格式
    private static List<String> CAACSCExcleNeedColumnName = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
            add("机号");
            add("机型");
            add("最大起飞全重");
            add("业载");
            add("最大座位数");
            add("宽/窄体");
            add("飞机属性");
            add("航空公司代码");
            add("航空公司两字代码");
            add("航空公司名称");
            add("备注");
            add("开始日期");
            add("结束日期");

        }
    };
    public String columnsContansCAACSCExcleNeedColumnName(List<String> columns) {
        String ret="";
        for (String strColumnName : CAACSCExcleNeedColumnName) {
            if(!columns.contains(strColumnName)){
                ret +="必须包含字段[" + strColumnName +"];" ;
            }
        }
        return ret;
    }
    public AircraftAirlineAlteration validate_CAACSC_SingleData(
            Map<String, String> rowValue, Map<String, Integer> columnIndex,
            int rowNumber,List<AircraftAirlineAlteration> aircraftAirlineAlterationAllList) {
        DataHandler<AircraftAirlineAlteration> aaaDataHandler = new DataHandler<>();
        
        AircraftAirlineAlteration aircraftAirlineAlteration = new AircraftAirlineAlteration();
        aircraftAirlineAlteration.setValidFlag(VALIDFLAG.VALID);
        aircraftAirlineAlteration.setIsWideOrNarrow("P");
        aircraftAirlineAlteration.setIsPackagingFacility("N");
        aircraftAirlineAlteration.setIsHighDensity("N");
        aircraftAirlineAlteration.setRemoveFlag("1");
        String verifyDescription = "";
        Map<Integer, List<Integer>> errorMessage = new HashMap<Integer, List<Integer>>();
        ArrayList<Integer> errorCells = new ArrayList<>();

        DataHandler<Airline> dataHandler = new DataHandler<>();
        // DataHandler<AircraftType> dataHandlerAircraftType = new
        // DataHandler<>();
        Map<String, Object> filters = new HashMap<>();
        String columnName;
        String value;
        columnName = "机号";
        value = rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
        } else {
            aircraftAirlineAlteration.setAircraftRegistration(value);
        }
        columnName = "机型";
        value = rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
        } else {
            aircraftAirlineAlteration.setAircraftTypeIATACode(value);
            aircraftAirlineAlteration.setAircraftTypeICAOCode(value);
        }
        columnName = "最大起飞全重";
        value = rowValue.get(columnName);
        if (value != null && !value.isEmpty()) {
            if (isNumeric(value)) {
                aircraftAirlineAlteration.setAircraftTakeoffWeight(Long.valueOf(value));
            }else {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]必须是数字;";
            }
        } 
        columnName = "业载";
        value = rowValue.get(columnName);
        if (value != null && !value.isEmpty()) {
            if (isNumeric(value)) {
                aircraftAirlineAlteration.setAircraftPayload(Long.valueOf(value));
            }else {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]必须是数字;";
            }
        } 
        columnName = "最大座位数";
        value = rowValue.get(columnName);
        if (value != null && !value.isEmpty()) {
            if (isNumeric(value)) {
                aircraftAirlineAlteration.setAircraftSeatCapacity(Long.valueOf(value));
            }else {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]必须是数字;";
            }
        } 
        columnName = "航空公司代码";
        value = rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
        } 
        columnName = "航空公司两字代码";
        value = rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
        } else {
            aircraftAirlineAlteration.setAirlineOfFlight(value);
        }
        columnName = "航空公司名称";
        value = rowValue.get(columnName);
        if (value != null && !value.isEmpty()) {
            filters.clear();
            filters.put("airlineDescription_equal", value);
            List<Airline> airlines = dataHandler
                    .findBy(airlineAllList, filters);
            if (airlines.size() == 1) {
                aircraftAirlineAlteration.setCurrentSubairline(airlines.get(0));
                aircraftAirlineAlteration.setOriginalSubairline(airlines.get(0));
                if(airlines.get(0).getParentAirline()!=null){
                    aircraftAirlineAlteration.setCurrentAirline(airlines.get(0).getParentAirline());
                    aircraftAirlineAlteration.setOriginalAirline(airlines.get(0).getParentAirline());
                }else{
                    aircraftAirlineAlteration.setCurrentAirline(airlines.get(0));
                    aircraftAirlineAlteration.setOriginalAirline(airlines.get(0));
                }
            } else {//airlineDescription找不到
                if(rowValue.get("航空公司两字代码")!=null && !rowValue.get("航空公司两字代码").isEmpty()
                   && rowValue.get("航空公司代码")!=null && !rowValue.get("航空公司代码").isEmpty()){
                    filters.clear();
                    filters.put("airlineIATACode_equal", rowValue.get("航空公司两字代码"));
                    filters.put("airlineUniqueCode_equal", rowValue.get("航空公司代码"));
                    airlines = dataHandler.findBy(airlineAllList, filters);
                    if (airlines.size() == 1) {
                        aircraftAirlineAlteration.setCurrentSubairline(airlines.get(0));
                        aircraftAirlineAlteration.setOriginalSubairline(airlines.get(0));
                        if(airlines.get(0).getParentAirline()!=null){
                            aircraftAirlineAlteration.setCurrentAirline(airlines.get(0).getParentAirline());
                            aircraftAirlineAlteration.setOriginalAirline(airlines.get(0).getParentAirline());
                        }else{
                            aircraftAirlineAlteration.setCurrentAirline(airlines.get(0));
                            aircraftAirlineAlteration.setOriginalAirline(airlines.get(0));
                        }
                    } else {//二码+airline_unique_code找不到
                        filters.clear();
                        filters.put("airlineIATACode_equal", rowValue.get("航空公司两字代码"));
                        filters.put("airlineICAOCode_equal", rowValue.get("航空公司代码"));
                        filters.put("basicDataID_equal", rowValue.get("航空公司两字代码"));
                        airlines = dataHandler.findBy(airlineAllList, filters);
                        if (airlines.size() == 1) {
                            aircraftAirlineAlteration.setCurrentSubairline(airlines.get(0));
                            aircraftAirlineAlteration.setOriginalSubairline(airlines.get(0));
                            if(airlines.get(0).getParentAirline()!=null){
                                aircraftAirlineAlteration.setCurrentAirline(airlines.get(0).getParentAirline());
                                aircraftAirlineAlteration.setOriginalAirline(airlines.get(0).getParentAirline());
                            }else{
                                aircraftAirlineAlteration.setCurrentAirline(airlines.get(0));
                                aircraftAirlineAlteration.setOriginalAirline(airlines.get(0));
                            }
                        } else {//二码+airlineICAOCode找不到
                            errorCells.add(columnIndex.get(columnName));
                            verifyDescription += "列[" + columnName + "]对应航空公司找不到;";
                        }
                    }
                }
            }
        }else{
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
        }
        
        String[] datePatterns = {"yyyyMMdd","yyyy-MM-dd","yyyy/MM/dd","MM/dd/yy"};
        
        columnName = "开始日期";
        value = rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
        } else {
//            if (!DateUtils.isValidDate(value, "yyyyMMdd")) {
//                errorCells.add(columnIndex.get(columnName));
//                verifyDescription  += "列[" + columnName + "]不符合格式yyyyMMdd;";
//            } else {
//                aircraftAirlineAlteration.setStartDate(DateUtils.String2Date(
//                        value, "yyyyMMdd"));
//            }
            boolean isValidDate=false;
            for (String datePattern : datePatterns) {
                if (DateUtils.isValidDate(value, datePattern)) {
                    aircraftAirlineAlteration.setStartDate(DateUtils.String2Date(value, datePattern));
                    isValidDate=true;
                }
            }
            if (!isValidDate) {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列[" + columnName + "]不符合格式;";
                verifyDescription += " ";
            }
        }
        columnName = "结束日期";
        value = rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
        } else {
//            if (!DateUtils.isValidDate(value, "yyyyMMdd")) {
//                errorCells.add(columnIndex.get(columnName));
//                verifyDescription += "列[" + columnName + "]不符合格式yyyyMMdd;";
//            } else {
//                aircraftAirlineAlteration.setEndDate(DateUtils.String2Date(
//                        value, "yyyyMMdd"));
//            }
            boolean isValidDate=false;
            for (String datePattern : datePatterns) {
                if (DateUtils.isValidDate(value, datePattern)) {
                    aircraftAirlineAlteration.setEndDate(DateUtils.String2Date(value, datePattern));
                    isValidDate=true;
                }
            }
            if (!isValidDate) {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列[" + columnName + "]不符合格式;";
                verifyDescription += " ";
            }
        }
        
        columnName="宽/窄体";
        value = rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
        } else {
            if(value.equals("普通")){
                aircraftAirlineAlteration.setIsWideOrNarrow("P");
            }else if(value.equals("宽体")){
                aircraftAirlineAlteration.setIsWideOrNarrow("L");
            }
            
        }
        
        columnName="飞机属性";
        value = rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
        } else {
            aircraftAirlineAlteration.setAircraftDescription(value);
        }
        
        columnName="备注";
        value = rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
//            errorCells.add(columnIndex.get(columnName));
//            verifyDescription += "列[" + columnName + "]不能为空;";
        } else {
        	aircraftAirlineAlteration.setRemark(value);
        }
        
        
        //查找是否重复
        if(verifyDescription.equals("")){
            filters.clear();
            filters.put("aircraftRegistration_equal", aircraftAirlineAlteration.getAircraftRegistration());
            filters.put("startDate_lessThanOrEqualTo",aircraftAirlineAlteration.getEndDate());
            filters.put("endDate_greaterThanOrEqualTo",aircraftAirlineAlteration.getStartDate());
            List<AircraftAirlineAlteration> AircraftAirlineAlterationsInList = aaaDataHandler.findBy(aircraftAirlineAlterationAllList, filters);
            if(AircraftAirlineAlterationsInList.size()>0){
                verifyDescription +="开始、结束日期存在冲突；";
                errorCells.add(columnIndex.get("开始日期"));
                errorCells.add(columnIndex.get("结束日期"));
            }
        }
        if (!verifyDescription.equals("")) {
            verifyDescription = AircraftAirlineAlterationMessages
                    .getString("SEQUENCE") + (rowNumber + 1) + AircraftAirlineAlterationMessages.getString("ROW") + verifyDescription; //$NON-NLS-2$
            aircraftAirlineAlteration.setVerifyDescription(verifyDescription);
        }

        errorMessage.put(rowNumber, errorCells);
        aircraftAirlineAlteration.setErrorMessage(errorMessage);
        return aircraftAirlineAlteration;

    }
   
}

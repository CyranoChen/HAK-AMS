package com.nlia.fqdb.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightData;
import com.nlia.fqdb.entity.FlightResource;
import com.nlia.fqdb.message.FlightBaseMessages;

public class ValidateFlightBaseFromExcel {

    /**
     * 校验单行记录数据，返回实体对象[返回的实体中带有校验信息]
     * @param rowValue
     * @return
     */
    public static FlightBase validateSingleData(Map<String, String> rowValue,int rowNumber) {
	
	FlightBase flightBase = new FlightBase();
	String verifyDescription = "";  
	Map<Integer, ArrayList<Integer>> errorMessage = new HashMap<Integer, ArrayList<Integer>>();
	ArrayList<Integer> errorCells = new ArrayList<>();
	
	//验证字段是否符合规则
	//validateEntityProperty(rowValue,verifyDescription,errorCells);
	
	//判断日期是否为空
    	if(rowValue.get(FlightBaseMessages.getString("DATE")) == null  
    	||rowValue.get(FlightBaseMessages.getString("DATE")).isEmpty()){ 
    		errorCells.add(0);
    		verifyDescription += FlightBaseMessages.getString("DATE_CANT_BE_NULL"); 
    		verifyDescription += " "; 
    	}
    	if(!DateUtils.isValidDate(rowValue.get(FlightBaseMessages.getString("DATE")),"yyyy-MM-dd")){
    	    	errorCells.add(0);
		verifyDescription += "[日期]不符合规则;"; 
		verifyDescription += " ";
    	}
    
    		
    	//判断航班号全称是否为空
    	if(rowValue.get(FlightBaseMessages.getString("FLIGHT_NUMBER")) == null  
    	||rowValue.get(FlightBaseMessages.getString("FLIGHT_NUMBER")).isEmpty()){ 
    		errorCells.add(3);
    		verifyDescription +="[航班号]不能为空;"; 
    		verifyDescription += " "; 
    	}
    		
    	//判断进离港类型是否为空	
    	if(rowValue.get(FlightBaseMessages.getString("DIRECTION")) == null  
    	    	||rowValue.get(FlightBaseMessages.getString("DIRECTION")).isEmpty()){ 
    	    		errorCells.add(1);
    	    		verifyDescription +="[进离]不能为空;"; 
    	    		verifyDescription += " "; 
    	    	}	
    	//判断计划起飞时间是否符合规则
    	if(!DateUtils.isValidDate(rowValue.get(FlightBaseMessages.getString("PLAN_DEPARTURE_TIME")),"yyyy-MM-dd HH:mm")){
	    	errorCells.add(11);
		verifyDescription += "[计划起飞时间]不符合规则;"; 
		verifyDescription += " ";
	}	
    	//判断计划落地时间是否符合规则
    	if(!DateUtils.isValidDate(rowValue.get(FlightBaseMessages.getString("PLAN_LAND_TIME")),"yyyy-MM-dd HH:mm")){
	    	errorCells.add(12);
		verifyDescription += "[计划落地时间]不符合规则;"; 
		verifyDescription += " ";
	}
    	//判断预计起飞时间是否符合规则　ESTIMATE_DEPARTURE_TIME
    	if(!DateUtils.isValidDate(rowValue.get(FlightBaseMessages.getString("ESTIMATE_DEPARTURE_TIME")),"yyyy-MM-dd HH:mm")){
	    	errorCells.add(13);
		verifyDescription += "[预计起飞时间]不符合规则;"; 
		verifyDescription += " ";
	}
    	//判断预计落地时间是否符合规则　ESTIMATE_LAND_TIME
    	if(!DateUtils.isValidDate(rowValue.get(FlightBaseMessages.getString("ESTIMATE_LAND_TIME")),"yyyy-MM-dd HH:mm")){
	    	errorCells.add(14);
		verifyDescription += "[预计落地时间]不符合规则;"; 
		verifyDescription += " ";
	}
    	//判断实际起飞时间是否符合规则　ACTUAL_DEPARTURE_TIME
    	if(!DateUtils.isValidDate(rowValue.get(FlightBaseMessages.getString("ACTUAL_DEPARTURE_TIME")),"yyyy-MM-dd HH:mm")){
	    	errorCells.add(15);
		verifyDescription += "[实际起飞时间]不符合规则;"; 
		verifyDescription += " ";
	}
    	//判断实际落地时间是否符合规则　ACTUAL_LAND_TIME
    	if(!DateUtils.isValidDate(rowValue.get(FlightBaseMessages.getString("ACTUAL_LAND_TIME")),"yyyy-MM-dd HH:mm")){
	    	errorCells.add(16);
		verifyDescription += "[实际落地时间]不符合规则;"; 
		verifyDescription += " ";
	}
	//验证结束
	
	if(!verifyDescription.equals("")){   
		verifyDescription = FlightBaseMessages.getString("SEQUENCE") + (rowNumber + 1) +  FlightBaseMessages.getString("ROW") + verifyDescription;  //$NON-NLS-2$
	}
	
	
	errorMessage.put(rowNumber, errorCells);
	flightBase.setErrorMessage(errorMessage);
	// 设置业务信息
	FlightData flightData=new FlightData();
	FlightResource flightResource=new FlightResource();
	//转换日期
	String flightScheduledDate=rowValue.get(FlightBaseMessages.getString("DATE")); 
	flightBase.setFlightScheduledDate(DateUtils.String2Date(flightScheduledDate, "yyyy-MM-dd")); 
	
	//把进离港转换为0，1
	String dirctoin= rowValue.get(FlightBaseMessages.getString("DIRECTION"));
	flightBase.setFlightDirection("进港".equals(dirctoin)?"0":"1"); 
	flightBase.setAirlineIATACode(rowValue.get(FlightBaseMessages.getString("AIRLINE_COMPANY"))); 
	flightBase.setFlightIdentity(rowValue.get(FlightBaseMessages.getString("FLIGHT_NUMBER"))); 
	flightData.setFlightCategory(rowValue.get(FlightBaseMessages.getString("CATEGORY"))); 
	flightData.setRegisteration(rowValue.get(FlightBaseMessages.getString("AIRCRAFT_NUMBER"))); 
	flightData.setAircraftIATACode(rowValue.get(FlightBaseMessages.getString("AIRCRAFT_TYPE"))); 
	flightResource.setStandID(rowValue.get(FlightBaseMessages.getString("STAND"))); 
	flightResource.setRunwayID(rowValue.get(FlightBaseMessages.getString("RUNWAY"))); 
	flightResource.setGateID(rowValue.get(FlightBaseMessages.getString("GATE"))); 
	flightData.setIATARouteAirport(rowValue.get(FlightBaseMessages.getString("AIRLINE"))); 
	
	String scheduledTakeOffDateTime=rowValue.get(FlightBaseMessages.getString("PLAN_DEPARTURE_TIME"));  
	String scheduledLandingDateTime=rowValue.get(FlightBaseMessages.getString("PLAN_LAND_TIME"));  
	String estimatedTakeOffDateTime=rowValue.get(FlightBaseMessages.getString("ESTIMATE_DEPARTURE_TIME"));  
	String estimatedLandingDateTime=rowValue.get(FlightBaseMessages.getString("ESTIMATE_LAND_TIME"));  
	String actualTakeOffDateTime=rowValue.get(FlightBaseMessages.getString("ACTUAL_DEPARTURE_TIME"));  
	String actualLandingDateTime=rowValue.get(FlightBaseMessages.getString("ACTUAL_LAND_TIME")); 
	
	flightData.setScheduledTakeOffDateTime(DateUtils.String2Date(scheduledTakeOffDateTime, "yyyy-MM-dd HH:mm")); 
	flightData.setScheduledLandingDateTime(DateUtils.String2Date(scheduledLandingDateTime, "yyyy-MM-dd HH:mm")); 
	flightData.setEstimatedTakeOffDateTime(DateUtils.String2Date(estimatedTakeOffDateTime, "yyyy-MM-dd HH:mm")); 
	flightData.setEstimatedLandingDateTime(DateUtils.String2Date(estimatedLandingDateTime, "yyyy-MM-dd HH:mm")); 
	flightData.setActualTakeOffDateTime(DateUtils.String2Date(actualTakeOffDateTime, "yyyy-MM-dd HH:mm")); 
	flightData.setActualLandingDateTime(DateUtils.String2Date(actualLandingDateTime, "yyyy-MM-dd HH:mm")); 
	flightData.setFlightStatus(rowValue.get(FlightBaseMessages.getString("FLIGHT_STATUS"))); 
	flightData.setOperationStatus(rowValue.get(FlightBaseMessages.getString("OPERATION_STATUS"))); 
	flightData.setFlightCountryType(rowValue.get(FlightBaseMessages.getString("COUNTRY_TYPE"))); 
	flightData.setIsTransitFlight(rowValue.get(FlightBaseMessages.getString("IS_TRANSIT_FLIGHT"))); 
	flightData.setIsOverNightFlight(rowValue.get(FlightBaseMessages.getString("IS_OVERNIGHT_FLIGHT"))); 
	flightBase.setFlightData(flightData);
	flightBase.setFlightResource(flightResource);
	flightBase.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());  
	
	flightBase.setVerifyDescription(verifyDescription);
	return flightBase;
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
	switch (index) {
	case 0:
	    if (!FlightBaseMessages.getString("DATE").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 1:
	    if (!FlightBaseMessages.getString("DIRECTION").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 2:
	    if (!FlightBaseMessages.getString("AIRLINE_COMPANY").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 3:
	    if (!FlightBaseMessages.getString("FLIGHT_NUMBER").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 4:
	    if (!FlightBaseMessages.getString("CATEGORY").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 5:
	    if (!FlightBaseMessages.getString("AIRCRAFT_NUMBER").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 6:
	    if (!FlightBaseMessages.getString("AIRCRAFT_TYPE").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 7:
	    if (!FlightBaseMessages.getString("STAND").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 8:
	    if (!FlightBaseMessages.getString("RUNWAY").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 9:
	    if (!FlightBaseMessages.getString("GATE").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 10:
	    if (!FlightBaseMessages.getString("AIRLINE").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 11:
	    if (!FlightBaseMessages.getString("PLAN_DEPARTURE_TIME").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 12:
	    if (!FlightBaseMessages.getString("PLAN_LAND_TIME").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 13:
	    if (!FlightBaseMessages.getString("ESTIMATE_DEPARTURE_TIME").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 14:
	    if (!FlightBaseMessages.getString("ESTIMATE_LAND_TIME").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 15:
	    if (!FlightBaseMessages.getString("ACTUAL_DEPARTURE_TIME").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 16:
	    if (!FlightBaseMessages.getString("ACTUAL_LAND_TIME").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 17:
	    if (!FlightBaseMessages.getString("FLIGHT_STATUS").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 18:
	    if (!FlightBaseMessages.getString("OPERATION_STATUS").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 19:
	    if (!FlightBaseMessages.getString("COUNTRY_TYPE").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 20:
	    if (!FlightBaseMessages.getString("IS_TRANSIT_FLIGHT").equals(name)) 
		isCorrectColumnName = false;
	    break;
	case 21:
	    if (!FlightBaseMessages.getString("IS_OVERNIGHT_FLIGHT").equals(name)) 
		isCorrectColumnName = false;
	    break;

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
            put(    0   ,   "日期[A]");
            put(    1   ,   "日期[D]");
            put(    2   ,   "航班号[A]");
            put(    3   ,   "航班号[D]");
            put(    4   ,   "本站成人");
            put(    5   ,   "本站儿童");
            put(    6   ,   "本站婴儿");
            put(    7   ,   "本站行李");
            put(    8   ,   "本站邮件");
            put(    9   ,   "本站货物");
            put(    10  ,   "过站成人");
            put(    11  ,   "过站儿童");
            put(    12  ,   "过站婴儿");
            put(    13  ,   "到达成人");
            put(    14  ,   "到达儿童");
            put(    15  ,   "到达婴儿");
            put(    16  ,   "到达行李");
            put(    17  ,   "到达邮件");
            put(    18  ,   "到达货物");

        }
    };
    public static boolean verifyLoadColumnName(int index, String name) {
        boolean isCorrectColumnName = true;
        if (!LoadColumnName.get(index).equals(name)) {// 列名不一致
            isCorrectColumnName=false;
        }
        return isCorrectColumnName;
    }
    
    /**
     * 校验单行记录数据，返回实体对象[返回的实体中带有校验信息]
     * @param rowValue
     * @return
     */
    public static List<FlightBase> validateLoadSingleData(Map<Integer, String> rowValue, int rowNumber) {

        List<FlightBase> listFlightBase = new ArrayList<FlightBase>();
        FlightBase aflightBase = new FlightBase();
        FlightBase dflightBase = new FlightBase();
        aflightBase.setFlightDirection("0");
        dflightBase.setFlightDirection("1");
        FlightData aflightData = new FlightData();
        FlightData dflightData = new FlightData();
        String averifyDescription = "";
        String dverifyDescription = "";
        Map<Integer, ArrayList<Integer>> aerrorMessage = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> aerrorCells = new ArrayList<>();
        Map<Integer, ArrayList<Integer>> derrorMessage = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> derrorCells = new ArrayList<>();
        
        Boolean isAflightnull=false;
        Boolean isDflightnull=false;
        if ((rowValue.get(0) == null || rowValue.get(0).isEmpty()) && (rowValue.get(2) == null || rowValue.get(2).isEmpty())) {
            isAflightnull=true;
        }
        if ((rowValue.get(1) == null || rowValue.get(1).isEmpty()) && (rowValue.get(3) == null || rowValue.get(3).isEmpty())) {
            isDflightnull=true;
        }
        if(isAflightnull && isDflightnull){
            averifyDescription += "进港航班不存在";
            averifyDescription += " ";
            dverifyDescription += "离港航班不存在";
            dverifyDescription += " ";
        }
        if(!isAflightnull){
            //日期
            if (!DateUtils.isValidDate(rowValue.get(0), "yyyy-MM-dd")) {
                aerrorCells.add(0);
                averifyDescription += "列\"日期[A]\"不符合规则;";
                averifyDescription += " ";
            }else{
                aflightBase.setFlightScheduledDate(DateUtils.String2Date(rowValue.get(0), "yyyy-MM-dd"));
            }
            //航班号
            if(rowValue.get(2) == null || rowValue.get(2).isEmpty()){
                aerrorCells.add(2);
                averifyDescription += "列\"航班号[A]\"不能为空;";
                averifyDescription += " "; 
            }
            else{    
                aflightBase.setFlightIdentity(rowValue.get(2));
            }
            //过站成人 10
            if(rowValue.get(10) != null &&  !rowValue.get(10).isEmpty() ){
                if(isNumeric(rowValue.get(10))){
                    aflightData.setTransitAdultPassgerNum(Integer.parseInt(rowValue.get(10)));
                }else{
                    aerrorCells.add(10);
                    averifyDescription += "列\"" + LoadColumnName.get(10)+"\"必须是数字;";
                    averifyDescription += " "; 
                }
            }
            //过站儿童 11
            if(rowValue.get(11) != null &&  !rowValue.get(11).isEmpty() ){
                if(isNumeric(rowValue.get(11))){
                    aflightData.setTransitChildPassgerNum(Integer.parseInt(rowValue.get(11)));
                }else{
                    aerrorCells.add(11);
                    averifyDescription += "列\"" + LoadColumnName.get(11)+"\"必须是数字;";
                    averifyDescription += " "; 
                }
            }
            //过站婴儿 12
            if(rowValue.get(12) != null &&  !rowValue.get(12).isEmpty() ){
                if(isNumeric(rowValue.get(12))){
                    aflightData.setTransitInfantPassgerNum(Integer.parseInt(rowValue.get(12)));
                }else{
                    aerrorCells.add(12);
                    averifyDescription += "列\"" + LoadColumnName.get(12)+"\"必须是数字;";
                    averifyDescription += " "; 
                }
            }
            //到达成人 13
            if(rowValue.get(13) != null &&  !rowValue.get(13).isEmpty() ){
                if(isNumeric(rowValue.get(13))){
                    aflightData.setTotalAdultPassengersNumber(Integer.parseInt(rowValue.get(13)));
                }else{
                    aerrorCells.add(13);
                    averifyDescription += "列\"" + LoadColumnName.get(13)+"\"必须是数字;";
                    averifyDescription += " "; 
                }
            }
            //到达儿童 14
            if(rowValue.get(14) != null &&  !rowValue.get(14).isEmpty() ){
                if(isNumeric(rowValue.get(14))){
                    aflightData.setTotalChildrenNumber(Integer.parseInt(rowValue.get(14)));
                }else{
                    aerrorCells.add(14);
                    averifyDescription += "列\"" + LoadColumnName.get(14)+"\"必须是数字;";
                    averifyDescription += " "; 
                }
            }
            //到达婴儿 15
            if(rowValue.get(15) != null &&  !rowValue.get(15).isEmpty() ){
                if(isNumeric(rowValue.get(15))){
                    aflightData.setTotalInfantPassengersNumber(Integer.parseInt(rowValue.get(15)));
                }else{
                    aerrorCells.add(15);
                    averifyDescription += "列\"" + LoadColumnName.get(15)+"\"必须是数字;";
                    averifyDescription += " "; 
                }
            }
            //到达行李 16
            if(rowValue.get(16) != null &&  !rowValue.get(16).isEmpty() ){
                if(isNumeric(rowValue.get(16))){
                    aflightData.setTotallBaggageWeight(Integer.parseInt(rowValue.get(16)));
                }else{
                    aerrorCells.add(16);
                    averifyDescription += "列\"" + LoadColumnName.get(16)+"\"必须是数字;";
                    averifyDescription += " "; 
                }
            }
            //到达邮件 17
            if(rowValue.get(17) != null &&  !rowValue.get(17).isEmpty() ){
                if(isNumeric(rowValue.get(17))){
                    aflightData.setTotallMailWeight(Integer.parseInt(rowValue.get(17)));
                }else{
                    aerrorCells.add(17);
                    averifyDescription += "列\"" + LoadColumnName.get(17)+"\"必须是数字;";
                    averifyDescription += " "; 
                }
            }
            //到达货物 18
            if(rowValue.get(18) != null &&  !rowValue.get(18).isEmpty() ){
                if(isNumeric(rowValue.get(18))){
                    aflightData.setTotalCargoWeight(Integer.parseInt(rowValue.get(18)));
                }else{
                    aerrorCells.add(18);
                    averifyDescription += "列\"" + LoadColumnName.get(18)+"\"必须是数字;";
                    averifyDescription += " "; 
                }
            }
            aflightBase.setFlightData(aflightData);
        }
        if(!isDflightnull){
            //日期
            if (!DateUtils.isValidDate(rowValue.get(1), "yyyy-MM-dd")) {
                derrorCells.add(1);
                dverifyDescription += "列\"日期[D]\"不符合规则;";
                dverifyDescription += " ";
            }else{
                dflightBase.setFlightScheduledDate(DateUtils.String2Date(rowValue.get(1), "yyyy-MM-dd"));
            }
            //航班号
            if(rowValue.get(3) == null || rowValue.get(3).isEmpty()){
                derrorCells.add(3);
                dverifyDescription += "列\"航班号[D]\"不能为空;";
                dverifyDescription += " "; 
            }
            else{    
                dflightBase.setFlightIdentity(rowValue.get(3));
            }
            //本站成人4
            if(rowValue.get(4) != null &&  !rowValue.get(4).isEmpty() ){
                if(isNumeric(rowValue.get(4))){
                    dflightData.setTotalAdultPassengersNumber(Integer.parseInt(rowValue.get(4)));
                }else{
                    derrorCells.add(4);
                    dverifyDescription += "列\"" + LoadColumnName.get(4)+"\"必须是数字;";
                    dverifyDescription += " "; 
                }
            }
            //本站儿童 5
            if(rowValue.get(5) != null &&  !rowValue.get(5).isEmpty() ){
                if(isNumeric(rowValue.get(5))){
                    dflightData.setTotalChildrenNumber(Integer.parseInt(rowValue.get(5)));
                }else{
                    derrorCells.add(5);
                    dverifyDescription += "列\"" + LoadColumnName.get(5)+"\"必须是数字;";
                    dverifyDescription += " "; 
                }
            }
            //本站婴儿 6
            if(rowValue.get(6) != null &&  !rowValue.get(6).isEmpty() ){
                if(isNumeric(rowValue.get(6))){
                    dflightData.setTotalInfantPassengersNumber(Integer.parseInt(rowValue.get(6)));
                }else{
                    derrorCells.add(6);
                    dverifyDescription += "列\"" + LoadColumnName.get(6)+"\"必须是数字;";
                    dverifyDescription += " "; 
                }
            }
            //本站行李 7
            if(rowValue.get(7) != null &&  !rowValue.get(7).isEmpty() ){
                if(isNumeric(rowValue.get(7))){
                    dflightData.setTotallBaggageWeight(Integer.parseInt(rowValue.get(7)));
                }else{
                    derrorCells.add(7);
                    dverifyDescription += "列\"" + LoadColumnName.get(7)+"\"必须是数字;";
                    dverifyDescription += " "; 
                }
            }
            //本站邮件 8
            if(rowValue.get(8) != null &&  !rowValue.get(8).isEmpty() ){
                if(isNumeric(rowValue.get(8))){
                    dflightData.setTotallMailWeight(Integer.parseInt(rowValue.get(8)));
                }else{
                    derrorCells.add(8);
                    dverifyDescription += "列\"" + LoadColumnName.get(8)+"\"必须是数字;";
                    dverifyDescription += " "; 
                }
            }
            //本站货物 9
            if(rowValue.get(9) != null &&  !rowValue.get(9).isEmpty() ){
                if(isNumeric(rowValue.get(9))){
                    dflightData.setTotalCargoWeight(Integer.parseInt(rowValue.get(9)));
                }else{
                    derrorCells.add(9);
                    dverifyDescription += "列\"" + LoadColumnName.get(9)+"\"必须是数字;";
                    dverifyDescription += " "; 
                }
            }
            //过站成人 10
            if(rowValue.get(10) != null &&  !rowValue.get(10).isEmpty() ){
                if(isNumeric(rowValue.get(10))){
                    dflightData.setTransitAdultPassgerNum(Integer.parseInt(rowValue.get(10)));
                }else{
                    derrorCells.add(10);
                    dverifyDescription += "列\"" + LoadColumnName.get(10)+"必须是数字;";
                    dverifyDescription += " "; 
                }
            }
            //过站儿童 11
            if(rowValue.get(11) != null &&  !rowValue.get(11).isEmpty() ){
                if(isNumeric(rowValue.get(11))){
                    dflightData.setTransitChildPassgerNum(Integer.parseInt(rowValue.get(11)));
                }else{
                    derrorCells.add(11);
                    dverifyDescription += "列\"" + LoadColumnName.get(11)+"\"必须是数字;";
                    dverifyDescription += " "; 
                }
            }
            //过站婴儿 12
            if(rowValue.get(12) != null &&  !rowValue.get(12).isEmpty() ){
                if(isNumeric(rowValue.get(12))){
                    dflightData.setTransitInfantPassgerNum(Integer.parseInt(rowValue.get(12)));
                }else{
                    derrorCells.add(12);
                    dverifyDescription += "列\"" + LoadColumnName.get(12)+"\"必须是数字;";
                    dverifyDescription += " "; 
                }
            }
            
            dflightBase.setFlightData(dflightData);
        }
        
        // 验证结束

        if (!averifyDescription.equals("")) {
            averifyDescription = FlightBaseMessages.getString("SEQUENCE") + (rowNumber + 1) + FlightBaseMessages.getString("ROW") + averifyDescription; //$NON-NLS-2$
        }
        if (!dverifyDescription.equals("")) {
            dverifyDescription = FlightBaseMessages.getString("SEQUENCE") + (rowNumber + 1) + FlightBaseMessages.getString("ROW") + dverifyDescription; //$NON-NLS-2$
        }

        aerrorMessage.put(rowNumber, aerrorCells);
        derrorMessage.put(rowNumber, derrorCells);
        aflightBase.setErrorMessage(aerrorMessage);
        dflightBase.setErrorMessage(derrorMessage);
       

        aflightBase.setVerifyDescription(averifyDescription);
        dflightBase.setVerifyDescription(dverifyDescription);
        listFlightBase.add(aflightBase);
        listFlightBase.add(dflightBase);
        return listFlightBase;
    }
    
    public static boolean isNumeric(String str){ 
        for (int i = str.length();--i>=0;){   
            if (!Character.isDigit(str.charAt(i))){
             return false;
            }
           }
           return true;
     } 
  //add by march 20140826 end 导入配载信息
}

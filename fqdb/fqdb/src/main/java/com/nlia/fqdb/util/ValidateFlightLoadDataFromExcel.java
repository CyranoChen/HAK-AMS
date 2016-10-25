package com.nlia.fqdb.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.nlia.fqdb.configuration.DataHandler;
import com.nlia.fqdb.entity.AircraftAirlineAlteration;
import com.nlia.fqdb.entity.FlightLoadData;
import com.nlia.fqdb.entity.FlightData;
import com.nlia.fqdb.entity.FlightResource;
import com.nlia.fqdb.entity.base.Airline;
import com.nlia.fqdb.message.FlightBaseMessages;

@Component
public class ValidateFlightLoadDataFromExcel {

    private List<FlightLoadData> flightLoadDataList ;
    
    public List<FlightLoadData> getFlightLoadDataList() {
        return flightLoadDataList;
    }
    public  void setFlightLoadDataList(List<FlightLoadData> flightLoadDataList) {
        this.flightLoadDataList = flightLoadDataList;
    }
    /**
     * excle必须的字段
     **/
    private static String[] flightLoadDataExcleNeedColumnName = {"date","cyr","hbh","hx","hxfl","hd","hdfl","jx","jh","hbxz","kgyz","kgzw","io","jcn","qjsj","cr","et","ye","crwh","etwh","yewh","xl","hw","yj","xljs","xg","bc","wjhz"};
    
    public static String columnsContansFlightLoadDataExcleNeedColumnName(List<String> columns){
        String ret="";
        for (String strColumnName : flightLoadDataExcleNeedColumnName) {
            if(!columns.contains(strColumnName)){
                ret +="必须包含字段[" + strColumnName +"]\r\n" ;
            }
        }
        return ret;
    }

    /**
     * 校验单行记录数据，返回实体对象[返回的实体中带有校验信息]
     * 
     * @param rowValue
     * @return
     */
    
    public FlightLoadData validateFlightLoadDataSingleData(
            Map<String, String> rowValue,Map<String, Integer> columnIndex, int rowNumber,List<FlightLoadData> flightLoadDataAllList) {
        
        DataHandler<FlightLoadData> dataHandler = new DataHandler<>();
        Map<String, Object> filters = new HashMap<>();
      
        FlightLoadData flightLoadData= new FlightLoadData();
        flightLoadData.setGenerateMethod("2");
        flightLoadData.setRowNumber(rowNumber);
        
        String verifyDescription = "";
        Map<Integer, ArrayList<Integer>> errorMessage = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> errorCells = new ArrayList<>();
        String columnName="";
        String value="";

        
        columnName = "date";
        value = rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
        } else {
            String[] datePatterns = {"yyyyMMdd","yyyy-MM-dd","yyyy/MM/dd","MM/dd/yy"};
            boolean isValidDate=false;
            for (String datePattern : datePatterns) {
                if (DateUtils.isValidDate(value, datePattern)) {
                    flightLoadData.setJhrq(DateUtils.String2Date(value, datePattern));
                    isValidDate=true;
                    break;
                }
            }
            if (!isValidDate) {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列[" + columnName + "]不符合格式;";
                verifyDescription += " ";
            }
        }

        columnName = "cyr";
        value = rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
        } else {
            flightLoadData.setCyr(value);
        }

        columnName = "hbh";
        value = rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
        } else {
            flightLoadData.setHbh(value);
        }        
        
        columnName = "hd";
        value = rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
        } else {
            flightLoadData.setHd(value);
        }  
        
        columnName = "io";
        value = rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
        } else {
            if (!value.equals("I") && !value.equals("O")) {
                // errorCells.add(col);
                verifyDescription += "列[" + columnName + "]不符合规则;";
                verifyDescription += " ";
            } else {
                flightLoadData.setIo(value);
            }
        }
        
        columnName="qjsj";      
        value=rowValue.get(columnName);    
        if (value != null) {
	        if (!DateUtils.isValidDate(value, "HH:mm")) {
	            errorCells.add(columnIndex.get(columnName));
	            verifyDescription += "列[" + columnName + "]不符合规则;";
	            verifyDescription += " ";
	        } else {
	            flightLoadData.setQjsj(value);
	        }
        }
        //lushuaifeng 20160920
        /*
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
      } else {
          if (!DateUtils.isValidDate(value, "HH:mm")) {
              errorCells.add(columnIndex.get(columnName));
              verifyDescription += "列[" + columnName + "]不符合规则;";
              verifyDescription += " ";
          } else {
              flightLoadData.setQjsj(value);
          }
      }
      */
      
//        if (verifyDescription.equals("")) {
//            filters.clear();
//            filters.put("sjrq_greaterThanOrEqualTo",
//                    DateUtils.getZeroOfOneday(flightLoadData.getSjrq()));
//            filters.put("sjrq_lessThanOrEqualTo",
//                    DateUtils.getLastMinuteOfOneday(flightLoadData.getSjrq()));
//            filters.put("cyr_equal", flightLoadData.getCyr());
//            filters.put("hbh_equal", flightLoadData.getHbh());
//            filters.put("hd_equal", flightLoadData.getHd());
//            filters.put("io_equal", flightLoadData.getIo());
//            filters.put("qjsj_equal", flightLoadData.getQjsj());
//
//            List<FlightLoadData> flightLoadDatas = dataHandler.findBy(
//                    flightLoadDataList, filters);
//            if (flightLoadDatas.size() == 1) {
//                flightLoadData = flightLoadDatas.get(0);
//            } else {
//                errorCells.add(columnIndex.get("date"));
//                errorCells.add(columnIndex.get("cyr"));
//                errorCells.add(columnIndex.get("hbh"));
//                errorCells.add(columnIndex.get("hd"));
//                errorCells.add(columnIndex.get("io"));
//                errorCells.add(columnIndex.get("qjsj")); 
//                verifyDescription += flightLoadData.getSjrq2s() + "|"
//                        + flightLoadData.getQjsj() + "|"
//                        + flightLoadData.getCyr() + flightLoadData.getHbh()
//                        + "|" + flightLoadData.getHd() + "|"
//                        + flightLoadData.getIo() + "对应配载记录数为" +flightLoadDatas.size() + ";";
//                verifyDescription += " ";
//            }
//        }
        columnName="hx";        value=rowValue.get(columnName); if (value != null) {            flightLoadData.setHx(value);        }     
        columnName="hx";
        value=rowValue.get(columnName);
        if (value != null) {
        	flightLoadData.setHx(value);        
        }
        columnName="hxfl";      value=rowValue.get(columnName); if (value != null) {            flightLoadData.setHxfl(value);        }     
        
        columnName="hdfl";      value=rowValue.get(columnName); if (value != null) {            flightLoadData.setHdfl(value);        }     
        columnName="jx";        value=rowValue.get(columnName); if (value != null) {            flightLoadData.setJx(value);        }     
        columnName="jh";        value=rowValue.get(columnName); if (value != null) {            flightLoadData.setJh(value);        }     
        columnName="hbxz";      value=rowValue.get(columnName); if (value != null) {            flightLoadData.setHbxz(value);        }     
        columnName="kgyz";      value=rowValue.get(columnName);
      if (value == null || value.isEmpty()) {
          errorCells.add(columnIndex.get(columnName));
          verifyDescription += "列[" + columnName + "]不能为空;";
          verifyDescription += " ";
    } else {
          if (isNumeric(value)) {
              flightLoadData.setKgyz(Long.parseLong(value));
          } else {
              errorCells.add(columnIndex.get(columnName));
              verifyDescription += "列[" + columnName + "]必须是数字;";
              verifyDescription += " ";
          }
      }

        columnName="kgzw";      value=rowValue.get(columnName); 
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
      } else {
            if (isNumeric(value)) {
                flightLoadData.setKgzw(Long.parseLong(value));
            } else {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列[" + columnName + "]必须是数字;";
                verifyDescription += " ";
            }
        }
       
        columnName="jcn";       value=rowValue.get(columnName); 
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
      } else {
            if (isNumeric(value)) {
                flightLoadData.setJcn(Long.parseLong(value));
            } else {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列[" + columnName + "]必须是数字;";
                verifyDescription += " ";
            }
        }
        columnName="cr";        value=rowValue.get(columnName); 
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
      } else {
            if (isNumeric(value)) {
                flightLoadData.setCr(Long.parseLong(value));
            } else {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列[" + columnName + "]必须是数字;";
                verifyDescription += " ";
            }
        }
        columnName="et";        value=rowValue.get(columnName); 
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
      } else {
            if (isNumeric(value)) {
                flightLoadData.setEt(Long.parseLong(value));
            } else {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列[" + columnName + "]必须是数字;";
                verifyDescription += " ";
            }
        }
        columnName="ye";        value=rowValue.get(columnName); 
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
      } else {
            if (isNumeric(value)) {
                flightLoadData.setYe(Long.parseLong(value));
            } else {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列[" + columnName + "]必须是数字;";
                verifyDescription += " ";
            }
        }
        columnName="crwh";      value=rowValue.get(columnName); 
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
      } else {
            if (isNumeric(value)) {
                flightLoadData.setCrwh(Long.parseLong(value));
            } else {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列[" + columnName + "]必须是数字;";
                verifyDescription += " ";
            }
        }
        columnName="etwh";      value=rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
      } else {
            if (isNumeric(value)) {
                flightLoadData.setEtwh(Long.parseLong(value));
            } else {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列[" + columnName + "]必须是数字;";
                verifyDescription += " ";
            }
        }
        columnName="yewh";      value=rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
      } else {
            if (isNumeric(value)) {
                flightLoadData.setYewh(Long.parseLong(value));
            } else {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列[" + columnName + "]必须是数字;";
                verifyDescription += " ";
            }
        }
        columnName="xl";        value=rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
      } else {
            if (isNumeric(value)) {
                flightLoadData.setXl(Long.parseLong(value));
            } else {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列[" + columnName + "]必须是数字;";
                verifyDescription += " ";
            }
        }
        columnName="hw";        value=rowValue.get(columnName); 
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
      } else {
            if (isNumeric(value)) {
                flightLoadData.setHw(Long.parseLong(value));
            } else {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列[" + columnName + "]必须是数字;";
                verifyDescription += " ";
            }
        }
        columnName="yj";        value=rowValue.get(columnName); 
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列[" + columnName + "]不能为空;";
            verifyDescription += " ";
      } else {
            if (isNumeric(value)) {
                flightLoadData.setYj(Long.parseLong(value));
            } else {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列[" + columnName + "]必须是数字;";
                verifyDescription += " ";
            }
        }
        columnName="xljs";      value=rowValue.get(columnName); 
//        if (value == null || value.isEmpty()) {
//            errorCells.add(columnIndex.get(columnName));
//            verifyDescription += "列[" + columnName + "]不能为空;";
//            verifyDescription += " ";
//      } else {
//            if (isNumeric(value)) {
//                flightLoadData.setXljs(Long.parseLong(value));
//            } else {
//                errorCells.add(columnIndex.get(columnName));
//                verifyDescription += "列[" + columnName + "]必须是数字;";
//                verifyDescription += " ";
//            }
//        }
        columnName="xg";        value=rowValue.get(columnName); if (value != null) {            flightLoadData.setXg(value);        }     
        columnName="bc";        value=rowValue.get(columnName); if (value != null) {            flightLoadData.setBc(value);        }     
        columnName="wjhz";      value=rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
//            errorCells.add(columnIndex.get(columnName));
//            verifyDescription += "列[" + columnName + "]不能为空;";
//            verifyDescription += " ";
      } else {
            if (isNumeric(value)) {
                flightLoadData.setWjhz(Long.parseLong(value));
            } else {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列[" + columnName + "]必须是数字;";
                verifyDescription += " ";
            }
        }
       
        if (!verifyDescription.equals("")) {
            verifyDescription = "第" + (rowNumber) +"行" + verifyDescription; //$NON-NLS-2$
        } else {
            // 查找是否重复
            filters.clear();
            filters.put("io_equal", flightLoadData.getIo());
            filters.put("cyr_equal", flightLoadData.getCyr());
            filters.put("hbh_equal", flightLoadData.getHbh());
            filters.put("jh_equal", flightLoadData.getJh());
            filters.put("jhrq_equal", flightLoadData.getSjrq());
           // filters.put("qjsj_equal", flightLoadData.getQjsj());
            filters.put("hd_equal", flightLoadData.getHd());
            List<FlightLoadData> fldInList = dataHandler.findBy(flightLoadDataAllList, filters);
            if (fldInList.size() > 0) {
                verifyDescription += "存在冲突；";
                errorCells.add(columnIndex.get("io"));
                errorCells.add(columnIndex.get("cyr"));
                errorCells.add(columnIndex.get("hbh"));
                errorCells.add(columnIndex.get("jh"));
                errorCells.add(columnIndex.get("date"));
//                errorCells.add(columnIndex.get("qjsj"));
                errorCells.add(columnIndex.get("hd"));
            }
        }
        flightLoadData.setVerifyDescription(verifyDescription);
        if (errorCells.size() > 0) {
            errorCells.add(columnIndex.size());
            errorMessage.put(rowNumber, errorCells);
            flightLoadData.setErrorMessage(errorMessage);
        }

        return flightLoadData;
    }
    
    
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0;) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    // add by march 20140826 end 导入配载信息
}

package com.nlia.fqdb.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.nlia.fqdb.configuration.DataHandler;
import com.nlia.fqdb.entity.FlightLoadData;
import com.nlia.fqdb.entity.FlightData;
import com.nlia.fqdb.entity.FlightMateInfo;
import com.nlia.fqdb.entity.FlightResource;
import com.nlia.fqdb.entity.base.Airline;
import com.nlia.fqdb.message.AircraftAirlineAlterationMessages;
import com.nlia.fqdb.message.FlightBaseMessages;
import com.nlia.fqdb.service.impl.AirlineManager;

@Component
public class ValidateFlightMateInfoFromExcel {
    @Resource
    private AirlineManager airlineManager;
    private List<Airline> airlineAllList ;
    public void getairlineAllList(){
        airlineAllList = airlineManager.findAllAirline();
    }
    /**
     * FlightMateInfo必须的字段
     **/
    private static List<String> flightMateInfoMastNeedColumnName = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;

        {
            //add("进港机型");
            add("进港航班号");
            add("进港航班性质");
            add("进港机号");
            //add("进港机位");
            add("国内货物重量");
            add("国际货物重量");
            add("空调车费系数");
            add("航空公司");
            add("国内邮件重量");
            add("国际邮件重量");
            add("气源车费系数");
            add("气源车使用时间");
            add("空调车使用时间");
            add("飞机最大业载");
            add("飞机座位数");
            add("飞机最大起飞全重");
            add("机组摆渡车使用次数");
            add("机组摆渡车费系数");
            add("旅客摆渡车使用次数");
            add("旅客摆渡车费系数");
            add("站坪（机坪）服务费系数");
            add("国内婴儿数量");
            add("国际婴儿数量");
            add("基本收费系数");
            add("货物邮件费系数");
            add("污水车使用次数");
            add("国内儿童数量");
            add("国际儿童数量");
            //add("创建时间");
            //add("创建人");
            //add("离港机型");
            add("离港航班号");
            add("离港航班性质");
            add("离港机号");
            //add("离港机位");
            add("除冰车费系数");
            add("除冰车使用时间");
            //add("延误原因编码");
            add("双廊桥费系数");
            add("电源车费系数");
            add("电源车使用时间");
            add("执行日");
            add("航班ID");
            //add("航班性质");
            add("国内国际收费标志位");
            add("国内国际查询标志位");
            add("进离港标识");
            //add("航段0");
            //add("航段1");
            //add("航段2");
            //add("航线");
            //add("航程");
            add("飞机放行费系数");
            add("垃圾车使用次数");
            add("ID");
            //add("夜航");
            //add("是否高峰");
            //add("过站");
            add("飞机宽窄体");
            add("起降费系数");
            add("停场时间");
            add("进港时间");
            add("引导车使用次数");
            add("引导车费系数");
            add("升降平台车费系数");
            add("升降平台车使用时间");
            add("连班ID");
            add("配对ID");
            add("国内行李重量");
            add("国际行李重量");
            //add("生成方式");
            //add("修改时间");
            //add("修改人");
            add("集装设备收费系数");
            add("停场费系数");
            add("旅客货物安检费费系数");
            add("旅客行李安检费费系数");
            add("旅客服务费系数");
            add("客梯车费系数");
            add("客桥数量");
            add("客桥使用时间");
            add("国内旅客数量");
            add("国际旅客数量");
            add("旅客和行李费系数");
            add("航后服务费系数");
            add("航前服务费系数");
            add("客梯车使用时间");
            //add("备注");
            add("例行检查费系数");
            add("例行检查时间");
            add("单廊桥费系数");
            add("扫雪车费系数");
            add("残疾人专用车使用次数");
            add("残疾人车费系数");
            //add("专机");
            add("配载通信费系数");
            add("勤务费系数");
            add("离港时间");
            add("牵引车费系数");
            add("牵引车使用次数");
            add("非例行检查费系数");
            add("非例行检查时间");
            add("过站旅客");
            add("过站婴儿");
            add("过站儿童");
            add("过站服务费系数");
            add("清水车使用次数");
            add("高密度");
            add("集装设备");
            add("始发站");
            add("目的站");
            add("飞机服务类型");

        }
    };
    public static String columnsContansFlightMateInfoMastNeedColumnName(List<String> columns){
        String ret="";
        for (String strColumnName : flightMateInfoMastNeedColumnName) {
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
    public FlightMateInfo validateFlightMateInfoSingleData(
            Map<String, String> rowValue,Map<String, Integer> columnIndex, int rowNumber) {
        
        DataHandler<Airline> dataHandler = new DataHandler<>();
        Map<String, Object> filters = new HashMap<>();
      
        FlightMateInfo flightMateInfo= new FlightMateInfo();
        String verifyDescription = "";
        Map<Integer, ArrayList<Integer>> errorMessage = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> errorCells = new ArrayList<>();
        String columnName="";
        String value="";
        
        
      //系数isDecimal                                                                        
        columnName="空调车费系数";                      value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setAirConditionTruckFeeCoe(value);                         } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="气源车费系数";                      value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setAirTruckFeeCoe(value);                                  } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="机组摆渡车费系数";                  value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setAirdromeUsedByCrewFeeCoe(value);                        } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="旅客摆渡车费系数";                  value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setAirdromeUsedByPasFeeCoe(value);                         } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="站坪（机坪）服务费系数";            value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setApronServiceFeeCoe(value);                              } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="基本收费系数";                      value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setBasicFeeCoe(value);                                     } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="货物邮件费系数";                    value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setCagroMailFeeCoe(value);                                 } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="除冰车费系数";                      value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setDeicingTruckFeeCoe(value);                              } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="双廊桥费系数";                      value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setDoubleAirBrageFeeCoe(value);                            } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="电源车费系数";                      value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setElectricTruckFeeCoe(value);                             } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="飞机放行费系数";                    value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setFlithtDispatchFeeCoe(value);                            } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="起降费系数";                        value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setLandAndTakeoffFeeCoe(value);                            } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="引导车费系数";                      value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setLeadcarFeeCoe(value);                                   } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="升降平台车费系数";                  value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setLiftingPlatformCarFeeCoe(value);                        } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="集装设备收费系数";                  value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setPackagingFacility(value);                               } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="停场费系数";                        value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setPartingFeeCoe(value);                                   } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="旅客货物安检费费系数";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setPasCargoSecurityCheckCoe(value);                        } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="旅客行李安检费费系数";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setPasSecurityCheckFeeCoe(value);                          } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="旅客服务费系数";                    value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setPasServiceFeeCoe (value);                               } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="客梯车费系数";                      value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setPassenageCarFeeCoe(value);                              } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="旅客和行李费系数";                  value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setPassengerLuggageFeeCoe(value);                          } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="航后服务费系数";                    value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setPostflightServiceFeeCoe(value);                         } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="航前服务费系数";                    value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setPreflightServiceFeeCoe(value);                          } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="例行检查费系数";                    value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setRoutineCheckFeeCoe(value);                              } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="单廊桥费系数";                      value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setSingleAirBrageFeeCoe(value);                            } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="扫雪车费系数";                      value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setSnowRemovalTruckFeeCoe(value);                          } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="残疾人车费系数";                    value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setSpeCarForDisabledFeeCoe(value);                         } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="配载通信费系数";                   value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setStowageCommunicationFeeCoe(value);                      } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="勤务费系数";                        value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setSundryDutiesFeeCoe(value);                              } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="牵引车费系数";                      value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setTractorFeeCoe(value);                                   } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="非例行检查费系数";                  value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setUnroutineCheckFeeCoe(value);                            } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
        columnName="过站服务费系数";                    value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setViaFlightServiceFeeCoe(value);                          } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; } 
//使用次数或时间isNumeric                                                              
        columnName="机组摆渡车使用次数";        value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setAirdromeUsedByCrewCount(value);                                       } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }                       
        columnName="旅客摆渡车使用次数";        value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setAirdromeUsedByPasCount(value);                                        } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="污水车使用次数";            value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setCesspoolageTruckUseCount(value);                                      } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="垃圾车使用次数";            value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setGarbageTruckUseCount(value);                                          } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="引导车使用次数";            value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setLeadCarUsedCount(value);                                              } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="残疾人专用车使用次数";      value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setSpeCarForDisabledCount(value);                                        } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="牵引车使用次数";            value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setTractorsUsedCount(value);                                             } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="清水车使用次数";            value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setWaterTruckUseCount(value);                                            } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }

        columnName="气源车使用时间";            value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setAirTruckTime(Double.parseDouble(value));                                  } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="空调车使用时间";            value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setAirconditioningTime(Double.parseDouble(value));                           } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="除冰车使用时间";            value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setDeicingVehicleTime(Double.parseDouble(value));                            } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="电源车使用时间";            value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setElectricTruckTime(Double.parseDouble(value));                             } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="升降平台车使用时间";        value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setLiftingPlatformCarTime(Double.parseDouble(value));                        } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="客桥使用时间";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setPassengerBridgeTime(Double.parseDouble(value));                           } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="客梯车使用时间";            value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setPsssengerCarUsedTime(Double.parseDouble(value));                          } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="例行检查时间";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setRoutineCheckTime(Double.parseDouble(value));                              } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="非例行检查时间";            value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setUnroutineCheckTime(Double.parseDouble(value));                            } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }

        columnName="飞机最大业载";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setAircraftPayload(Double.parseDouble(value));                               } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="飞机座位数";                value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setAircraftSeatCapacity(Double.parseDouble(value));                          } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="飞机最大起飞全重";          value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setAircraftTakeOffWeight(Double.parseDouble(value));                         } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="停场时间";                  value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setLandTime(Double.parseDouble(value));                                      } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="航班ID";                    value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isNumeric(value)) {        flightMateInfo.setFlightBaseId(Long.parseLong(value));                                      } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="ID";                        value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isNumeric(value)) {        flightMateInfo.setId(Long.parseLong(value));                                                } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="连班ID";                    value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setLinkFlightBaseId(Long.parseLong(value));                                  } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="配对ID";                    value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setLinkFlighMateInfoId(Long.parseLong(value));                               } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
//人员数量或重量isNumeric
        columnName="国内婴儿数量";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setBabyInternal(Double.parseDouble(value));                                  } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="国际婴儿数量";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setBabyInternational(Double.parseDouble(value));                             } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="国内儿童数量";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setChildInternal(Double.parseDouble(value));                                 } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="国际儿童数量";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setChildInternational(Double.parseDouble(value));                            } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="国内旅客数量";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setPassengerInternal(Double.parseDouble(value));                             } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="国际旅客数量";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setPassengerInternational(Double.parseDouble(value));                        } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="国内货物重量";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setAirCargoWeightInternal(Double.parseDouble(value));                        } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="国际货物重量";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setAirCargoWeightInternational(Double.parseDouble(value));                   } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="国内邮件重量";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setAirMailWeightInternal(Double.parseDouble(value));                         } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="国际邮件重量";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setAirMailWeightInternational(Double.parseDouble(value));                    } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="国内行李重量";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setLuggageWeightInternal(Double.parseDouble(value));                         } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="国际行李重量";              value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setLuggageWeightInternational(Double.parseDouble(value));                    } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="过站旅客";                  value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setViaAdult(Double.parseDouble(value));                                      } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="过站婴儿";                  value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setViaBaby(Double.parseDouble(value));                                       } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
        columnName="过站儿童";                  value=rowValue.get(columnName); if (value != null && !value.isEmpty() && isDecimal(value)) {        flightMateInfo.setViaChild(Double.parseDouble(value));                                      } else {  errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须是数字;";  verifyDescription += " "; }
//非空                                      
        columnName="航空公司"; value=rowValue.get(columnName);
        filters.clear();
        filters.put("airlineDescription_equal", value);
        List<Airline> airlines = dataHandler
                .findBy(airlineAllList, filters);
        if (airlines.size() == 1) {
            flightMateInfo.setAirline(airlines.get(0));
            flightMateInfo.setAirlineName(airlines.get(0).getAirlineDescription());
        } else {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列["+ columnName +"]对应航空公司("+value+")错误;";
            verifyDescription += " ";
        }
        columnName="进港航班号";         value=rowValue.get(columnName);  if (value == null || value.isEmpty()) { errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]不能为空;"; verifyDescription += " ";} else { flightMateInfo.setAFlightIdentity(value);}
        columnName="进港航班性质";       value=rowValue.get(columnName);  if (value == null || value.isEmpty()) { errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]不能为空;"; verifyDescription += " ";} else { flightMateInfo.setAFlightProperty(value);}
        columnName="进港机号";           value=rowValue.get(columnName);  if (value == null || value.isEmpty()) { errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]不能为空;"; verifyDescription += " ";} else { flightMateInfo.setAregisteration(value);}
        columnName="离港航班号";         value=rowValue.get(columnName);  if (value == null || value.isEmpty()) { errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]不能为空;"; verifyDescription += " ";} else { flightMateInfo.setDFlightIdentity(value);}
        columnName="离港航班性质";       value=rowValue.get(columnName);  if (value == null || value.isEmpty()) { errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]不能为空;"; verifyDescription += " ";} else { flightMateInfo.setDFlightProperty(value);}
        columnName="离港机号";           value=rowValue.get(columnName);  if (value == null || value.isEmpty()) { errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]不能为空;"; verifyDescription += " ";} else { flightMateInfo.setDregisteration(value);}

        columnName="始发站";           value=rowValue.get(columnName);  if (value == null || value.isEmpty()) { errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]不能为空;"; verifyDescription += " ";} else { flightMateInfo.setIataOriginAirport(value);}
        columnName="目的站";           value=rowValue.get(columnName);  if (value == null || value.isEmpty()) { errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]不能为空;"; verifyDescription += " ";} else { flightMateInfo.setIataDestinationAirport(value);}
        
        columnName="国内国际收费标志位"; value=rowValue.get(columnName);  if (value == null || value.isEmpty() || (!value.equals("0")&&!value.equals("1"))) { errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须为0或1;"; verifyDescription += " ";} else { flightMateInfo.setFlightCountryTypeCharge(value);}
        columnName="国内国际查询标志位"; value=rowValue.get(columnName);  if (value == null || value.isEmpty() || (!value.equals("0")&&!value.equals("1"))) { errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须为0或1;"; verifyDescription += " ";} else { flightMateInfo.setFlightCountryTypeQuery(value);}
        columnName="进离港标识";         value=rowValue.get(columnName);  if (value == null || value.isEmpty() || (!value.equals("A")&&!value.equals("D"))) { errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须为A或D;"; verifyDescription += " ";} else { if(value.equals("A")){value="0";}else if(value.equals("D")){value="1";} flightMateInfo.setFlightDirection(value);}
        columnName="飞机宽窄体";         value=rowValue.get(columnName);  if (value == null || value.isEmpty() || (!value.equals("B")&&!value.equals("P"))) { errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须为B或P;"; verifyDescription += " ";} else { if(value.equals("B")){value="0";}else if(value.equals("P")){value="1";} flightMateInfo.setIsWideOrNarrow(value);}
        columnName="高密度";             value=rowValue.get(columnName);  if (value == null || value.isEmpty() || (!value.equals("Y")&&!value.equals("N"))) { errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须为Y或N;"; verifyDescription += " ";} else { flightMateInfo.setIsHighDensity(value);}
        columnName="集装设备";           value=rowValue.get(columnName);  if (value == null || value.isEmpty() || (!value.equals("Y")&&!value.equals("N"))) { errorCells.add(columnIndex.get(columnName)); verifyDescription += "列["+ columnName +"]必须为Y或N;"; verifyDescription += " ";} else { flightMateInfo.setIsPackagingFacility(value);}
      
        columnName="离港时间";
        value=rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列["+ columnName +"]不能为空;";
            verifyDescription += " ";
        } else {
            if (!DateUtils.isValidDate(value,"yyyy-MM-dd HH:mm")) {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列["+ columnName +"]不符合格式yyyy-MM-dd HH:mm;";
                verifyDescription += " ";
            } else {
                flightMateInfo.setLandedTime(DateUtils.String2Date(value));
            }
        }
        columnName="进港时间";
        value=rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列["+ columnName +"]不能为空;";
            verifyDescription += " ";
        } else {
            if (!DateUtils.isValidDate(value,"yyyy-MM-dd HH:mm")) {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列["+ columnName +"]不符合格式yyyy-MM-dd HH:mm;";
                verifyDescription += " ";
            } else {
                flightMateInfo.setTakeOffTime(DateUtils.String2Date(value));
            }
        }
        columnName="执行日";
        value=rowValue.get(columnName);
        if (value == null || value.isEmpty()) {
            errorCells.add(columnIndex.get(columnName));
            verifyDescription += "列["+ columnName +"]不能为空;";
            verifyDescription += " ";
        } else {
            if (!DateUtils.isValidDate(value,"yyyy-MM-dd")) {
                errorCells.add(columnIndex.get(columnName));
                verifyDescription += "列["+ columnName +"]不符合格式yyyy-MM-dd;";
                verifyDescription += " ";
            } else {
                flightMateInfo.setExecuteTime(DateUtils.String2Date(value,"yyyy-MM-dd"));
            }
        }
        
      //可以为空
        columnName="飞机服务类型";
        value=rowValue.get(columnName);
        if (value == null || value.isEmpty() || (!value.equals("航前")&&!value.equals("航后")&&!value.equals("短停"))){
            errorCells.add(columnIndex.get(columnName)); 
            verifyDescription += "列["+ columnName +"]必须为航前、航前、短停"; 
            verifyDescription += " ";
        } else { 
            String aircraftService=null;
            if(value.equals("航前")){
                aircraftService="0";
            }else if(value.equals("航后")){
                aircraftService="1";
            }else if(value.equals("短停")){
                aircraftService="2";
            }
            flightMateInfo.setAircraftService(aircraftService);
        }
        columnName="进港机型";                             value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setAAircraftType(value);        } 
        columnName="进港机位";                             value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setAStandNum(value);        }       
        columnName="离港机型";                             value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setDAircraftType(value);        }       
        columnName="离港机位";                             value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setDStandNum(value);        }       
        columnName="航段0";                                value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setRemark(value);        }    
        columnName="航段1";                                value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setFlightPart0(value);        }    
        columnName="航段2";                                value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setFlightPart1(value);        }    
        columnName="航线";                                 value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setFlightPart2(value);        }   
        columnName="航程";                                 value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setFlightRoute(value);        }   
               
        columnName="备注";                                 value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setRemark(value);        }   
//        columnName="创建时间";                             value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setCreatTime(value);        }       
        columnName="创建人";                               value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setCreateUser(value);        }     
        columnName="延误原因编码";                         value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setDelayCode(value);        }           
//        columnName="修改时间";                             value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setModifyTime(value);        }       
        columnName="修改人";                               value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setModifyUser(value);        }     
        columnName="客桥数量";                             value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setPassengerBridgeNumber(value);        }       
        columnName="专机";                                 value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setSpecialPlane(value);        }   
        columnName="航班性质";                             value=rowValue.get(columnName); if (value != null) {            flightMateInfo.setFlightCountryType(value);        }
        columnName="生成方式";                             value=rowValue.get(columnName); if (value != null && (value.equals("自动")||value.equals("人工"))) {   if(value.equals("自动")){value="0";}else{value="1";}         flightMateInfo.setMatchMethod(value);        }
        columnName="夜航";                                 value=rowValue.get(columnName); if (value != null && (value.equals("Y")||value.equals("N"))) {            flightMateInfo.setFlightVoyage(value);        }   
        columnName="是否高峰";                             value=rowValue.get(columnName); if (value != null && (value.equals("Y")||value.equals("N"))) {            flightMateInfo.setIsPeakFlight(value);        }       
        columnName="过站";                                 value=rowValue.get(columnName); if (value != null && (value.equals("Y")||value.equals("N"))) {            flightMateInfo.setIsVia(value);        }   
        if (!verifyDescription.equals("")) {
            verifyDescription = "第" + (rowNumber + 1) +"行" + verifyDescription; //$NON-NLS-2$
            flightMateInfo.setVerifyDescription(verifyDescription);
        }

        errorMessage.put(rowNumber, errorCells);
        flightMateInfo.setErrorMessage(errorMessage);
        return flightMateInfo;
    }

    
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0;) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否为数字，包含小数
     * @param str
     * @return
     */
    public static boolean isDecimal(String str) {
        return Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?").matcher(str)
                .matches();
    } 
}


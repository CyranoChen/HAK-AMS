package com.wonders.frame.ams.rmi;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by 3701 on 2015/12/16.
 */
public interface IFlightMateInfoManager {
    //导入舱单数据
    public Map<String, Object> importFlightLoadDataByExcel2007(byte[] bytes) throws IOException;
    public Map<String, Object> importFlightLoadDataByExcel2003(byte[] bytes) throws IOException;


    public Map<String, Object> importFlightLoadDataByExcel(String fileName) throws IOException;



    //计算航班收费项目
    public Map<String,String> updateChargeTermById(List<Long> ids);


    //判断是否会近远机位
    public int isBridgeStand(String stand);


    //用于修改航段信息后 ,再次计算
    public Map<String, Object> saveFlightLoadData2FlightMateInfoByIds(List<Long> fmiIds) ;


    //单条航班配对
    //返回map键值对，数据类似："总计航班 ：347;生成：300;缺少对应连班：47;缺少机号：0;对应连班缺少机号：0;数据不完整：57;"
    //id = base_id
    public Map<String, Object> flightMatedInfoInit(Long id);



    // 机号导入
    public Map<String, Object> importAircraftAirlineAlterationDataByExcel(String fileName) throws IOException;


    //单条航班配载
    //入参id：flightBase的主键ID
    //返回的map：key为“total”，value是生成的配载记录数量；

    public Map<String, Object> flightLoadDataInit(Long id);



}

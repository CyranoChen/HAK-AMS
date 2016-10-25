package com.nlia.fqdb.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.ChargeTerm;
import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightMateInfo;
import com.nlia.fqdb.vo.ChargeTermVo;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IFlightMateInfoManager extends AbstractBaseService<FlightMateInfo, Long> {
    public Map<String, Object> flightMatedInfoInit(Date startDate,Date endDate);
    
    public Map<String, Object> flightMatedInfoInit(Long id);
    
    public Map<String, Object> flightLoadDataInit(Long id);
    
    public List<ChargeTerm> updateChargeTermAccordingToFlightMate(List<FlightMateInfo> flightMateInfos);
    
	/**
	 * return : 
	 *  key = term 收费条目数量
	 *  key = success 计算成功的航班数
	 *  key = failed 计算失败的航班数
	 *  key = msg 异常信息
	 */
    public Map<String,String> updateChargeTermById(List<Long> ids);
    /**
     * add by wangsq 20151129
     * 优化计费流程
     */
    public Object[] updateChargeTerm(List<FlightMateInfo> flightMateInfos);

    
    public Map<String, Object> importFlightMateInfoByExcel2003(byte[] bytes) throws IOException;

    public Map<String, Object> importFlightMateInfoByExcel2007(byte[] bytes) throws IOException;
    
    //导入舱单数据
//    public Map<String, Object> importFlightLoadDataByExcel2007(byte[] bytes) throws IOException;
//    public Map<String, Object> importFlightLoadDataByExcel2003(byte[] bytes) throws IOException;
    //导入舱单数据
    public Map<String, Object> importFlightLoadDataByExcel(String fileName) throws IOException;
    
	 //导入机号数据
    public Map<String, Object> importAircraftAirlineAlterationDataByExcel(String fileName) throws IOException;

    //查找时间时间段内，缺失FlightMateInfo的航班
    public List<FlightBase> findMisFlightMateInfo(Date startDate,Date endDate);
    //重新生成选定航班的FlightMateInfo
    public  Map<String, Object> repairMisFlightMateInfo(List<FlightBase> fbs);
    //返回远近机位标志 0 近机位 1远机位
    public int isBridgeStand(String stand); 
    
    public Map<String, Object> saveFlightLoadData2FlightMateInfoByIds(List<Long> fmiIds) ;

}

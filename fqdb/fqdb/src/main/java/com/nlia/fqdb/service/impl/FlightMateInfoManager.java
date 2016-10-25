package com.nlia.fqdb.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nlia.fqdb.configuration.DataCache;
import com.nlia.fqdb.configuration.DataHandler;
import com.nlia.fqdb.dao.ChargeInfoDao;
import com.nlia.fqdb.dao.ChargeSubjectDao;
import com.nlia.fqdb.dao.FlightMateInfoDao;
import com.nlia.fqdb.dao.FlightViewDao;
import com.nlia.fqdb.entity.AircraftAirlineAlteration;
import com.nlia.fqdb.entity.ChargeSubject;
import com.nlia.fqdb.entity.ChargeTarget;
import com.nlia.fqdb.entity.ChargeTerm;
import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightLoadData;
import com.nlia.fqdb.entity.FlightMateInfo;
import com.nlia.fqdb.entity.FlightView;
import com.nlia.fqdb.entity.SyntheticChargeTerm;
import com.nlia.fqdb.entity.base.Airline;
import com.nlia.fqdb.entity.base.Stand;
import com.nlia.fqdb.service.IFlightMateInfoManager;
import com.nlia.fqdb.specification.ChargeUtils;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;
import com.nlia.fqdb.util.ExcelOperatorUtils;
import com.nlia.fqdb.util.ImportFlightLoadDataByExcel;
import com.nlia.fqdb.util.ImportFlightMateInfoByExcel;
import com.nlia.fqdb.util.JsonUtil;
import com.nlia.fqdb.util.ResourceUtil;
import com.nlia.fqdb.vo.ChargeInfoVo;
import com.nlia.fqdb.vo.ChargeTermVo;
import com.nlia.fqdb.vo.SyntheticChargeInfoVo;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;
@Service
public class FlightMateInfoManager extends AbstractCrudService<FlightMateInfo, Long> implements IFlightMateInfoManager{
	
	@Resource
	private FlightMateInfoDao flightMateInfoDao;
	
	private String aircraftService_startime="02";
	private String aircraftService_endtime="06";
	//桥位
	private String bridge_Stand=",001,002,003,004,005,006,007,008,009,010,011,012,013,014,105,106,017,018,019,020,021,022,023,024,025,026,027,028";
	private List<Stand> listStand;
	
	//CAAC与IATA对应关系
	private Map<String,String> CAAC2IATAmap;
	@Resource
	private StandManager standmanager;
	/**
	 * add by wangsq 20151129
	 */
	@Resource
	private ChargeInfoDao chargeInfoDao;
	
	@Resource
	private ChargeSubjectDao chargeSubjectDao;
	
	@Resource
	private ChargeSubjectManager chargeSubjectManager;
	
	@Resource
	private FlightBaseManager flightBaseManager;
	
	@Resource
	private FlightMateInfoManager flightMateInfoManager;
	
	@Resource
	private AirlineManager airlineManager;
	
	@Resource
	private AircraftAirlineAlterationManager aircraftAirlineAlterationManager;
	
    @Resource
    private ImportFlightLoadDataByExcel importFlightLoadDataByExcel;
	
	@Resource
	private FlightLoadDataManager flightLoadDataManager;
	
	@Resource
    private ChargeTargetManager chargeTargetManager;
	
	@Resource
	private SyntheticChargeTermManager syntheticChargeTermManager;
	
	@Resource
	private ChargeTermManager chargeTermManager;
	
	@Resource
	private ImportFlightMateInfoByExcel importFlightMateInfoByExcel;
	
	@Resource
	private FlightServiceTypeManager flightServiceTypeManager;
	
    @Resource
    private FlightViewDao flightViewDao;
    
	@Override
	protected GenericDao<FlightMateInfo, Long> getDao() {
		// TODO Auto-generated method stub
		return flightMateInfoDao;
	}
	
	 @Override
    public Map<String, Object> flightMatedInfoInit(Long id) {
        // 数据准备
        // FLIGHTBASE
	 	 List<FlightBase> fbs = flightBaseManager
	                .getFlightBaseFromDataBaseWithId(id);
        	       
        // FlightMateInfo
        List<Long> ids=new ArrayList<>();
        for(FlightBase flightBase : fbs){
            ids.add(flightBase.getId());
        }
        List<FlightMateInfo> flightMateInfos = this.findByFlightBases(ids,null);
		Map<String, Object> resultMap= generate(flightMateInfos, fbs);
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("total",resultMap.get("leftFlightBases") );
		result.put("done",((List)resultMap.get("flightMateInfos")).size());
		result.put("noLink",resultMap.get("fbNoLinkFlightMes"));
		result.put("noReg",resultMap.get("fbNoRegisterationMes") );
		result.put("noLinkReg",resultMap.get("fbLinkNoRegisterationMes"));
		result.put("notComplete", resultMap.get("fbNotComplate"));
		return result;
    }
	 
	 public Map<String, Object> flightLoadDataInit(Long id) {
		return flightLoadDataManager.generate(id);
	 }

    @Override
    public Map<String, Object> flightMatedInfoInit(Date startDate, Date endDate) {
        // 数据准备
        // FLIGHTBASE
        List<FlightBase> fbs = flightBaseManager
                .getFlightBaseFromDataBaseWithActualTime(startDate, endDate);
        // 去处重复FLIGHTBASE
        List<FlightBase> temp = new ArrayList<>();
        for (FlightBase flightBase : fbs) {
            if (!temp.contains(flightBase)) {
                temp.add(flightBase);
            }
        }
        fbs.clear();
        fbs.addAll(temp);
        // FlightMateInfo
        List<Long> ids=new ArrayList<>();
        for(FlightBase flightBase : fbs){
            ids.add(flightBase.getId());
        }
        List<FlightMateInfo> flightMateInfos = this.findByFlightBases(ids,null);
		Map<String, Object> resultMap= generate(flightMateInfos, fbs);
		
		int cleatNum=clearInvalidData(startDate,endDate);
		
		System.out.println("-------------clear "+cleatNum+" flightMateInfo ");
		
		return resultMap;
    }

    private List<FlightMateInfo> findByFlightBases(List<Long> ids, String stat) {
        return flightMateInfoDao.findByFlightBases(ids, stat);
    }
    
	private int clearInvalidData(Date startTime, Date endTime) {
		return flightMateInfoDao.clear(startTime, endTime);
	}

    private Map<String, Object> generate(List<FlightMateInfo> flightMateInfos, List<FlightBase> fbs) {
        Map<Long, Long> linkFlightMateInfoIdMap = new HashMap<>();
        Map<String, Object> flightMateInfoResult = new HashMap<>();
        List<FlightBase> fbNoLinkFlightMes = new ArrayList<>();
        List<FlightBase> fbNoRegisterationMes = new ArrayList<>();
        List<FlightBase> fbLinkNoRegisterationMes = new ArrayList<>();
        List<FlightBase> fbNotComplate = new ArrayList<>();
        Map<String, Object> filters = new HashMap<>();

        // aircraftAirlineAlteration
        filters.clear();
        filters.put("validFlag_equal", AircraftAirlineAlteration.VALIDFLAG.VALID);
        filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
        List<AircraftAirlineAlteration> aaas = aircraftAirlineAlterationManager.findBy(filters);

        Properties p = ResourceUtil.getProperties("aircraftService", true);
        aircraftService_startime = p.getProperty("startime", "02");
        aircraftService_endtime = p.getProperty("endtime", "06");
        bridge_Stand= p.getProperty("bridgeStand",",001,002,003,004,005,006,007,008,009,010,011,012,013,014,015,016,017,018,019,020,021,022,023,024,025,026,027,028");
//        listStand = standmanager.findAllStand();

        DataHandler<FlightMateInfo> dataHandler = new DataHandler<>();
        List<FlightMateInfo> flightMateInfoshtMateInfos = new ArrayList<>();
        
        CAAC2IATAmap=flightServiceTypeManager.getCAAC2IATAmap();
        // 开始处理
        for (FlightBase flightBase : fbs) {
            filters.clear();
            filters.put("flightBaseId_equal", flightBase.getId());
            FlightMateInfo flightMateInfo = null;
            List<FlightMateInfo> listFlightMateInfo = dataHandler.findBy(flightMateInfos, filters);
            if (listFlightMateInfo.size() == 1) {
                flightMateInfo = listFlightMateInfo.get(0);
            }
            flightMateInfo = flightMatedInfoInitPara(flightMateInfo, flightBase, aaas);
            if (flightMateInfo.getLinkFlightBaseId() != null) {
                linkFlightMateInfoIdMap.put(flightMateInfo.getFlightBaseId(), flightMateInfo.getId());
            }else{
                fbNoLinkFlightMes.add(flightBase);
            }
            if (flightMateInfo != null && "1".equals(flightMateInfo.getStat())) {
                flightMateInfoshtMateInfos.add(flightMateInfo);
            } else {
                flightMateInfoshtMateInfos.add(flightMateInfo);
                fbNotComplate.add(flightBase);
            }
        }
        List<FlightMateInfo> retFlightMateInfos = new ArrayList<>();
        for (FlightMateInfo flightMateInfo : flightMateInfoshtMateInfos) {
            if (flightMateInfo.getLinkFlightBaseId() != null) {
                flightMateInfo.setLinkFlighMateInfoId(linkFlightMateInfoIdMap.get(flightMateInfo.getLinkFlightBaseId()));
                if (flightMateInfo.getId() != null) {
                	flightMateInfo.setModifyTime(new Timestamp(System.currentTimeMillis()));
                    flightMateInfo = flightMateInfoManager.save(flightMateInfo);
                }
                retFlightMateInfos.add(flightMateInfo);
            }
        }
        flightMateInfoResult.put("leftFlightBases", fbs.size());
        flightMateInfoResult.put("flightMateInfos", retFlightMateInfos);
        flightMateInfoResult.put("fbNoLinkFlightMes", fbNoLinkFlightMes.size());
        flightMateInfoResult.put("fbNoRegisterationMes",
                fbNoRegisterationMes.size());
        flightMateInfoResult.put("fbLinkNoRegisterationMes",
                fbLinkNoRegisterationMes.size());
        flightMateInfoResult.put("fbNotComplate", fbNotComplate.size());
        return flightMateInfoResult;
    }
    
    private FlightMateInfo flightMatedInfoInitPara(FlightMateInfo fmi,FlightBase flightBase,List<AircraftAirlineAlteration> aaas){
        if(fmi==null){
            fmi=new FlightMateInfo();
        }
        fmi = setFlightMateInfo(fmi,flightBase,aaas);      
        fmi.setModifyTime(new Timestamp(System.currentTimeMillis()));
        fmi = flightMateInfoManager.save(fmi);
        if(fmi.getVerifyDescription()!=null && !fmi.getVerifyDescription().equals("")){
        	System.out.println("----------flightMateInfo error:"+fmi.getId()+":"+fmi.getVerifyDescription());
        }
        return fmi;
    }
	
	//设置FlightMateInfo A的字段
	private void setFlightMateInfoAfields(FlightMateInfo flightMateInfo,
            FlightBase flightBase){
	    String VerifyDescription = flightMateInfo.getVerifyDescription();
	    if(VerifyDescription==null){
	        VerifyDescription="";
	    }
	    if (flightBase.getFlightData().getRegisteration() != null) {
            flightMateInfo.setAregisteration(flightBase.getFlightData()
                    .getRegisteration());
        } else {
            flightMateInfo.setStat("0");// 没有机号
            VerifyDescription += "没有进港机号;";
            //lushuaifeng 20160906
            flightMateInfo.setAregisteration(null);
        }
        flightMateInfo.setAFlightIdentity(flightBase.getFlightIdentity());
        flightMateInfo.setAAircraftType(flightBase.getFlightData()
                .getAircraftIATACode());
        flightMateInfo.setAStandNum(flightBase.getFlightResource()
                .getStandID());
        // 航班性质
        String AFlightProperty=CAAC2IATAmap.get(flightBase.getFlightData().getFlightServiceType());
        if(null==AFlightProperty){
            AFlightProperty=flightBase.getFlightData().getFlightServiceType();
        }
        flightMateInfo.setAFlightProperty(AFlightProperty);
        
//        //只判断进港部分是否异常
//        
//        //备降、其他备降、运输备降
//        if("B/J，Q/B，Y/B".contains(AFlightProperty)){
//            flightMateInfo.setAbnormalFlag("1");
//        }
//        
//        //返航、其他返航、运输返航
//        if("F/H，Q/F，Y/F".contains(AFlightProperty)){
//            flightMateInfo.setAbnormalFlag("2");
//        }
//
//
//        //取消
//        String flightStatus=flightBase.getFlightData().getFlightStatus();
//        if(flightStatus!=null && flightStatus.equals("X")){
//            flightMateInfo.setAbnormalFlag("3");
//        }
//        
        // 始发站
        flightMateInfo.setIataOriginAirport(flightBase.getFlightData()
                .getIATAOriginAirport());
        if (flightBase.getFlightData().getActualLandingDateTime() != null) {
            if(flightMateInfo.getExecuteTime()==null){
                flightMateInfo.setExecuteTime(flightBase.getFlightData()
                    .getActualLandingDateTime());
            }
            flightMateInfo.setLandedTime(flightBase.getFlightData()
                    .getActualLandingDateTime());
        } else {
            flightMateInfo.setStat("0");// 没有实际落地时间
            VerifyDescription += "没有实际落地时间;";
            if(flightMateInfo.getExecuteTime()==null){
                flightMateInfo.setExecuteTime(flightBase.getFlightData()
                    .getScheduledLandingDateTime());
            }
            //lushuaifeng 20160906
            flightMateInfo.setLandedTime(null);
        }
        if(flightMateInfo.getScheduledTime()==null){
        	Date scheduledTime=DateUtils.getRunningDate(flightBase.getFlightData().getScheduledLandingDateTime());
        	
            flightMateInfo.setScheduledTime(scheduledTime);
        }
        flightMateInfo.setScheduledLandedTime(flightBase.getFlightData().getScheduledLandingDateTime());
        flightMateInfo.setVerifyDescription(VerifyDescription);
        
       
	}
	//设置FlightMateInfo D的字段
	private void setFlightMateInfoDfields(FlightMateInfo flightMateInfo,
            FlightBase flightBase){
	    String VerifyDescription = flightMateInfo.getVerifyDescription();
	    if(VerifyDescription==null){
            VerifyDescription="";
        }
	    if (flightBase.getFlightData().getRegisteration() != null) {
            flightMateInfo.setDregisteration(flightBase.getFlightData()
                    .getRegisteration());
        } else {
            flightMateInfo.setStat("0");// 没有机号
            VerifyDescription += "没有离港机号;";
            //lushuaifeng 20160906
            flightMateInfo.setDregisteration(null);
        }
        flightMateInfo.setDFlightIdentity(flightBase.getFlightIdentity());
        flightMateInfo.setDAircraftType(flightBase.getFlightData().getAircraftIATACode());
        flightMateInfo.setDStandNum(flightBase.getFlightResource().getStandID());
     // 航班性质
        String DFlightProperty=CAAC2IATAmap.get(flightBase.getFlightData().getFlightServiceType());
        if(null==DFlightProperty){
            DFlightProperty=flightBase.getFlightData().getFlightServiceType();
        }
        flightMateInfo.setDFlightProperty(DFlightProperty);
        // 目的站
        flightMateInfo.setIataDestinationAirport(flightBase.getFlightData()
                .getIATADestinationAirport());
        if (flightBase.getFlightData().getActualTakeOffDateTime() != null) {
            if(flightMateInfo.getExecuteTime()==null){
                flightMateInfo.setExecuteTime(flightBase.getFlightData()
                    .getActualTakeOffDateTime());
            }
            flightMateInfo.setTakeOffTime(flightBase.getFlightData()
                    .getActualTakeOffDateTime());
        } else {
            flightMateInfo.setStat("0");// 没有实际起飞时间
            VerifyDescription += "没有实际起飞时间";
            if(flightMateInfo.getExecuteTime()==null){
                flightMateInfo.setExecuteTime(flightBase.getFlightData()
                    .getScheduledTakeOffDateTime());
            }
            //lushuaifeng 20160906
            flightMateInfo.setTakeOffTime(null);
        }
        if(flightMateInfo.getScheduledTime()==null){
        	Date scheduledTime=DateUtils.getRunningDate(flightBase.getFlightData().getScheduledTakeOffDateTime());
            flightMateInfo.setScheduledTime(scheduledTime);
        }
        flightMateInfo.setScheduledTakeOffTime(flightBase.getFlightData().getScheduledTakeOffDateTime());
        flightMateInfo.setVerifyDescription(VerifyDescription);
    }
	
	
    private FlightMateInfo setFlightMateInfo(FlightMateInfo flightMateInfo,
            FlightBase flightBase,
            List<AircraftAirlineAlteration> listAircraftAirlineAlteration) {
        String VerifyDescription = "";
        Date jhrq = null;
        flightMateInfo.setStat("1");//默认成功
        // flightBaseID
        flightMateInfo.setFlightBaseId(flightBase.getId());
        // flightMateInfo.setFlightServiceInfoId(flightBase.getFlightServiceInfo().getId());
        if (flightBase.getFlightDirection().equals("0")) {
            setFlightMateInfoAfields(flightMateInfo,flightBase);
            jhrq=flightMateInfo.getScheduledLandedTime();
            if (flightBase.getLinkFlight() != null) {
                setFlightMateInfoDfields(flightMateInfo,flightBase.getLinkFlight());
            }
            VerifyDescription=flightMateInfo.getVerifyDescription();
        } else {
            setFlightMateInfoDfields(flightMateInfo,flightBase);
            jhrq=flightMateInfo.getScheduledTakeOffTime();
            if (flightBase.getLinkFlight() != null) {
                setFlightMateInfoAfields(flightMateInfo,flightBase.getLinkFlight());
            }
            VerifyDescription=flightMateInfo.getVerifyDescription();
        }
        
        
      //只判断进港部分是否异常           
      //返航、其他返航、运输返航
      String flightProperty=null;
  	  if(flightMateInfo.getFlightDirection().equals("0")){
  		  flightProperty=flightMateInfo.getAFlightProperty();
      }else{
    	  flightProperty=flightMateInfo.getDFlightProperty();
      }
      
      if(flightProperty!=null && "F/H，Q/F，Y/F".contains(flightProperty)){
          flightMateInfo.setAbnormalFlag("2");
      }  	 
      
      //备降、其他备降、运输备降
      if(flightMateInfo.getAFlightProperty()!=null && "B/J，Q/B，Y/B".contains(flightMateInfo.getAFlightProperty())){
          flightMateInfo.setAbnormalFlag("1");
      }
      
      //取消
      String flightStatus=flightBase.getFlightData().getFlightStatus();
      if(flightStatus!=null && flightStatus.equals("X")){
          flightMateInfo.setAbnormalFlag("3");
      }
      
        if (flightMateInfo.getAFlightIdentity()!=null && flightMateInfo.getDFlightIdentity()!=null && flightMateInfo.getAFlightIdentity().equals(flightMateInfo.getDFlightIdentity())) {
            //flightMateInfo.setIsVia("Y");
            flightMateInfo.setIsVia("1");
        }
        if (flightBase.getLinkFlight() != null) {
            flightMateInfo.setLinkFlightBaseId(flightBase.getLinkFlight()
                    .getId());
        } else {// 没有连班
            flightMateInfo.setStat("0");
            VerifyDescription += "没有连班";
            //lushuaifeng 20160906
            flightMateInfo.setLinkFlightBaseId(null);
        }
       
        // 进离港标识
        flightMateInfo.setFlightDirection(flightBase.getFlightDirection());
        // 延误原因编码
        flightMateInfo.setDelayCode(flightBase.getFlightData().getDelayCode());

        // 停场时间
        if (flightMateInfo.getLandedTime() != null
                && flightMateInfo.getTakeOffTime() != null) {
            double landTimeHours = Math.ceil(Math.abs(DateUtils.getDifferHour(
                    flightMateInfo.getLandedTime(),
                    flightMateInfo.getTakeOffTime())));
            
            long landTime=0;
            if(landTimeHours>2){//HAK 改为停场天数
                landTime=(long) Math.ceil(landTimeHours/24);
            }
            flightMateInfo.setLandTime((double) landTime);
        }
        //设置航前、航后、过站标志
        setAircaftService(flightMateInfo);
        
        setStandFlag(flightMateInfo);
        // 收费对象
        if (listAircraftAirlineAlteration.size() > 0
                && flightBase.getFlightData().getRegisteration() != null) {
            DataHandler<AircraftAirlineAlteration> dataHandler = new DataHandler<>();
            Map<String, Object> filters = new HashMap<>();
            if (jhrq != null) {// 有计划日期
            	Date yyrq=DateUtils.getRunningDate(jhrq);
                filters.put("endDate_greaterThanOrEqualTo", yyrq);
                filters.put("startDate_lessThanOrEqualTo", yyrq);// 开始、结束时间在flightLoadData.getSjrq的两边
                filters.put("aircraftRegistration_equal", flightBase
                        .getFlightData().getRegisteration());
                List<AircraftAirlineAlteration> aircraftAirlineAlterationList = dataHandler
                        .findBy(listAircraftAirlineAlteration, filters);
                if (aircraftAirlineAlterationList.size() != 1) {
                    flightMateInfo.setStat("0");
                    flightMateInfo.setAirlineName(null);
                    
                	                    
                    //lushuaifeng 20160912     
                    List<Airline> allAirlines=airlineManager.findAllAirline();
                         
                    boolean setAirlineHandler=setAirlineHandlerByIATACode(allAirlines,flightMateInfo,flightBase.getAirlineIATACode());
                    
                    if(!setAirlineHandler){
                    	 setAirlineHandler=setAirlineHandlerByICAOCode(allAirlines,flightMateInfo,flightBase.getAirlineICAOCode());
                    }                   

                    if(!setAirlineHandler){
                		flightMateInfo.setAirlineHandler(null);
                	}
                    
                    VerifyDescription += "机号所属航空公司数量不等于1";
                } else {
                    AircraftAirlineAlteration aircraftAirlineAlteration = aircraftAirlineAlterationList
                            .get(0);
                    flightMateInfo.setAircraftAirlineAlterationId(aircraftAirlineAlteration.getId());//aaa_id
                    if (aircraftAirlineAlteration.getCurrentSubairline() != null
                            && !aircraftAirlineAlteration
                                    .getCurrentSubairline().equals("")) {
                        // 航空公司
                        flightMateInfo.setAirline(aircraftAirlineAlteration
                                .getCurrentSubairline());
                        // 航空公司名称
                        flightMateInfo
                                .setAirlineName(aircraftAirlineAlteration
                                        .getCurrentSubairline()
                                        .getAirlineDescription());
                        //航空公司代理
                        flightMateInfo.setAirlineHandler(aircraftAirlineAlteration.getCurrentSubairline().getAirlineHandler());
                    } else if (aircraftAirlineAlteration.getCurrentAirline() != null
                            && !aircraftAirlineAlteration.getCurrentAirline()
                                    .equals("")) {
                        flightMateInfo.setAirline(aircraftAirlineAlteration
                                .getCurrentAirline());
                        flightMateInfo.setAirlineName(aircraftAirlineAlteration
                                .getCurrentAirline().getAirlineDescription());
                      //航空公司代理
                      flightMateInfo.setAirlineHandler(aircraftAirlineAlteration.getCurrentAirline().getAirlineHandler());
                    }
                    // 起飞全重
                    flightMateInfo
                            .setAircraftTakeOffWeight((double) (aircraftAirlineAlteration
                                    .getAircraftTakeoffWeight() == null ? 0
                                    : aircraftAirlineAlteration
                                            .getAircraftTakeoffWeight()));
                    // 飞机宽窄体
                    flightMateInfo
                            .setIsWideOrNarrow((aircraftAirlineAlteration
                                    .getIsWideOrNarrow() != null && aircraftAirlineAlteration
                                    .getIsWideOrNarrow().equals("L")) ? "0"
                                    : "1");
                    // 飞机最大业载
                    flightMateInfo.setAircraftPayload((double) (aircraftAirlineAlteration
                            .getAircraftPayload() == null ? 0
                            : aircraftAirlineAlteration.getAircraftPayload()));
                    // 飞机座位数
                    flightMateInfo
                            .setAircraftSeatCapacity((double) (aircraftAirlineAlteration
                                    .getAircraftSeatCapacity() == null ? 0
                                    : aircraftAirlineAlteration
                                            .getAircraftSeatCapacity()));
                    // 高密度标识
                    String IsHighDensity="0";
                    if(null!= aircraftAirlineAlteration.getIsHighDensity() && !aircraftAirlineAlteration.getIsHighDensity().isEmpty()){
                        IsHighDensity=aircraftAirlineAlteration.getIsHighDensity().equals("Y")?"1":"0";
                    }
                    flightMateInfo.setIsHighDensity(IsHighDensity);
                    
                    // 使用集装设备标识
                    String IsPackagingFacility="0";
                    if(null!= aircraftAirlineAlteration.getIsPackagingFacility() && !aircraftAirlineAlteration.getIsPackagingFacility().isEmpty()){
                        IsPackagingFacility=aircraftAirlineAlteration.getIsPackagingFacility().equals("Y")?"1":"0";
                    }
                    flightMateInfo.setIsPackagingFacility(IsPackagingFacility);
                    
                }
            }
        } else {
            flightMateInfo.setStat("0");
            VerifyDescription += "机号所属航空公司数量不等于1";
        }
        // 航班类型（国内、国际、港澳台）
        String flightCountryType = flightBase.getFlightData()
                .getFlightCountryType();
        if(null==flightCountryType){//默认D
            flightCountryType="D";
        }
        if (flightCountryType.equals("D")) {
            flightCountryType = "0";
        } else if (flightCountryType.equals("I")) {
            flightCountryType = "1";
        } else if (flightCountryType.equals("R")) {
            flightCountryType = "2";
        } else if (flightCountryType.equals("M")) {
            flightCountryType = "3";
        }
        flightMateInfo.setFlightCountryType(flightCountryType);
        // 是否夜航
        if (flightIsNightFlight(flightBase.getFlightData().getActualLandingDateTime())
          ||flightIsNightFlight(flightBase.getFlightData().getActualTakeOffDateTime())) {
            flightMateInfo.setIsNightFlight("1");
        } else {
            flightMateInfo.setIsNightFlight("0");
        }

        if (flightIsPeakFlight(flightBase.getFlightData()
                .getActualLandingDateTime())
                || flightIsPeakFlight(flightBase.getFlightData()
                        .getActualTakeOffDateTime())) {
            flightMateInfo.setIsPeakFlight("1");
        } else {
            flightMateInfo.setIsPeakFlight("0");
        }

        flightMateInfo.setVerifyDescription(VerifyDescription);
        return flightMateInfo;
    }
    
    private boolean setAirlineHandlerByIATACode(List<Airline> airlines,FlightMateInfo fi,String airlineIATACode){
    	boolean isSuccess=false;
    	
    	if(airlineIATACode==null || airlineIATACode.equals("")){
    		return isSuccess;
    	}
    	
    	Map<String, Object> filterMap = new HashMap<>();
    	filterMap.put("airlineIATACode_equal", airlineIATACode);
    	
        DataHandler<Airline> dataHandler = new DataHandler<>();
        List<Airline> list = dataHandler.findBy(airlines, filterMap);
    	
    	if(list==null || list.isEmpty()){
    		return isSuccess;
    	}
    	
    	for(Airline airline:list){
    		if(airline.getParentAirline()==null){
    			fi.setAirlineHandler(airline.getAirlineHandler());
    			isSuccess=true;
    			break;
    		}
    	}
    	
    	return isSuccess;
    		
        
    }
    
    private boolean setAirlineHandlerByICAOCode(List<Airline> airlines,FlightMateInfo fi,String airlineICAOCode){
    	boolean isSuccess=false;
    	if(airlineICAOCode==null || airlineICAOCode.equals("")){
    		return isSuccess;
    	}
    	
    	Map<String, Object> filterMap = new HashMap<>();

    	filterMap.put("airlineICAOCode_equal", airlineICAOCode);
    	
    	DataHandler<Airline> dataHandler = new DataHandler<>();
    	 
        List<Airline> list = dataHandler.findBy(airlines, filterMap);
    	
    	if(list==null || list.isEmpty()){
    		return isSuccess;
    	}
    	
    	for(Airline airline:list){
    		if(airline.getParentAirline()==null){
    			fi.setAirlineHandler(airline.getAirlineHandler());
    			isSuccess=true;
    			break;
    		}
    	}
    	
    	return isSuccess;
    		
        
    }
    private void setStandFlag(FlightMateInfo flightMateInfo) {
//        if(listStand!=null&& !listStand.isEmpty()){
            DataHandler<Stand> standDataHandler = new DataHandler<>();
            Map<String, Object> filters = new HashMap<>();
            if("HOLD".equals(flightMateInfo.getAStandNum())){
                flightMateInfo.setAStandFlag("");
                flightMateInfo.setAStandNum("");
            }
            if(flightMateInfo.getAStandNum()!=null && !flightMateInfo.getAStandNum().isEmpty()){
                String aStandFlag="1";//0:近机位 1:远机位
                if(bridge_Stand.contains(flightMateInfo.getAStandNum())){
                    aStandFlag="0";
                }
                flightMateInfo.setAStandFlag(aStandFlag);
            }
            if("HOLD".equals(flightMateInfo.getDStandNum())){
                flightMateInfo.setDStandFlag("");
                flightMateInfo.setDStandNum("");
            }
            if(flightMateInfo.getDStandNum()!=null && !flightMateInfo.getDStandNum().isEmpty()){
                String dStandFlag="1";//0:近机位 1:远机位
                if(bridge_Stand.contains(flightMateInfo.getDStandNum())){
                    dStandFlag="0";
                }
                flightMateInfo.setDStandFlag(dStandFlag);
            }
//        }
        
    }

    private void setAircaftService(FlightMateInfo fmi){
        //设置 aircraftService
        String aircraftService="2";
        Date startTime=null,endTime=null;
        boolean isBeforaircraftService_startime=true,isAfteraircraftService_endtime=true;
        String landyyyymmdd="",landhour="",takeoffyyyymmdd="",takeoffhour="";
        
        if(fmi.getScheduledTakeOffTime()!=null){
             takeoffyyyymmdd=DateUtils.Date2String(fmi.getScheduledTakeOffTime(), "yyyy-MM-dd ");
             takeoffhour =DateUtils.Date2String(fmi.getScheduledTakeOffTime(), "HH");
             isAfteraircraftService_endtime=aircraftService_endtime.compareTo(takeoffhour)<=0?true:false;
        }
        if (fmi.getScheduledLandedTime() != null) {
            landyyyymmdd = DateUtils.Date2String(fmi.getScheduledLandedTime(),"yyyy-MM-dd ");
            landhour = DateUtils.Date2String(fmi.getScheduledLandedTime(), "HH");
            if(!takeoffyyyymmdd.isEmpty()){
                startTime=DateUtils.String2Date(takeoffyyyymmdd+" " + aircraftService_startime +":00");
                isBeforaircraftService_startime=fmi.getScheduledLandedTime().before(startTime);
            }else{
                if(landhour.compareTo(aircraftService_startime)>0){
                    startTime=DateUtils.getDateAfter(DateUtils.String2Date(landyyyymmdd+" " + aircraftService_startime +":00"),1);
                    if(DateUtils.getDifferHour(startTime, fmi.getScheduledLandedTime())>8){//时差大于8小时
                        isBeforaircraftService_startime=false;
                    }
                }
            }
            
        }
        if(isBeforaircraftService_startime && isAfteraircraftService_endtime){
            if(fmi.getFlightDirection().equals("0")){
                aircraftService="0";
            }else{
                aircraftService="1";
            }
        }
        fmi.setAircraftService(aircraftService);
    }
    
    private boolean flightIsNightFlight(Date date){
        boolean isNightFlight=false;
        if(date!=null){
//            String yyyymmdd=DateUtils.Date2String(date, "yyyy-MM-dd ");
            String HHmm=DateUtils.Date2String(date, "HHmm");
            String st="2300";
            String et="0600";
            if(HHmm.compareTo(st)>=0 || HHmm.compareTo(et)<=0){
                isNightFlight=true;
            }
//            Date startTime=null,endTime=null;
//            if(HHmm.compareTo(st)>=0){//2300以后
//                startTime=DateUtils.String2Date(yyyymmdd+" 23:00");
//                endTime=DateUtils.getDateAfter(DateUtils.String2Date(yyyymmdd+" 06:00"),1);
//            }else{//2300以前
//                 return false;
//            }
//            if(HHmm.compareTo(et)<=0){//0600以前
//                startTime=DateUtils.getDateAfter(DateUtils.String2Date(yyyymmdd+" 23:00"),-1);
//                endTime=DateUtils.String2Date(yyyymmdd+" 06:00");
//            }else{//0600以后
//                return false;
//            }
//            if(DateUtils.isDateDuringPeriod(date, startTime, endTime)){
//                isNightFlight=true;
//            }
        }
        return isNightFlight;
    }
    
	private boolean flightIsPeakFlight(Date date){
	    boolean isPeakFlight=false;
	    if(date!=null){
	        String yyyymmdd=DateUtils.Date2String(date, "yyyy-MM-dd ");
	        Date startTime=DateUtils.String2Date(yyyymmdd+" 08:00");
	        Date endTime=DateUtils.String2Date(yyyymmdd+" 10:00");
	        Date startTime2=DateUtils.String2Date(yyyymmdd+" 11:30");
	        Date endTime2=DateUtils.String2Date(yyyymmdd+" 13:00");
	        Date startTime3=DateUtils.String2Date(yyyymmdd+" 14:00");
	        Date endTime3=DateUtils.String2Date(yyyymmdd+" 18:00");
	        if(DateUtils.isDateDuringPeriod(date, startTime, endTime)
	         ||DateUtils.isDateDuringPeriod(date, startTime2, endTime2)
	         ||DateUtils.isDateDuringPeriod(date, startTime3, endTime3)){
	            isPeakFlight=true;
	        }
	    }
	    return isPeakFlight;
	}
	        
//	private FlightMateInfo flightMatedInfoInitPara(List<FlightMateInfo> fmis,FlightBase flightBase,List<FlightLoadData> flds,List<AircraftAirlineAlteration> aaas,List<FlightServiceInfoHistory> fsis){
//	    FlightMateInfo fmi=null;
//	    //航班及其连班有实际时间
//	    if(  (flightBase.getFlightData().getActualLandingDateTime()!=null && flightBase.getLinkFlight().getFlightData().getActualTakeOffDateTime()!=null)
//	       || (flightBase.getFlightData().getActualTakeOffDateTime()!=null && flightBase.getLinkFlight().getFlightData().getActualLandingDateTime()!=null)){
////    	    DataHandler<FlightMateInfo> dataHandler = new DataHandler<>();
////    	    Map<String, Object> filters = new HashMap<>();
////            filters.put("flightBaseId_equal", flightBase.getId());
////            List<FlightMateInfo> flightMateInfoList= dataHandler.findBy(fmis,filters);
////            if (flightMateInfoList.size()==0) {//不存在，生成
//                fmi = new FlightMateInfo();
//                //航班信息实体赋值
//                fmi = setFlightMateInfo(fmi,flightBase,aaas,flds, fsis);
//                if(fmi!=null && fmi.getVerifyDescription()==null){
//                    fmi = flightMateInfoManager.save(fmi);
//                }
////            }else{//存在
////               fmi= flightMateInfoList.get(0);
////               if(fmi.getMatchMethod().equals("0")){//自动生成的
////                  fmi = setFlightMateInfo(fmi,flightBase,aaas,flds,fsis);
////               }
////            }
//	    }
//	    return fmi;
//	}
//	private FlightMateInfo setFlightMateInfo(FlightMateInfo flightMateInfo,FlightBase flightBase,List<AircraftAirlineAlteration> listAircraftAirlineAlteration ,List<FlightLoadData> listFlightLoadData,List<FlightServiceInfoHistory> listFlightServiceInfoHistory){
//	    DataHandler<FlightServiceInfoHistory> dataHandlerFsiH = new DataHandler<>();
//        Map<String, Object> filtersFsiH = new HashMap<>();
//        filtersFsiH.put("flightBaseId_equal", flightBase.getId());
//	    List<FlightServiceInfoHistory> flightServiceInfoHistorys = dataHandlerFsiH.findBy(listFlightServiceInfoHistory,filtersFsiH);
//	    
//        // 机号、航班号等基础信息
//        
//        // flightBaseID
//        flightMateInfo.setFlightBaseId(flightBase.getId());
//        flightMateInfo.setFlightServiceInfoId(flightBase.getFlightServiceInfo().getId());
//        flightMateInfo.setLinkFlightBaseId(flightBase.getLinkFlight().getId());
//        if(flightBase.getFlightIdentity().equals(flightBase.getLinkFlight().getFlightIdentity())){
//            flightMateInfo.setIsVia("Y");
//        }
//        //进离港标识
//        flightMateInfo.setFlightDirection(flightBase.getFlightDirection());
//        //延误原因编码
//        flightMateInfo.setDelayCode(flightBase.getFlightData().getDelayCode());
//        //add by march 20151110 飞机服务类型PRE("0"), AFT("1"), TR("2");
//        if(flightBase.getFlightServiceInfo().getAircraftService()!=null){
//          flightMateInfo.setAircraftService(flightBase.getFlightServiceInfo().getAircraftService().getValue());
//        }
//        //执行日
//        Date sjrq=null,actualLandingDateTime=null, actualTakeOffDateTime=null;
//        if(flightBase.getFlightDirection().equals("0")){
//            flightMateInfo.setAregisteration(flightBase.getFlightData().getRegisteration());
//            flightMateInfo.setDregisteration(flightBase.getLinkFlight().getFlightData().getRegisteration());
//            flightMateInfo.setAFlightIdentity(flightBase.getFlightIdentity());
//            flightMateInfo.setDFlightIdentity(flightBase.getLinkFlight().getFlightIdentity());
//            flightMateInfo.setAAircraftType(flightBase.getFlightData().getAircraftIATACode());
//            flightMateInfo.setAStandNum(flightBase.getFlightResource().getStandID());
//            flightMateInfo.setDAircraftType(flightBase.getLinkFlight().getFlightData().getAircraftIATACode());
//            flightMateInfo.setDStandNum(flightBase.getLinkFlight().getFlightResource().getStandID());
//            
//            sjrq=actualLandingDateTime=flightBase.getFlightData().getActualLandingDateTime();
//            actualTakeOffDateTime=flightBase.getLinkFlight().getFlightData().getActualTakeOffDateTime();
//            flightMateInfo.setExecuteTime(flightBase.getFlightData().getActualLandingDateTime());
//            flightMateInfo.setLandedTime(sjrq);
//            flightMateInfo.setTakeOffTime(flightBase.getLinkFlight().getFlightData().getActualTakeOffDateTime());
//            //航班性质
//            flightMateInfo.setAFlightProperty(flightBase.getFlightData().getFlightServiceType());
//            flightMateInfo.setDFlightProperty(flightBase.getLinkFlight().getFlightData().getFlightServiceType());
//            //始发站
//            flightMateInfo.setIataOriginAirport(flightBase.getFlightData().getIATAOriginAirport()); 
//            //目的站
//            flightMateInfo.setIataDestinationAirport(flightBase.getLinkFlight().getFlightData().getIATADestinationAirport());
//        }else{
//            flightMateInfo.setAregisteration(flightBase.getLinkFlight().getFlightData().getRegisteration());
//            flightMateInfo.setDregisteration(flightBase.getFlightData().getRegisteration());
//            flightMateInfo.setAFlightIdentity(flightBase.getLinkFlight().getFlightIdentity());
//            flightMateInfo.setDFlightIdentity(flightBase.getFlightIdentity());
//            flightMateInfo.setAAircraftType(flightBase.getFlightData().getAircraftIATACode());
//            flightMateInfo.setAStandNum(flightBase.getFlightResource().getStandID());
//            flightMateInfo.setDAircraftType(flightBase.getLinkFlight().getFlightData().getAircraftIATACode());
//            flightMateInfo.setDStandNum(flightBase.getLinkFlight().getFlightResource().getStandID());
//            
//            sjrq=actualTakeOffDateTime=flightBase.getFlightData().getActualTakeOffDateTime();
//            actualLandingDateTime=flightBase.getLinkFlight().getFlightData().getActualLandingDateTime();
//            flightMateInfo.setExecuteTime(flightBase.getFlightData().getActualTakeOffDateTime());
//            flightMateInfo.setLandedTime(flightBase.getLinkFlight().getFlightData().getActualLandingDateTime());
//            flightMateInfo.setTakeOffTime(sjrq);
//            //航班性质
//            flightMateInfo.setAFlightProperty(flightBase.getLinkFlight().getFlightData().getFlightServiceType());
//            flightMateInfo.setDFlightProperty(flightBase.getFlightData().getFlightServiceType());
//            //始发站
//            flightMateInfo.setIataOriginAirport(flightBase.getLinkFlight().getFlightData().getIATAOriginAirport()); 
//            //目的站
//            flightMateInfo.setIataDestinationAirport(flightBase.getFlightData().getIATADestinationAirport());
//        }
//        //停场时间
//        if(actualLandingDateTime!=null &&  actualTakeOffDateTime!=null){
//            long landTime = (long) Math.ceil(Math.abs(DateUtils.getDifferHour(actualLandingDateTime, actualTakeOffDateTime)));
//            flightMateInfo.setLandTime(landTime);
//        }
//        
//	        //收费对象
//	        if( listAircraftAirlineAlteration.size()>0){
//	            DataHandler<AircraftAirlineAlteration> dataHandler = new DataHandler<>();
//	            Map<String, Object> filters = new HashMap<>();
//	            if(sjrq!=null){//有实际日期
//    	            filters.put("endDate_greaterThanOrEqualTo",sjrq);
//    	            filters.put("startDate_lessThanOrEqualTo",sjrq);//开始、结束时间在flightLoadData.getSjrq的两边
//    	            filters.put("aircraftRegistration_equal",flightBase.getFlightData().getRegisteration());
//    	            List<AircraftAirlineAlteration> aircraftAirlineAlterationList = dataHandler.findBy(listAircraftAirlineAlteration, filters);
//    	            if(!aircraftAirlineAlterationList.isEmpty()){
//    	                if(aircraftAirlineAlterationList.size()>1){
//    	                    System.out.println("多条AircraftAirlineAlteration，取第1条");
//    	                }
//    	                AircraftAirlineAlteration aircraftAirlineAlteration=aircraftAirlineAlterationList.get(0);
//    	                if(aircraftAirlineAlteration.getCurrentSubairline()!= null && !aircraftAirlineAlteration.getCurrentSubairline().equals("")){
//    	                    //航空公司
//    	                    flightMateInfo.setAirline(aircraftAirlineAlteration.getCurrentSubairline());
//    	                    //航空公司名称
//    	                    flightMateInfo.setAirlineName(aircraftAirlineAlteration.getCurrentSubairline().getAirlineDescription());
//    	                }else if(aircraftAirlineAlteration.getCurrentAirline()!= null && !aircraftAirlineAlteration.getCurrentAirline().equals("")){
//    	                    flightMateInfo.setAirline(aircraftAirlineAlteration.getCurrentAirline());
//    	                    flightMateInfo.setAirlineName(aircraftAirlineAlteration.getCurrentAirline().getAirlineDescription());
//    	                }
//    	              //起飞全重
//    	                flightMateInfo.setAircraftTakeOffWeight(aircraftAirlineAlteration.getAircraftTakeoffWeight()==null?0:aircraftAirlineAlteration.getAircraftTakeoffWeight());
//    	                //飞机宽窄体
//    	                flightMateInfo.setIsWideOrNarrow((aircraftAirlineAlteration.getIsWideOrNarrow()!=null && aircraftAirlineAlteration.getIsWideOrNarrow().equals("L"))?"0":"1");
//    	                //飞机最大业载
//    	                flightMateInfo.setAircraftPayload(aircraftAirlineAlteration.getAircraftPayload()==null?0:aircraftAirlineAlteration.getAircraftPayload());
//    	                //飞机座位数
//    	                flightMateInfo.setAircraftSeatCapacity(aircraftAirlineAlteration.getAircraftSeatCapacity()==null?0:aircraftAirlineAlteration.getAircraftSeatCapacity());
//    	                //高密度标识
//    	                flightMateInfo.setIsHighDensity((aircraftAirlineAlteration.getIsHighDensity()==null || "".equals(aircraftAirlineAlteration.getIsHighDensity()) )?"N":aircraftAirlineAlteration.getIsHighDensity());
//    	                //使用集装设备标识
//    	                flightMateInfo.setIsPackagingFacility((aircraftAirlineAlteration.getIsPackagingFacility()==null || "".equals(aircraftAirlineAlteration.getIsPackagingFacility()) )?"N":aircraftAirlineAlteration.getIsPackagingFacility());
//    	            }else{
//    	                flightMateInfo.setVerifyDescription("no AircraftAirlineAlteration");
//    	            }
//	            }else{//没有实际日期
//	                return null;
//	            }
//	        }else{
//	            flightMateInfo.setVerifyDescription("no AircraftAirlineAlteration");
//	        }
//	        //引导车使用次数
//            flightMateInfo.setLeadCarUsedCount(flightBase.getFlightServiceInfo().getLeadCarUsedCount()==null?"0":flightBase.getFlightServiceInfo().getLeadCarUsedCount());
//            //客桥数量
////          flightMateInfo.setPassengerBridgeNumber(flightBase.getFlightServiceInfo().getPassengerBridgeNumber());
//            //客桥使用时间
//            flightMateInfo.setPassengerBridgeTime(FlightServiceInfoHistoryManager.generateFacilityUseTime(flightServiceInfoHistorys, 1));
//            //牵引车使用次数
//            flightMateInfo.setTractorsUsedCount(flightBase.getFlightServiceInfo().getTractorsUsedCount()==null?"0":flightBase.getFlightServiceInfo().getTractorsUsedCount());
//            //汽源车使用时间
//            flightMateInfo.setAirTruckTime(FlightServiceInfoHistoryManager.generateFacilityUseTime(flightServiceInfoHistorys, 33));
//            //空调车使用时间
//            flightMateInfo.setAirconditioningTime(FlightServiceInfoHistoryManager.generateFacilityUseTime(flightServiceInfoHistorys, 11));
//            //除冰车
//            flightMateInfo.setDeicingVehicleTime(FlightServiceInfoHistoryManager.generateFacilityUseTime(flightServiceInfoHistorys, 9));
//            //电源车
//            flightMateInfo.setElectricTruckTime(FlightServiceInfoHistoryManager.generateFacilityUseTime(flightServiceInfoHistorys, 5));
//            //升降平台车
//            flightMateInfo.setLiftingPlatformCarTime(FlightServiceInfoHistoryManager.generateFacilityUseTime(flightServiceInfoHistorys, 7));
//            //客梯车
//            flightMateInfo.setPsssengerCarUsedTime(FlightServiceInfoHistoryManager.generateFacilityUseTime(flightServiceInfoHistorys, 3));
//            //例行检查
//            flightMateInfo.setRoutineCheckTime(FlightServiceInfoHistoryManager.generateFacilityUseTime(flightServiceInfoHistorys, 29));
//            //机组摆渡车使用次数
//            flightMateInfo.setAirdromeUsedByCrewCount(flightBase.getFlightServiceInfo().getAirdromeCountUsedByCrew()==null?"0":flightBase.getFlightServiceInfo().getAirdromeCountUsedByCrew());
//            //旅客摆渡车使用次数
//            flightMateInfo.setAirdromeUsedByPasCount(flightBase.getFlightServiceInfo().getAirdromeCountUsedByPassenger()==null?"0":flightBase.getFlightServiceInfo().getAirdromeCountUsedByPassenger());
//            //残疾人专用车使用次数
//            flightMateInfo.setSpeCarForDisabledCount(flightBase.getFlightServiceInfo().getSpecialVehiclesCountForDisabled()==null?"0":flightBase.getFlightServiceInfo().getSpecialVehiclesCountForDisabled());
//            //垃圾车次数
//            flightMateInfo.setGarbageTruckUseCount(flightBase.getFlightServiceInfo().getGarbageTruckUsedCount()==null?"0":flightBase.getFlightServiceInfo().getGarbageTruckUsedCount());
//            //清水车次数
//            flightMateInfo.setWaterTruckUseCount(flightBase.getFlightServiceInfo().getWaterTruckUsedCount()==null?"0":flightBase.getFlightServiceInfo().getWaterTruckUsedCount());
//            //污水车次数
//            flightMateInfo.setCesspoolageTruckUseCount(flightBase.getFlightServiceInfo().getCesspoolageTruckUsedCount()==null?"0":flightBase.getFlightServiceInfo().getCesspoolageTruckUsedCount());
//            //航班类型（国内、国际、港澳台）
//            String flightCountryType = flightBase.getFlightData().getFlightCountryType();
//            if(flightCountryType.equals("D")){
//                flightCountryType = "0";
//            }else if(flightCountryType.equals("I")){
//                flightCountryType = "1";
//            }else if(flightCountryType.equals("R")){
//                flightCountryType = "2";
//            }else if(flightCountryType.equals("M")){
//                flightCountryType = "3";
//            }
//            flightMateInfo.setFlightCountryType(flightCountryType);
//            //是否夜航
//            if ((flightBase.getFlightData().getIsOverNightFlight() != null && flightBase.getFlightData().getIsOverNightFlight().equals("Y"))) {
//                flightMateInfo.setIsNightFlight("Y");
//            }else {
//                flightMateInfo.setIsNightFlight("N");
//            }
//            
//            
//            //配载信息
//        if (listFlightLoadData.size() > 0) {
//            DataHandler<FlightLoadData> dataHandler = new DataHandler<>();
//            Map<String, Object> filters = new HashMap<>();
//            filters.put("flightbaseid_equal", flightBase.getId());
//            List<FlightLoadData> flightLoadDatas = dataHandler.findBy(
//                    listFlightLoadData, filters);
//            long passengerInternal = 0, childInternal = 0, babyInternal = 0, passengerInternational = 0, childInternational = 0, babyInternational = 0, viaAdult = 0, viaChild = 0, viaBaby = 0, airMailWeightInternal = 0, airMailWeightInternational = 0, airCargoWeightInternal = 0, airCargoWeightInternational = 0, luggageWeightInternal = 0, luggageWeightInternational = 0;
//            if (flightLoadDatas != null && flightLoadDatas.size() > 0) {
//                // 航线
//                flightMateInfo.setFlightRoute(flightLoadDatas.get(0).getHx());
//                // 航段
//                for (int i = 0; i < flightLoadDatas.size(); i++) {
//                    FlightLoadData flightLoadData = flightLoadDatas.get(0);
//                    if (i == 0) {
//                        flightMateInfo.setFlightPart0(flightLoadData.getHd());
//                    } else if (i == 1) {
//                        flightMateInfo.setFlightPart1(flightLoadData.getHd());
//                    } else if (i == 2) {
//                        flightMateInfo.setFlightPart2(flightLoadData.getHd());
//                    }
//                    if (!"".equals(flightLoadData.getHdfl())
//                            && !flightLoadData.getHdfl().equals("DOM")) {
//                        passengerInternal = passengerInternal
//                                + flightLoadData.getCr();
//                        childInternal = childInternal + flightLoadData.getEt();
//                        babyInternal = babyInternal + flightLoadData.getYe();
//                        airMailWeightInternal = airMailWeightInternal
//                                + flightLoadData.getYj();
//                        airCargoWeightInternal = airCargoWeightInternal
//                                + flightLoadData.getHw();
//                        luggageWeightInternal = luggageWeightInternal
//                                + flightLoadData.getXl();
//                        // 国内国际查询标志位0国内 1国外
//                        flightMateInfo.setFlightCountryTypeQuery("1");
//                        // 国内国际收费标志位0国内 1国外
//                        flightMateInfo.setFlightCountryTypeCharge("1");
//                    } else if (!"".equals(flightLoadData.getHdfl())) {
//                        passengerInternational = passengerInternational
//                                + flightLoadData.getCr();
//                        childInternational = childInternational
//                                + flightLoadData.getEt();
//                        babyInternational = babyInternational
//                                + flightLoadData.getYe();
//                        airMailWeightInternational = airMailWeightInternational
//                                + flightLoadData.getYj();
//                        airCargoWeightInternational = airCargoWeightInternational
//                                + flightLoadData.getHw();
//                        luggageWeightInternational = luggageWeightInternational
//                                + flightLoadData.getXl();
//                        if (!"1".equals(flightMateInfo.getFlightCountryTypeQuery())) {
//                            // 国内国际查询标志位 : 0国内;1国外
//                            flightMateInfo.setFlightCountryTypeQuery("0");
//                            // 国内国际收费标志位: 0国内;1国外
//                            flightMateInfo.setFlightCountryTypeCharge("0");
//                        }
//                    }
//                    viaAdult = viaAdult + flightLoadData.getCrwh();
//                    viaChild = viaChild + flightLoadData.getEtwh();
//                    viaBaby = viaBaby + flightLoadData.getYewh();
//                }
//                // 国内旅客数
//                flightMateInfo.setPassengerInternal(passengerInternal);
//                // 国内儿童数
//                flightMateInfo.setChildInternal(childInternal);
//                // 国内婴儿数
//                flightMateInfo.setBabyInternal(babyInternal);
//                // 国内邮件
//                flightMateInfo.setAirMailWeightInternal(airMailWeightInternal);
//                // 国内货物
//                flightMateInfo
//                        .setAirCargoWeightInternal(airCargoWeightInternal);
//                // 国内行李
//                flightMateInfo.setLuggageWeightInternal(luggageWeightInternal);
//                // 国际旅客数
//                flightMateInfo
//                        .setPassengerInternational(passengerInternational);
//                // 国际儿童数
//                flightMateInfo.setChildInternational(childInternational);
//                // 国际婴儿数
//                flightMateInfo.setBabyInternational(babyInternational);
//                // 国际邮件
//                flightMateInfo
//                        .setAirMailWeightInternational(airMailWeightInternational);
//                // 国际货物
//                flightMateInfo
//                        .setAirCargoWeightInternational(airCargoWeightInternational);
//                // 国际行李
//                flightMateInfo
//                        .setLuggageWeightInternational(luggageWeightInternational);
//                // 过站旅客
//                flightMateInfo.setViaAdult(viaAdult);
//                // 过站儿童
//                flightMateInfo.setViaChild(viaChild);
//                // 过站婴儿
//                flightMateInfo.setViaBaby(viaBaby);
//                // 前面AircraftAirlineAlteration中已设置
//                // //飞机最大业载
//                // flightMateInfo.setAircraftPayload(flightLoadDatas.get(0).getZdyz());
//                // //飞机座位数
//                // flightMateInfo.setAircraftSeatCapacity(flightLoadDatas.get(0).getZdzw());
//            }else{
//                flightMateInfo.setVerifyDescription("no FlightLoadData");
//            }
//        }else{
//            flightMateInfo.setVerifyDescription("no FlightLoadData");
//        }
//            
//        return flightMateInfo;
//	    
//	}
	
	
	/**
	 * add by wangsq 20151129
	 * 优化计费流程
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Object [] updateChargeTerm(List<FlightMateInfo> flightMateInfos) {
		
		//通过base_airline 表 补全所有chargeTarget
		int count = chargeInfoDao.fullChargeTarget();
//		System.out.println(" 新增 " + count + " 条 收费对象记录") ;
		
		//RETURN : 收费条目结果集
        List<ChargeTermVo> terms = new ArrayList<ChargeTermVo>();
		
		//查询所有配置或没有默认的 收费对象\收费项目;
		List<ChargeInfoVo> chargeInfos = chargeInfoDao.queryChargeInfo();
		//查询所有的收费项目ID
		
		List<ChargeSubject> subjects = chargeSubjectManager.findAll();

		//如没有任何数据则流程结束
		if(chargeInfos.isEmpty() || subjects.isEmpty()){
			return new Object []{terms,0,0,""};
		}
		
		//把上面的结果封装成Map数据
		Map<Long,ChargeInfoVo> chargeInfoMap = new HashMap<Long,ChargeInfoVo>();
 		for(ChargeInfoVo c : chargeInfos){
 			chargeInfoMap.put(c.getSubairlineId(), c);
 		}
 		
		Map<Long,ChargeSubject> subjectMap = new HashMap<Long,ChargeSubject>();
		List<Long> commonSubjectIdList = new ArrayList<Long>();
		for(ChargeSubject subject : subjects){
			subjectMap.put(subject.getId(), subject);
			if(subject != null && "0".equals(subject.getSpecialFlag())){
				commonSubjectIdList.add(subject.getId());
			}
		}

		
		Long [] allCommonSubjectIds = commonSubjectIdList.toArray(new Long [commonSubjectIdList.size()]);
		
		//记录计算成功和失败的 flight_mate_info id
		HashSet<Long> successIds = new HashSet<Long>();
		HashSet<Long> failedIds = new HashSet<Long>();

		
		//add by wangsq 20160421
		//记录term计算时错误信息
		StringBuffer errSb = new StringBuffer();
		int zeroNum=0;
 		//遍历航班信息数据进行计算
		
		Date createTime = DateUtils.Date2Date(new Date());
			
 		for(FlightMateInfo fm : flightMateInfos){
            ChargeUtils chargeUtils = new ChargeUtils(fm);
            
            //计费对象ID
            Long subairlineId = fm.getAirLine() == null ? null : fm.getAirLine().getId();

            ChargeInfoVo chargeInfo = chargeInfoMap.get(subairlineId);
            if(chargeInfo == null){
            	//没有该航班的收费对象时直接跳过
            	continue;
            }

 			Long [] subjectIds = (chargeInfo.getSubjectId() == null || chargeInfo.getSubjectId().trim().length() == 0 ) ? 
 					allCommonSubjectIds : (Long[]) ConvertUtils.convert(chargeInfo.getSubjectId().split(","), Long.class);

	
			// 遍历收费项目 			
 			for (Long subjectId : subjectIds) {
 				ChargeSubject subject = subjectMap.get(subjectId);
 				if (subject == null) {
 					continue;
 				}
 				
 	 			try{
	 				ChargeTermVo term = calculateCharge(chargeInfo.getTargetId(), fm, subject,createTime,chargeUtils);
	 				if (term != null) {
	 					if(term.getFee()==0){
	 						zeroNum++;
	 					}
	 					terms.add(term);
	 				}
 	 			}catch(Exception e){
 	 			    e.printStackTrace();
 	 				failedIds.add(fm.getId());
 	 				String flightIdentity = "0".equals(fm.getFlightDirection()) ? fm.getAFlightIdentity() : fm.getDFlightIdentity();
 	 				errSb.append("航班号: ").append(flightIdentity).append(" (" + fm.getId() + ")").append(", 收费项目: ").append(subject.getName()).append(" (" + subject.getId() + ")<br/>");
 	 			}
 			}
 			
 			if( ! failedIds.contains(fm.getId())){
 	 			successIds.add(fm.getId());
 			}
 		}
 		
		//add by wangsq 20160421
		//一旦该航班在计算收费项时出现异常,则所有收费项都不进行保存
 		for(int i = terms.size() - 1 ; i >= 0 ; i --){
 			if(failedIds.contains(terms.get(i).getFlightMateInfoId())){
 				terms.remove(i);
 			}
 		}
 		
 		List<Long> flightMateInfoIds =  chargeInfoDao.saveChargeTerms(terms);
 		
 		//处理统后的计收费条目
 		this.dealSyntheticChargeTerms(flightMateInfoIds);
 		
 		//更新flight_mate_info状态位
 		
 		int suc=chargeInfoDao.updateFlightMateInfoStat(new ArrayList<>(successIds), "11");
 		int fail=chargeInfoDao.updateFlightMateInfoStat(new ArrayList<>(failedIds), "10");
 		System.out.println("++++++++++++++++terms.size:"+terms.size()+",fee zero record num="+zeroNum);
 		System.out.println("++++++++++++++++successIds.size:"+successIds.size()+",updateFlightMateInfoStat 11:"+suc);
 		System.out.println("++++++++++++++++failedIds.size:"+failedIds.size()+",updateFlightMateInfoStat 10:"+fail);
 		System.out.println("++++++++++++++++error:"+errSb.toString());
		return new Object [] {terms,successIds.size(),failedIds.size(),errSb.toString()};
	}
	
	/**
	 * 处理统计后的收费条目
	 * 1.计算数据
	 * 2.删除原有数据
	 * 3.保存数据
	 * @param flightMateInfoIds
	 */
	private void dealSyntheticChargeTerms(List<Long> flightMateInfoIds){
 		//按大类统计金额并保存到synthetic_charge_term表中
 		//再次到数据库中查询刚才保存的terms 进行统计
 		
 		List<SyntheticChargeInfoVo> list = chargeInfoDao.getSumChargeTerm(flightMateInfoIds);
 		
 		Map<Long,HashMap<Long,Double>> syntheticMap = new HashMap<Long,HashMap<Long,Double>>();
 		for(SyntheticChargeInfoVo t : list){
 			HashMap<Long,Double> m = syntheticMap.get(t.getFlightMateInfoId());
 			if( m == null ){
 				m = new HashMap<Long,Double>();
 				syntheticMap.put(t.getFlightMateInfoId(), m);
 			}
 			m.put(t.getChargeTypeId(), t.getFee());
 		}
 		
 		
 		List<SyntheticChargeTerm> terms = new ArrayList<SyntheticChargeTerm>();
 		for(Iterator<Long> it = syntheticMap.keySet().iterator() ; it.hasNext() ;){
 			Long key = it.next();
 			Map<Long,Double> m = syntheticMap.get(key);
 			if(m == null){
 				continue;
 			}
 			
 			SyntheticChargeTerm term = new SyntheticChargeTerm();
 			term.setAlightingAndTakeOffFee(nullToZero(m.get(1L)));
 			term.setParkingFee(nullToZero(m.get(2L)));
 			term.setBridgeFee(nullToZero(m.get(3L)));
 			term.setPassengerServiceFee(nullToZero(m.get(4L)));
 			term.setSecurityCheckFee(nullToZero(m.get(5L)) + nullToZero(m.get(6L)));
 			term.setBookingOfficeFee(nullToZero(m.get(9L)));
 			term.setCheck_inCounterFee(nullToZero(m.get(10L)));
 			term.setGenerallyAgentFee(nullToZero(m.get(11L)));
 			term.setStowageCommunicationFee(nullToZero(m.get(12L)));
 			term.setWaresAndPostFee(nullToZero(m.get(13L)));
 			term.setTransportationServiceFee(nullToZero(m.get(14L)));
 			term.setFlightServiceFee(nullToZero(m.get(15L)));
 			term.setFlightDutyFee(nullToZero(m.get(16L)));
 			term.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
 			
 			FlightMateInfo f = new FlightMateInfo();
 			f.setId(key);
 			
 			term.setFlightMateInfo(f);
 			
 			terms.add(term);
 		}
 		//删除原有的统计金额数据
 		chargeInfoDao.deleteSyntheticChargeTerm(flightMateInfoIds);
 		//保存
 		chargeInfoDao.saveSyntheticChargeTerm(terms);
 		
	}

	private Double nullToZero(Double d){
		return d == null ? 0 : d;
	}
	
	
	@Override
    public List<ChargeTerm> updateChargeTermAccordingToFlightMate(List<FlightMateInfo> flightMateInfos) {
        List<ChargeTerm> chargeTerms = new ArrayList<>();
        Map<String, Object> filters = new HashMap<>();  
        filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
        List<ChargeTarget> listchargeTarget = chargeTargetManager.findBy(filters);
//        List<SyntheticChargeTerm> syntheticChargeTerms = syntheticChargeTermManager.findBy(filters);
        for (FlightMateInfo flightMateInfo : flightMateInfos) {
            List<ChargeTerm> tempChargeTerms = updateChargeTermAccordingToFlightMate(flightMateInfo,listchargeTarget);
            if (tempChargeTerms != null) {
                chargeTerms.addAll(tempChargeTerms);
            }//else
//                return null;
        }
        return chargeTerms;
    }
	
	private List<ChargeTerm> updateChargeTermAccordingToFlightMate(FlightMateInfo flightMateInfo,List<ChargeTarget> chargeTargets){
        Map<String, Object> filters = new HashMap<>();  
        DataHandler<ChargeTarget> dataHandlerChargeTarget = new DataHandler<>();
        filters.put("subairline.id_equal", flightMateInfo.getAirLine().getId());            
        List<ChargeTarget> listChargeTarget = dataHandlerChargeTarget.findBy(chargeTargets,filters);
            if (listChargeTarget == null || listChargeTarget.size()  <= 0) {
                return null;
            }

            List<ChargeTerm> chargeTerms = new ArrayList<>();
            Map<String, Object> remove_filters = new HashMap<>();
            remove_filters.put("flightMateInfo_equal", flightMateInfo);
            remove_filters.put("chargeTarget_equal", listChargeTarget.get(0));
            remove_filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
            chargeTerms = chargeTermManager.findBy(remove_filters);
            chargeTermManager.remove(chargeTerms);
            chargeTerms.clear();
            ChargeUtils chargeUtils = new ChargeUtils(flightMateInfo);
            for (ChargeSubject chargeSubject : listChargeTarget.get(0).getChargeSubjects()) {
                ChargeTerm chargeTerm;
                chargeTerm = calculateCharge(listChargeTarget.get(0), flightMateInfo, chargeSubject, chargeUtils);
                if (chargeTerm != null) {
                    chargeTerms.add(chargeTerm);
                }
            }
//        }
        //查询出指定航班配对的收费账目进行新增或者update
        Map<String, Object> syntheticChargeTerm_filters = new HashMap<>();
        syntheticChargeTerm_filters.put("flightMateInfo_equal", flightMateInfo);
        remove_filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
        List<SyntheticChargeTerm> syntheticChargeTerms = syntheticChargeTermManager.findBy(syntheticChargeTerm_filters);
        SyntheticChargeTerm syntheticChargeTerm;
        if (syntheticChargeTerms != null && syntheticChargeTerms.size() > 0) {
            
            syntheticChargeTerm = syntheticChargeTerms.get(0);
        }else
            syntheticChargeTerm = new SyntheticChargeTerm();
        syntheticChargeTerm = filledSyntheticChargeTerm(syntheticChargeTerm, chargeTerms, flightMateInfo);
        syntheticChargeTermManager.save(syntheticChargeTerm);
        return chargeTerms;
    }
    private SyntheticChargeTerm filledSyntheticChargeTerm(SyntheticChargeTerm syntheticChargeTerm,List<ChargeTerm> chargeTerms, FlightMateInfo flightMateInfo) {
        syntheticChargeTerm.setFlightMateInfo(flightMateInfo);
        syntheticChargeTerm.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
        syntheticChargeTerm.setParkingFee(0.0);
        syntheticChargeTerm.setAlightingAndTakeOffFee(0.0);
        syntheticChargeTerm.setBookingOfficeFee(0.0);
        syntheticChargeTerm.setBridgeFee(0.0);
        syntheticChargeTerm.setCheck_inCounterFee(0.0);
        syntheticChargeTerm.setFlightDutyFee(0.0);
        syntheticChargeTerm.setFlightServiceFee(0.0);
        syntheticChargeTerm.setGenerallyAgentFee(0.0);
        syntheticChargeTerm.setPassengerServiceFee(0.0);
        syntheticChargeTerm.setSecurityCheckFee(0.0);
        syntheticChargeTerm.setTransportationServiceFee(0.0);
        syntheticChargeTerm.setWaresAndPostFee(0.0);
        syntheticChargeTerm.setStowageCommunicationFee(0.0);
        //通过判断向收费账目表中插入航班配对计算数据
        for (ChargeTerm chargeTerm : chargeTerms) {
            if (chargeTerm.getChargeType().getId().equals(1L)) {
                syntheticChargeTerm.setAlightingAndTakeOffFee(syntheticChargeTerm.getAlightingAndTakeOffFee() + chargeTerm.getFee());
            }else if (chargeTerm.getChargeType().getId().equals(2L)) {
                syntheticChargeTerm.setParkingFee(syntheticChargeTerm.getParkingFee() + chargeTerm.getFee());
            }else if(chargeTerm.getChargeType().getId().equals(3L)){
                syntheticChargeTerm.setBridgeFee(syntheticChargeTerm.getBridgeFee() + chargeTerm.getFee());
            }else if(chargeTerm.getChargeType().getId().equals(4L)){
                syntheticChargeTerm.setPassengerServiceFee(syntheticChargeTerm.getPassengerServiceFee() + chargeTerm.getFee());
            }else if(chargeTerm.getChargeType().getId().equals(5L) || chargeTerm.getChargeType().getId().equals(6L)){
                syntheticChargeTerm.setSecurityCheckFee(syntheticChargeTerm.getSecurityCheckFee() + chargeTerm.getFee());
            }else if(chargeTerm.getChargeType().getId().equals(9L)){
                syntheticChargeTerm.setBookingOfficeFee(syntheticChargeTerm.getBookingOfficeFee() + chargeTerm.getFee());
            }else if(chargeTerm.getChargeType().getId().equals(10L)){
                syntheticChargeTerm.setCheck_inCounterFee(syntheticChargeTerm.getCheck_inCounterFee() + chargeTerm.getFee());
            }else if(chargeTerm.getChargeType().getId().equals(11L)){
                syntheticChargeTerm.setGenerallyAgentFee(syntheticChargeTerm.getGenerallyAgentFee() + chargeTerm.getFee());
            }else if(chargeTerm.getChargeType().getId().equals(12L)){
                syntheticChargeTerm.setStowageCommunicationFee(syntheticChargeTerm.getStowageCommunicationFee() + chargeTerm.getFee());
            }else if(chargeTerm.getChargeType().getId().equals(13L)){
                syntheticChargeTerm.setWaresAndPostFee(syntheticChargeTerm.getWaresAndPostFee() + chargeTerm.getFee());
            }else if(chargeTerm.getChargeType().getId().equals(14L)){
                syntheticChargeTerm.setTransportationServiceFee(syntheticChargeTerm.getTransportationServiceFee() + chargeTerm.getFee());
            }else if(chargeTerm.getChargeType().getId().equals(15L)){
                syntheticChargeTerm.setFlightServiceFee(syntheticChargeTerm.getFlightServiceFee() + chargeTerm.getFee());
            }else if(chargeTerm.getChargeType().getId().equals(16L)){
                syntheticChargeTerm.setFlightDutyFee(syntheticChargeTerm.getFlightDutyFee() + chargeTerm.getFee());
            }
        }
       return syntheticChargeTerm;
    }
    
    /**
     * add by wangsq 20151129
     * @param chargeTargetId
     * @param flightMateInfo
     * @param chargeSubject
     * @param chargeUtils
     */
    private ChargeTermVo calculateCharge(Long chargeTargetId , FlightMateInfo flightMateInfo, ChargeSubject chargeSubject,Date createTime,ChargeUtils chargeUtils) {
		ChargeTermVo term = null;
		 
		if (chargeUtils.isChargeSubjectMatch(chargeSubject)) {
		    System.out.println("---------ChargeSubject.id=" + chargeSubject.getId() +","+ chargeSubject.getChargeType().getName() + "," + chargeSubject.getName() +"-命中-------------------");
			term = new ChargeTermVo();
			// 收费项目规则匹配上的表达式
			String expression = chargeUtils.showExpression(chargeSubject);
			// 费用
			StringBuffer exp = new StringBuffer(expression);
			Double fee = chargeUtils.getSubjectCharge(chargeSubject, exp);
			
			// 对于的收费项目的折扣费用
			Double discountFee = chargeUtils.getDiscount(chargeSubject, fee, exp);
			expression = exp.toString();
			System.out.println(expression);
			// debug版 显示满足的表达式
			// chargeTerm = chargeTermManager.addChargeTerm(chargeTarget,
			// flightMateInfo, chargeSubject, discountFee, expression);

			if (discountFee == null) {
				discountFee = 0.00;
			}
			
			//处理系数
            Double feeCoe=1.0;
            if(flightMateInfo.getFlightDirection().equals("0")){
                feeCoe=chargeSubject.getaFeeCoe();
            }else if(flightMateInfo.getFlightDirection().equals("1")){
                feeCoe=chargeSubject.getdFeeCoe();
            }
            discountFee=discountFee*feeCoe;

			term.setChargeSubjectId(chargeSubject.getId());
			term.setChargeTargetId(chargeTargetId);
			term.setChargeTypeId(chargeSubject.getChargeType().getId());
			term.setFlightMateInfoId(flightMateInfo.getId());
			term.setCurrency(chargeSubject.getCurrency());
			term.setFee(discountFee);
			term.setRemark(expression);
			term.setName((flightMateInfo.getFlightDirection().equals("0") ? flightMateInfo.getAFlightIdentity() + "_A"
					: flightMateInfo.getDFlightIdentity() + "_D") + "_"
					+ DateUtils.Date2String(flightMateInfo.getExecuteTime(), "yyyy-MM-dd") + "_"
					+ chargeSubject.getChargeType().getName() + "_" + chargeSubject.getName());
			term.setCreateUser("admin");
			term.setCreateTime(createTime);
			term.setCurrencyUnit(chargeSubject.getCurrencyUnit());
			term.setRemoveFlag("1");

		}else{
		    System.out.println("----------------------ChargeSubject.id=" + chargeSubject.getId() + "------未命中-----");
		}

		return term;
	}
    
    
    
    
    private ChargeTerm calculateCharge(ChargeTarget chargeTarget,FlightMateInfo flightMateInfo, ChargeSubject chargeSubject,ChargeUtils chargeUtils) {
        List<FlightMateInfo> flightInfos = new ArrayList<>();
        ChargeTerm chargeTerm = null;
        
//        Map<String, Object> filters = new HashMap<>();
//        filters.put("flightMateInfo_equal", flightMateInfo);
//        flightInfos = flightMateInfoManager.findBy(filters);
//        if (flightInfos != null && flightInfos.size() > 0) {
            
            
            if (chargeUtils.isChargeSubjectMatch(chargeSubject)) {
              //收费项目规则匹配上的表达式
                String expression = chargeUtils.showExpression(chargeSubject);
                //费用
                StringBuffer exp= new StringBuffer(expression);
                Double fee = chargeUtils.getSubjectCharge(chargeSubject,exp);
                
                //对于的收费项目的折扣费用
                Double discountFee = chargeUtils.getDiscount(chargeSubject, fee, exp);
                expression=exp.toString();
                
//              chargeTerm = chargeTermManager.addChargeTerm(chargeTarget, flightMate, chargeSubject, discountFee);
                //debug版 显示满足的表达式
                chargeTerm = chargeTermManager.addChargeTerm(chargeTarget, flightMateInfo, chargeSubject, discountFee, expression);
            }
//          else {
//              chargeTerm = chargeTermManager.addChargeTerm(chargeTarget, flightMateInfo, chargeSubject, null);
//          }
//        }
        return chargeTerm;
    }
    
    
    @Override
    public Map<String, Object> importFlightMateInfoByExcel2003(byte[] bytes) throws IOException {
        @SuppressWarnings("unused")
        List<FlightMateInfo> flightMateInfoAllList = new ArrayList<>();
        List<FlightMateInfo> flightMateInfoErrorList = new ArrayList<>();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        @SuppressWarnings("rawtypes")
        Map<String, Object> result = importFlightMateInfoByExcel
                .ImportFlightMateInfoByExcel2003(inputStream);
        return saveFlightMateInfoData(result);
    }
   
    @Override
    public Map<String, Object> importFlightMateInfoByExcel2007(byte[] bytes) throws IOException {
        @SuppressWarnings("unused")
        List<FlightMateInfo> flightMateInfoAllList = new ArrayList<>();
        List<FlightMateInfo> flightMateInfoErrorList = new ArrayList<>();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        @SuppressWarnings("rawtypes")
        Map<String, Object> result = importFlightMateInfoByExcel
                .ImportFlightMateInfoByExcel2007(inputStream);
        return saveFlightMateInfoData(result);
    }
    
    private Map<String, Object> saveFlightMateInfoData(
            Map<String, Object> result) {
        int addCounter = 0, modifyCounter = 0;
        List<FlightMateInfo> flightMateInfoAllList;
        List<FlightMateInfo> flightMateInfoErrorList;
        List<FlightMateInfo> resultlist = (List<FlightMateInfo>) result
                .get("result");
        flightMateInfoAllList = (List<FlightMateInfo>) resultlist.get(0);
        flightMateInfoErrorList = (List<FlightMateInfo>) resultlist.get(1);
        if (flightMateInfoErrorList.size() == 0) {
            List<Long> ids = new ArrayList<>();
            for (FlightMateInfo flightMateInfo : flightMateInfoAllList) {
                this.save(flightMateInfo);
            }
        }

        Map<String, Object> flightMateInfoResult = new HashMap<>();
        flightMateInfoResult.put("list", flightMateInfoAllList);
//        flightMateInfoResult.put("errorList", flightMateInfoErrorList);
        flightMateInfoResult.put("errorMessage", result.get("resultMessage"));
        return flightMateInfoResult;
    }

    //导入舱单数据
//    @Override
    public Map<String, Object> importFlightLoadDataByExcel2003(byte[] bytes,String fileName)
            throws IOException {
        @SuppressWarnings("unused")
        List<FlightLoadData> flightLoadDataAllList = new ArrayList<>();
        List<FlightLoadData> flightLoadDataErrorList = new ArrayList<>();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        @SuppressWarnings("rawtypes")
        Map<String, Object> result = importFlightLoadDataByExcel
                .ImportFlightLoadDataByExcel2003(inputStream);
        result.put("fileName", fileName);
        return saveFlightLoadData2FlightMateInfo(result);
    }

   

//    @Override
    public Map<String, Object> importFlightLoadDataByExcel2007(byte[] bytes,String fileName)
            throws IOException {
        List<FlightLoadData> flightLoadDataAllList = new ArrayList<>();
        List<FlightLoadData> flightLoadDataErrorList = new ArrayList<>();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        @SuppressWarnings("rawtypes")
        Map<String, Object> result = importFlightLoadDataByExcel
                .ImportFlightLoadDataByExcel2007(inputStream);
        result.put("fileName", fileName);
        return saveFlightLoadData2FlightMateInfo(result);
    }
    
    //保存FlightLoadData
    private Map<String, Object> saveFlightLoadData(Map<String, Object> result){
        Map<String, Object> retResult = new HashMap<>();
        int addCounter = 0, modifyCounter = 0;
        int columnsCount=(int) result.get("columnsCount");
        List<FlightLoadData> flightLoadDataAllList;
        List<FlightLoadData> flightLoadDataErrorList;
        List<FlightLoadData> flightLoadDataNotFoundList =new ArrayList<FlightLoadData>();
        List<FlightLoadData> flightLoadSaveList=new ArrayList<FlightLoadData>();
        List<FlightLoadData> resultlist = (List<FlightLoadData>) result.get("result");
        flightLoadDataAllList = (List<FlightLoadData>) resultlist.get(0);
        flightLoadDataErrorList = (List<FlightLoadData>) resultlist.get(1);
        String  globalError=(String) result.get("resultMessage");
        Date startDate=DateUtils.getZeroOfOneday((Date)result.get("minDate"));
        Date endDate=DateUtils.getLatestTimeOfOneday((Date)result.get("maxDate"));
        if (flightLoadDataErrorList.size() == 0 && globalError.isEmpty()) {
            
            //读取FlightLoadData in DB 
            Map<String, Object> filters = new HashMap<>();
            filters.put("jhrq_greaterThanOrEqualTo", startDate);
            filters.put("jhrq_lessThanOrEqualTo", endDate);

            List<FlightLoadData> fldInDb = flightLoadDataManager.findBy(filters);

			HashMap<String,List<FlightLoadData>> hmKeyFldList= new HashMap<String,List<FlightLoadData>>();
			for(FlightLoadData fld:fldInDb){
				if(fld.getSjrq()==null||fld.getCyr()==null||fld.getHbh()==null||fld.getIo()==null||fld.getJh()==null||fld.getHd()==null){
					continue;
				}
				String key=DateUtils.Date2String(fld.getJhrq(),"MM/dd/yy")
						.concat("-").concat(fld.getCyr()).concat(fld.getHbh())
						.concat("-").concat(fld.getIo())
						.concat("-").concat(fld.getJh())
						.concat("-").concat(fld.getHd());
				if(hmKeyFldList.containsKey(key)){
					hmKeyFldList.get(key).add(fld);
				}else{
					List<FlightLoadData> flds=new ArrayList<FlightLoadData>();
					flds.add(fld);
					hmKeyFldList.put(key, flds);
				}
			}

            int row=2;
            //String pbm=DateUtils.Date2String(new Date(), "MM/dd/yyHH:mm:ss");
            for (FlightLoadData flightLoadData : flightLoadDataAllList) {
            	/*
                DataHandler<FlightLoadData> dataHandler = new DataHandler<>();
                filters.clear();
                filters.put("io_equal", flightLoadData.getIo());
                filters.put("cyr_equal", flightLoadData.getCyr());
                filters.put("hbh_equal", flightLoadData.getHbh());
                filters.put("jh_equal", flightLoadData.getJh());
                filters.put("sjrq2s_equal", DateUtils.Date2String(flightLoadData.getSjrq(),"MM/dd/yy") );
//                filters.put("qjsj_equal",flightLoadData.getQjsj());
                filters.put("hd_equal", flightLoadData.getHd());
                 //List<FlightLoadData> listFlightLoadData = dataHandler.findBy(fldInDb, filters);
                */
            	
            	String key=DateUtils.Date2String(flightLoadData.getJhrq(),"MM/dd/yy")
						.concat("-").concat(flightLoadData.getCyr()).concat(flightLoadData.getHbh())
						.concat("-").concat(flightLoadData.getIo())
						.concat("-").concat(flightLoadData.getJh())
						.concat("-").concat(flightLoadData.getHd());

				List<FlightLoadData> listFlightLoadData = hmKeyFldList.get(key);
				
                if (listFlightLoadData!=null && listFlightLoadData.size() == 1) {
                    FlightLoadData flightLoadDatatmp = listFlightLoadData.get(0);
                    /*
                    flightLoadData.setJqjd(flightLoadDatatmp.getJqjd());
                    flightLoadData.setPbm(pbm);
                    flightLoadData.setId(flightLoadDatatmp.getId());
                    flightLoadData.setJhrq(flightLoadDatatmp.getJhrq());
                    flightLoadData.setDm(flightLoadDatatmp.getDm());
                    flightLoadData.setSs(flightLoadDatatmp.getSs());
                    flightLoadData.setHx0(flightLoadDatatmp.getHx0());
                    flightLoadData.setJhrq(flightLoadDatatmp.getJhrq());
                    flightLoadData.setQjsj(flightLoadDatatmp.getQjsj());
                    flightLoadData.setOperationstatus(flightLoadDatatmp.getOperationstatus());
                    flightLoadData.setSjrq(flightLoadDatatmp.getSjrq());
                    flightLoadData.setFlightbaseid(flightLoadDatatmp.getFlightbaseid());
                    flightLoadData.setFlightdataid(flightLoadDatatmp.getFlightdataid());
                    flightLoadData.setFlightIdentity(flightLoadDatatmp.getFlightIdentity());
                    flightLoadData.setFlightstatus(flightLoadDatatmp.getFlightstatus());
                    flightLoadData.setGenerateMethod("2");
                    flightLoadData.setLegNo(flightLoadDatatmp.getLegNo());
                    */
                    flightLoadDatatmp.setCr(flightLoadData.getCr());
                    flightLoadDatatmp.setEt(flightLoadData.getEt());
                    flightLoadDatatmp.setYe(flightLoadData.getYe());
                    flightLoadDatatmp.setCrwh(flightLoadData.getCrwh());
                    flightLoadDatatmp.setEtwh(flightLoadData.getEtwh());
                    flightLoadDatatmp.setYewh(flightLoadData.getYewh());
                    flightLoadDatatmp.setXl(flightLoadData.getXl());
                    flightLoadDatatmp.setYj(flightLoadData.getYj());
                    flightLoadDatatmp.setHw(flightLoadData.getHw());
                    flightLoadDatatmp.setGenerateMethod("2");
                    if(flightLoadData.getWjhz()!=null){
                    	flightLoadDatatmp.setWjhz(flightLoadData.getWjhz());
                    }
                    if(flightLoadData.getXljs()!=null){
                    	flightLoadDatatmp.setXljs(flightLoadData.getXljs());
                    }
                    flightLoadSaveList.add(flightLoadDatatmp);
                    
                    
                    
                }else{//没有找到 listFlightLoadData
                    flightLoadData.setVerifyDescription("第"+row+"行没有找到对应信息！");
                    Map<Integer, ArrayList<Integer>> errorMessage = new HashMap<Integer, ArrayList<Integer>>();
                    ArrayList<Integer> errorCells = new ArrayList<>();
                    errorCells.add(1);
                    errorMessage.put(row, errorCells);
                    flightLoadData.setErrorMessage(errorMessage);
                    flightLoadDataNotFoundList.add(flightLoadData);
                }
                row++;
            }

			
            if(flightLoadSaveList.size()>0){
                flightLoadSaveList=(List<FlightLoadData>) flightLoadDataManager.save(flightLoadSaveList);
                retResult.put("flightLoadData", flightLoadSaveList);
            }
        }
       
        retResult.put("minDate", startDate);
        retResult.put("maxDate", endDate);
       
        retResult.put("totalCount", flightLoadDataAllList.size()+flightLoadDataErrorList.size()+"");//总数
        retResult.put("successCount", flightLoadSaveList.size() + "");//成功
        List<String> noFoundlist=new ArrayList<String>();
        
        for(FlightLoadData flightLoadData:flightLoadDataNotFoundList){
            noFoundlist.add(flightLoadData.getVerifyDescription());
            ArrayList<Integer> errorCells = new ArrayList<>();
            errorCells.add(columnsCount);
            Map<Integer, ArrayList<Integer>> noFoundErrorMessage = new HashMap<Integer, ArrayList<Integer>>();
            noFoundErrorMessage.put(flightLoadData.getRowNumber(), errorCells);
            flightLoadData.setErrorMessage(noFoundErrorMessage);
        }
        retResult.put("noFound", noFoundlist); //没找到
        retResult.put("noFoundList", flightLoadDataNotFoundList); //没找到
        
        List<String> errorlist=new ArrayList<String>();
        for(FlightLoadData flightLoadData:flightLoadDataErrorList){
            errorlist.add(flightLoadData.getVerifyDescription());
        }
        if (!globalError.isEmpty()) {
            retResult.put("globalError", globalError);// 列数量或格式不正确
        }
        retResult.put("error",errorlist);//错误
        
        // 遍历航班list，生成报错坐标errorMessage
        List<FlightLoadData> flightLoadDataAllErrorList=new ArrayList<>();
        flightLoadDataAllErrorList.addAll(flightLoadDataErrorList);
        flightLoadDataAllErrorList.addAll(flightLoadDataNotFoundList);
        Map<Integer, List<Integer>> errorMessage = new HashMap<>();
        for (FlightLoadData flightLoadData : flightLoadDataAllErrorList) {
            if (flightLoadData.getErrorMessage() != null) {
                errorMessage.putAll(flightLoadData.getErrorMessage());
            }
        }
        String fileName=(String) result.get("fileName");
        String retFile="";
        try {
            if (fileName.endsWith(".xls")) {
                retFile=ExcelOperatorUtils.changeExcel2003BackgroundColor(fileName,errorMessage);
            } else if (fileName.endsWith(".xlsx")) {
                retFile=ExcelOperatorUtils.changeExcel2007BackgroundColor(fileName,errorMessage);
            }
            if(errorMessage.size()>0){
                retResult.put("File", retFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return retResult;
    }

    private Map<String, Object> saveFlightLoadData2FlightMateInfo(List<FlightMateInfo> fmis, List<FlightLoadData> flightLoadDataAllList) {
        Map<Long, FlightMateInfo> fmiMap = new HashMap();
        List<FlightLoadData> flightLoadDataNotFoundList = new ArrayList<FlightLoadData>();
        List<String> noFound=new ArrayList<String>();
        boolean isFmiInfmiMap = false;
        int row = 2;
        Map<String, Object> filters = new HashMap<>();
        DataHandler<FlightMateInfo> dataHandler = new DataHandler<>();
        for (FlightLoadData flightLoadData : flightLoadDataAllList) {
            isFmiInfmiMap = false;
            filters.clear();
            filters.put("flightBaseId_equal", flightLoadData.getFlightbaseid());
            List<FlightMateInfo> listFlightMateInfo = dataHandler.findBy(fmis, filters);
            if (listFlightMateInfo.size() == 1) {
                FlightMateInfo flightMateInfo = listFlightMateInfo.get(0);
                FlightMateInfo flightMateInfoInMap = fmiMap.get(flightMateInfo.getId());
                if (flightMateInfoInMap == null) {
                    fmiMap.put(flightMateInfo.getId(), flightMateInfo);
                } else {
                    isFmiInfmiMap = true;
                }
                flightMateInfo.setFlightRoute(flightLoadData.getHx());
                if(null!=flightLoadData.getLegNo()&&flightLoadData.getLegNo().equals("0") ){
                    flightMateInfo.setFlightPart0(flightLoadData.getHd());
                }else if(null!=flightLoadData.getLegNo()&&flightLoadData.getLegNo().equals("1") ){
                    flightMateInfo.setFlightPart1(flightLoadData.getHd());
                }else if(null!=flightLoadData.getLegNo()&&flightLoadData.getLegNo().equals("2") ){
                    flightMateInfo.setFlightPart2(flightLoadData.getHd());
                }
                if (!"".equals(flightLoadData.getHdfl()) && (flightLoadData.getHdfl().equals("DOM") || flightLoadData.getHdfl().equals("D"))) {
                    double passengerInternal = 0, childInternal = 0, babyInternal = 0, airMailWeightInternal = 0, 
                            luggageWeightInternal = 0, airCargoWeightInternal = 0,luggageNumInternal=0;
                    if (isFmiInfmiMap) {// 在MAP里面，取出旧值
                        passengerInternal = 0 + flightMateInfo.getPassengerInternal();
                        childInternal = 0 + flightMateInfo.getChildInternal();
                        babyInternal = 0 + flightMateInfo.getBabyInternal();
                        airMailWeightInternal = 0 + flightMateInfo.getAirMailWeightInternal();
                        luggageWeightInternal = 0 + flightMateInfo.getLuggageWeightInternal();
                        airCargoWeightInternal = 0 + flightMateInfo.getAirCargoWeightInternal();
                        luggageNumInternal = 0 + flightMateInfo.getLuggageNumInternal();
                    }
                    passengerInternal = passengerInternal + flightLoadData.getCr();
                    childInternal = childInternal + flightLoadData.getEt();
                    babyInternal = babyInternal + flightLoadData.getYe();
                    airMailWeightInternal = airMailWeightInternal + flightLoadData.getYj();
                    airCargoWeightInternal = airCargoWeightInternal + flightLoadData.getHw();
                    luggageWeightInternal = luggageWeightInternal + flightLoadData.getXl();
                    luggageNumInternal=luggageNumInternal+flightLoadData.getXljs();
                    // 国内旅客数
                    flightMateInfo.setPassengerInternal(passengerInternal);
                    // 国内儿童数
                    flightMateInfo.setChildInternal(childInternal);
                    // 国内婴儿数
                    flightMateInfo.setBabyInternal(babyInternal);
                    // 国内邮件重量
                    flightMateInfo.setAirMailWeightInternal(airMailWeightInternal);
                    // 国内货物重量
                    flightMateInfo.setAirCargoWeightInternal(airCargoWeightInternal);
                    // 国内行李重量
                    flightMateInfo.setLuggageWeightInternal(luggageWeightInternal);
                    //国内行李数量
                    flightMateInfo.setLuggageNumInternal(luggageNumInternal);
                    // 国内国际查询标志位0国内 1国外
                    flightMateInfo.setFlightCountryTypeQuery("0");
                    // 国内国际收费标志位0国内 1国外
                    flightMateInfo.setFlightCountryTypeCharge("0");
                } else if (!"".equals(flightLoadData.getHdfl())) {
                    double passengerInternational = 0, childInternational = 0, babyInternational = 0, 
                            airCargoWeightInternational = 0, airMailWeightInternational = 0, luggageWeightInternational = 0,luggageNumInternational=0;
                    if (isFmiInfmiMap) {
                        passengerInternational = 0 + flightMateInfo.getPassengerInternational();
                        childInternational = 0 + flightMateInfo.getChildInternational();
                        babyInternational = 0 + flightMateInfo.getBabyInternational();
                        airCargoWeightInternational = 0 + flightMateInfo.getAirCargoWeightInternational();
                        airMailWeightInternational = 0 + flightMateInfo.getAirMailWeightInternational();
                        luggageWeightInternational = 0 + flightMateInfo.getLuggageWeightInternational();
                        luggageNumInternational = 0 +flightMateInfo.getLuggageNumInternational();
                    }
                    passengerInternational = passengerInternational + flightLoadData.getCr();
                    childInternational = childInternational + flightLoadData.getEt();
                    babyInternational = babyInternational + flightLoadData.getYe();
                    airMailWeightInternational = airMailWeightInternational + flightLoadData.getYj();
                    airCargoWeightInternational = airCargoWeightInternational + flightLoadData.getHw();
                    luggageWeightInternational = luggageWeightInternational + flightLoadData.getXl();
                    luggageNumInternational=luggageNumInternational +flightLoadData.getXljs();
                    // 国际旅客数
                    flightMateInfo.setPassengerInternational(passengerInternational);
                    // 国际儿童数
                    flightMateInfo.setChildInternational(childInternational);
                    // 国际婴儿数
                    flightMateInfo.setBabyInternational(babyInternational);
                    // 国际邮件重量
                    flightMateInfo.setAirMailWeightInternational(airMailWeightInternational);
                    // 国际货物重量
                    flightMateInfo.setAirCargoWeightInternational(airCargoWeightInternational);
                    // 国际行李重量
                    flightMateInfo.setLuggageWeightInternational(luggageWeightInternational);
                    // 国际行李数量
                    flightMateInfo.setLuggageNumInternational(luggageNumInternational);
                    if (!"1".equals(flightMateInfo.getFlightCountryTypeQuery())) {
                        // 国内国际查询标志位 : 0国内;1国外
                        flightMateInfo.setFlightCountryTypeQuery("1");
                        // 国内国际收费标志位: 0国内;1国外
                        flightMateInfo.setFlightCountryTypeCharge("1");
                    }
                }
                double viaAdult = 0, viaChild = 0, viaBaby = 0;
                if (isFmiInfmiMap) {
                    viaAdult = 0 + flightMateInfo.getViaAdult();
                    viaChild = 0 + flightMateInfo.getViaChild();
                    viaBaby = 0 + flightMateInfo.getViaBaby();
                }
                viaAdult = viaAdult + flightLoadData.getCrwh();
                viaChild = viaChild + flightLoadData.getEtwh();
                viaBaby = viaBaby + flightLoadData.getYewh();
                // 过站旅客
                flightMateInfo.setViaAdult(viaAdult);
                // 过站儿童
                flightMateInfo.setViaChild(viaChild);
                // 过站婴儿
                flightMateInfo.setViaBaby(viaBaby);

                // 计算客座率
                double totalpass = 0;
                // 国内旅客数 + 国内儿童数 + 国内婴儿数 + 国际旅客数 + 国际儿童数 + 国际婴儿数
                totalpass = flightMateInfo.getPassengerInternal() 
                        + flightMateInfo.getChildInternal() 
                        + flightMateInfo.getBabyInternal() 
                        + flightMateInfo.getPassengerInternational()
                        + flightMateInfo.getChildInternational() 
                        + flightMateInfo.getBabyInternational();

                double PassengerLoadFactor = 0;
                if (flightMateInfo.getAircraftSeatCapacity() != null && flightMateInfo.getAircraftSeatCapacity() > 0L) {
                    PassengerLoadFactor = ((double) totalpass) / flightMateInfo.getAircraftSeatCapacity();
                }
                flightMateInfo.setPassengerLoadFactor(DateUtils.get2Double(PassengerLoadFactor));

            } else {// 没有找到 flightMateInfo
                flightLoadDataNotFoundList.add(flightLoadData);
                noFound.add("第"+row+"行没有找到对应航班配对信息！");
            }
            row++;
        }
        List<FlightMateInfo> retFlightMateInfolist = new ArrayList<FlightMateInfo>();
        for (long key : fmiMap.keySet()) {
            retFlightMateInfolist.add(fmiMap.get(key));
        }
        this.save(retFlightMateInfolist);

        Map<String, Object> retResult = new HashMap<>();
        retResult.put("totalCount", flightLoadDataAllList.size());// 总数
        retResult.put("successCount", retFlightMateInfolist.size() + "");// 成功
        retResult.put("noFound", noFound);
        return retResult;

    }
    
    
    @Override
    public Map<String, Object> saveFlightLoadData2FlightMateInfoByIds(List<Long> fmiIds) {
        List<FlightMateInfo> fmis=flightMateInfoDao.findByIds(fmiIds);
        List<Long> fbids=new ArrayList<Long>();
        for (FlightMateInfo flightMateInfo : fmis) {
            fbids.add(flightMateInfo.getFlightBaseId());
        }
        List<FlightLoadData> flightLoadDataAllList=flightLoadDataManager.findByFbIds(fbids);
        return saveFlightLoadData2FlightMateInfo(fmis,flightLoadDataAllList);
    }
    
    private Map<String, Object> saveFlightLoadData2FlightMateInfo(Map<String, Object> result) {
        // 先保存FlightLoadData
        Map<String, Object> retResult = saveFlightLoadData(result);

        //lushuaifeng 20160921 去除自动配载的逻辑
        /*
        //处理数据中没有找到FLD的，尝试重新生成
        List<FlightLoadData> flightLoadDataNotFoundList=(List<FlightLoadData>) retResult.get("noFoundList");
        if(null!=flightLoadDataNotFoundList &&flightLoadDataNotFoundList.size()>0 ){
            //find fb
            StringBuffer sql=new StringBuffer();
            sql.append("select * from flight_view t where t.Remove_Flag='1' ");
            sql.append(" and (1<>1 ");
            for (FlightLoadData flightLoadData : flightLoadDataNotFoundList) {
                sql.append("or ( t.FLIGHT_IDENTITY='");
                sql.append(flightLoadData.getCyr());
                sql.append(flightLoadData.getHbh());
                sql.append("' and t.REGISTERATION='");
                sql.append(flightLoadData.getJh());
                sql.append("' and ");
                if(flightLoadData.getIo().equals("I")){
                    sql.append(" t.FLIGHT_DIRECTION='0' and to_char(t.ACTUAL_LANDING_TIME,'yyyy-mm-dd')='");
                    
                }else{
                    sql.append(" t.FLIGHT_DIRECTION='1' and to_char(t.ACTUAL_TAKE_OFF_TIME,'yyyy-mm-dd')='");
                }
                sql.append(DateUtils.Date2String(flightLoadData.getSjrq(),"yyyy-MM-dd"));
//                sql.append(" ");
//                sql.append(flightLoadData.getQjsj());
                sql.append("')");
            }
            sql.append(")");
            List<FlightView> viewList = flightViewDao.findFlightBaseByNativeQuery(sql.toString());
            DataCache a = new DataCache();
            List<FlightBase> bs = a.convertFlightView(viewList);
            //generate fb
            flightLoadDataManager.generate(bs);
            //save again
           retResult = saveFlightLoadData(result);
           retResult.remove("noFoundList");
        }
        */
        retResult.remove("noFoundList");
        /*        
        Date startDate = DateUtils.getZeroOfOneday((Date) retResult.get("minDate"));
        Date endDate = DateUtils.getLatestTimeOfOneday((Date) retResult.get("maxDate"));
       
        String file = (String) retResult.get("File");
        if (null == file || file.isEmpty()) {
            Map<String, Object> filters = new HashMap<>();        
          filters.put("executeTime_greaterThanOrEqualTo", startDate);
          filters.put("executeTime_lessThanOrEqualTo", endDate);
          filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
          List<FlightMateInfo> fmis = this.findBy(filters);
          List<FlightLoadData> flightLoadDataAllList=(List<FlightLoadData>) retResult.get("flightLoadData");
          retResult=saveFlightLoadData2FlightMateInfo(fmis,flightLoadDataAllList);
        }
        */
        List<FlightLoadData> flightLoadDataList=(List<FlightLoadData>) retResult.get("flightLoadData");
        
        saveFlightLoadData2FlightMateInfo(flightLoadDataList);
        
        retResult.remove("flightLoadData");
        return retResult;
    }
    
    private boolean saveFlightLoadData2FlightMateInfo(List<FlightLoadData> flightLoadDataList){
    	
    	if(flightLoadDataList==null ||flightLoadDataList.isEmpty()){
    		System.out.println("No Useful FLD Data Input ");
    		return false;
    	}
    	
        List<Long> ids=new ArrayList<Long>();
         
	    for(FlightLoadData fld:flightLoadDataList){
	    	if(!ids.contains(fld.getFlightbaseid())){
	    		ids.add(fld.getFlightbaseid());         
	    	}
	    }
         
        if(ids.isEmpty()){
        	System.out.println("No Useful FlightBaseId ");
        	return false;
        }
 
    	List<FlightLoadData> flds=flightLoadDataManager.findByFbIds(ids);    	
    	    	
    	if(flds==null || flds.isEmpty()){
    		System.out.println("No FLD Data Be Queried By FlightBaseIds ");
      		return false;
      	}
    	
    	//直接截取2位小数，不四舍五入
    	DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setGroupingSize(0);
        df.setRoundingMode(RoundingMode.FLOOR);          
        
    	HashMap<Long,List<FlightLoadData>> hmFlightBaseIdFlightLoadDatas=new HashMap<Long,List<FlightLoadData>>();
    	for(FlightLoadData fld:flds){
          	
    		if(hmFlightBaseIdFlightLoadDatas.containsKey(fld.getFlightbaseid())){
    			hmFlightBaseIdFlightLoadDatas.get(fld.getFlightbaseid()).add(fld);
    		}else{
    			List<FlightLoadData> values=new ArrayList<FlightLoadData>();
    			values.add(fld);
    			hmFlightBaseIdFlightLoadDatas.put(fld.getFlightbaseid(), values);
    		}
        }
    	
     	List<FlightMateInfo> fmis=flightMateInfoDao.findByFlightBases(ids, null);
    	
     	if(fmis==null || fmis.isEmpty()){
     		System.out.println("No FMI Data Be Queried By FlightBaseIds ");
     		return false;
     	}
     	try{
	     	for(FlightMateInfo fmi:fmis){
	     		//国内旅客
	            double passengerInternal = 0, childInternal = 0, babyInternal = 0, airMailWeightInternal = 0, 
	                    luggageWeightInternal = 0, airCargoWeightInternal = 0,luggageNumInternal=0;
	            //国外旅客
	            double passengerInternational = 0, childInternational = 0, babyInternational = 0, 
	                    airCargoWeightInternational = 0, airMailWeightInternational = 0, luggageWeightInternational = 0,luggageNumInternational=0;
	            double viaAdult = 0, viaChild = 0, viaBaby = 0;
	            double totalpass = 0;
	            double passengerLoadFactor = 0;
	            // 国内国际查询标志位 : 0国内;1国外
	            String flightCountryTypeQuery="0";
	            // 国内国际收费标志位: 0国内;1国外
	            String flightCountryTypeCharge="0";
	                        
	     		if(hmFlightBaseIdFlightLoadDatas.containsKey(fmi.getFlightBaseId())){
	     			
	     			List<FlightLoadData> list=hmFlightBaseIdFlightLoadDatas.get(fmi.getFlightBaseId());
	     			
	     			for(FlightLoadData fld:list){
	     				fmi.setFlightRoute(fld.getHx());
	     				if(fld.getLegNo()!=null){
	     					switch(fld.getLegNo()){
	 	    					case "0":
	 	    						fmi.setFlightPart0(fld.getHd());
	 	    						break;
	 	    					case "1":
	 	    						fmi.setFlightPart1(fld.getHd());
	 	    						break;
	 	    					case "2":
	 	    						fmi.setFlightPart2(fld.getHd());
	 	    						break;
	     						default:
	     							break;
	     					}
	     				}
	     			
	     				if(fld.getHdfl()!=null && !fld.getHdfl().equals("")){
	     					switch(fld.getHdfl()){
		     					case "DOM":
		     					case "D":
		     						passengerInternal +=  fld.getCr();
		     	                    childInternal += fld.getEt();
		     	                    babyInternal += fld.getYe();
		     	                    airMailWeightInternal += fld.getYj();
		     	                    airCargoWeightInternal += fld.getHw();
		     	                    luggageWeightInternal += fld.getXl();
		     	                    luggageNumInternal += fld.getXljs();	     	                    
		     						break;
		     						
		     					default:
		     						passengerInternational += fld.getCr();
		     	                    childInternational += fld.getEt();
		     	                    babyInternational += fld.getYe();
		     	                    airMailWeightInternational += fld.getYj();
		     	                    airCargoWeightInternational += fld.getHw();
		     	                    luggageWeightInternational += fld.getXl();
		     	                    luggageNumInternational += fld.getXljs();
		
		     	                    // 国内国际查询标志位 : 0国内;1国外
		     	                    flightCountryTypeQuery="1";
		     	                    // 国内国际收费标志位: 0国内;1国外
		     	                    flightCountryTypeCharge="1";
		     						break;
	     					}
	     				}   
	     				
		                viaAdult +=  fld.getCrwh();
		                viaChild +=  fld.getEtwh();
		                viaBaby +=  fld.getYewh();
		                
	     			}
	     			
	                
	                // 计算总数：国内旅客数 + 国内儿童数 + 国内婴儿数 + 国际旅客数 + 国际儿童数 + 国际婴儿数
	     			/*totalpass = passengerInternal
	                        + childInternal
	                        + babyInternal 
	                        + passengerInternational
	                        + childInternational 
	                        + babyInternational;
	                */
	     			//lushuaifeng 20161011
	                // 计算总数：国内旅客数 + 国内儿童数 + 国际旅客数 + 国际儿童数 
	     			totalpass = passengerInternal+ childInternal + passengerInternational+ childInternational; 
	                        	
	                // 计算客座率
	                if (fmi.getAircraftSeatCapacity() != null && fmi.getAircraftSeatCapacity() > 0L) {
	                	
	                	Double plf = ((double) totalpass) / fmi.getAircraftSeatCapacity();
	                	
	                	passengerLoadFactor=Double.valueOf(df.format(plf));
	                	
	                }	               	                
	                
	     			 // 国内旅客数
	                fmi.setPassengerInternal(passengerInternal);
	                // 国内儿童数
	                fmi.setChildInternal(childInternal);
	                // 国内婴儿数
	                fmi.setBabyInternal(babyInternal);
	                // 国内邮件重量
	                fmi.setAirMailWeightInternal(airMailWeightInternal);
	                // 国内货物重量
	                fmi.setAirCargoWeightInternal(airCargoWeightInternal);
	                // 国内行李重量
	                fmi.setLuggageWeightInternal(luggageWeightInternal);
	                //国内行李数量
	                fmi.setLuggageNumInternal(luggageNumInternal);  
	                
	                // 国际旅客数
	                fmi.setPassengerInternational(passengerInternational);
	                // 国际儿童数
	                fmi.setChildInternational(childInternational);
	                // 国际婴儿数
	                fmi.setBabyInternational(babyInternational);
	                // 国际邮件重量
	                fmi.setAirMailWeightInternational(airMailWeightInternational);
	                // 国际货物重量
	                fmi.setAirCargoWeightInternational(airCargoWeightInternational);
	                // 国际行李重量
	                fmi.setLuggageWeightInternational(luggageWeightInternational);
	                // 国际行李数量
	                fmi.setLuggageNumInternational(luggageNumInternational);
	                
	                // 国内国际查询标志位 : 0国内;1国外
	                fmi.setFlightCountryTypeQuery(flightCountryTypeQuery);
	                // 国内国际收费标志位: 0国内;1国外
	                fmi.setFlightCountryTypeCharge(flightCountryTypeCharge);
	                
	                // 过站旅客
	                fmi.setViaAdult(viaAdult);
	                // 过站儿童
	                fmi.setViaChild(viaChild);
	                // 过站婴儿
	                fmi.setViaBaby(viaBaby);
	                
	     			fmi.setPassengerLoadFactor(DateUtils.get2Double(passengerLoadFactor));
	     			
	     	     	flightMateInfoDao.save(fmi);
	     			
	     		}	     		
	     	}
     	}catch(Exception e){
     		e.printStackTrace();
     		return false;
     	}
     	
     	return true;
     	     
    }

    
//    private Map<String, Object> saveFlightLoadData2FlightMateInfo(Map<String, Object> result) {
//        int addCounter = 0, modifyCounter = 0;
//        int columnsCount=(int) result.get("columnsCount");
//        List<FlightMateInfo> retFlightMateInfolist=new ArrayList<FlightMateInfo>();
//        List<FlightLoadData> flightLoadDataAllList;
//        List<FlightLoadData> flightLoadDataErrorList;
//        List<FlightLoadData> flightLoadDataNotFoundList =new ArrayList<FlightLoadData>();
//        List<FlightLoadData> resultlist = (List<FlightLoadData>) result
//                .get("result");
//        flightLoadDataAllList = (List<FlightLoadData>) resultlist.get(0);
//        flightLoadDataErrorList = (List<FlightLoadData>) resultlist.get(1);
//        String  globalError=(String) result.get("resultMessage");
//        if (flightLoadDataErrorList.size() == 0 && globalError.isEmpty()) {
//            //读取FlightMateInfo 
//            Date startDate=DateUtils.getZeroOfOneday((Date)result.get("minDate"));
//            Date endDate=DateUtils.getLatestTimeOfOneday((Date)result.get("maxDate"));
//            Map<String, Object> filters = new HashMap<>();        
//            filters.put("executeTime_greaterThanOrEqualTo", startDate);
//            filters.put("executeTime_lessThanOrEqualTo", endDate);
//            filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
//            List<FlightMateInfo> fmis = this.findBy(filters);
//            Map<Long,FlightMateInfo> fmiMap=new HashMap();
//            boolean isFmiInfmiMap=false;
//            int row=2;
//            for (FlightLoadData flightLoadData : flightLoadDataAllList) {
//                isFmiInfmiMap = false;
//                DataHandler<FlightMateInfo> dataHandler = new DataHandler<>();
//                filters.clear();
//                String excTime=DateUtils.Date2String(flightLoadData.getSjrq(),"yyyy-MM-dd") + " " + flightLoadData.getQjsj();
//                filters.put("executeTime2s_equal", excTime);
//                if ("I".equals(flightLoadData.getIo())) {
//                    filters.put("flightDirection_equal", "0");
//                    filters.put("aFlightIdentity_equal", flightLoadData.getCyr() + flightLoadData.getHbh());
//                    filters.put("aregisteration_equal", flightLoadData.getJh());
//                } else if ("O".equals(flightLoadData.getIo())) {
//                    filters.put("flightDirection_equal", "1");
//                    filters.put("dFlightIdentity_equal", flightLoadData.getCyr() + flightLoadData.getHbh());
//                    filters.put("dregisteration_equal", flightLoadData.getJh());
//                }
//                List<FlightMateInfo> listFlightMateInfo = dataHandler.findBy(fmis, filters);
//                if (listFlightMateInfo.size() == 1) {
//                    FlightMateInfo flightMateInfo = listFlightMateInfo.get(0);
//                    FlightMateInfo flightMateInfoInMap = fmiMap
//                            .get(flightMateInfo.getId());
//                    if (flightMateInfoInMap == null) {
//                        fmiMap.put(flightMateInfo.getId(), flightMateInfo);
//                    } else {
//                        isFmiInfmiMap = true;
//                    }
//                    flightMateInfo.setFlightRoute(flightLoadData.getHx());
//                    if (flightMateInfo.getFlightPart0() == null
//                            || flightMateInfo.getFlightPart0().isEmpty()) {
//                        flightMateInfo.setFlightPart0(flightLoadData.getHd());
//                    } else if (flightMateInfo.getFlightPart1() == null
//                            || flightMateInfo.getFlightPart1().isEmpty()) {
//                        flightMateInfo.setFlightPart1(flightLoadData.getHd());
//                    } else if (flightMateInfo.getFlightPart2() == null
//                            || flightMateInfo.getFlightPart2().isEmpty()) {
//                        flightMateInfo.setFlightPart2(flightLoadData.getHd());
//                    }
//                    if (!"".equals(flightLoadData.getHdfl())
//                            && (flightLoadData.getHdfl().equals("DOM") ||flightLoadData.getHdfl().equals("D") )) {
//
//                        double passengerInternal = 0, childInternal = 0, babyInternal = 0, airMailWeightInternal = 0, luggageWeightInternal = 0, airCargoWeightInternal = 0;
//                        if (isFmiInfmiMap) {
//                            passengerInternal = 0 + flightMateInfo
//                                    .getPassengerInternal();
//                            childInternal = 0 + flightMateInfo
//                                    .getChildInternal();
//                            babyInternal = 0 + flightMateInfo.getBabyInternal();
//                            airMailWeightInternal = 0 + flightMateInfo
//                                    .getAirMailWeightInternal();
//                            luggageWeightInternal = 0 + flightMateInfo
//                                    .getLuggageWeightInternal();
//                            airCargoWeightInternal = 0 + flightMateInfo
//                                    .getAirCargoWeightInternal();
//                        }
//                        passengerInternal = passengerInternal
//                                + flightLoadData.getCr();
//                        childInternal = childInternal + flightLoadData.getEt();
//                        babyInternal = babyInternal + flightLoadData.getYe();
//                        airMailWeightInternal = airMailWeightInternal
//                                + flightLoadData.getYj();
//                        airCargoWeightInternal = airCargoWeightInternal
//                                + flightLoadData.getHw();
//                        luggageWeightInternal = luggageWeightInternal
//                                + flightLoadData.getXl();
//                        // 国内旅客数
//                        flightMateInfo.setPassengerInternal(passengerInternal);
//                        // 国内儿童数
//                        flightMateInfo.setChildInternal(childInternal);
//                        // 国内婴儿数
//                        flightMateInfo.setBabyInternal(babyInternal);
//                        // 国内邮件
//                        flightMateInfo
//                                .setAirMailWeightInternal(airMailWeightInternal);
//                        // 国内货物
//                        flightMateInfo
//                                .setAirCargoWeightInternal(airCargoWeightInternal);
//                        // 国内行李
//                        flightMateInfo
//                                .setLuggageWeightInternal(luggageWeightInternal);
//                        // 国内国际查询标志位0国内 1国外
//                        flightMateInfo.setFlightCountryTypeQuery("0");
//                        // 国内国际收费标志位0国内 1国外
//                        flightMateInfo.setFlightCountryTypeCharge("0");
//                    } else if (!"".equals(flightLoadData.getHdfl())) {
//                        double passengerInternational = 0, childInternational = 0, babyInternational = 0, airCargoWeightInternational = 0, airMailWeightInternational = 0, luggageWeightInternational = 0;
//                        if (isFmiInfmiMap) {
//                            passengerInternational = 0 + flightMateInfo
//                                    .getPassengerInternational();
//                            childInternational = 0 + flightMateInfo
//                                    .getChildInternational();
//                            babyInternational = 0 + flightMateInfo
//                                    .getBabyInternational();
//                            airCargoWeightInternational = 0 + flightMateInfo
//                                    .getAirCargoWeightInternational();
//                            airMailWeightInternational = 0 + flightMateInfo
//                                    .getAirMailWeightInternational();
//                            luggageWeightInternational = 0 + flightMateInfo
//                                    .getLuggageWeightInternational();
//                        }
//                        passengerInternational = passengerInternational
//                                + flightLoadData.getCr();
//                        childInternational = childInternational
//                                + flightLoadData.getEt();
//                        babyInternational = babyInternational
//                                + flightLoadData.getYe();
//                        airMailWeightInternational = airMailWeightInternational
//                                + flightLoadData.getYj();
//                        airCargoWeightInternational = airCargoWeightInternational
//                                + flightLoadData.getHw();
//                        luggageWeightInternational = luggageWeightInternational
//                                + flightLoadData.getXl();
//                        // 国际旅客数
//                        flightMateInfo
//                                .setPassengerInternational(passengerInternational);
//                        // 国际儿童数
//                        flightMateInfo
//                                .setChildInternational(childInternational);
//                        // 国际婴儿数
//                        flightMateInfo.setBabyInternational(babyInternational);
//                        // 国际邮件
//                        flightMateInfo
//                                .setAirMailWeightInternational(airMailWeightInternational);
//                        // 国际货物
//                        flightMateInfo
//                                .setAirCargoWeightInternational(airCargoWeightInternational);
//                        // 国际行李
//                        flightMateInfo
//                                .setLuggageWeightInternational(luggageWeightInternational);
//                        if (!"1".equals(flightMateInfo
//                                .getFlightCountryTypeQuery())) {
//                            // 国内国际查询标志位 : 0国内;1国外
//                            flightMateInfo.setFlightCountryTypeQuery("1");
//                            // 国内国际收费标志位: 0国内;1国外
//                            flightMateInfo.setFlightCountryTypeCharge("1");
//                        }
//                    }
//                    double viaAdult = 0, viaChild = 0, viaBaby = 0;
//                    if (isFmiInfmiMap) {
//                        viaAdult = 0 + flightMateInfo.getViaAdult();
//                        viaChild = 0 + flightMateInfo.getViaChild();
//                        viaBaby = 0 + flightMateInfo.getViaBaby();
//                    }
//                    viaAdult = viaAdult + flightLoadData.getCrwh();
//                    viaChild = viaChild + flightLoadData.getEtwh();
//                    viaBaby = viaBaby + flightLoadData.getYewh();
//                    // 过站旅客
//                    flightMateInfo.setViaAdult(viaAdult);
//                    // 过站儿童
//                    flightMateInfo.setViaChild(viaChild);
//                    // 过站婴儿
//                    flightMateInfo.setViaBaby(viaBaby);
//                    
//                    //计算客座率
//                    double totalpass=0;
//                    //国内旅客数 + 国内儿童数 + 国内婴儿数 + 国际旅客数 + 国际儿童数 + 国际婴儿数
//                    totalpass = flightMateInfo.getPassengerInternal()
//                            + flightMateInfo.getChildInternal()
//                            + flightMateInfo.getBabyInternal()
//                            + flightMateInfo.getPassengerInternational()
//                            + flightMateInfo.getChildInternational()
//                            + flightMateInfo.getBabyInternational();
//                    
//                    double PassengerLoadFactor=0;
//                    if(flightMateInfo.getAircraftSeatCapacity()!=null&& flightMateInfo.getAircraftSeatCapacity()>0L){
//                        PassengerLoadFactor=((double)totalpass)/flightMateInfo.getAircraftSeatCapacity();
//                    }
//                    flightMateInfo.setPassengerLoadFactor(DateUtils.get2Double(PassengerLoadFactor));
//                    
//                }else{//没有找到 flightMateInfo
//                    flightLoadData.setVerifyDescription("第"+row+"行没有找到对应航班配对信息！");
//                    Map<Integer, ArrayList<Integer>> errorMessage = new HashMap<Integer, ArrayList<Integer>>();
//                    ArrayList<Integer> errorCells = new ArrayList<>();
//                    errorCells.add(1);
//                    errorMessage.put(row, errorCells);
//                    flightLoadData.setErrorMessage(errorMessage);
//                    flightLoadDataNotFoundList.add(flightLoadData);
//                }
//                row++;
//            }
//            //去掉有问题的flightLoadData
//            //flightLoadDataAllList.removeAll(flightLoadDataErrorList);
//            for (long key : fmiMap.keySet()) {
//                retFlightMateInfolist.add(fmiMap.get(key));
//            }
//            this.save(retFlightMateInfolist);
//            
//        }
//        Map<String, Object> retResult = new HashMap<>();
////        retResult.put("list", retFlightMateInfolist);
////        retResult.put("errorList", flightLoadDataErrorList);
////        retResult.put("errorMessage", result.get("resultMessage"));
//        retResult.put("totalCount", flightLoadDataAllList.size()+flightLoadDataErrorList.size()+"");//总数
//        retResult.put("successCount", retFlightMateInfolist.size()+"");//成功
//        List<String> noFoundlist=new ArrayList<String>();
//        
//        for(FlightLoadData flightLoadData:flightLoadDataNotFoundList){
//            noFoundlist.add(flightLoadData.getVerifyDescription());
//            ArrayList<Integer> errorCells = new ArrayList<>();
//            errorCells.add(columnsCount);
//            Map<Integer, ArrayList<Integer>> noFoundErrorMessage = new HashMap<Integer, ArrayList<Integer>>();
//            noFoundErrorMessage.put(flightLoadData.getRowNumber(), errorCells);
//            flightLoadData.setErrorMessage(noFoundErrorMessage);
//        }
//        retResult.put("noFound", noFoundlist); //没找到
//        
//        List<String> errorlist=new ArrayList<String>();
//        for(FlightLoadData flightLoadData:flightLoadDataErrorList){
//            errorlist.add(flightLoadData.getVerifyDescription());
//        }
//        if (!globalError.isEmpty()) {
//            retResult.put("globalError", globalError);// 列数量或格式不正确
//        }
//        retResult.put("error",errorlist);//错误
//        
//        // 遍历航班list，生成报错坐标errorMessage
//        List<FlightLoadData> flightLoadDataAllErrorList=new ArrayList<>();
//        flightLoadDataAllErrorList.addAll(flightLoadDataErrorList);
//        flightLoadDataAllErrorList.addAll(flightLoadDataNotFoundList);
//        Map<Integer, List<Integer>> errorMessage = new HashMap<>();
//        for (FlightLoadData flightLoadData : flightLoadDataAllErrorList) {
//            if (flightLoadData.getErrorMessage() != null) {
//                errorMessage.putAll(flightLoadData.getErrorMessage());
//            }
//        }
//        String fileName=(String) result.get("fileName");
//        String retFile="";
//        try {
//            if (fileName.endsWith(".xls")) {
//                retFile=ExcelOperatorUtils.changeExcel2003BackgroundColor(fileName,errorMessage);
//            } else if (fileName.endsWith(".xlsx")) {
//                retFile=ExcelOperatorUtils.changeExcel2007BackgroundColor(fileName,errorMessage);
//            }
//            retResult.put("File", retFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        return retResult;
//    }
    
  //查找时间时间段内，缺失FlightMateInfo的航班
    public List<FlightBase> findMisFlightMateInfo(Date startDate,Date endDate){
     // FLIGHTBASE
        List<FlightBase> fbs = flightBaseManager.getFlightBaseMisFlightMateInfoByActualTime(startDate, endDate);
        // 去处重复FLIGHTBASE
        List<FlightBase> temp = new ArrayList<>();
        for (FlightBase flightBase : fbs) {
            if (!temp.contains(flightBase)) {
                temp.add(flightBase);
            }
        }
        fbs.clear();
        fbs.addAll(temp);
        return fbs;
    }
    //重新生成选定航班的FlightMateInfo
    public  Map<String, Object> repairMisFlightMateInfo(List<FlightBase> fbs){
        // 数据准备
        // FlightMateInfo
        List<Long> ids=new ArrayList<>();
        for(FlightBase flightBase : fbs){
            ids.add(flightBase.getId());
        }
        List<FlightMateInfo> flightMateInfos = this.findByFlightBases(ids,null);
        return generate(flightMateInfos,fbs);
    }


    @Override
    public Map<String,String> updateChargeTermById(List<Long> ids) {
        //查找FlightMateInfo
//        List<FlightMateInfo> flightMateInfos=new ArrayList<>();
//        for(Long id: ids){
//            flightMateInfos.add( this.find(id)); 
//        } 
    	
    	List<FlightMateInfo> flightMateInfos = flightMateInfoDao.findByIds(ids);
    	
    	Object [] rtn = updateChargeTerm(flightMateInfos);
    	
    	List<ChargeTermVo> term = (List<ChargeTermVo>) rtn[0];
    	String success= (Integer) rtn[1] + "";
    	String failed = (Integer) rtn[2] + "";
    	String msg = (String)rtn[3];

    	Map<String,String> result = new HashMap<String,String>();
    	result.put("term", term.size() + "");
    	result.put("success", success);
    	result.put("failed", failed);
    	result.put("msg",msg);

        return result;
    }


    @Override
    public Map<String, Object> importFlightLoadDataByExcel(String fileName)
            throws IOException {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Map<String, Object> flightMateInfoDataResult = new HashMap<>();
//        String columnErrorMessage ="";
        
        try {
            inputStream = new FileInputStream(fileName);
            byte[] buffer = new byte[8192];

            for (int length = 0; (length = inputStream.read(buffer)) > 0;) {
                outputStream.write(buffer, 0, length);
            }
            byte[] bytes = outputStream.toByteArray();

            if (fileName.endsWith(".xls")) {
                flightMateInfoDataResult =  importFlightLoadDataByExcel2003(bytes,fileName);
//                columnErrorMessage = (String) flightMateInfoDataResult.get("errorMessage");

            } else if (fileName.endsWith(".xlsx")) {
                flightMateInfoDataResult = importFlightLoadDataByExcel2007(bytes,fileName);
//                columnErrorMessage = (String) flightMateInfoDataResult.get("errorMessage");
            }
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                inputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return flightMateInfoDataResult;

    
    }

    public Map<String, Object> importAircraftAirlineAlterationDataByExcel(String fileName) throws IOException{

        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Map<String, Object> aircraftAirlineAlterationDataResult = new HashMap<>();
//        String columnErrorMessage ="";
        
        try {
            inputStream = new FileInputStream(fileName);
            byte[] buffer = new byte[8192];

            for (int length = 0; (length = inputStream.read(buffer)) > 0;) {
                outputStream.write(buffer, 0, length);
            }
            byte[] bytes = outputStream.toByteArray();

            if (fileName.endsWith(".xls")) {
            	aircraftAirlineAlterationDataResult =  aircraftAirlineAlterationManager.importAircraftAirlineAlterationDataFromCAACSCExcel2003(bytes);
//                columnErrorMessage = (String) flightMateInfoDataResult.get("errorMessage");

            } else if (fileName.endsWith(".xlsx")) {
            	aircraftAirlineAlterationDataResult = aircraftAirlineAlterationManager.importAircraftAirlineAlterationDataFromCAACSCExcel2007(bytes);
//                columnErrorMessage = (String) flightMateInfoDataResult.get("errorMessage");
            }
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                inputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return aircraftAirlineAlterationDataResult;        

    }

    @Override
    public int isBridgeStand(String stand) {
        Properties p = ResourceUtil.getProperties("aircraftService", true);
        bridge_Stand = p.getProperty("bridgeStand",
                        ",001,002,003,004,005,006,007,008,009,010,011,012,013,014,105,106,017,018,019,020,021,022,023,024,025,026,027,028");
        int StandFlag = 1;// 0:近机位 1:远机位
        if (stand != null && bridge_Stand.contains(stand)) {
            StandFlag = 0;
        }
        return StandFlag;
    }


    
    public static void main(String[] args){
    	DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setGroupingSize(0);
        df.setRoundingMode(RoundingMode.FLOOR);
        for(int i=0;i<100;i++){
	    	double plf=((double)i)/80;
	    	double ll=Double.valueOf(df.format(plf));
	    	System.out.println(plf+","+ll);
        }
    }
}

package com.nlia.fqdb.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.FlightBase;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IClientFlightBaseManager extends AbstractBaseService<FlightBase, Long> {

    	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return 错误数据
    	 * @throws IOException 
	 */
    	public List<FlightBase> importByExcel2003(byte[] bytes) throws IOException;
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<FlightBase> importByExcel2007(byte[] bytes) throws IOException;
	
	/**
	 * 功能：判断航班是否存在
	 * 根据日期、航班号全称、进离港属性来判断航班是否存在
	 * @param flightBase
	 * @return true /false
	 */
	public boolean isFlightBaseExist(FlightBase flightBase);
	
	/**
	 * 跳转到指定页
	 */
	public Map<String,Object>findFlightByPage(int pageSize,int destinationPageNumber,Map<String,Object> filters);
	
	/**
	 * 刷新内存
	 */
	public void refreshFlightBases(Date startDate, Date endDate);
	
	/**
	 * 获得一段时期内的flightBase
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public List<FlightBase> getPeriodFlight(Date startdate, Date enddate);
	
	/**
	 * 查找航班逻辑（包含linkflight，未完成sp航班）
	 * @param startTime
	 * @param endTime
	 * @param flightIdentity
	 * @param flightDirection
	 * @param aircraftIATACode
	 * @return
	 */
	public List<FlightBase> findFlightBase(Date startTime, Date endTime, String flightIdentity, FlightBase.TYPE flightDirection, String aircraftIATACode, String flightRegisteration);
	public List<FlightBase> findFlightBase(Date startTime, Date endTime, String flightIdentity, FlightBase.TYPE flightDirection, String aircraftIATACode, String flightRegisteration, boolean isHistoryQuery);
	
	/*	
	*//**
	 * 导出指定时间段内的航班数据（xml形式），用于数据交换
	 *//*
	public String flightToXml(Date startDate, Date endDate);*/
	
	

    //add by march 20140826 从库中获取
	public List<FlightBase> findByInDB(Map<String, Object> filters, List<String> sorters);

    /**
     * 从excel2003中导入数据到表格中
     * 
     * @param bytes
     * @return 错误数据
     * @throws IOException
     */
    public Map<String, Object> importLoadDataByExcel2003(byte[] bytes)
            throws IOException;

    /**
     * 从excel2007中导入数据到表格
     * 
     * @param bytes
     * @return
     */
    public Map<String, Object> importLoadDataByExcel2007(byte[] bytes)
            throws IOException;
    
  //刷新单条航班
    public void refreshFlightInCache(FlightBase entity);
    //add by march 为解决双机缓存不一致而增加的方法
}

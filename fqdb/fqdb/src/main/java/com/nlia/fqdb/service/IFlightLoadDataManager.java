package com.nlia.fqdb.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.FlightLoadData;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IFlightLoadDataManager extends AbstractBaseService<FlightLoadData, Long>{
   
    /**
     * 从excel2003中导入数据到表格中
     * 
     * @param bytes
     * @return 错误数据
     * @throws IOException
     */
    public Map<String, Object> importFlightLoadDataByExcel2003(byte[] bytes)
            throws IOException;

    /**
     * 从excel2007中导入数据到表格
     * 
     * @param bytes
     * @return
     */
    public Map<String, Object> importFlightLoadDataByExcel2007(byte[] bytes)
            throws IOException;
    
    //生成FlightLoadData数据
    public List<FlightLoadData> generate(Date startTime, Date endTime);
    
    //生成单个航班的FlightLoadData数据
    public Map<String, Object> generate(Long id);
    
    //通过FlightLoadData更新FLIGHTDATA
    public void updateFDbyFLD(List<Long> ids);
}

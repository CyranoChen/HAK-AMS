package com.wonders.frame.ams.service.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wonders.frame.ams.constants.Constant;
import com.wonders.frame.ams.dao.basic.AircraftAirlineAlterationDao;
import com.wonders.frame.ams.dao.basic.impl.AircraftAirlineAlterationDaoImpl;
import com.wonders.frame.ams.dto.basic.AircraftAirlineAlterationDto;
import com.wonders.frame.ams.dto.permission.UserDto;
import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;
import com.wonders.frame.ams.model.basic.AircraftAirlineAlteration;
import com.wonders.frame.ams.utils.Chk;
import com.wonders.frame.ams.utils.DateUtil;
import com.wonders.frame.ams.vo.basic.AircraftAirlineAlterationVo;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SimplePage;
import com.wonders.frame.core.model.vo.SingleModelParams;
import com.wonders.frame.core.service.SingleCrudService;
import com.wonders.frame.core.utils.SingleParamsConvertUtil;
@Service
public class AircraftAirlineAlterationService {
	@Resource
	private AircraftAirlineAlterationDao aircraftAirlineAlterationDao;
	
	@Resource
	private AircraftAirlineAlterationDaoImpl aircraftAirlineAlterationDaoImpl;
	
	@Resource
	private SingleCrudService<ILongIdRemoveFlagModel,Long> basicCrudService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public SimplePage<AircraftAirlineAlterationVo> query(AircraftAirlineAlterationDto aaaform,String pagenum,String pagesize){
//		HashMap<String,String> fliter = new HashMap<String,String>();
//		LinkedHashMap<String,String> sorter = new LinkedHashMap<String,String>();
		Integer pageSize,pageNum;
		
		if(null!=pagesize && null!=pagenum){
			pageSize = Integer.parseInt(pagesize);
			pageNum = Integer.parseInt(pagenum) + 1;
		}else{
			pageSize = 50;
			pageNum = 1;
		}
//		if(null != aaaform){
//			
//			if(Chk.spaceCheck(aaaform.getAircraftRegistration())){
//				fliter.put("aircraftRegistration_l", aaaform.getAircraftRegistration().toUpperCase());
//			}
//		}
//		fliter.put("removeFlag", Constant.NOT_REMOVED);
//		sorter.put("aircraftRegistration", "asc");
//		SimplePage<AircraftAirlineAlteration> aircraftAirlineAlterations = aircraftAirlineAlterationDao.findByPage(fliter, sorter, pageNum, pageSize);
			SimplePage<AircraftAirlineAlterationVo> aircraftAirlineAlterations = aircraftAirlineAlterationDaoImpl.findbyPage(aaaform, pageNum, pageSize);
		return aircraftAirlineAlterations;
	}

	public ReturnObj<Integer> remove(HttpServletRequest req) {
		String id = req.getParameter("id");
		if(null != id){
			logger.debug("remove:id="+id);
			String[] ids=id.split(",");
			if(ids.length>1){
				List<Long> idList=new ArrayList<Long>();
				for(String i:ids){
					idList.add(Long.valueOf(i));
				}
				return basicCrudService.removeByIds(AircraftAirlineAlteration.class,idList);
			}else{
				return basicCrudService.removeById(AircraftAirlineAlteration.class, Long.valueOf(id));
			}
		}else{
			return null;
		}
	}
	
	/**
	 *  按ID查找
	 */
	public AircraftAirlineAlteration findById(Long id){
		return aircraftAirlineAlterationDao.findById(id);
	}
	
	/**
	 *  更新
	 */				 
	public ReturnObj<ILongIdRemoveFlagModel> update(HttpServletRequest req){
		SingleModelParams smp = SingleParamsConvertUtil.getModelParams(AircraftAirlineAlteration.class, req);
		String loginName = ((UserDto)req.getSession().getAttribute(Constant.LOGIN_USER)).getLoginName();
		smp.addData("modifyUser",loginName);
		smp.addData("modifyTime",DateUtil.getDateString());
		smp.addData("validFlag", "1");
		return basicCrudService.saveOrUpdate(smp);
	}
	
	/**
	 *  新增
	 */
	public ReturnObj<ILongIdRemoveFlagModel> save(HttpServletRequest req){
		SingleModelParams smp = SingleParamsConvertUtil.getModelParams(AircraftAirlineAlteration.class, req);
		String loginName = ((UserDto)req.getSession().getAttribute(Constant.LOGIN_USER)).getLoginName();
		smp.addData("createUser",loginName);
		smp.addData("createTime",DateUtil.getDateString());
		smp.addData("removeFlag","1");
		smp.addData("validFlag", "1");
		smp.addData("basicDataID", req.getParameter("aircraftRegistration"));
		smp.addData("originalAirline", req.getParameter("currentAirline"));
		smp.addData("originalSubairline", req.getParameter("currentSubairline"));
		smp.addData("isPackagingFacility", "N");

		return basicCrudService.saveOrUpdate(smp);
	}
}

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
import com.wonders.frame.ams.dao.basic.BaseAircraftDao;
import com.wonders.frame.ams.dto.permission.UserDto;
import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;
import com.wonders.frame.ams.model.basic.BaseAircraft;
import com.wonders.frame.ams.utils.Chk;
import com.wonders.frame.ams.utils.DateUtil;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SimplePage;
import com.wonders.frame.core.model.vo.SingleModelParams;
import com.wonders.frame.core.service.SingleCrudService;
import com.wonders.frame.core.utils.SingleParamsConvertUtil;
@Service
public class BaseAircraftService {
	@Resource
	private BaseAircraftDao baseAircraftDao;
	@Resource
	private SingleCrudService<ILongIdRemoveFlagModel,Long> basicCrudService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public SimplePage<BaseAircraft> query(String aircraftRegistration,String aircraftTypeICAOCode,String pagenum,String pagesize){
		HashMap<String,String> fliter = new HashMap<String,String>();
		LinkedHashMap<String,String> sorter = new LinkedHashMap<String,String>();
		Integer pageSize = Integer.parseInt(pagesize);
		Integer pageNum = Integer.parseInt(pagenum) + 1;
		if(Chk.spaceCheck(aircraftRegistration)){
			fliter.put("aircraftRegistration_l", aircraftRegistration.toUpperCase());
		}
		if(Chk.spaceCheck(aircraftTypeICAOCode)){
			fliter.put("aircraftTypeICAOCode_l", aircraftTypeICAOCode.toUpperCase());
		}
		fliter.put("removeFlag", Constant.NOT_REMOVED);
		sorter.put("aircraftRegistration", "asc");

		SimplePage<BaseAircraft> baseAircrafts = baseAircraftDao.findByPage(fliter, sorter, pageNum, pageSize);
		return baseAircrafts;
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
				return basicCrudService.removeByIds(BaseAircraft.class,idList);
			}else{
				return basicCrudService.removeById(BaseAircraft.class, Long.valueOf(id));
			}
		}else{
			return null;
		}
	}
	
	public BaseAircraft findById(Long id){
		return baseAircraftDao.findById(id);
	}
	
	/**
	 * @see 更新
	 */
	public ReturnObj<ILongIdRemoveFlagModel> update(HttpServletRequest req){
		SingleModelParams smp = SingleParamsConvertUtil.getModelParams(BaseAircraft.class, req);
		String loginName = ((UserDto)req.getSession().getAttribute(Constant.LOGIN_USER)).getLoginName();
		smp.addData("modifyUser",loginName);
		smp.addData("modifyTime",DateUtil.getDateString());
		return basicCrudService.saveOrUpdate(smp);
	}
	
	/**
	 * @see 新增
	 */
	public ReturnObj<ILongIdRemoveFlagModel> save(HttpServletRequest req){
		SingleModelParams smp = SingleParamsConvertUtil.getModelParams(BaseAircraft.class, req);
		String loginName = req.getSession().getAttribute(Constant.LOGIN_USER).toString();
		smp.addData("createUser",loginName);
		smp.addData("createTime",DateUtil.getDateString());
		smp.addData("removeFlag","1");
		return basicCrudService.saveOrUpdate(smp);
	}
}

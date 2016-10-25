package com.wonders.frame.ams.service.fqs;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.wonders.frame.ams.constants.Constant;
import com.wonders.frame.ams.dao.basic.ChargeTermDao;
import com.wonders.frame.ams.dto.permission.UserDto;
import com.wonders.frame.ams.model.ChargeTerm;
import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;
import com.wonders.frame.ams.utils.DateUtil;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SingleModelParams;
import com.wonders.frame.core.service.SingleCrudService;
import com.wonders.frame.core.utils.SingleParamsConvertUtil;

@Service
public class ChargeTermService {
	@Resource 
	private ChargeTermDao chargeTermDao;
	
	@Resource
	private SingleCrudService<ILongIdRemoveFlagModel,Long> basicCrudService;
	
	/**
	 * @see 按flightmateInfoid查找ChargeTerm
	 */
	public List<ChargeTerm> findAll(Long flightMateInfoId){
		HashMap<String,String> fliter = new HashMap<String,String>();
		LinkedHashMap<String,String> sorter = new LinkedHashMap<String,String>();
		if(null == flightMateInfoId){
			return null;
		}else{
			fliter.put("flightMateInfoId",flightMateInfoId.toString());
		}
		fliter.put("removeFlag", Constant.NOT_REMOVED);
		sorter.put("name", "asc");
		List<ChargeTerm> chargeTerms = chargeTermDao.findAll(fliter, sorter);
		return chargeTerms;
	}
	
	/**
	 * @see 保存
	 */
	public List<ChargeTerm> save(List<ChargeTerm> chargeTerms){
		return chargeTermDao.save(chargeTerms);
	}
	
	/**
	 * @see 更新
	 */				 
	public ReturnObj<ILongIdRemoveFlagModel> update(HttpServletRequest req){
		SingleModelParams smp = SingleParamsConvertUtil.getModelParams(ChargeTerm.class, req);
		String loginName = ((UserDto)req.getSession().getAttribute(Constant.LOGIN_USER)).getLoginName();
		smp.addData("modifyUser",loginName);
		smp.addData("modifyTime",DateUtil.getDateString());
		return basicCrudService.saveOrUpdate(smp);
	}

}

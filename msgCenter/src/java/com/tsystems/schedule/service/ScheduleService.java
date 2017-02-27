/**   
*    
* 项目名称：schedule   
* 类名称：ScheduleService   
* 类描述：   
* 创建人：zhoushun   
* 创建时间：2012-11-27 下午06:54:06   
* 修改人：zhoushun   
* 修改时间：2012-11-27 下午06:54:06   
* 修改备注：   
* @version    
*    
*/
package com.tsystems.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tsystems.schedule.common.service.CommonService;
import com.tsystems.schedule.model.bo.TScheduleConfig;


/** 
 * @ClassName: ScheduleService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun
 * @date 2012-11-27 下午06:54:06 
 *  
 */

@Service("scheduleService")
public class ScheduleService {
	private static CommonService commonService;
	
	//得到计划任务bo
	@SuppressWarnings("unchecked")
	public static List<TScheduleConfig> getSvcConfig(){
		return commonService.ListByHql("from TScheduleConfig t where t.removed = '0'");
	}

	
	public static CommonService getCommonService() {
		return commonService;
	}
	
	@Autowired
	public void setCommonService(@Qualifier(value="commonService")CommonService commonService) {
		ScheduleService.commonService = commonService;
	}

}

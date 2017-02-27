/**
 * 
 */
package com.tsystems.schedule.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.tsystems.schedule.common.service.CommonService;
import com.tsystems.schedule.model.bo.TScheduleConfig;
import com.tsystems.schedule.model.bo.TScheduleLog;
import com.tsystems.schedule.util.TimeUtil;

/**
 * @ClassName: LogUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2012-12-5 上午11:30:16
 * 
 */
@Component("logUtil")
public class LogUtil {
	private static CommonService commonService;

	// 日志记录
	public static void writeLog(TScheduleConfig t, String process, String rtv) {
		TScheduleLog log = new TScheduleLog();
		log.setMethod(t.getMethod());
		log.setParam(t.getParam());
		log.setType(t.getType());
		log.setName(t.getDescription());
		log.setProcess(process);
		log.setExecTime(TimeUtil.getNow());
		log.setResult(rtv);
		commonService.save(log);
	}

	// 异常记录
	public static void writeException(TScheduleConfig t, String process,
			String error) {
		TScheduleLog log = new TScheduleLog();
		log.setMethod(t.getMethod());
		log.setParam(t.getParam());
		log.setType(t.getType());
		log.setName(t.getDescription());
		log.setProcess(process);
		log.setExecTime(TimeUtil.getNow());
		log.setException(error);
		commonService.save(log);
	}

	// 更新运行时间
	public static void updateTime(TScheduleConfig t) {
		t.setLastTime(TimeUtil.getNow());
		commonService.update(t);
	}

	public static CommonService getCommonService() {
		return commonService;
	}

	@Autowired
	public void setCommonService(
			@Qualifier(value = "commonService") CommonService commonService) {
		LogUtil.commonService = commonService;
	}
}

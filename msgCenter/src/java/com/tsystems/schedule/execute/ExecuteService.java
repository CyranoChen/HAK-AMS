/**
 * 
 */
package com.tsystems.schedule.execute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tsystems.schedule.model.bo.TScheduleConfig;
import com.tsystems.schedule.util.ExecUtil;

/**
 * @ClassName: ExcuteService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2012-12-5 下午07:55:02
 * 
 */
@Service("executeService")
@Scope("prototype")
public class ExecuteService implements IExecutable {
	private static final Logger log = LoggerFactory.getLogger(ExecuteService.class);
	
	public String execute(TScheduleConfig t) throws Exception {
		String result = "";
		String type = t.getType();
		try {
			if ("1".equals(type)) {// native
				result = ExecUtil.nativeExec(t);
			} else if ("2".equals(type)) {// web
				result = ExecUtil.webExec(t);
			} else if ("3".equals(type)) {// database
				result = ExecUtil.procedureExec(t);
			}
		} catch (Exception e) {
			log.error("unexcepted Exception when {}","execute",e);
			//e.printStackTrace();
			throw e;
		}
		return result;
	}
}

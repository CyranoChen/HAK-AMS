/**
 * 
 */
package com.tsystems.schedule.execute;

import com.tsystems.schedule.model.bo.TScheduleConfig;

/**
 * @ClassName: IExecutable
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2012-12-5 下午07:54:43
 * 
 */
public interface IExecutable {
	public String execute(TScheduleConfig t) throws Exception;
}

package com.tsystems.schedule.servlet;

/**   
*    
* 项目名称：schedule   
* 类名称：ScheduleServlet   
* 类描述：   
* 创建人：zhoushun   
* 创建时间：2012-11-27 下午06:45:17   
* 修改人：zhoushun   
* 修改时间：2012-11-27 下午06:45:17   
* 修改备注：   
* @version    
*    
*/

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tsystems.schedule.main.ScheduleMain;
import com.tsystems.schedule.manage.ScheduleTask;



/** 
 * @ClassName: ScheduleServlet 
 * @Description: TODO(计划任务) 
 * @author zhoushun
 * @date 2012-11-27 下午06:45:17 
 *  
 */
public class ScheduleServlet extends HttpServlet {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	
	
	/** 
	* @Title: init 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @throws ServletException    设定文件 
	* @throws 
	*/
	@Override
	
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		log.debug("计划任务启动");
		ScheduleMain.start();	
	}

}

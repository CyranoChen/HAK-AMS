/**
 * 
 */
package com.tsystems.schedule.main;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tsystems.schedule.execute.ExecuteService;
import com.tsystems.schedule.manage.ScheduleTask;
import com.tsystems.schedule.model.bo.TScheduleConfig;
import com.tsystems.schedule.service.ScheduleService;
import com.tsystems.schedule.util.TimeUtil;

/**
 * @ClassName: ScheduleMain
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2012-12-4 上午10:26:48
 * 
 */
public class ScheduleMain {
	private static final Logger log = LoggerFactory.getLogger(ScheduleMain.class);
	private static ConcurrentHashMap<String,ScheduledFuture> taskMap =new ConcurrentHashMap<String,ScheduledFuture>();
	private ScheduleMain() {

	}

	/**
	 * 定时执行
	 */
	private static ScheduledExecutorService scheduler = null;

	/**
	 * @Title: addTask
	 * @Description: TODO(增加计划任务)
	 * @param @param config 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void addTask(TScheduleConfig t) {
		long delay = TimeUtil.getDelay(t.getTime(), t.getInterval());
		long period = TimeUtil.getIntervalSec(t.getInterval());
		log.debug(t.getName() + " 模块正在启动" + " 延迟为：" + delay + " 周期为：" + period);
		ScheduleTask task = new ScheduleTask(t);
		//若之前执行失败，强制执行一次
		if(!TimeUtil.isExec(t.getLastTime(), t.getTime(), t.getInterval())){
			scheduler.schedule(task,0,TimeUnit.SECONDS);
		}

		ScheduledFuture sf=scheduler.scheduleAtFixedRate(task, delay, period,
				TimeUnit.SECONDS);
		
		taskMap.put(t.getId(), sf);
	}
	public static void cancelTask(String scheduleConfigId){
		ScheduledFuture sf=taskMap.get(scheduleConfigId);
		if(sf!=null){
			sf.cancel(false);
			taskMap.remove(scheduleConfigId);
		}
	}
	
	public static void resetTask(TScheduleConfig t){
		cancelTask(t.getId());
		addTask(t);
	}
	/**
	 * @Title: start
	 * @Description: TODO(计划任务启动)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void start() {
		if (scheduler == null) {
			scheduler = Executors.newScheduledThreadPool(20);

			synchronized (scheduler) {
				List<TScheduleConfig> list = ScheduleService.getSvcConfig();
				for (TScheduleConfig t : list) {
					addTask(t);
				}
			}

		} else {
			log.warn("定时器正在运行!");
		}
	}

	/**
	 * @Title: stop
	 * @Description: TODO(停止)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void stop() {
		synchronized (scheduler) {
			if (scheduler != null && !scheduler.isShutdown()) {
				scheduler.shutdown();
				scheduler = null;
				log.debug("关闭定时器!");
			}
		}
	}

	/**
	 * @Title: restart
	 * @Description: TODO(重启)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void restart() {
		stop();
		start();
	}

	/**
	 * @Title: trigger
	 * @Description: TODO(手动触发 用于交互)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void trigger() {

	}

}

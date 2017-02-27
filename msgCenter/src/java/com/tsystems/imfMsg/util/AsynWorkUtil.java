/** 
* @Title: AsynWorkUtil.java 
* @Package com.wonders.frame.core.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.tsystems.imfMsg.util;

import com.googlecode.asyn4j.service.AsynService;
import com.googlecode.asyn4j.service.AsynServiceImpl;

/** 
 * @ClassName: AsynWorkUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public class AsynWorkUtil {
	private static AsynService asynService;
	static{
		/*getService参数
		 * 	1.(maxCacheWork)最大工作队列缓存工作数 – 300(默认值) 
			2.(addWorkWaitTime)当工作队列满时添加工作等待时间-- Long.MAX_VALUE(默认值) 
			3.(workThreadNum)异步工作执行线程池大小 ---- CPU核数/2 +1(默认值) 
			4.(callBackThreadNum)回调执行线程池大小 --- CPU核数/2(默认值) 
			5.(closeServiceWaitTime) 服务关闭等待时间 ---- 60000s(默认值) 

		 */
        asynService =  AsynServiceImpl.getService(300, 3000L, 100, 100, 1000);          
        asynService.init();  // 启动服务    
        
        //asynService2 =  AsynServiceImpl.getService(300, 1000L, 3, 2, 100);                     
        //asynService2.setWorkQueueFullHandler(new CacheAsynWorkHandler(100));    //异步工作缓冲处理器                                             
        //asynService2.setErrorAsynWorkHandler(new DefaultErrorAsynWorkHandler());   //异步工作执行异常处理器                
        //asynService2.init();   // 启动服务             
	}
 
	
	public static AsynService getAsynService(){
		return asynService;
	}
}

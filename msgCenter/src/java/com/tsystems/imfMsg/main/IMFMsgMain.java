/**
 * 
 */
package com.tsystems.imfMsg.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tsystems.imfMsg.imfClient.BssClient;
import com.tsystems.imfMsg.imfClient.FssClient;

/**
 * @ClassName: ScheduleMain
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2012-12-4 上午10:26:48
 * 
 */
public class IMFMsgMain {
	private static final Logger log = LoggerFactory.getLogger(IMFMsgMain.class);	
     
	public static void main(String[] args)  {
		XssClientListener();
	}
	
	private static void XssClientListener(){
		try{
			boolean suc=false;
			suc=FssClient.stop();
			if(suc){
				log.info("FSS starting......");
				Thread.sleep(10000);
				suc=FssClient.start();
				if(!suc){
					log.info("FSS start failed!");
				}
			}else{			
					log.info("FSS stop(before start) failed!");			
			}
			
			suc=BssClient.stop();
			if(suc){
				log.info("BSS starting......");
				Thread.sleep(10000);
				suc=BssClient.start();
				if(!suc){
					log.info("BSS start failed!");
				}
			}else{			
					log.info("BSS stop(before start) failed!");			
			}
		}catch(Exception e){
			log.error("unexcepted Exception when {}","XssClientListener",e);
			//e.printStackTrace();
		}
		//GssClient.start();
	}
	
	
	private IMFMsgMain() {
	}


	/**
	 * @Title: start
	 * @Description: TODO()
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void start() {
		log.info("Auto subscribe FSS,BSS starting !");
		XssClientListener();
	}

	/**
	 * @Title: stop
	 * @Description: TODO(停止)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void stop() {
	}

	/**
	 * @Title: restart
	 * @Description: TODO(重启)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void restart() {
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

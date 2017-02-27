package com.tsystems.imfMsg.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.asyn4j.service.AsynService;
import com.googlecode.asyn4j.service.AsynServiceImpl;

import com.tsystems.imfMsg.imfClient.BssClient;
import com.tsystems.imfMsg.imfClient.FssClient;



/** 
 * @ClassName: IMFMsgServlet 
 * @Description: TODO(消息监听) 
 * @author lushuaifeng
 * @date 2013-05-22 下午06:45:17 
 *  
 */
public class IMFMsgServlet extends HttpServlet {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = -1600327747212116607L;

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


	
	public void subscribeFSS(){
		try{
			log.info("subscribeFSS starting......");
			boolean suc=false;
			log.info("FSS stoping......");
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
						
		}catch(Exception e){
			log.error("unexcepted Exception when {}","subscribeFSS",e);
			//e.printStackTrace();
		}
	}
	
	public void subscribeBSS(){
		try{
			log.info("subscribeBSS starting......");
			boolean suc=false;
			log.info("BSS stoping......");
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
			log.error("unexcepted Exception when {}","subscribeBSS",e);
			//e.printStackTrace();
		}
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
		//log.debug("IMF Message Listener auto start");
		AsynService  asynService =  AsynServiceImpl.getService(300, 1000L, 3, 2, 100);    //设置异步工作服务      
        asynService.init();  // 启动服务    
		asynService.addWork(IMFMsgServlet.class,"subscribeFSS",new Object[] {});
		asynService.addWork(IMFMsgServlet.class,"subscribeBSS",new Object[] {});
		//IMFMsgMain.start();	
	}

}
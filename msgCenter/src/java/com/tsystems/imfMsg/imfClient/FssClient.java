/** 
* @Title: SssClient.java 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com 
* @date 2013-5-22 下午05:32:50 
* @version V1.0 
*/
package com.tsystems.imfMsg.imfClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tsystems.aviation.imf.client.commons.ImfServiceType;
import com.tsystems.aviation.imf.client.exception.ImfClientConnectionException;
import com.tsystems.aviation.imf.client.exception.ImfClientInvalidServiceTypeException;
import com.tsystems.aviation.imf.client.exception.ImfClientSsException;
import com.tsystems.aviation.imf.client.message.ImfMessageListener;
import com.tsystems.aviation.imf.client.subsystem.ImfSsAutoClient;
import com.tsystems.imfMsg.ImfMessageCache;
import com.tsystems.imfMsg.service.ImfClientService;


@SuppressWarnings("serial")
public class FssClient {
	private static final Logger log = LoggerFactory.getLogger(FssClient.class);
	private static ImfSsAutoClient fssClient = null;
    static ImfMessageListener fssListener = new ImfMessageListener() {
        @Override
        public void handleMessage(String msg) {
        	//log.info(msg);  	
        	//boolean suc=false;
            if(!msg.contains("<HeartBeat>")){//not receive heartBeat Msg
            	//suc=ImfClientService.saveMsg("FSS", msg);
            	ImfMessageCache.addFssMessage(msg);
            }
            
        }
    };
    public static boolean start(){

		try {
			if(fssClient==null){
				fssClient = ImfSsAutoClient.getImfSsAutoClient(ImfServiceType.FSS, fssListener);
			}
			boolean suc=fssClient.subscribe();
			if(!suc){
				return false;
			}			
		} catch (ImfClientSsException e) {
			return false;
		} catch (ImfClientConnectionException e) {
			return false;
		} catch (ImfClientInvalidServiceTypeException e) {
			return false;
		}
		return true;
    }
    
    public static boolean stop(){    		
		try {
			if(fssClient==null){
				fssClient = ImfSsAutoClient.getImfSsAutoClient(ImfServiceType.FSS, fssListener);
			}
			boolean suc=fssClient.unSubscribe();
			if(!suc){
				return false;
			}
		} catch (ImfClientConnectionException e) {
			return false;
		} catch (ImfClientInvalidServiceTypeException e) {
			return false;
		}
		return true;
    }      
    
    public static String connectionStatus(){
    	String status="";
		try {
			if(fssClient==null){
				fssClient = ImfSsAutoClient.getImfSsAutoClient(ImfServiceType.FSS, fssListener);
			}
			status=fssClient.connectionStatus();
			log.info("FSS connection status:{}",status);
		} catch (ImfClientConnectionException e) {
			return status;
		} catch (ImfClientInvalidServiceTypeException e) {
			return status;
		}
			
       return status;
    }    
}

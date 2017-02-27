/** 
* @Title: TargetBack.java 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com 
* @date 2013-7-23 上午11:12:08 
* @version V1.0 
*/
package com.tsystems.imfMsg.service;

import com.googlecode.asyn4j.core.callback.AsynCallBack;

/** 
 * @ClassName: TargetBack 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author A18ccms a18ccms_gmail_com 
 * @date 2013-7-23 上午11:12:08 
 *  
 */
public class DataParseCallBack extends AsynCallBack {    
	    
	        public void doNotify() {    
	                //输出异步方法调用返回结果    
	                ImfClientService.finishParseMsg((String)this.methodResult);	                	                	  
	        }    
	    
	} 

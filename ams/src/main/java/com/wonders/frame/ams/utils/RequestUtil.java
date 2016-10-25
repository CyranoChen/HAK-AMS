package com.wonders.frame.ams.utils;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RequestUtil {
	
	/**
	 * @see 判断此次请求是否通过ajax发送
	 * @param req
	 * @return
	 */
	public static boolean  fromAjax(HttpServletRequest  req){
		String requestType = req.getHeader("X-Requested-With"); 
		return requestType != null;
		
	}	
}

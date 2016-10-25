package com.wonders.frame.ams.utils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wonders.frame.ams.constants.Constant;
import com.wonders.frame.ams.dto.permission.UserDto;

/**
 * 工具类（request,response）
 * @return 
 */
public class AppUtil {

	private static ThreadLocal<HttpServletRequest> request_local= new ThreadLocal<HttpServletRequest>();  
	
    private static ThreadLocal<HttpServletResponse> response_local= new ThreadLocal<HttpServletResponse>();
		
//	public static void setLoginUserInfo(UserDto u){
//    	HttpSession session = getSession();
//    	if(session != null){
//    		session.setAttribute(Constant.LOGIN_USER, u);
//    		session.setAttribute(Constant.LOGIN_USER_TICKET, u.getTicket());
//
//    	}
//
//    }
	
//	public static void setUserMenuResource(MenuDto m ){
//		HttpSession session = getSession();
//		if(session != null && m != null){
//			session.setAttribute(Constant.SYSTEM_MENU_RESOURCE, m);
//		}
//	}
    
	/**
     * @author wangshuqin
     * @return 当前线程中的session对象
     */
    public static HttpSession getSession(){
    	HttpServletRequest req = request_local.get();
    	return req == null ? null : req.getSession();
    }
//    
    public static UserDto getLoginUser(){
    	
    	HttpSession session = getSession();
    	
    	return session == null ? null : (UserDto) session.getAttribute(Constant.LOGIN_USER);
    }
 
    public static boolean isLogin(HttpServletRequest req){
		HttpSession session = req.getSession();
		if(session != null && AppUtil.getLoginUser() != null){
			return true;
		}else{
			return false;
		}
	}
}

package com.wonders.frame.ams.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wonders.frame.ams.constants.Constant;
import com.wonders.frame.ams.utils.Chk;
import com.wonders.frame.ams.utils.RequestUtil;


/**
 * @see 过滤器
 * @return 
 */
public class AppFilter implements Filter{



	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;

		if(filter(req)){
			System.out.println(req.getRequestURI());
			boolean isLogin = false;
			HttpSession session = req.getSession();
			if(session.getAttribute("role")!=null){
				isLogin = true;
			};
			if(isLogin){
				chain.doFilter(req, resp);
			}else{
				
				if(RequestUtil.fromAjax(req)){
					if(!isLogin){
						resp.getWriter().print(Constant.USER_LOGIN_FLAG);
						return;
					}
				}else{
					String path = getNologinRedirectUrl(req);
					resp.sendRedirect(path);
				}
				return;
			}
		}else{
			chain.doFilter(req, resp);
		}
	}

	//未登录时跳转到登录页面时，记录下跳转的请求路径。登录成功后 自动重定向
	//目前只跳转index.do类型的模块首页连接
	public String getNologinRedirectUrl(HttpServletRequest req){
//		String url = req.getRequestURI();
		String tourl = "";
//		if(url.endsWith(".index.do")){
//			tourl = url;
//			String queryString = req.getQueryString();
//			if(Chk.spaceCheck(queryString)){
//				tourl += "?";
//				tourl += queryString;
//			}
//			//配置成完整的请求路径
//			tourl = getBasePath(req) + tourl.replaceFirst(req.getContextPath(),"").replaceFirst(":"+req.getServerPort(),"");
//			System.out.println(tourl);
//			tourl = Base64Utils.encodeToString(tourl.getBytes());
//
//		}
		return getBasePath(req) + "/" + login + ( Chk.spaceCheck(tourl) ? ( "?tourl=" + tourl ) : "");
	}


	/**
	 * 获取服务器请求基本路径
	 * @param req
	 * @return
	 */
	public static String getBasePath(HttpServletRequest req){
		return req.getScheme() + "://"+ req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
	}

	
	public static String login = "login.jsp";

	public static Set<String> passpathSet = new HashSet<String>();
	static{
		passpathSet.add("login.validityCheck.do");
		passpathSet.add("login.check.do");
		passpathSet.add("login.logout.do");
		passpathSet.add("login.jsp");
		passpathSet.add("acdm");
		/**
		 * 首页的航班
		 */
		passpathSet.add("indexFlightSearch.index.do");
		passpathSet.add("indexFlightSearch.search.do");
	}
	
	
	
	private boolean filter(HttpServletRequest req){
		String [] paths = req.getRequestURI().split("\\/");
		String path = paths[paths.length - 1];//.toLowerCase();
//		System.out.println(path);
		if(passpathSet.contains(path)){
			return false;
		}
		
//		if(RequestUtil.fromMobileDevice(req)){
//			return false;
//		}
//		
//		if(RequestUtil.fromAjax(req)){
//			return false;
//		}
		if(path.endsWith(".do")){
			return true;
		}
		if(path.endsWith(".html")){
			return true;
		}
		if(path.endsWith(".jsp")){
			return true;
		}
		if(path.indexOf(".") < 0){
			return true;
		}
		return false;
		

	}
	
	private String getRealPath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		

	}
}

package com.tsystems.logon;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tsystems.ConfigProperties;


public class LogonFilter implements Filter{
	protected final String P_IGNORE_URLS = "ignoreUrls";
	protected final String URL_SPLITER = ",";
	private String[] ignoreUrl = null;
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) servletRequest;    
	    HttpServletResponse response = (HttpServletResponse) servletResponse;  
	    
	    String url = getCurrUrl(request);
	    if (ignoreUrl != null && ignoreUrl.length > 0) {
		    for (int i=0; i<ignoreUrl.length; i++) {
		    	if(ignoreUrl[i].trim().endsWith("*")){
		    		if (url.startsWith(ignoreUrl[i].trim().replaceAll("\\*", ""))||url.endsWith(ignoreUrl[i].trim().replaceAll("\\*", ""))) {
		    			chain.doFilter(servletRequest, servletResponse);
		    			return ;
		    		}
		    	}else{
		    		if (url.equals(ignoreUrl[i].trim())) {
		    			chain.doFilter(servletRequest, servletResponse);
		    			return ;
		    		}
		    	}
		    }
	    }

		String loginUser = (String) request.getSession().getAttribute(ConfigProperties.getProperty(ConfigProperties.Login_Session));
		if(loginUser!=null){
			 chain.doFilter(request,response);  
	         return;
		}else{
	    	response.sendRedirect(request.getContextPath()+"/logon/login.action");
	    	return;
		}
	    
	}

	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		String ignoreUrls = config.getInitParameter(P_IGNORE_URLS);
		if (ignoreUrls != null) {
			ignoreUrl = ignoreUrls.split(URL_SPLITER); 
		}
	}
	
	
	private static String getCurrUrl(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String ctxpath = request.getContextPath();
		if (! "".equals(ctxpath)) {
			return uri.substring(ctxpath.length());
		}else{
			return uri;
		}
	}

}

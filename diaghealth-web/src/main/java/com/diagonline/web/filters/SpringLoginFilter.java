package com.diagonline.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.diagonline.web.utils.SessionUtil;

@Component("springLoginFilter")
public class SpringLoginFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
    private SessionUtil sessionUtil;
	@Value("${baseurl}") 
	private String baseUrl;
	@Value("${loginurl}") 
	private String loginUrl;
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		/*String loginRequestUrl = baseUrl + "/" + loginUrl; 
		StringBuffer requestUrl = httpServletRequest.getRequestURL();
		if(requestUrl.toString().contains(loginRequestUrl)){
			return;
		}*/
		String uri = httpServletRequest.getRequestURI();
		String requestPage = uri.substring(uri.lastIndexOf("/") + 1);
		if(requestPage == null || !(requestPage.contains("login") || requestPage.contains("register") || 
				requestPage.contains(".js") || requestPage.contains(".css"))){		
	        if (sessionUtil.getLoggedInUser(httpServletRequest) == null) {
	            // No user logegd In
	        	String redirectUrl = loginUrl; //"/login" + httpServletRequest.getRequestURI();
	            logger.info("User Not logged In Redirecting to {}", redirectUrl);
	            ((HttpServletResponse) response).sendRedirect(redirectUrl);
	            return;
	        }
		}
        chain.doFilter(httpServletRequest, response);
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}

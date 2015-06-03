package com.diagonline.web.utils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.diagonline.nodes.user.UserDetails;
import com.google.gdata.util.common.base.StringUtil;

@Component("sessionUtil")
public class SessionUtil {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    protected static final String REDIRECT_URL_PARAM_ID = "redirect_url";
    protected static final String LOGGED_IN_USER_SESSION_PARAM = "LIUSP";

    public String getRedirectUrlForRedirectionAfterLogin(HttpServletRequest httpServletRequest) {
        String redirectUrlAfterLogin = getRedirectUrl(httpServletRequest);
        logger.info("redirectUrlAfterLogin from param = " + redirectUrlAfterLogin);
        if (StringUtil.isEmpty(redirectUrlAfterLogin)) {
            redirectUrlAfterLogin = httpServletRequest.getContextPath() + "/index.html";
            logger.info("redirectUrlAfterLogin default = " + redirectUrlAfterLogin);
        }
        return redirectUrlAfterLogin;
    }

    public String getRedirectUrl(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getParameter(REDIRECT_URL_PARAM_ID);
    }

    public void setRedirectUrlInSessiom(HttpServletRequest httpServletRequest) {
        String redirectUrl = getRedirectUrlForRedirectionAfterLogin(httpServletRequest);
        httpServletRequest.getSession(true).setAttribute(REDIRECT_URL_PARAM_ID, redirectUrl);
    }

    public String getAndRemoveRedirectUrlFromSession(HttpServletRequest httpServletRequest) {
        String redirectUrl = (String) httpServletRequest.getSession().getAttribute(REDIRECT_URL_PARAM_ID);
        if (StringUtil.isEmpty(redirectUrl)) {
            redirectUrl = httpServletRequest.getContextPath() + "/index.html";
        } else {
            httpServletRequest.getSession().removeAttribute(REDIRECT_URL_PARAM_ID);
        }

        return redirectUrl;
    }

    public void setLoggedInUser(HttpServletRequest httpServletRequest, UserDetails user) {
        httpServletRequest.getSession(true).setAttribute(LOGGED_IN_USER_SESSION_PARAM, user);
    }

    public UserDetails getLoggedInUser(HttpServletRequest httpServletRequest) {
        return (UserDetails) httpServletRequest.getSession().getAttribute(LOGGED_IN_USER_SESSION_PARAM);
    }
    
    public void addAttribute(HttpServletRequest httpServletRequest, String attribute, Object value){
    	httpServletRequest.getSession().setAttribute(attribute, value);
    }
    
    public Object getAttribute(HttpServletRequest httpServletRequest, String attribute){
    	return httpServletRequest.getSession().getAttribute(attribute);
    }
    
    public void removeAttribute(HttpServletRequest httpServletRequest, String attribute){
    	httpServletRequest.getSession().removeAttribute(attribute);
    }
}

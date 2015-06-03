package com.diagonline.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.diagonline.nodes.user.UserDetails;
import com.diagonline.web.utils.SessionUtil;

@Controller
public class UserLogoutController {
	
	@Autowired
    private SessionUtil sessionUtil;
	@Value("${login.jsp}")
	private String USER_LOGIN_JSP;
	private static Logger logger = LoggerFactory.getLogger(UserLogoutController.class);
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView /*String*/ loginPage(HttpServletRequest httpServletRequest, ModelAndView mv /*ModelMap model*/) throws ApplicationException {
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		sessionUtil.setLoggedInUser(httpServletRequest, null);
		mv.setViewName("redirect:" + USER_LOGIN_JSP);
		httpServletRequest.getSession().invalidate(); //TODO check if needed
		logger.info("User logged out: " + loggedInUser.getUsername() + " type: " + loggedInUser.getUserType()); 
		return mv;
		
	}

}

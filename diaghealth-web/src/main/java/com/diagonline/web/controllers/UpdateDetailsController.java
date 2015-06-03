package com.diagonline.web.controllers;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.diagonline.models.UpdateDetailsDto;
import com.diagonline.nodes.user.UserDetails;
import com.diagonline.services.UserRegisterService;
import com.diagonline.web.utils.BeanUtilsNonNullCopy;
import com.diagonline.web.utils.SessionUtil;

@Controller
public class UpdateDetailsController {
	
	private static final int MIN_PASSWORD_LENGTH = 8;
	
	@Autowired
    private SessionUtil sessionUtil;
	@Value("${user.update.jsp}")
	private String USER_DETAILS_UPDATE_JSP;
	@Value("${welcome.jsp}")
	private String USER_WELCOME_JSP;
	@Autowired
	UserRegisterService userRegisterService;
	private static Logger logger = LoggerFactory.getLogger(UpdateDetailsController.class);
	
	@RequestMapping(value = "/updateDetails", method = RequestMethod.GET)
	public ModelAndView getUserDetails(HttpServletRequest httpServletRequest, ModelAndView mv) throws ApplicationException {
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		UpdateDetailsDto details = new UpdateDetailsDto();
		details.setUserDetails(loggedInUser);
		mv.getModel().put("detailsForm", details);
		mv.setViewName(USER_DETAILS_UPDATE_JSP);
		logger.debug("Request to update details by user: " + loggedInUser.getUsername());
		return mv;
	}
	
	@RequestMapping(value = "/updateDetails", method = RequestMethod.POST)
	public ModelAndView updateUserDetails( @Valid @ModelAttribute("detailsForm") UpdateDetailsDto details,
            BindingResult result, HttpServletRequest httpServletRequest, ModelAndView mv) throws ApplicationException {
		
		mv.setViewName(USER_DETAILS_UPDATE_JSP);
		/*if (result.hasErrors()) {
			 return mv;
	    }*/
		
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		//Password check
		if(!StringUtils.isEmpty(details.getNewPassword()) && !StringUtils.isEmpty(details.getNewPasswordRepeat())){
			if(!details.getNewPassword().equals(details.getNewPasswordRepeat())){
				result.reject("password.error", "Both Given passwords dont match.");
				logger.info("PasswordError. Password: " + details.getNewPassword() + " Repeat: " + details.getNewPasswordRepeat());
			}
			else if(details.getNewPassword().length() < MIN_PASSWORD_LENGTH){
				result.reject("password.error", "Password must be of at least 8 characters.");
				logger.info("PasswordError. Password length not " + MIN_PASSWORD_LENGTH + " password: " + details.getNewPassword());
			}
			else if(!loggedInUser.getPassword().equals(details.getOldPassword())){
				result.reject("password.error", "Entered Password does not match.");
				logger.info("PasswordError. Password dont match with previous pwd" + details.getOldPassword());
			}
		}
		
		if (result.hasErrors()) {
			 return mv;
	    }
		
		UserDetails changedInfo = details.getUserDetails();
		changedInfo.setPassword(details.getNewPassword());
		loggedInUser.setPassword(details.getNewPassword());
		logger.info("Password changed successfully for user: " + loggedInUser.getUsername());
		//TODO the copy should be done field by field
		try {
			new BeanUtilsNonNullCopy().copyProperties(loggedInUser, changedInfo);
		} catch (IllegalAccessException e) {
			logger.error("Error updating values for user: " + loggedInUser.getUsername());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.error("Error updating values for user: " + loggedInUser.getUsername());
			e.printStackTrace();
		}
		userRegisterService.updateDetails(loggedInUser, null);
		mv.setViewName(USER_WELCOME_JSP);
		return mv;
	}

}

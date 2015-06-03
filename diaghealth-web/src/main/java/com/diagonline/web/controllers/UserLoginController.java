package com.diagonline.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.diagonline.models.LoginViewDto;
import com.diagonline.nodes.user.UserDetails;
import com.diagonline.services.SearchService;
import com.diagonline.web.utils.SessionUtil;

@Controller("userLoginController")
@SessionAttributes({"userDetails","userType"})
//@PropertySource("classpath:urlconstants.properties")
public class UserLoginController {
	
	@Autowired
	private SearchService searchService;
	@Value("${login.jsp}")
	private String USER_LOGIN_JSP;
	@Value("${welcome.jsp}")
	private String USER_WELCOME_JSP;
	
	private String USER_ERROR_LOGIN_MSG = "Invalid Login/Password";
	private static Logger logger = LoggerFactory.getLogger(UserLoginController.class);	
	@Autowired
    private SessionUtil sessionUtil;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage(HttpServletRequest httpServletRequest, ModelAndView mv /*ModelMap model*/) throws ApplicationException {
		mv.setViewName(USER_LOGIN_JSP);
		return mv;
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginDetails(@Valid @ModelAttribute("userForm") LoginViewDto userForm,
            BindingResult result, HttpServletRequest httpServletRequest, ModelAndView mv) throws ApplicationException {

		 UserDetails verifyUser = searchService.verifyLoginuser(userForm.getUsername(), userForm.getPassword());
		 
		 String displayPage = USER_LOGIN_JSP;
		 if(verifyUser == null){
			 displayPage = USER_LOGIN_JSP;
			 mv.getModel().put("errorMessage", USER_ERROR_LOGIN_MSG);
			 result.reject("login.error", USER_ERROR_LOGIN_MSG);
			 logger.info("Incorrect username/pwd: " + userForm.getUsername() + "/" + userForm.getPassword());
		 }
		 else {
			 displayPage = "redirect:" + USER_WELCOME_JSP;
			 mv.addObject("userDetails", verifyUser);	
			 mv.addObject("userType", verifyUser.getUserType().name());
			 logger.info("Successfully logged in user: " + verifyUser.getUsername());
		 }
		 
		 mv.setViewName(displayPage);	
		 if(result.hasErrors()){
			 return mv;
		 }		 	 
		 sessionUtil.setLoggedInUser(httpServletRequest, verifyUser);
		 
		 return mv;
				
	}

}

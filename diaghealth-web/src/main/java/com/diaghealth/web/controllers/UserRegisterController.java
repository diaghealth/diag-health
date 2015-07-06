package com.diaghealth.web.controllers;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.diaghealth.mail.MailSenderUtil;
import com.diaghealth.nodes.user.UserDetails;
import com.diaghealth.services.SearchService;
import com.diaghealth.services.UserRegisterService;
import com.diaghealth.services.UserRepositoryService;
import com.diaghealth.util.DateUtilCore;
import com.diaghealth.utils.UserType;
import com.diaghealth.web.utils.ModelBuilder;
import com.diaghealth.web.utils.SessionUtil;

@Controller
/*@SessionAttributes({"userDetails","userAdd"})*/
@SessionAttributes("userDetails")
public class UserRegisterController {	
	
	private static final int MIN_PASSWORD_LENGTH = 8;
	
	@Value("${register.user.jsp}")
	private String USER_REGISTER_JSP;
	@Value("${welcome.jsp}")
	private String LABS_WELCOME_JSP;
	private static final String LOGIN_URL = "login";
	@Value("${show.userlist.jsp}")
	private String USERS_SHOW_JSP;
	@Value("${user.list.attr}")
	private String USER_LIST_ATTR;
	@Value("${user.add.attr}")
	private String USER_ADD_ATTR;
	@Autowired
	MailSenderUtil mailSenderUtil;
	
	private static final String INPUT_DETAILS_ATTR = "detailsForm";
	private static final int MAX_ASCII = 45;
	private static final String EMAIL_SUBJECT = "DiagHealth registration";
	private static final String EMAIL_BODY_WELCOME = "Welcome to DiagHealth";
	private static final int NAME_LENGTH_IN_USERNAME  = 4;
	private static final int ASCCI_START = 48;
	
	@Autowired
	private UserRegisterService userRegisterService;
	@Autowired
	private SearchService searchService;
	
	@Autowired
    private SessionUtil sessionUtil;
	
	private static Logger logger = LoggerFactory.getLogger(UserRegisterController.class);
	
	//TODO uncomment this if drop box slow down
	@ModelAttribute("userTypes")
    public UserType[] userTypes() {
        return UserType.values();
    }
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView  loginPage(HttpServletRequest httpServletRequest, ModelAndView mv /*ModelMap model*/) throws ApplicationException {
		UserDetails details = new UserDetails();
		mv.getModel().put(INPUT_DETAILS_ATTR, details);
		mv.setViewName(USER_REGISTER_JSP);
		return mv;
		
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView loginDetails( @Valid @ModelAttribute("detailsForm") UserDetails detailsForm,
            BindingResult result, HttpServletRequest httpServletRequest, ModelAndView mv) throws ApplicationException {
		
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);		
		String displayPage = USER_REGISTER_JSP;
		mv.setViewName(displayPage);
		 if (result.hasErrors()) {	
			 logger.error("User Register Error: " + result.getAllErrors());
			 return mv;
	     }
		 
		 //Generate Username / password if not given
		 if(detailsForm.getUsername() == null || detailsForm.getUsername().length() == 0){
			 String userNameDefault = detailsForm.getFirstname().substring(0, NAME_LENGTH_IN_USERNAME).toLowerCase() + detailsForm.getPhoneNumber();
			 detailsForm.setUsername(userNameDefault);
			 logger.info("Setting default username: " + userNameDefault);
		 }
		 
		 if(detailsForm.getPassword() == null || detailsForm.getPassword().length() == 0){
			 Random randomGenerator = new Random();
			 char random1 = (char)(ASCCI_START + randomGenerator.nextInt(MAX_ASCII));
			 char random2 = (char)(ASCCI_START + randomGenerator.nextInt(MAX_ASCII));
			 String passwordDefault = detailsForm.getFirstname().substring(0, NAME_LENGTH_IN_USERNAME).toLowerCase() + detailsForm.getPhoneNumber() + 
					 Character.toString(random1) + Character.toString(random2);
			 detailsForm.setPassword(passwordDefault);
			 logger.info("Setting default password: " + passwordDefault);
			 
		 } else if(detailsForm.getPassword().length() < MIN_PASSWORD_LENGTH){
			 result.addError(new ObjectError("PasswordError", "Password must be of at least 8 characters."));
			 logger.error("Password length error");
			 return mv;
		 }
		 
		 UserDetails userExists = searchService.userExists(detailsForm);
		 if(userExists != null){
			 //result.addError(new ObjectError("UserNameExists", "User Name Already Exists"));
			 logger.error("User already exists: " + detailsForm.getUsername());
			 Set<UserDetails> userList = new HashSet<UserDetails>();
			 userList.add(userExists);
			 new ModelBuilder().buildUserListModel(loggedInUser, mv, detailsForm.getUserType(), userList);
			 mv.getModel().put(USER_LIST_ATTR, loggedInUser.getRelatedList());
			 displayPage = USERS_SHOW_JSP;
			 mv.setViewName(displayPage);
			 return mv;
		 }
		 boolean userSaved = false;
				 
		 
		 detailsForm = UserRepositoryService.getNewUserNode(detailsForm);
		 detailsForm.setDateCreated(DateUtilCore.getCurrentDateIST());
		 if(loggedInUser != null){ //user already logged in and registering new user
			 //set relation between logged in user and new registered user
			 logger.info("Logged in username: " + loggedInUser.getUsername() + 
					 " type: " + loggedInUser.getUserType() + 
					 " creating/registering new username: " + detailsForm.getUsername() + " type " + detailsForm.getUserType());
			 			 
			 displayPage = USERS_SHOW_JSP;			 
			 detailsForm.setCreatorId(loggedInUser.getId());
			 loggedInUser.addRelated(detailsForm);
			 userSaved = userRegisterService.saveDetails(loggedInUser, null);	
			 Set<UserDetails> userList = new HashSet<UserDetails>();
			 userList.add(detailsForm);
			 new ModelBuilder().buildUserListModel(loggedInUser, mv, detailsForm.getUserType(), userList);
			 mv.getModel().put(USER_LIST_ATTR, loggedInUser.getRelatedList());
		 } else {
			 logger.info("Registering new username: " + detailsForm.getUsername() + " type: " + detailsForm.getUserType());
			 userSaved = userRegisterService.saveDetails(detailsForm, null);
			 displayPage = "redirect:" + LOGIN_URL; //redirect to Login page to login 
		 }
		 
		 sendMail(detailsForm);
		 //Save new Registered user and its relationship			 
		 if(!userSaved) //could not save the new user
			 displayPage = USER_REGISTER_JSP;
		 		 
		 mv.setViewName(displayPage);
		 return mv;
				
	}
	
	private void sendMail(UserDetails detailsForm){
		//MailSenderUtil mailSenderUtil = new MailSenderUtil();
		if(detailsForm.getEmail() == null || detailsForm.getEmail().isEmpty()){
			return;
		}
		logger.info("Sending mail to user: " + detailsForm.getUsername() + " email: " + detailsForm.getEmail());
		String emailBody = EMAIL_BODY_WELCOME;
		emailBody += "\n Username: " + detailsForm.getUsername();
		emailBody += "\n Password: " + detailsForm.getPassword();
		mailSenderUtil.addToMail(detailsForm.getEmail());
		mailSenderUtil.setSubject(EMAIL_SUBJECT);
		mailSenderUtil.setBody(emailBody);
		//mailSenderUtil.setFromMail(FROM_EMAIL);
		
		mailSenderUtil.sendMail();
	}

}

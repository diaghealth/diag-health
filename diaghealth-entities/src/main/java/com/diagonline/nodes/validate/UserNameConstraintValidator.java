package com.diagonline.nodes.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class UserNameConstraintValidator implements ConstraintValidator<UserName, String>{
	private static final String USERNAME_PATTERN =  "[a-z0-9]"; 
	private Pattern pattern;  
	private Matcher matcher;
	
	 
	public boolean isValid(String usernameField, ConstraintValidatorContext cxt) {
 	//Empty username is fine
     if(StringUtils.isEmpty(usernameField)) {
         return true;
     }
     
	   pattern = Pattern.compile(USERNAME_PATTERN);  
	   matcher = pattern.matcher(usernameField);          	   
	    return matcher.matches();
	 }

	@Override
	public void initialize(UserName arg0) {	}
}

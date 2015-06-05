package com.diaghealth.nodes.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class EmailConstraintValidator implements ConstraintValidator<Email, String>{
	
	private static final String EMAIL_PATTERN =  "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"  
			   + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; 
	private Pattern pattern;  
	private Matcher matcher;
	
	public void initialize(Email phone) { }
	 
    public boolean isValid(String emailField, ConstraintValidatorContext cxt) {
    	//Empty email is fine
        if(StringUtils.isEmpty(emailField)) {
            return true;
        }
        
	   pattern = Pattern.compile(EMAIL_PATTERN);  
	   matcher = pattern.matcher(emailField);          	   
       return matcher.matches();
    }
}

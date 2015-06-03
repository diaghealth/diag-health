package com.diagonline.nodes.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneConstraintValidator implements ConstraintValidator<PhoneNumber, Long> {
	
	/*private String MOBILE_PATTERN = "[1-9][0-9]{9}"; 
	private Pattern pattern;  
	private Matcher matcher;  */
 
    public void initialize(PhoneNumber phone) { }
 
    public boolean isValid(Long phoneField, ConstraintValidatorContext cxt) {
        if(phoneField == null) {
            return false;
        }
        
        return phoneField.compareTo(Long.valueOf("1000000000")) >0  && phoneField.compareTo(Long.valueOf("9999999999")) < 0;
        
	   /*pattern = Pattern.compile(MOBILE_PATTERN);  
	   matcher = pattern.matcher(phoneField);          	   
       return matcher.matches();*/
    }
}

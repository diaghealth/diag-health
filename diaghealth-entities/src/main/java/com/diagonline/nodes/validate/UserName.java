package com.diagonline.nodes.validate;

import javax.validation.Payload;

public @interface UserName {
	String message() default "Username must be alphanumeric in lowercase";
    
    Class<?>[] groups() default {};
     
    Class<? extends Payload>[] payload() default {};
}

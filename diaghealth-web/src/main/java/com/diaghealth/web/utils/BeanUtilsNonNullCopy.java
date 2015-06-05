package com.diaghealth.web.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.util.StringUtils;

public class BeanUtilsNonNullCopy extends BeanUtilsBean {	
	@Override
    public void copyProperty(Object dest, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
        //if(value==null || (value instanceof String && StringUtils.isEmpty(value)))return;
		if(StringUtils.isEmpty(value)) return;
        super.copyProperty(dest, name, value);
    }
}

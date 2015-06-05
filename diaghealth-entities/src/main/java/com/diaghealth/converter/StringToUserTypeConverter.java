package com.diaghealth.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.diaghealth.utils.UserType;

@Component
public class StringToUserTypeConverter implements Converter<String, UserType>{

	@Override
	public UserType convert(String source) {
		return UserType.valueOf(source);
	}

}

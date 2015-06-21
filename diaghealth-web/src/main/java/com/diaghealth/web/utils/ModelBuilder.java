package com.diaghealth.web.utils;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;

import com.diaghealth.nodes.user.UserDetails;
import com.diaghealth.utils.UserType;

public class ModelBuilder {
	
	@Value("${user.list.attr}")
	private static String USER_LIST_ATTR;
	@Value("${user.history.attr}")
	private static String USER_HISTORY_ATTR;
	
	public void buildUserListModel(UserDetails loggedInUser, ModelAndView mv, UserType listType, Set<UserDetails> relatedUsers){
		mv.getModel().put("userList", relatedUsers);
		mv.getModel().put("userHistory", 1);
		if((loggedInUser.getUserType() == UserType.DOCTOR || loggedInUser.getUserType() == UserType.CLINIC)
				 && listType == UserType.PATIENT){
			 mv.getModel().put("buildReceipt", 1);
		}
	}

}

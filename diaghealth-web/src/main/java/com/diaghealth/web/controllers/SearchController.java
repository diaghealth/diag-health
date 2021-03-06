package com.diaghealth.web.controllers;

import java.util.HashSet;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.diaghealth.models.SearchUserViewDto;
import com.diaghealth.nodes.user.UserDetails;
import com.diaghealth.services.SearchService;
import com.diaghealth.utils.UserType;

@Controller
public class SearchController {
	
	private static final String SEARCH_FORM = "searchForm";
	@Value("${search.user.jsp}")
	private String SEARCH_USER_JSP;
	@Value("${search.user.results.jsp}")
	private String SEARCH_USER_RESULTS_JSP;	
	@Value("${show.userlist.jsp}")
	private String USERS_SHOW_JSP;
	@Value("${user.list.attr}")
	private String USER_LIST_ATTR;
	@Value("${user.add.attr}")
	private String USER_ADD_ATTR;
	
	private static Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	private SearchService searchService;
	
	/*@ModelAttribute("userTypes")
    public UserType[] userTypes() {
        return UserType.values();
    }*/
	
	@RequestMapping(value = "/searchUser", method = RequestMethod.GET)
	public ModelAndView searchPage(HttpServletRequest httpServletRequest, ModelAndView mv /*ModelMap model*/) throws ApplicationException {
		SearchUserViewDto searchForm = new SearchUserViewDto();
		mv.getModel().put(SEARCH_FORM, searchForm);
		mv.setViewName(SEARCH_USER_JSP);
		mv.getModel().put("userTypes", UserType.values());
		return mv;
		
	}
	
	@RequestMapping(value = "/searchUser", method = RequestMethod.POST)
	public ModelAndView searchResult( @Valid @ModelAttribute("searchForm") SearchUserViewDto searchForm,
            BindingResult result, Map<String, Object> model, ModelAndView mv) throws ApplicationException {
		
		if (result.hasErrors()) {
			mv.setViewName(SEARCH_FORM);
			 return mv;
	     }
		
		Set<UserDetails> searchResults = searchService.searchUsers(searchForm);
		
		if(searchResults == null)
			searchResults = new HashSet<UserDetails>(); //Empty set
		
		mv.getModel().put(USER_LIST_ATTR, searchResults);
		mv.getModel().put(USER_ADD_ATTR, 1); 
		mv.setViewName(USERS_SHOW_JSP);
		logger.error("Search Result returned " + searchResults.size() + " users");
		return mv;
		
	}

}

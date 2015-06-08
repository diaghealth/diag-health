package com.diaghealth.web.controllers;

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

import com.diaghealth.models.SearchViewDto;
import com.diaghealth.models.labtest.LabTestListViewDto;
import com.diaghealth.nodes.labtest.LabTestDetails;
import com.diaghealth.nodes.user.UserDetails;
import com.diaghealth.repository.LabTestDetailsRepo;
import com.diaghealth.services.LabTestDetailsService;
import com.diaghealth.utils.UserType;
import com.diaghealth.web.utils.SessionUtil;

@Controller
public class AdminTestTypeAddController {

	@Autowired
    private SessionUtil sessionUtil;
	@Autowired
	private LabTestDetailsService labTestDetailsService;
	@Value("${unauthorized.jsp}")
	private String UNAUTHORIZED_JSP;
	@Value("${admin.testadd.jsp}")
	private String LAB_TEST_ADMIN_JSP;
	
	private static Logger logger = LoggerFactory.getLogger(AdminTestTypeAddController.class);
	
	
	@RequestMapping(value = "/addLabTestType", method = RequestMethod.GET)
	public ModelAndView addLabTestTypeDetails(HttpServletRequest httpServletRequest, ModelAndView mv /*ModelMap model*/) throws ApplicationException {
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		if(!sessionUtil.isAdminUser(httpServletRequest)){
			mv.setViewName(UNAUTHORIZED_JSP);
			return mv;
		}
		LabTestDetails labTestDetails = new LabTestDetails();
		Set<LabTestDetails> labTestList = getLabTestExistList(loggedInUser);
		mv.getModel().put("labTest", labTestDetails);
		mv.getModel().put("labTestExistList", labTestList);
		mv.setViewName(LAB_TEST_ADMIN_JSP);
		return mv;		
	}
	
	@RequestMapping(value = "/saveLabTestType", method = RequestMethod.POST)
	public ModelAndView saveLabTestTypeDetails(@Valid @ModelAttribute("labTest") LabTestDetails labTest,			
            BindingResult result, HttpServletRequest httpServletRequest, ModelAndView mv) throws ApplicationException { 
		if(result.hasErrors()){
			return mv;
		}
		//Change to upper case
		labTest.setName(labTest.getName().toUpperCase());
		labTest.setType(labTest.getType().toUpperCase());
		
		if(!labTestDetailsService.findIfExists(labTest)){
			logger.info("Added new Test Type: " + labTest);
			labTestDetailsService.save(labTest);
		} else {
			result.reject("test.exists", "Test Already Exists");
		}
		mv.setViewName("redirect:" + "/addLabTestType");
		return mv;		
	}
	
	public Set<LabTestDetails> getLabTestExistList(UserDetails loggedInUser){
		return labTestDetailsService.getAllTests();		
	}
}

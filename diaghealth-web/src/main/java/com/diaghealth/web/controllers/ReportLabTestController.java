package com.diaghealth.web.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.diaghealth.models.TestListViewDto;
import com.diaghealth.nodes.labtest.LabTestDoneObject;
import com.diaghealth.nodes.user.UserDetails;
import com.diaghealth.services.LabTestService;
import com.diaghealth.utils.UserType;
import com.diaghealth.web.utils.SessionUtil;

@Controller
public class ReportLabTestController {

	@Autowired
    private SessionUtil sessionUtil;
	@Value("${unauthorized.jsp}")
	private String UNAUTHORIZED_JSP;
	@Value("${show.usertest.report.jsp}")
	private String USERS_TESTS_REPORT_SHOW_JSP;
	@Autowired
	private LabTestService labTestService;
	private static Logger logger = LoggerFactory.getLogger(ReportLabTestController.class);
	
	@RequestMapping(value = "/showReport", method = RequestMethod.GET)
	public ModelAndView showLabTestReport(@RequestParam Long id, @RequestParam String userType, HttpServletRequest httpServletRequest, ModelAndView mv) throws ApplicationException {
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		if(UserType.valueOf(userType) != UserType.PATIENT){
			mv.setViewName(UNAUTHORIZED_JSP);
			return mv;
		}
		Set<LabTestDoneObject> testSet = new HashSet<LabTestDoneObject>();
		testSet = labTestService.getTestsByUserId(id, loggedInUser.getId());
		TestListViewDto testView = new TestListViewDto();
		testView.setId(id);
		testView.setTestList(testSet);
		mv.getModel().put("testViewObject", testView);
		mv.getModel().put("buildResult", 1);
		mv.setViewName(USERS_TESTS_REPORT_SHOW_JSP);
		logger.info("Relation Test Report requested by user: " + loggedInUser.getUsername() + " for userId: " + id + " userType " + userType);
		return mv;
	}
}

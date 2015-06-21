package com.diaghealth.web.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.time.DateUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.diaghealth.models.TestListViewDto;
import com.diaghealth.nodes.labtest.LabTestDoneObject;
import com.diaghealth.nodes.user.UserDetails;
import com.diaghealth.services.LabTestService;
import com.diaghealth.services.SearchService;
import com.diaghealth.web.utils.SessionUtil;

@Controller
public class ShowTestPaymentHistoryController {
	
	@Autowired
    private SessionUtil sessionUtil;
	@Autowired
	private SearchService searchService;
	@Value("${show.usertest.payment.jsp}")
	private String USERS_TESTS_PAYMENT_SHOW_JSP;
	@Autowired
	private LabTestService labTestService;
	private static Logger logger = LoggerFactory.getLogger(ShowTestPaymentHistoryController.class);
	
	@RequestMapping(value = "/userRelationHistory", method = RequestMethod.GET)
	public ModelAndView showHistory(@RequestParam Long id, @RequestParam String userType, HttpServletRequest httpServletRequest, ModelAndView mv /*ModelMap model*/) throws ApplicationException {
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		Set<LabTestDoneObject> testSet = new HashSet<LabTestDoneObject>();
		testSet = labTestService.getTestsByUserId(id, loggedInUser.getId());
		TestListViewDto testView = new TestListViewDto();
		testView.setId(id);
		testView.setTotalPrice(getTotalAmount(testSet));
		testView.setTestList(testSet);
		mv.getModel().put("testViewObject", testView);
		mv.setViewName(USERS_TESTS_PAYMENT_SHOW_JSP);
		logger.info("Relation History requested by user: " + loggedInUser.getUsername() + " for userId: " + id + " userType " + userType);
		return mv;
	}
	
	
	public int getTotalAmount(Set<LabTestDoneObject> testSet){
		int totalAmount = 0;
		for(LabTestDoneObject test: testSet){
			totalAmount += test.getPrice();
		}
		return totalAmount;
		
	}
	
	
	@RequestMapping(value = "/filterTestsOnDate", method = RequestMethod.POST)
	public ModelAndView showHistory( @Valid @ModelAttribute("testViewObject") TestListViewDto testView, BindingResult result, HttpServletRequest httpServletRequest, ModelAndView mv /*ModelMap model*/) throws ApplicationException {
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		Set<LabTestDoneObject> testSet = new HashSet<LabTestDoneObject>();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		mv.setViewName(USERS_TESTS_PAYMENT_SHOW_JSP);
		Date fromDate = null;
		Date toDate = null;
		try{
			fromDate = format.parse(testView.getFromDate());
			toDate = DateUtils.addDays(format.parse(testView.getToDate()),1);	//start of next day	
			
		}catch (Exception e){
			logger.error("Invalid Date format: " + testView.getFromDate() + " " + testView.getToDate());
			result.reject("error.code", "Invalid Date Format: MM/DD/YYYY");
		}
		
		if(result.hasErrors()){
			return mv;
		}
		if(fromDate != null && toDate !=null)
			testSet = labTestService.getTestsByUserIdAndDate(testView.getId(), loggedInUser.getId(), fromDate, toDate);
		
		testView.setTotalPrice(getTotalAmount(testSet));
		testView.setTestList(testSet);
		
		mv.getModel().put("testViewObject", testView);		
		logger.info("Relation History requested by user: " + loggedInUser.getUsername() + " for userId: " + testView.getId() + " fromDate " + testView.getFromDate() 
				+ " toDate: " + testView.getToDate());
		return mv;
	}

}

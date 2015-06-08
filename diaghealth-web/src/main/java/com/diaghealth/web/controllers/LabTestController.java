package com.diaghealth.web.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.diaghealth.models.labtest.LabTestListViewDto;
import com.diaghealth.nodes.labtest.LabTestAvailablePrice;
import com.diaghealth.nodes.labtest.LabTestDetails;
import com.diaghealth.nodes.user.UserDetails;
import com.diaghealth.services.LabTestService;
import com.diaghealth.utils.UserType;
import com.diaghealth.web.utils.LabTestUtils;
import com.diaghealth.web.utils.SessionUtil;

@Controller
@SessionAttributes("labTests")
public class LabTestController {
	
	
	@Value("${lab.tests.jsp}")
	private String LAB_TESTS_JSP;
	@Value("${unauthorized.jsp}")
	private String UNAUTHORIZED_JSP;
	@Value("${welcome.jsp}")
	private String USER_WELCOME_JSP;
	@Value("${labtest.row.insert.jsp}")
	private String LABTEST_ROW_INSRT_JSP;
	@Autowired
	private LabTestService labTestService;
	@Autowired
    private SessionUtil sessionUtil;
	
	private static Logger logger = LoggerFactory.getLogger(LabTestController.class);
	
	@ModelAttribute("labTests")
	public LabTestListViewDto getTestList()
	{
		LabTestListViewDto labTests = new LabTestListViewDto();
		return labTests;
	}

	@RequestMapping(value = "/labTests", method = RequestMethod.GET)
	public ModelAndView showLabTestList(HttpServletRequest httpServletRequest, ModelAndView mv) throws ApplicationException {
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		if(loggedInUser.getUserType() != UserType.LAB){
			mv.setViewName(UNAUTHORIZED_JSP);
			return mv;
		}
		
		List<LabTestAvailablePrice> tests = labTestService.getTestsForUser(loggedInUser);
		List<LabTestDetails> allTests = new ArrayList<LabTestDetails>(labTestService.getAllAvailableTests());
		LabTestUtils.putAllTestsInModel(mv, allTests);
		LabTestListViewDto testListObject = new LabTestListViewDto();
		if(tests != null){
			AutoPopulatingList<LabTestAvailablePrice> autoList = new AutoPopulatingList<LabTestAvailablePrice>(LabTestAvailablePrice.class);
			autoList.addAll(tests);
			testListObject.setTestList(autoList);			
		}
		
		//AutoPopulatingList<Integer> deletedIndexList = new AutoPopulatingList<Integer>(Integer.class);
		testListObject.setDeletedIndexList(new ArrayList<Integer>());
		mv.getModel().put("labTests", testListObject);
		mv.setViewName(LAB_TESTS_JSP);
		logger.info("Populated tests list for user: " + loggedInUser.getUsername());
		return mv;
		
	}
	
	@RequestMapping(value = "/saveTests", method = RequestMethod.POST)
	public ModelAndView addTestsToExistingLabTests(@Valid @ModelAttribute("labTests") LabTestListViewDto labTests,
            BindingResult result, HttpServletRequest httpServletRequest, ModelAndView mv) throws ApplicationException {
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		if(loggedInUser.getUserType() != UserType.LAB){
			mv.setViewName(UNAUTHORIZED_JSP);
			return mv;
		}
		LabTestListViewDto testList = (LabTestListViewDto)sessionUtil.getAttribute(httpServletRequest, "labTests");
		boolean testsSaved = true;
		if(testList != null){
			testsSaved = saveTests(loggedInUser, testList.getTestList(), testList.getDeletedIndexList());
		}
		
		if(!testsSaved){
			logger.error("Cannot save tests by user: " + loggedInUser.getUsername());
		}
		sessionUtil.removeAttribute(httpServletRequest, "labTests");
		mv.setViewName(USER_WELCOME_JSP);
		logger.info("Saved tests by user: " + loggedInUser.getUsername());
		return mv;
	}
	
	/*@RequestMapping(method = RequestMethod.GET, value="/addTestRow")
	protected ModelAndView appendStudentField(@RequestParam Integer fieldId, ModelAndView mv)
	{	
		mv.getModel().put("testCount", fieldId);
		mv.setViewName(LABTEST_ROW_INSRT_JSP);
		return mv;
	}*/
	
	@RequestMapping(method = RequestMethod.GET, value="/deleteTestRow")
	protected ModelAndView deleteTestRow(@RequestParam Integer fieldId, ModelAndView mv, HttpServletRequest httpServletRequest)
	{	
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		LabTestListViewDto testList = (LabTestListViewDto)sessionUtil.getAttribute(httpServletRequest, "labTests");
		if(testList != null){
			/*Only save deleted indexes which are there in testList*/
			if(fieldId.intValue() < testList.getTestList().size()){
				
				logger.debug("Request to test delete row " + fieldId.intValue() + " for user: " + loggedInUser.getUsername());
				testList.getDeletedIndexList().add(fieldId);
			}
		}
		/*mv.getModel().put("testCount", fieldId);
		mv.setViewName(LABTEST_ROW_INSRT_JSP);*/
		return mv;
	}
	
	private boolean saveTests(UserDetails loggedInUser, AutoPopulatingList<LabTestAvailablePrice> testList, List<Integer> deletedIndexes){
		List<LabTestAvailablePrice> deleteList = new ArrayList<LabTestAvailablePrice>();
		Set<LabTestAvailablePrice> saveList = new HashSet<LabTestAvailablePrice>();
		for(Integer index: deletedIndexes){
			LabTestAvailablePrice obj = (LabTestAvailablePrice) testList.get(index.intValue());
			if(obj != null){
				deleteList.add(obj);
			}			
		}
		testList.removeAll(deleteList);
		logger.debug("Deleting tests for user: " + loggedInUser.getUsername() + " tests: " + deleteList);
		labTestService.deleteTests(deleteList); //TODO delete later
		
		for(int i=0;i < testList.size();i++){
			saveList.add((LabTestAvailablePrice)testList.get(i));
		}			
		
		logger.debug("Saving tests for user: " + loggedInUser.getUsername() + " tests: " + saveList);
	
		return labTestService.saveTestsPrice(saveList, loggedInUser);
	}
	
	
}

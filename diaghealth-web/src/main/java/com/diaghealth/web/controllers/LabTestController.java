package com.diaghealth.web.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.diaghealth.models.SearchTestViewDto;
import com.diaghealth.models.labtest.LabTestListViewDto;
import com.diaghealth.nodes.labtest.LabTestAvailablePrice;
import com.diaghealth.nodes.labtest.LabTestDetails;
import com.diaghealth.nodes.labtest.LabTestDoneObject;
import com.diaghealth.nodes.user.UserDetails;
import com.diaghealth.services.LabTestService;
import com.diaghealth.utils.UserGender;
import com.diaghealth.utils.UserType;
import com.diaghealth.web.utils.LabTestUtils;
import com.diaghealth.web.utils.SessionUtil;

@Controller
@SessionAttributes("labTests")
public class LabTestController {
	
	private static final String SEARCH_FORM = "searchForm";
	@Value("${lab.tests.jsp}")
	private String LAB_TESTS_JSP;
	@Value("${update.lab.tests.jsp}")
	private String UPDATE_TESTS_JSP;
	@Value("${unauthorized.jsp}")
	private String UNAUTHORIZED_JSP;
	@Value("${welcome.jsp}")
	private String USER_WELCOME_JSP;
	@Value("${labtest.row.insert.jsp}")
	private String LABTEST_ROW_INSRT_JSP;
	@Value("${search.test.jsp}")
	private String SEARCH_TEST_JSP;
	@Value("${search.test.results.jsp}")
	private String SEARCH_TEST_RESULTS_JSP;
	@Autowired
	private LabTestService labTestService;
	@Autowired
    private SessionUtil sessionUtil;
	
	private static Logger logger = LoggerFactory.getLogger(LabTestController.class);
	
	@ModelAttribute("userGender")
    public UserGender[] userGender() {
        return UserGender.values();
    }
	
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
		//mv.setViewName(LAB_TESTS_JSP);
		mv.setViewName(UPDATE_TESTS_JSP);
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
		if(deleteList.size() > 0)
			labTestService.deleteTests(deleteList); //TODO delete later
		
		//Most recent are at the ends (bottom), discard duplicates from top
		for(int i=testList.size()-1;i >= 0;i--){
			if(!existsInList(saveList, (LabTestAvailablePrice)testList.get(i))){
				saveList.add((LabTestAvailablePrice)testList.get(i));
			}
		}			
		
		logger.debug("Saving tests for user: " + loggedInUser.getUsername() + " tests: " + saveList);
	
		return labTestService.saveTestsPrice(saveList, loggedInUser);
	}
	
	private boolean existsInList(Set<LabTestAvailablePrice> list, LabTestAvailablePrice element){
		if(list.size() > 0){
			for(LabTestAvailablePrice test: list){
				if(test.getName().equals(element.getName())){
					return true;
				}
			}
		}
		return false;
	}
	
	@RequestMapping(value = "/searchTests", method = RequestMethod.POST)
	public ModelAndView searchTestResult( @Valid @ModelAttribute("searchForm") SearchTestViewDto searchForm,
            BindingResult result, Map<String, Object> model, ModelAndView mv) throws ApplicationException {
		
		if (result.hasErrors()) {
			mv.setViewName(SEARCH_FORM);
			 return mv;
	     }
		
		Set<LabTestDoneObject> searchResults = labTestService.searchTests(searchForm);
		
		if(searchResults == null)
			searchResults = new HashSet<LabTestDoneObject>(); //Empty set
		
		/*mv.getModel().put(USER_LIST_ATTR, searchResults);
		mv.getModel().put(USER_ADD_ATTR, 1); 
		mv.setViewName(USERS_SHOW_JSP);*/
		logger.error("Search Result returned " + searchResults.size() + " users");
		return mv;
	}
		
		
	@RequestMapping(value = "/searchTests", method = RequestMethod.GET)
	public ModelAndView searchTestPage(HttpServletRequest httpServletRequest, ModelAndView mv /*ModelMap model*/) throws ApplicationException {
		SearchTestViewDto searchForm = new SearchTestViewDto();
		mv.getModel().put(SEARCH_FORM, searchForm);
		mv.setViewName(SEARCH_TEST_JSP);
		mv.getModel().put("userTypes", UserType.values());
		return mv;
	}
	
	
}

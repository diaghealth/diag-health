package com.diaghealth.web.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import org.springframework.util.AutoPopulatingList;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.diaghealth.mail.MailSenderUtil;
import com.diaghealth.models.ReceiptViewDto;
import com.diaghealth.nodes.ReceiptObject;
import com.diaghealth.nodes.labtest.LabTestAvailablePrice;
import com.diaghealth.nodes.labtest.LabTestDoneObject;
import com.diaghealth.nodes.labtest.LabTestTreeNode;
import com.diaghealth.nodes.user.UserDetails;
import com.diaghealth.services.LabTestService;
import com.diaghealth.services.ReceiptService;
import com.diaghealth.services.SearchService;
import com.diaghealth.services.UserRegisterService;
import com.diaghealth.util.DateUtilCore;
import com.diaghealth.utils.LabTestTreeUtils;
import com.diaghealth.utils.UserType;
import com.diaghealth.web.utils.LabTestUtils;
import com.diaghealth.web.utils.ModelBuilder;
import com.diaghealth.web.utils.SessionUtil;
import com.google.gdata.util.common.base.StringUtil;

@Controller
public class ReceiptController {
	
	@Autowired
    private SessionUtil sessionUtil;
	@Autowired
	MailSenderUtil mailSenderUtil;
	@Autowired
	private ReceiptService receiptService;
	@Value("${receipt.view.jsp}")
	private String RECEIPT_VIEW_JSP;
	@Value("${receipt.search.jsp}")
	private String RECEIPT_SEARCH;
	@Value("${show.userlist.jsp}")
	private String USERS_SHOW_JSP;
	@Value("${unauthorized.jsp}")
	private String UNAUTHORIZED_JSP;
	@Autowired
	private LabTestService labTestService;
	@Autowired
	private SearchService searchService;
	@Autowired
	private UserRegisterService userRegisterService;
	private static final String TEST_REPORT_SUBJECT = "Diaghealth Test Report";
	
	private static Logger logger = LoggerFactory.getLogger(ReceiptController.class);
	
	@RequestMapping(value = "/buildReceipt", method = RequestMethod.GET)
	public ModelAndView buildReceipt(@RequestParam Long id, /*@RequestParam String firstname,*/ HttpServletRequest httpServletRequest, ModelAndView mv /*ModelMap model*/) throws ApplicationException {
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		UserDetails subjectUser = searchService.findById(id);
		
		ReceiptObject receipt = receiptService.createReceipt(loggedInUser, subjectUser);
		logger.info("Created Receipt by User: " + loggedInUser.getUsername() + " for user: " + subjectUser.getUsername()
				+ " ReceiptId: " + receipt.getReceiptId());
		ReceiptViewDto receiptView = new ReceiptViewDto();
		receiptView.setReceipt(receipt);
		mv.getModel().put("receiptView", receiptView);
		mv.setViewName(RECEIPT_VIEW_JSP);
		return mv;
	}
	
	@RequestMapping(value = "/searchReceipt", method = RequestMethod.GET)
	public ModelAndView searchReceiptGet(HttpServletRequest httpServletRequest, ModelAndView mv /*ModelMap model*/) throws ApplicationException {
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		mv.setViewName(RECEIPT_SEARCH);
		Set<ReceiptObject> allReceipts = receiptService.getAllReceiptByUser(loggedInUser.getId());
		mv.getModel().put("allReceipts", allReceipts);
		return mv;
	}
	
	/*
	 * Input parameters - For valid receiptId, Long id is not required
	 * 					  For DUMMY recieptId, Long id is required
	 */
	@RequestMapping(value="/searchReceiptById", method = RequestMethod.GET)
	public ModelAndView searchReceiptByIdInRequest(@RequestParam String receiptId, @RequestParam Long id, HttpServletRequest httpServletRequest, ModelAndView mv /*ModelMap model*/) throws ApplicationException {
		
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		ReceiptObject receipt = new ReceiptObject();
		
		if(!receiptId.equals("DUMMY")){
			receipt = receiptService.findReceipt(receiptId);
		} else {
			receipt.setSubject(searchService.findById(id));
			Set<UserDetails> relatedUsers = new HashSet<UserDetails>();
			receipt.setReceiptId(receiptService.getRandomUniqueString());
			relatedUsers.add(loggedInUser);
			receipt.setRelatedUsers(relatedUsers);
			receiptService.save(receipt);
		}
		
		if(receipt == null){
			logger.info("Invalid Receipt Id: " + receiptId + " by user: " + loggedInUser.getUsername());
			mv.setViewName(UNAUTHORIZED_JSP);
			return mv;
		}
		createReceiptViewModel(mv, receipt, httpServletRequest);
		return mv;
	}
	
	@RequestMapping(value = "/searchReceipt", method = RequestMethod.POST)
	public ModelAndView searchReceiptPost(@Valid @ModelAttribute("receiptObj") ReceiptObject receiptObj, BindingResult result, HttpServletRequest httpServletRequest, ModelAndView mv /*ModelMap model*/) throws ApplicationException {

		ReceiptObject receipt = receiptService.findReceipt(receiptObj.getReceiptId());
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		boolean isInvalid = false;
		mv.setViewName(RECEIPT_SEARCH);
		if(receipt == null){
			//mv.getModel().put("errorMessage", "Invalid Id");
			result.reject("receipt.invalid", "Invalid Id");
			logger.info("Invalid Receipt Id: " + receiptObj.getReceiptId() + " by user: " + loggedInUser.getUsername());
			isInvalid = true;
		}
		else if(receipt.getValidTill() != null && DateUtilCore.getCurrentDateIST().compareTo(receipt.getValidTill()) > 0){
			//mv.getModel().put("errorMessage", "Receipt has expired");
			result.reject("receipt.expired", "Receipt has expired");
			logger.info("Expired Receipt: " + receipt.getReceiptId() + " Expiry: " + receipt.getValidTill() + 
					" Created: " + receipt.getDateCreated());
			isInvalid = true;
		}
		if (result.hasErrors() || isInvalid) {
			logger.error("Error retrieving receipt : " + result.getAllErrors());
			 return mv;
	    }
		/*ReceiptViewDto receiptView = new ReceiptViewDto();		
		receiptView.setReceipt(receipt);
		if(loggedInUser.getUserType() == UserType.LAB && 
				!receipt.getRelatedUsers().contains(loggedInUser)){
			receiptView.setCurrentLab(loggedInUser);
		}
		receiptView.getTestList().addAll(receipt.getLabTestDoneObject());
		receiptView.setTestList(receiptView.getTestList()); //TODO why ?
		mv.getModel().put("receiptView", receiptView);
		List<LabTestAvailablePrice> availableTests = labTestService.getAvailableTestsInLab(loggedInUser.getId());
		//List<LabTestDetailsDto> allTests = labTestService.getAllAvailableTests();
		LabTestUtils.putAvailableTestsInModel(mv, availableTests);
		sessionUtil.removeAttribute(httpServletRequest, "labTests");
		mv.getModel().put("labTests", receiptView);
		logger.info("Found Receipt: " + receipt.getReceiptId() + " requestby User: " + loggedInUser.getUsername());
		mv.getModel().put("showTestList", 1);
		mv.getModel().put("buildResult", 1);
		mv.setViewName(RECEIPT_VIEW_JSP);*/
		createReceiptViewModel(mv, receipt, httpServletRequest);
		return mv;
	}
	
	private void createReceiptViewModel(ModelAndView mv, ReceiptObject receipt, HttpServletRequest httpServletRequest){
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		ReceiptViewDto receiptView = new ReceiptViewDto();		
		receiptView.setReceipt(receipt);
		if(loggedInUser.getUserType() == UserType.LAB && 
				!receipt.getRelatedUsers().contains(loggedInUser)){
			receiptView.setCurrentLab(loggedInUser);
		}
		if(receipt.getLabTestDoneObject() != null)
			receiptView.getTestList().addAll(receipt.getLabTestDoneObject());
		receiptView.setTestList(receiptView.getTestList()); //TODO why ?
		mv.getModel().put("receiptView", receiptView);
		
		List<LabTestTreeNode> allTests = new ArrayList<LabTestTreeNode>(labTestService.getAllAvailableTestTypes(LabTestTreeUtils.STR_HEAD_APPEND + loggedInUser.getUsername()));
		LabTestUtils.putAllTestTypesInModel(mv, allTests);
		
		//List<LabTestAvailablePrice> availableTests = labTestService.getAvailableTestsInLab(loggedInUser.getId());
		//List<LabTestDetailsDto> allTests = labTestService.getAllAvailableTests();
		//LabTestUtils.putAvailableTestsInModel(mv, availableTests);
		//LabTestUtils.putAllTestsInModel(mv, availableTests);
		sessionUtil.removeAttribute(httpServletRequest, "labTests");
		mv.getModel().put("labTests", receiptView);
		logger.info("Found Receipt: " + receipt.getReceiptId() + " requestby User: " + loggedInUser.getUsername());
		mv.getModel().put("showTestList", 1);
		//mv.getModel().put("buildResult", 1);
		mv.setViewName(RECEIPT_VIEW_JSP);
	}
	
	
	@RequestMapping(value = "/saveReceiptDetails", method = RequestMethod.POST)
	public ModelAndView saveReceiptAndTestDetails(@Valid @ModelAttribute ReceiptViewDto receiptView, BindingResult result, HttpServletRequest httpServletRequest, ModelAndView mv /*ModelMap model*/) throws ApplicationException {
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		//Save Receipt
		ReceiptObject receiptObjDto = receiptView.getReceipt();
		if(receiptView.getReceipt().getId() != null)
			receiptObjDto = receiptService.findReceipt(receiptView.getReceipt().getId());
		
		receiptObjDto.setValidTill(DateUtils.addMonths(DateUtilCore.getCurrentDateIST(),1)); //Set Validity of 1 month
		receiptObjDto.addRelatedUsers(loggedInUser);
		
		
		if (result.hasErrors()) {
			 return mv;
	    }
		//Add to patient list of Tests
		UserDetails subject = receiptObjDto.getSubject();
		Set<UserDetails> relatedUsers = receiptObjDto.getRelatedUsers();
		
		for(LabTestDoneObject test: receiptView.getTestList()){
			
			if(test.getDateCreated() == null){
				test.setDateCreated(DateUtilCore.getCurrentDateIST());
				test.setCreatorId(loggedInUser.getId());				
			}
			
			test.addRelatedUsers(subject);
			for(UserDetails user: relatedUsers){
				test.addRelatedUsers(user);
			}
		}
		
		//Save Tests
		Set<LabTestDoneObject> testsToSave = new HashSet<LabTestDoneObject>();
		testsToSave.addAll(receiptView.getTestList());
		testsToSave = labTestService.saveTestsDone(testsToSave);
		
		//Save related users
		for(LabTestDoneObject test: testsToSave){			
			/*test.addRelatedUsers(subject);
			for(UserDetails user: relatedUsers){
				test.addRelatedUsers(user);
			}*/
			subject.addTest(test);
			loggedInUser.addTest(test);
		}
		userRegisterService.saveDetails(subject); //TODO check if this is required since loggedInUser is already being saved
		loggedInUser.addRelated(subject);
		//userRegisterService.saveRelationship(user, relatedId, relatedUserType) TODO if we can save only relationship
		userRegisterService.saveDetails(loggedInUser);
		
		
		//Save receipt Object
		receiptObjDto.setLabTestDoneObject(testsToSave);
		receiptObjDto = receiptService.save(receiptObjDto);
		
		//Save Tests in Lab
		/*UserDetailsDto loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		if(loggedInUser.getUserTypeEnum() == UserType.LAB){
			UserDetailsDto lab = searchService.findById(loggedInUser.getId());
			for(LabTestObjectDto test: receiptView.getTestList()){
				lab.addTestDoneListDto(test);
			}
			userRegisterService.saveDetails(lab, null);
		}*/
		
		/*Set<UserDetails> users = new HashSet<UserDetails>();
		users.add(subject);		
		new ModelBuilder().buildUserListModel(loggedInUser, mv, subject.getUserType(), users);*/	
		//List<LabTestAvailablePrice> availableTests = labTestService.getAvailableTestsInLab(loggedInUser.getId());
		//List<LabTestDetailsDto> allTests = labTestService.getAllAvailableTests();
		//LabTestUtils.putAvailableTestsInModel(mv, availableTests);
		//LabTestUtils.putAllTestsInModel(mv, availableTests);
		
		List<LabTestTreeNode> allTests = new ArrayList<LabTestTreeNode>(labTestService.getAllAvailableTestTypes(LabTestTreeUtils.STR_HEAD_APPEND + loggedInUser.getUsername()));
		LabTestUtils.putAllTestTypesInModel(mv, allTests);
		sessionUtil.removeAttribute(httpServletRequest, "labTests");
		
		//createReceiptViewModel(mv, receiptObjDto, httpServletRequest);
		
		mv.getModel().put("labTests", receiptView);
		receiptView.setReceipt(receiptObjDto);
		mv.getModel().put("receiptView", receiptView);
		mv.getModel().put("showTestList", 1);
		mv.getModel().put("buildResult", 1);
		mv.setViewName(RECEIPT_VIEW_JSP);
		
		result.reject("save.success", "Saved");	
		
		sendTestResultsMail(receiptView);
		
		return mv;
	}
	
	private boolean hasReports(ReceiptViewDto receiptView){
		int testResultCount = 0;
		for(LabTestDoneObject test: receiptView.getReceipt().getLabTestDoneObject()){
			if(!StringUtils.isEmpty(test.getResultValue())){ 
				testResultCount++;
			}
		}
		return testResultCount != 0;
	}
	
	private void sendTestResultsMail(ReceiptViewDto receiptView){
		ReceiptObject receiptObjDto = receiptView.getReceipt();
		if(!hasReports(receiptView))
			return;
		
		//MailSenderUtil mailSender = new MailSenderUtil();
		for(UserDetails related: receiptObjDto.getRelatedUsers()){
			mailSenderUtil.addToMail(related.getEmail());
		}
		mailSenderUtil.setSubject(TEST_REPORT_SUBJECT);		
		String body = getTestReport(receiptView);		
		mailSenderUtil.setBody(body);		
		mailSenderUtil.sendMail();
	}
	
	private String getTestReport(ReceiptViewDto receiptView){
		StringBuilder report = new StringBuilder();
		
		report.append("<html>");
		//body
		report.append("<body>");
		//User Details
		report.append("<h3>");
		report.append(receiptView.getReceipt().getSubject().getUserType() + " : " + receiptView.getReceipt().getSubject().getFullName().toUpperCase());
		report.append("</h3>");
		for(UserDetails user: receiptView.getReceipt().getRelatedUsers()){
			report.append("<h3>");
			report.append(user.getUserType() + " : " + user.getFullName().toUpperCase());
			report.append("</h3>");
		}
		report.append("<br>");
		//table
		report.append("<table border=1 width=100%>");
		report.append("<tr><th>Test Name</th><th>Result</th><th>Range</th><th>Comments</th><th>Date</th><tr>");
		for(LabTestDoneObject test: receiptView.getReceipt().getLabTestDoneObject()){			
			report.append("<tr>");
			report.append("<td>");
			report.append(test.getName());
			report.append("</td>");
			if(!isResultWithinRange(test))
				report.append("<td style='color: red;	font-weight: bold'>");
			else
				report.append("<td>");
			report.append(test.getResultValue());
			report.append("</td>");
			report.append("<td>");
			report.append(test.getRefLower() + " - " + test.getRefUpper() + " " + test.getUnit());
			report.append("</td>");			
			report.append("<td>");
			report.append(test.getComments());
			report.append("</td>");
			report.append("<td>");
			report.append(test.getDateCreated());
			report.append("</td>");
			report.append("</tr>");
			
		}
		report.append("</table>");
		report.append("</body>");
		report.append("</html>");
		
		return report.toString();
	}
	
	public boolean isResultWithinRange(LabTestDoneObject test){
		if(test.getRefLower() < test.getRefUpper() && !StringUtil.isEmpty(test.getUnit())){			
			try{
				float resultValue = Float.parseFloat(test.getResultValue());
				if(resultValue < test.getRefLower() || resultValue > test.getRefUpper()){
					return false;
				}
			} catch (NumberFormatException e) {
			    logger.error("Result is not a number : " + test.getResultValue());
			}
		}
		return true;
	}


}

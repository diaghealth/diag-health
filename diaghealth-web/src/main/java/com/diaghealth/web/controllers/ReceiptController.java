package com.diaghealth.web.controllers;

import java.util.Date;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.diaghealth.models.ReceiptViewDto;
import com.diaghealth.nodes.ReceiptObject;
import com.diaghealth.nodes.labtest.LabTestAvailablePrice;
import com.diaghealth.nodes.labtest.LabTestDoneObject;
import com.diaghealth.nodes.user.UserDetails;
import com.diaghealth.services.LabTestService;
import com.diaghealth.services.ReceiptService;
import com.diaghealth.services.SearchService;
import com.diaghealth.services.UserRegisterService;
import com.diaghealth.web.utils.LabTestUtils;
import com.diaghealth.web.utils.ModelBuilder;
import com.diaghealth.web.utils.SessionUtil;

@Controller
public class ReceiptController {
	
	@Autowired
    private SessionUtil sessionUtil;
	@Autowired
	private ReceiptService receiptService;
	@Value("${receipt.view.jsp}")
	private String RECEIPT_VIEW_JSP;
	@Value("${receipt.search.jsp}")
	private String RECEIPT_SEARCH;
	@Value("${show.userlist.jsp}")
	private String USERS_SHOW_JSP;
	@Autowired
	private LabTestService labTestService;
	@Autowired
	private SearchService searchService;
	@Autowired
	private UserRegisterService userRegisterService;
	
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
		mv.setViewName(RECEIPT_SEARCH);
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
		else if(receipt.getValidTill() != null && new Date().compareTo(receipt.getValidTill()) > 0){
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
		ReceiptViewDto receiptView = new ReceiptViewDto();
		receiptView.setReceipt(receipt);
		mv.getModel().put("receiptView", receiptView);
		List<LabTestAvailablePrice> availableTests = labTestService.getAvailableTestsInLab(loggedInUser.getId());
		//List<LabTestDetailsDto> allTests = labTestService.getAllAvailableTests();
		LabTestUtils.putAvailableTestsInModel(mv, availableTests);
		sessionUtil.removeAttribute(httpServletRequest, "labTests");
		logger.info("Found Receipt: " + receipt.getReceiptId() + " requestby User: " + loggedInUser.getUsername());
		mv.getModel().put("showTestList", 1);
		mv.setViewName(RECEIPT_VIEW_JSP);
		return mv;
	}
	
	
	@RequestMapping(value = "/saveReceiptDetails", method = RequestMethod.POST)
	public ModelAndView saveReceiptAndTestDetails(@Valid @ModelAttribute ReceiptViewDto receiptView, BindingResult result, HttpServletRequest httpServletRequest, ModelAndView mv /*ModelMap model*/) throws ApplicationException {
		UserDetails loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		//Save Receipt
		ReceiptObject receiptObjDto = receiptService.findReceipt(receiptView.getReceipt().getId());
		receiptObjDto.setValidTill(new Date());
		receiptObjDto.addRelatedUsers(loggedInUser);
		receiptObjDto = receiptService.save(receiptObjDto);
		
		if (result.hasErrors()) {
			 return mv;
	    }
		//Add to patient list of Tests
		UserDetails subject = receiptObjDto.getSubject();
		Set<UserDetails> relatedUsers = receiptObjDto.getRelatedUsers();
		
		for(LabTestDoneObject test: receiptView.getTestList()){
			test.setDateCreated(new Date());
			test.setCreatorId(loggedInUser.getId());
			
			test.addRelatedUsers(subject);
			for(UserDetails user: relatedUsers){
				test.addRelatedUsers(user);
			}
			subject.addTest(test);
			loggedInUser.addTest(test);
		}
		userRegisterService.saveDetails(subject); //TODO check if this is required since loggedInUser is already being saved
		loggedInUser.addRelated(subject);
		//userRegisterService.saveRelationship(user, relatedId, relatedUserType) TODO if we can save only relationship
		userRegisterService.saveDetails(loggedInUser);
		
		//Save Tests in Lab
		/*UserDetailsDto loggedInUser = sessionUtil.getLoggedInUser(httpServletRequest);
		if(loggedInUser.getUserTypeEnum() == UserType.LAB){
			UserDetailsDto lab = searchService.findById(loggedInUser.getId());
			for(LabTestObjectDto test: receiptView.getTestList()){
				lab.addTestDoneListDto(test);
			}
			userRegisterService.saveDetails(lab, null);
		}*/
		
		Set<UserDetails> users = new HashSet<UserDetails>();
		users.add(subject);
		new ModelBuilder().buildUserListModel(loggedInUser, mv, subject.getUserType(), users);
		mv.setViewName(USERS_SHOW_JSP);
		return mv;
	}


}

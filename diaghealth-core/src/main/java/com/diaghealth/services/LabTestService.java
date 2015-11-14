package com.diaghealth.services;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diaghealth.models.SearchTestViewDto;
import com.diaghealth.nodes.labtest.LabTestAvailablePrice;
import com.diaghealth.nodes.labtest.LabTestDetails;
import com.diaghealth.nodes.labtest.LabTestDoneObject;
import com.diaghealth.nodes.labtest.LabTestTreeNode;
import com.diaghealth.nodes.user.Lab;
import com.diaghealth.nodes.user.UserDetails;
import com.diaghealth.repository.LabTestDetailsRepo;
import com.diaghealth.repository.LabTestDoneRepo;
import com.diaghealth.repository.LabTestPriceRepo;
import com.diaghealth.repository.LabTestTreeNodeRepo;

@Component
public class LabTestService {
	
	@Autowired
	LabTestPriceRepo labTestPriceRepo;
	@Autowired
	LabTestDoneRepo labTestDoneRepo;
	@Autowired
	LabTestDetailsRepo labTestDetailsRepo;
	@Autowired
	LabTestTreeNodeRepo labTestTreeNodeRepo;
	@Autowired
	UserRepositoryService userRepositoryService;
	@Autowired
	LabTestDetailsService labTestDetailsService;
	
	private static Logger logger = LoggerFactory.getLogger(LabTestService.class);
	
	public List<LabTestAvailablePrice> getTestsForUser(UserDetails user){
		return labTestPriceRepo.searchTestsForLab(user.getId());
	}
	
	public Set<LabTestDetails> getAllAvailableTests(){
		return labTestDetailsRepo.searchAllAvailableTests();
	}
	
	public Set<LabTestTreeNode> getAllAvailableTestTypes(String headName){
		LabTestTreeNode head = labTestDetailsService.getHead(headName);
		if(head == null)
			return null;
		return head.getChildren();
	}
	
	public List<LabTestAvailablePrice> getAvailableTestsInLab(Long id){
		return labTestPriceRepo.searchTestsForLab(id);
	}
	
	public void deleteTestsPrice(List<LabTestAvailablePrice> tests){
		if(CollectionUtils.isEmpty(tests)){
			return;
		}
		labTestPriceRepo.delete(tests);
	}
	
	public boolean saveTestsPrice(Set<LabTestAvailablePrice> tests, UserDetails user){
		
		if(CollectionUtils.isEmpty(tests)){
			return true;
		}
		Lab labUser = (Lab)user;
		labUser.setTestPriceList(tests);
		return userRepositoryService.saveUser(labUser, null) != null;
	}
	
	public Set<LabTestDoneObject> getTestsByUserId(Long id1, Long id2){
		return labTestDoneRepo.findTestsById(id1, id2);
		
	}
	
	public Set<LabTestDoneObject> getTestsByUserIdAndDate(Long id1, Long id2, Date from, Date to){
		return labTestDoneRepo.findTestsByIdAndDate(id1, id2, from.getTime(), to.getTime());
	}
	
	public void deleteTests(List<LabTestAvailablePrice> deleteList){
		labTestPriceRepo.delete(deleteList);
	}
	
	public Set<LabTestDoneObject> saveTestsDone(Set<LabTestDoneObject> set){
		return (Set<LabTestDoneObject>) labTestDoneRepo.save(set);
	}
	
	public Set<LabTestDoneObject> searchTests(SearchTestViewDto searchObject){
	
		return null;
	}
	

}

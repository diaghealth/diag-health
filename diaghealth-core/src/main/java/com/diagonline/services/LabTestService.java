package com.diagonline.services;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diagonline.nodes.labtest.LabTestAvailablePrice;
import com.diagonline.nodes.labtest.LabTestDetails;
import com.diagonline.nodes.labtest.LabTestDoneObject;
import com.diagonline.nodes.user.Lab;
import com.diagonline.nodes.user.UserDetails;
import com.diagonline.repository.LabTestDetailsRepo;
import com.diagonline.repository.LabTestDoneRepo;
import com.diagonline.repository.LabTestPriceRepo;

@Component
public class LabTestService {
	
	@Autowired
	LabTestPriceRepo labTestPriceRepo;
	@Autowired
	LabTestDoneRepo labTestDoneRepo;
	@Autowired
	LabTestDetailsRepo labTestDetailsRepo;
	@Autowired
	UserRepositoryService userRepositoryService;
	
	private static Logger logger = LoggerFactory.getLogger(LabTestService.class);
	
	public List<LabTestAvailablePrice> getTestsForUser(UserDetails user){
		return labTestPriceRepo.searchTestsForLab(user.getId());
	}
	
	public List<LabTestDetails> getAllAvailableTests(){
		return labTestDetailsRepo.searchAllAvailableTests();
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
	

}

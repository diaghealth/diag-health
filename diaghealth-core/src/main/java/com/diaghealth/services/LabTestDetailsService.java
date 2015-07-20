package com.diaghealth.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Component;

import com.diaghealth.nodes.labtest.LabTestDetails;
import com.diaghealth.repository.LabTestDetailsRepo;

@Component
public class LabTestDetailsService {
	
	@Autowired
	LabTestDetailsRepo labTestDetailsRepo;
	
	public Set<LabTestDetails> getAllTests(){
		return labTestDetailsRepo.searchAllAvailableTests();		
	}
	
	public LabTestDetails save(LabTestDetails test){
		return labTestDetailsRepo.save(test);
	}
	
	public Set<LabTestDetails> findIfExists(LabTestDetails test){
		return labTestDetailsRepo.findByTypeNameGender(test.getType().toUpperCase(), test.getName().toUpperCase(), test.getUserGender().toString());
	}
	
	public void deleteTest(LabTestDetails test){
		labTestDetailsRepo.delete(test);
	}

}

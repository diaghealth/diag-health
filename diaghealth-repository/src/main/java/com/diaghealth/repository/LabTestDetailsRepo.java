package com.diaghealth.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.diaghealth.nodes.labtest.LabTestDetails;

public interface LabTestDetailsRepo extends GraphRepository<LabTestDetails>{
	
	@Query("match (n{__type__:'LabTestDetails'}) return n;")
	public Set<LabTestDetails> searchAllAvailableTests();
	
	/*@Query("match (n{__type__:'LabTestDetails',type:{0},name:{1},userGender:{2}}) return n;")
	public Set<LabTestDetails> findByTypeNameGender(String type, String name, String Gender);*/
	
	@Query("match (n{__type__:'LabTestDetails',name:{0},userGender:{1}}) return n;")
	public Set<LabTestDetails> findByTypeNameGender(String name, String Gender);
	
	@Query("match (n{__type__:'LabTestDetails',name:{0}}) return n;")
	public Set<LabTestDetails> findByTypeName(String name);

}

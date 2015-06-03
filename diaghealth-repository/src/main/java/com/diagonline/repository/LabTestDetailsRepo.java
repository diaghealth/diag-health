package com.diagonline.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.diagonline.nodes.labtest.LabTestDetails;

public interface LabTestDetailsRepo extends GraphRepository<LabTestDetails>{
	
	@Query("match (n{__type__:'LabTestDetails'}) return n;")
	public List<LabTestDetails> searchAllAvailableTests();

}

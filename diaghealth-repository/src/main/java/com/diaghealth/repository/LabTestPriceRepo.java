package com.diaghealth.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.diaghealth.nodes.labtest.LabTestAvailablePrice;

public interface LabTestPriceRepo extends GraphRepository<LabTestAvailablePrice>{
	
	@Query("start n=node({0}) match (n)-[:TEST_AVAILABLE_PRICE]->(m) return m;")
	public List<LabTestAvailablePrice> searchTestsForLab(Long id);
	//@Query("START n=node:__types__(className='LabTestDetails') return n")
	
	/*@Query("start n=node({0}) OPTIONAL MATCH (n)-[r]-() delete n,r;")
	public void deleteTests(String idList);*/

}
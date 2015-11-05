package com.diaghealth.repository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.diaghealth.nodes.labtest.LabTestDetails;
import com.diaghealth.nodes.labtest.LabTestTreeNode;

public interface LabTestTreeNodeRepo extends GraphRepository<LabTestTreeNode>{
	
	@Query("match (n{__type__:'LabTestTreeNode',testGroupName:{0}}) return n;")
	public LabTestTreeNode findNodeByTestGroupName(String testGroupName);
}

package com.diaghealth.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diaghealth.nodes.labtest.LabTestDetails;
import com.diaghealth.nodes.labtest.LabTestTreeNode;
import com.diaghealth.repository.LabTestDetailsRepo;
import com.diaghealth.repository.LabTestTreeNodeRepo;
import com.diaghealth.utils.LabTestTreeUtils;

@Component
public class LabTestDetailsService {
	
	@Autowired
	LabTestDetailsRepo labTestDetailsRepo;
	@Autowired
	LabTestTreeNodeRepo labTestTreeNodeRepo;
	private static LabTestTreeNode head;	
	
	public Set<LabTestDetails> getAllTests(){
		return labTestDetailsRepo.searchAllAvailableTests();		
	}
	
	/*public LabTestDetails save(LabTestDetails test){
		return labTestDetailsRepo.save(test);
	}*/
	
	public LabTestTreeNode save(LabTestTreeNode test){
		if(test instanceof LabTestTreeNode)
			return labTestTreeNodeRepo.save(test);
		else if(test instanceof LabTestDetails)
			return labTestDetailsRepo.save((LabTestDetails)test);
		else
			return null;
	}
	
	public Set<LabTestDetails> findIfExists(LabTestDetails test){
		return labTestDetailsRepo.findByTypeNameGender(test.getName().toUpperCase(), test.getUserGender().toString());
	}
	
	public void deleteTest(LabTestDetails test){
		labTestDetailsRepo.delete(test);
	}
	
	public LabTestTreeNode getHead(){
		if(head == null){
			//Check in DB
			head = labTestTreeNodeRepo.findNodeByTestGroupName(LabTestTreeUtils.STR_HEAD);
			if(head == null){
				head = new LabTestTreeNode();
				head.setTestGroupName(LabTestTreeUtils.STR_HEAD);
			}
		}
		return head;
	}
	
	public Set<LabTestTreeNode> getBaseTestGroup(){
		Set<LabTestTreeNode> children = getHead().getChildren();
		return children;
	}
	
	public Set<LabTestTreeNode> getChildTestGroup(LabTestTreeNode node){
		if(node == null)
			return null;
		
		return node.getChildren();
	}
	
	public Set<LabTestTreeNode> getSubGroupTests(String groupName){
		if(head == null)
			getHead();
		LabTestTreeNode node = LabTestTreeUtils.findNodeInTree(head, groupName);
		if(node != null){
			return node.getChildren();
		}		
		
		return null;
	}

}

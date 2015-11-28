package com.diaghealth.services;

import static com.diaghealth.utils.LabTestTreeUtils.MAX_SUB_GROUPS;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.diaghealth.nodes.labtest.LabTestAvailablePrice;
import com.diaghealth.nodes.labtest.LabTestDetails;
import com.diaghealth.nodes.labtest.LabTestTreeNode;
import com.diaghealth.repository.LabTestDetailsRepo;
import com.diaghealth.repository.LabTestPriceRepo;
import com.diaghealth.repository.LabTestTreeNodeRepo;
import com.diaghealth.utils.LabTestTreeUtils;
import com.diaghealth.utils.UserGender;

@Component
public class LabTestDetailsService {
	
	@Autowired
	LabTestDetailsRepo labTestDetailsRepo;
	@Autowired
	LabTestTreeNodeRepo labTestTreeNodeRepo;
	@Autowired
	LabTestPriceRepo labTestPriceRepo;
	//private LabTestTreeNode head;	
	
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
		else if(test instanceof LabTestAvailablePrice)
			return labTestPriceRepo.save((LabTestAvailablePrice)test);
		else
			return null;
	}
	
	public Set<LabTestDetails> findIfExists(LabTestDetails test){
		return findIfExists(test.getName().toUpperCase(), test.getUserGender().toString());
	}
	
	public Set<LabTestDetails> findIfExists(String testName, String testGender){
		 Set<LabTestDetails> tests = labTestDetailsRepo.findByTypeName(testName.toUpperCase());
		 Iterator<LabTestDetails> i = tests.iterator();
		 
		 while(i.hasNext()){
			 LabTestDetails test = i.next();
			 UserGender gender = test.getUserGender();
			 if(!LabTestTreeUtils.isSameGender(test, gender))
				 i.remove();
		 }
		//return labTestDetailsRepo.findByTypeNameGender(testName.toUpperCase(), testGender.toString());
		 return tests;
	}
	
	public void deleteTest(LabTestDetails test){
		labTestDetailsRepo.delete(test);
	}
	
	public LabTestTreeNode getHead(){
		return getHead(LabTestTreeUtils.STR_HEAD);
	}
	
	public LabTestTreeNode getHead(String headName){
		//if(head == null){
		//Check in DB
		LabTestTreeNode head = labTestTreeNodeRepo.findNodeByTestGroupName(headName);
		if(head == null){
			head = new LabTestTreeNode();
			head.setTestGroupName(headName);
		}
		//}
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
		return getSubGroupTests(groupName, LabTestTreeUtils.STR_HEAD);
	}
	
	public Set<LabTestTreeNode> getSubGroupTests(String groupName, String headName){
		LabTestTreeNode head =	getHead(headName);
		LabTestTreeNode node = LabTestTreeUtils.findNodeInTree(head, groupName);
		if(node != null){
			return node.getChildren();
		}		
		
		return null;
	}
	
	public Set<LabTestDetails> getTestByName(String testName){
		return labTestDetailsRepo.findByTypeName(testName);
	}
	
	public void saveAncestorTree(LabTestDetails labTest, String headName){
		LabTestTreeNode baseNode = getHead(headName);
		List<String> groups = labTest.getAncestorGroupNames(headName);
		boolean nodeFound = false;
		int index = 0;
		int maxSize = groups.size() > MAX_SUB_GROUPS ? MAX_SUB_GROUPS : groups.size();
		for(index = 0; index < maxSize;index++ ){
			if(isValidNode(groups.get(index))){
				LabTestTreeNode tmp = LabTestTreeUtils.findNodeInTree(baseNode, groups.get(index));
				if(tmp != null){
					baseNode = tmp;
					nodeFound = true;
				} else {
					break;
				}
			}
		}
		
		LabTestTreeNode toSaveNode = baseNode;
		/*if(nodeFound)
			index++; //Save from the next node
*/		
		
		
		for(;index < maxSize;index++){
			if(isValidNode(groups.get(index))){
				LabTestTreeNode  newNode = new LabTestTreeNode();
				newNode.setParent(baseNode);
				newNode.setTestGroupName(groups.get(index));
				newNode = baseNode.addChild(newNode);				
				baseNode = newNode;
			}
		}
		//Add head as ancestor
		//labTest.saveAncestors(labTest);
		
		//Add LabTest as leaf node
		baseNode.addChild(labTest);
		save(baseNode);
		if(baseNode != toSaveNode)
			save(toSaveNode);
		
	}
	
	private boolean isValidNode(String name){
		//Thread myThread = new Thread();
		return !StringUtils.isEmpty(name) && !name.equalsIgnoreCase("NULL");
	}

}

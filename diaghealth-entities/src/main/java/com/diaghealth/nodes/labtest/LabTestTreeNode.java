package com.diaghealth.nodes.labtest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.diaghealth.nodes.BaseNode;

@NodeEntity
@TypeAlias("LabTestTreeNode")
public class LabTestTreeNode extends BaseNode {
	
	@RelatedTo(type="RELATED_TEST_CHILDREN", direction=Direction.OUTGOING)
	@Fetch
	private Set<LabTestTreeNode> children;
	@RelatedTo(type="RELATED_TEST_PARENT", direction=Direction.OUTGOING)
	@Fetch
	private LabTestTreeNode parent;
	private String testGroupName;	
	
	public Set<LabTestTreeNode> getChildren() {
		return children;
	}
	public void setChildren(Set<LabTestTreeNode> children) {
		this.children = children;
	}
	public LabTestTreeNode getParent() {
		return parent;
	}
	public void setParent(LabTestTreeNode parent) {
		this.parent = parent;
	}
	public LabTestTreeNode addChild(LabTestTreeNode child){
		if(children == null){
			children = new HashSet<LabTestTreeNode>();
		}
		for(LabTestTreeNode treeChild: children){
			if(child.equals(treeChild)){ //This child already exists, no need to add again
				return treeChild;
			}
		}
		child.setParent(this);
		children.add(child);
		return child;
				
	}
	
	public LabTestTreeNode addChild(String groupName){
		if(children == null){
			children = new HashSet<LabTestTreeNode>();
		}
		
		for(LabTestTreeNode treeChild: children){
			if(treeChild.getTestGroupName().equals(groupName)){ //This child already exists, no need to add again
				return treeChild;
			}
		}
		LabTestTreeNode child = new LabTestTreeNode();
		child.setTestGroupName(groupName);
		child.setParent(this);
		children.add(child);
		return child;
				
	}
	
	public String getTestGroupName() {
		return testGroupName;
	}
	public void setTestGroupName(String testGroupName) {
		this.testGroupName = testGroupName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((testGroupName == null) ? 0 : testGroupName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LabTestTreeNode other = (LabTestTreeNode) obj;
		if (testGroupName == null) {
			if (other.testGroupName != null)
				return false;
		} else if (!testGroupName.equals(other.testGroupName))
			return false;
		return true;
	}	
	

}

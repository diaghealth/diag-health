package com.diaghealth.nodes.labtest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.util.StringUtils;

import com.diaghealth.nodes.BaseNode;
import com.diaghealth.utils.LabTestTreeUtils;
import com.diaghealth.utils.UserGender;

import static com.diaghealth.utils.LabTestTreeUtils.MAX_SUB_GROUPS;

@NodeEntity
@TypeAlias("LabTestDetails")
public class LabTestDetails  extends LabTestTreeNode {
	
	/*@GraphId
	protected Long id;*/	
	protected String type; //Not used 
	@NotNull(message = "Please enter Test Name.")
	protected String name;
	protected float refLower;
	protected float refUpper;
	protected UserGender userGender;
	protected String unit;
	protected String comments;
	@Transient
	protected List<String> ancestorGroupNames = new ArrayList<String>(MAX_SUB_GROUPS);
	
	public String getType() {
		List<String> ancestorGroupNamesTemp = getAncestorGroupNames();
		if(ancestorGroupNamesTemp.size() > 0){
			return ancestorGroupNamesTemp.get(0);
		}
		return null;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public float getRefLower() {
		return refLower;
	}
	public void setRefLower(float refLower) {
		this.refLower = refLower;
	}
	public float getRefUpper() {
		return refUpper;
	}
	public void setRefUpper(float refUpper) {
		this.refUpper = refUpper;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public UserGender getUserGender() {
		return userGender;
	}
	public void setUserGender(UserGender gender) {
		this.userGender = gender;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((userGender == null) ? 0 : userGender.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LabTestDetails other = (LabTestDetails) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (userGender != other.userGender)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LabTestDetails [name=" + name + ", refLower=" + refLower
				+ ", refUpper=" + refUpper + ", userGender=" + userGender
				+ ", unit=" + unit + ", comments=" + comments + "]";
	}
	
	public List<String> getAncestorGroupNames(){
		return getAncestorGroupNames(LabTestTreeUtils.STR_HEAD);
	}
	
	public List<String> getAncestorGroupNames(String headName) {
		
		LabTestTreeNode prevNode = this.getParent();	
		
		if(ancestorGroupNames.size() > 0)
			return ancestorGroupNames;
		
		int i = 0;		
		//ancestorGroupNames = new ArrayList<String>();
		while(prevNode != null && !prevNode.getTestGroupName().contains(headName) && i < MAX_SUB_GROUPS){
			//ancestorGroupNames.set(i++, prevNode.getTestGroupName());
			ancestorGroupNames.add(i++, prevNode.getTestGroupName());
			prevNode = prevNode.getParent();
		}
		Collections.reverse(ancestorGroupNames);
		return ancestorGroupNames;
	}
	
	public void setAncestorGroupNames(List<String> ancestorGroupNames) {
		this.ancestorGroupNames = ancestorGroupNames;
	}
	
	public void saveAncestors(){
		
	}

}

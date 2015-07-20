package com.diaghealth.nodes.labtest;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.diaghealth.nodes.BaseNode;
import com.diaghealth.utils.UserGender;

@NodeEntity
@TypeAlias("LabTestDetails")
public class LabTestDetails  extends BaseNode{
	
	/*@GraphId
	protected Long id;*/
	protected String type;
	@NotNull(message = "Please enter Test Name.")
	protected String name;
	protected float refLower;
	protected float refUpper;
	protected UserGender userGender;
	protected String unit;
	protected String comments;
	
	public String getType() {
		return type;
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

}

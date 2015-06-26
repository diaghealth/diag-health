package com.diaghealth.nodes.labtest;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.diaghealth.nodes.user.UserDetails;

@NodeEntity
@TypeAlias("LabTestObject")
public class LabTestDoneObject extends LabTestAvailablePrice {
	
	@RelatedTo(type="TEST_DONE", direction=Direction.BOTH)
	private Set<UserDetails> relatedUsers;

	public Set<UserDetails> getRelatedUsers() {
		return relatedUsers;
	}

	public void setRelatedUsers(Set<UserDetails> relatedUsers) {
		this.relatedUsers = relatedUsers;
	}
	
	public void addRelatedUsers(UserDetails user){
		if(relatedUsers == null){
			relatedUsers = new HashSet<UserDetails>();
		}
		
		relatedUsers.add(user);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		if(id != null)
			result = prime * result + id.intValue();
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
		LabTestDoneObject other = (LabTestDoneObject) obj;
		if(id == null || other.id == null)
			return false;
		if (id.intValue() != other.id.intValue())
			return false;
		return true;
	}
}

package com.diagonline.nodes.labtest;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.diagonline.nodes.user.UserDetails;

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
}

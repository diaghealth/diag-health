package com.diaghealth.nodes.user;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.diaghealth.nodes.labtest.LabTestAvailablePrice;

@NodeEntity
@TypeAlias("User")
public class Lab extends UserDetails {
	
	public Lab(){
		
	}
	
	public Lab(UserDetails user){
		super(user);
	}
	
    
	@RelatedTo(type="TEST_AVAILABLE_PRICE", direction=Direction.BOTH)
    @Fetch
	private Set<LabTestAvailablePrice> testPriceList;
	
	public Set<LabTestAvailablePrice> getTestPriceList() {
		return testPriceList;
	}
	public void setTestPriceList(Set<LabTestAvailablePrice> testPriceList) {
		this.testPriceList = testPriceList;
	}
}

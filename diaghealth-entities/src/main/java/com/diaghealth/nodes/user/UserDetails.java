package com.diaghealth.nodes.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.support.index.IndexType;
import org.springframework.util.StringUtils;

import com.diaghealth.nodes.BaseNode;
import com.diaghealth.nodes.labtest.LabTestDoneObject;
import com.diaghealth.nodes.validate.Email;
import com.diaghealth.nodes.validate.PhoneNumber;
import com.diaghealth.nodes.validate.UserName;
import com.diaghealth.utils.UserType;

@NodeEntity
@TypeAlias("User")
public class UserDetails extends BaseNode {
	
	@Indexed(indexType = IndexType.FULLTEXT, indexName = "searchByUserName")
	@UserName
	private String username;
 
    private String password;
	
    @NotNull(message = "Please enter Name.") 
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "searchByFirstName")
    @Size(min=4, max=30)
    private String firstname;
    
    private String lastname;
    
    @Email
	private String email;
    
	@NotNull(message = "Please enter Valid Phone Number.")
	@PhoneNumber
	@Indexed(indexName = "searchByPhoneNumber")
	private Long phoneNumber;
	
    private String addressLine1;
    private String addressLine2;
    
    @NotNull(message = "Please enter Valid Location.")
    @Size(min=1)
    private String location;
    
    private String state;
    private String pincode;
    
    /*@NotNull(message = "Please choose a userType.") 
    @Size(min=1)
    private String userType;*/
    
    @NotNull(message = "Please choose a userType.") 
    private UserType userType;
    
    private String userRole;
    
    @NotNull(message = "Enter a valid location.")
    @Indexed
    private Double latLocation;
    
    @Indexed
    private Double longLocation;
    
    @RelatedTo(type="RELATED_BOTH", direction=Direction.BOTH)
    @Fetch
    private Set<UserDetails> relatedList;
        
    @RelatedTo(type="TEST_DONE", direction=Direction.BOTH)
    //@Fetch
	private Set<LabTestDoneObject> testDoneList;	
	
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		if(StringUtils.isEmpty(this.username)){
			this.username = username;
		}
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public Double getLatLocation() {
		return latLocation;
	}
	public void setLatLocation(Double latLocation) {
		this.latLocation = latLocation;
	}
	public Double getLongLocation() {
		return longLocation;
	}
	public void setLongLocation(Double longLocation) {
		this.longLocation = longLocation;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		/*if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;*/
		UserDetails other = (UserDetails) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		if(this.phoneNumber == null)
			this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "UserDetails [username=" + username + ", password=" + password
				+ ", firstname=" + firstname + ", email=" + email + ", phoneNumber="
				+ phoneNumber + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", location=" + location
				+ ", state=" + state + ", pincode=" + pincode + ", userType="
				+ userType + ", latLocation=" + latLocation + ", longLocation="
				+ longLocation  + "]";
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		if(StringUtils.isEmpty(this.firstname))
			this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public Set<UserDetails> getRelatedList() {
		return relatedList;
	}
	public void setRelatedList(Set<UserDetails> relatedList) {
		if(this.relatedList == null)
			this.relatedList = relatedList;
	}
	

	public void addRelated(UserDetails related){
		if(this.relatedList == null)
			this.relatedList = new HashSet<UserDetails>();
		this.relatedList.add(related);
	}
	
	public Set<LabTestDoneObject> getTestList() {
		return testDoneList;
	}
	

	public void setTestList(Set<LabTestDoneObject> testList) {
		if(this.testDoneList == null)
			this.testDoneList = testList;
	}
	
	public void addTest(LabTestDoneObject testList){
		if(this.testDoneList == null)
			this.testDoneList = new HashSet<LabTestDoneObject>();
		this.testDoneList.add(testList);
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public Set<LabTestDoneObject> getTestDoneList() {
		return testDoneList;
	}
	public void setTestDoneList(Set<LabTestDoneObject> testDoneList) {
		this.testDoneList = testDoneList;
	}
	
		
}

package com.diaghealth.nodes.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.time.DateUtils;
import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.support.index.IndexType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.diaghealth.nodes.BaseNode;
import com.diaghealth.nodes.labtest.LabTestDoneObject;
import com.diaghealth.nodes.validate.Email;
import com.diaghealth.nodes.validate.PhoneNumber;
import com.diaghealth.nodes.validate.UserName;
import com.diaghealth.utils.UserGender;
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
    
    
    @NotNull(message = "Please enter Valid Date Of Birth.")
    @DateTimeFormat(pattern = "DD/MM/YYYY")
	private Date dateOfBirth;
        
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
    private UserGender userGender;
    
    @Transient
    private int age;
    
    public UserDetails(){
    	
    }
    
    public UserDetails(UserDetails obj){
    	this.username = obj.getUsername();
    	this.password = obj.getPassword();
    	this.firstname = obj.getFirstname();
    	this.lastname = obj.getLastname();
    	this.email = obj.getEmail();
    	this.phoneNumber = obj.getPhoneNumber();
    	this.addressLine1 = obj.getAddressLine1();    	
    	this.addressLine2 = obj.getAddressLine2();
    	this.location = obj.getLocation();
    	this.state = obj.getState(); 
    	this.pincode = obj.getPincode();
    	this.userType = obj.getUserType();
    	this.userRole = obj.getUserRole();
    	this.latLocation = obj.getLatLocation();    	
    	this.longLocation = obj.getLongLocation();
    	this.testDoneList = obj.getTestDoneList();
    	this.relatedList = obj.getRelatedList();
    	
    }
	
    
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
	public String getFullName(){
		return this.firstname + this.lastname;
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
	
	/*public Set<LabTestDoneObject> getTestList() {
		return testDoneList;
	}
	

	public void setTestList(Set<LabTestDoneObject> testList) {
		if(this.testDoneList == null)
			this.testDoneList = testList;
	}*/
	
	public void addTest(LabTestDoneObject testList){
		if(this.testDoneList == null)
			this.testDoneList = new HashSet<LabTestDoneObject>();
		if(!this.testDoneList.add(testList)){
			this.testDoneList.remove(testList); //Element already exists, remove the element and add the latest element
			this.testDoneList.add(testList);
		}
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
		if(this.testDoneList == null)
			this.testDoneList = testDoneList;
	}
	public UserGender getUserGender() {
		return userGender;
	}
	public void setUserGender(UserGender gender) {
		this.userGender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getAge() {
		if(age == 0 && dateOfBirth != null){
			long diffInMillies = new Date().getTime() - dateOfBirth.getTime();
			 return (int)((TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS))/365);
		}
			
		return age;
	}

	public void setAge(int age) {		
		this.age = age;
		if(dateOfBirth == null){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - age);
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			dateOfBirth = cal.getTime();
			//dateOfBirth = new Date(new Date().getYear() - age,1,1);
		}
	}
	
		
}

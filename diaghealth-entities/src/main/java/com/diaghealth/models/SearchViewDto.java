package com.diaghealth.models;

import com.diaghealth.utils.UserType;

public class SearchViewDto extends BaseDto {
	
	private UserType userType;
	private String location;
	private Double latLocation;
	private Double longLocation;
	private String name;
	private Integer distance;
	private Long phoneNumber;
	
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}

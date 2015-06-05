package com.diaghealth.nodes;


public class SearchObject {
	
	private String usertype;
	private String location;
	private String latLocation;
	private String longLocation;
	private String name;
	private Integer distance;
	
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLatLocation() {
		return latLocation;
	}
	public void setLatLocation(String latLocation) {
		this.latLocation = latLocation;
	}
	public String getLongLocation() {
		return longLocation;
	}
	public void setLongLocation(String longLocation) {
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

}

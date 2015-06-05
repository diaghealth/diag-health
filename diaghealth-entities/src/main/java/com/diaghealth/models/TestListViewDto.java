package com.diaghealth.models;

import java.util.Date;
import java.util.Set;

import com.diaghealth.nodes.labtest.LabTestDoneObject;

public class TestListViewDto {
	
	Long id;
	Set<LabTestDoneObject> testList;
	String fromDate;
	String toDate;
	int totalPrice;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Set<LabTestDoneObject> getTestList() {
		return testList;
	}
	public void setTestList(Set<LabTestDoneObject> testList) {
		this.testList = testList;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int total) {
		this.totalPrice = total;
	}
}

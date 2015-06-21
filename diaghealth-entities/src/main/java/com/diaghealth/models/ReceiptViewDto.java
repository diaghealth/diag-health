package com.diaghealth.models;

import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.diaghealth.nodes.ReceiptObject;
import com.diaghealth.nodes.labtest.LabTestDoneObject;
import com.diaghealth.nodes.user.UserDetails;

public class ReceiptViewDto {

	ReceiptObject receipt;
	private AutoPopulatingList<LabTestDoneObject> testList = new AutoPopulatingList<LabTestDoneObject>(LabTestDoneObject.class);
	private UserDetails currentLab;
	
	public ReceiptObject getReceipt() {
		return receipt;
	}
	public void setReceipt(ReceiptObject receipt) {
		this.receipt = receipt;
	}
	public AutoPopulatingList<LabTestDoneObject> getTestList() {
		return testList;
	}
	public void setTestList(AutoPopulatingList<LabTestDoneObject> testList) {
		this.testList = testList;
	}
	public UserDetails getCurrentLab() {
		return currentLab;
	}
	public void setCurrentLab(UserDetails currentLab) {
		this.currentLab = currentLab;
	}
	
}

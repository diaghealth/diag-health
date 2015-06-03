package com.diagonline.models;

import org.springframework.util.AutoPopulatingList;

import com.diagonline.nodes.ReceiptObject;
import com.diagonline.nodes.labtest.LabTestDoneObject;

public class ReceiptViewDto {

	ReceiptObject receipt;
	private AutoPopulatingList<LabTestDoneObject> testList = new AutoPopulatingList<LabTestDoneObject>(LabTestDoneObject.class);
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
	
}

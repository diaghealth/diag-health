package com.diagonline.models.labtest;

import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.diagonline.nodes.labtest.LabTestAvailablePrice;

public class LabTestListViewDto {
	
	private String className = null;
	private AutoPopulatingList<LabTestAvailablePrice> testList = null;
	private List<Integer> deletedIndexList = null;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public AutoPopulatingList<LabTestAvailablePrice> getTestList() {
		return testList;
	}
	public void setTestList(AutoPopulatingList<LabTestAvailablePrice> testList) {
		this.testList = testList;
	}
	public List<Integer> getDeletedIndexList() {
		return deletedIndexList;
	}
	public void setDeletedIndexList(List<Integer> deletedIndexList) {
		this.deletedIndexList = deletedIndexList;
	}
	
	/*private List<LabTestDto> testList = new AutoPopulatingList(
            new AllocateElementFactory());
	private List testList = 
		    LazyList.decorate(
		      new ArrayList(),
		      FactoryUtils.instantiateFactory(LabTestDto.class));*/

}

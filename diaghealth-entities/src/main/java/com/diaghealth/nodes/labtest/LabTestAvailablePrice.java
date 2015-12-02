package com.diaghealth.nodes.labtest;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.diaghealth.nodes.BaseNode;
import com.diaghealth.utils.UserGender;

@NodeEntity
@TypeAlias("LabTestPrice")
public class LabTestAvailablePrice extends LabTestDetails {
	
	//private String name;
	//private String type;
	private int price;
	private float discountPercent;
	private String resultValue;
	/*private float refLower;
	private float refUpper;
	private UserGender userGender;
	private String unit;
	private String comments;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}*/
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public float getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(float discountPercent) {
		this.discountPercent = discountPercent;
	}	
	/*public float getRefLower() {
		return refLower;
	}
	public void setRefLower(float refLower) {
		this.refLower = refLower;
	}
	public float getRefUpper() {
		return refUpper;
	}
	public void setRefUpper(float refUpper) {
		this.refUpper = refUpper;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}*/
	public String getResultValue() {
		return resultValue;
	}
	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}
	/*public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public UserGender getUserGender() {
		return userGender;
	}
	public void setUserGender(UserGender gender) {
		this.userGender = gender;
	}*/
	
	@Override
	public String toString() {
		return "LabTestAvailablePrice [price=" + price + ", discountPercent="
				+ discountPercent + ", resultValue=" + resultValue + ", type="
				+ type + ", name=" + name + ", userGender=" + userGender
				+ ", ageLower=" + ageLower + ", ageUpper=" + ageUpper
				+ ", unit=" + unit + "]";
	}
}

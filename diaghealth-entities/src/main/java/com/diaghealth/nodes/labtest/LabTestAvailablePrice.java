package com.diaghealth.nodes.labtest;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.diaghealth.nodes.BaseNode;

@NodeEntity
@TypeAlias("LabTestPrice")
public class LabTestAvailablePrice extends BaseNode {
	
	private String name;
	private String type;
	private int price;
	private float discountPercent;
	private String resultValue;
	private float refLower;
	private float refUpper;
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
	}
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
	public float getRefLower() {
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
	}
	
	@Override
	public String toString() {
		return "LabTestAvailablePrice [name=" + name + ", type=" + type
				+ ", price=" + price + ", discountPercent=" + discountPercent
				+ ", resultValue=" + resultValue + ", refLower=" + refLower
				+ ", refUpper=" + refUpper + ", unit=" + unit + ", id=" + id
				+ ", dateCreated=" + dateCreated + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LabTestAvailablePrice other = (LabTestAvailablePrice) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (id.intValue() != other.id.intValue())
			return false;
		return true;
	}
	public String getResultValue() {
		return resultValue;
	}
	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}

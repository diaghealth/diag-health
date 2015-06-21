package com.diaghealth.nodes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.diaghealth.nodes.labtest.LabTestDoneObject;
import com.diaghealth.nodes.user.UserDetails;

@NodeEntity
@TypeAlias("Receipt")
public class ReceiptObject extends BaseNode {

	@Indexed
	private String receiptId;
	@RelatedTo(type="RECEIPT_USER", direction=Direction.BOTH)
	@Fetch
	private Set<UserDetails> relatedUsers;
	private UserDetails subject;
	@RelatedTo(type="RECEIPT_TESTS_DONE", direction=Direction.BOTH)
	private Set<LabTestDoneObject> labTestDoneObject;
	
	private Date validTill;
	
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}	
	public Date getValidTill() {
		return validTill;
	}
	public void setValidTill(Date validTill) {
		this.validTill = validTill;
	}
	public Set<UserDetails> getRelatedUsers() {
		return relatedUsers;
	}
	public void setRelatedUsers(Set<UserDetails> relatedUsers) {
		this.relatedUsers = relatedUsers;
	}
	public UserDetails getSubject() {
		return subject;
	}
	public void setSubject(UserDetails subject) {
		this.subject = subject;
	}
	public void addRelatedUsers(UserDetails user){
		if(this.relatedUsers == null){
			this.relatedUsers = new HashSet<UserDetails>();
		}
		this.relatedUsers.add(user);
	}
	public Set<LabTestDoneObject> getLabTestDoneObject() {
		return labTestDoneObject;
	}
	public void setLabTestDoneObject(Set<LabTestDoneObject> labTestDoneObject) {
		this.labTestDoneObject = labTestDoneObject;
	}
	public void addLabTestDoneObject(Set<LabTestDoneObject> labTestDoneObject){
		if(this.labTestDoneObject == null){
			this.labTestDoneObject = new HashSet<LabTestDoneObject>();
		}
		this.labTestDoneObject.addAll(labTestDoneObject);
	}
}

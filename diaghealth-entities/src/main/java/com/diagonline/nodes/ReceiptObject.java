package com.diagonline.nodes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.diagonline.nodes.user.UserDetails;

@NodeEntity
@TypeAlias("Receipt")
public class ReceiptObject extends BaseNode {

	@Indexed
	private String receiptId;
	@RelatedTo(type="RECEIPT_USER", direction=Direction.BOTH)
	@Fetch
	private Set<UserDetails> relatedUsers;
	private UserDetails subject;
	
	private Date validTill;
	
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	/*public Long getClinicId() {
		return clinicId;
	}
	public void setClinicId(Long clinicId) {
		this.clinicId = clinicId;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public Long getLabId() {
		return labId;
	}
	public void setLabId(Long labId) {
		this.labId = labId;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getLabName() {
		return labName;
	}
	public void setLabName(String labName) {
		this.labName = labName;
	}*/
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
}

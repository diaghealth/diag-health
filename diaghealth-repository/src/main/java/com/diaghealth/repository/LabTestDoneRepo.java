package com.diaghealth.repository;

import java.util.Date;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.diaghealth.nodes.labtest.LabTestDoneObject;

public interface LabTestDoneRepo extends GraphRepository<LabTestDoneObject>{
	
	/*@Query("match (t{__type__:'LabTestObject',patientId:{0}}) return t")
	public Set<LabTestObject> findTestsByPatientId(Long userId);
	@Query("match (t{__type__:'LabTestObject',clinicId:{0}}) return t")
	public Set<LabTestObject> findTestsByClinicId(Long userId);
	@Query("match (t{__type__:'LabTestObject',labId:{0}}) return t")
	public Set<LabTestObject> findTestsByLabId(Long userId);
	@Query("match (t{__type__:'LabTestObject',doctorId:{0}}) return t")
	public Set<LabTestObject> findTestsByDoctorId(Long userId);
	@Query("match (t{__type__:'LabTestObject',doctorId:{0},patientId:{1}}) return t")
	public Set<LabTestObject> findTestsByDoctorAndPatientId(Long doctorId, Long patientId);
	@Query("match (t{__type__:'LabTestObject',doctorId:{0},labId:{1}}) return t")
	public Set<LabTestObject> findTestsByDoctorAndLabId(Long doctorId, Long labId);
	@Query("match (t{__type__:'LabTestObject',clinicId:{0},patientId:{1}}) return t")
	public Set<LabTestObject> findTestsByClinicAndPatientId(Long clinicId, Long patientId);
	@Query("match (t{__type__:'LabTestObject',clinicId:{0},labId:{1}}) return t")
	public Set<LabTestObject> findTestsByClinicAndLabId(Long clinicId, Long labId);
	@Query("match (t{__type__:'LabTestObject',labId:{0},patientId:{1}}) return t")
	public Set<LabTestObject> findTestsByLabAndPatientId(Long labId, Long patientId);*/
	
	@Query("start m=node({0}) match (t{__type__:'LabTestObject'})-[r:TEST_DONE]-(m) return t")
	public Set<LabTestDoneObject> findTestsById(Long id);
	@Query("start m=node({0}), n=node({1}) match "
			+ "(m)-[:TEST_DONE]-(t{__type__:'LabTestObject'})-[:TEST_DONE]-(n) return t")
	public Set<LabTestDoneObject> findTestsById(Long id1, Long id2);
	
	@Query("start m=node({0}), n=node({1}) match "
			+ "(m)-[:TEST_DONE]-(t{__type__:'LabTestObject'})-[:TEST_DONE]-(n) where t.dateCreated >= {2}"
			+ " and t.dateCreated <= {3} return t")
	public Set<LabTestDoneObject> findTestsByIdAndDate(Long id1, Long id2, Long from, Long to);
}

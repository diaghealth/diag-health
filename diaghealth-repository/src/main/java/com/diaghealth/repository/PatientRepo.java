package com.diaghealth.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.diaghealth.nodes.user.Patient;

public interface PatientRepo extends GraphRepository<Patient> {

}

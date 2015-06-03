package com.diagonline.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.diagonline.nodes.user.Patient;

public interface PatientRepo extends GraphRepository<Patient> {

}

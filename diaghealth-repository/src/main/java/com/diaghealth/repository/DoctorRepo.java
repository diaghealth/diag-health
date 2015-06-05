package com.diaghealth.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.diaghealth.nodes.user.Doctor;

public interface DoctorRepo extends GraphRepository<Doctor> {

}

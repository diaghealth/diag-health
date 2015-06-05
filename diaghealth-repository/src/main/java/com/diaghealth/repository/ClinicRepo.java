package com.diaghealth.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.diaghealth.nodes.user.Clinic;

public interface ClinicRepo extends GraphRepository<Clinic>{

}

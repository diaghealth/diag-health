package com.diaghealth.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.diaghealth.nodes.user.Lab;

public interface LabRepo extends GraphRepository<Lab> {

}

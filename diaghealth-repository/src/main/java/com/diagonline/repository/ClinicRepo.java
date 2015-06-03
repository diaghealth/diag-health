package com.diagonline.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.diagonline.nodes.user.Clinic;

public interface ClinicRepo extends GraphRepository<Clinic>{

}

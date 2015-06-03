package com.diagonline.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.diagonline.nodes.user.Lab;

public interface LabRepo extends GraphRepository<Lab> {

}

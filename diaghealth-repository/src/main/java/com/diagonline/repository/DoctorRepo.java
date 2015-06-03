package com.diagonline.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.diagonline.nodes.user.Doctor;

public interface DoctorRepo extends GraphRepository<Doctor> {

}

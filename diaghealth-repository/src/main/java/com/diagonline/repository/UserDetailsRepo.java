package com.diagonline.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.diagonline.nodes.user.UserDetails;

public interface UserDetailsRepo extends GraphRepository<UserDetails>{
	@Query("start a({0}), b({1}) CREATE (a)-[r:{2}]-(b) return r;")
	public String saveRelation(Long userId, Long relatedId, String relationship);
}

package com.diagonline.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.diagonline.nodes.user.UserDetails;
public interface SearchRepo extends GraphRepository<UserDetails>{

	@Query("match (n) where n.userType = {4} and n.latLocation > {0} and n.latLocation < {2} and n.longLocation > {1} and n.longLocation < {3} return n")	
	//@Query("match (n) where n.userType = '{4}' and n.latLocation > {0} and n.latLocation < {2} and n.longLocation > {1} and n.longLocation < {3} return n")
	//@Query("match (n) where n.userType = {4} and n.latLocation > 28 and n.latLocation < 29 and n.longLocation > 76 and n.longLocation < 79 return n")
	public Iterable<UserDetails> findByLocation(Double lat1, Double long1, Double lat2, Double long2, String userType);
	
	@Query("match (n) where n.userType = {1} and ( n.firstname =~ '(?i).*{0}.*' or n.lastname =~ '(?i).*{0}.*' ) return n")
	public Iterable<UserDetails> findByUsernameUserType(String firstname, String userType);
	
	@Query("match (n) where  n.userType = {1} and n.phoneNumber = {0} return n")
	public Iterable<UserDetails> findByPhoneNumber(Long phoneNumber, String userType);
	
	// match (n)-[:{1}]-(m{userType:{2} })
	@Query("start n=node({0}) match (n)-[r:RELATED_BOTH]-(m{userType:{1}}) return m")
	public Iterable<UserDetails> findByRelation(Long id, String userType);
	
	@Query("START n=node:__types__(className='User') WHERE n.username = {0} AND n.password = {1} RETURN n")
	public UserDetails findByUsernameAndPassword(String username, String password);
	@Query("START n=node:__types__(className='User') WHERE n.username = {0} RETURN n")
	public UserDetails findByUsername(String username);
	
	/* latLocation1 < latLocation2 and latLocation1 < longLocation2*/
}

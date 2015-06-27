package com.diaghealth.repository;

import java.util.Date;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.diaghealth.nodes.ReceiptObject;

public interface ReceiptRepo extends GraphRepository<ReceiptObject>{
	
	@Query("match (m{__type__:'Receipt',receiptId:{0}}) return m")
	public ReceiptObject findById(String id);
	@Query("START n=node({0}) SET n.validTill = {1} RETURN n")
	public ReceiptObject setValidity(Long id, Date date);
	@Query("start u=node({0}) match (r{__type__:'Receipt'})-[:RECEIPT_USER]-(u) return r")
	public Set<ReceiptObject> getAllReceiptByUserId(Long id);

}

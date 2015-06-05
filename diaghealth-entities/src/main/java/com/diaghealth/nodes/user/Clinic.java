package com.diaghealth.nodes.user;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
@TypeAlias("User")
public class Clinic extends UserDetails {
	
}

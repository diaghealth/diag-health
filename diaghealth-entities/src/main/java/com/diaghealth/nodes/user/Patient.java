package com.diaghealth.nodes.user;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.diaghealth.utils.UserGender;

@NodeEntity
@TypeAlias("User")
public class Patient extends UserDetails {  
	
	public Patient(){
		
	}
	
	public Patient(UserDetails user){
		super(user);
	}
	
}

package com.diagonline.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Component;

import com.diagonline.nodes.user.Clinic;
import com.diagonline.nodes.user.Doctor;
import com.diagonline.nodes.user.Lab;
import com.diagonline.nodes.user.Patient;
import com.diagonline.nodes.user.UserDetails;
import com.diagonline.repository.ClinicRepo;
import com.diagonline.repository.DoctorRepo;
import com.diagonline.repository.LabRepo;
import com.diagonline.repository.PatientRepo;
import com.diagonline.repository.UserDetailsRepo;
import com.diagonline.utils.UserType;

@Component
public class UserRepositoryService {
	
	@Autowired
	public LabRepo labRepo;
	@Autowired
	public DoctorRepo doctorRepo;
	@Autowired
	public ClinicRepo clinicRepo;
	@Autowired
	public PatientRepo patientRepo;
	@Autowired
	public UserDetailsRepo userDetailsRepo;
	
	private static Logger logger = LoggerFactory.getLogger(UserRepositoryService.class);
	
	public static void setDateTime(UserDetails user, Long id){
		if(user.getDateCreated() == null){
			user.setDateCreated(new Date());
			if(id != null)
				user.setCreatorId(id);
		} else {
			user.setDateModified(new Date());
			if(id != null)
				user.setModifierId(id);
		}
	}
	
	public static UserDetails getNewUserNode(UserDetails details){
		UserDetails node = null;
		switch(details.getUserType()){
		case DOCTOR:
			node = new Doctor();
			break;
		case LAB:
			node = new Lab();
			break;
		case CLINIC:
			node = new Clinic();
			break;
		case PATIENT:
			node = new Patient();
			break;
		default:
			break;
		}
		return node;
	}
	
	public UserDetails saveUser(UserDetails user, Long id){
		setDateTime(user, id);
		logger.debug("Saving User: " + user.getUsername());
		return (UserDetails)getRepoByType(user.getUserType()).save(user);
	}
	
	public UserDetails getActualObjectByIdType(Long id, UserType type){
		return (UserDetails)getRepoByType(type).findOne(id);		
	}
	
	public GraphRepository getRepoByType(UserType type){
		GraphRepository graphRepo = null;
		switch(type){
			case DOCTOR:
				graphRepo = doctorRepo;
				break;
			case CLINIC:
				graphRepo = clinicRepo;
				break;
			case LAB:
				graphRepo = labRepo;
				break;
			case PATIENT:
				graphRepo = patientRepo;
				break;
			default:
				break;
		}
		return graphRepo;
	}

}

package com.diaghealth.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Component;

import com.diaghealth.nodes.user.Clinic;
import com.diaghealth.nodes.user.Doctor;
import com.diaghealth.nodes.user.Lab;
import com.diaghealth.nodes.user.Patient;
import com.diaghealth.nodes.user.UserDetails;
import com.diaghealth.repository.ClinicRepo;
import com.diaghealth.repository.DoctorRepo;
import com.diaghealth.repository.LabRepo;
import com.diaghealth.repository.PatientRepo;
import com.diaghealth.repository.UserDetailsRepo;
import com.diaghealth.utils.UserType;

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
			node = new Doctor(details);
			break;
		case LAB:
			node = new Lab(details);
			break;
		case CLINIC:
			node = new Clinic(details);
			break;
		case PATIENT:
			node = new Patient(details);
			break;
		default:
			break;
		}
		return node;
	}
	
	public UserDetails saveUser(UserDetails user, Long id){
		setDateTime(user, id);
		logger.debug("Saving User: " + user.getUsername());
		return (UserDetails)saveUserByType(user.getUserType(), user);
	}
	
	public UserDetails saveUserByType(UserType type, UserDetails userDetails){
		UserDetails user = null;
		switch(type){
			case DOCTOR:
				if(userDetails instanceof Doctor){
					user = doctorRepo.save((Doctor)userDetails);
				}
				break;
			case CLINIC:
				if(userDetails instanceof Clinic){
					user = clinicRepo.save((Clinic)userDetails);
				}				
				break;
			case LAB:
				if(userDetails instanceof Lab){
					user = labRepo.save((Lab)userDetails);
				}
				break;
			case PATIENT:
				if(userDetails instanceof Patient){
					user = patientRepo.save((Patient)userDetails);
				}
				break;
			default:
				break;
		}	
		return user;
	}
	
	public UserDetails getActualObjectByIdType(Long id, UserType type){
		UserDetails user = null;
		switch(type){
			case DOCTOR:
				user = doctorRepo.findOne(id);
				break;
			case CLINIC:
				user = clinicRepo.findOne(id);
				break;
			case LAB:
				user = labRepo.findOne(id);
				break;
			case PATIENT:
				user = patientRepo.findOne(id);
				break;
			default:
				break;
		}	
		return user;
	}
	
	/*public GraphRepository getRepoByType(UserType type){
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
	}*/

}

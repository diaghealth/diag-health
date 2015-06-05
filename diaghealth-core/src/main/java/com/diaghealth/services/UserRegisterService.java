package com.diaghealth.services;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diaghealth.nodes.user.UserDetails;
import com.diaghealth.repository.UserDetailsRepo;
import com.diaghealth.utils.UserType;

@Component
public class UserRegisterService {
	
	@Autowired
	UserDetailsRepo userDetailsRepo;
	@Autowired
	UserRepositoryService userRepositoryService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//creatorId can be null
	public boolean saveDetails(UserDetails details, Long creatorId){
		return userRepositoryService.saveUser(details, creatorId) != null;
	}
	
	public boolean saveDetails(UserDetails details){
		return saveDetails(details, null);
	}
	
	
	
	public boolean updateDetails(UserDetails details, Long id){
		return saveDetails(details, id);
	}
	
	public boolean saveRelationship(UserDetails user, Long relatedId, UserType relatedUserType){
		//String relationship = RelationshipUtil.getRelationship(user.getUserType(), relatedUserType);
		String relationship = "RELATED_BOTH";
		return userDetailsRepo.saveRelation(user.getId(), relatedId, relationship) != null;
	}
	
	/*Everytime a user is saved, the actual user details should be fetched from DB and details be appended before saving*/
	public boolean saveRelationshipNode(UserDetails node, Long relatedId, UserType relatedUserType){
		UserDetails related = userDetailsRepo.findOne(relatedId);
		Set<UserDetails> relatedList = node.getRelatedList();
		if(relatedList == null){
			relatedList = new HashSet<UserDetails>();
			node.setRelatedList(relatedList);
		}
		
		relatedList.add(related);
		//return UserDetailsUtils.getRepoByType(UserType.valueOf(node.getUserType())).save(node) != null;
		return userRepositoryService.saveUser(node, null) != null;
	}

}

package com.diaghealth.services;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diaghealth.models.SearchTestViewDto;
import com.diaghealth.models.SearchUserViewDto;
import com.diaghealth.nodes.user.UserDetails;
import com.diaghealth.repository.SearchRepo;
import com.diaghealth.utils.UserType;

@Component
public class SearchService {
	
	private static final double RADIUS_EARTH = 6370;
	private static final double DEG_TO_RADIAN = Math.PI / 180;
	private static final double RADIAN_TO_DEG = 180 / Math.PI;
	private static Logger logger = LoggerFactory.getLogger(SearchService.class);
	
	@Autowired
	SearchRepo searchRepo;	
	@Autowired
	UserRepositoryService userRepoService;
	
	public UserDetails verifyLoginuser(String username, String password){
		UserDetails match = searchRepo.findByUsernameAndPassword(username, password);
		if(match == null){
			logger.info("Cannot find username/pwd " + username + "/" + password);
			return null;
		}
		
		return userRepoService.getActualObjectByIdType(match.getId(), match.getUserType());
	}
	
	public UserDetails userExists(UserDetails user){
		return searchRepo.findByUsername(user.getUsername());
	}
	
	public Set<UserDetails> searchUsers(SearchUserViewDto searchObject){
		if(!StringUtils.isEmpty(searchObject.getName())){
			logger.debug("Searching user by name: " + searchObject.getName() + " type " + searchObject.getUserType().name());
			return searchUsersByName(searchObject.getName(), searchObject.getUserType().name());
		}
		else if(searchObject.getPhoneNumber() != null){
			logger.debug("Searching user by phone: " + searchObject.getPhoneNumber() + " type " + searchObject.getUserType().name());
			return searchUsersByPhone(searchObject.getPhoneNumber(), searchObject.getUserType().name());
		}
		else if(searchObject.getLatLocation() != null && searchObject.getLongLocation() != null){
			int distance = (searchObject.getDistance() == null) ? 10 : searchObject.getDistance(); //10 km
			logger.debug("Searching user by Lat: " + searchObject.getLatLocation() + 
					"Long: " + searchObject.getLongLocation() + " distance: " + distance + " type " + searchObject.getUserType().name());
			return searchUsersByLocation(searchObject.getLatLocation(), searchObject.getLongLocation(), distance,
					 searchObject.getUserType().name());
		}
		return null;
	}
	
	
	public UserDetails findById(Long id){
		return searchRepo.findOne(id);
	}
	
	private Set<UserDetails> getUserDetailsSet(Iterable<UserDetails> matches){
		
		if(matches == null)
			return null;
		
		Set<UserDetails> userDetailsDtoList = new HashSet<UserDetails>();
		for(UserDetails match: matches){
			userDetailsDtoList.add(match);
		}		
		return userDetailsDtoList;
	}
	
	public Set<UserDetails> searchUsersByName(String firstname, String type){
		
		Iterable<UserDetails> matches = searchRepo.findByUsernameUserType(firstname,type);
		return getUserDetailsSet(matches);
	}
	
	public Set<UserDetails> searchUsersByPhone(Long phoneNumber, String type){
		Iterable<UserDetails> matches = searchRepo.findByPhoneNumber(phoneNumber,type);
		return getUserDetailsSet(matches);
	}
	
	public Set<UserDetails> searchRelatedUsers(Long id, UserType userType){		
		Iterable<UserDetails> matches = searchRepo.findByRelation(id, userType.name());
		return getUserDetailsSet(matches);
		
	}
	
	public Set<UserDetails> searchUsersByLocation(Double latLocation, Double longLocation, int distance, String type){
		
		Double[] latLong = getLimitingCoordinates(latLocation, longLocation, distance);
		Iterable<UserDetails> matches = searchRepo.findByLocation(latLong[0], latLong[1], latLong[2], latLong[3], type);
		return getUserDetailsSet(matches);
	}
	
	 /*lat1, long1, lat2, long2 and lat1 < lat2 and long1 < long2*/
	public Double[] getLimitingLocation(Double latLocation, Double longLocation, int distance){
		Double[] latLong = new Double[4];
		
		double d = (distance/RADIUS_EARTH);
		double firstTerm = Math.sin(DegreesToRadians(latLocation))*Math.cos(d);
		double secondTerm = Math.cos(DegreesToRadians(latLocation))*Math.sin(d);
		latLong[0] = Math.asin(firstTerm + secondTerm); 
		latLong[2] = Math.asin(firstTerm - secondTerm); 
		
		return latLong;
	}
	
	public Double[] getLimitingCoordinates(Double latLocation, Double longLocation, double distance){
		Double[] latLong = new Double[4];
		
		double d = (distance/RADIUS_EARTH);
		double theta = RadiansToDegrees(d);
		latLong[1] = longLocation - theta;
		latLong[3] = longLocation + theta;
		latLong[0] = latLocation - theta;
		latLong[2] = latLocation + theta;
		return latLong;
	}
	
	public static double DegreesToRadians(double degrees)
	{
	    
	    return degrees * DEG_TO_RADIAN;
	}
	
	public static double RadiansToDegrees(double radians)
	{
	    
	    return radians * RADIAN_TO_DEG;
	}

	
}

package com.diagonline.repository.test;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.diagonline.nodes.ReceiptObject;
import com.diagonline.nodes.labtest.LabTestDoneObject;
import com.diagonline.nodes.user.UserDetails;
import com.diagonline.repository.LabTestDoneRepo;
import com.diagonline.repository.ReceiptRepo;
import com.diagonline.repository.SearchRepo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/diagonline-repository-context.xml" )
public class RepositoryTest {
	
	@Autowired
	SearchRepo searchRepo;	
	@Autowired
	ReceiptRepo receiptRepo;
	@Autowired
	LabTestDoneRepo labTestDoneRepo;
	
	/*@Test
	public void testSearchRepo(){
		Iterable<UserDetails> matches = searchRepo.findByLocation(28.367451842659083,77.10922904265912,
				28.727236957340917,77.46901415734096,"DOCTOR");		
		
		matches = searchRepo.findByRelation(new Long(0), "DOCTOR");
		showList(matches);
		System.out.println("Completed Test");
		
	}
	
	@Test
	public void searchReceiptRepo(){
		ReceiptObject objects = receiptRepo.findById("dummy");				
	}*/
	
	@Test
	public void searchTestDoneRepo(){
		Set<LabTestDoneObject> labTestDoneSet = labTestDoneRepo.findTestsByIdAndDate(new Long(0), new Long(4), new Date().getTime(), DateUtils.addDays(new Date(), 1).getTime() );
		for(LabTestDoneObject test: labTestDoneSet){
			System.out.println(test);
		}
	}
	
	private void showList(Iterable<UserDetails> matches){
		if(matches !=null && matches.iterator().hasNext()){
			
			UserDetails u = matches.iterator().next();
			System.out.println("found match" + u.toString());
		}
	}
	
	/*public static void main(String[] args){
		RepositoryTest repoTestObj = new RepositoryTest();
		Iterable<UserDetails> matches = repoTestObj.getSearchRepo().findByLocation(28.0,78.0,29.0,79.0,"DOCTOR");
		if(matches !=null && matches.iterator().hasNext()){
			UserDetails u = matches.iterator().next();
			UserDetailsDto userDetailsDto = new UserDetailsDto();
		}
	}

	public SearchRepo getSearchRepo() {
		return searchRepo;
	}

	public void setSearchRepo(SearchRepo searchRepo) {
		this.searchRepo = searchRepo;
	}*/

}

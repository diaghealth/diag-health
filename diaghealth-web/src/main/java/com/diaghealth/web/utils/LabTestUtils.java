package com.diaghealth.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.diaghealth.nodes.labtest.LabTestAvailablePrice;
import com.diaghealth.nodes.labtest.LabTestDetails;


public class LabTestUtils {
	
	private static Logger logger = LoggerFactory.getLogger(LabTestUtils.class);
	
	public static void putAllTestsInModel(ModelAndView mv, List<LabTestDetails> allTests) {
		
		HashMap<String, List<LabTestDetails>> mapTypeTest = new HashMap<String, List<LabTestDetails>>();
		if(allTests != null){
		
			for(LabTestDetails test: allTests){
				List<LabTestDetails> testOfType = mapTypeTest.get(test.getType());
				if(testOfType == null){
					testOfType = new ArrayList<LabTestDetails>();
					mapTypeTest.put(test.getType(), testOfType);
				}
				testOfType.add(test);				
			}	
		
			//mv.getModel().put("allTests", mapTypeTest);
			try{
				String jsonMap = new ObjectMapper().writeValueAsString(mapTypeTest);
				mv.getModel().put("jsonMap", jsonMap);
			} catch(Exception e){
				logger.error("Cannot create jsonMap");
			}

			
		}
	}
	
	public static void putAvailableTestsInModel(ModelAndView mv, List<LabTestAvailablePrice> allTests){
		
		HashMap<String, List<LabTestAvailablePrice>> mapTypeTest = new HashMap<String, List<LabTestAvailablePrice>>();
		if(allTests != null){
		
			for(LabTestAvailablePrice test: allTests){
				List<LabTestAvailablePrice> testOfType = mapTypeTest.get(test.getType());
				if(testOfType == null){
					testOfType = new ArrayList<LabTestAvailablePrice>();
					mapTypeTest.put(test.getType(), testOfType);
				}
				testOfType.add(test);				
			}	
		
			//mv.getModel().put("allTests", mapTypeTest);
			try{
				String jsonMap = new ObjectMapper().writeValueAsString(mapTypeTest);
				mv.getModel().put("jsonMap", jsonMap);
			} catch(Exception e){
				logger.error("Cannot create jsonMap");
			}
		}
	}

}

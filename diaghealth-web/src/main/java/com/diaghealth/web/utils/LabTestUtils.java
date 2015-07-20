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
	
	/*public static void putAllTestsInModel(ModelAndView mv, List<LabTestDetails> allTests) {
		
		HashMap<String, HashMap<String, List<LabTestDetails>>> mapTypeTest = new HashMap<String, HashMap<String, List<LabTestDetails>>>();
		if(allTests != null){
		
			for(LabTestDetails test: allTests){
				HashMap<String, List<LabTestDetails>> testOfType = mapTypeTest.get(test.getType());
				if(testOfType == null){
					testOfType = new HashMap<String, List<LabTestDetails>>();					
					mapTypeTest.put(test.getType(), testOfType);
				}
				//testOfType = mapTypeTest.get(test.getType());
				List<LabTestDetails> testList = testOfType.get(test.getName());
				if(testList == null){
					testList = new ArrayList<LabTestDetails>();
					testOfType.put(test.getName(), testList);
				}
				testList.add(test);				
			}	
		
			//mv.getModel().put("allTests", mapTypeTest);
			try{
				String jsonMap = new ObjectMapper().writeValueAsString(mapTypeTest);
				mv.getModel().put("jsonMap", jsonMap);
			} catch(Exception e){
				logger.error("Cannot create jsonMap");
			}

			
		}
	}*/
	
	public static <T> void putAllTestsInModel(ModelAndView mv, List<T> allTests){
		HashMap<String, HashMap<String, List<T>>> mapTypeTest = new HashMap<String, HashMap<String, List<T>>>();
		if(allTests != null){
		
			for(T test: allTests){
				HashMap<String, List<T>> testOfType = mapTypeTest.get(((LabTestDetails)test).getType());
				if(testOfType == null){
					testOfType = new HashMap<String, List<T>>();					
					mapTypeTest.put(((LabTestDetails)test).getType(), testOfType);
				}
				//testOfType = mapTypeTest.get(test.getType());
				List<T> testList = testOfType.get(((LabTestDetails)test).getName());
				if(testList == null){
					testList = new ArrayList<T>();
					testOfType.put(((LabTestDetails)test).getName(), testList);
				}
				testList.add(test);				
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
	
	/*public static void putAvailableTestsInModel(ModelAndView mv, List<LabTestAvailablePrice> allTests){
		
		HashMap<String, HashMap<String, List<LabTestAvailablePrice>>> mapTypeTest = new HashMap<String, HashMap<String, List<LabTestAvailablePrice>>>();
		if(allTests != null){
		
			for(LabTestAvailablePrice test: allTests){
				HashMap<String, List<LabTestAvailablePrice>> testOfType = mapTypeTest.get(test.getType());
				if(testOfType == null){
					testOfType = new ArrayList<LabTestAvailablePrice>();
					mapTypeTest.put(test.getType(), testOfType);
				}
				testOfType.add(test);		
				if(testOfType == null){
					testOfType = new HashMap<String, List<LabTestAvailablePrice>>();					
					mapTypeTest.put(test.getType(), testOfType);
				}
				//testOfType = mapTypeTest.get(test.getType());
				List<LabTestDetails> testList = testOfType.get(test.getName());
				if(testList == null){
					testList = new ArrayList<LabTestDetails>();
					testOfType.put(test.getName(), testList);
				}
				testList.add(test);	
			}	
		
			//mv.getModel().put("allTests", mapTypeTest);
			try{
				String jsonMap = new ObjectMapper().writeValueAsString(mapTypeTest);
				mv.getModel().put("jsonMap", jsonMap);
			} catch(Exception e){
				logger.error("Cannot create jsonMap");
			}
		}
	}*/

}

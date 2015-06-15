package com.diaghealth.services;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diaghealth.nodes.ReceiptObject;
import com.diaghealth.nodes.user.UserDetails;
import com.diaghealth.repository.ReceiptRepo;

@Component
public class ReceiptService {
	
	@Autowired
	ReceiptRepo receiptRepo;
	private static final int validity = 10;
	
	private static final int MAX_RECEIPT_ID_LENGTH = 10;
	private static Logger logger = LoggerFactory.getLogger(ReceiptService.class);
	
	public ReceiptObject createReceipt(UserDetails creator, UserDetails subjectUser){
		ReceiptObject receipt = new ReceiptObject();
		String random = RandomStringUtils.randomAlphanumeric(MAX_RECEIPT_ID_LENGTH).toUpperCase();
		while(receiptRepo.findById(random) != null){
			random = RandomStringUtils.randomAlphanumeric(MAX_RECEIPT_ID_LENGTH).toUpperCase();
		}
		receipt.setReceiptId(random);
		receipt.setDateCreated(new Date());
		receipt.setValidTill(DateUtils.addDays(new Date(), validity));		
		receipt.setCreatorId(creator.getId());
		receipt.addRelatedUsers(creator);
		receipt.setSubject(subjectUser);
		receipt = receiptRepo.save(receipt); 
		logger.debug("Created New Receipt: " + receipt);
		return receipt;		
	}	
	
	public ReceiptObject save(ReceiptObject obj){
		logger.debug("Saving receipt " + obj);
		return receiptRepo.save(obj);
	}
	
	public ReceiptObject findReceipt(String id){
		return receiptRepo.findById(id);
	}
	
	public ReceiptObject findReceipt(Long id){
		return receiptRepo.findOne(id);
	}
	
	public boolean setValidity(Long id, Date date){
		return receiptRepo.setValidity(id, date) != null;
	}

}

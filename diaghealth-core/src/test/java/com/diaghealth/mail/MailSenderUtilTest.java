package com.diaghealth.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/diaghealth-core.xml" )
public class MailSenderUtilTest {
	
	@Autowired
	MailSenderUtil mailSenderUtil;

	@Test
	public void sendTestMail(){
		mailSenderUtil.addToMail("i.m.sanu@gmail.com");
		mailSenderUtil.setSubject("Test Mail Subject");
		mailSenderUtil.setBody("Test Mail Body");
		mailSenderUtil.setFromMail("contact@diaghealth.com");
		
		mailSenderUtil.sendMail();
	}
}

package com.diaghealth.mail;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MailSenderUtil {
	@Autowired
	JavaMailSender mailSender;
	private static Logger logger = LoggerFactory.getLogger(MailSenderUtil.class);
	
	private String fromMail = new String("contact@diaghealth.com");
	private List<String> toMail = new ArrayList<String>();
	private String subject;
	private String attachment;
	private String body;
	
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public String getFromMail() {
		return fromMail;
	}
	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}
	public List<String> getToMail() {
		return toMail;
	}
	public void setToMail(List<String> toMail) {
		if(toMail != null)
			this.toMail = toMail;
	}
	public void addToMail(String toMail){
		if(!StringUtils.isEmpty(toMail))
			this.toMail.add(toMail);
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	private InternetAddress[] getInternetAddressFromMailId(){
		List<InternetAddress> addresses = new ArrayList<InternetAddress>();
		for(String email: toMail){
			try{
			addresses.add(new InternetAddress(email));
			} catch (AddressException exc){
				logger.error("Cannot send mail. Invalid email id: " + email);
				logger.debug(exc.toString());
			}
		}
		return addresses.toArray(new InternetAddress[addresses.size()]);
	}
	
	public void sendMail(){
		try{
		MimeMessagePreparator mimeMessagePrep = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipients(Message.RecipientType.TO, getInternetAddressFromMailId());
                mimeMessage.setFrom(new InternetAddress(fromMail));
                //mimeMessage.setText(body);
                mimeMessage.setContent(body, "text/html");
                mimeMessage.setSubject(subject);
                logger.debug("Preparing mail: " + mimeMessage);
                 
			    /*MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			    message.setFrom(fromMail);
			    message.setTo(toMail);
			    message.setSubject(subject);
			    message.setText(body, true);
			    if(attachment != null)
			    	message.addAttachment(attachment, new File(attachment));*/
			  }
			};
		mailSender.send(mimeMessagePrep);
		reset();
		logger.debug("Mail sent");
		}catch(Exception e){
			logger.error("Cannot send mail: Exception" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void reset(){
		subject = "";
		attachment = "";
		body = "";
		toMail = new ArrayList<String>();		
	}
}

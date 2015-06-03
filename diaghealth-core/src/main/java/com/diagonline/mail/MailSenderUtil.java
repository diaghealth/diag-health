package com.diagonline.mail;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class MailSenderUtil {
	@Autowired
	JavaMailSender mailSender;
	private static Logger logger = LoggerFactory.getLogger(MailSenderUtil.class);
	
	private String fromMail;
	private String toMail;
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
	public String getToMail() {
		return toMail;
	}
	public void setToMail(String toMail) {
		this.toMail = toMail;
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
	
	public void sendMail(){
		//try{
		MimeMessagePreparator mimeMessagePrep = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(toMail));
                mimeMessage.setFrom(new InternetAddress(fromMail));
                mimeMessage.setText(body);
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
		logger.debug("Mail sent");
		/*}catch(Exception e){
			logger.error("Canot sent mail: Exception" + e.getMessage());
			e.printStackTrace();
		}*/
	}
}

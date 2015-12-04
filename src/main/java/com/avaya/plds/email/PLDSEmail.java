/**
 * 
 */
package com.avaya.plds.email;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class PLDSEmail {

	/* mail address infomation */
	private static String MAIL_HOST = "mailhost.avaya.com";
	private static String FROM = "do_not_reply@email.com";
	private static String SUBJECT = "PLDS Financial Report -";
	private static int PORT = 25;
	private Session session = null;
	private MimeMessage mimeMessage = null;
	
	private Properties mailProp = null;
	
	
	public String sendEmail(String emailid,String fileName,String inputFileName) throws AddressException, MessagingException{
		System.out.println("Inside of sendEmail");
		session = Session.getDefaultInstance(getMailProperties());
		Transport.send(getMailBody(emailid, fileName,inputFileName));
		System.out.println("Mail sent successfully with attachment");
		return "Mail sent successfully with attachment";
	}
	
	
	public MimeMessage getMailBody(String to, String fileName,String uploadedFileName) throws AddressException, MessagingException{
		System.out.println("inside of mail getMailBody .... ");
		mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(FROM));
		mimeMessage.setSubject(SUBJECT+"- "+ uploadedFileName);
		System.out.println("1");
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText("Please find the Finace Report as attachment");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		System.out.println("11");
		messageBodyPart = new MimeBodyPart();
		System.out.println("12");
		DataSource dataSource = new FileDataSource(new File(fileName));
		System.out.println("13");
		messageBodyPart.setDataHandler(new DataHandler(dataSource));
		System.out.println("14");
		messageBodyPart.setFileName(fileName);
		multipart.addBodyPart(messageBodyPart);
		mimeMessage.setContent(multipart);
		
		return mimeMessage;
	}
	
	public Properties getMailProperties(){
		mailProp = new Properties();
		System.out.println("inside of mail properties .... ");
	      mailProp.put("mail.smtp.host", MAIL_HOST);
	      mailProp.put("mail.smtp.port", PORT);
		
		return mailProp;
	}
	
	
	public void sendExceptionMail(String exceptionStackTrace,String userEmailId) throws MessagingException{
		
		 session = Session.getDefaultInstance(getMailProperties());
		 mimeMessage = new MimeMessage(session);
		 mimeMessage.setFrom(new InternetAddress(FROM));
		 mimeMessage.setSubject(SUBJECT+"-  Exception Occured ");
		 
		 mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("mkarunakar@avaya.com"));
		 BodyPart messageBodyPart = new MimeBodyPart();
		 messageBodyPart.setText(exceptionStackTrace +"\r\n"+ userEmailId);
		 Multipart multipart = new MimeMultipart();
		 multipart.addBodyPart(messageBodyPart);
		 
		 mimeMessage.setContent(multipart);
		 Transport.send(mimeMessage);
		 
		 
	}
	
	
}

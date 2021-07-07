package com.fiserv.b2bclient.client;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class EmailRead {
	
	private static final String MAIL_POP_HOST = "pop.gmail.com";
	private static final String MAIL_STORE_TYPE = "pop3";
	private static final String POP_USER = "slautomation2018@gmail.com";
	private static final String POP_PASSWORD = "njrbvpqnduulbjny";
	private static final String POP_PORT = "995";

	public static void getMails(String user, String password) {
		user = "slautomation2018@gmail.com";
		password = "test1234!";
		try {
			// create properties field
			Properties properties = new Properties();
			properties.put("mail.pop3.host", MAIL_POP_HOST);
			properties.put("mail.pop3.port", POP_PORT);
			properties.put("mail.pop3.starttls.enable", "true");
			properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			// Session emailSession = Session.getDefaultInstance(properties);
			Session emailSession = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(POP_USER, POP_PASSWORD);
				}
			});
			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore(MAIL_STORE_TYPE);
			store.connect(MAIL_POP_HOST, user, password);
			// create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);
			
			// retrieve the messages from the folder in an array and print it
			
			Message[] messages = emailFolder.getMessages();
			int m = messages.length-1;
			int y = messages.length-2;
			System.out.println(messages[m]);
			System.out.println(messages[y]);
			System.out.println("messages.length---" + messages.length);
			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];
				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println(message.getSubject());
				System.out.println(message.getFrom()[0]);
				System.out.println(message.getContent().toString());
				System.out.println(message.getReceivedDate());
		
			}
			
			 
			
			// close the store and folder objects
			emailFolder.close(false);
			store.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		getMails(POP_USER, POP_PASSWORD);
	}
}

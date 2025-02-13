package org.oegam.gestorcorreos.beans;


import javax.mail.PasswordAuthentication;

public class SMTPAuthentication extends javax.mail.Authenticator {
	
	private PasswordAuthentication passwordAuthentication;

	/**
	 * 
	 */
	public SMTPAuthentication(){
		super();
	}

	/**
	 * 
	 * Constructor que se le pasa el par usuario/password
	 * 
	 * @param username
	 * @param password
	 */
	public SMTPAuthentication(String username, String password) {
		super();
		passwordAuthentication = new PasswordAuthentication(username, password);
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return passwordAuthentication;
	}

}

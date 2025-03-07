package utiles.correo;


import javax.mail.PasswordAuthentication;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.web.OegamExcepcion;

public class SMTPAuthentication extends javax.mail.Authenticator {

	private PasswordAuthentication passwordAuthentication;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	/**
	 * 
	 * Constructor que toma los valores de usuario y password del properties
	 * gestorCorreo.properties
	 * 
	 * @throws OegamExcepcion
	 */
	public SMTPAuthentication() throws OegamExcepcion {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		String username = gestorPropiedades.valorPropertie("mail.usuario");
		String password = gestorPropiedades.valorPropertie("mail.password");
		passwordAuthentication = new PasswordAuthentication(username, password);
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
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		passwordAuthentication = new PasswordAuthentication(username, password);
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return passwordAuthentication;
	}

}

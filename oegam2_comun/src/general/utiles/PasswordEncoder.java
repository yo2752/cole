package general.utiles;

import java.io.FileInputStream;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import oegam.constantes.ValoresSchemas;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class PasswordEncoder {

	/* INICIO DECLARACIÓN DE ATRIBUTOS */

	private String password;

	private String passwordMessage;
	private final static String SECRET_KEY_URL = "secret.key.url";
	private static final ILoggerOegam log = LoggerOegam.getLogger(PasswordEncoder.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;

	/* FIN DECLARACIÓN DE ATRIBUTOS */

	/* INICIO MÉTODOS LÓGICOS */

	public PasswordEncoder(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getPassword(){

		//Si no se ha especificado el key store de las claves públicas se coje el definido por defecto
		try {
			ILoggerOegam log = LoggerOegam.getLogger(ValoresSchemas.class);
			String passwordCodificado = gestorPropiedades.valorPropertie(passwordMessage);
			String claveCifrado = obtenerClaveCifrado();
			password = TripleDES.decrypt(claveCifrado, passwordCodificado, log);
		}  catch (Throwable e) {
			log.error("Se ha producido una excepcion al obtener la password", e);
		} 
		return password;
	}

	/**
	 * Obtiene la clave de cifrado almacenada en el secret.key
	 * 
	 * @return
	 * @throws Throwable
	 */
	private String obtenerClaveCifrado() throws Throwable{
		//Se lee el fichero secret.key para obtener la clave de cifrado de contraseñas.
		FileInputStream fis = new FileInputStream(gestorPropiedades.valorPropertie(SECRET_KEY_URL));
		byte[] datos = new byte[fis.available()];
		fis.read(datos);
		//Clave de cifrado
		String claveCifrado = new String(datos);
		return claveCifrado;
	}

	// Método para comprobar el valor encriptado de lo que queramos llevar al properties.
	public static void main(String[] args) {
		try {
			log.debug(TripleDES.encrypt("123453343425345", "", log));
			String passwordKeyStoreClavePublica = TripleDES.encrypt("123453343425345", "", log);
			System.out.println(passwordKeyStoreClavePublica);
			log.debug(passwordKeyStoreClavePublica);
		} catch (Throwable e) {
			log.error(e);
		}
	}

	/* FIN MÉTODOS LÓGICOS */

	/* INICIO GETTERS Y SETTERS */

	public String getPasswordMessage() {
		return passwordMessage;
	}

	public void setPasswordMessage(String passwordMessage) {
		this.passwordMessage = passwordMessage;
	}

	/* FIN GETTERS Y SETTERS */

}
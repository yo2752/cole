package oegam.constantes;

import java.io.FileInputStream;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import general.utiles.TripleDES;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class ValoresSchemas {

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	private static final String SCHEMA_mensaje = "schema";
	private final static String SECRET_KEY_URL = "secret.key.url";
	private static final ILoggerOegam log = LoggerOegam.getLogger(ValoresSchemas.class);

	
public String getSchema(){
	
	//Si no se ha especificado el key store de las claves públicas se coje el de por defecto
	String schemaDecodificado="";
	try {
		ILoggerOegam log = LoggerOegam.getLogger(ValoresSchemas.class);
		String schemaCodificado = gestorPropiedades.valorPropertie(SCHEMA_mensaje); 
		String claveCifrado = obtenerClaveCifrado();
		schemaDecodificado = TripleDES.decrypt(claveCifrado, schemaCodificado, log);
	}  catch (Throwable e) {
		log.error(e);
	} 
	return schemaDecodificado;
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

public static void main(String[] args) {
	try {
		
		log.debug(TripleDES.encrypt("123453343425345", "OEGAM2", log));
		String passwordKeyStoreClavePublica = TripleDES.decrypt("123453343425345", "94F25DEC2ABAD4B7", log);
		log.debug(passwordKeyStoreClavePublica);
	}  catch (Throwable e) {
		log.error(e);
	}
	 
}


}
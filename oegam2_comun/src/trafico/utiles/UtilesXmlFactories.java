package trafico.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;


public class UtilesXmlFactories {
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilesXmlFactories.class);

	/**
	 * Convierte un File a String. Tiene forzada la codificacion UTF-8.
	 * @param ficheroAenviar File de un xml
	 * @return El fichero xml en String
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public static String xmlFileToString(File ficheroAenviar) throws FileNotFoundException  {
		return xmlFileToString(ficheroAenviar,"UTF-8");
	}
	
	/**
	 * Convierte un File a String. Es una
	 * sobrecarga del otro método, pero lleva como parametro la codificacion
	 * @param ficheroAenviar File de un xml
	 * @param codificacion Codificacion a usar (UTF-8, ISO-8859-1, ANSI...)
	 * @return El fichero xml en String
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public static String xmlFileToString(File ficheroAenviar, String codificacion) throws FileNotFoundException  {
		byte[] by = null; 
		
			FileInputStream fis;
			fis = new FileInputStream(ficheroAenviar);
			
			try {
				by = IOUtils.toByteArray(fis);
			} catch (IOException e) {
				log.error(e);
			}
				
		String  peticionXML = null;
		try {
			peticionXML = new String (by, codificacion);
		} catch (UnsupportedEncodingException e1){
			log.error(e1);
		}
		
		return peticionXML;
	}
	
	
}

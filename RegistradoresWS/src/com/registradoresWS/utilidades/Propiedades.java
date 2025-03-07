package com.registradoresWS.utilidades;

import java.util.Properties;

import org.apache.log4j.Logger;

public class Propiedades {
	
	/**
	 * Ahora el fichero de propiedades se recupera de tomcat-home/shared/lib y el fichero de propiedades se recupera como un objeto Properties
	 * Están comentadas las líneas de cuando el fichero de propiedades se recuperaba de un paquete de src de la aplicación como un ResourceBundle
	 */
	
	public static final String REGISTRADORES_WS_PROPERTIES = "registradoresWS.properties";
	//public static final String REGISTRADORES_WS_PROPERTIES = "com.registradoresWS.resources.registradoresWS";
	
	// PROPIEDADES
	public static final String XMLNS_XSI = "XMLNS_XSI";
	public static final String XSI_NON_NAME_SPACE_SCHEMA_LOCATION = "XSI_NON_NAME_SPACE_SCHEMA_LOCATION";
	public static final String ACK_VERSION = "ACK_VERSION";
	public static final String TIPO_ENTIDAD = "TIPO_ENTIDAD";
	public static final String CODIGO_ENTIDAD = "CODIGO_ENTIDAD";
	// FIN DE PROPIEDADES
	
	private static final Logger log = Logger.getLogger(Propiedades.class);
	//private static ResourceBundle fichero = null;
	private static Properties fichero = null;
	
	public static String leer(String propiedad) throws Exception{
		try{
			if(fichero == null){
				inicializar();
			}
			//return fichero.getString(propiedad);
			return fichero.getProperty(propiedad);
		}catch(Exception ex){
			log.error(ex);
			throw ex;
		}
	}
	
	private static void inicializar() throws Exception{
		
		try{
			//fichero = ResourceBundle.getBundle(REGISTRADORES_WS_PROPERTIES);
			fichero = new Properties();
			fichero.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(REGISTRADORES_WS_PROPERTIES));
		}catch(Exception ex){
			log.error(ex);
			throw ex;
		}
		
	}

}

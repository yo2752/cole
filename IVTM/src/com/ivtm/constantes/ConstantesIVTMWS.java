package com.ivtm.constantes;

public class ConstantesIVTMWS {

	public static final String TIPO_IVTM_GESTOR_FICHEROS = "IVTM";
	
	public static final String IVTMMATRICULACIONPETICION_GESTOR_FICHEROS = "MATRICULACION_PETICION";
	public static final String IVTMMATRICULACIONRESPUESTA_GESTOR_FICHEROS = "MATRICULACION_RESPUESTA";
	public static final String IVTMCONSULTAPETICION_GESTOR_FICHEROS = "CONSULTA_PETICION";
	public static final String IVTMCONSULTARESPUESTA_GESTOR_FICHEROS = "CONSULTA_RESPUESTA";

	public static final String IVTM_INICIO_NOMBRE_FICHERO_GUARDAR_PETICION = "peticion";
	public static final String IVTM_INICIO_NOMBRE_FICHERO_GUARDAR_RESPUESTA = "respuesta";
	
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	
	public static final String ENVINI = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
	public static final String ENVFIN = "</soapenv:Envelope>";

	public static final String RUTA_ESQUEMA_ALTA = "resources/schemas/alta/Peticion.xsd";
	public static final String RUTA_ESQUEMA_CONSULTA = "resources/schemas/consulta/Peticion.xsd";
	public static final String RUTA_ESQUEMA_MODIFICACION = "resources/schemas/modificacion/Peticion.xsd";
	
	public static final String CABECERA_XML="<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>";

	public static final int TIEMPO_TIMEOUT = 120000;

	public static final String TEXTO_LOG_PROCESO = "Proceso IVTM: ";

	
}

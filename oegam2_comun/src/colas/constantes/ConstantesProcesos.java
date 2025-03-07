package colas.constantes;

public class ConstantesProcesos {

	public static final String INDICE = "indice";
	public static final String RETORNO = "retorno";
	public static final String TIMEOUT = "timeout";
	public static final String NO_SE_HA_PODIDO_RECUPERAR_LA_SOLICITUD = "No se ha podido recuperar la solicitud.";
	public static final String PETICION_CORRECTA = "peticion correcta";
	public static final String SIN_PETICIONES = "sin peticiones";
	public static final String ERROR_DE_WEBSERVICE = "error de webservice";
	public static final String DESCRIPCION = "descripcion:";
	public static final String CODIGO = "codigo:";
	public static final String ERROR_INTERNO_DEL_SERVIDOR = "Error interno del servidor.";
	public static final String ERROR_ESTADISTICAS_CTIT = "Error al recuperar las Estadisticas de CTIT.";
	public static final String ERROR_ESTADISTICAS_MATE = "Error al recuperar las Estadisticas de MATE.";
	public static final String EL_WEBSERVICE_NO_HA_DADO_UNA_RESPUESTA_CONOCIDA = " El webservice no ha dado una respuesta conocida:";
	public static final String ERROR_SSL_EN_LA_COMUNICACION_CON_EL_WEBSERVICE = "Error SSL en la comunicacion con el webservice";
	public static final String RESULTADO_WS = "resultadoWS:";
	public static final String EJECUCION_CORRECTA = "CORRECTA";
	public static final String EJECUCION_NO_CORRECTA = "DEVOLVIÓ ERROR";
	public static final String EJECUCION_EXCEPCION = "LANZÓ EXCEPCIÓN";
	public static final String EJECUCION_ENVIO_STOCK = "ENVIO_STOCK_OK";
	public static final String EXCEPCION_INVOCACION_WS = "Excepción en la invocación al servicio web: ";
	public static final String EJECUCION_CORRECTA_MENSAJE_X_DEFECTO = "Ejecucion correcta";
	public static final String INTEVE_MENSAJE_EJECUCION_CORRECTA = "Ejecución correcta del hilo. Todos los informes solicitados se han recibido";
	public static final String INTEVE_MENSAJE_EJECUCION_PARCIALMENTE_CORRECTA = "Ejecución correcta del hilo. Varios de los informes solicitados se han recibido";
	public static final String INTEVE_MENSAJE_EJECUCION_CORRECTA_SIN_INFORMES = "Ejecución correcta del hilo pero no se ha recibido ningún informe de los solicitados";
	public static final String SI = "SI";
	public static final String NO = "NO";
	public static final String ATEM_MENSAJE_EJECUCION_CORRECTA = "Ejecución correcta del hilo de Atem";
	public static final String ATEM_MENSAJE_EJECUCION_PARCIALMENTE_CORRECTA = "Ejecución correcta del hilo. Varios de los informes solicitados se han recibido";
	public static final String ATEM_MENSAJE_EJECUCION_CON_ERRORES = "Ejecución correcta del hilo. Varios de los informes solicitados han tenido error";
	
	public static final String FICHA_TECNICA="_ficha_tecnica";
	
	//respuestas de los webservice de CTIT
	public static final String ERROR = "ERROR";
	public static final String TRAMITABLE_CON_INCIDENCIAS = "TRAMITABLE_CON_INCIDENCIAS";
	public static final String NO_TRAMITABLE = "NO_TRAMITABLE";
	public static final String OK = "OK";
	public static final String TRAMITABLE = "TRAMITABLE";
	public static final String FINALIZADO_CON_ERROR = "FINALIZADO_CON_ERROR";
	public static final String JEFATURA = "JEFATURA";
	public static final String INFORME_REQUERIDO_PARA_TRAMITACION = "INFORME_REQUERIDO_PARA_TRAMITACIÓN";
	public static final String INFORME_REQUERIDO = "Informe Requerido";
	public static final String TRAMITABLE_JEFATURA_TRAFICO = "Tramitable en Jefatura";
	public static final String NO_TRAMITABLE_BTV = "No Tramitable";
	public static final String XML_PARA_JUSTIFICANTE_PROFESIONAL_NO_ENCONTRADO = "xml para justificante profesional no encontrado.";
	public static final String XML_PARA_NOTIFICATION_NO_ENCONTRADO = "xml para notification no encontrado.";
	public static final String XML_PARA_TRADE_NO_ENCONTRADO = "xml para trade no encontrado.";
	public static final String XML_PARA_EITV_NO_ENCONTRADO = "xml para eitv no encontrado.";
	public static final String ERROR_AL_DESCONTAR_CRÉDITOS = "Error al descontar créditos";
	public static final String ERROR_AL_CAMBIAR_ESTADO = "Error al cambiar el estado del trámite";
	public static final String XML_PARA_CHECK_CTIT_NO_ENCONTRADO = "XML para Check CTIT no encontrado";
	public static final String EMBARGO = "EMBARGO";
	public static final String PRECINTO = "PRECINTO";
	
	// Mantis 18007: Respuestas ProcesoImportacionSantander
	public static final String ERROR_RECUPERABLE = "DEVOLVIO ERROR RECUPERABLE";
	public static final String ERROR_NO_RECUPERABLE = "DEVOLVIO ERROR NO RECUPERABLE";
	public static final String SIN_DATOS = "NO SE ENCONTRARON DATOS";
	public static final String ERROR_RECUPERACION_FICHEROS = "Error en la recuperacion de los ficheros: ";
	public static final String ERROR_GUARDADO_DATOS = "Error en el guardado de los datos: ";
	public static final String ERROR_CUSTODIA_FICHEROS = "Error en la custodia de los ficheros: ";
	public static final String ERROR_BORRADO_FICHEROS = "Error en el borrado de los archivos del FTP: ";
	public static final String SIN_FICHEROS =	"No se han encontrado ficheros para importar";
	
	// Mantis 19728: Procesado de correos de liberacion de bastidores
	public static final String RECUPERACION_CORRECTA_CORREO_LIBERACION = "Recuperado y procesado el bastidor del correo de liberacion";
	public static final String RECUPERACION_CORRECTA_CORREO_LIBERACION_SIN_ACCION = "No se ha realizado accion sobre el bastidor del correo de liberacion";
	public static final String ERROR_RECUPERACION_CORREO_LIBERACION = "Error recuperacion bastidor correo liberacion ";
	
	//Mensajes para el usuario
	public static final String NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_AL_WEBSERVICE = "No se puede recuperar la información del trámite necesaria para invocar al webservice.";
	public static final String NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO = "No se puede recuperar la información del trámite necesaria para invocar el servicio.";
	
	// Códigos de error de MATW
	public static final String CODIGO_ERROR_BASTIDOR_YA_MATRICULADO = "SIMA01010";
	
	//Mensajes para proceso recuperacion de notificaciones de la seguridad social y envio de correo
	public static final String RECUPERACION_CORRECTA_NOTIFICACIONES = "La recuperación automática de notificaciones se ha ejecutado correctamente";
	
	// nombres de los procesos
	public static final String PROCESO_MATW 					= "MATW";
	public static final String PROCESO_WREG 					= "WREG";
	public static final String PROCESO_CHECKCTIT 				= "CHECKCTIT";
	public static final String PROCESO_FULLCTIT 				= "FULLCTIT";
	public static final String PROCESO_NOTIFICATIONCTIT 		= "NOTIFICATIONCTIT";
	public static final String PROCESO_TRADECTIT 				= "TRADECTIT";
	public static final String PROCESO_ENVIO_ESTADISTICAS		= "ENVIOESTADISTICASCTIT";
	public static final String PROCESO_ENVIO_FIRST_MATE			= "ENVIOFIRSTMATE";
	public static final String PROCESO_ENVIO_ULT_MATE			= "ENVIOULTMATE";
	public static final String PROCESO_CONSULTAEITV  			= "CONSULTAEITV";
	public static final String PROCESO_IVTM						= "IVTM";
	public static final String PROCESO_MODIFICACION_IVTM		= "MODIFICACION_IVTM";
	public static final String PROCESO_ELECTRONICMATE			= "ELECTRONICMATE";
	public static final String PROCESO_ISSUEPROFESSIONALPROOF	= "ISSUEPROPROOF";
	public static final String PROCESO_ISSUEPROFESSIONALPROOF_SEGA	= "ISSUEPROPROOFSEGA";
	public static final String PROCESO_VERIFYPROFESSIONALPROOF	= "VERIFYPROPROOF";
	public static final String PROCESO_GETPROFESSIONALPROOF 	= "GETPROPROOF";
	public static final String PROCESO_INFO_WS 					= "INFOWS";
	public static final String PROCESO_YERBABUENA 				= "YERBABUENA";
	public static final String PROCESO_DOC_BASE_GENERATOR 		= "DOC_BASE_GENERATOR";
	public static final String PROCESO_INTEVE 					= "INTEVE";
	public static final String PROCESO_INTEVE_ANUNTIS 		 	= "INTEVE4ANUNTIS";
	public static final String PROCESO_AVPO_ANUNTIS 			= "AVPOANUNTIS";
	public static final String PROCESO_ENTIDADES_FINANCIERAS    = "ENTIDADESFINANCIERAS";
	public static final String PROCESO_SEA_ENVIO_DS				= "SEA_ENVIO_DS";
	public static final String PROCESO_SIGA					    = "SIGA";
	public static final String PROCESO_CODIGO_ITV 			    = "CODIGOITV";
	public static final String PROCESO_EXCEL_DUPLICADOS		    = "EXCELDUPLICADOS";
	public static final String PROCESO_EXCEL_BAJAS		    	= "EXCELBAJAS";
	public static final String PROCESO_576						= "576";
	public static final String PROCESO_EEFF						= "EEFF";
	public static final String PROCESO_ATEM_ANUNTIS 		 	= "ATEM4ANUNTIS";
	public static final String IMPRIMIR_TRAMITES				= "IMPRIMIRTRAMITES";
	public static final String VEHICULO_PREMATICULADO_DATOS_EITV = "VPDATOSEITV";
	public static final String PROCESO_VEHICULO_PREMATRICULADO_FICHA_TECNICA_EITV = "VPFICHATECNICA";
	public static final String IMPORTACION_MASIVA				= "IMPORTACIONMASIVA";
	public static final String PROCESO_PRESENTACION_JPT	    	= "PRESENTACIONJPT";
	public static final String PROCESO_JUSTIFICANTE_PROF	    = "JUSTIFICANTE_PROF";

	public static final String LOG_APPENDER_JUSTIFICANTES = "JUSTIFICANTES";
	
	public static final String COMPROBAR_FECHA_LABORABLE = "comprobar.fecha.laborable.procesos";
	public static final String COMPROBAR_FESTIVO_NACIONAL = "comprobar.festivo.nacional.procesos";
	
	public static final String FORZAR_EJECUCION_MATRICULACION = "forzar.ejecucion.matriculacion";
	public static final String FORZAR_EJECUCION_TRANSMISION = "forzar.ejecucion.transmision";
	public static final String FORZAR_EJECUCION_BAJA = "forzar.ejecucion.baja";
	
	//Constantes Procesos Atex5
	public static String TIPO_SOLICITUD_PROCESO_CONSULTA_PERS_ATEX5 		= "consultaProPersAtex5";
	public static String TIPO_SOLICITUD_PROCESO_CONSULTA_VEH_ATEX5 			= "consultaProVehAtex5";
	public static String TIPO_SOLICITUD_PROCESO_CONSULTA_DOC_ATEX5 			= "consultaProDocAtex5";
	public static String TIPO_SOLICITUD_PROCESO_CONSULTA_EUCARIS_ATEX5 		= "consultaProEcrsAtex5";

	//Constantes Procesos Cayc (Arrendatario - Conductor Habitual)
	public static String TIPO_SOLICITUD_PROCESO_ALTA_ARRENDATARIO		= "altaProArrend";
	public static String TIPO_SOLICITUD_PROCESO_MODIFICACION_ARRENDATARIO = "modProArrend";
	public static String TIPO_SOLICITUD_PROCESO_ALTA_CONDUCTOR = "altaProCond";
	public static String TIPO_SOLICITUD_PROCESO_MODIFICACION_CONDUCTOR = "modProCond";
	
}

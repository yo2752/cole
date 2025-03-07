package trafico.utiles.constantes;

/**
 * Clase de constantes
 * @author 	TB·Solutions
 * @version	1.0.0
 * @since
 */
public class Constantes {
	
	//MANDATO_PERMISOS
	//General
	public static final String TRUE_VALUE="1";
	public static final String ORIGEN_OEGAM = "origenOegam";
	public static final String ORIGEN_EXTERNO = "origenExterno";
	public static final String VERSION_ACTUAL_MATE = "MATW";
	public static final String VERSION_ELECTRONICA_MATE = "2.5";
	public static final String TRUE = "true";
	
	// Rutas esquemas xsd de matriculación:
	public static final String PROPERTIES_RUTA_ESQUEMA_MATRICULACION = "RUTA_ESQUEMA_MATRICULACION";
	public static final String PROPERTIES_RUTA_ESQUEMA_MATRICULACION_MATW = "RUTA_ESQUEMA_MATRICULACION_MATW";
	public static final String PROPERTIES_RUTA_ESQUEMA_TRANSMISION = "RUTA_ESQUEMA_TRANSMISION";
	public static final String PROPERTIES_RUTA_ESQUEMA_BAJA = "RUTA_ESQUEMA_BAJA";	
	public static final String PROPERTIES_RUTA_ESQUEMA_FACTURACION_SIGA = "RUTA_ESQUEMA_FACTURACION_SIGA";
	public static final String PROPERTIES_RUTA_ESQUEMA_DUPLICADO = "RUTA_ESQUEMA_DUPLICADO";
	public static final String PROPERTIES_RUTA_ESQUEMA_SOLICITUD = "RUTA_ESQUEMA_SOLICITUD";
	
	//Permisos
	public static final String PERM_SUFIJO_CONSULTA = "_C";
	public static final String PERM_VEHICULO_MATRICULACION_C = "1" + PERM_SUFIJO_CONSULTA;
	
	//Displaytag
	public static final String DISPLAYTAG_ORDER_ASC="1";
	public static final String DISPLAYTAG_ORDER_DES="2";

	//Session
	public static final String APPLICATION_SESSION="rampart.application.session";
	public static final String SESSION_USERPROFILE="UserProfile";
	public static final String SESSION_RESULTBEAN="resultBean";
	public static final String SESSION_VISTA_LISTADO_PARAMS="VistaListadoParamsBean";
	public static final String SESSION_TOKEN="token";
	public static final String SESSION_DATALIST="dataList";
	public static final String ADMIN_TEMP_DIR = "adminTempDir";

	//Sequences
	public static final String SEQ_ADMINISTRADOR="ADMINISTRADOR_SEQ";
	public static final String SEQ_CONTRATO="CONTRATO_SEQ";
	public static final String SEQ_USUARIO="USUARIO_SEQ";
	public static final String SEQ_TRAFICO_TRAMITE="TRAFICO_TRAMITE_SEQ";
	public static final String SEQ_NUM_EXPEDIENTE="TRAFICO_NUM_EXPEDIENTE_SEQ";
	public static final String SEQ_DGT_MUNICIPIO="DGT_MUNICIPIO_SEQ";
	public static final String SEQ_DGT_MARCA="DGT_MARCA_SEQ";
	public static final String SEQ_TRAFICO_CASILLA_TRAMITE = "TRAFICO_CASILLA_TRAMITE_SEQ";
	public static final String SEQ_TRAFICO_EVOLUCION_TRAMITE = "TRAFICO_EVOLUCION_TRAMITE_SEQ";
	public static final String SEQ_TRAFICO_TASA = "TRAFICO_TASA_SEQ";
	public static final String SEQ_TRAFICO_TRAMITE_DOCUMENTO = "TRAFICO_TRAMITE_DOCUMENTO_SEQ";
	public static final String SEQ_LOG_ENTRY = "LOG_ENTRY_SEQ";
	public static final String SEQ_CREDITO = "CREDITO_SEQ";
	public static final String SEQ_TRAFICO_LOG_ENTRY = "TRAFICO_LOG_ENTRY_SEQ";
	public static final String SEQ_TIPO_CREDITO="TIPO_CREDITO_SEQ";
	public static final String SEQ_EXPEDIENTE_UPDATE = "EXPEDIENTE_UPDATE_SEQ";
	public static final String SEQ_CASILLAS_UPDATE = "CASILLAS_UPDATE_SEQ";

	//IDs de los Beans de Spring
	public static final String ID_BEAN_PROPIEDADES = "properties";
	public static final String ID_BEAN_VALIDACIONES = "validaciones";
	public static final String ID_BEAN_REQUEST_CONTEXT_FACTORY = "RequestContextFactory";
	public static final String ID_BEAN_PRESENTACION = "IPresentacionTramitesBL";
	public static final String ID_BEAN_TRAFICO_TRAMITE = "ITraficoTramiteBL";
	public static final String ID_BEAN_GEST = "IGest";

	// Pruebas del transactional
	public static final int pruebaTime = 5;

	//Acciones sobre contrato
	public static final int ACC_ELIMINAR  = 1;
	public static final int ACC_HABILITAR  = 2;
	public static final int ACC_DESHABILITAR  = 3;

	//Estados de contrato
	public static final int ESTADO_CONTRATO_HABILITADO = 1;
	public static final int ESTADO_CONTRATO_DESHABILITADO=2;
	public static final int ESTADO_CONTRATO_ELIMINADO=3;

	//Estados de usuario
	public static final int ESTADO_USUARIO_ELIMINADO=3;
	public static final int ESTADO_USUARIO_HABILITADO=1;
	public static final int ESTADO_USUARIO_DESHABILITADO=2;

	//Tipos de tramites
	public static final int TIPO_TRAMITE_TRAFICO_MATRICULACION = 1;
	public static final int TIPO_TRAMITE_TRAFICO_TRANSMISION = 2;
	public static final int TIPO_TRAMITE_TRAFICO_INFORMES = 3;
	public static final int TIPO_TRAMITE_TRAFICO_BAJAS = 4;
	public static final int TIPO_TRAMITE_TRAFICO_TASAS = 5;

	//Nombre del tramite (usado para especificar el tramite al usuario en la validacion de errores)
	public static final String TRAMITE_MATRICULACION = "MATRICULACION";
	public static final String TRAMITE_TRANSMISION = "TRANSMISION";
	public static final String TRAMITE_SOLICITUD = "SOLICITUD DE DATOS";
	public static final String TRAMITE_BAJA = "BAJA";
	
	//Valores de tipos de tramites
	public static final String VALOR_TRAMITE_TRAFICO_PDF = "PDF";
	public static final String VALOR_TRAMITE_TRAFICO_MATRICULACION = "040";
	public static final String VALOR_TRAMITE_TRAFICO_TRANSMISION = "030";
	//620
	public static final String VALOR_TRAMITE_TRAFICO_TRANSMISION_CET = "050";
	public static final String VALOR_TRAMITE_TRAFICO_INFORMES = "010";
	public static final String VALOR_TRAMITE_TRAFICO_BAJAS = "020";
	public static final String VALOR_TRAMITE_TRAFICO_TASAS = "";
	public static final String VALOR_TRAMITE_AEAT576 = "576";
	public static final String VALOR_TRAMITE_AEAT06 = "006";
	public static final String VALOR_TRAMITE_AEAT05 = "005";
	public static final String VALOR_TRAMITE_AEATLOG576 = "LOG576"; 
	public static final String VALOR_TRAMITE_NRC = "NRC";


	//Tipos de estado de tramites
	public static final int ESTADO_TRAMITE_ALTA_FICHERO = 1;
	public static final int ESTADO_TRAMITE_ALTA_ONLINE = 2;
	public static final int ESTADO_TRAMITE_ALTA_DUPLICADO = 3;
	public static final int ESTADO_PENDIENTE_PAGO_AEAT = 4;
	public static final int ESTADO_PENDIENTE_PRESENTACION_AEAT = 5;
	public static final int ESTADO_PENDIENTE_PAGO_IVTM = 6;
	public static final int ESTADO_ERROR_EN_PAGO_IVTM = 7;
	public static final int ESTADO_PENDIENTE_PRESENTACION_DGT = 8;
	public static final int ESTADO_ESPERA_CONFIRMACION_DGT = 9;
	public static final int ESTADO_ERROR_PRESENTACION_DGT = 10;
	public static final int ESTADO_FINALIZADO = 11;
	public static final int ESTADO_PENDIENTE_PAGOPRESENTACION_620 = 12;
	public static final int ESTADO_ERROR_PRESENTACION_AEAT = 13;


	//Tipos de documentos asociados a los trámites de tráfico
	public static final long TIPO_DOCUMENTO_TRAFICO_PTC = 1;
	public static final long TIPO_DOCUMENTO_TRAFICO_IVTM = 2;
	public static final long TIPO_DOCUMENTO_TRAFICO_JUSTIFICANTE_ENTRADA = 5;
	public static final long TIPO_DOCUMENTO_TRAFICO_JUSTIFICANTE_SALIDA = 6;
	public static final long TIPO_DOCUMENTO_TRAFICO_AEAT = 7;
	public static final long TIPO_DOCUMENTO_TRAFICO_PDF_TELEMATICO = 8;
	
	//Estados de traficoTramite que permiten eliminar registros
	public static final long VALOR_ORDEN_NO_ELIMINACION_TRAMITE_MATRICULACION = 6;
	public static final long VALOR_ORDEN_NO_ELIMINACION_TRAMITE_TRANSMISION = 4;
	public static final long VALOR_ORDEN_NO_ELIMINACION_TRAMITE_BAJA = 3;
	public static final long VALOR_ORDEN_NO_ELIMINACION_TRAMITE_SOLICITUD = 3;
	
	public static final long VALOR_ORDEN_NO_DESASIGNACION_TASA_MATRICULACION = 6;
	public static final long VALOR_ORDEN_NO_DESASIGNACION_TASA_TRANSMISION = 4;
	public static final long VALOR_ORDEN_NO_DESASIGNACION_TASA_BAJA = 3;
	public static final long VALOR_ORDEN_NO_DESASIGNACION_TASA_SOLICITUD = 3;
	
	//Valores de orden de estado según tipo de tramite para deshabilitar campos en JSPs
	public static final long VALOR_ORDEN_TRAMITE_MATRICULACION_AEAT = 3;
	public static final long VALOR_ORDEN_TRAMITE_MATRICULACION_IVTM = 4;
	public static final long VALOR_ORDEN_TRAMITE_MATRICULACION_DGT = 5;
	public static final long VALOR_ORDEN_TRAMITE_BAJA_FINALIZADO = 3;
	public static final long VALOR_ORDEN_TRAMITE_TRANSMISION_FINALIZADO = 4;
	
	//Valor del atributo ID del nodo a firmar en la petición XML a MATEGE
	public static final String MATEGE_NODO_FIRMAR_ID = "D0";
	public static final String MATEGE_XPATH_NODO_ESCRIBIR_FIRMA_CLIENTE = "/AFIRMA";
	public static final String MATEGE_XPATH_NODO_ESCRIBIR_FIRMA_SERVIDOR = "AFIRMA";

	
	//Gráficos. Tipo de estadística
	//Generales
	public static final int ESTADISTICA_ALTA_CONTRATOS = 1;
	public static final int ESTADISTICA_ACCESOS = 2;
	public static final int ESTADISTICA_ALTA_TRAMITES = 3;
	public static final int ESTADISTICA_TRAMITES_PRESENTADOS = 4;
//	public static final int ESTADISTICA_TRAMITES_PAGADOS = 5;
	//Tráfico
	public static final int ESTADISTICA_ACCESOS_TRAFICO = 1;
	public static final int ESTADISTICA_ALTA_TRAMITES_TRAFICO = 2;
	public static final int ESTADISTICA_TRAMITES_TRAFICO_PRESENTADOS_TELEMÁTICAMENTE = 3;
	public static final int ESTADISTICA_TRAMITES_TRAFICO_FINALIZADOS = 4;
	public static final int ESTADISTICA_CREDITOS_TRAFICO_TRAMITE = 5;
	public static final int ESTADISTICA_CREDITOS_TRAFICO_DELEGACION = 6;
	public static final int ESTADISTICA_CREDITOS_GESTORIA = 7;
	public static final int ESTADISTICA_CREDITOS_GESTORIA_TRAMITE = 8;
	
	//Gráficos. Periodo para la estadística
	public static final int ESTADISTICA_PERIODO_DIA = 0;
	public static final int ESTADISTICA_PERIODO_MES = 1;
	public static final int ESTADISTICA_PERIODO_ANIO = 2;
	public static final int ESTADISTICA_PERIODO_TOTAL = 3;
	
	//Mensaje que se muestra en la jsp de contrato cuando los créditos del espacio se comparten por todos los trámites
	public static final String MENSAJE_TODOS_TRAMITES = "TODOS";
	
	//Respuesta que da el servicio que refresca la caché.
	public static String RESPUESTA_OK = "OK";
	public static String RESPUESTA_ERROR = "ERROR";
	
        //Identificadores de aplicaciones de la DGT
	public static final long ID_APLICACION_DGT_BSTI = 1;
	public static final long ID_APLICACION_DGT_AVPO = 2;
	public static final long ID_APLICACION_DGT_GEST = 3;
	//620
	public static final long ID_APLICACION_XML_620 = 4;
	public static final String GATA_TURISMO = "A";
	public static final String GATA_TODO_TERRENO = "B";
	
	//Protocolos de comunicación
	public static String PROTOCOLO_HTTP = "http";
	public static String PROTOCOLO_HTTPS = "https";
	public static String PUERTO_DEF_HTTPS = "443";
	
	//Tipos de operaciones de los expedientes
	public static long TIPO_OPERACION_ALTA = 1;
	public static long TIPO_OPERACION_IMPORTAR_AEAT = 2;
	public static long TIPO_OPERACION_MODIFICAR = 3;
	public static long TIPO_OPERACION_ELIMINAR = 4;
	public static long TIPO_OPERACION_PRESENTAR_AEAT = 5;
	public static long TIPO_OPERACION_PRESENTAR_DGT = 6;
	public static long TIPO_OPERACION_OBTENER_MATRICULA = 7;
	
	//Tipo de fichero importación DGT
	public static String VALOR_TIPO_FICHERO_DGT = "DGT";
	public static String VALOR_TIPO_FICHERO_XML = "XML";
	public static String VALOR_TIPO_FICHERO_XML_MATE = "XML_MATE";
	public static String VALOR_TIPO_FICHERO_XML_TRANSMISION = "XML_TRANSMISION";

	//Tipos de alertas de usuario
	public static long TIPO_ALERTA_ALTA = 1;
	public static long TIPO_ALERTA_MODIFICADO = 2;
	public static long TIPO_ALERTA_HABILITADO = 3;
	public static long TIPO_ALERTA_DESHABILITADO = 4;
	public static long TIPO_ALERTA_ELIMINADO = 5;

	//Tipos de estados de alertas de usuario
	public static long TIPO_ESTADO_ALERTA_LEIDA = 1;
	public static long TIPO_ESTADO_ALERTA_NO_LEIDA = 2;
	public static long TIPO_ESTADO_ALERTA_ELIMINADA = 3;

	//ENTORNO_CODIGO Nuevo bloque de sentencias. Inicio
	public static String ENTORNO_CODIGO = "ENTORNO_CODIGO";
	public static String ENTORNO_CODIGO_CLAVE = "ENTORNO_CODIGO_CLAVE";
	public static Integer ENTORNO_CODIGO_PRE =new Integer(1);
	public static Integer ENTORNO_CODIGO_PRO =new Integer(2);
	
	
	//Constantes de Datos Sensibles
	public static String DATOS_SENSIBLES_GRUPO_ADMINISTRADOR ="ADM";
	
	
	//ENTORNO_CODIGO Nuevo bloque de sentencias. Fin
	
	
	//SINCODIG Nuevo bloque de sentencias. Inicio
	private static java.util.Map<String,String> MAPA_SINCODIG=null;
	
	public static final String CAMPO_SINCODIG = "ID_SINCODIG";
	public static final String CODIGO_ITV_NO_VALIDO_1 = "SINCODIG"; 
	
	//SINCODIG Nuevo bloque de sentencias. Fin
	
	/*
	 * 620: Para generar el XML del 620
	 */
	public static final String FORMATO_FECHA_XML = "dd/MM/yyyy";
	
	//Constante para el proceso Atem
	public static final String SOLICITUD_OK = "INFORMACION DE VEHICULO RECIBIDA";
	public static final String SESION_EXPIRADA = "sesionExpirada";
	
	public static final String ID_CONTRATO_COLEGIO = "519";
	
	//Constante para obtener el bean del contexto de Spring que tiene la Lógica de negocio de histórico de créditos
	public static final String SERVICIO_HISTORICO_CREDITO = "servicioCreditoFacturadoImpl";

	//Contanste para obtener la property con el mensaje de los justificantes profesionales.
	//public static final String JUSTIFICANTE_REPETIDO_EXCEPTION = "JUSTIFICANTE_REPETIDO_EXCEPTION";
//	public static final String JUSTIFICANTE_POR_FECHA_EXCEPTION = "JUSTIFICANTE_POR_FECHA_EXCEPTION";
//	public static final String PERMITE_SER_FORZADO = "PERMITE_SER_FORZADO";
	
	//Contanste para obtener el mensaje de los justificantes profesionales.
		public static final String JUSTIFICANTE_REPETIDO_EXCEPTION = "Por la instrucción 14/V - 107, una vez ya emitido un justificante provisional, no se podrá emitir uno nuevo con idénticos adquiriente y matrícula.";
		public static final String JUSTIFICANTE_POR_FECHA_EXCEPTION = "Por la instrucción 14/V - 107, una vez ya emitido un justificante provisional, solo se podrá emitir uno nuevo si se tiene el permiso de circulación a nombre titular del primer justificante. En dicho caso contacte con el Colegio.";
		public static final String PERMITE_SER_FORZADO = "SI";
	
	
}









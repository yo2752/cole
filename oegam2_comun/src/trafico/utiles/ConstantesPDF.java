package trafico.utiles;



import trafico.utiles.constantes.ConstantesTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;


public class ConstantesPDF extends ConstantesTrafico{
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConstantesPDF.class);
	
	public static final String ID_PREFIX = "ID_";
	
	//********************************************
	//* IDENTIFICADORES DE CAMPOS NUBE DE PUNTOS *
	//********************************************				
	/**
	 * Numero de registro. 
	 * @see #TAM_NUM_REG
	 */
	public static final String ID_NUM_REG = ID_PREFIX + "NUM_REG";
	/**
	 * Campo reservado. 
	 * @see #TAM_RESERVADO
	 */
	public static final String ID_RESERVADO = ID_PREFIX + "RESERVADO";
		
	//*******************************
	//* TAMAÑO DE VALORES DE CAMPOS	*
	//*******************************
	public static final int TAM_NUM_REG = 10;
	public static final int TAM_RESERVADO = 24;
	public static final int TAM_PRIMER_APELLIDO=26;
	public static final int TAM_PRIMER_APELLIDO_RAZON_SOCIAL=70;
	public static final int TAM_TUTELA=2;
	
	//*******************************
	//* IDENTIFICADORES DE CAMPOS	*
	//*******************************
	
	/**
	 * Persona Juridica 
	 */
	public static final String PERSONA_JURIDICA = "X";

	/**
	 * Numero de versión 
	 */
	public static final String VERSION_MATRICULACION = "V02.01";
	
	/**
	 * Versión de programa 
	 */
	public static final String PROGRAMA_MATRICULACION = "PFSEGAA";
	
	public static final String PROGRAMA_MATRICULACION_NIVE = "PFSEGA9"; // SE ELIMINA PARA MATEW

	/**
	 * Numero de versión 
	 */
	//public static final String VERSION_TRANSMISION = "V01GGJ";
	public static final String VERSION_TRANSMISION = "      ";
	
	/**
	 * Versión de programa 
	 */
	public static final String PROGRAMA_TRANSMISION = "PFSEGA5";
	
	/**
	 * Numero de versión 
	 */
	//public static final String VERSION_BAJA = "V01AEG";
	public static final String VERSION_BAJA = "V01   ";
	
	/**
	 * Versión de programa 
	 */
	public static final String PROGRAMA_BAJA = "PFSEGA3";
	
	/**
	 * Numero de versión 
	 */
	//public static final String VERSION_SOLICITUD = "V01   ";
	public static final String VERSION_SOLICITUD = "      ";
	
	/**
	 * Versión de programa 
	 */
	public static final String PROGRAMA_SOLICITUD = "PFSEGA4";
	
	public static final String VALOR_TUTELA = "SI";
	public static final String VALOR_SI = "SI";
	public static final String VALOR_NO = "NO";
	public static final String VALOR_S = "S";
	public static final String VALOR_N = "N";
	public static final String VALOR_NUEVO = "N";
	public static final String VALOR_USADO = "U";
	public static final String VALOR_E = "E";
	public static final String VALOR_CERO = "0";
	
	
	public static final String VALOR_TASA_EXENTO = "EXENTO";
	
	public static final String SEXO_MUJER = "M";
	public static final String SEXO_HEMBRA = "H";
	
	public static final String BAJA_TEMPORAL_TRANSFERENCIA = "4";
	
	public static final String ID_MATRICULA_DNI = "ID_MATRICULA_DNI_";
	
	public static final String VALOR_TIPO_MATRICULACION_ORDI = 	"ORDI";
	public static final String VALOR_TIPO_MATRICULACION_TURI = 	"TURI";
	public static final String VALOR_TIPO_MATRICULACION_REMO = 	"REMO";
	public static final String VALOR_TIPO_MATRICULACION_ESPE = 	"ESPE";
	public static final String VALOR_TIPO_MATRICULACION_CICL = 	"CICL";
	public static final String VALOR_TIPO_MATRICULACION_HIST = 	"HIST";
	
	public static final float ANGULO = 30;
	public static final float _f842 = 842;
	public static final float _f612 = 612;
	public static final float _f610 = 610;
	public static final float _f595 = 595;
	public static final float _f560 = 560;
	public static final float _f537 = 537;
	public static final float _f415 = 415;
	public static final float _f380 = 380;
	public static final float _f340 = 340;
	public static final float _f310 = 310;
	public static final float _f290 = 290;
	public static final float _f270 = 270;
	public static final float _f250 = 250;
	public static final float _f170 = 170;
	public static final float _f165 = 165;
	public static final float _f160 = 160;
	public static final float _f155 = 155;
	public static final float _f150 = 150;
	public static final float _f145 = 145;
	public static final float _f140 = 140;
	public static final float _f130 = 130;
	public static final float _f115 = 115;
	public static final float _f110 = 110;
	public static final float _f90 = 90;
	public static final float _f73 = 73;
	public static final float _f50 = 50;
	public static final float _f45 = 45;
	public static final float _f40 = 40;
	public static final float _f30 = 30;
	public static final float _f26 = 26;
	public static final float _f20 = 20;
	public static final float _f16 = 16;
	public static final float _f15 = 15;
	public static final float _f13 = 13;
	public static final float _f12 = 12;
	public static final float _f11 = 11;
	public static final float _f10 = 10;
	public static final float _f9 = 9;
	public static final float _f8 = 8;
	public static final float _f7 = 7;
	public static final float _f6 = 6;
	public static final float _f5 = 5;
	public static final float _f4 = 4;
	public static final float _f3 = 3;
	public static final float _f2 = 2;
	public static final float _f1 = 1;
	public static final float _f0 = 0;
	
	public static final float _f17_5 = 17.5f;
	
	public static final int _842 = 842;
	public static final int _595 = 595;
	public static final int _560 = 560;
	public static final int _415 = 415;
	public static final int _380 = 380;
	public static final int _340 = 340;
	public static final int _330 = 330;
	public static final int _310 = 310;
	public static final int _290 = 290;
	public static final int _270 = 270;
	public static final int _250 = 250;
	public static final int _170 = 170;
	public static final int _160 = 160;
	public static final int _150 = 150;
	public static final int _145 = 145;
	public static final int _140 = 140;
	public static final int _130 = 130;
	public static final int _115 = 115;
	public static final int _110 = 110;
	public static final int _90 = 90;
	public static final int _50 = 50;
	public static final int _45 = 45;
	public static final int _40 = 40;
	public static final int _30 = 30;
	public static final int _26 = 26;
	public static final int _25 = 25;
	public static final int _24 = 24;
	public static final int _21 = 21;
	public static final int _20 = 20;
	public static final int _16 = 16;
	public static final int _15 = 15;
	public static final int _13 = 13;
	public static final int _12 = 12;
	public static final int _11 = 11;
	public static final int _10 = 10;
	public static final int _9 = 9;
	public static final int _8 = 8;
	public static final int _7 = 7;
	public static final int _6 = 6;
	public static final int _5 = 5;
	public static final int _4 = 4;
	public static final int _3 = 3;
	public static final int _2 = 2;
	public static final int _1 = 1;
	public static final int _0 = 0;
	public static final float _0_25 = 0.25f;
	public static final String ALINEACION_DERECHA = "dcha";
	public static final String ALINEACION_CENTRO = "centro";
	public static final String ALINEACION_IZQUIERDA = "izqda";
	public static final String COURIER = "Courier";
	public static final String HELVETICA = "Helvetica";
	public static final String TIMES = "Times";
	public static final String TIMES_ROMAN = "Times-Roman";
	
	public final static String BORRADOR_PDF_417="BorradorPDF417";
	public final static String PDF_417="PDF417";
	public final static String PDF_PRESENTACION_TELEMATICA="PDFPresentacionTelematica";
	public final static String PERMISO_TEMPORAL_CIRCULACION="PermisoTemporalCirculacion";
	public final static String FICHA_TECNICA="FichaTecnica";
	public final static String MANDATO_GENERICO="MandatoGenerico";
	public final static String MANDATO_ESPECIFICO="MandatoEspecifico";
	public final static String CARTA_PAGO_IVTM="CartaPagoIVTM";
	public final static String MODELO_AEAT="ModeloAEAT";
	public final static String PEGATINAS_ETIQUETA_MATRICULA="PegatinasEtiquetaMatricula";
	public final static String LISTADO_BASTIDORES="ListadoBastidores";
	public final static String MODELO_430="Modelo430";
	public final static String _VEHICULO="_VEHICULO"; 
	public static final String _TITULAR = "_TITULAR";
	public static final String _ADQUIRIENTE = "_ADQUIRIENTE";
	public static final String _GESTOR = "_GESTOR";
	public static final String _TRANSMITENTE = "_TRANSMITENTE";
	public static final String _ARRENDATARIO = "_ARRENDATARIO" ;
	public static final String _COMPRAVENTA = "_COMPRAVENTA" ;
	public static final String _REPR_ADQUIRIENTE = "_REPR_ADQUIRIENTE" ;
	public static final String _REPR_TRANSMITENTE = "_REPR_TRANSMITENTE" ;
	public static final String _REPR_TITULAR = "_REPR_TITULAR" ;
	public static final String _CONDUCTOR_HABITUAL = "_CONDUCTOR_HABITUAL";
	public static final String _REPRESENTANTE = "_REPRESENTANTE";
	
	//Extensiones PDF
	public static final String EXTENSION_PDF = ".pdf";
	public static final String EXTENSION_TXT = ".txt";
	public static final String EXTENSION_ZIP = ".zip";
	public static final String EXTENSION_XML = ".xml";
	public static final String EXTENSION_XLS = ".xls";
	public static final String EXTENSION_XLSX = ".xlsx";
	public static final String EXTENSION_BM = ".bm";
	public static final String EXTENSION_HTML = ".html";
	
	//Rutas de las plantillas(Esto hay que pasarlo a un properties y acceder a la ruta así)
	public static final String RUTA_PLANTILLAS = "trafico.rutaSrc.plantillasPDF";
	public static final String RUTA_JUSTIFICANTES = "/temp/pdf/justificantes/";
	
	public static final String RUTA_MARCA_AGUA = "marcaAgua.jpg";
	public static final String RUTA_MARCA_AGUA_BASTIDOR_MATRICULADO = "marcaAguaBastMatriculado.png";
	public static final String RUTA_MARCA_AGUA_VEHICULO_ELECTRICO = "marcaAguaVehiculoElectrico.png";
	public static final String RUTA_MARCA_AGUA_PEQUEÑA = "marcaAguaPeque.jpg";
	public static final String RUTA_MARCA_AGUA_FIN_SERIE = "marcaFinSerieFinal2.jpg";
	// Campos utilizados por la plantilla
	public static final String TIT_PRIMER_APELLIDO = "TIT_PRIMER_APELLIDO";
	public static final String TIT_SEGUNDO_APELLIDO = "TIT_SEGUNDO_APELLIDO";
	public static final String TIT_NOMBRE = "TIT_NOMBRE";
	public static final String TIT_PRIMER_APELLIDO_ADQUIRENTE = "TIT_PRIMER_APELLIDO_ADQUIRENTE";
	public static final String TIT_SEGUNDO_APELLIDO_ADQUIRENTE = "TIT_SEGUNDO_APELLIDO_ADQUIRENTE";
	public static final String TIT_NOMBRE_ADQUIRENTE = "TIT_NOMBRE_ADQUIRENTE";
	public static final String TIT_PRIMER_APELLIDO_TITULAR = "TIT_PRIMER_APELLIDO_TITULAR";
	public static final String TIT_SEGUNDO_APELLIDO_TITULAR = "TIT_SEGUNDO_APELLIDO_TITULAR";
	public static final String TIT_NOMBRE_TITULAR = "TIT_NOMBRE_TITULAR";
	public static final String DENOMINACION = "Denominación:";
	public static final String APELLIDO1 = "Apellido _1:";
	public static final String APELLIDO2 = "Apellido 2:";
	public static final String NOMBRE = "Nombre:";
	
	// Campos utilizados por la plantilla de matriculacion Mandato
	public static final String ID_DNI_TITULAR = "ID_DNI_TITULAR";
	public static final String ID_DNI_TITULAR2 = "ID_DNI_TITULAR2";
	public static final String ID_NOMBRE_ENTIDAD = "ID_NOMBRE_ENTIDAD";
	public static final String CIF_ENTIDAD = "CIF_ENTIDAD";
	public static final String ID_PROVINCIA_ENTIDAD = "ID_PROVINCIA_ENTIDAD";
	public static final String ID_MUNICIPIO_ENTIDAD = "ID_MUNICIPIO_ENTIDAD";
	public static final String ID_DOMICILIO_ENTIDAD = "ID_DOMICILIO_ENTIDAD";
	public static final String ID_DOMICILIO_Y_MUNICIPIO_ENTIDAD = "ID_DOMICILIO_Y_MUNICIPIO_ENTIDAD";
	public static final String ID_NUM_DOMICILIO_ENTIDAD = "ID_NUM_DOMICILIO_ENTIDAD";
	public static final String ID_CP_ENTIDAD = "ID_CP_ENTIDAD";
	public static final String ID_NOMBRE_APELLIDOS_GESTOR = "ID_NOMBRE_APELLIDOS_GESTOR";
	public static final String ID_CONTRATO_GESTOR = "ID_CONTRATO_GESTOR";
	public static final String ID_NUM_COLEGIADO_GESTOR = "ID_NUM_COLEGIADO_GESTOR";
	public static final String ID_COLEGIO_GESTOR = "ID_COLEGIO_GESTOR";
	public static final String ID_MUNICIPIO_GESTOR = "ID_MUNICIPIO_GESTOR";
	public static final String ID_DOMICILIO_GESTOR = "ID_DOMICILIO_GESTOR";
	public static final String ID_NUM_DOMICILIO_GESTOR = "ID_NUM_DOMICILIO_GESTOR";
	public static final String ID_CP_GESTOR = "ID_CP_GESTOR";
	public static final String ID_ASUNTO1_MOTIVO = "ID_ASUNTO1_MOTIVO";
	public static final String ID_ASUNTO1_EXPLICACION = "ID_ASUNTO1_EXPLICACION";
	public static final String ID_ASUNTO2 = "ID_ASUNTO2";
	public static final String ID_COLEGIO = "ID_COLEGIO";
	public static final String ID_MUNICIPIO_MANDANTE = "ID_MUNICIPIO_MANDANTE";
	public static final String ID_FECHA_MANDANTE = "ID_FECHA_MANDANTE";
	public static final String ID_MUNICIPIO_MANDATARIO = "ID_MUNICIPIO_MANDATARIO";
	public static final String ID_FECHA_MANDATARIO = "ID_FECHA_MANDATARIO";
	public static final String ID_MATRICULA = "ID_MATRICULA";
	public static final String NIF_GA = "NIF_GA";
	public static final String ID_NIF_GA = "ID_NIF_GA";
	
	
	public static final String ID_DOMICILIO_GA = "ID_DOMICILIO_GA";
	public static final String ID_VIA_MANDATARIO = "ID_VIA_MANDATARIO";
	public static final String ID_VIA_GA = "ID_VIA_GA";
	public static final String ID_CP_GA = "ID_CP_GA";
	

	
	//Ruta del fichero pdf resultante del webservice de Mate, permiso temporal de circulacion
	public static final String RUTA_PERMISO_TEMPORAL_CIRCULACION = "/temp/pdf/PTC/"; 
	//Ruta para guardar la Ficha tecnica devuelta por el WS sendElectronicMATE
	public static final String RUTA_FICHA_TECNICA = "/temp/pdf/FichaTecnica/";
	public static final String RUTA_CARTA_PAGO_IVTM = "/pdf/IVTM/"; 
	public static final String RUTA_INFORME = "/temp/pdf/informe/";
	public static final String RUTA_EXCEL = "/temp/EXCELS/";
	
//	public static final String RUTA_PERMISO_TEMPORAL_CIRCULACION = b
	
	
	// Transmisión
	public static final String TIT_PRIMER_APELLIDO_TRANSMITENTE = "TIT_PRIMER_APELLIDO_TRANSMITENTE";
	public static final String TIT_SEGUNDO_APELLIDO_TRANSMITENTE = "TIT_SEGUNDO_APELLIDO_TRANSMITENTE";
	public static final String TIT_NOMBRE_TRANSMITENTE = "TIT_NOMBRE_TRANSMITENTE";
	public static final String ID_NOMBRE_REPRESENTANTE_ADQUIRIENTE = "ID_NOMBRE_REPRESENTANTE_ADQUIRIENTE";
	public static final String ID_NOMBRE_REPRESENTANTE_TRANSMITENTE = "ID_NOMBRE_REPRESENTANTE_TRANSMITENTE";
	public static final String ID_IAE_TRANSMITENTE = "ID_IAE_TRANSMITENTE";
	public static final String ID_IAE_ADQUIRIENTE = "ID_IAE_ADQUIRIENTE";
	public static final String ID_CV = "ID_CV";
	public static final String ID_CV2 = "ID_CV2";
	public static final String ID_CLASE_TRANSMISION = "ID_CLASE_TRANSMISION";
	public static final String ID_TIPO_SOLICITUD = "ID_TIPO_SOLICITUD";
	
	// Campos utilizados por la plantilla de matriculacion
	public static final String ID_NOMBRE_APELLIDOS_TITULAR = "ID_NOMBRE_APELLIDOS_TITULAR";
	public static final String ID_NOMBRE_APELLIDOS_TITULAR_MTW = "Nombre y Apellidos/Razón Social";
	public static final String ID_NOMBRE_APELLIDOS_TITULAR2 = "ID_NOMBRE_APELLIDOS_TITULAR_2";
	public static final String ID_NOMBRE_APELLIDOS_TITULAR3 = "ID_NOMBRE_APELLIDOS_TITULAR_3";
	public static final String ID_DOMICILIO_COMPLETO_TITULAR = "ID_DOMICILIO_COMPLETO_TITULAR";
	public static final String ID_DOMICILIO_COMPLETO_TITULAR2 = "ID_DOMICILIO_COMPLETO_TITULAR_2";
	public static final String ID_DOMICILIO_COMPLETO_ITULAR3 = "ID_DOMICILIO_COMPLETO_TITULAR_3";
	public static final String ID_NOMBRE_APELLIDOS_ARRENDATARIO = "ID_NOMBRE_APELLIDOS_ARRENDATARIO";
	public static final String ID_FECHA_ACTUAL = "ID_FECHA_ACTUAL";
	public static final String ID_NUM_EXPEDIENTE = "ID_NUM_EXPEDIENTE";
	public static final String ID_FECHA = "ID_FECHA";
	public static final String ID_DGT = "ID_DGT";
	public static final String ID_NUM_EXPEDIENTE_FIRMA = "ID_NUM_EXPEDIENTE_FIRMA";
	public static final String ID_FIRMA_GESTOR = "ID_FIRMA_GESTOR_POSICION";
	public static final String ID_FIRMA_COLEGIO = "ID_FIRMA_COLEGIO_POSICION";
	public static final String ID_NIF_PROFESIONAL = "ID_NIF_PROFESIONAL";
	public static final String ID_PROFESIONAL = "ID_PROFESIONAL";
	public static final String ID_IVTM = "ID_IVTM";
	public static final String ID_CET = "ID_CET";
	public static final String ID_CEMA = "ID_CEMA";
	public static final String ID_TIPO_TRANSFERENCIA = "ID_TIPO_TRANSFERENCIA";
	public static final String ID_TIPO_TRANSFERENCIA_2 = "ID_TIPO_TRANSFERENCIA_2";
	public static final String ID_STATUS_TRANSFERIBLE = "ID_STATUS_TRANSFERIBLE";
	public static final String ID_MODO_ADJUDICACION = "ID_MODO_ADJUDICACION";
	public static final String ID_FACTURA = "ID_FACTURA";
	public static final String ID_BASE_IMPONIBLE = "ID_BASE_IMPONIBLE";
	public static final String ID_PORCENTAJE_ADQUISICION = "ID_PORCENTAJE_ADQUISICION";
	public static final String ID_TIPO_INDUSTRIA = "ID_TIPO_INDUSTRIA";
	public static final String ID_TIPO_TRANSMISION = "ID_TIPO_TRANSMISION";
	public static final String ID_TIPO_TRANSMISION_1 = "CTI - Cambio de Titularidad completo";
	public static final String ID_TIPO_TRANSMISION_2 = "CTI - Finalización tras una notificación";
	public static final String ID_TIPO_TRANSMISION_3 = "CTI - Interviene Compraventa";
	public static final String ID_TIPO_TRANSMISION_4 = "NOT - Notificación de Venta";
	public static final String ID_TIPO_TRANSMISION_5 = "ENT - Entrega a Compraventa";
	public static final String ID_TRAMITABLE_TELEMATICAMENTE = "Expediente tramitable telemáticamente";
	
	// Campos de la plantillas de listado de bastidores
	public static final String ID_NUM = "ID_NUM";
	public static final String ID_NIF = "ID_NIF";
	public static final String ID_TITULAR = "ID_TITULAR";
	public static final String ID_PAG = "ID_PAG";
	public static final String ID_TOTAL_PAG = "ID_TOTAL_PAG";
	public static final String ID_TOTAL_EXPEDIENTES = "ID_TOTAL_EXPEDIENTES";
	public static final String ID_CODIGO_COLEGIADO = "ID_CODIGO_COLEGIADO";
	public static final String ID_GESTORIA = "ID_GESTORIA"; //Utilizado por plantilla de matriculación también.
	public static final String ID_NIF_GESTORIA = "ID_NIF_GESTORIA"; 
	public static final String ID_TRAMITE = "ID_TRAMITE";
	public static final String ID_GESTOR = "ID_GESTOR";
	public static final String ID_NIF_AD = "ID_NIF_AD";
	//Campos de listado de Matriculas nuevo
	public static final String ID_ADQUI = "ID_ADQUI";
	public static final String ID_NIF_TRANSMITENTE = "ID_NIF_TRANSMITENTE";
	public static final String ID_TRANS = "ID_TRANS";
	
	// Nuevos Campos MATEW
	
	public static final String  _TITULAR_MTW= "_TITULAR_MTW";
	public static final String  _ARRENDATARIO_MTW = "_ARRENDATARIO_MTW";
	public static final String  _CONDUCTOR_HABITUAL_MTW = "_CONDUCTOR_HABITUAL_MTW";
	public static final String  _REPRESENTANTE_MTW = "_REPRESENTANTE_MTW";
	
	
	
	// Campos de la plantillas de baja y transmision
	public static final String ID_ADQUIRIENTE = "ID_ADQUIRIENTE";
	public static final String ID_TRANSMITENTE = "ID_TRANSMITENTE";
	public static final String ID_POSEEDOR = "ID_POSEEDOR";
	public static final String ID_TITULO = "ID_TITULO";
	public static final String ID_GEST = "ID_GEST";
	public static final String ID_ERROR_GEST = "ID_ERROR_GEST";
	public static final String TIT_GEST = "TIT_GEST";
	// Valores
	public static final String VALOR_SERVICIO_AGRICOLA = "B06";
	public static final String VALOR_TITULO_BAJA = 			"BAJA";
	public static final String VALOR_TITULO_BAJA_4 = 		"NOTIFICACIÓN DE TRANSMISIÓN";
	public static final String VALOR_TITULO_TRANSMISION = 	"TRANSMISIÓN";
	public static final String VALOR_TITULO_TRANSMISION_2 = "NOTIFICACIÓN DE TRANSMISIÓN";
	public static final String TIPO_TRANSMISION_1 = 		"1";
	public static final String TIPO_TRANSMISION_2 = 		"2";
	public static final String VALOR_GEST = 				"GEST: ";
	public static final String VALOR_GEST_OK = 				"OK";
	public static final String VALOR_GEST_ERROR = 			"ERROR";
	public static final String VALOR_GEST_REVISAR = 		"REVISAR";
	public static final String VALOR_TIT_GEST = 			"RESULTADO VALIDACIÓN GEST";
	public static final String VALOR_TIPO_GEST = 			"TIPO";
	public static final String VALOR_REG_GEST = 			"NO.REGISTRO";
	public static final String VALOR_FECHA_GEST = 			"FECHA";
	public static final String VALOR_FINAN_GEST = 			"FINANCIERA Y DOMICILIO";
	public static final String VALOR_LIN1_GEST = 			"--------";
	public static final String VALOR_LIN2_GEST = 			"-----------";
	public static final String VALOR_LIN3_GEST = 			"----------";
	public static final String VALOR_LIN4_GEST = 			"-------------------------------";
	public static final String VALOR_CONCEPTO_GEST = 		"CONCEPTO";
	public static final String VALOR_EXPED_GEST = 			"EXPEDIENTE";
	public static final String VALOR_FEMB_GEST = 			"FECHA";
	public static final String VALOR_FMATERI_GEST = 		"F. MATERI";
	public static final String VALOR_AUTORIDAD_GEST = 		"AUTORIDAD";
	public static final String ID_SINCODIG =         		"ID_SINCODIG";
	public static final String ID_RESULTADO_TELEMATICO = 	"ID_RESULTADO_TELEMATICO";
	public static final String ID_EXENCION = 	"ID_EXENCION";
	public static final String ID_ERRORES_MATE = 	"ID_ERRORES_MATE";
	public static final String VALOR_TIPO_MATRICULACION_CICLOMOTOR	= 	"Ciclomotor";
	public static final String VALOR_TIPO_MATRICULACION_REMOLQUE	= 	"Remolque";
	public static final String VALOR_TIPO_MATRICULACION_ESPECIAL 	= 	"Especial";
	public static final String VALOR_TIPO_MATRICULACION_ORDINARIA 	= 	"Ordinaria";
	public static final String VALOR_PROVINCIA_MADRID 	= "MADRID";
	public static final String VALOR_DIRECCION_COLEGIO 	= "C\\ JACOMETREZO";
	public static final String VALOR_NUMERO_DIRECCION_COLEGIO 	= "3";
	public static final String VALOR_CODIGO_POSTAL_COLEGIO 	= "28013";

	//Campos plantilla Certificado Revision Colegial
	public static final String NUM_COLEGIADO = "Numero Colegiado";
	public static final String NUM_EXPEDIENTE = "Numero Expediente";
	public static final String DOI_TITULAR = "Doi titular";
	public static final String BASTIDOR_MATRICULA = "bastidorMatricula";
	public static final String JEFATURA = "Jefatura";
	public static final String ESTACION_ITV = "estacionItv";
	public static final String KILOMETRAJE = "kilometraje";
	public static final String PAIS_MATRICULA_PREVIA = "paisMatrPrev";
	public static final String VALIDACION_ESTACION_ITV = "validacionEstacionItv";
	public static final String VALIDACION_KILOMETRAJE = "validacionKilometraje";
	public static final String VALIDACION_PAIS_MATRICULA_PREVIA = "validacionPaisMatrPrev";
	public static final String TARJETA_TIPO_A1 = "Check Tarjeta tipo A1";
	public static final String IEDMT1 = "Check IEDMT1";
	public static final String IVTM1 = "Check IVTM1";
	public static final String PROPIEDAD_FACTURA1 = "Check Propiedad Factura1";
	public static final String IVA1 = "Check Iva1";
	public static final String ACREDITAR_CENSO1 = "Check Acreditar Censo1";
	public static final String SERVICIO_VEHICULO1 = "Check Servicio Vehiculo1";
	public static final String CEMA1 = "Check Cema1";
	public static final String NO_CERTIF_TRANSPORTE1 = "Check No Certificado Transporte1";
	public static final String TARJETA_TIPO_A2 = "Check Tarjeta tipo A2";
	public static final String IEDMT2 = "Check IEDMT2";
	public static final String IVTM2 = "Check IVTM2";
	public static final String PROPIEDAD_FACTURA2 = "Check Propiedad Factura2";
	public static final String SERVICIO_VEHICULO2 = "Check Servicio Vehiculo2";
	public static final String CEMA2 = "Check Cema2";
	public static final String DUA2 = "Check Dua2";
	public static final String NO_CERTIF_TRANSPORTE2 = "Check No Certificado Transporte2";
	public static final String DOC_ORIGINAL3 = "Check Doc Original3";
	public static final String DOC_ORIGINALII3 = "Check Doc Original parteII3";
	public static final String PLACA_VERDE3 = "Check Placa Verde3";
	public static final String TARJETA_TIPO_A3 = "Check Tarjeta tipo A3";
	public static final String EIDTM3 = "Check Iedmt3";
	public static final String IVTM3 = "Check Ivtm3";
	public static final String ACREDITAR_PROPIEDAD3 = "Check Acreditar Propiedad3";
	public static final String TRADUCCION3 = "Check Traduccion3";
	public static final String ITP3 = "Check Itp3";
	public static final String IAE3 = "Check IAE3";
	public static final String SERVICIO_VEHICULO3 = "Check Acreditar Servicio Vehiculo3";
	public static final String CEMA3 = "Check Cema3";
	public static final String NO_CERTIF_TRANSPORTE3 = "Check No Certificado Transporte3";
	public static final String DOC_ORIGINAL4 = "Check Doc Original4";
	public static final String DOC_ORIGINALII4 = "Check Doc Original parteII4";
	public static final String TARJETA_TIPO_A4 = "Check Tarjeta tipo A4";
	public static final String EIDTM4 = "Check Iedmt4";
	public static final String IVTM4 = "Check Ivtm4";
	public static final String ACREDITAR_PROPIEDAD4 = "Check Acreditar Propiedad4";
	public static final String TRADUCCION4 = "Check Traduccion4";
	public static final String ITP4 = "Check Itp4";
	public static final String IAE4 = "Check IAE4";
	public static final String SERVICIO_VEHICULO4 = "Check Acreditar Servicio Vehiculo4";
	public static final String CEMA4 = "Check Cema4";
	public static final String DUA4 = "Check Dua4";
	public static final String NO_CERTIF_TRANSPORTE4 = "Check No Certificado Transporte4";
	//CONSTANTES CERTIFICADO INICIAL
	public static final String TITULO = "Titulo";
	public static final String EXP_BASTIDOR = "ExpBasti";
	public static final String TARJETA_A = "Tarjeta_A";
	public static final String IEDMT = "IEDMT";
	public static final String IVTM = "IVTM";
	public static final String ACREDITACION_PROPIEDAD = "ACREDITACION_PROPIEDAD";
	public static final String JUSTIFICANTE_IVA = "JUSTIFICANTE_IVA";
	public static final String CENSO = "CENSO";
	public static final String SERVICIO_VEHICULO = "SERVICIO_VEHICULO";
	public static final String CEMA = "CEMA";
	public static final String NO_CERTIF_TRANSPORTE = "NO_CERTIF_TRANSPORTE";
	public static final String DOCUMENTO_ORIGINAL_1 = "DOCUMENTO_ORIGINAL_1";
	public static final String DOCUMENTO_ORIGINAL_2 = "DOCUMENTO_ORIGINAL_2";
	public static final String PLACA_VERDE = "PLACA_VERDE";
	public static final String TRADUCCION_CONTRATO = "TRADUCCION_CONTRATO";
	public static final String ITP = "ITP";
	public static final String FACTURA = "FACTURA";
	public static final String DUA = "DUA";
	public static final String KILOMETR = "KILOMETR";
	public static final String EST_ITV = "EST_ITV";
	public static final String PAIS_PREVIA = "PAIS_PREVIA";
	public static final String KILOMETROS = "KILOMETROS";
	public static final String ESTA_ITV = "ESTA_ITV";
	public static final String PAIS = "PAIS";
	
	//Constantes para la generación de etiquetas
	
	public static final int _100 = 100;

	public static final float _11_5F = 11.5f;

	public static final float _0_5F = 0.5f;
	public static final float _19_6F = 19.6f;
	public static final float _18_8F = 18.8f;
	public static final float _18_35F = 18.35f;
	public static final float _2_8F = 2.8f;

	public static final double _25_4 = 25.4;

	public static final int _72 = 72;

	
	//Preferencias por defecto
	public static final long COPIAS_POR_DEFECTO = 3;
	public static final float MARGEN_SUP_POR_DEFECTO = 12;
	public static final float MARGEN_INF_POR_DEFECTO = 12;
	public static final float MARGEN_DER_POR_DEFECTO = 17.5f;
	public static final float MARGEN_IZQ_POR_DEFECTO = 17.5f;
	public static final float ESPACIO_HOR_POR_DEFECTO = 0;
	public static final float ESPACIO_VER_POR_DEFECTO = 0;
	public static final long NUM_ETIQ_FILA_POR_DEFECTO = 5;
	public static final long NUM_ETIQ_COLUMNA_POR_DEFECTO = 21;
	public static final long PRIMER_POS_FILA_POR_DEFECTO = 1;
	public static final long PRIMER_POS_COL_POR_DEFECTO = 1;
	
	// Constantes que se utilizan para el calculo de las posiciones en la impresion de etiquetas
	public static final float PTO_TO_MM = (float) (_25_4 / _72); // Conversion de puntos a milimetros
	public static final float MM_TO_PP = (float) (_72 / _25_4); 	// Conversion de milimetros a puntos
	public static final float ANCHO_ETIQ = 35; 				// Anchura de las etiquetas
	public static final float ALTO_ETIQ = 13; 					// Altura de las etiquetas
	public static final float ANCHO_BASE_BARRA = 0.5f; 		// Ancho minimo de las barras del codigo de barras
	public static final float ALTO_BARRA = 7.3f; 				// Altura de las barras del código de barras
	public static final float POSICION_X_BARCODE = 4.6f; 		// Posicion X del código de barras
	public static final float POSICION_Y_BARCODE = 8.7f; 		// Posicion Y del código de barras. pos_Y + altura (1.6 + 7.3)
	public static final float POSICION_X_MATRICULA = (POSICION_X_BARCODE - _2_8F); 	// Posicion X de la matricula
	public static final float POSICION_X_BASTIDOR = (_19_6F - POSICION_X_BARCODE); 	// Posicion X del bastidor
	public static final float POSICION_Y_TEXTO = (_11_5F - POSICION_Y_BARCODE); // Posicion Y del texto
	
	public static final int NUMERO_CARACTERES_BASTIDOR_PEGATINA = 6;
	//public static final int NUMERO_CARACTERES_BASTIDOR_PEGATINA = 4;


	// Tamaño de la fuente de la matricula
	public static final float TAM_FUENTE = 3.1f;
	
	//Tipos Matrícula
	public static final String TIPO_MATRICULA_ORDINARIA 	= 	"ORDI";
	public static final String TIPO_MATRICULA_TURISTICA 	= 	"TURI";
	public static final String TIPO_MATRICULA_REMOLQUE	 	= 	"REMO";
	public static final String TIPO_MATRICULA_ESPECIAL	 	= 	"ESPE";
	public static final String TIPO_MATRICULA_CICLOMOTOR 	= 	"CICL";
	public static final String TIPO_MATRICULA_HISTORICO 	= 	"HIST";

	public static final String ALIAS_COLEGIO = "trafico.claves.colegio.alias";
	public static final String PASSWORD_COLEGIO = "trafico.claves.colegio.password";

	
	//AVPO
	public static final int NUMERO_LINEAS_POR_PAGINA = 46;

	
	// ANUNTIS
	public static final String PDF_ANUNTIS_MATRICULA_TIT = "MATRICULA";
	
	public static final String PDF_ANUNTIS_BASTIDOR = "BASTIDOR_VEHIC";
	public static final String PDF_ANUNTIS_MATRICULA = "MATRICULA_VEHIC";
	public static final String PDF_ANUNTIS_PRIMERA_MATRICULACION = "FECHA_PRIMER_MAT_VEHIC";
	public static final String PDF_ANUNTIS_MATRICULACION = "FECHA_MAT_VEHIC";
	public static final String PDF_ANUNTIS_MARCA = "MARCA_VEHIC";
	public static final String PDF_ANUNTIS_MODELO = "MODELO_VEHIC";
	public static final String PDF_ANUNTIS_PROCEDENCIA = "PROCEDENCIA_VEHIC";
	public static final String PDF_ANUNTIS_PROPULSION = "PROPULSION_VEHIC";
	public static final String PDF_ANUNTIS_PLAZAS = "PLAZAS_VEHIC";
	public static final String PDF_ANUNTIS_CVF = "CVF_VEHIC";
	public static final String PDF_ANUNTIS_CILINDRADA = "CILINDRADA_VEHIC";
	public static final String PDF_ANUNTIS_TIPO_VARIANTE_VERSION = "TIPOVARIANTEVERSION_VEHIC";
	public static final String PDF_ANUNTIS_POTENCIA_NETA_MAXIMA = "POTENCIA_MAXIMA_VEHIC";
	public static final String PDF_ANUNTIS_MASA_CIRCULACION = "MASA_CIRCULACION_VEHIC";
	public static final String PDF_ANUNTIS_MMA = "MMA_VEHIC";
	public static final String PDF_ANUNTIS_CO2 = "CO2_VEHIC";
	public static final String PDF_ANUNTIS_PLAZA_PIE = "PLAZAS_PIE_VEHIC";
	
	public static final String PDF_ANUNTIS_BASTIDOR_LOC = "BASTIDOR_LOC";
	public static final String PDF_ANUNTIS_MATRICULA_LOC = "MMATRICULA_LOC";
	public static final String PDF_ANUNTIS_PROVINCIA = "PROVINCIA_LOC";
	public static final String PDF_ANUNTIS_NUM_TRANSFERENCIAS = "NUMERO_TRANSFERENCIAS_LOC";

	public static final String PDF_ANUNTIS_CONCEPTO_ITV = "CONCEPTO_ITV";
	public static final String PDF_ANUNTIS_FECHA_ITV = "FECHA_ITV";
	public static final String PDF_ANUNTIS_FECHA_CADUC_ITV = "FECHA_CADUC_ITV";
	public static final String PDF_ANUNTIS_ESTACION_ITV = "ESTACION_ITV";
	public static final String PDF_ANUNTIS_PROVINCIA_ITV = "PROVINCIA_ITV";
	public static final String PDF_ANUNTIS_MOTIVO_ITV = "MOTIVO_ITV";
	public static final String PDF_ANUNTIS_DEFECTOS_ITV = "DEFECTOS_ITV";

	public static final String PDF_ANUNTIS_INCIDENCIAS = "INCIDENCIAS_VEHICULO";
	public static final String PDF_ANUNTIS_TIMESTAMP = "TIMESTAMP";
	
	

	// RECONOCIMIENTOS MEDICOS
	public static final String PDF_RECOMED_NOMBRE_PROFESIONAL = "NOMBRE_PROFESIONAL";
	public static final String PDF_RECOMED_FECHA_CITA = "FECHA_CITA";
	public static final String PDF_RECOMED_HORA_CITA = "HORA_CITA";
	
	public static final String PDF_RECOMED_NIF_CLIENTE = "NIF_CLIENTE";
	public static final String PDF_RECOMED_NOMBRE_CLIENTE = "NOMBRE_CLIENTE";
	public static final String PDF_RECOMED_APELLIDO1_CLIENTE = "APELLIDO1_CLIENTE";
	public static final String PDF_RECOMED_APELLIDO2_CLIENTE = "APELLIDO2_CLIENTE";
	public static final String PDF_RECOMED_NACIMIENTO_CLIENTE = "NACIMIENTO_CLIENTE";
	public static final String PDF_RECOMED_CADUCIDAD_CARNET_CLIENTE = "CADUCIDAD_CARNET_CLIENTE";
	public static final String PDF_RECOMED_TELEFONO_CLIENTE = "TELEFONO_CLIENTE";
	public static final String PDF_RECOMED_DIRECCION_CLIENTE = "DIRECCION_CLIENTE";
	
	//PERMISO PARA IMPRIMIR PDF 417 (NUBE DE PUNTOS)
	public static final String NO_SE_PUEDE_IMPRIMIR_LA_MANCHA_PDF_PORQUE_NO_TIENE_PERMISO_PARA_ESTA_ACCION = "No se puede imprimir la mancha Pdf  por que no tiene permiso para esta acción";
	
	//Nombre de la property para indicar si se completa con 0 a la izquierda los campos autonomía eléctrica y consumo.
	public static final String PROPERTY_COMPLETA_CEROS_ELECTRICOS = "matw.completa0electricos";

	public static final Object VALOR_CIERTO_PROPERTY_COMPLETA_CEROS_ELECTRICOS = "1";
	
	public static final String ID_NOMBRE_GESTOR = "ID_NOMBRE";
	public static final String ID_CODIGO = "ID_CODIGO";
	public static final String ID_ENTIDAD = "ID_ENTIDAD";
	public static final String ID_NUMERO = "ID_NUMERO";
	public static final String ID_LUGAR = "ID_LUGAR";
	public static final String ID_DIA = "ID_DIA";
	public static final String ID_MES = "ID_MES";
	public static final String ID_ANIO = "ID_ANIO";
	
	public static String getSchema(){
		
		//Si no se ha especificado el key store de las claves públicas se coje el de por defecto
		String schemaDecodificado="";
		try {
			ILoggerOegam log = LoggerOegam.getLogger(ConstantesPDF.class);
		}  catch (Throwable e) {
			log.error(e);
		} 
		return schemaDecodificado;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
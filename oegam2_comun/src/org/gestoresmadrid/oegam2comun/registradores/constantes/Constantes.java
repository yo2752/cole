package org.gestoresmadrid.oegam2comun.registradores.constantes;

public final class Constantes {

	public static final String EXFISCALID = "exFiscalId";
	public static final String POFISCALID = "poFiscalId";
	public static final String AGFISCALID = "agFiscalId";
	public static final String AYFISCALID = "ayFiscalId";
	
	public static final String MENSAJE_FALTA_ID_PARAM = "No se ha recibido el siguiente parametro requerido: ";
	public static final String MENSAJE_ERROR_GENERAL = "Ha ocurrido un error interno, si el problema continua, pongase en contacto con el administrador. ";
	public static final String ERROR = "ERROR";

	public static final String MENSAJE_NOT_FOUND_CONTRATO_FINANCIACION = "No se ha encontrado el contrato de financiación solicitado. ";
	public static final String MENSAJE_NOT_FOUND_CONTRATO_RENTING = "No se ha encontrado el contrato de Renting solicitado. ";
	
	public static final String HEADER_CONTENT_TYPE = "Content-Type";

	public static final String FORMAT_TIMESTAMP = "dd/MM/yyyy HH:mm";
	
	public static final String ID_FIRMA_SESION = "CMSSignatureWithOutTimeStamp";
	public static final String ID_DOSSIER_NUMBER = "dossierNumberID";
	public static final String ID_XML_SIGNED = "xmlSignedID";
	public static final String ID_CONTRACT_TYPE = "contractTypeID";
	public static final String ID_SIGNABLE_ELEM = "signableElement";
	public static final String ID_DOCUMENT_TYPE = "documentType";
	
	 // Cachés ---------------------
	public static final String CACHE_CCOUNTRY_CODE = "CCOUNTRY";
	public static final String CACHE_CCOUNTRY_DESC = "Cormpe country";
	public static final String CACHE_CSTREET_TYPE_CODE = "CSTREET_TYPE";
	public static final String CACHE_CSTREET_TYPE_DESC = "Cormpe street type";
	public static final String CACHE_FINANCIAL_BACKER_CODE = "FINANCIAL_BACKER";
	public static final String CACHE_FINANCIAL_BACKER_DESC = "Financial backer";
	public static final String CACHE_PROPERTY_GROUP_CODE = "PROPERTY_GROUP";
	public static final String CACHE_PROPERTY_GROUP_DESC = "Property group";
	public static final String CACHE_REGISTRY_OFFICE_CODE = "REGISTRY_OFFICE";
	public static final String CACHE_REGISTRY_OFFICE_DESC = "Registry office";
	
	public static final String RUTA_CLASES_JAXB = "oegam.sea.rbm.clases";
	//public static final String RUTA_XSD_FINANCING_DOSSIER = "/main/resources/FinancingDossier.xsd";
	public static final String RUTA_XSD_CONTRATO_FINANCIACION = "/oegam/sea/rbm/importExport/FinancingDossier.xsd";
	public static final String RUTA_XSD_LEASING_RENTING_DOSSIER = "/oegam/sea/rbm/importExport/LeasingRentingDossier.xsd";

	// XMLs
	public static final String XML_TAG_CABECERA = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	public static final String XML_TAG_FIRMA_DATOS_MENSAJE = "<Firma_Datos_Mensaje>";
	public static final String XML_TAG_FIN_FIRMA_DATOS_MENSAJE = "</Firma_Datos_Mensaje>";
	public static final String XML_TAG_FIRMA = "<Firma>";
	public static final String XML_TAG_FIN_FIRMA = "</Firma>";
	public static final String XML_TAG_TIMESTAMP = "<TimeStamp>";
	public static final String XML_TAG_FIN_TIMESTAMP = "</TimeStamp>";
	public static final String XML_TAG_FIN_CORPME = "</CORPME-eDoc>";
	public static final String XML_TAG_INI_CORPME = "<CORPME-eDoc";

	// Config
	public static final String PROPERTIES_SEARBM = "oegamSeaRbm";
	public static final String FICHERO_PROVINCIAS = "provincias_ine";
	public static final String SAVEKEEPIG_XML_ORIGIN = "safekeeping.xml.origin";
	public static final String SAVEKEEPIG_XML_ORIGIN_PATH = "safekeeping.xml.origin.path";
	public static final String SAVEKEEPIG_XML_SIGNED = "safekeeping.xml.signed";
	public static final String SAVEKEEPIG_XML_SIGNED_PATH = "safekeeping.xml.signed.path";
	public static final String SAVEKEEPIG_RECEIVED = "safekeeping.received";
	public static final String SAVEKEEPIG_RECEIVED_PATH = "safekeeping.received.path";
	public static final String SIGN_METHOD_SIGNBYSERVER = "sign.method.signbyserver";
	public static final String SIGN_METHOD_APPLET = "sign.method.applet";

	public static final String SESSION_KEY_GESTOR = "GestorSession";
	public static final String SESSION_KEY_CERTIFICATE = "CertificadoSession";
	public static final String SESSION_KEY_IDPARAMS = "idParams";

	// Ficheros
	public static final String FILE_PARENT_NODE = "file.parent.node";
	public static final String FILE_NODE_FINANCING_DOSSIER = "FINANCINGDOSSIER";
	public static final String FILE_NODE_RECEIVED = "RECEIVED";
	public static final String FILE_NODE_XML = "XML_SIGNED";
	public static final String FILE_NODE_XML_ORIGIN = "XML_ORIGINAL";
	public static final String FILE_NODE_UNKNOW = "file.unknow.node";
	public static final String FILE_DATE_FORMAT = "_yyyyMMddHHmm";
	public static final String FILE_XML_EXTENSION = ".xml";
	public static final String FILE_ZIP_EXTENSION = ".zip";

	// Users
	public static final String USER_FILE = "users";
	public static final String BLACK_LIST = "black.list";

	// Tipos de oficinas de registro
	public static final String TIPO_REGISTRO_MERCANTIL = "RM";
	public static final String TIPO_REGISTRO_PROPIEDAD = "RP";
	public static final String TIPO_REGISTRO_BIENES_MUEBLES = "RB";

	// ENCODINGS
	public static final String ENCODING_UTF8 = "UTF-8";

	private Constantes(){
		super();
	}

}

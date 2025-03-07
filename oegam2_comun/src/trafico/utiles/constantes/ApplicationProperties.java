package trafico.utiles.constantes;

import java.util.Locale;
import java.util.ResourceBundle;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * Interfaz de acceso a las propiedades del portal de administración de OEGAM
 * 
 * @author	TB·Solutions
 * @version	1.0.0
 * @since	11/09/2007
 */
public enum ApplicationProperties {

	/* ******************************************************** *
	 *			Enumeración de propiedades disponibles			*
	 * ******************************************************** */

	//Propiedades comunes de configuración de ASF
	ASF_URL						("security.asf.urlWebsigner"),
	ASF_COMUN_INVOKING_APP		("security.asf.application"),
	ASF_COMUN_APPLICATION_ID	("security.asf.application"),

	//Propiedades comunes de configuración de ASF
	ASF_OPERACION_VERIFICA_PKCS7("security.asf.operation.verify"),
	ASF_OPERACION_FIRMA			("security.asf.operation.firma"),
	ASF_CERTIFICADO_FIRMA		("security.asf.certicate.firma"),

	//Dirección del Acceso a la plantilla e imagenes de tráfico
	DIRECCION_PLANTILLA_TRAFICO	("pdf.templates"),

	//Dirección del Acceso al directorio de esquemas XML
	DIRECCION_ESQUEMAS_XML	("esquemas.path"),

	//URL de acceso al servicio MATEGE de la DGT
	COLEGIADOS_MATEGE_PRUEBA 	("matege.dgt.colegiados.prueba"),

	//Dirección de la carpeta temporal donde se almacenan los trámites a imprimir
	DIRECCION_CARPETA_TEMPORAL	("dir.temp.url"),
	DIRECCION_CARPETA_TEMPORAL_ADM	("dir.temp.adm.url"),

	//Propiedades para configurar el HttpClient
	HTTPCLIENT_TIMEOUT					("httpclient.timeout"),

	HTTPCLIENT_PROXY_HOST				("httpclient.proxy.host"),
	HTTPCLIENT_PROXY_PORT				("httpclient.proxy.port"),
	HTTPCLIENT_PROXY_USER				("httpclient.proxy.user"),
	HTTPCLIENT_PROXY_PASSWORD			("httpclient.proxy.password"),

	SECRET_KEY_URL						("secret.key.url"),

	KEY_STORE_CLAVE_PUBLICA_URL			("httpclient.truststore.url"),
	KEY_STORE_CLAVE_PUBLICA_PASSWORD	("httpclient.truststore.password"),

	//Propiedades para la comunicación con la AEAT
	AEAT_DIRECCION_SERVIDOR_576				("aeat.server.host.modelo576"),
	AEAT_DIRECCION_SERVIDOR_05				("aeat.server.host.modelo05"),
	AEAT_DIRECCION_SERVIDOR_06				("aeat.server.host.modelo06"),
	AEAT_DIRECCION_SERVIDOR_VALIDAR_CEM		("aeat.server.host.validarCEM"),
	AEAT_DIRECCION_SERVIDOR_VALIDAR_ANAGRAMA("aeat.server.host.validarAnagrama"),
	KEY_STORE_CLAVE_PRIVADA_URL				("aeat.keystore.url"),
	KEY_STORE_CLAVE_PRIVADA_PASSWORD		("aeat.keystore.password"),

	//Propiedades conexión aplicación DGT
	DGT_APLICACION_HOST					("dgt.aplicacion.host"),
	DGT_APLICACION_NAME					("dgt.aplicacion.name"),
	DGT_APLICACION_USER					("dgt.aplicacion.user"),
	DGT_APLICACION_PASSWORD				("dgt.aplicacion.password"),

	//Dirección de los ficheros XML para invocar a las aplicaciones de la DGT
	DGT_APLICACION_BSTI_XML_URL			("dgt.xml.bsti.url"),
	DGT_APLICACION_AVPO_XML_URL			("dgt.xml.avpo.url"),
	DGT_APLICACION_GEST_XML_URL			("dgt.xml.gest.url"),

	//Propiedades del servidor de sockets para el control de sesiones con la DGT
	DGT_APLICACION_SOCKET_HOST			("dgt.aplicacion.socket.host"),
	DGT_APLICACION_SOCKET_PORT			("dgt.aplicacion.socket.port"),

	//Propiedad que indica si el entorno es el de producción o el de pre
	INDICADOR_ENTORNO_PRODUCCION		("aplicacion.entorno.produccion"),

	//Propiedad que indica si se bloquean o no los expedientes que ya están firmados o finalizados
	INDICADOR_EXPED_MODIFICABLE			("aplicacion.expediente.modificable"),

	//Path donde se guardarán los XML
	DIRECCION_XML						("xmlStorage.url"),

	//ENTORNO_CODIGO
	ENTORNO_CODIGO						("entorno.codigo"),

	//MATEGE inicio
	WEB_SERVICE_MATEGE					("webservice.matege"),
	WEB_SERVICE_MATEGE_URL				("webservice.matege.url"),
	WEB_SERVICE_MATEGE_TIMEOUT			("webservice.matege.timeout"),
	//MATEGE fin

	//Dirección de la URL donde se refrescan las cachés
	DIRECCION_REFRESCAR_CACHE			("refrescarCache.url");

	/* *********************************************************** */

	//Nombre del fichero de propiedades
	private final String FICHERO_PROPIEDADES = "propertyFiles/application";

	private String propertyName;
	private ResourceBundle properties;
	private  ILoggerOegam log = LoggerOegam.getLogger(ApplicationProperties.class);

	/**
	 * Constuctor público de la enumeración
	 * @param accionValue
	 */
	ApplicationProperties(String propertyName) {
		//Cargamos el resource bundle si se necesita...
		if (null == properties) {
			log.info("Carga inicial estatica de propiedades");
			properties = ResourceBundle.getBundle(FICHERO_PROPIEDADES, Locale.getDefault());
		}
		this.propertyName = propertyName;
	}

	public int toInt(){
		return Integer.parseInt(toString());
	}

	public boolean toBoolean(){
		return Boolean.parseBoolean(toString());
	}

}
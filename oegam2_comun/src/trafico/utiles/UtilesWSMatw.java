package trafico.utiles;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import trafico.utiles.constantes.ConstantesSystemProperties;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class UtilesWSMatw {

	private static final String NODO_LECTURA = "NodoLecturaMatwElectronica";
	private static final String CARGANDO_ALMACENES_DE_CERTIFICADOS_DE_TRAFICO = "cargando almacenes de certificados de trafico";
	private static final String RUTA_XML_LECTURA_MATW = "ruta_xml_lectura_matw";
	private static final String TAG_DATOS_FIRMADOS_INICIO = "</Datos_Firmados>";
	private static final String TAG_DATOS_FIRMADOS_FIN = "</Datos_Firmados>";
	private static final String TAG_FIRMA_INICIO = "<Firma>";
	private static final String TAG_FIRMA_FIN = "</Firma>";
	private static final String TAG_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private static String javax_net_ssl_trustStore;
	private static String javax_net_ssl_trustStorePassword;
	private static String javax_net_sslkeyStore;
	private static String javax_net_ssl_keyStorePassword;

	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilesWSMatw.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;

	public UtilesWSMatw() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	// Establece las propiedades de sistema para que utilice los almacenes
	// que contienen el certificasdo y la clave pública del registro:
	public void cargarAlmacenesTrafico() throws OegamExcepcion {

		log.debug(CARGANDO_ALMACENES_DE_CERTIFICADOS_DE_TRAFICO); 
		javax_net_ssl_trustStore =  gestorPropiedades.valorPropertie(ConstantesSystemProperties.TRAFICO_TRUSTSTORE);
		javax_net_ssl_trustStorePassword =  gestorPropiedades.valorPropertie(ConstantesSystemProperties.TRAFICO_PASSTRUSTSTORE);
		javax_net_sslkeyStore =  gestorPropiedades.valorPropertie(ConstantesSystemProperties.TRAFICO_KEYSTORE);
		javax_net_ssl_keyStorePassword = gestorPropiedades.valorPropertie(ConstantesSystemProperties.TRAFICO_PASS_KEYSTORE);

		System.setProperty(ConstantesSystemProperties.JAVAX_NET_SSL_TRUSTSTORE, javax_net_ssl_trustStore);
		log.debug(System.getProperty(ConstantesSystemProperties.JAVAX_NET_SSL_TRUSTSTORE));
		System.setProperty(ConstantesSystemProperties.JAVAX_NET_SSL_TRUSTSTOREPASSWORD, javax_net_ssl_trustStorePassword);
		log.debug(System.getProperty(ConstantesSystemProperties.JAVAX_NET_SSL_TRUSTSTOREPASSWORD));
		System.setProperty(ConstantesSystemProperties.JAVAX_NET_SSL_KEYSTORE, javax_net_sslkeyStore);
		log.debug(System.getProperty(ConstantesSystemProperties.JAVAX_NET_SSL_KEYSTORE));
		System.setProperty(ConstantesSystemProperties.JAVAX_NET_SSL_KEYSTOREPASSWORD,javax_net_ssl_keyStorePassword);
		log.debug(System.getProperty(ConstantesSystemProperties.JAVAX_NET_SSL_KEYSTOREPASSWORD));

		if (!System.getProperty(ConstantesSystemProperties.JAVAX_NET_SSL_TRUSTSTORE).equals(javax_net_ssl_trustStore)) {
			log.error(ConstantesSystemProperties.JAVAX_NET_SSL_TRUSTSTORE + "no establecido al nuevo valor");
			System.setProperty(ConstantesSystemProperties.JAVAX_NET_SSL_TRUSTSTORE, javax_net_ssl_trustStore);
		}

		if (!System.getProperty(ConstantesSystemProperties.JAVAX_NET_SSL_TRUSTSTOREPASSWORD).equals(javax_net_ssl_trustStorePassword)) {
			log.error(ConstantesSystemProperties.JAVAX_NET_SSL_TRUSTSTOREPASSWORD + "no establecido al nuevo valor");
			System.setProperty(ConstantesSystemProperties.JAVAX_NET_SSL_TRUSTSTOREPASSWORD, javax_net_ssl_trustStorePassword);
		}
		if (!System.getProperty(ConstantesSystemProperties.JAVAX_NET_SSL_KEYSTORE).equals(javax_net_sslkeyStore)) {
			log.error(ConstantesSystemProperties.JAVAX_NET_SSL_KEYSTORE + "no establecido al nuevo valor");
			System.setProperty(ConstantesSystemProperties.JAVAX_NET_SSL_KEYSTORE, javax_net_sslkeyStore);
		}

		if (!System.getProperty(ConstantesSystemProperties.JAVAX_NET_SSL_KEYSTOREPASSWORD).equals(javax_net_ssl_keyStorePassword)) {
			log.error(ConstantesSystemProperties.JAVAX_NET_SSL_KEYSTOREPASSWORD + "no establecido al nuevo valor");
			System.setProperty(ConstantesSystemProperties.JAVAX_NET_SSL_KEYSTOREPASSWORD,javax_net_ssl_keyStorePassword);
		}
		log.info("fin cargar almacenes trafico");
	}

	public String getIpNodoLectura() throws OegamExcepcion {
		return gestorPropiedades.valorPropertie("ficheros." + getNodoLecturaMatw());
	}

	/**
	 * 
	 * @return Nos da la ruta para poder ejecutar el unmarshall sobre un xml que necesita ser modificado.
	 */
	public String getRutaLecturaXMLMatwElectronica(){
		String rutaXMLMatwLectura="";
		try {
			rutaXMLMatwLectura = getIpNodoLectura() + getRutaLocalLecturaXml();
		} catch (OegamExcepcion e) {
			log.error(e);
		}
		return rutaXMLMatwLectura;
	}

	public String getNodoLecturaMatw()	throws OegamExcepcion {
		return gestorPropiedades.valorPropertie(NODO_LECTURA);
	}

	public String getRutaLocalLecturaXml() throws OegamExcepcion {
		return gestorPropiedades.valorPropertie(RUTA_XML_LECTURA_MATW);
	}
	public void restablecerAlmacenes(String trustStore,String trustStorePassword,
			String keyStore,String keyStorePassword){
	}

	public static String setFirma(String xml, String contenidoFirma){
		// Elimina la declaración 'xml' de la cadena de la firma:
		contenidoFirma = contenidoFirma.substring(TAG_XML.length());
		String[] array = new String[2];
		array[0] = xml.substring(0, xml.indexOf(TAG_DATOS_FIRMADOS_INICIO) +
				TAG_DATOS_FIRMADOS_FIN.length());
		array[1] = xml.substring(xml.indexOf(TAG_DATOS_FIRMADOS_FIN) +
				TAG_DATOS_FIRMADOS_FIN.length());
		String completo = array[0] + TAG_FIRMA_INICIO + contenidoFirma + TAG_FIRMA_FIN + array[1];
		return completo;
	}

}
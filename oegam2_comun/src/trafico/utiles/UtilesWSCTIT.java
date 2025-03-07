package trafico.utiles;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import trafico.utiles.constantes.ConstantesSystemProperties;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class UtilesWSCTIT {

	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilesWSCTIT.class);

	private String rutaXML620;
	private static String javax_net_ssl_trustStore;
	private static String javax_net_ssl_trustStorePassword;
	private static String javax_net_sslkeyStore;
	private static String javax_net_ssl_keyStorePassword;
	private static final String NODO_TRANSMISION_CTIT = "NodoTransmisionCTIT";
	private static final String RUTA_XML_CTIT = "ruta_xml_CTIT";
	private static final String RUTA_XML_620 = "ruta_xml_620";
	private static final String RUTA_XML_LECTURA_CTIT = "ruta_xml_lectura_CTIT";

	@Autowired
	private GestorPropiedades gestorPropiedades;

	public UtilesWSCTIT() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	// Establece las propiedades de sistema para que utilice los almacenes
	// que contienen el certificasdo y la clave pública del registro:
	public void cargarAlmacenesTrafico() throws OegamExcepcion {
		javax_net_ssl_trustStore =  gestorPropiedades.valorPropertie(ConstantesSystemProperties.TRAFICO_TRUSTSTORE);
		javax_net_ssl_trustStorePassword =  gestorPropiedades.valorPropertie(ConstantesSystemProperties.TRAFICO_PASSTRUSTSTORE);
		javax_net_sslkeyStore =  gestorPropiedades.valorPropertie(ConstantesSystemProperties.TRAFICO_KEYSTORE);
		javax_net_ssl_keyStorePassword = gestorPropiedades.valorPropertie(ConstantesSystemProperties.TRAFICO_PASS_KEYSTORE);

		System.setProperty(ConstantesSystemProperties.JAVAX_NET_SSL_TRUSTSTORE, javax_net_ssl_trustStore);
		System.setProperty(ConstantesSystemProperties.JAVAX_NET_SSL_TRUSTSTOREPASSWORD, javax_net_ssl_trustStorePassword);
		System.setProperty(ConstantesSystemProperties.JAVAX_NET_SSL_KEYSTORE, javax_net_sslkeyStore);
		System.setProperty(ConstantesSystemProperties.JAVAX_NET_SSL_KEYSTOREPASSWORD,javax_net_ssl_keyStorePassword);
	}

	/**
	 * 
	 * @return da la ruta de los xml que deben ser enviados.
	 */
	public String getRutaXML620(){
		try {
			rutaXML620 = getIpNodo()+  getRuta_Xml_620();
		} catch (OegamExcepcion e) {
			log.error(e);
		}
		return rutaXML620;
	}

	/**
//	 * 
//	 * @return da la ruta de los xml que deben ser enviados.
//	 */
//	public String getRutaXMLCTIT(){
//		try {
//			rutaXMLCTIT = getIpNodo()+  getRutaLocalXml();
//			log.info("ruta que devolvemos: " + rutaXMLCTIT);
//		} catch (OegamExcepcion e) {
//			log.error(e);
//		}
//		return rutaXMLCTIT;
//	}
//	/**
//	 * 
//	 * @return nos da la ruta para poder ejecutar el unmarshall sobre un xml que necesita ser modificado.
//	 */
//	public String getRutaLecturaXMLCTIT(){
//		try {
//			rutaXMLCTITLectura = getIpNodoLectura() + getRutaLocalLecturaXml();
//		} catch (OegamExcepcion e) {
//			log.error(e);
//		}
//		return rutaXMLCTITLectura;
//	}

	public String getRutaLocalXml() throws OegamExcepcion {
		return gestorPropiedades.valorPropertie(RUTA_XML_CTIT);
	}

	public String getRuta_Xml_620() throws OegamExcepcion {
		return gestorPropiedades.valorPropertie(RUTA_XML_620);
	}

	public String getIpNodo() throws OegamExcepcion {
		return gestorPropiedades.valorPropertie("ficheros." +getNodoTransmision());
	}

	public String getNodoTransmision()	throws OegamExcepcion {
		return gestorPropiedades.valorPropertie(NODO_TRANSMISION_CTIT);
	}

	public String getRutaLocalLecturaXml() throws OegamExcepcion {
		return gestorPropiedades.valorPropertie(RUTA_XML_LECTURA_CTIT);
	}

}
package org.gestoresmadrid.utilidades.components;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.springframework.stereotype.Component;

import utilidades.constantes.ConstantesUtilesProperties;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class GestorPropiedades implements Serializable{

	private static final long serialVersionUID = 5490744877019586621L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestorPropiedades.class);

	private static final String CARGANDO_ALMACENES_DE_CERTIFICADOS_DE_TRAFICO = "cargando almacenes de certificados de trafico";
	private static HashMap<String,String> mapaPropiedades;
	private static String javax_net_ssl_trustStore;
	private static String javax_net_ssl_trustStorePassword;
	private static String javax_net_sslkeyStore;
	private static String javax_net_ssl_keyStorePassword;

	public void iniciarProperties() {
		mapaPropiedades = new HashMap<>();
	}

	public void cargarPropertie(String nombre, String valor) {
		if (nombre != null && !nombre.isEmpty() && valor != null && !valor.isEmpty()) {
			mapaPropiedades.put(nombre, valor);
		}
	}

	public void cargarPreferentes() {
		try {
			Properties oegamProperties = new Properties();
			oegamProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("oegam.properties"));
			@SuppressWarnings("unchecked")
			Enumeration<String> propiedadesFichero = (Enumeration<String>) oegamProperties.propertyNames();
			while (propiedadesFichero.hasMoreElements()) {
				String propiedadFichero = (String)propiedadesFichero.nextElement();
				mapaPropiedades.put(propiedadFichero, oegamProperties.getProperty(propiedadFichero));
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cargar las properties de oegam.properties, error: ", e);
		}
	}

	public String valorPropertie(String nombre) {
		if (nombre != null && !nombre.isEmpty()) {
			return mapaPropiedades.get(nombre);
		}
		return null;
	}

	public void eliminarProperties() {
		mapaPropiedades = null;
	}

	public void cargarAlmacenesTrafico() {
		log.debug(CARGANDO_ALMACENES_DE_CERTIFICADOS_DE_TRAFICO);
		javax_net_ssl_trustStore = valorPropertie(ConstantesUtilesProperties.TRAFICO_TRUSTSTORE);
		javax_net_ssl_trustStorePassword = valorPropertie(ConstantesUtilesProperties.TRAFICO_PASSTRUSTSTORE);
		javax_net_sslkeyStore = valorPropertie(ConstantesUtilesProperties.TRAFICO_KEYSTORE);
		javax_net_ssl_keyStorePassword = valorPropertie(ConstantesUtilesProperties.TRAFICO_PASS_KEYSTORE);

		System.setProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_TRUSTSTORE, javax_net_ssl_trustStore);
		log.debug(System.getProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_TRUSTSTORE));
		System.setProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_TRUSTSTOREPASSWORD, javax_net_ssl_trustStorePassword);
		log.debug(System.getProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_TRUSTSTOREPASSWORD));
		System.setProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_KEYSTORE, javax_net_sslkeyStore);
		log.debug(System.getProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_KEYSTORE));
		System.setProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_KEYSTOREPASSWORD,javax_net_ssl_keyStorePassword);
		log.debug(System.getProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_KEYSTOREPASSWORD));

		if (!System.getProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_TRUSTSTORE).equals(javax_net_ssl_trustStore)) {
			log.error(ConstantesUtilesProperties.JAVAX_NET_SSL_TRUSTSTORE + "no establecido al nuevo valor");
			System.setProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_TRUSTSTORE, javax_net_ssl_trustStore);
		}
		if (!System.getProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_TRUSTSTOREPASSWORD).equals(javax_net_ssl_trustStorePassword)) {
			log.error(ConstantesUtilesProperties.JAVAX_NET_SSL_TRUSTSTOREPASSWORD + "no establecido al nuevo valor");
			System.setProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_TRUSTSTOREPASSWORD, javax_net_ssl_trustStorePassword);
		}
		if (!System.getProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_KEYSTORE).equals(javax_net_sslkeyStore)) {
			log.error(ConstantesUtilesProperties.JAVAX_NET_SSL_KEYSTORE + "no establecido al nuevo valor");
			System.setProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_KEYSTORE, javax_net_sslkeyStore);
		}
		if (!System.getProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_KEYSTOREPASSWORD).equals(javax_net_ssl_keyStorePassword)) {
			log.error(ConstantesUtilesProperties.JAVAX_NET_SSL_KEYSTOREPASSWORD + "no establecido al nuevo valor");
			System.setProperty(ConstantesUtilesProperties.JAVAX_NET_SSL_KEYSTOREPASSWORD,javax_net_ssl_keyStorePassword);
		}
		log.info("fin cargar almacenes trafico");
	}

	public void refrescarPropperties(HashMap<String, String> mapaPropiedadesRefrescar) {
		if (mapaPropiedadesRefrescar != null && !mapaPropiedadesRefrescar.isEmpty()) {
			eliminarProperties();
			mapaPropiedades = mapaPropiedadesRefrescar;
		}
	}

	public static String dameValorPropiedad(String nombre) {
		if (nombre != null && !nombre.isEmpty()) {
			return mapaPropiedades.get(nombre);
		}
		return null;
	}

}
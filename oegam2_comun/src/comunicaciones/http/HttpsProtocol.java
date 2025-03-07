package comunicaciones.http;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.httpclient.protocol.Protocol;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.http.ssl.AuthSSLProtocolSocketFactory;
import utilidades.web.OegamExcepcion;

/**
 * Encapsula el establecimiento de un protocolo https para comunicaciones http
 */
public class HttpsProtocol {
	
	private static final String PROTOCOLO_HTTPS = "https";
	private static final int PUERTO_HTTPS = 443;
	private static final String REF_PROP_CLAVES_PRIVADAS_UBICACION = "keystore.claves.privadas.xDefecto.ubicacion";
	private static final String REF_PROP_CLAVES_PRIVADAS_PASSWORD = "keystore.claves.privadas.xDefecto.password";
	private static final String REF_PROP_CLAVES_PUBLICAS_UBICACION = "keystore.claves.publicas.xDefecto.ubicacion";
	private static final String REF_PROP_CLAVES_PUBLICAS_PASSWORD = "keystore.claves.publicas.xDefecto.password";
	private static final String FILE = "file:";
	private Protocol protocol;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	/**
	 * El constructor por defecto carga los almacenes por defecto de OEGAM:
	 * colegioGestores.jks y clavesPublicas.jks
	 * @throws OegamExcepcion 
	 * @throws Exception 
	 */
	@SuppressWarnings("deprecation")
	public HttpsProtocol() throws OegamExcepcion, Exception{
		try{
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

			String keystoreClavesPublicas = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PUBLICAS_UBICACION);
			String passwordKeystoreClavesPublicas = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PUBLICAS_PASSWORD);
			String keystoreClavesPrivadas = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PRIVADAS_UBICACION);
			String passwordKeystoreClavesPrivadas = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PRIVADAS_PASSWORD);
			URL urlClavesPublicas = new URL(FILE + keystoreClavesPublicas);
			URL urlClavesPrivadas = new URL(FILE + keystoreClavesPrivadas);
			AuthSSLProtocolSocketFactory authSslProtocolSocketFactory = new AuthSSLProtocolSocketFactory(urlClavesPrivadas, passwordKeystoreClavesPrivadas,
					urlClavesPublicas, passwordKeystoreClavesPublicas);
			protocol = new Protocol(PROTOCOLO_HTTPS, authSslProtocolSocketFactory, PUERTO_HTTPS);  
		}catch(MalformedURLException ex){
			throw new Exception("No se ha podido crear el protocolo: " + ex.toString());
		}
	}
	
	/**
	 * Constructor que crea el protocolo con los keystores y passwords respectivos que recibe como parámetros
	 * @param ubicacionKeystoreClavesPrivadas
	 * @param passwordKeystoreClavesPrivadas
	 * @param ubicacionKeystoreClavesPublicas
	 * @param passwordKeystoreClavesPublicas
	 * @throws OegamExcepcion
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public HttpsProtocol(String ubicacionKeystoreClavesPrivadas, String passwordKeystoreClavesPrivadas,
			String ubicacionKeystoreClavesPublicas, String passwordKeystoreClavesPublicas) throws OegamExcepcion, Exception{
		try{
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
			URL urlClavesPublicas = new URL(ubicacionKeystoreClavesPublicas);
			URL urlClavesPrivadas = new URL(ubicacionKeystoreClavesPrivadas);
			AuthSSLProtocolSocketFactory authSslProtocolSocketFactory = new AuthSSLProtocolSocketFactory(urlClavesPrivadas, passwordKeystoreClavesPrivadas,
					urlClavesPublicas, passwordKeystoreClavesPublicas);
			protocol = new Protocol(PROTOCOLO_HTTPS, authSslProtocolSocketFactory, PUERTO_HTTPS);  
		}catch(Exception ex){
			throw new Exception("No se ha podido crear el protocolo: " + ex.toString());
		}
	}
	
	/**
	 * Registra el protocolo
	 * @return boolean 
	 * @throws Exception si no establece un protocolo https seguro
	 */
	public void establecerHttps() throws Exception{
		if(protocol != null){
			Protocol.registerProtocol(PROTOCOLO_HTTPS, protocol);
			if(!protocol.isSecure()){
				throw new Exception("No se ha podido establecer el protocolo https");
			}
		}else{
			throw new Exception("No se ha podido establecer el protocolo https");
		}
	}

	public Protocol getProtocol() {
		return protocol;
	}

}

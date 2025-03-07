package general.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.protocol.Protocol;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import escrituras.beans.ResultBean;
import trafico.beans.matriculacion.modelo576.RespuestaJsonRecibida;
import utilidades.http.ssl.AuthSSLProtocolSocketFactory;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;


/**
 * Métodos para la invocación de servicios a través del HttpClient
 * 
 * @author ammiguez
 *
 */
@Component
public class UtilHttpClient {
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilHttpClient.class);

	private final static String FILE = "file:";
	
	private final static String FIN_PROTOCOLO = "://";
	
	private final static String HTTPCLIENT_TIMEOUT = "httpclient.timeout";
	
	private final static String HTTPCLIENT_PROXY_HOST = "httpclient.proxy.host";
	private final static String HTTPCLIENT_PROXY_PORT = "httpclient.proxy.port";
	private final static String HTTPCLIENT_PROXY_USER = "httpclient.proxy.user";
	private final static String HTTPCLIENT_PROXY_PASSWORD = "httpclient.proxy.password";
	
	private final static String SECRET_KEY_URL = "secret.key.url";
	
	private final static String KEY_STORE_CLAVE_PUBLICA_URL = "httpclient.truststore.url";
	private final static String KEY_STORE_CLAVE_PUBLICA_PASSWORD = "httpclient.truststore.password";
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	
	public UtilHttpClient() {
	
	}
	
	/**
	 * Realizar una invocación post al host indicado en urlHost y con los parametros indicados. Si se trata de una petición
	 * usando el protocolo https (SSL) se comprobará el certificado del servidor contra el almacen de certificados de confianza
	 * usado por defecto en OEGAM.
	 *  
	 * @param urlHost
	 * @param params
	 * @return
	 * @throws Throwable
	 */
	public ResultBean executeMethod(String urlHost, LinkedHashMap<String, String> params) throws Throwable{
		
		return executeMethod(urlHost, null, null, null, null, params, null);

	}
	
	/**
	 * Realizar una invocación post al host indicado en urlHost y con los parametros indicados. Si se trata de una petición
	 * usando el protocolo https (SSL) se comprobará el certificado del servidor contra el almacen de certificados de confianza
	 * usado por defecto en OEGAM. Si se especifica un File, etonces se realizará una invocación multipart.
	 *  
	 * @param urlHost
	 * @param params
	 * @param file
	 * @return
	 * @throws Throwable
	 */
	public ResultBean executeMethod(String urlHost, LinkedHashMap<String, String> params, 
			LinkedHashMap<String, File> files) throws Throwable{
		
		return executeMethod(urlHost, null, null, null, null, params, files);

	}
	
	/**
	 * Realizar una invocación post al host indicado en urlHost y con los parametros indicados. Al indicar un 
	 * alamacen de certificados con clave privada, se usará el certificado del almacén para autenticar el cliente
	 * en el servidor.
	 * 
	 * @param urlHost
	 * @param privateKeyStore Ruta donde se encuentra al almacén de certificados de claves privadas
	 * @param passwordPrivateKS El password del almacen de certificados debe coincidir con el del certificado
	 * @param params
	 * @return
	 * @throws Throwable
	 */
	public ResultBean executeMethod(String urlHost, String privateKeyStore, String passwordPrivateKS, 
			LinkedHashMap<String, String> params) throws Throwable{
		
		return executeMethod(urlHost, null, null, privateKeyStore, passwordPrivateKS, params, null);
		
	}
	
	/**
	 * Realizar una invocación post al host indicado en urlHost y con los parametros indicados. Al indicar un 
	 * alamacen de certificados con clave privada, se usará el certificado del almacén para autenticar el cliente
	 * en el servidor.
	 * 
	 * @param urlHost
	 * @param privateKeyStore Ruta donde se encuentra al almacén de certificados de claves privadas
	 * @param passwordPrivateKS El password del almacen de certificados debe coincidir con el del certificado
	 * @param params
	 * @param files
	 * @return
	 * @throws Throwable
	 */
	public ResultBean executeMethod(String urlHost, String privateKeyStore, String passwordPrivateKS, 
			LinkedHashMap<String, String> params, LinkedHashMap<String, File> files) throws Throwable{
		
		return executeMethod(urlHost, null, null, privateKeyStore, passwordPrivateKS, params, files);
		
	}
	
	

	/**
	 * Realizar una invocación post al host indicado en urlHost y con los parametros indicados. Al indicar un 
	 * alamacen de certificados con clave privada, se usará el certificado del almacén para autenticar el cliente
	 * en el servidor. Si además se indica un almacén de certifcados de claves públicas, se comprobará el certificado 
	 * del servidor contra el almacen de certificados de confianza indicado, en vez de usar el almacén por defecto de OEGAM.
	 * 
	 * @param urlHost
	 * @param privateKeyStore Ruta donde se encuentra al almacén de certificados de claves privadas
	 * @param passwordPrivateKS El password del almacen de certificados debe coincidir con el del certificado
	 * @param params
	 * @param files
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("deprecation")
	public ResultBean executeMethod(String urlHost, String publicKeyStore, String passwordPublicKS,
			String privateKeyStore, String passwordPrivateKS, LinkedHashMap<String, String> params, 
			LinkedHashMap<String, File> files) throws Throwable{

		
		//Si el protocolo usado es el http se realiza ya la invocación al host.
		if("http".equals(obtenerProtocolo(urlHost))){
			return realizarInvocacion(urlHost, params, files);
		}
		//Si se trata de un protocolo https hay que configurar los certificados a usar
		else{
			//Obtiene la clave de cifrado almacenada en el secret.key
			String claveCifrado = obtenerClaveCifrado();
			//Ruta donde se encutra la jks de claves publicas
			String keyStoreClavePublica = publicKeyStore;
			//Contraseña del jks
			String passwordKeyStoreClavePublica = passwordPublicKS;
			
			//Ruta donde se encutra la jks de claves privadas
			URL urlKeyStoreClavePrivada = null;
			//Contraseña del jks
			String passwordKeyStoreClavePrivada = null;
			
			
			//Si se especifica un keyStore de claves privadas, se descifra la contraseña y se crea la URL
			if(privateKeyStore!=null){
		    	//Se descifra la contraseña de los keystores. 
		    	passwordKeyStoreClavePrivada = TripleDES.decrypt(claveCifrado, passwordPrivateKS, log);
		    	
		    	urlKeyStoreClavePrivada = new URL(FILE+privateKeyStore);
			}
			//En otro caso será null y no se hará autenticación del cliente

	    	
			//Si no se ha especificado el key store de las claves públicas se coje el de por defecto
	    	if(keyStoreClavePublica==null){
	    		keyStoreClavePublica = gestorPropiedades.valorPropertie(KEY_STORE_CLAVE_PUBLICA_URL);
	    		passwordKeyStoreClavePublica = TripleDES.decrypt(claveCifrado, gestorPropiedades.valorPropertie(KEY_STORE_CLAVE_PUBLICA_PASSWORD), log);
	    	}
	    	
	    	//Se crea el protocolo
			Protocol authhttps = new Protocol("https",  
			          new AuthSSLProtocolSocketFactory(
			        		  urlKeyStoreClavePrivada, passwordKeyStoreClavePrivada, 
			        		  new URL(FILE+keyStoreClavePublica),  passwordKeyStoreClavePublica), 443); 
			Protocol.registerProtocol("https", authhttps);
			
			if (urlHost.equalsIgnoreCase(gestorPropiedades.valorPropertie("trafico.576.host"))) {
				return realizarInvocacion576(urlHost, params);
			} else {
				return realizarInvocacion(urlHost, params, files);
			}
		}
	}


	/**
	 * Dada la url, se optiene el protocolo (http ó https)
	 * @param url
	 * @return
	 */
	private static String obtenerProtocolo(String url){
		int fin = url.indexOf(FIN_PROTOCOLO);
		String protocolo = url.substring(0, fin);
		return protocolo;
	}
	
	/**
	 * Obtiene la clave de cifrado almacenada en el secret.key
	 * 
	 * @return
	 * @throws Throwable
	 */
	public String obtenerClaveCifrado() throws Throwable{
    	//Se lee el fichero secret.key para obtener la clave de cifrado de contraseñas.
    	FileInputStream fis = new FileInputStream(gestorPropiedades.valorPropertie(SECRET_KEY_URL));
    	byte[] datos = new byte[fis.available()];
    	fis.read(datos);
    	//Clave de cifrado
    	String claveCifrado = new String(datos); 
    	return claveCifrado;
	}
	
	/**
	 * Obtiene una instacia estática del HttpClient
	 * 
	 * @return
	 * @throws OegamExcepcion 
	 * @throws NumberFormatException 
	 */
	public HttpClient obtenerHttpClient() throws NumberFormatException, OegamExcepcion{
		HttpClient httpclient = new HttpClient();
		
		//Configurar el PROXY
		//Si hay configurado un proxy en el fichero de propiedades, se establece esa configuración
		if(gestorPropiedades.valorPropertie(HTTPCLIENT_PROXY_HOST)!=null && gestorPropiedades.valorPropertie(HTTPCLIENT_PROXY_HOST).length()>0){
			httpclient.getHostConfiguration().setProxy(gestorPropiedades.valorPropertie(HTTPCLIENT_PROXY_HOST), Integer.parseInt(gestorPropiedades.valorPropertie(HTTPCLIENT_PROXY_PORT)));
			//Si se indica un usuario para el proxy, se configura dicho usuario.
			if(gestorPropiedades.valorPropertie(HTTPCLIENT_PROXY_USER)!=null && gestorPropiedades.valorPropertie(HTTPCLIENT_PROXY_USER).length()>0){
				httpclient.getState().setProxyCredentials(new AuthScope(gestorPropiedades.valorPropertie(HTTPCLIENT_PROXY_HOST), Integer.parseInt(gestorPropiedades.valorPropertie(HTTPCLIENT_PROXY_PORT))),
						new UsernamePasswordCredentials(gestorPropiedades.valorPropertie(HTTPCLIENT_PROXY_USER), gestorPropiedades.valorPropertie(HTTPCLIENT_PROXY_PASSWORD)));
			}
		}
		
		//Establecer el time out de conexión: (tiempo de espera mientras se realiza la conexión)
		httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(Integer.parseInt(gestorPropiedades.valorPropertie(HTTPCLIENT_TIMEOUT)));
		//Establecer el time out de socket: (tiempo de espera mientras se espera la respuesta)
		httpclient.getHttpConnectionManager().getParams().setSoTimeout(Integer.parseInt(gestorPropiedades.valorPropertie(HTTPCLIENT_TIMEOUT)));
		
		return httpclient;
	}
	
	/**
	 * Realiza la invocación y devuelve la respuesta o el error en caso de producirse
	 * 
	 * @param urlHost
	 * @param params
	 * @return
	 * @throws OegamExcepcion 
	 */
	private ResultBean realizarInvocacion(String urlHost, LinkedHashMap<String, String> params, 
			LinkedHashMap<String, File> files) throws OegamExcepcion{
		
		ResultBean result = new ResultBean();
		
		HttpClient httpclient = obtenerHttpClient();
		
		PostMethod httpPost = new PostMethod(urlHost);	
		
		try { 
			//Si no hay ningún archivo se meten los parámetros en el PostMethod con addParameter
			if(files==null || files.size()==0){
				if(params!=null){
					Set<Entry<String, String>> set = params.entrySet();
					//añadir los parámetros al post
					for(Entry<String, String> entry:set){
	//					String paramUrlEncoded = URLEncoder.encode(entry.getValue(), "ISO-8859-1");
						//No es necesario codificar el valor del parámetro porque lo debe codifica auomáticamente al realizar la invocación.
						//El Charset por defecto es ISO-8859-1. (Está sin comprobar)
						String paramUrlEncoded = entry.getValue();
						httpPost.addParameter(entry.getKey(), paramUrlEncoded);
					}
				}
			}
			//Si hay un fichero, se crea un MultipartRequestEntity para pasar los parámtetros y el fichero
			else{
				int numParts = 0;
				Part[] parts = null;
				if(params!=null){
					numParts = params.size() + files.size();
					parts = new Part[numParts];
					Set<Entry<String, String>> set = params.entrySet();
					Set<Entry<String, File>> setFiles = files.entrySet();
					int i = 0;
					for(Entry<String, String> entry:set){
						String paramUrlEncoded = entry.getValue();
						parts[i] = new StringPart(entry.getKey(), paramUrlEncoded);
						i++;
					}
					for(Entry<String, File> entry:setFiles){
						parts[i] = new FilePart(entry.getKey(), entry.getValue());
						i++;
					}
				}else{
					parts = new Part[files.size()];
					Set<Entry<String, File>> setFiles = files.entrySet();
					int i = 0;
					for(Entry<String, File> entry:setFiles){
						parts[i] = new FilePart(entry.getKey(), entry.getValue());
						i++;
					}
				}
				httpPost.setRequestEntity(new MultipartRequestEntity(parts, httpPost.getParams()));
				
			}
			httpclient.executeMethod(httpPost);
			//Obtener la respuesta
			result.setMensaje(new String(httpPost.getResponseBody()));
			result.setError(Boolean.FALSE);
			
		} catch(Throwable e){
			result.setMensaje(e.getMessage());
			result.setError(Boolean.TRUE);
		} finally {
			httpPost.releaseConnection();
		}
		
		return result;
	}
	
	/**
	 * Realiza la invocación para presentar el 576 en la AEAT y devuelve la respuesta o el error en caso de producirse
	 * 
	 * @param urlHost
	 * @param params
	 * @return
	 * @throws OegamExcepcion 
	 */
	private ResultBean realizarInvocacion576(String urlHost, LinkedHashMap<String, String> params) throws OegamExcepcion {

		ResultBean result = new ResultBean();
		HttpClient httpclient = obtenerHttpClient();
		PostMethod httpPost = new PostMethod(urlHost);

		try {
			Set<Entry<String, String>> set = params.entrySet();

			ObjectNode node = new ObjectMapper().createObjectNode();
			for (Entry<String, String> entry : set) {
				String paramUrlEncoded = entry.getValue();
				node.put(entry.getKey(), paramUrlEncoded);
			}
			httpPost.setRequestEntity(new StringRequestEntity(node.toString(), "application/json", "UTF-8"));
			//Se adjunta al resultado el json enviado
			result.addAttachment("jsonEnviado", node.toString());
			httpclient.executeMethod(httpPost);
			// Obtener la respuesta
			result.setMensaje(new String(httpPost.getResponseBody()));
			// Se guarda la respuesta recibida
			result.addAttachment("respuestaRecibidaSintratrar", result.getMensaje());
			// Se adjunta al resultado el mapeo de la respuesta recibida
			result.addAttachment("respuestaJsonRecibida", new ObjectMapper().readValue(result.getMensaje(), RespuestaJsonRecibida.class));
			result.setError(Boolean.FALSE);

		} catch (Throwable e) {
			log.error("Error al llamar al WS de AEAT para la presentación del modelo 576.", e);
			result.setMensaje(e.getMessage());
			result.setError(Boolean.TRUE);
		} finally {
			httpPost.releaseConnection();
		}
		return result;
	}
}

package comunicaciones.http;

import java.io.IOException;

import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.gestoresmadrid.oegam2comun.peticionesurl.jaxb.ParametrosPeticiones;
import org.gestoresmadrid.oegam2comun.peticionesurl.jaxb.ParametrosPeticiones.Param;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import escrituras.beans.ResultBean;

/**
 * Encapsula el establecimiento y ejecución de una petición post
 */
public class HttpPostMethod {
	private static final ILoggerOegam log = LoggerOegam.getLogger(HttpPostMethod.class);

	public static final String RESPUESTA = "respuesta";
	public static final String RESPONSE_HEADERS = "responseHeaders";
	public static final String BYTES_FICHERO = "bytesFichero";
	
	/**
	 * 
	 * Ejecuta la petición post garantizando la concurrencia
	 * 
	 * @param url
	 * @param headers
	 * @param parametros
	 * @param download booleano, a true si espera descargar un fichero, false si no.
	 * @return ResultBean que tendrá error en caso de que el cuerpo de la response sea null.
	 */
	public synchronized ResultBean ejecutarPeticion(String url, List<Header> headers, ParametrosPeticiones parametros, boolean download) {
		ResultBean resultado = new ResultBean();

		if (url == null || url.trim().isEmpty()) {
			resultado.setError(true);
			resultado.setMensaje("No existe URL");
		} else {

			PostMethod postMethod = null;
			try {
				postMethod = new PostMethod(url);

				if (headers != null) {
					for (Header header : headers) {
						postMethod.addRequestHeader(header);
					}
				}

				if (parametros != null) {
					List<Param> param = parametros.getParam();
					if (param!=null && !param.isEmpty()){
						for (Param p : param){
							postMethod.addParameter(p.getNombre(), p.getValor());
						}
					}
				}

				if (!postMethod.validate()) {
					resultado.setError(true);
					resultado.setMensaje(HttpStatus.getStatusText(HttpStatus.SC_BAD_REQUEST));
				} else {
					HttpClient httpClient = new HttpClient();
					int returnCode = httpClient.executeMethod(postMethod);
					if (download) {
						// Con descarga
						if (returnCode == HttpStatus.SC_OK && postMethod.getResponseBody() != null) {
							resultado.addAttachment(BYTES_FICHERO, postMethod.getResponseBody());
							resultado.addAttachment(RESPUESTA, postMethod.getResponseBodyAsString());
							resultado.addAttachment(RESPONSE_HEADERS, postMethod.getResponseHeaders());
							resultado.setMensaje(HttpStatus.getStatusText(returnCode));
							resultado.setError(false);
						} else {
							resultado.setMensaje(HttpStatus.getStatusText(returnCode));
							resultado.setError(true);
						}
					} else {
						// Sin descarga
						if (postMethod.getResponseBody() != null && !postMethod.getResponseBodyAsString().equals("")) {
							resultado.addAttachment(RESPONSE_HEADERS,postMethod.getResponseHeaders());
							resultado.addAttachment(RESPUESTA, postMethod.getResponseBodyAsString());
							resultado.setMensaje(HttpStatus.getStatusText(returnCode));
							resultado.setError(false);
						} else {
							resultado.setMensaje(HttpStatus.getStatusText(returnCode));
							resultado.setError(true);
						}
					}
				}
			} catch (HttpException e) {
				log.error("Error ejecutando post", e);
				resultado.setError(true);
				resultado.setMensaje(e.getMessage());
			} catch (IOException e) {
				log.error("Error lectura/escritura en la llamada al post", e);
				resultado.setError(true);
				resultado.setMensaje(e.getMessage());
			} finally {
				postMethod.releaseConnection();
			}
		}
		return resultado;
	}

}

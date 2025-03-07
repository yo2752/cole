package org.gestoresmadrid.oegamInterga.restWS;

import java.io.FileInputStream;
import java.io.Serializable;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.gestoresmadrid.oegamInterga.restWS.request.FileRequest;
import org.gestoresmadrid.oegamInterga.restWS.request.SendFilesRequest;
import org.gestoresmadrid.oegamInterga.restWS.request.UpdateRequest;
import org.gestoresmadrid.oegamInterga.restWS.response.ErrorResponse;
import org.gestoresmadrid.oegamInterga.restWS.response.Errors;
import org.gestoresmadrid.oegamInterga.restWS.response.FilePdfResponse;
import org.gestoresmadrid.oegamInterga.restWS.response.FileResponse;
import org.gestoresmadrid.oegamInterga.restWS.response.SendFilesResponse;
import org.gestoresmadrid.oegamInterga.restWS.response.UpdateResponse;
import org.gestoresmadrid.oegamInterga.view.bean.ResultadoIntergaBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class IntergaRest implements Serializable {

	private static final long serialVersionUID = 3142594378946105665L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(IntergaRest.class);

	@Autowired
	GestorPropiedades gestorPropiedades;

	public ResultadoIntergaBean enviar(SendFilesRequest request) {
		ResultadoIntergaBean resultBean = new ResultadoIntergaBean(Boolean.FALSE);
		int code = 0;
		try {
			WebResource webResource = getClient("sendfiles/");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request);
			code = clientResponse.getStatus();
			if (code == 200) {
				SendFilesResponse response = clientResponse.getEntity(SendFilesResponse.class);
				resultBean.setResponse(response);
			} else {
				Errors response = clientResponse.getEntity(Errors.class);
				resultBean.setResponse(response);
			}
			resultBean.setCode(code);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest de Envio a Interga (" + code + ")", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest de Envio del Interga (" + code + ")");
		}
		return resultBean;
	}

	public ResultadoIntergaBean consultar(FileRequest request) {
		ResultadoIntergaBean resultBean = new ResultadoIntergaBean(Boolean.FALSE);
		int code = 0;
		try {
			WebResource webResource = getClient("file/");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request);
			code = clientResponse.getStatus();
			if (code == 200) {
				FileResponse response = clientResponse.getEntity(FileResponse.class);
				resultBean.setResponse(response);
			} else {
				ErrorResponse response = clientResponse.getEntity(ErrorResponse.class);
				resultBean.setResponse(response);
			}
			resultBean.setCode(code);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest de Consulta de Interga  (" + code + ")", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest de Consulta de Interga  (" + code + ")");
		}
		return resultBean;
	}

	public ResultadoIntergaBean descargar(FileRequest request, String parametroPdf) {
		ResultadoIntergaBean resultBean = new ResultadoIntergaBean(Boolean.FALSE);
		int code = 0;
		try {
			WebResource webResource = getClient("file/" + parametroPdf);
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request);
			code = clientResponse.getStatus();
			if (code == 200) {
				FilePdfResponse response = clientResponse.getEntity(FilePdfResponse.class);
				resultBean.setResponse(response);
			} else {
				ErrorResponse response = clientResponse.getEntity(ErrorResponse.class);
				resultBean.setResponse(response);
			}
			resultBean.setCode(code);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest de Descarga de Interga  (" + code + ")", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest de Descarga de Interga  (" + code + ")");
		}
		return resultBean;
	}

	public ResultadoIntergaBean subirDocumentacion(UpdateRequest request, String tipoPdf) {
		ResultadoIntergaBean resultBean = new ResultadoIntergaBean(Boolean.FALSE);
		int code = 0;
		try {
			WebResource webResource = getClient("file/" + tipoPdf + "/update/");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request);
			code = clientResponse.getStatus();
			if (code == 200) {
				UpdateResponse response = clientResponse.getEntity(UpdateResponse.class);
				resultBean.setResponse(response);
			} else {
				ErrorResponse response = clientResponse.getEntity(ErrorResponse.class);
				resultBean.setResponse(response);
			}
			resultBean.setCode(code);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest de subida de documentación de Interga (" + code + ")", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest de subida de documentación de Interga (" + code + ")");
		}
		return resultBean;
	}

	private WebResource getClient(String tipoOperacion) {
		ClientConfig clientConfig = new DefaultClientConfig();
		try {
			String url = gestorPropiedades.valorPropertie("interga.rest.url") + tipoOperacion;

			String alg = KeyManagerFactory.getDefaultAlgorithm();
			KeyManagerFactory kmFact = KeyManagerFactory.getInstance(alg);
			KeyStore ks = KeyStore.getInstance("JKS");
			FileInputStream fis = new FileInputStream(gestorPropiedades.valorPropertie("trafico.keyStore"));
			ks.load(fis, gestorPropiedades.valorPropertie("keystore.claves.privadas.xDefecto.password").toCharArray());
			fis.close();
			kmFact.init(ks, gestorPropiedades.valorPropertie("keystore.claves.privadas.xDefecto.password").toCharArray());

			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			KeyStore kst = KeyStore.getInstance("JKS");
			FileInputStream fist = new FileInputStream(gestorPropiedades.valorPropertie("trafico.trustStore"));
			kst.load(fist, gestorPropiedades.valorPropertie("keystore.claves.publicas.xDefecto.password").toCharArray());
			fist.close();
			tmf.init(kst);

			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(kmFact.getKeyManagers(), tmf.getTrustManagers(), null);

			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

			clientConfig.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(null, sslContext));

			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			clientConfig.getClasses().add(JacksonJaxbJsonProvider.class);
			Client client = Client.create(clientConfig);

			return client.resource(url);
		} catch (Throwable e) {

		}
		return null;
	}
}

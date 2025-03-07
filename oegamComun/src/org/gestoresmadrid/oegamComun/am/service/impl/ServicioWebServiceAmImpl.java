package org.gestoresmadrid.oegamComun.am.service.impl;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.gestoresmadrid.oegamComun.am.model.json.ReponseMatw;
import org.gestoresmadrid.oegamComun.am.model.json.RequestValidarBaja;
import org.gestoresmadrid.oegamComun.am.model.json.RequestValidarMatw;
import org.gestoresmadrid.oegamComun.am.model.json.ResponseBajas;
import org.gestoresmadrid.oegamComun.am.model.json.ResponseCtit;
import org.gestoresmadrid.oegamComun.am.service.ServicioWebServiceAm;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioWebServiceAmImpl implements ServicioWebServiceAm{

	private static final long serialVersionUID = 491567042762835535L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceAmImpl.class);
	
	@Autowired
	GestorPropiedades gestorPropiedades;

	@Override
	public ResultadoBean validarCtit(BigDecimal numExpediente, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			RequestValidarMatw request = crearRequestValidarMatw(numExpediente, idUsuario);
			WebResource webResource = getClient("transmision/validar");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request);
			if (clientResponse != null) {
				if (clientResponse.getStatus() == 200) {
					ResponseCtit response = clientResponse.getEntity(ResponseCtit.class);
					if (response != null) {
						if (StringUtils.isNotBlank(response.getCodigoResultado())) {
							if (!"00".equals(response.getCodigoResultado())) {
								resultado.setError(Boolean.TRUE);
								String mensaje = "";
								if(response.getListaErrores() != null && !response.getListaErrores().isEmpty()){
									for(String mensajeError : response.getListaErrores()){
										mensaje += mensajeError;
									}
								} else {
									mensaje = response.getResultado();
									if(response.getListaAvisos() != null && !response.getListaAvisos().isEmpty()){
										for(String mensajeAviso : response.getListaAvisos()){
											mensaje += mensajeAviso;
										}
									}
								}
								resultado.setMensaje(mensaje);
							} else {
								if(response.getListaAvisos() != null && !response.getListaAvisos().isEmpty()){
									String mensaje = "";
									for(String mensajeAviso : response.getListaAvisos()){
										mensaje += mensajeAviso;
									}
									resultado.setMensaje(mensaje);
								}
							}
							if(StringUtils.isNotBlank(response.getEstadoTramite())){
								resultado.setEstado(response.getEstadoTramite());
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Código de respuesta vacío");
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se ha podido gestionar el objeto Response");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha habido un error en la llamada al WS para realizar la validacion del tramite: " + numExpediente);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al crear el cliente en la llamada al WS para realizar la validacion del tramite: " + numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite: " + numExpediente + " contra AM, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite: " + numExpediente + " contra AM.");
		}
		return resultado;
	}
	
	@Override
	public ResultadoBean validarMatw(BigDecimal numExpediente, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			RequestValidarMatw request = crearRequestValidarMatw(numExpediente, idUsuario);
			WebResource webResource = getClient("matriculacion/validar");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request);
			if (clientResponse != null) {
				if (clientResponse.getStatus() == 200) {
					ReponseMatw response = clientResponse.getEntity(ReponseMatw.class);
					if (response != null) {
						if (StringUtils.isNotBlank(response.getCodigoResultado())) {
							if (!"00".equals(response.getCodigoResultado())) {
								String mensaje = "";
								if(response.getListaErrores() != null && !response.getListaErrores().isEmpty()){
									resultado.setError(Boolean.TRUE);
									for(String mensajeError : response.getListaErrores()){
										mensaje += mensajeError;
									}
								} else {
									mensaje = response.getResultado();
									if(response.getListaAvisos() != null && !response.getListaAvisos().isEmpty()){
										for(String mensajeAviso : response.getListaAvisos()){
											mensaje += mensajeAviso;
										}
									}
								}
								resultado.setMensaje(mensaje);
							} else {
								if(response.getListaAvisos() != null && !response.getListaAvisos().isEmpty()){
									String mensaje = "";
									for(String mensajeAviso : response.getListaAvisos()){
										mensaje += mensajeAviso;
									}
									resultado.setMensaje(mensaje);
								}
							}
							if(StringUtils.isNotBlank(response.getEstadoTramite())){
								resultado.setEstado(response.getEstadoTramite());
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Código de respuesta vacío");
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se ha podido gestionar el objeto Response");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha habido un error en la llamada al WS para realizar la validacion del tramite: " + numExpediente);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al crear el cliente en la llamada al WS para realizar la validacion del tramite: " + numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite: " + numExpediente + " contra AM, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite: " + numExpediente + " contra AM.");
		}
		return resultado;
	}
	
	private RequestValidarMatw crearRequestValidarMatw(BigDecimal numExpediente, Long idUsuario) {
		RequestValidarMatw request = new RequestValidarMatw();
		request.setTipoOperacion("0");
		request.setNumExpediente(numExpediente.toString());
		request.setIdUsuario(idUsuario);
		request.setPeticionDistintivo("NO");
		return request;
	}

	@Override
	public ResultadoBean validarBaja(BigDecimal numExpediente, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			RequestValidarBaja request = crearRequestValidarBaja(numExpediente, idUsuario);
			WebResource webResource = getClient("baja/validar");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request);
			if (clientResponse != null) {
				if (clientResponse.getStatus() == 200) {
					ResponseBajas response = clientResponse.getEntity(ResponseBajas.class);
					if (response != null) {
						if (StringUtils.isNotBlank(response.getCodigoResultado())) {
							if (!"00".equals(response.getCodigoResultado())) {
								resultado.setError(Boolean.TRUE);
								String mensaje = "";
								if(response.getListaErrores() != null && !response.getListaErrores().isEmpty()){
									for(String mensajeError : response.getListaErrores()){
										mensaje += mensajeError;
									}
								} else {
									mensaje = response.getResultado();
									if(response.getListaAvisos() != null && !response.getListaAvisos().isEmpty()){
										for(String mensajeAviso : response.getListaAvisos()){
											mensaje += mensajeAviso;
										}
									}
								}
								resultado.setMensaje(mensaje);
							}
							if(StringUtils.isNotBlank(response.getEstadoTramite())){
								resultado.setEstado(response.getEstadoTramite());
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Código de respuesta vacío");
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se ha podido gestionar el objeto Response");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha habido un error en la llamada al WS para realizar la validacion del tramite: " + numExpediente);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al crear el cliente en la llamada al WS para realizar la validacion del tramite: " + numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite: " + numExpediente + " contra AM, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite: " + numExpediente + " contra AM.");
		}
		return resultado;
	}
	
	private RequestValidarBaja crearRequestValidarBaja(BigDecimal numExpediente, Long idUsuario) {
		RequestValidarBaja request = new RequestValidarBaja();
		request.setTipoOperacion("0");
		request.setNumExpediente(numExpediente.toString());
		request.setIdUsuario(idUsuario);
		return request;
	}

	private WebResource getClient(String tipoOperacion) {
		ClientConfig clientConfig = new DefaultClientConfig();
		try {
			String url = gestorPropiedades.valorPropertie("oegamAm.rest.url") + tipoOperacion;

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

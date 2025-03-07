package org.gestoresmadrid.oegam2comun.licenciasCam.restWS;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.gestoresmadrid.oegam2comun.licenciasCam.gestor.GestorDatosMaestrosLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.Codigo;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.CodigosError;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.ObjectFactoryPrincipal;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.request.PeticionEnvio;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.request.RegistrarSolicitudRequest;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.EstadoSolicitud;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.ResultadoEnvioDocumentacion;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.ResultadoRegistro;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import com.sun.jersey.multipart.impl.MultiPartWriter;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class LicenciasCamRest implements Serializable {

	private static final long serialVersionUID = -250011669029283471L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(LicenciasCamRest.class);

	@Autowired
	GestorDatosMaestrosLic gestorDatosMaestrosLic;

	@Autowired
	GestorPropiedades gestorPropiedades;

	public ResultBean enviarSolicitud(PeticionEnvio request) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = getClient("solicitudes/envios/");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).post(ClientResponse.class, request);
			List<LinkedHashMap<String, String>> envSolResponse = (List<LinkedHashMap<String, String>>) clientResponse.getEntity(List.class);
			resultBean.addAttachment("RESPONSE", convertirReponse(envSolResponse));
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest de Envío de Solicitud de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest de Envío de Solicitud de licencias CAM");
		}
		return resultBean;
	}

	private CodigosError convertirReponse(List<LinkedHashMap<String, String>> response) {
		ObjectFactoryPrincipal objFactory = new ObjectFactoryPrincipal();
		CodigosError codigos = objFactory.createCodigosError();
		for (LinkedHashMap<String, String> linked : response) {
			Codigo codigoError = objFactory.createCodigo();
			codigoError.setCampo(linked.get("campo"));
			codigoError.setCodigoRespuesta(linked.get("codigoRespuesta"));
			codigoError.setDescripcion(linked.get("descripcion"));
			codigos.getCodigo().add(codigoError);
		}
		return codigos;
	}

	public ResultBean enviarDocumentacion(File fichero, String idSolicitud) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = getClient("solicitudes/envios/" + idSolicitud);
			FormDataMultiPart form = new FormDataMultiPart();
			FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("fichero", fichero, MediaType.APPLICATION_OCTET_STREAM_TYPE);
			form.bodyPart(fileDataBodyPart);
			ClientResponse clientResponse = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).header("Authorization", "Bearer " + token).post(ClientResponse.class, form);
			String json = getStringFromInputStream(clientResponse.getEntityInputStream());
			ObjectMapper mapper = new ObjectMapper();
			ResultadoEnvioDocumentacion envSolResponse = mapper.readValue(json, ResultadoEnvioDocumentacion.class);
			resultBean.addAttachment("RESPONSE", envSolResponse);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest de Envío de Documentación de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest de Envío de Documentación de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean registrarSolicitud(RegistrarSolicitudRequest request) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = getClient("solicitudes/presentaciones/");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).post(ClientResponse.class, request);
			ResultadoRegistro presSolResponse = clientResponse.getEntity(ResultadoRegistro.class);
			resultBean.addAttachment("RESPONSE", presSolResponse);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest de Registro de Solicitud de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest de Registro de Solicitud de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean consultarSolicitud(String idSolicitud) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = getClient("solicitudes/" + idSolicitud);
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).post(ClientResponse.class);
			EstadoSolicitud conSolResponse = clientResponse.getEntity(EstadoSolicitud.class);
			resultBean.addAttachment("RESPONSE", conSolResponse);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest de Consulta de Solicitud de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest de Consulta de Solicitud de licencias CAM");
		}
		return resultBean;
	}

	public WebResource getClient(String tipoOperacion) {
		ClientConfig clientConfig = new DefaultClientConfig();
		String url = gestorPropiedades.valorPropertie("licencias.cam.rest.url") + tipoOperacion;
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		clientConfig.getClasses().add(JacksonJaxbJsonProvider.class);
		clientConfig.getClasses().add(MultiPartWriter.class);
		Client client = Client.create(clientConfig);
		return client.resource(url);
	}

	private String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		final StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
}
package org.gestoresmadrid.oegamComun.correo.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.gestoresmadrid.oegamComun.correo.service.ServicioComunCorreo;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.utilesCorreo.CorreoRequest;
import org.gestoresmadrid.utilidades.utilesCorreo.CorreoResponse;
import org.gestoresmadrid.utilidades.utilesCorreo.FicheroAdjunto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioComunCorreoImpl implements ServicioComunCorreo {

	private static final long serialVersionUID = 7478656483687162013L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioComunCorreoImpl.class);

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Override
	public ResultadoBean enviarCorreo(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia, String direccionesOcultas, String origen,
			FicheroBean... adjuntos) {
		ResultadoBean resultBean = new ResultadoBean(Boolean.FALSE);
		try {
			CorreoRequest correo = crearRequest(texto, textoPlano, from, subject, recipient, copia, direccionesOcultas, origen, adjuntos);
			resultBean = invocarRest(correo, "enviarCorreo");
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de enviar el correo, error: ", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Ha sucedido un error a la hora de enviar el correo.");
		}
		return resultBean;
	}

	@Override
	public ResultadoBean enviarNotificacion(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia, String direccionesOcultas, String origen,
			FicheroBean... adjuntos) {
		ResultadoBean resultBean = new ResultadoBean(Boolean.FALSE);
		try {
			CorreoRequest correo = crearRequest(texto, textoPlano, from, subject, recipient, copia, direccionesOcultas, origen, adjuntos);
			resultBean = invocarRest(correo, "enviarNotificacion");
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de enviar el correo de notificación error: ", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Ha sucedido un error a la hora de enviar el correo de notificación.");
		}
		return resultBean;
	}

	private CorreoRequest crearRequest(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia, String direccionesOcultas, String origen,
			FicheroBean... adjuntos) {
		CorreoRequest correo = new CorreoRequest();
		List<FicheroAdjunto> ficherosAdjuntos = new ArrayList<FicheroAdjunto>();
		try {
			if (adjuntos != null) {
				ficherosAdjuntos = dameAdjuntos(adjuntos);
			}
		} catch (Exception e) {
			log.error("Error al adjuntado elemetos");
			ficherosAdjuntos = null;
		}
		correo.setOrigen(origen);
		correo.setAdjuntos(ficherosAdjuntos);
		correo.setCopia(copia);
		correo.setDireccionesOcultas(direccionesOcultas);
		correo.setFrom(from);
		correo.setRecipient(recipient);
		correo.setSubject(subject);
		correo.setTexto(texto);
		correo.setTextoPlano(textoPlano);

		return correo;
	}

	private ResultadoBean invocarRest(CorreoRequest correo, String metodo) {
		String url = gestorPropiedades.valorPropertie("gestorcorreo.url") + metodo;
		ResultadoBean respuestaBean = new ResultadoBean(false);
		try {
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			clientConfig.getClasses().add(JacksonJaxbJsonProvider.class);
			Client client = Client.create(clientConfig);
			WebResource webResource = client.resource(url);
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, correo);
			CorreoResponse respuestaCadena = response.getEntity(CorreoResponse.class);
			respuestaBean.setError(respuestaCadena.getError());
			respuestaBean.setListaMensajes(respuestaCadena.getListaMensajes());
		} catch (Exception e) {
			respuestaBean.setError(Boolean.TRUE);
			respuestaBean.setMensaje("Ha sucedido un error a la hora de invocar el WS del gestor de correos.");
			log.error("Ha sucedido un error a la hora de invocar el WS del gestor de correos, error: ", e);
		}
		return respuestaBean;
	}

	private List<FicheroAdjunto> dameAdjuntos(FicheroBean[] adjuntos) throws IOException {
		List<FicheroAdjunto> ficherosAdjuntos = new ArrayList<FicheroAdjunto>();

		for (FicheroBean adjunto : adjuntos) {
			FicheroAdjunto fichero = new FicheroAdjunto();
			if (adjunto.getFichero() != null) {
				fichero.setFicheroByte(org.apache.commons.io.FileUtils.readFileToByteArray(adjunto.getFichero()));
			} else if (adjunto.getFicheroByte() != null) {
				fichero.setFicheroByte(adjunto.getFicheroByte());
			}

			if (adjunto.getNombreYExtension() != null) {
				fichero.setNombreDocumento(adjunto.getNombreYExtension());
			} else if (adjunto.getNombreDocumento() != null) {
				fichero.setNombreDocumento(adjunto.getNombreDocumento());
			} else {
				fichero.setNombreDocumento("default");
			}
			if (fichero.getFicheroByte() != null) {
				ficherosAdjuntos.add(fichero);
			}
		}
		return ficherosAdjuntos;
	}
}

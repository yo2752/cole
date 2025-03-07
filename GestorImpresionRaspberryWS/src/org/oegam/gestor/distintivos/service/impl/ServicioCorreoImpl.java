package org.oegam.gestor.distintivos.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.oegam.gestor.distintivos.correo.utiles.CorreoRequest;
import org.oegam.gestor.distintivos.correo.utiles.CorreoResponse;
import org.oegam.gestor.distintivos.correo.utiles.FicheroAdjunto;
import org.oegam.gestor.distintivos.integracion.bean.FicheroBean;
import org.oegam.gestor.distintivos.service.ServicioCorreo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioCorreoImpl implements ServicioCorreo {

	private static final long serialVersionUID = 3800472444751543507L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioCorreoImpl.class);

	@Autowired GestorPropiedades gestorPropiedades;

	@Override
	public void  enviarCorreo(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia, String direccionesOcultas, String origen, FicheroBean... adjuntos) throws OegamExcepcion, IOException {
		// Seteo de la clase Correo con los parametros
		CorreoRequest correo = new CorreoRequest();
		List<FicheroAdjunto> ficherosAdjuntos = new ArrayList<>();
		correo.setOrigen(origen);
		correo.setAdjuntos(ficherosAdjuntos);
		correo.setCopia(copia);
		correo.setDireccionesOcultas(direccionesOcultas);
		correo.setFrom(from);
		correo.setRecipient(recipient);
		correo.setSubject(subject);
		correo.setTexto(texto);
		correo.setTextoPlano(textoPlano);
		invocarRest(correo, "enviarCorreo");
	}

	private void invocarRest(CorreoRequest correo, String metodo) {
		String url = gestorPropiedades.valorPropertie(PROPERTY_URL_CORREO) + metodo;
		try {
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(
					JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			clientConfig.getClasses().add(JacksonJaxbJsonProvider.class);
			Client client = Client.create(clientConfig);
			WebResource webResource = client
					.resource(url);
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, correo);
			CorreoResponse respuestaCadena = response.getEntity(CorreoResponse.class);
			if (respuestaCadena.getError()) {
				for (String mensaje : respuestaCadena.getListaMensajes()) {
					log.error("Error al enviar el mail:"+ mensaje);
				}
			} else {
				log.info("Mail impresion enviado correctamente.");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de enviar el mail de impresión, error: ", e);
		}
	}
}
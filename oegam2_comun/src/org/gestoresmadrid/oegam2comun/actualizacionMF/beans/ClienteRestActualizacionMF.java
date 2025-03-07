package org.gestoresmadrid.oegam2comun.actualizacionMF.beans;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class ClienteRestActualizacionMF {

	String url;

	public ClienteRestActualizacionMF(String entorno) {
//		if(entorno.equalsIgnoreCase("DESARROLLO")){
//			this.url = Constantes.URL_DESARROLLO_MF;
//		}else if(entorno.equalsIgnoreCase("PREPRODUCCION")){
//			this.url = Constantes.URL_PRE_MF;
//		}else if(entorno.equalsIgnoreCase("TEST")){
//			this.url = Constantes.URL_TEST_MF;
//		}else{
//			this.url = Constantes.URL_PRO_MF;
//		}
		this.url = "http://localhost:8080/actualizacionMFWS/service/procesarActualizacion";
	}

	public ClienteRestActualizacionMF() {
	}

	@SuppressWarnings("unused")
	public ActualizacionMFResponse procesarMF(ActualizacionMFRequest request) {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(clientConfig);
		WebResource webResource = client.resource(url);
		ActualizacionMFResponse respuesta = null;
		Builder builder = webResource.accept(MediaType.APPLICATION_JSON).header("content-type", MediaType.APPLICATION_JSON);

		ObjectMapper mapper = new ObjectMapper();
		ClientResponse response = null;
		try {
			response = webResource.type("application/json").post(ClientResponse.class, mapper.writeValueAsString(request));
			// Status 200 is successful.
			if (response.getStatus() != 200) {
				String error= response.getEntity(String.class);
				respuesta = new ActualizacionMFResponse();
				respuesta.setError(Boolean.TRUE);
				respuesta.setMensajeError(error);
				return respuesta;
			}
			respuesta = (ActualizacionMFResponse) response.getEntity(ActualizacionMFResponse.class);
		} catch (JsonGenerationException | JsonMappingException e) {
			e.printStackTrace();
		} catch (UniformInterfaceException | ClientHandlerException | IOException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return respuesta;
	}

}
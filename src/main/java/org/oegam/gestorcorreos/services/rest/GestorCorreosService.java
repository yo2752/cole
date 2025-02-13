package org.oegam.gestorcorreos.services.rest;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.oegam.gestorcorreos.beans.CorreoRequest;
import org.oegam.gestorcorreos.beans.CorreoResponse;
import org.oegam.gestorcorreos.beans.ResultBean;
import org.oegam.gestorcorreos.services.GestorCorreo;
import org.oegam.gestorcorreos.services.GestorCorreoBajas;
import org.oegam.gestorcorreos.services.GestorCorreoDuplicados;
import org.oegam.gestorcorreos.services.GestorCorreoIncidenciaCanje;
import org.oegam.gestorcorreos.services.GestorCorreoIncidenciaJptm;
import org.oegam.gestorcorreos.services.GestorCorreoNotificacionesEni;

@Path("/")
public class GestorCorreosService{

	public static final Logger logOegam = Logger.getLogger(GestorCorreo.class);
	public static final Logger logDuplicados = Logger.getLogger(GestorCorreoDuplicados.class);
	public static final Logger logBajas = Logger.getLogger(GestorCorreoBajas.class);
	public static final Logger logInciJptm = Logger.getLogger(GestorCorreoIncidenciaJptm.class);
	public static final Logger logInciCanje = Logger.getLogger(GestorCorreoIncidenciaCanje.class);

	@POST
	@Path("/enviarCorreo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CorreoResponse enviarCorreo(CorreoRequest correo){
		GestorCorreo gestorCorreo = GestorCorreo.getInstance();
		
		CorreoResponse respuesta = new CorreoResponse();
		if(correo.getOrigen()==null || correo.getOrigen().trim().isEmpty()){
			correo.setOrigen("oegam");
		}
		if(correo.getTexto() == null || correo.getTexto().trim().isEmpty()){
			respuesta.setError(true);
			respuesta.addMensajeALista("No se ha recibido el texto del mensaje");
			return respuesta;
		}
		if(correo.getRecipient() == null || correo.getRecipient().trim().isEmpty()){
			respuesta.setError(true);
			respuesta.addMensajeALista("No se han configurado los destinatarios del mensaje");
			return  respuesta;
		}
		ResultBean resultado = gestorCorreo.enviarMail(correo);
		logOegam.info("CorreoRequest = " + correo);
		return procesarResultado(resultado);
	}

	@POST
	@Path("/enviarNotificacion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CorreoResponse enviarNotificacion(CorreoRequest correo){
		GestorCorreo gestorCorreo = GestorCorreo.getInstance();
		StringBuffer sb = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>Notificaci&oacute;n desde la Oficina Electr&oacute;nica de Gesti&oacute;n Administrativa (OEGAM) <br>")
				.append(correo.getTexto())
				.append("<br><br></span>");
		try {
			correo.setTexto(new String(sb.toString().getBytes(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logOegam.error("Ha sucedido un error a la hora de parsear el texto de la notificación, error: ",e);
		}
		CorreoResponse respuesta = new CorreoResponse();
		if(correo.getOrigen()==null || correo.getOrigen().trim().isEmpty()){
			correo.setOrigen("oegam");
		}
		if(correo.getRecipient() == null || correo.getRecipient().trim().isEmpty()){
			respuesta.setError(true);
			respuesta.addMensajeALista("No se han configurado los destinatarios del mensaje");
			return  respuesta;
		}
		ResultBean resultado = gestorCorreo.enviarMail(correo);
		logOegam.info("CorreoRequest = " + correo);
		return procesarResultado(resultado);
	}

	@POST
	@Path("/enviarCorreoDuplicados")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CorreoResponse enviarCorreoDuplicados(CorreoRequest correo){
		GestorCorreoDuplicados gestorCorreo = GestorCorreoDuplicados.getInstance();
		
		CorreoResponse respuesta = new CorreoResponse();
		if(correo.getOrigen()==null || correo.getOrigen().trim().isEmpty()){
			correo.setOrigen("duplicados");
		}
		if(correo.getTexto() == null || correo.getTexto().trim().isEmpty()){
			respuesta.setError(true);
			respuesta.addMensajeALista("No se ha recibido el texto del mensaje");
			return respuesta;
		}
		if(correo.getRecipient() == null || correo.getRecipient().trim().isEmpty()){
			respuesta.setError(true);
			respuesta.addMensajeALista("No se han configurado los destinatarios del mensaje");
			return  respuesta;
		}

		ResultBean resultado = gestorCorreo.enviarMail(correo);
		logDuplicados.info("CorreoRequest = " + correo);
		return procesarResultado(resultado);
	}
	
	@POST
	@Path("/enviarCorreoBajas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CorreoResponse enviarCorreoBajas(CorreoRequest correo){
		GestorCorreoBajas gestorCorreo = GestorCorreoBajas.getInstance();
		
		CorreoResponse respuesta = new CorreoResponse();
		if(correo.getOrigen()==null || correo.getOrigen().trim().isEmpty()){
			correo.setOrigen("bajas");
		}
		if(correo.getTexto() == null || correo.getTexto().trim().isEmpty()){
			respuesta.setError(true);
			respuesta.addMensajeALista("No se ha recibido el texto del mensaje");
			return respuesta;
		}
		if(correo.getRecipient() == null || correo.getRecipient().trim().isEmpty()){
			respuesta.setError(true);
			respuesta.addMensajeALista("No se han configurado los destinatarios del mensaje");
			return  respuesta;
		}

		ResultBean resultado = gestorCorreo.enviarMail(correo);
		logBajas.info("CorreoRequest = " + correo);

		return procesarResultado(resultado);
	}
	
	@POST
	@Path("/enviarCorreoNotificacionesEni")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CorreoResponse enviarCorreoNotificacionesEni(CorreoRequest correo){
		GestorCorreoNotificacionesEni gestorCorreo = GestorCorreoNotificacionesEni.getInstance();
		
		CorreoResponse respuesta = new CorreoResponse();
		if(correo.getOrigen()==null || correo.getOrigen().trim().isEmpty()){
			correo.setOrigen("Notificaciones Eni");
		}
		if(correo.getTexto() == null || correo.getTexto().trim().isEmpty()){
			respuesta.setError(true);
			respuesta.addMensajeALista("No se ha recibido el texto del mensaje");
			return respuesta;
		}
		if(correo.getRecipient() == null || correo.getRecipient().trim().isEmpty()){
			respuesta.setError(true);
			respuesta.addMensajeALista("No se han configurado los destinatarios del mensaje");
			return  respuesta;
		}

		ResultBean resultado = gestorCorreo.enviarMail(correo);
		logBajas.info("CorreoRequest = " + correo);

		return procesarResultado(resultado);
	}
	
	@POST
	@Path("/enviarCorreoInciJptm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CorreoResponse enviarCorreoInciJptm(CorreoRequest correo){
		GestorCorreoIncidenciaJptm gestorCorreo = GestorCorreoIncidenciaJptm.getInstance();
		
		CorreoResponse respuesta = new CorreoResponse();
		if(correo.getOrigen()==null || correo.getOrigen().trim().isEmpty()){
			correo.setOrigen("Incidencias Jptm");
		}
		if(correo.getTexto() == null || correo.getTexto().trim().isEmpty()){
			respuesta.setError(true);
			respuesta.addMensajeALista("No se ha recibido el texto del mensaje");
			return respuesta;
		}
		if(correo.getRecipient() == null || correo.getRecipient().trim().isEmpty()){
			respuesta.setError(true);
			respuesta.addMensajeALista("No se han configurado los destinatarios del mensaje");
			return  respuesta;
		}

		ResultBean resultado = gestorCorreo.enviarMail(correo);
		logInciJptm.info("CorreoRequest = " + correo);
		return procesarResultado(resultado);
	}
	
	@POST
	@Path("/enviarCorreoInciCanje")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CorreoResponse enviarCorreoInciCanje(CorreoRequest correo){
		GestorCorreoIncidenciaCanje gestorCorreo = GestorCorreoIncidenciaCanje.getInstance();
		
		CorreoResponse respuesta = new CorreoResponse();
		if(correo.getOrigen()==null || correo.getOrigen().trim().isEmpty()){
			correo.setOrigen("Incidencias Jptm");
		}
		if(correo.getTexto() == null || correo.getTexto().trim().isEmpty()){
			respuesta.setError(true);
			respuesta.addMensajeALista("No se ha recibido el texto del mensaje");
			return respuesta;
		}
		if(correo.getRecipient() == null || correo.getRecipient().trim().isEmpty()){
			respuesta.setError(true);
			respuesta.addMensajeALista("No se han configurado los destinatarios del mensaje");
			return  respuesta;
		}

		ResultBean resultado = gestorCorreo.enviarMail(correo);
		logInciCanje.info("CorreoRequest = " + correo);
		return procesarResultado(resultado);
	}
	
	
	private CorreoResponse procesarResultado(ResultBean resultado) {
		CorreoResponse respuesta = new CorreoResponse();
		if(resultado==null){
			respuesta.setError(true);
			respuesta.addMensajeALista("Error al enviar el mail");
		}else if( resultado.getError()){
			respuesta.setError(true);
			respuesta.setListaMensajes(resultado.getListaMensajes());;
		}else{
			respuesta.setError(false);
			respuesta.setListaMensajes(resultado.getListaMensajes());;
		}
		return respuesta;
	}

}

package org.gestoresmadrid.oegam2comun.licenciasCam.restWS;

import java.io.Serializable;

import javax.ws.rs.core.MediaType;

import org.gestoresmadrid.oegam2comun.licenciasCam.gestor.GestorDatosMaestrosLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.request.ValidarDireccion;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.Salida;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoValidacionDireccionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class LicenciasCamValidacionesRest implements Serializable {

	private static final long serialVersionUID = -250011669029283471L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(LicenciasCamValidacionesRest.class);

	@Autowired
	LicenciasCamRest licenciasCamRest;

	@Autowired
	GestorDatosMaestrosLic gestorDatosMaestrosLic;

	public ResultadoValidacionDireccionBean validacionDireccion(ValidarDireccion request) {
		ResultadoValidacionDireccionBean resultBean = new ResultadoValidacionDireccionBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("bdc/validacion/");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).post(ClientResponse.class, request);
			Salida validDireccionResponse = clientResponse.getEntity(Salida.class);
			resultBean.addAttachment("RESPONSE", validDireccionResponse);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest validar dirección de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest Obtener Datos Ciudadano de licencias CAM");
		}
		return resultBean;
	}
}
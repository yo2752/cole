package org.gestoresmadrid.oegam2comun.licenciasCam.restWS;

import java.io.Serializable;

import javax.ws.rs.core.MediaType;

import org.gestoresmadrid.oegam2comun.licenciasCam.gestor.GestorDatosMaestrosLic;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class LicenciasCamDatosMaestrosRest implements Serializable {

	private static final long serialVersionUID = -250011669029283471L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(LicenciasCamDatosMaestrosRest.class);

	@Autowired
	LicenciasCamRest licenciasCamRest;

	@Autowired
	GestorDatosMaestrosLic gestorDatosMaestrosLic;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	public ResultBean obtenerToken() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		String usuario = gestorPropiedades.valorPropertie("licencias.cam.usuario.llamadas.rest");
		String password = gestorPropiedades.valorPropertie("licencias.cam.password.llamadas.rest");
		try {
			WebResource webResource = licenciasCamRest.getClient("accesos/token/");
			ClientResponse clientResponse = webResource.header("usuario", usuario).header("Password", password).get(ClientResponse.class);
			String token = clientResponse.getEntity(String.class);
			resultBean.addAttachment("RESPONSE", token);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener el token de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener el token de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerTipoActividad() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/TIPACT_LIC");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerProvincias() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/PROVIN?ordenacion=descripcion");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerPaises() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/PAISES?ordenacion=descripcion");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerTipoVias() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/TIPVIA?ordenacion=descripcion");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerTipoNumeracion() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/NOMAPP");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerNormaZonal() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/NORZON");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerNivelProteccion() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/NIVEL");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerTipoPlanta() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/TIPPLA");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerTipoEdificacion() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/TIPEDIF");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerTiposUso() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/OBUSOS");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerTipoCombustible() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/TIPCOMB");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerSuperficieNoComputable() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/NOCOMP");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerTipoObra() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/TOBRA_LIC");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerTiposDocumentos() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/DOCU_LIC?ordenacion=valor2");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerFormatoArchivosSoportados() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/FORMATO");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerEpigrafes() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/EPIACT");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerEstadoInformacion() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/TIPDAT");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerSituacionLocal() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/SITLOC");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerTipoAcceso() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/TIPACC");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerTiposLocal() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/TIPLOC");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerTipoDocumento() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("catalogo/extendido/TIPDOC");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}

	public ResultBean obtenerTipoSujeto() {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			String token = gestorDatosMaestrosLic.obtenerToken();
			WebResource webResource = licenciasCamRest.getClient("cid360/datos-maestros/TiposSujetos");
			ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get(ClientResponse.class);
			Object response = clientResponse.getEntity(Object.class);
			resultBean.addAttachment("RESPONSE", response);
		} catch (Exception e) {
			log.error("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Error en la llamada al servicio rest para obtener los datos maestros de licencias CAM");
		}
		return resultBean;
	}
}

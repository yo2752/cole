package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.CodigoError;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.AtributosAnotacion;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.AtributosRegistro;

public class ResultadoRegistro implements Serializable {

	private static final long serialVersionUID = -4802010033106481273L;

	private AtributosRegistro atributosRegistro;

	private AtributosAnotacion atributosAnotacion;

	private String b64Solicitud;

	private CodigoError codigoError;

	public AtributosRegistro getAtributosRegistro() {
		return atributosRegistro;
	}

	public void setAtributosRegistro(AtributosRegistro atributosRegistro) {
		this.atributosRegistro = atributosRegistro;
	}

	public AtributosAnotacion getAtributosAnotacion() {
		return atributosAnotacion;
	}

	public void setAtributosAnotacion(AtributosAnotacion atributosAnotacion) {
		this.atributosAnotacion = atributosAnotacion;
	}

	public String getB64Solicitud() {
		return b64Solicitud;
	}

	public void setB64Solicitud(String b64Solicitud) {
		this.b64Solicitud = b64Solicitud;
	}

	public CodigoError getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(CodigoError codigoError) {
		this.codigoError = codigoError;
	}
}

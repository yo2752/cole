package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.CodigoError;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", namespace = "", propOrder = {
    "idSolicitud",
    "tam",
    "codigoError"
})
public class ResultadoEnvioDocumentacion implements Serializable {

	private static final long serialVersionUID = 2735783314327957464L;

	private String idSolicitud;

	private int tam;

	private CodigoError codigoError;

	public String getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public int getTam() {
		return tam;
	}

	public void setTam(int tam) {
		this.tam = tam;
	}

	public CodigoError getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(CodigoError codigoError) {
		this.codigoError = codigoError;
	}
}

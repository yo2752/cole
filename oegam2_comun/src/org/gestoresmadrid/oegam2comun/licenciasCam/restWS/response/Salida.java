package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.SalidaErrorDTO;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.SalidaOkDTO;

public class Salida implements Serializable {

	private static final long serialVersionUID = -6176761764889432589L;

	private Integer estadoGlobal;

	private String estadoHistorico;

	private SalidaOkDTO salidaOkDTO;

	private SalidaErrorDTO salidaErrorDTO;

	public Integer getEstadoGlobal() {
		return estadoGlobal;
	}

	public void setEstadoGlobal(Integer estadoGlobal) {
		this.estadoGlobal = estadoGlobal;
	}

	public String getEstadoHistorico() {
		return estadoHistorico;
	}

	public void setEstadoHistorico(String estadoHistorico) {
		this.estadoHistorico = estadoHistorico;
	}

	public SalidaOkDTO getSalidaOkDTO() {
		return salidaOkDTO;
	}

	public void setSalidaOkDTO(SalidaOkDTO salidaOkDTO) {
		this.salidaOkDTO = salidaOkDTO;
	}

	public SalidaErrorDTO getSalidaErrorDTO() {
		return salidaErrorDTO;
	}

	public void setSalidaErrorDTO(SalidaErrorDTO salidaErrorDTO) {
		this.salidaErrorDTO = salidaErrorDTO;
	}
}

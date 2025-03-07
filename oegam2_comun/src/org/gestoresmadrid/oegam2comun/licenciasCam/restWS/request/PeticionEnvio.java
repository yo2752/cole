package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.request;

import java.io.Serializable;

public class PeticionEnvio implements Serializable {

	private static final long serialVersionUID = -2741569032158163386L;

	private String idSolicitud;

	private String idCanal;

	private String idProceso;

	private String idEtapa;

	private String ficheroBase64;

	private boolean soloValidar;

	public String getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getIdCanal() {
		return idCanal;
	}

	public void setIdCanal(String idCanal) {
		this.idCanal = idCanal;
	}

	public String getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}

	public String getIdEtapa() {
		return idEtapa;
	}

	public void setIdEtapa(String idEtapa) {
		this.idEtapa = idEtapa;
	}

	public String getFicheroBase64() {
		return ficheroBase64;
	}

	public void setFicheroBase64(String ficheroBase64) {
		this.ficheroBase64 = ficheroBase64;
	}

	public boolean getSoloValidar() {
		return soloValidar;
	}

	public void setSoloValidar(boolean soloValidar) {
		this.soloValidar = soloValidar;
	}
}

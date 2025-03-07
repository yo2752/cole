package org.gestoresmadrid.oegam2comun.actualizacionMF.beans;

import java.io.Serializable;

public class ActualizacionMFRequest implements Serializable {

	private static final long serialVersionUID = -1368191257172282163L;

	public ActualizacionMFRequest(byte[] fichero, Long idActualizacion) {
		this.fichero = fichero;
		this.idActualizacion = idActualizacion;
	}

	private byte[] fichero;

	private Long idActualizacion;

	public byte[] getFichero() {
		return fichero;
	}

	public void setFichero(byte[] fichero) {
		this.fichero = fichero;
	}

	public Long getIdActualizacion() {
		return idActualizacion;
	}

	public void setIdActualizacion(Long idActualizacion) {
		this.idActualizacion = idActualizacion;
	}

}
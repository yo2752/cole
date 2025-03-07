package org.gestoresmadrid.oegam2comun.situacion.view.dto;

import java.io.Serializable;

public class SituacionDto implements Serializable{

	private static final long serialVersionUID = 3952332496708588817L;
	
	private String idSituacion;
	private String descSituacion;
	
	public String getIdSituacion() {
		return idSituacion;
	}
	public void setIdSituacion(String idSituacion) {
		this.idSituacion = idSituacion;
	}
	public String getDescSituacion() {
		return descSituacion;
	}
	public void setDescSituacion(String descSituacion) {
		this.descSituacion = descSituacion;
	}
	
	
}

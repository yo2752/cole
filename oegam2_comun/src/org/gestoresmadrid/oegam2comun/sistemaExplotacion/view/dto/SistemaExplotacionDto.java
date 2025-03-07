package org.gestoresmadrid.oegam2comun.sistemaExplotacion.view.dto;

import java.io.Serializable;

public class SistemaExplotacionDto implements Serializable{
	
	
	private static final long serialVersionUID = -8534917845939583821L;

	private String idSistemaExplotacion;
	private String descSistema;
	
	public String getIdSistemaExplotacion() {
		return idSistemaExplotacion;
	}
	public void setIdSistemaExplotacion(String idSistemaExplotacion) {
		this.idSistemaExplotacion = idSistemaExplotacion;
	}
	public String getDescSistema() {
		return descSistema;
	}
	public void setDescSistema(String descSistema) {
		this.descSistema = descSistema;
	}

}

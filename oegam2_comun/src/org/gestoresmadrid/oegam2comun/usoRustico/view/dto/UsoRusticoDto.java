package org.gestoresmadrid.oegam2comun.usoRustico.view.dto;

import java.io.Serializable;

public class UsoRusticoDto implements Serializable{

	private static final long serialVersionUID = -3450596391293027142L;
	
	private String idUsoRustico;
	private String descUsoRustico;
	private String tipoUso;
	
	public String getIdUsoRustico() {
		return idUsoRustico;
	}
	public void setIdUsoRustico(String idUsoRustico) {
		this.idUsoRustico = idUsoRustico;
	}
	public String getDescUsoRustico() {
		return descUsoRustico;
	}
	public void setDescUsoRustico(String descUsoRustico) {
		this.descUsoRustico = descUsoRustico;
	}
	public String getTipoUso() {
		return tipoUso;
	}
	public void setTipoUso(String tipoUso) {
		this.tipoUso = tipoUso;
	}
	
	
}

package org.gestoresmadrid.oegam2comun.direccion.view.dto;

import java.io.Serializable;

public class TipoViaCamDto implements Serializable{

	private static final long serialVersionUID = -6001780319872146464L;

	private String idTipoViaCam;
	private String nombre;
	private String obsoleto;
	
	public String getIdTipoViaCam() {
		return idTipoViaCam;
	}
	public void setIdTipoViaCam(String idTipoViaCam) {
		this.idTipoViaCam = idTipoViaCam;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getObsoleto() {
		return obsoleto;
	}
	public void setObsoleto(String obsoleto) {
		this.obsoleto = obsoleto;
	}
	
}

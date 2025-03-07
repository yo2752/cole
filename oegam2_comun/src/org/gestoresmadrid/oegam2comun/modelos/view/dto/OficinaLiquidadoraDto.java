package org.gestoresmadrid.oegam2comun.modelos.view.dto;

import java.io.Serializable;


public class OficinaLiquidadoraDto implements Serializable{
	
	private static final long serialVersionUID = 257052884809940802L;

	private String id;
	private String idProvincia;
	private String nombreOficinaLiq;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNombreOficinaLiq() {
		return nombreOficinaLiq;
	}
	
	public void setNombreOficinaLiq(String nombreOficinaLiq) {
		this.nombreOficinaLiq = nombreOficinaLiq;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

}

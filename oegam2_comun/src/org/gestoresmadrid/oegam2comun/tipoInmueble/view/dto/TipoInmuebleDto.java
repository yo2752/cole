package org.gestoresmadrid.oegam2comun.tipoInmueble.view.dto;

import java.io.Serializable;

public class TipoInmuebleDto implements Serializable{
	
	private static final long serialVersionUID = 755009723598949824L;
	
	private String idTipoBien;
	private String idTipoInmueble;
	private String descTipoInmueble;
	
	public String getIdTipoBien() {
		return idTipoBien;
	}
	public void setIdTipoBien(String idTipoBien) {
		this.idTipoBien = idTipoBien;
	}
	public String getIdTipoInmueble() {
		return idTipoInmueble;
	}
	public void setIdTipoInmueble(String idTipoInmueble) {
		this.idTipoInmueble = idTipoInmueble;
	}
	public String getDescTipoInmueble() {
		return descTipoInmueble;
	}
	public void setDescTipoInmueble(String descTipoInmueble) {
		this.descTipoInmueble = descTipoInmueble;
	}
	
	
	
}

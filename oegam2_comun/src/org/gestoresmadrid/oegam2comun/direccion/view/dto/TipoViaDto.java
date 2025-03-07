package org.gestoresmadrid.oegam2comun.direccion.view.dto;

import java.io.Serializable;

public class TipoViaDto implements Serializable {

	private static final long serialVersionUID = 1596358016406403518L;

	private String idTipoDgt;

	private String idTipoVia;

	private String idTipoViaDgt;

	private String nombre;

	private String obsoleto;

	public String getIdTipoDgt() {
		return idTipoDgt;
	}

	public void setIdTipoDgt(String idTipoDgt) {
		this.idTipoDgt = idTipoDgt;
	}

	public String getIdTipoVia() {
		return idTipoVia;
	}

	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	public String getIdTipoViaDgt() {
		return idTipoViaDgt;
	}

	public void setIdTipoViaDgt(String idTipoViaDgt) {
		this.idTipoViaDgt = idTipoViaDgt;
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
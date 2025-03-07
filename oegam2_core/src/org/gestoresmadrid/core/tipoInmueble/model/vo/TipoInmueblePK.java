package org.gestoresmadrid.core.tipoInmueble.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TipoInmueblePK implements Serializable{

	private static final long serialVersionUID = 5325505273687243182L;

	@Column(name="TIPO_BIEN")
	private String idTipoBien;
	
	@Column(name="TIPO_INMUEBLE")
	private String idTipoInmueble;

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

}

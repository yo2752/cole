package org.gestoresmadrid.core.modelos.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OficinaLiquidadoraPK implements Serializable{

	private static final long serialVersionUID = -5297088599986086614L;

	@Column(name="OFICINA_LIQUIDADORA")
	private String oficinaLiquidadora;
	
	@Column(name = "ID_PROVINCIA")
	private String idProvincia;

	public String getOficinaLiquidadora() {
		return oficinaLiquidadora;
	}

	public void setOficinaLiquidadora(String oficinaLiquidadora) {
		this.oficinaLiquidadora = oficinaLiquidadora;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
	
}

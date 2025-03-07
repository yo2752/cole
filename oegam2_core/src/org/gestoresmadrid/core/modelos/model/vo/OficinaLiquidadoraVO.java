package org.gestoresmadrid.core.modelos.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OFICINA_LIQUIDADORA")
public class OficinaLiquidadoraVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OficinaLiquidadoraPK id;
	
	@Column(name="NOMBRE_OFICINA_LIQ")
	private String nombreOficinaLiq;
	
	
	public String getNombreOficinaLiq() {
		return nombreOficinaLiq;
	}

	public void setNombreOficinaLiq(String nombreOficinaLiq) {
		this.nombreOficinaLiq = nombreOficinaLiq;
	}

	public OficinaLiquidadoraPK getId() {
		return id;
	}

	public void setId(OficinaLiquidadoraPK id) {
		this.id = id;
	}

}

package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the OFICINA_LIQUIDADORA_620 database table.
 */
@Entity
@Table(name = "OFICINA_LIQUIDADORA_620")
public class OficinaLiquidadora620VO implements Serializable {

	private static final long serialVersionUID = -645845683398809294L;

	@EmbeddedId
	private OficinaLiquidadora620PK id;

	@Column(name = "NOMBRE_OFICINA_LIQ")
	private String nombreOficinaLiq;

	public OficinaLiquidadora620PK getId() {
		return id;
	}

	public void setId(OficinaLiquidadora620PK id) {
		this.id = id;
	}

	public String getNombreOficinaLiq() {
		return nombreOficinaLiq;
	}

	public void setNombreOficinaLiq(String nombreOficinaLiq) {
		this.nombreOficinaLiq = nombreOficinaLiq;
	}
}

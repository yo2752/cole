package org.gestoresmadrid.core.bien.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UNIDAD_METRICA")
public class UnidadMetricaVO implements Serializable{

	private static final long serialVersionUID = -949128658226798992L;

	@Id
	@Column(name="UNIDAD_METRICA")
	private String unidadMetrica;
	
	@Column(name="DEC_UNIDAD")
	private String descunidad;

	public String getUnidadMetrica() {
		return unidadMetrica;
	}

	public void setUnidadMetrica(String unidadMetrica) {
		this.unidadMetrica = unidadMetrica;
	}

	public String getDescunidad() {
		return descunidad;
	}

	public void setDescunidad(String descunidad) {
		this.descunidad = descunidad;
	}
	
	
}

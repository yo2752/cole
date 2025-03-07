package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the TIPO_VEHICULO_CRITERIOS database table.
 */
@Entity
@Table(name = "TIPO_VEHICULO_CRITERIOS")
public class TipoVehiculoCriteriosVO implements Serializable {

	private static final long serialVersionUID = 4730333436142232750L;

	@EmbeddedId
	private TipoVehiculoCriteriosPK id;

	private String servicio;

	@Column(name = "MMA_MAX")
	private BigDecimal mmaMax;

	@Column(name = "MMA_MIN")
	private BigDecimal mmaMin;

	private BigDecimal ruedas;

	public TipoVehiculoCriteriosVO() {}

	public TipoVehiculoCriteriosPK getId() {
		return id;
	}

	public void setId(TipoVehiculoCriteriosPK id) {
		this.id = id;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public BigDecimal getMmaMax() {
		return mmaMax;
	}

	public void setMmaMax(BigDecimal mmaMax) {
		this.mmaMax = mmaMax;
	}

	public BigDecimal getMmaMin() {
		return mmaMin;
	}

	public void setMmaMin(BigDecimal mmaMin) {
		this.mmaMin = mmaMin;
	}

	public BigDecimal getRuedas() {
		return ruedas;
	}

	public void setRuedas(BigDecimal ruedas) {
		this.ruedas = ruedas;
	}
}
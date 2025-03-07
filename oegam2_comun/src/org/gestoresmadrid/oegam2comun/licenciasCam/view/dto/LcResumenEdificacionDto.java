package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class LcResumenEdificacionDto implements Serializable {

	private static final long serialVersionUID = 1466799141667893229L;

	private Long idResumenEdificacion;

	private String tipoResumenEdificacion;

	private BigDecimal numUnidadesBajoRasante;

	private BigDecimal supComputableBajoRasante;

	private BigDecimal supConstruidaBajoRasante;

	private BigDecimal numUnidadesSobreRasante;

	private BigDecimal supComputableSobreRasante;

	private BigDecimal supConstruidaSobreRasante;

	private Long idEdificacion;

	public LcResumenEdificacionDto() {}

	public Long getIdResumenEdificacion() {
		return idResumenEdificacion;
	}

	public void setIdResumenEdificacion(Long idResumenEdificacion) {
		this.idResumenEdificacion = idResumenEdificacion;
	}

	public String getTipoResumenEdificacion() {
		return tipoResumenEdificacion;
	}

	public void setTipoResumenEdificacion(String tipoResumenEdificacion) {
		this.tipoResumenEdificacion = tipoResumenEdificacion;
	}

	public BigDecimal getNumUnidadesBajoRasante() {
		return numUnidadesBajoRasante;
	}

	public void setNumUnidadesBajoRasante(BigDecimal numUnidadesBajoRasante) {
		this.numUnidadesBajoRasante = numUnidadesBajoRasante;
	}

	public BigDecimal getSupComputableBajoRasante() {
		return supComputableBajoRasante;
	}

	public void setSupComputableBajoRasante(BigDecimal supComputableBajoRasante) {
		this.supComputableBajoRasante = supComputableBajoRasante;
	}

	public BigDecimal getSupConstruidaBajoRasante() {
		return supConstruidaBajoRasante;
	}

	public void setSupConstruidaBajoRasante(BigDecimal supConstruidaBajoRasante) {
		this.supConstruidaBajoRasante = supConstruidaBajoRasante;
	}

	public BigDecimal getNumUnidadesSobreRasante() {
		return numUnidadesSobreRasante;
	}

	public void setNumUnidadesSobreRasante(BigDecimal numUnidadesSobreRasante) {
		this.numUnidadesSobreRasante = numUnidadesSobreRasante;
	}

	public BigDecimal getSupComputableSobreRasante() {
		return supComputableSobreRasante;
	}

	public void setSupComputableSobreRasante(BigDecimal supComputableSobreRasante) {
		this.supComputableSobreRasante = supComputableSobreRasante;
	}

	public BigDecimal getSupConstruidaSobreRasante() {
		return supConstruidaSobreRasante;
	}

	public void setSupConstruidaSobreRasante(BigDecimal supConstruidaSobreRasante) {
		this.supConstruidaSobreRasante = supConstruidaSobreRasante;
	}

	public Long getIdEdificacion() {
		return idEdificacion;
	}

	public void setIdEdificacion(Long idEdificacion) {
		this.idEdificacion = idEdificacion;
	}

}
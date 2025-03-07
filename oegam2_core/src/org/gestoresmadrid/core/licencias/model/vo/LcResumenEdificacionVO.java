package org.gestoresmadrid.core.licencias.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the LC_RESUMEN_EDIFICACION database table.
 */
@Entity
@Table(name = "LC_RESUMEN_EDIFICACION")
public class LcResumenEdificacionVO implements Serializable {

	private static final long serialVersionUID = 4668504085605135790L;

	@Id
	@SequenceGenerator(name = "lc_resumen_edificacion", sequenceName = "LC_RESUMEN_EDIFICACION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_resumen_edificacion")
	@Column(name = "ID_RESUMEN_EDIFICACION")
	private Long idResumenEdificacion;

	@Column(name = "TIPO_RESUMEN_EDIFICACION")
	private String tipoResumenEdificacion;

	@Column(name = "NUM_UNIDADES_BAJO_RASANTE")
	private BigDecimal numUnidadesBajoRasante;

	@Column(name = "SUP_COMPUTABLE_BAJO_RASANTE")
	private BigDecimal supComputableBajoRasante;

	@Column(name = "SUP_CONSTRUIDA_BAJO_RASANTE")
	private BigDecimal supConstruidaBajoRasante;

	@Column(name = "NUM_UNIDADES_SOBRE_RASANTE")
	private BigDecimal numUnidadesSobreRasante;

	@Column(name = "SUP_COMPUTABLE_SOBRE_RASANTE")
	private BigDecimal supComputableSobreRasante;

	@Column(name = "SUP_CONSTRUIDA_SOBRE_RASANTE")
	private BigDecimal supConstruidaSobreRasante;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EDIFICACION", referencedColumnName = "ID_EDIFICACION", insertable = false, updatable = false)
	private LcEdificacionVO lcEdificacion;

	@Column(name = "ID_EDIFICACION")
	private Long idEdificacion;

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

	public LcEdificacionVO getLcEdificacion() {
		return lcEdificacion;
	}

	public void setLcEdificacion(LcEdificacionVO lcEdificacion) {
		this.lcEdificacion = lcEdificacion;
	}

}
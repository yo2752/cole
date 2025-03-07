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
 * The persistent class for the LC_SUP_NO_COMPUTABLE_PLANTA database table.
 */
@Entity
@Table(name = "LC_SUP_NO_COMPUTABLE_PLANTA")
public class LcSupNoComputablePlantaVO implements Serializable {

	private static final long serialVersionUID = 4719207199636295800L;

	@Id
	@SequenceGenerator(name = "lc_sup_no_comp_planta", sequenceName = "LC_SUP_NO_COMP_PLANTA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_sup_no_comp_planta")
	@Column(name = "ID_SUP_NO_COMPUTABLE_PLANTA")
	private Long idSupNoComputablePlanta;

	@Column(name="TAMANIO")
	private BigDecimal tamanio;

	@Column(name="TIPO")
	private BigDecimal tipo;

	@Column(name = "ID_DATOS_PLANTA_ALTA")
	private Long idDatosPlantaAlta;

	// bi-directional many-to-one association to LcDatosPlantaAlta
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DATOS_PLANTA_ALTA", referencedColumnName = "ID_DATOS_PLANTA_ALTA", insertable = false, updatable = false)
	private LcDatosPlantaAltaVO lcDatosPlantaAlta;

	public Long getIdSupNoComputablePlanta() {
		return this.idSupNoComputablePlanta;
	}

	public void setIdSupNoComputablePlanta(Long idSupNoComputablePlanta) {
		this.idSupNoComputablePlanta = idSupNoComputablePlanta;
	}

	public BigDecimal getTamanio() {
		return this.tamanio;
	}

	public void setTamanio(BigDecimal tamanio) {
		this.tamanio = tamanio;
	}

	public BigDecimal getTipo() {
		return this.tipo;
	}

	public void setTipo(BigDecimal tipo) {
		this.tipo = tipo;
	}

	public Long getIdDatosPlantaAlta() {
		return idDatosPlantaAlta;
	}

	public void setIdDatosPlantaAlta(Long idDatosPlantaAlta) {
		this.idDatosPlantaAlta = idDatosPlantaAlta;
	}

	public LcDatosPlantaAltaVO getLcDatosPlantaAlta() {
		return lcDatosPlantaAlta;
	}

	public void setLcDatosPlantaAlta(LcDatosPlantaAltaVO lcDatosPlantaAlta) {
		this.lcDatosPlantaAlta = lcDatosPlantaAlta;
	}

}
package org.gestoresmadrid.core.licencias.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the LC_DATOS_PORTAL_ALTA database table.
 */
@Entity
@Table(name = "LC_DATOS_PORTAL_ALTA")
public class LcDatosPortalAltaVO implements Serializable {

	private static final long serialVersionUID = 639801289039667137L;

	@Id
	@SequenceGenerator(name = "lc_datos_portal_alta", sequenceName = "LC_DATOS_PORTAL_ALTA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_datos_portal_alta")
	@Column(name = "ID_DATOS_PORTAL_ALTA")
	private Long idDatosPortalAlta;

	@Column(name = "NOMBRE_PORTAL")
	private String nombrePortal;

	@Column(name = "SUPERFICIE_COMPTB_BJ_RST")
	private BigDecimal superficieComputableBjRasante;

	@Column(name = "SUPERFICIE_CONSTRUIDA_BJ_RST")
	private BigDecimal superficieConstruidaBjRasante;
	
	@Column(name = "UNIDADES_BJ_RST")
	private BigDecimal unidadesBjRasante;

	@Column(name = "SUPERFICIE_COMPTB_SB_RST")
	private BigDecimal superficieComputableSbRasante;

	@Column(name = "SUPERFICIE_CONSTRUIDA_SB_RST")
	private BigDecimal superficieConstruidaSbRasante;
	
	@Column(name = "UNIDADES_SB_RST")
	private BigDecimal unidadesSbRasante;

	@OneToMany(mappedBy = "lcDatosPortalAlta")
	private Set<LcDatosPlantaAltaVO> lcDatosPlantasAlta;

	@Column(name = "ID_INFO_EDIF_ALTA")
	private Long idInfoEdificioAlta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INFO_EDIF_ALTA", referencedColumnName = "ID_INFO_EDIFICIO_ALTA", insertable = false, updatable = false)
	private LcInfoEdificioAltaVO lcInfoEdificioAlta;

	public List<LcDatosPlantaAltaVO> getLcDatosPlantasAltaAsList() {
		List<LcDatosPlantaAltaVO> lista;
		if (lcDatosPlantasAlta != null && lcDatosPlantasAlta.isEmpty()) {
			lista = new ArrayList<LcDatosPlantaAltaVO>(lcDatosPlantasAlta);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}
	
	public Long getIdDatosPortalAlta() {
		return this.idDatosPortalAlta;
	}

	public void setIdDatosPortalAlta(Long idDatosPortalAlta) {
		this.idDatosPortalAlta = idDatosPortalAlta;
	}

	public Long getIdInfoEdificioAlta() {
		return idInfoEdificioAlta;
	}

	public void setIdInfoEdificioAlta(Long idInfoEdificioAlta) {
		this.idInfoEdificioAlta = idInfoEdificioAlta;
	}

	public LcInfoEdificioAltaVO getLcInfoEdificioAlta() {
		return lcInfoEdificioAlta;
	}

	public void setLcInfoEdificioAlta(LcInfoEdificioAltaVO lcInfoEdificioAlta) {
		this.lcInfoEdificioAlta = lcInfoEdificioAlta;
	}

	public String getNombrePortal() {
		return nombrePortal;
	}

	public void setNombrePortal(String nombrePortal) {
		this.nombrePortal = nombrePortal;
	}

	public Set<LcDatosPlantaAltaVO> getLcDatosPlantasAlta() {
		return lcDatosPlantasAlta;
	}

	public void setLcDatosPlantasAlta(Set<LcDatosPlantaAltaVO> lcDatosPlantasAlta) {
		this.lcDatosPlantasAlta = lcDatosPlantasAlta;
	}

	public BigDecimal getSuperficieComputableBjRasante() {
		return superficieComputableBjRasante;
	}

	public void setSuperficieComputableBjRasante(BigDecimal superficieComputableBjRasante) {
		this.superficieComputableBjRasante = superficieComputableBjRasante;
	}

	public BigDecimal getSuperficieConstruidaBjRasante() {
		return superficieConstruidaBjRasante;
	}

	public void setSuperficieConstruidaBjRasante(BigDecimal superficieConstruidaBjRasante) {
		this.superficieConstruidaBjRasante = superficieConstruidaBjRasante;
	}

	public BigDecimal getUnidadesBjRasante() {
		return unidadesBjRasante;
	}

	public void setUnidadesBjRasante(BigDecimal unidadesBjRasante) {
		this.unidadesBjRasante = unidadesBjRasante;
	}

	public BigDecimal getSuperficieComputableSbRasante() {
		return superficieComputableSbRasante;
	}

	public void setSuperficieComputableSbRasante(BigDecimal superficieComputableSbRasante) {
		this.superficieComputableSbRasante = superficieComputableSbRasante;
	}

	public BigDecimal getSuperficieConstruidaSbRasante() {
		return superficieConstruidaSbRasante;
	}

	public void setSuperficieConstruidaSbRasante(BigDecimal superficieConstruidaSbRasante) {
		this.superficieConstruidaSbRasante = superficieConstruidaSbRasante;
	}

	public BigDecimal getUnidadesSbRasante() {
		return unidadesSbRasante;
	}

	public void setUnidadesSbRasante(BigDecimal unidadesSbRasante) {
		this.unidadesSbRasante = unidadesSbRasante;
	}

}
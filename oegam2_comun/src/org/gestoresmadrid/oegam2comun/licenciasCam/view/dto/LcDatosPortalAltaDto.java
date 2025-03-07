package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class LcDatosPortalAltaDto implements Serializable {

	private static final long serialVersionUID = -4520862543538501503L;

	private Long idDatosPortalAlta;

	private String nombrePortal;

	private BigDecimal superficieComputableBjRasante;

	private BigDecimal superficieConstruidaBjRasante;

	private BigDecimal unidadesBjRasante;

	private BigDecimal superficieComputableSbRasante;

	private BigDecimal superficieConstruidaSbRasante;

	private BigDecimal unidadesSbRasante;

	private List<LcDatosPlantaAltaDto> lcDatosPlantasAlta;

	private Long idInfoEdificioAlta;

	// Objeto de pantalla
	private LcDatosPlantaAltaDto lcDatosPlantaAlta;

	public LcDatosPortalAltaDto() {}

	public Long getIdDatosPortalAlta() {
		return idDatosPortalAlta;
	}

	public void setIdDatosPortalAlta(Long idDatosPortalAlta) {
		this.idDatosPortalAlta = idDatosPortalAlta;
	}

	public String getNombrePortal() {
		return nombrePortal;
	}

	public void setNombrePortal(String nombrePortal) {
		this.nombrePortal = nombrePortal;
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

	public List<LcDatosPlantaAltaDto> getLcDatosPlantasAlta() {
		return lcDatosPlantasAlta;
	}

	public void setLcDatosPlantasAlta(List<LcDatosPlantaAltaDto> lcDatosPlantasAlta) {
		this.lcDatosPlantasAlta = lcDatosPlantasAlta;
	}

	public Long getIdInfoEdificioAlta() {
		return idInfoEdificioAlta;
	}

	public void setIdInfoEdificioAlta(Long idInfoEdificioAlta) {
		this.idInfoEdificioAlta = idInfoEdificioAlta;
	}

	public LcDatosPlantaAltaDto getLcDatosPlantaAlta() {
		return lcDatosPlantaAlta;
	}

	public void setLcDatosPlantaAlta(LcDatosPlantaAltaDto lcDatosPlantaAlta) {
		this.lcDatosPlantaAlta = lcDatosPlantaAlta;
	}

}
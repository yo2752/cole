package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class LcDatosPlantaAltaDto implements Serializable {

	private static final long serialVersionUID = 8501716796192491288L;

	private Long idDatosPlantaAlta;

	private BigDecimal alturaLibre;

	private BigDecimal alturaPiso;

	private String numPlanta;

	private BigDecimal numUnidades;

	private BigDecimal supComputable;

	private BigDecimal supConstruida;

	private String tipoPlanta;

	private String usoPlanta;

	private List<LcSupNoComputablePlantaDto> lcSupNoComputablesPlanta;

	private Long idDatosPortalAlta;

	// Dato de pantalla
	private LcSupNoComputablePlantaDto lcSupNoComputablePlanta;

	public LcDatosPlantaAltaDto() {}

	public Long getIdDatosPlantaAlta() {
		return this.idDatosPlantaAlta;
	}

	public void setIdDatosPlantaAlta(Long idDatosPlantaAlta) {
		this.idDatosPlantaAlta = idDatosPlantaAlta;
	}

	public BigDecimal getAlturaLibre() {
		return this.alturaLibre;
	}

	public void setAlturaLibre(BigDecimal alturaLibre) {
		this.alturaLibre = alturaLibre;
	}

	public BigDecimal getAlturaPiso() {
		return this.alturaPiso;
	}

	public void setAlturaPiso(BigDecimal alturaPiso) {
		this.alturaPiso = alturaPiso;
	}

	public BigDecimal getNumUnidades() {
		return this.numUnidades;
	}

	public void setNumUnidades(BigDecimal numUnidades) {
		this.numUnidades = numUnidades;
	}

	public BigDecimal getSupComputable() {
		return this.supComputable;
	}

	public void setSupComputable(BigDecimal supComputable) {
		this.supComputable = supComputable;
	}

	public BigDecimal getSupConstruida() {
		return this.supConstruida;
	}

	public void setSupConstruida(BigDecimal supConstruida) {
		this.supConstruida = supConstruida;
	}

	public String getTipoPlanta() {
		return this.tipoPlanta;
	}

	public void setTipoPlanta(String tipoPlanta) {
		this.tipoPlanta = tipoPlanta;
	}

	public String getUsoPlanta() {
		return this.usoPlanta;
	}

	public void setUsoPlanta(String usoPlanta) {
		this.usoPlanta = usoPlanta;
	}

	public List<LcSupNoComputablePlantaDto> getLcSupNoComputablesPlanta() {
		return lcSupNoComputablesPlanta;
	}

	public void setLcSupNoComputablesPlanta(List<LcSupNoComputablePlantaDto> lcSupNoComputablesPlanta) {
		this.lcSupNoComputablesPlanta = lcSupNoComputablesPlanta;
	}

	public void setLcSupNoComputablePlanta(LcSupNoComputablePlantaDto lcSupNoComputablePlanta) {
		this.lcSupNoComputablePlanta = lcSupNoComputablePlanta;
	}

	public Long getIdDatosPortalAlta() {
		return idDatosPortalAlta;
	}

	public void setIdDatosPortalAlta(Long idDatosPortalAlta) {
		this.idDatosPortalAlta = idDatosPortalAlta;
	}

	public LcSupNoComputablePlantaDto getLcSupNoComputablePlanta() {
		return lcSupNoComputablePlanta;
	}

	public String getNumPlanta() {
		return numPlanta;
	}

	public void setNumPlanta(String numPlanta) {
		this.numPlanta = numPlanta;
	}

}
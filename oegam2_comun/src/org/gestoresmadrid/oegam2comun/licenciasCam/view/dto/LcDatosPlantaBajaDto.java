package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class LcDatosPlantaBajaDto implements Serializable {

	private static final long serialVersionUID = 4786331439027431331L;

	private Long idDatosPlantaBaja;

	private BigDecimal supConstruida;

	private String tipoPlanta;

	private Long idInfoEdificioBaja;

	public LcDatosPlantaBajaDto() {}

	public Long getIdDatosPlantaBaja() {
		return this.idDatosPlantaBaja;
	}

	public void setIdDatosPlantaBaja(Long idDatosPlantaBaja) {
		this.idDatosPlantaBaja = idDatosPlantaBaja;
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

	public Long getIdInfoEdificioBaja() {
		return idInfoEdificioBaja;
	}

	public void setIdInfoEdificioBaja(Long idInfoEdificioBaja) {
		this.idInfoEdificioBaja = idInfoEdificioBaja;
	}

}
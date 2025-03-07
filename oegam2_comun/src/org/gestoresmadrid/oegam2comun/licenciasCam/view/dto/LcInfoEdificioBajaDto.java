package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class LcInfoEdificioBajaDto implements Serializable {

	private static final long serialVersionUID = 7029414445875780392L;

	private Long idInfoEdificioBaja;

	private BigDecimal numEdificio;

	private Long idLcDirEdificacion;

	private LcDireccionDto lcDirEdificacion;

	private Long idEdificacion;

	private List<LcDatosPlantaBajaDto> lcDatosPlantasBaja;

	// Objetos de pantalla
	private LcDatosPlantaBajaDto lcDatosPlantaBaja;

	public LcInfoEdificioBajaDto() {}

	public Long getIdInfoEdificioBaja() {
		return idInfoEdificioBaja;
	}

	public void setIdInfoEdificioBaja(Long idInfoEdificioBaja) {
		this.idInfoEdificioBaja = idInfoEdificioBaja;
	}

	public BigDecimal getNumEdificio() {
		return numEdificio;
	}

	public void setNumEdificio(BigDecimal numEdificio) {
		this.numEdificio = numEdificio;
	}

	public Long getIdLcDirEdificacion() {
		return idLcDirEdificacion;
	}

	public void setIdLcDirEdificacion(Long idLcDirEdificacion) {
		this.idLcDirEdificacion = idLcDirEdificacion;
	}

	public LcDireccionDto getLcDirEdificacion() {
		return lcDirEdificacion;
	}

	public void setLcDirEdificacion(LcDireccionDto lcDirEdificacion) {
		this.lcDirEdificacion = lcDirEdificacion;
	}

	public Long getIdEdificacion() {
		return idEdificacion;
	}

	public void setIdEdificacion(Long idEdificacion) {
		this.idEdificacion = idEdificacion;
	}

	public List<LcDatosPlantaBajaDto> getLcDatosPlantasBaja() {
		return lcDatosPlantasBaja;
	}

	public void setLcDatosPlantasBaja(List<LcDatosPlantaBajaDto> lcDatosPlantasBaja) {
		this.lcDatosPlantasBaja = lcDatosPlantasBaja;
	}

	public LcDatosPlantaBajaDto getLcDatosPlantaBaja() {
		return lcDatosPlantaBaja;
	}

	public void setLcDatosPlantaBaja(LcDatosPlantaBajaDto lcDatosPlantaBaja) {
		this.lcDatosPlantaBaja = lcDatosPlantaBaja;
	}

}
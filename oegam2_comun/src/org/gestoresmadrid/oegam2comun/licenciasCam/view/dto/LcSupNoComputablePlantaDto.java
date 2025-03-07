package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class LcSupNoComputablePlantaDto implements Serializable {

	private static final long serialVersionUID = 4228902842192900876L;

	private Long idSupNoComputablePlanta;

	private BigDecimal tamanio;

	private BigDecimal tipo;

	private Long idDatosPlantaAlta;

	public LcSupNoComputablePlantaDto() {}

	public Long getIdSupNoComputablePlanta() {
		return idSupNoComputablePlanta;
	}

	public void setIdSupNoComputablePlanta(Long idSupNoComputablePlanta) {
		this.idSupNoComputablePlanta = idSupNoComputablePlanta;
	}

	public BigDecimal getTamanio() {
		return tamanio;
	}

	public void setTamanio(BigDecimal tamanio) {
		this.tamanio = tamanio;
	}

	public BigDecimal getTipo() {
		return tipo;
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

}
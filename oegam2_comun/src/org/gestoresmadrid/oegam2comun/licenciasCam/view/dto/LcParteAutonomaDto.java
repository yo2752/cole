package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class LcParteAutonomaDto implements Serializable {

	private static final long serialVersionUID = -2306285334378785326L;

	private Long idParteAutonoma;

	private String descripcion;

	private BigDecimal numero;

	private Long idDatosObra;

	public LcParteAutonomaDto() {}

	public Long getIdParteAutonoma() {
		return this.idParteAutonoma;
	}

	public void setIdParteAutonoma(Long idParteAutonoma) {
		this.idParteAutonoma = idParteAutonoma;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getNumero() {
		return this.numero;
	}

	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}

	public Long getIdDatosObra() {
		return idDatosObra;
	}

	public void setIdDatosObra(Long idDatosObra) {
		this.idDatosObra = idDatosObra;
	}

}
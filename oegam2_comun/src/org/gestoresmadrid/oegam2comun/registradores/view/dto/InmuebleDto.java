package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;

public class InmuebleDto implements Serializable {

	private static final long serialVersionUID = -8312606412318949738L;

	private Long idInmueble;

	private Long idBien;

	private BienDto bien;

	private BigDecimal idTramiteRegistro;

	// Para el DPR
	private String inmatriculada;

	public Long getIdInmueble() {
		return idInmueble;
	}

	public void setIdInmueble(Long idInmueble) {
		this.idInmueble = idInmueble;
	}

	public Long getIdBien() {
		return idBien;
	}

	public void setIdBien(Long idBien) {
		this.idBien = idBien;
	}

	public BienDto getBien() {
		return bien;
	}

	public void setBien(BienDto bien) {
		this.bien = bien;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public String getInmatriculada() {
		return inmatriculada;
	}

	public void setInmatriculada(String inmatriculada) {
		this.inmatriculada = inmatriculada;
	}

}
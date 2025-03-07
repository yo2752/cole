package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConvocatoriaDto implements Serializable {

	private static final long serialVersionUID = -1300505952607386734L;

	private BigDecimal idTramiteRegistro;

	private Long idReunion;

	private String numeroConvo;
	
	private Long porcentajeCapital;

	private Long porcentajeSocios;
	
	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public Long getIdReunion() {
		return idReunion;
	}

	public void setIdReunion(Long idReunion) {
		this.idReunion = idReunion;
	}
	
	public String getNumeroConvo() {
		return numeroConvo;
	}

	public void setNumeroConvo(String numeroConvo) {
		this.numeroConvo = numeroConvo;
	}

	public Long getPorcentajeCapital() {
		return porcentajeCapital;
	}

	public void setPorcentajeCapital(Long porcentajeCapital) {
		this.porcentajeCapital = porcentajeCapital;
	}

	public Long getPorcentajeSocios() {
		return porcentajeSocios;
	}

	public void setPorcentajeSocios(Long porcentajeSocios) {
		this.porcentajeSocios = porcentajeSocios;
	}
}
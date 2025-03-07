package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DatosNcapAtex5Dto implements Serializable{

	private static final long serialVersionUID = 3463026148719898889L;
	
	private String anioEnsayo;
	private BigDecimal adultos;
	private BigDecimal menores;
	private BigDecimal peatones;
	private BigDecimal seguridad;
	private BigDecimal global;
	
	public String getAnioEnsayo() {
		return anioEnsayo;
	}
	public void setAnioEnsayo(String anioEnsayo) {
		this.anioEnsayo = anioEnsayo;
	}
	public BigDecimal getAdultos() {
		return adultos;
	}
	public void setAdultos(BigDecimal adultos) {
		this.adultos = adultos;
	}
	public BigDecimal getMenores() {
		return menores;
	}
	public void setMenores(BigDecimal menores) {
		this.menores = menores;
	}
	public BigDecimal getPeatones() {
		return peatones;
	}
	public void setPeatones(BigDecimal peatones) {
		this.peatones = peatones;
	}
	public BigDecimal getSeguridad() {
		return seguridad;
	}
	public void setSeguridad(BigDecimal seguridad) {
		this.seguridad = seguridad;
	}
	public BigDecimal getGlobal() {
		return global;
	}
	public void setGlobal(BigDecimal global) {
		this.global = global;
	}
	
}

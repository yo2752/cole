package org.gestoresmadrid.oegamInteve.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import utilidades.estructuras.Fecha;

public class ConsultaTramiteInteveBean implements Serializable {

	private static final long serialVersionUID = -1470164419093240318L;

	BigDecimal numExpediente;
	String descContrato;
	String estado;
	String matriculas;
	String bastidores;
	String nives;
	Fecha fechaPresentacion;

	public void addMatricula(String matricula) {
		if (matriculas == null || matriculas.isEmpty()) {
			matriculas = matricula;
		} else {
			matriculas += ", " + matricula;
		}
	}

	public void addBastidor(String bastidor) {
		if (bastidores == null || bastidores.isEmpty()) {
			bastidores = bastidor;
		} else {
			bastidores += ", " + bastidor;
		}
	}

	public void addNive(String nive) {
		if (nives == null || nives.isEmpty()) {
			nives = nive;
		} else {
			nives += ", " + nive;
		}
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getDescContrato() {
		return descContrato;
	}

	public void setDescContrato(String descContrato) {
		this.descContrato = descContrato;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(String matriculas) {
		this.matriculas = matriculas;
	}

	public String getBastidores() {
		return bastidores;
	}

	public void setBastidores(String bastidores) {
		this.bastidores = bastidores;
	}

	public String getNives() {
		return nives;
	}

	public void setNives(String nives) {
		this.nives = nives;
	}

	public Fecha getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Fecha fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

}
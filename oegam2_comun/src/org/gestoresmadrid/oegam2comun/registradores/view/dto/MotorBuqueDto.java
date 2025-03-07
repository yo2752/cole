package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;


public class MotorBuqueDto implements Serializable {

	private static final long serialVersionUID = -8492872715207490200L;

	private long idMotor;

	private String anioConstruccion;

	private String marca;

	private String modelo;

	private String numSerie;

	private String potenciaCv;

	private String potenciaKw;

	private String tipo;

	private long idBuque;

	public MotorBuqueDto() {
	}

	public long getIdMotor() {
		return this.idMotor;
	}

	public void setIdMotor(long idMotor) {
		this.idMotor = idMotor;
	}

	public String getAnioConstruccion() {
		return this.anioConstruccion;
	}

	public void setAnioConstruccion(String anioConstruccion) {
		this.anioConstruccion = anioConstruccion;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNumSerie() {
		return this.numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getPotenciaCv() {
		return this.potenciaCv;
	}

	public void setPotenciaCv(String potenciaCv) {
		this.potenciaCv = potenciaCv;
	}

	public String getPotenciaKw() {
		return this.potenciaKw;
	}

	public void setPotenciaKw(String potenciaKw) {
		this.potenciaKw = potenciaKw;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public long getIdBuque() {
		return idBuque;
	}

	public void setIdBuque(long idBuque) {
		this.idBuque = idBuque;
	}


}
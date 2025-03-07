package org.gestoresmadrid.oegam2comun.distintivo.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;


import hibernate.entities.personas.Persona;

public class ConsultaDistintivoDgtBean implements Serializable{

	private static final long serialVersionUID = 6499006710785116880L;
	
	private BigDecimal numExpediente;
	private String distintivo;
	private String tipoDistintivo;
	private String descContrato;
	private String estadoPetImp;
	private String estadoDstv; 
	private String respPetPermDstv;
	private Persona titular;
	private String matricula;
	private String refPropia;
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getDistintivo() {
		return distintivo;
	}
	public void setDistintivo(String distintivo) {
		this.distintivo = distintivo;
	}
	public String getTipoDistintivo() {
		return tipoDistintivo;
	}
	public void setTipoDistintivo(String tipoDistintivo) {
		this.tipoDistintivo = tipoDistintivo;
	}
	public String getDescContrato() {
		return descContrato;
	}
	public void setDescContrato(String descContrato) {
		this.descContrato = descContrato;
	}
	public String getEstadoPetImp() {
		return estadoPetImp;
	}
	public void setEstadoPetImp(String estadoPetImp) {
		this.estadoPetImp = estadoPetImp;
	}
	public String getEstadoDstv() {
		return estadoDstv;
	}
	public void setEstadoDstv(String estadoDstv) {
		this.estadoDstv = estadoDstv;
	}
	public Persona getTitular() {
		return titular;
	}
	public void setTitular(Persona titular) {
		this.titular = titular;
	}
	public String getRespPetPermDstv() {
		return respPetPermDstv;
	}
	public void setRespPetPermDstv(String respPetPermDstv) {
		this.respPetPermDstv = respPetPermDstv;
	}
	public String getRefPropia() {
		return refPropia;
	}
	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}



	
}

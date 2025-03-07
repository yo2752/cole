package org.gestoresmadrid.oegam2comun.docPermDistItv.view.bean;

import java.io.Serializable;

public class TramitesPermDistItvBean implements Serializable{
	
	private static final long serialVersionUID = -8214017603704059917L;
	private int numero;
	private String matricula;
	private String numExpediente;
	private String fechaPresentacion;
	private String pc;
	private String eitv;
	private String tipoVehiculo;
	private String bastidor;
	private String primeraImpresion;
	
	public String getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getPc() {
		return pc;
	}
	public void setPc(String pc) {
		this.pc = pc;
	}
	public String getEitv() {
		return eitv;
	}
	public void setEitv(String eitv) {
		this.eitv = eitv;
	}
	public String getTipoVehiculo() {
		return tipoVehiculo;
	}
	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
	public String getBastidor() {
		return bastidor;
	}
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
	public String getPrimeraImpresion() {
		return primeraImpresion;
	}
	public void setPrimeraImpresion(String primeraImpresion) {
		this.primeraImpresion = primeraImpresion;
	}
}
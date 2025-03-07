package org.gestoresmadrid.oegam2comun.arrendatarios.view.beans;

import java.math.BigDecimal;

public class ConsultaArrendatarioBean {

	private String nif;
	private String doiVehiculo;
	private String matricula;
	private String bastidor;
	private BigDecimal numExpediente;
	private Long idContrato;
	private String estadotxt;
	private String operaciontxt;
	private String numRegistro;
	private String refPropia;

	public String getEstadotxt() {
		return estadotxt;
	}

	public void setEstadotxt(String estadotxt) {
		this.estadotxt = estadotxt;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getDoiVehiculo() {
		return doiVehiculo;
	}

	public void setDoiVehiculo(String doiVehiculo) {
		this.doiVehiculo = doiVehiculo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getOperaciontxt() {
		return operaciontxt;
	}

	public void setOperaciontxt(String operaciontxt) {
		this.operaciontxt = operaciontxt;
	}

	public String getNumRegistro() {
		return numRegistro;
	}

	public void setNumRegistro(String numRegistro) {
		this.numRegistro = numRegistro;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

}

package org.gestoresmadrid.oegamInteve.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class TramiteTraficoInterveFilterBean implements Serializable {

	private static final long serialVersionUID = 1656326551483767599L;

	@FilterSimpleExpression(name="idContrato")
	private Long idContrato;

	@FilterSimpleExpression(name="numExpediente")
	private BigDecimal numExpediente;

	@FilterSimpleExpression(name="matricula")
	private String matricula;

	@FilterSimpleExpression(name="bastidor")
	private String bastidor;

	@FilterSimpleExpression(name="nive")
	private String nive;

	@FilterSimpleExpression(name="codigoTasa")
	private String codigoTasa;

	@FilterSimpleExpression(name="tipoInforme")
	private String tipoInforme;

	@FilterSimpleExpression(name="estado")
	private String estado;

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
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

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getTipoInforme() {
		return tipoInforme;
	}

	public void setTipoInforme(String tipoInforme) {
		this.tipoInforme = tipoInforme;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
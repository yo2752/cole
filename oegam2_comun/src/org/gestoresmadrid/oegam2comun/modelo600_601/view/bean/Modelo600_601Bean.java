package org.gestoresmadrid.oegam2comun.modelo600_601.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class Modelo600_601Bean implements Serializable{

	private static final long serialVersionUID = -757479096247718056L;
	
	private Long idModelo;
	private BigDecimal numExpediente;
	private BigDecimal numExpedienteRemesa;
	private String modelo;
	private String concepto;
	private String descEstado;
	private String estado;
	private BigDecimal valorDeclarado;
	private String codNotario;
	private String referenciaPropia;
	
	public Long getIdModelo() {
		return idModelo;
	}
	public void setIdModelo(Long idModelo) {
		this.idModelo = idModelo;
	}
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getDescEstado() {
		return descEstado;
	}
	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public BigDecimal getValorDeclarado() {
		return valorDeclarado;
	}
	public void setValorDeclarado(BigDecimal valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}
	public String getCodNotario() {
		return codNotario;
	}
	public void setCodNotario(String codNotario) {
		this.codNotario = codNotario;
	}
	public BigDecimal getNumExpedienteRemesa() {
		return numExpedienteRemesa;
	}
	public void setNumExpedienteRemesa(BigDecimal numExpedienteRemesa) {
		this.numExpedienteRemesa = numExpedienteRemesa;
	}
	public String getReferenciaPropia() {
		return referenciaPropia;
	}
	public void setReferenciaPropia(String referenciaPropia) {
		this.referenciaPropia = referenciaPropia;
	}
	
}

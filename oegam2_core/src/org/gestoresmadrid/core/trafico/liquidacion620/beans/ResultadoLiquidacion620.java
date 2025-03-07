package org.gestoresmadrid.core.trafico.liquidacion620.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ResultadoLiquidacion620 implements Serializable {

	private static final long serialVersionUID = 4298551580504609526L;

	private String matricula;
	private String marca;
	private String modelo;
	private String estado;
	private BigDecimal valorDeclarado;
	private BigDecimal baseImponible;
	private BigDecimal cuotaTributaria;
	private BigDecimal valorCam;
	private BigDecimal baseImponibleCam;
	private BigDecimal cuotaCam;
	private BigDecimal numExpediente;
	private Date fechaPrimMat;
	private Date fechaDevengo;

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
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
	public BigDecimal getBaseImponible() {
		return baseImponible;
	}
	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}
	public BigDecimal getCuotaTributaria() {
		return cuotaTributaria;
	}
	public void setCuotaTributaria(BigDecimal cuotaTributaria) {
		this.cuotaTributaria = cuotaTributaria;
	}
	public BigDecimal getValorCam() {
		return valorCam;
	}
	public void setValorCam(BigDecimal valorCam) {
		this.valorCam = valorCam;
	}
	public BigDecimal getBaseImponibleCam() {
		return baseImponibleCam;
	}
	public void setBaseImponibleCam(BigDecimal baseImponibleCam) {
		this.baseImponibleCam = baseImponibleCam;
	}
	public BigDecimal getCuotaCam() {
		return cuotaCam;
	}
	public void setCuotaCam(BigDecimal cuotaCam) {
		this.cuotaCam = cuotaCam;
	}
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
	public Date getFechaDevengo() {
		return fechaDevengo;
	}
	public void setFechaDevengo(Date fechaDevengo) {
		this.fechaDevengo = fechaDevengo;
	}
	public Date getFechaPrimMat() {
		return fechaPrimMat;
	}
	public void setFechaPrimMat(Date fechaPrimMat) {
		this.fechaPrimMat = fechaPrimMat;
	}

}
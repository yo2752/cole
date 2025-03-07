package org.gestoresmadrid.oegam2comun.bien.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;

public class ModeloBienDto implements Serializable{

	private static final long serialVersionUID = 3739843524060293673L;
	
	private BienDto bien;
	private Modelo600_601Dto modelo600_601;
	private BigDecimal transmision;
	private BigDecimal valorDeclarado;
	private BigDecimal valorTasacion;
	
	public BienDto getBien() {
		return bien;
	}
	public void setBien(BienDto bien) {
		this.bien = bien;
	}
	public Modelo600_601Dto getModelo600_601() {
		return modelo600_601;
	}
	public void setModelo600_601(Modelo600_601Dto modelo600_601) {
		this.modelo600_601 = modelo600_601;
	}
	public BigDecimal getTransmision() {
		return transmision;
	}
	public void setTransmision(BigDecimal transmision) {
		this.transmision = transmision;
	}
	public BigDecimal getValorDeclarado() {
		return valorDeclarado;
	}
	public void setValorDeclarado(BigDecimal valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}
	/**
	 * @return the valorTasacion
	 */
	public BigDecimal getValorTasacion() {
		return valorTasacion;
	}
	/**
	 * @param valorTasacion the valorTasacion to set
	 */
	public void setValorTasacion(BigDecimal valorTasacion) {
		this.valorTasacion = valorTasacion;
	}
	
}

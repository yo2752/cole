package org.gestoresmadrid.oegam2comun.bien.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;

public class BienRemesaDto implements Serializable{
	
	private static final long serialVersionUID = -2813883176533676124L;
	
	private BienDto bien;
	private RemesaDto remesa;
	private BigDecimal transmision;
	private BigDecimal valorDeclarado;
	private BigDecimal valorTasacion;
	
	public BienDto getBien() {
		return bien;
	}
	
	public void setBien(BienDto bien) {
		this.bien = bien;
	}
	
	public RemesaDto getRemesa() {
		return remesa;
	}
	
	public void setRemesa(RemesaDto remesa) {
		this.remesa = remesa;
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

	public BigDecimal getValorTasacion() {
		return valorTasacion;
	}

	public void setValorTasacion(BigDecimal valorTasacion) {
		this.valorTasacion = valorTasacion;
	}

}

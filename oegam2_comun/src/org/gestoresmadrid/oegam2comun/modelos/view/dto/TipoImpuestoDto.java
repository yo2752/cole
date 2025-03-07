package org.gestoresmadrid.oegam2comun.modelos.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TipoImpuestoDto implements Serializable{

	private static final long serialVersionUID = 2996369489559576375L;
	
	private String tipoImpuesto;
	private BigDecimal monto;
	private String fundamentoExencion;
	private String descripcion;
	private String sujetoExento;
	private ModeloDto modelo;
	private ConceptoDto concepto;
	
	public String getTipoImpuesto() {
		return tipoImpuesto;
	}
	public void setTipoImpuesto(String tipoImpuesto) {
		this.tipoImpuesto = tipoImpuesto;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	public String getFundamentoExencion() {
		return fundamentoExencion;
	}
	public void setFundamentoExencion(String fundamentoExencion) {
		this.fundamentoExencion = fundamentoExencion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getSujetoExento() {
		return sujetoExento;
	}
	public void setSujetoExento(String sujetoExento) {
		this.sujetoExento = sujetoExento;
	}
	public ModeloDto getModelo() {
		return modelo;
	}
	public void setModelo(ModeloDto modelo) {
		this.modelo = modelo;
	}
	public ConceptoDto getConcepto() {
		return concepto;
	}
	public void setConcepto(ConceptoDto concepto) {
		this.concepto = concepto;
	}
	
}

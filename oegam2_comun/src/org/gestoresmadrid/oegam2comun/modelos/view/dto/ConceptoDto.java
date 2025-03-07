package org.gestoresmadrid.oegam2comun.modelos.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConceptoDto implements Serializable{
	
	private static final long serialVersionUID = 1608700505066582916L;
	
	private ModeloDto modelo;
	private String concepto;
	private String descConcepto;
	private Integer ninter;
	private String grupoValidacion;
	private BigDecimal estado;
	
	public ModeloDto getModelo() {
		return modelo;
	}
	public void setModelo(ModeloDto modelo) {
		this.modelo = modelo;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getDescConcepto() {
		return descConcepto;
	}
	public void setDescConcepto(String descConcepto) {
		this.descConcepto = descConcepto;
	}
	public Integer getNinter() {
		return ninter;
	}
	public void setNinter(Integer ninter) {
		this.ninter = ninter;
	}
	public String getGrupoValidacion() {
		return grupoValidacion;
	}
	public void setGrupoValidacion(String grupoValidacion) {
		this.grupoValidacion = grupoValidacion;
	}
	public BigDecimal getEstado() {
		return estado;
	}
	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}
	
}

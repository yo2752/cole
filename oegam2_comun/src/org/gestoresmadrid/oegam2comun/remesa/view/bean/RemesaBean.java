package org.gestoresmadrid.oegam2comun.remesa.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class RemesaBean implements Serializable{
	
	private static final long serialVersionUID = 4753041953113658203L;
	
	private Long idRemesa;
	private BigDecimal numExpediente;
	private String codNotario;
	private String modelo;
	private String concepto;
	private String descEstado;
	private String estado;
	private BigDecimal importeTotal;
	private String referenciaPropia;
	
	public Long getIdRemesa() {
		return idRemesa;
	}
	public void setIdRemesa(Long idRemesa) {
		this.idRemesa = idRemesa;
	}
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getCodNotario() {
		return codNotario;
	}
	public void setCodNotario(String codNotario) {
		this.codNotario = codNotario;
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
	public BigDecimal getImporteTotal() {
		return importeTotal;
	}
	public void setImporteTotal(BigDecimal importeTotal) {
		this.importeTotal = importeTotal;
	}
	public String getReferenciaPropia() {
		return referenciaPropia;
	}
	public void setReferenciaPropia(String referenciaPropia) {
		this.referenciaPropia = referenciaPropia;
	}
	
}

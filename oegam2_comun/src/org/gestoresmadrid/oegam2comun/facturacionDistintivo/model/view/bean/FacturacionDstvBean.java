package org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.bean;

import java.io.Serializable;

public class FacturacionDstvBean implements Serializable{

	private static final long serialVersionUID = 4827665394006817881L;

	private Long idFact;
	private String contrato;
	private String fecha;
	private String estado;
	private String docId;
	private String tipo;
	private String totalFacturado;
	private String totalIncidencia;
	private String totalInciDuplicado;
	private String totalFacturadoDup;
	
	
	public String getTotalFacturadoDup() {
		return totalFacturadoDup;
	}
	public void setTotalFacturadoDup(String totalFacturadoDup) {
		this.totalFacturadoDup = totalFacturadoDup;
	}
	public String getTotalFacturado() {
		return totalFacturado;
	}
	public void setTotalFacturado(String totalFacturado) {
		this.totalFacturado = totalFacturado;
	}
	public String getTotalIncidencia() {
		return totalIncidencia;
	}
	public void setTotalIncidencia(String totalIncidencia) {
		this.totalIncidencia = totalIncidencia;
	}
	public String getContrato() {
		return contrato;
	}
	public void setContrato(String contrato) {
		this.contrato = contrato;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTotalInciDuplicado() {
		return totalInciDuplicado;
	}
	public void setTotalInciDuplicado(String totalInciDuplicado) {
		this.totalInciDuplicado = totalInciDuplicado;
	}
	public Long getIdFact() {
		return idFact;
	}
	public void setIdFact(Long idFact) {
		this.idFact = idFact;
	}
}

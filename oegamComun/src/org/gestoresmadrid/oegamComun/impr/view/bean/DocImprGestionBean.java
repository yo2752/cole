package org.gestoresmadrid.oegamComun.impr.view.bean;

import java.io.Serializable;

public class DocImprGestionBean implements Serializable{

	private static final long serialVersionUID = -7817128334437349679L;
	
	Long id; 
	String tipoImpr;
	String carpeta;
	String estado;
	String fechaImpresion;
	String fechaAlta;
	String tipoTramite;
	String docId; 
	
	public String getTipoImpr() {
		return tipoImpr;
	}
	public void setTipoImpr(String tipoImpr) {
		this.tipoImpr = tipoImpr;
	}
	public String getCarpeta() {
		return carpeta;
	}
	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaImpresion() {
		return fechaImpresion;
	}
	public void setFechaImpresion(String fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}
	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	} 
	
}

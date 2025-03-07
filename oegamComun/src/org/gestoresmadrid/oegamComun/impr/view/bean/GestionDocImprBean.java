package org.gestoresmadrid.oegamComun.impr.view.bean;

import java.io.Serializable;

public class GestionDocImprBean implements Serializable{

	private static final long serialVersionUID = -2346285130742951857L;
	
	private Long id;
	private String docImpr;
	private String carpeta;
	private String tipoImpr;
	private String estado;
	private String fechaAlta;
	private String fechaImpresion;
	private String tipoTramite;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCarpeta() {
		return carpeta;
	}
	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}
	public String getTipoImpr() {
		return tipoImpr;
	}
	public void setTipoImpr(String tipoImpr) {
		this.tipoImpr = tipoImpr;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getFechaImpresion() {
		return fechaImpresion;
	}
	public void setFechaImpresion(String fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getDocImpr() {
		return docImpr;
	}
	public void setDocImpr(String docImpr) {
		this.docImpr = docImpr;
	}
}

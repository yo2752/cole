package org.gestoresmadrid.oegamComun.impr.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class GestionImprBean implements Serializable{

	private static final long serialVersionUID = 2018981384428893657L;

	private Long id;
	private BigDecimal numExpediente;
	private String matricula;
	private String bastidor;
	private String carpeta;
	private String tipoImpr;
	private String tipoTramite;
	private String estadoImpr;
	private String estadoSolicitud;
	private Long docId;
	private String fechaAlta;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getBastidor() {
		return bastidor;
	}
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
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
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getEstadoImpr() {
		return estadoImpr;
	}
	public void setEstadoImpr(String estadoImpr) {
		this.estadoImpr = estadoImpr;
	}
	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}
	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}
	public Long getDocId() {
		return docId;
	}
	public void setDocId(Long docId) {
		this.docId = docId;
	}
	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

}
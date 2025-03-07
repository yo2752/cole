package org.gestoresmadrid.oegamDocBase.view.bean;

import java.io.Serializable;

public class GestionDocBaseBean implements Serializable {

	private static final long serialVersionUID = 6787876224606966019L;

	private Long id;
	private String docId;
	private String descContrato;
	private String fechaPresentacion;
	private String estado;
	private String carpeta;
	private String motivo;

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

	public String getDescContrato() {
		return descContrato;
	}

	public void setDescContrato(String descContrato) {
		this.descContrato = descContrato;
	}

	public String getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}

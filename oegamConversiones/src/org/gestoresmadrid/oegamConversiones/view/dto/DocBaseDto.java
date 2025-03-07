package org.gestoresmadrid.oegamConversiones.view.dto;

import java.io.Serializable;

import utilidades.estructuras.Fecha;

public class DocBaseDto implements Serializable {

	private static final long serialVersionUID = 6506750311950173684L;

	private Long id;

	private String carpeta;

	private String docId;

	private String estado;

	private Fecha fechaCreacion;

	private Fecha fechaPresentacion;

	private String qrCode;

	private Boolean indDocYb;

	private String ordenDocbase;

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

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Fecha getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Fecha fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Fecha getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Fecha fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Boolean getIndDocYb() {
		return indDocYb;
	}

	public void setIndDocYb(Boolean indDocYb) {
		this.indDocYb = indDocYb;
	}

	public String getOrdenDocbase() {
		return ordenDocbase;
	}

	public void setOrdenDocbase(String ordenDocbase) {
		this.ordenDocbase = ordenDocbase;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

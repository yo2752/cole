package org.gestoresmadrid.oegam2comun.consultaDev.model.bean;

import java.io.Serializable;
import java.util.Date;

public class ConsultaDevBean implements Serializable{

	private static final long serialVersionUID = -1764789393100472934L;
	
	private String cif;
	private String estado;
	private String estadoCif;
	private Long idConsultaDev;
	private String numColegiado;
	private String descContrato;
	private Date fechaAlta;
	
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Long getIdConsultaDev() {
		return idConsultaDev;
	}
	public void setIdConsultaDev(Long idConsultaDev) {
		this.idConsultaDev = idConsultaDev;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getDescContrato() {
		return descContrato;
	}
	public void setDescContrato(String descContrato) {
		this.descContrato = descContrato;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getEstadoCif() {
		return estadoCif;
	}
	public void setEstadoCif(String estadoCif) {
		this.estadoCif = estadoCif;
	}
}

package org.gestoresmadrid.core.trafico.model.procedure.bean;

import java.io.Serializable;
import java.util.Date;

public class ValidacionTramite implements Serializable {

	private static final long serialVersionUID = 3573981217802663901L;

	private Long numExpediente;

	private Long estado;

	private Date fechaUltModif;

	private Long idUsuario;

	private Long idContrato;

	private String revisado;

	private String informacion;

	private String code;

	private String sqlerrm;

	public Long getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public Date getFechaUltModif() {
		return fechaUltModif;
	}

	public void setFechaUltModif(Date fechaUltModif) {
		this.fechaUltModif = fechaUltModif;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getRevisado() {
		return revisado;
	}

	public void setRevisado(String revisado) {
		this.revisado = revisado;
	}

	public String getInformacion() {
		return informacion;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSqlerrm() {
		return sqlerrm;
	}

	public void setSqlerrm(String sqlerrm) {
		this.sqlerrm = sqlerrm;
	}
}

package org.gestoresmadrid.oegam2comun.transporte.view.beans;

import java.io.Serializable;

public class NotificacionTransporteBean implements Serializable{

	private static final long serialVersionUID = 3766864347071487329L;

	private Long idNotificacionTransporte;
	
	private String estado;
	
	private String nifEmpresa;
	
	private String nombreEmpresa;
	
	private String contrato;
	
	public Long getIdNotificacionTransporte() {
		return idNotificacionTransporte;
	}

	public void setIdNotificacionTransporte(Long idNotificacionTransporte) {
		this.idNotificacionTransporte = idNotificacionTransporte;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNifEmpresa() {
		return nifEmpresa;
	}

	public void setNifEmpresa(String nifEmpresa) {
		this.nifEmpresa = nifEmpresa;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

}

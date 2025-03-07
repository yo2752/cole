package org.gestoresmadrid.oegam2comun.transporte.view.beans;

import java.io.Serializable;

public class PoderesTransporteBean implements Serializable{

	private static final long serialVersionUID = 3965710182835818063L;
	
	private Long idPoderTransporte;
	
	private String contrato;
	
	private String nifPoderdante;
	
	private String nifEmpresa;
	
	private String nombreEmpresa;
	
	private String poderdante;

	public Long getIdPoderTransporte() {
		return idPoderTransporte;
	}

	public void setIdPoderTransporte(Long idPoderTransporte) {
		this.idPoderTransporte = idPoderTransporte;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public String getNifPoderdante() {
		return nifPoderdante;
	}

	public void setNifPoderdante(String nifPoderdante) {
		this.nifPoderdante = nifPoderdante;
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

	public String getPoderdante() {
		return poderdante;
	}

	public void setPoderdante(String poderdante) {
		this.poderdante = poderdante;
	}
	
}

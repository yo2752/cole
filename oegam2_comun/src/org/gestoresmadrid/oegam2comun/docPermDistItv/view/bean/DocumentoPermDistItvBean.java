package org.gestoresmadrid.oegam2comun.docPermDistItv.view.bean;

import java.io.Serializable;
import java.util.List;


public class DocumentoPermDistItvBean implements Serializable{

	private static final long serialVersionUID = -4429196008324818687L;

	private String documento;
	private String fechaPresentacion;
	private String numColegiado;
	private String nifGestor;
	private String gestoria;
	private String jefatura;
	private String docId;
	private String tipoVehiculo;
	private String tipoTramite;
	
	public String getJefatura() {
		return jefatura;
	}
	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}
	private List<TramitesPermDistItvBean> tramites;

	public String getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getNifGestor() {
		return nifGestor;
	}
	public void setNifGestor(String nifGestor) {
		this.nifGestor = nifGestor;
	}
	public String getGestoria() {
		return gestoria;
	}
	public void setGestoria(String gestoria) {
		this.gestoria = gestoria;
	}
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public List<TramitesPermDistItvBean> getTramites() {
		return tramites;
	}
	public void setTramites(List<TramitesPermDistItvBean> tramites) {
		this.tramites = tramites;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getTipoVehiculo() {
		return tipoVehiculo;
	}
	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

}
package org.gestoresmadrid.oegamPermisoInternacional.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DocumentoTramitesPermisoInterBean implements Serializable{

	private static final long serialVersionUID = -5882386502687562589L;
	
	String documento;
	String fechaPresentacion;
	String nifGestor;
	String gestoria;
	String jefatura;
	List<TramitePermisoInternDocBean> tramites;
	
	public void addListaTramites(TramitePermisoInternDocBean tramite) {
		if (tramites == null || tramites.isEmpty()) {
			tramites = new ArrayList<>();
		}
		tramites.add(tramite);
	}
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
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
	public String getJefatura() {
		return jefatura;
	}
	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}
	public List<TramitePermisoInternDocBean> getTramites() {
		return tramites;
	}
	public void setTramites(List<TramitePermisoInternDocBean> tramites) {
		this.tramites = tramites;
	}
}
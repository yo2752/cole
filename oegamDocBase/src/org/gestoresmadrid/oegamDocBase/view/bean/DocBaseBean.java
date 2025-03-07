package org.gestoresmadrid.oegamDocBase.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DocBaseBean implements Serializable{
	
	private static final long serialVersionUID = -5871196799821228663L;
	
	private String docId;
	private String qrCode;
	private String carpeta;
	private String fechaCreacion;
	private String fechaPresentacion;
	private String numColegiado;
	private String nifGestor;
	private String gestoria;
	private List<TramiteBaseBean> tramites;
	
	public void addListaTramitesBaseBean(TramiteBaseBean tramiteBase){
		if(tramites == null){
			tramites = new ArrayList<TramiteBaseBean>();
		}
		tramites.add(tramiteBase);
	}
	
	public String getDocId() {
		return docId;
	}
	
	public void setDocId(String docId) {
		this.docId = docId;
	}
	
	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getCarpeta() {
		return carpeta;
	}
	
	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
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

	public List<TramiteBaseBean> getTramites() {
		return tramites;
	}

	public void setTramites(List<TramiteBaseBean> tramites) {
		this.tramites = tramites;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	

}
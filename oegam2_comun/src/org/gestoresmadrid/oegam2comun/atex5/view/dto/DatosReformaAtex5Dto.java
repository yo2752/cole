package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DatosReformaAtex5Dto implements Serializable{

	private static final long serialVersionUID = 4738479789879170138L;
	
	private String estacionReforma;
    private Date fechaReforma;
    private Date fechaValidez;
    private List<DatosMotivoReformaAtex5Dto> listaMotivosReforma;
    private String nuevoPermiso;
    private String provinciaReforma;
    
	public String getEstacionReforma() {
		return estacionReforma;
	}
	public void setEstacionReforma(String estacionReforma) {
		this.estacionReforma = estacionReforma;
	}
	public Date getFechaReforma() {
		return fechaReforma;
	}
	public void setFechaReforma(Date fechaReforma) {
		this.fechaReforma = fechaReforma;
	}
	public Date getFechaValidez() {
		return fechaValidez;
	}
	public void setFechaValidez(Date fechaValidez) {
		this.fechaValidez = fechaValidez;
	}
	public List<DatosMotivoReformaAtex5Dto> getListaMotivosReforma() {
		return listaMotivosReforma;
	}
	public void setListaMotivosReforma(List<DatosMotivoReformaAtex5Dto> listaMotivosReforma) {
		this.listaMotivosReforma = listaMotivosReforma;
	}
	public String getNuevoPermiso() {
		return nuevoPermiso;
	}
	public void setNuevoPermiso(String nuevoPermiso) {
		this.nuevoPermiso = nuevoPermiso;
	}
	public String getProvinciaReforma() {
		return provinciaReforma;
	}
	public void setProvinciaReforma(String provinciaReforma) {
		this.provinciaReforma = provinciaReforma;
	}
    
}

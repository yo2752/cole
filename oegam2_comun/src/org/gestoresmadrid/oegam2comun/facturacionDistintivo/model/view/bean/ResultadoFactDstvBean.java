package org.gestoresmadrid.oegam2comun.facturacionDistintivo.model.view.bean;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ResultadoFactDstvBean implements Serializable{

	private static final long serialVersionUID = -7660709237235638561L;

	private Boolean error;
	private String mensaje;
	private List<String> listaMensajes;
	private String nombreFichero;
	private Boolean esDescargable;
	private Date fechaGenExcel;
	private File fichero;
	
	public ResultadoFactDstvBean(Boolean error) {
		super();
		this.error = error;
		this.esDescargable = Boolean.FALSE;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public List<String> getListaMensajes() {
		return listaMensajes;
	}
	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}
	public String getNombreFichero() {
		return nombreFichero;
	}
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	public Boolean getEsDescargable() {
		return esDescargable;
	}
	public void setEsDescargable(Boolean esDescargable) {
		this.esDescargable = esDescargable;
	}
	public Date getFechaGenExcel() {
		return fechaGenExcel;
	}
	public void setFechaGenExcel(Date fechaGenExcel) {
		this.fechaGenExcel = fechaGenExcel;
	}
	public File getFichero() {
		return fichero;
	}
	public void setFichero(File fichero) {
		this.fichero = fichero;
	}
}

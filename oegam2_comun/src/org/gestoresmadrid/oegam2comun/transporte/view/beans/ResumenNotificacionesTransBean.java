package org.gestoresmadrid.oegam2comun.transporte.view.beans;

import java.io.Serializable;
import java.util.List;


public class ResumenNotificacionesTransBean implements Serializable{

	private static final long serialVersionUID = -1258028239368818747L;
	
	private Boolean esImprimir = Boolean.FALSE;
	private Boolean esRechazar = Boolean.FALSE;
	private Boolean existeFichero = Boolean.FALSE;
	private String nombreFichero;
	private Integer numOK;
	private Integer numError;
	private List<String> listaMensajesOk;
	private List<String> listaMensajesError;
	
	public Boolean getEsImprimir() {
		return esImprimir;
	}
	public void setEsImprimir(Boolean esImprimir) {
		this.esImprimir = esImprimir;
	}
	public Boolean getEsRechazar() {
		return esRechazar;
	}
	public void setEsRechazar(Boolean esRechazar) {
		this.esRechazar = esRechazar;
	}
	public Boolean getExisteFichero() {
		return existeFichero;
	}
	public void setExisteFichero(Boolean existeFichero) {
		this.existeFichero = existeFichero;
	}
	public String getNombreFichero() {
		return nombreFichero;
	}
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	public List<String> getListaMensajesOk() {
		return listaMensajesOk;
	}
	public void setListaMensajesOk(List<String> listaMensajesOk) {
		this.listaMensajesOk = listaMensajesOk;
	}
	public List<String> getListaMensajesError() {
		return listaMensajesError;
	}
	public void setListaMensajesError(List<String> listaMensajesError) {
		this.listaMensajesError = listaMensajesError;
	}
	public Integer getNumOK() {
		return numOK;
	}
	public void setNumOK(Integer numOK) {
		this.numOK = numOK;
	}
	public Integer getNumError() {
		return numError;
	}
	public void setNumError(Integer numError) {
		this.numError = numError;
	}
	
}

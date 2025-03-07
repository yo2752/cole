package org.gestoresmadrid.oegam2comun.trafico.view.beans;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultadoMatriculacionBean implements Serializable{

	private static final long serialVersionUID = -3241596984832269371L;

	private String mensaje;
	private Boolean error;
	private File fichero;
	private String nombreFichero;
	private Long numError;
	private List<String> listaErrores;
	private Long numOk;
	private List<String> listaOk;
	private Boolean esZip;

	public ResultadoMatriculacionBean(Boolean error) {
		super();
		this.error = error;
		this.esZip = Boolean.FALSE;
	}

	public void addNumError(){
		if(numError == null){
			numError = new Long(0);
		}
		numError++;
	}

	public void addNumOk(){
		if(numOk == null){
			numOk = new Long(0);
		}
		numOk++;
	}

	public void addListaError(String mensaje){
		if(listaErrores == null || listaErrores.isEmpty()){
			listaErrores = new ArrayList<String>();
		}
		listaErrores.add(mensaje);
	}

	public void addListaOk(String mensaje){
		if(listaOk == null || listaOk.isEmpty()){
			listaOk = new ArrayList<String>();
		}
		listaOk.add(mensaje);
	}

	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public File getFichero() {
		return fichero;
	}
	public void setFichero(File fichero) {
		this.fichero = fichero;
	}
	public String getNombreFichero() {
		return nombreFichero;
	}
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	public Long getNumError() {
		return numError;
	}
	public void setNumError(Long numError) {
		this.numError = numError;
	}
	public List<String> getListaErrores() {
		return listaErrores;
	}
	public void setListaErrores(List<String> listaErrores) {
		this.listaErrores = listaErrores;
	}
	public Long getNumOk() {
		return numOk;
	}
	public void setNumOk(Long numOk) {
		this.numOk = numOk;
	}
	public List<String> getListaOk() {
		return listaOk;
	}
	public void setListaOk(List<String> listaOk) {
		this.listaOk = listaOk;
	}

	public Boolean getEsZip() {
		return esZip;
	}

	public void setEsZip(Boolean esZip) {
		this.esZip = esZip;
	}

}
package org.gestoresmadrid.oegam2comun.consultaKo.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.globaltms.gestorDocumentos.bean.FicheroBean;

public class ResumenConsultaKoBean implements Serializable{

	private static final long serialVersionUID = -1809483058511194737L;
	public Integer numOk;
	public Integer numError;
	public List<String> listaOk;
	public List<String> listaErrores;
	private String nombreFichero;
	private Boolean existeFichero;
	private FicheroBean ficheroExcelResumen;

	public void addListaOk(String mensaje){
		if (listaOk == null || listaOk.isEmpty()) {
			listaOk = new ArrayList<>();
			numOk = Integer.valueOf(0);
		}
		listaOk.add(mensaje);
		numOk++;
	}

	public void addListaKO(String mensaje){
		if(listaErrores == null || listaErrores.isEmpty()){
			listaErrores = new ArrayList<>();
			numError = Integer.valueOf(0);
		}
		listaErrores.add(mensaje);
		numError++;
	}

	public FicheroBean getFicheroExcelResumen() {
		return ficheroExcelResumen;
	}

	public void setFicheroExcelResumen(FicheroBean ficheroExcelResumen) {
		this.ficheroExcelResumen = ficheroExcelResumen;
	}

	/**
	 * Add an attachment
	 * @param key
	 * @param value
	 */

	public ResumenConsultaKoBean() {
		super();
	}

	public void addNumError() {
		if (numError == null) {
			numError = 0;
		}
		numError++;
	}

	public void addNumOk() {
		if (numOk == null) {
			numOk = 0;
		}
		numOk++;
	}

	public void addListaMensajeOk(String mensaje){
		if(listaOk == null || listaOk.isEmpty()){
			listaOk = new ArrayList<>();
		}
		listaOk.add(mensaje);
	}

	public void addListaMensajeError(String mensaje) {
		if (listaErrores == null || listaErrores.isEmpty()) {
			listaErrores = new ArrayList<>();
		}
		listaErrores.add(mensaje);
	}

	public void addListaNumErrores(Integer numErrores) {
		if (numError== null) {
			numError = 0;
		}
		numError += numErrores;
	}

	public void addListaErrores(List<String> listaMensaje) {
		if (listaErrores == null) {
			listaErrores =  new ArrayList<>();
		}
		for (String mensaje : listaMensaje) {
			listaErrores.add(mensaje);
		}
	}
	public void addListaNumOk(Integer numOks){
		if (numOk== null) {
			numOk = 0;
		}
		numOk += numOks;
	}

	public void addListaOk(List<String> listaMensaje){
		if (listaOk == null) {
			listaOk =  new ArrayList<>();
		}
		for (String mensaje : listaMensaje) {
			listaOk.add(mensaje);
		}
	}

	public Integer getNumOk() {
		return numOk;
	}
	public void setNumOk(Integer numOk) {
		this.numOk = numOk;
	}
	public Integer getNumError() {
		return numError;
	}
	public void setNumError(Integer numError) {
		this.numError = numError;
	}
	public List<String> getListaOk() {
		return listaOk;
	}
	public void setListaOk(List<String> listaOk) {
		this.listaOk = listaOk;
	}
	public List<String> getListaErrores() {
		return listaErrores;
	}
	public void setListaErrores(List<String> listaErrores) {
		this.listaErrores = listaErrores;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public Boolean getExisteFichero() {
		return existeFichero;
	}

	public void setExisteFichero(Boolean existeFichero) {
		this.existeFichero = existeFichero;
	}

}
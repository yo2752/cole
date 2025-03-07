package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.globaltms.gestorDocumentos.bean.FicheroBean;

public class ResumenPermisoDistintivoItvBean implements Serializable{

	private static final long serialVersionUID = 5672210717124092766L;
	
	public Integer numOk;
	public Integer numError;
	public List<String> listaOk;
	public List<String> listaErrores;
	private String nombreFichero;
	private Boolean existeFichero;
	private Map<String, Object>	attachments;
	
	private FicheroBean ficheroExcelResumen;
	
	
	public Map<String, Object> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<String, Object> attachments) {
		this.attachments = attachments;
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
	public void addAttachment(String key, Object value) {
		if (null == attachments){
			attachments = new HashMap<String, Object>();
		}
		attachments.put(key, value);
	}

	/**
	 * @return an attachment
	 */
	public Object getAttachment(String key) {
		if (null == attachments){
			return null;
		}
		else{
			return attachments.get(key);
		}
	}

	public ResumenPermisoDistintivoItvBean() {
		super();
	}

	public void addNumError(){
		if(numError == null){
			numError = 0;
		}
		numError++;
	}
	
	public void addNumOk(){
		if(numOk == null){
			numOk = 0;
		}
		numOk++;
	}
	
	public void addListaMensajeOk(String mensaje){
		if(listaOk == null || listaOk.isEmpty()){
			listaOk = new ArrayList<String>();
		}
		listaOk.add(mensaje);
	}
	
	public void addListaMensajeError(String mensaje){
		if(listaErrores == null || listaErrores.isEmpty()){
			listaErrores = new ArrayList<String>();
		}
		listaErrores.add(mensaje);
	}
	
	public void addListaNumErrores(Integer numErrores){
		if(numError== null){
			numError = 0;
		}
		numError += numError;
	}
	
	public void addListaErrores(List<String> listaMensaje){
		if(listaErrores == null){
			listaErrores =  new ArrayList<String>();
		}
		for(String mensaje : listaMensaje){
			listaErrores.add(mensaje);
		}
	}
	public void addListaNumOk(Integer numOks){
		if(numOk== null){
			numOk = 0;
		}
		numOk += numOk;
	}
	
	public void addListaOk(List<String> listaMensaje){
		if(listaOk == null){
			listaOk =  new ArrayList<String>();
		}
		for(String mensaje : listaMensaje){
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

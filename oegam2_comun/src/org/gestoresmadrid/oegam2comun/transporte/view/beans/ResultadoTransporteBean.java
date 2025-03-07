package org.gestoresmadrid.oegam2comun.transporte.view.beans;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.transporte.view.dto.PoderTransporteDto;

public class ResultadoTransporteBean implements Serializable{

	private static final long serialVersionUID = -4653222075408231759L;
	
	private Boolean error;
	private String mensaje;
	private List<String> listaMensajes;
	public File fichero;
	public Boolean esZip = Boolean.FALSE;
	public String nombreFichero;
	public Integer numOk;
	public Integer numError;
	public List<String> listaOK;
	public List<String> listaErrores;
	public PoderTransporteDto poderTransporte;
	public List<String> listaNombresPdf;
	
	public ResultadoTransporteBean(Boolean error) {
		super();
		this.error = error;
	}

	public ResultadoTransporteBean(Boolean error, String mensaje) {
		super();
		this.error = error;
		this.mensaje = mensaje;
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

	public List<String> getListaOK() {
		return listaOK;
	}

	public void setListaOK(List<String> listaOK) {
		this.listaOK = listaOK;
	}

	public List<String> getListaErrores() {
		return listaErrores;
	}

	public void setListaErrores(List<String> listaErrores) {
		this.listaErrores = listaErrores;
	}
	
	public void addOk(){
		if(numOk== null){
			numOk = 0;
		}
		numOk++;
	}
	
	public void addError(){
		if(numError== null){
			numError = 0;
		}
		numError++;
	}
	
	public void addListaOk(String mensaje){
		if(listaOK == null){
			listaOK =  new ArrayList<String>();
		}
		listaOK.add(mensaje);
	}
	
	public void addListaError(String mensaje){
		if(listaErrores == null){
			listaErrores =  new ArrayList<String>();
		}
		listaErrores.add(mensaje);
	}

	public PoderTransporteDto getPoderTransporte() {
		return poderTransporte;
	}

	public void setPoderTransporte(PoderTransporteDto poderTransporte) {
		this.poderTransporte = poderTransporte;
	}

	public List<String> getListaNombresPdf() {
		return listaNombresPdf;
	}

	public void setListaNombresPdf(List<String> listaNombresPdf) {
		this.listaNombresPdf = listaNombresPdf;
	}

	public Boolean getEsZip() {
		return esZip;
	}

	public void setEsZip(Boolean esZip) {
		this.esZip = esZip;
	}

	
}

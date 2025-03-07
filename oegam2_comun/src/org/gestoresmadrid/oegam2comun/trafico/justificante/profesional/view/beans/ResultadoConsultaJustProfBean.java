package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultadoConsultaJustProfBean implements Serializable{

	private static final long serialVersionUID = 3189321751984158097L;
	
	public Integer numOk;
	public Integer numError;
	public Integer numOkDuplicado;
	public Integer numErrorDuplicado;
	public Integer numOkTransmisiones;
	public Integer numErrorTransmisiones;
	public List<String> listaOK;
	public List<String> listaErrores;
	public List<String> listaOKDuplicados;
	public List<String> listaErroresDuplicados;
	public List<String> listaOKTransmisiones;
	public List<String> listaErroresTransmisiones;
	public String mensaje;
	public Boolean error;
	public File fichero;
	public byte[] byteFichero;
	public String nombreFichero;
	public String tipoTramiteGenerarBloque;
	
	public ResultadoConsultaJustProfBean() {
		super();
		this.error = Boolean.FALSE;
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
	
	public void addOkDuplicado(){
		if(numOkDuplicado == null){
			numOkDuplicado = 0;
		}
		numOkDuplicado++;
	}
	
	public void addErrorDuplicado(){
		if(numErrorDuplicado == null){
			numErrorDuplicado = 0;
		}
		numErrorDuplicado++;
	}
	
	public void addOkTransmision(){
		if(numOkTransmisiones == null){
			numOkTransmisiones = 0;
		}
		numOkTransmisiones++;
	}
	
	public void addErrorTransmision(){
		if(numErrorTransmisiones == null){
			numErrorTransmisiones = 0;
		}
		numErrorTransmisiones++;
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
	
	public void addListaOkDuplicados(String mensaje){
		if(listaOKDuplicados == null){
			listaOKDuplicados =  new ArrayList<String>();
		}
		listaOKDuplicados.add(mensaje);
	}
	
	public void addListaErrorDuplicados(String mensaje){
		if(listaErroresDuplicados == null){
			listaErroresDuplicados =  new ArrayList<String>();
		}
		listaErroresDuplicados.add(mensaje);
	}
	
	public void addListaOkTransmision(String mensaje){
		if(listaOKTransmisiones == null){
			listaOKTransmisiones =  new ArrayList<String>();
		}
		listaOKTransmisiones.add(mensaje);
	}
	
	public void addListaErrorTransmision(String mensaje){
		if(listaErroresTransmisiones == null){
			listaErroresTransmisiones =  new ArrayList<String>();
		}
		listaErroresTransmisiones.add(mensaje);
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

	public List<String> getListaOKDuplicados() {
		return listaOKDuplicados;
	}

	public void setListaOKDuplicados(List<String> listaOKDuplicados) {
		this.listaOKDuplicados = listaOKDuplicados;
	}

	public List<String> getListaErroresDuplicados() {
		return listaErroresDuplicados;
	}

	public void setListaErroresDuplicados(List<String> listaErroresDuplicados) {
		this.listaErroresDuplicados = listaErroresDuplicados;
	}

	public List<String> getListaOKTransmisiones() {
		return listaOKTransmisiones;
	}

	public void setListaOKTransmisiones(List<String> listaOKTransmisiones) {
		this.listaOKTransmisiones = listaOKTransmisiones;
	}

	public List<String> getListaErroresTransmisiones() {
		return listaErroresTransmisiones;
	}

	public void setListaErroresTransmisiones(List<String> listaErroresTransmisiones) {
		this.listaErroresTransmisiones = listaErroresTransmisiones;
	}

	public Integer getNumOkDuplicado() {
		return numOkDuplicado;
	}

	public void setNumOkDuplicado(Integer numOkDuplicado) {
		this.numOkDuplicado = numOkDuplicado;
	}

	public Integer getNumErrorDuplicado() {
		return numErrorDuplicado;
	}

	public void setNumErrorDuplicado(Integer numErrorDuplicado) {
		this.numErrorDuplicado = numErrorDuplicado;
	}

	public Integer getNumOkTransmisiones() {
		return numOkTransmisiones;
	}

	public void setNumOkTransmisiones(Integer numOkTransmisiones) {
		this.numOkTransmisiones = numOkTransmisiones;
	}

	public Integer getNumErrorTransmisiones() {
		return numErrorTransmisiones;
	}

	public byte[] getByteFichero() {
		return byteFichero;
	}

	public void setByteFichero(byte[] byteFichero) {
		this.byteFichero = byteFichero;
	}

	public void setNumErrorTransmisiones(Integer numErrorTransmisiones) {
		this.numErrorTransmisiones = numErrorTransmisiones;
	}

	public String getTipoTramiteGenerarBloque() {
		return tipoTramiteGenerarBloque;
	}

	public void setTipoTramiteGenerarBloque(String tipoTramiteGenerarBloque) {
		this.tipoTramiteGenerarBloque = tipoTramiteGenerarBloque;
	}
	
}

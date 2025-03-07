package org.gestoresmadrid.oegam2comun.tasas.view.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;

public class ResultadoConsultaTasasBean implements Serializable{


	private static final long serialVersionUID = -4893441410940248156L;
	
	private Boolean error;
	private String mensaje;
	private Integer numOk;
	private Integer numError;
	private List<String> listaOk;
	private List<String> listaError;
	private String nombreFichero;
	private Date fechaInicio;
	private Date fechaFin;
	public TasaDto tasaDto;
	
	public ResultadoConsultaTasasBean(Boolean error) {
		this.error = error;
		this.listaOk = new ArrayList<String>();
		this.listaError = new ArrayList<String>();
		this.numOk = 0;
		this.numError = 0;
	}
	
	public void addListaErroresList(List<String> listaMensajes){
		if(listaError == null || listaError.isEmpty()){
			listaError = new ArrayList<String>();
		}
		for(String mensaje : listaMensajes){
			listaError.add(mensaje);
		}
	}
	
	public ResultadoConsultaTasasBean() {
		super();
		this.error = Boolean.FALSE;
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

	public List<String> getListaError() {
		return listaError;
	}

	public void setListaError(List<String> listaError) {
		this.listaError = listaError;
	}
	
	public void aniadirMensajeListaOk(String mensaje){
		this.listaOk.add(mensaje);
	}
	
	public void aniadirMensajeListaError(String mensaje){
		this.listaError.add(mensaje);
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
		if(listaOk == null){
			listaOk =  new ArrayList<String>();
		}
		listaOk.add(mensaje);
	}
	
	public void addListaError(String mensaje){
		if(listaError == null){
			listaError =  new ArrayList<String>();
		}
		listaError.add(mensaje);
	}
	
	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public TasaDto getTasaDto() {
		return tasaDto;
	}

	public void setTasaDto(TasaDto tasaDto) {
		this.tasaDto = tasaDto;
	}

}

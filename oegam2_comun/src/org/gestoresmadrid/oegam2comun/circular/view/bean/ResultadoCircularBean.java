package org.gestoresmadrid.oegam2comun.circular.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.circular.view.dto.CircularDto;


public class ResultadoCircularBean implements Serializable {

	private static final long serialVersionUID = -963118925448786573L;
	
	private Boolean error;
	private Exception excepcion;
	private String mensaje;
	private Integer numOk;
	private Integer numError;
	private List<String> listaOk;
	private List<String> listaError;
	private List<String> listaMensajes;
	private ResumenCircularBean resumen;
	private CircularDto circular;
	private Boolean continuarGestion;
	private String valorPropertie;
	
	public ResultadoCircularBean(Boolean error) {
		super();
		this.error = error;
		this.resumen = new ResumenCircularBean();
		listaError = new ArrayList<String>();
		listaOk = new ArrayList<String>();
		listaMensajes = new ArrayList<String>();
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
	
	public void addListaMensaje(String mensaje){
		if(listaMensajes == null){
			listaMensajes =  new ArrayList<String>();
		}
		listaMensajes.add(mensaje);
	}
	
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public Exception getExcepcion() {
		return excepcion;
	}
	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
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
	public List<String> getListaMensajes() {
		return listaMensajes;
	}
	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}
	public ResumenCircularBean getResumen() {
		return resumen;
	}
	public void setResumen(ResumenCircularBean resumen) {
		this.resumen = resumen;
	}
	public CircularDto getCircular() {
		return circular;
	}
	public void setCircular(CircularDto circular) {
		this.circular = circular;
	}
	public Boolean getContinuarGestion() {
		return continuarGestion;
	}
	public void setContinuarGestion(Boolean continuarGestion) {
		this.continuarGestion = continuarGestion;
	}
	public String getValorPropertie() {
		return valorPropertie;
	}
	public void setValorPropertie(String valorPropertie) {
		this.valorPropertie = valorPropertie;
	}
	
	

}

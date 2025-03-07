package org.gestoresmadrid.oegam2comun.atex5.view.bean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaPersonaAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaVehiculoAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.xml.persona.Persona;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Vehiculo;

public class ResultadoAtex5Bean implements Serializable{

	private static final long serialVersionUID = -3435952789983438686L;

	public Integer numOk;
	public Integer numError;
	public Integer numOkMasivasPersonas;
	public Integer numErrorMasivasPersonas;
	public Integer numOkMasivasVehiculos;
	public Integer numErrorMasivasVehiculos;
	public List<String> listaOK;
	public List<String> listaErrores;
	public List<String> listaTasa;
	public String mensaje;
	public Boolean error;
	public String nombreFichero;
	public File ficheroDescarga;
	public ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto;
	public ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto;
	public Vehiculo vehiculoAtex5;
	public Persona personaAtex5;
	public BigDecimal numExpediente; 
	public String codigoTasa;
	public Boolean esZip;
	
	public void addListaErroresList(List<String> listaMensajes){
		if(listaErrores == null || listaErrores.isEmpty()){
			listaErrores = new ArrayList<String>();
		}
		for(String mensaje : listaMensajes){
			listaErrores.add(mensaje);
		}
	}
	
	public ResultadoAtex5Bean() {
		super();
		this.error = Boolean.FALSE;
		this.esZip = Boolean.FALSE;
	}
	
	public ResultadoAtex5Bean(Boolean error) {
		super();
		this.error = error;
		this.esZip = Boolean.FALSE;
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
	
	public void addOkMasivasPersona(){
		if(numOkMasivasPersonas== null){
			numOkMasivasPersonas = 0;
		}
		numOkMasivasPersonas++;
	}
	
	public void addErrorMasivasPersona(){
		if(numErrorMasivasPersonas== null){
			numErrorMasivasPersonas = 0;
		}
		numErrorMasivasPersonas++;
	}
	
	public void addOkMasivasVehiculo(){
		if(numOkMasivasVehiculos== null){
			numOkMasivasVehiculos = 0;
		}
		numOkMasivasVehiculos++;
	}
	
	public void addErrorMasivasVehiculo(){
		if(numErrorMasivasVehiculos== null){
			numErrorMasivasVehiculos = 0;
		}
		numErrorMasivasVehiculos++;
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
	public String getNombreFichero() {
		return nombreFichero;
	}
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	public File getFicheroDescarga() {
		return ficheroDescarga;
	}

	public void setFicheroDescarga(File ficheroDescarga) {
		this.ficheroDescarga = ficheroDescarga;
	}

	public Integer getNumOkMasivasPersonas() {
		return numOkMasivasPersonas;
	}

	public void setNumOkMasivasPersonas(Integer numOkMasivasPersonas) {
		this.numOkMasivasPersonas = numOkMasivasPersonas;
	}

	public Integer getNumErrorMasivasPersonas() {
		return numErrorMasivasPersonas;
	}

	public void setNumErrorMasivasPersonas(Integer numErrorMasivasPersonas) {
		this.numErrorMasivasPersonas = numErrorMasivasPersonas;
	}

	public Integer getNumOkMasivasVehiculos() {
		return numOkMasivasVehiculos;
	}

	public void setNumOkMasivasVehiculos(Integer numOkMasivasVehiculos) {
		this.numOkMasivasVehiculos = numOkMasivasVehiculos;
	}

	public Integer getNumErrorMasivasVehiculos() {
		return numErrorMasivasVehiculos;
	}

	public void setNumErrorMasivasVehiculos(Integer numErrorMasivasVehiculos) {
		this.numErrorMasivasVehiculos = numErrorMasivasVehiculos;
	}

	public ConsultaVehiculoAtex5Dto getConsultaVehiculoAtex5Dto() {
		return consultaVehiculoAtex5Dto;
	}

	public void setConsultaVehiculoAtex5Dto(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto) {
		this.consultaVehiculoAtex5Dto = consultaVehiculoAtex5Dto;
	}

	public ConsultaPersonaAtex5Dto getConsultaPersonaAtex5Dto() {
		return consultaPersonaAtex5Dto;
	}

	public void setConsultaPersonaAtex5Dto(ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto) {
		this.consultaPersonaAtex5Dto = consultaPersonaAtex5Dto;
	}

	public Vehiculo getVehiculoAtex5() {
		return vehiculoAtex5;
	}

	public void setVehiculoAtex5(Vehiculo vehiculoAtex5) {
		this.vehiculoAtex5 = vehiculoAtex5;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Persona getPersonaAtex5() {
		return personaAtex5;
	}

	public void setPersonaAtex5(Persona personaAtex5) {
		this.personaAtex5 = personaAtex5;
	}

	public List<String> getListaTasa() {
		return listaTasa;
	}

	public void setListaTasa(List<String> listaTasa) {
		this.listaTasa = listaTasa;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public Boolean getEsZip() {
		return esZip;
	}

	public void setEsZip(Boolean esZip) {
		this.esZip = esZip;
	}

}

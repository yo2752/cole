package org.gestoresmadrid.oegamSanciones.view.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegamSanciones.view.dto.SancionDto;
import org.gestoresmadrid.oegamSanciones.view.dto.SancionListadosMotivosDto;

import utilidades.estructuras.Fecha;

public class ResultadoSancionesBean implements Serializable{

	private static final long serialVersionUID = 1097387200555764155L;
	
	private Boolean error;
	private String mensaje;
	private List<String> listaMensajesError;
	private List<String> listaMensajesAvisos;
	private SancionListadosMotivosDto sancionListadosMotivosDto;
	private SancionDto sancionDto;
	private Fecha fechaPresentacion;
	private byte[] byteFinal;
	
	public void addListaMensajeError(String mensaje){
		if(listaMensajesError == null || listaMensajesError.isEmpty()){
			listaMensajesError = new ArrayList<String>();
		}
		listaMensajesError.add(mensaje);
	}
	
	public void addListaMensajeAvisos(String mensaje){
		if(listaMensajesAvisos == null || listaMensajesAvisos.isEmpty()){
			listaMensajesAvisos = new ArrayList<String>();
		}
		listaMensajesAvisos.add(mensaje);
	}
	
	public Boolean getError() {
		return error;
	}
	public ResultadoSancionesBean(Boolean error) {
		super();
		this.error = error;
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

	public List<String> getListaMensajesError() {
		return listaMensajesError;
	}

	public void setListaMensajesError(List<String> listaMensajesError) {
		this.listaMensajesError = listaMensajesError;
	}

	public List<String> getListaMensajesAvisos() {
		return listaMensajesAvisos;
	}

	public void setListaMensajesAvisos(List<String> listaMensajesAvisos) {
		this.listaMensajesAvisos = listaMensajesAvisos;
	}

	public SancionListadosMotivosDto getSancionListadosMotivosDto() {
		return sancionListadosMotivosDto;
	}

	public void setSancionListadosMotivosDto(SancionListadosMotivosDto sancionListadosMotivosDto) {
		this.sancionListadosMotivosDto = sancionListadosMotivosDto;
	}

	public SancionDto getSancionDto() {
		return sancionDto;
	}

	public void setSancionDto(SancionDto sancionDto) {
		this.sancionDto = sancionDto;
	}

	public Fecha getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Fecha fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public byte[] getByteFinal() {
		return byteFinal;
	}

	public void setByteFinal(byte[] byteFinal) {
		this.byteFinal = byteFinal;
	}
	
}

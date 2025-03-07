package org.gestoresmadrid.oegamComun.semaforo.view.bean;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegamComun.semaforo.view.dto.SemaforoDto;

public class ResultadoSemaforosBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8177524559347699855L;
	
	private Boolean error;
	private String mensaje;
	private List<String> listaMensajesError;
	private List<String> listaMensajesAviso;
	private SemaforoDto semaforoDto;
	
	public ResultadoSemaforosBean(Boolean error) {
		super();
		this.error = error;
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
	public List<String> getListaMensajesError() {
		return listaMensajesError;
	}
	public void setListaMensajesError(List<String> listaMensajesError) {
		this.listaMensajesError = listaMensajesError;
	}
	public List<String> getListaMensajesAviso() {
		return listaMensajesAviso;
	}
	public void setListaMensajesAviso(List<String> listaMensajesAviso) {
		this.listaMensajesAviso = listaMensajesAviso;
	}
	public SemaforoDto getSemaforoDto() {
		return semaforoDto;
	}
	public void setSemaforoDto(SemaforoDto semaforoDto) {
		this.semaforoDto = semaforoDto;
	}
	
}

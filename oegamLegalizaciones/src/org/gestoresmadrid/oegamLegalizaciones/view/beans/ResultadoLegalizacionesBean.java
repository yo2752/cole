package org.gestoresmadrid.oegamLegalizaciones.view.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegamLegalizaciones.view.dto.LegalizacionCitaDto;

public class ResultadoLegalizacionesBean implements Serializable {

	private static final long serialVersionUID = 5823207637951271044L;

	private Boolean error;
	private String mensaje;
	private List<String> listaMensajesError;
	private List<String> listaMensajesAvisos;
	private LegalizacionCitaDto legalizacionCitaDto;

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

	public void addMensajeAListaError(String mensaje) {
		if (listaMensajesError == null) {
			listaMensajesError = new ArrayList<String>();
		}
		listaMensajesError.add(mensaje);
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

	public LegalizacionCitaDto getLegalizacionCitaDto() {
		return legalizacionCitaDto;
	}

	public void setLegalizacionCitaDto(LegalizacionCitaDto legalizacionCitaDto) {
		this.legalizacionCitaDto = legalizacionCitaDto;
	}

}

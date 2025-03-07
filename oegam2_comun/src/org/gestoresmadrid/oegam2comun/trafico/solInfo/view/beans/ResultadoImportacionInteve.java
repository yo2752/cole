package org.gestoresmadrid.oegam2comun.trafico.solInfo.view.beans;

import java.util.ArrayList;
import java.util.List;

import trafico.beans.ResumenImportacion;

public class ResultadoImportacionInteve {

	private List<ResumenImportacion> resumen;

	private List<String> mensajes;

	private int tipoTramiteIndex;

	private int total;

	private Boolean error;

	public ResultadoImportacionInteve(List<ResumenImportacion> resumen, List<String> mensajes) {
		if (this.resumen == null) {
			this.resumen = resumen;
		}
		if (error == null) {
			error = false;
		}

		if (this.mensajes == null) {
			this.mensajes = mensajes;
		}

	}

	public ResultadoImportacionInteve() {
		if (this.resumen == null) {
			this.resumen = new ArrayList<ResumenImportacion>();
		}
		if (error == null) {
			error = false;
		}

	}

	public List<ResumenImportacion> getResumen() {
		return resumen;
	}

	public void setResumen(List<ResumenImportacion> resumen) {
		this.resumen = resumen;
	}

	public int getTipoTramiteIndex() {
		return tipoTramiteIndex;
	}

	public void setTipoTramiteIndex(int tipoTramiteIndex) {
		this.tipoTramiteIndex = tipoTramiteIndex;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public List<String> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<String> mensajes) {
		this.mensajes = mensajes;
	}

}

package org.gestoresmadrid.oegam2comun.proceso.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.proceso.view.dto.ProcesoDto;

public class ResultadoProcesosBean implements Serializable{

	private static final long serialVersionUID = -8283251811174222560L;

	private Boolean				error;
	private String				mensaje;
	private List<String>		listaMensajes;
	private List<ProcesoDto>	listaProcesos;
	private String				listaProcesosPatron;
	private String				nombreProceso;

	public ResultadoProcesosBean(Boolean error) {
		super();
		this.error = error;
	}

	public void addListaMensaje(String mensaje){
		if (listaMensajes == null) {
			listaMensajes = new ArrayList<>();
		}
		listaMensajes.add(mensaje);
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

	public List<ProcesoDto> getListaProcesos() {
		return listaProcesos;
	}

	public void setListaProcesos(List<ProcesoDto> listaProcesos) {
		this.listaProcesos = listaProcesos;
	}

	public String getListaProcesosPatron() {
		return listaProcesosPatron;
	}

	public void setListaProcesosPatron(String listaProcesosPatron) {
		this.listaProcesosPatron = listaProcesosPatron;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

}
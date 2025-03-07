package org.gestoresmadrid.oegam2comun.ficheros.temporales.view.dto;

import java.util.List;

import es.globaltms.gestorDocumentos.bean.FicheroBean;

public class ResultadoFicherosTemp {
	private boolean error;
	private String mensaje;
	private List<String> listaMensajes;
	private FicheroBean fichero;
	
	public ResultadoFicherosTemp(boolean error, String mensaje) {
		super();
		this.error = error;
		this.mensaje = mensaje;
	}

	public ResultadoFicherosTemp(boolean error, String mensaje,
			FicheroBean fichero) {
		super();
		this.error = error;
		this.mensaje = mensaje;
		this.fichero = fichero;
	}
	
	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public List<String> getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public FicheroBean getFichero() {
		return fichero;
	}

	public void setFichero(FicheroBean fichero) {
		this.fichero = fichero;
	}

} 

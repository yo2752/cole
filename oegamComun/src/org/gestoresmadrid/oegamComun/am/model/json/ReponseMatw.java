package org.gestoresmadrid.oegamComun.am.model.json;

import java.io.Serializable;
import java.util.List;

public class ReponseMatw implements Serializable{

	private static final long serialVersionUID = -4355622796413777957L;
	
	String numExpediente;
	String codigoResultado;
	String resultado;
	List<String> listaErrores;
	List<String> listaAvisos;
	String estadoTramite;
	
	public String getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getCodigoResultado() {
		return codigoResultado;
	}
	public void setCodigoResultado(String codigoResultado) {
		this.codigoResultado = codigoResultado;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public List<String> getListaErrores() {
		return listaErrores;
	}
	public void setListaErrores(List<String> listaErrores) {
		this.listaErrores = listaErrores;
	}
	public List<String> getListaAvisos() {
		return listaAvisos;
	}
	public void setListaAvisos(List<String> listaAvisos) {
		this.listaAvisos = listaAvisos;
	}
	public String getEstadoTramite() {
		return estadoTramite;
	}
	public void setEstadoTramite(String estadoTramite) {
		this.estadoTramite = estadoTramite;
	}
	
	
}

package org.gestoresmadrid.oegam2comun.trafico.view.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.sega.checkCtit.view.xml.SolicitudTramite;
import org.gestoresmadrid.oegam2comun.sega.ctit.view.xml.SolicitudRegistroEntrada;

public class ResultadoCtitBean implements Serializable{

	private static final long serialVersionUID = 1896964271285520038L;

	private Boolean error;
	private List<String> listaMensajes;
	private String mensajeError;
	private SolicitudTramite solicitudTramite;
	private SolicitudRegistroEntrada solicitudRegistroEntrada;
	private String xmlFimado;
	private String nombreXml;
	private Exception excepcion;
	private Boolean esRecuperable;
	private EstadoTramiteTrafico estadoNuevoProceso;
	private String respuesta;
	private Boolean ahiSegundoEnvio;
	private List<String> listaMensajesOks;
	private List<String> listaMensajesAvisos;
	private List<String> listaMensajesError;
	private String numExpediente;
	private Boolean esTelematico;
	private String estadoTramite;

	public ResultadoCtitBean(Boolean error) {
		super();
		this.error = error;
		this.esTelematico = Boolean.TRUE;
		this.esRecuperable = Boolean.FALSE;
		this.ahiSegundoEnvio = Boolean.FALSE;
	}

	public void addMensajeLista(String mensaje){
		if(listaMensajes == null){
			listaMensajes = new ArrayList<String>();
		}
		listaMensajes.add(mensaje);
	}
	public void addListaMensajesAvisos(String mensajeAviso) {
		if (listaMensajesAvisos == null || listaMensajesAvisos.isEmpty()) {
			listaMensajesAvisos = new ArrayList<String>();
		}
		listaMensajesAvisos.add(mensajeAviso);
	}

	public void addListaMensajesError(String mensajeError) {
		if (listaMensajesError == null || listaMensajesError.isEmpty()) {
			listaMensajesError = new ArrayList<String>();
		}
		listaMensajesError.add(mensajeError);
	}

	public void addListaMensajesOk(String mensajeOk) {
		if (listaMensajesOks == null || listaMensajesOks.isEmpty()) {
			listaMensajesOks = new ArrayList<String>();
		}
		listaMensajesOks.add(mensajeOk);
	}

	public String getNombreXml() {
		return nombreXml;
	}
	public void setNombreXml(String nombreXml) {
		this.nombreXml = nombreXml;
	}
	public String getXmlFimado() {
		return xmlFimado;
	}
	public void setXmlFimado(String xmlFimado) {
		this.xmlFimado = xmlFimado;
	}
	public SolicitudTramite getSolicitudTramite() {
		return solicitudTramite;
	}
	public void setSolicitudTramite(SolicitudTramite solicitudTramite) {
		this.solicitudTramite = solicitudTramite;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public List<String> getListaMensajes() {
		return listaMensajes;
	}
	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public SolicitudRegistroEntrada getSolicitudRegistroEntrada() {
		return solicitudRegistroEntrada;
	}

	public void setSolicitudRegistroEntrada(SolicitudRegistroEntrada solicitudRegistroEntrada) {
		this.solicitudRegistroEntrada = solicitudRegistroEntrada;
	}

	public Exception getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}

	public Boolean getEsRecuperable() {
		return esRecuperable;
	}

	public void setEsRecuperable(Boolean esRecuperable) {
		this.esRecuperable = esRecuperable;
	}

	public EstadoTramiteTrafico getEstadoNuevoProceso() {
		return estadoNuevoProceso;
	}

	public void setEstadoNuevoProceso(EstadoTramiteTrafico estadoNuevoProceso) {
		this.estadoNuevoProceso = estadoNuevoProceso;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Boolean getAhiSegundoEnvio() {
		return ahiSegundoEnvio;
	}

	public void setAhiSegundoEnvio(Boolean ahiSegundoEnvio) {
		this.ahiSegundoEnvio = ahiSegundoEnvio;
	}

	public List<String> getListaMensajesOks() {
		return listaMensajesOks;
	}

	public void setListaMensajesOks(List<String> listaMensajesOks) {
		this.listaMensajesOks = listaMensajesOks;
	}

	public List<String> getListaMensajesAvisos() {
		return listaMensajesAvisos;
	}

	public void setListaMensajesAvisos(List<String> listaMensajesAvisos) {
		this.listaMensajesAvisos = listaMensajesAvisos;
	}

	public List<String> getListaMensajesError() {
		return listaMensajesError;
	}

	public void setListaMensajesError(List<String> listaMensajesError) {
		this.listaMensajesError = listaMensajesError;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Boolean getEsTelematico() {
		return esTelematico;
	}

	public void setEsTelematico(Boolean esTelematico) {
		this.esTelematico = esTelematico;
	}

	public String getEstadoTramite() {
		return estadoTramite;
	}

	public void setEstadoTramite(String estadoTramite) {
		this.estadoTramite = estadoTramite;
	}

}
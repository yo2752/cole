package org.gestoresmadrid.oegamBajas.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;

public class ResultadoBajasBean implements Serializable {

	private static final long serialVersionUID = 1097387200555764155L;

	Boolean error;
	String mensaje;
	List<String> listaMensajesError;
	List<String> listaMensajesAvisos;
	BigDecimal numExpediente;
	TramiteTrafBajaDto tramiteBajaDto;
	Long estadoNuevo;
	String xml;
	String nombreFichero;
	Exception excepcion;
	String resultadoConsuta;
	String respuesta;
	Boolean isNoTramitable;

	public void addListaMensajeError(String mensaje) {
		if (listaMensajesError == null || listaMensajesError.isEmpty()) {
			listaMensajesError = new ArrayList<>();
		}
		listaMensajesError.add(mensaje);
	}

	public void addListaMensajeAvisos(String mensaje) {
		if (listaMensajesAvisos == null || listaMensajesAvisos.isEmpty()) {
			listaMensajesAvisos = new ArrayList<>();
		}
		listaMensajesAvisos.add(mensaje);
	}

	public Boolean getError() {
		return error;
	}
	public ResultadoBajasBean(Boolean error) {
		super();
		this.error = error;
		this.isNoTramitable = Boolean.FALSE;
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

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public TramiteTrafBajaDto getTramiteBajaDto() {
		return tramiteBajaDto;
	}

	public void setTramiteBajaDto(TramiteTrafBajaDto tramiteBajaDto) {
		this.tramiteBajaDto = tramiteBajaDto;
	}

	public Long getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(Long estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public Exception getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}

	public String getResultadoConsuta() {
		return resultadoConsuta;
	}

	public void setResultadoConsuta(String resultadoConsuta) {
		this.resultadoConsuta = resultadoConsuta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Boolean getIsNoTramitable() {
		return isNoTramitable;
	}

	public void setIsNoTramitable(Boolean isNoTramitable) {
		this.isNoTramitable = isNoTramitable;
	}
}
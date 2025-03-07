package org.gestoresmadrid.oegam2comun.wscn.model.dto;

import java.util.List;

import org.gestoresmadrid.core.notificacion.model.vo.NotificacionSSVO;
import org.gi.infra.wscn.pruebas.ws.AcuseNotificacion;
import org.gi.infra.wscn.pruebas.ws.NotificacionRecuperada;

public class RespuestaNotificacionesSS {

	private boolean error = false;
	private Exception exception;
	private String mensajeError;
	private List<String> mensajesError;
	private byte[] xmlAcuseNotificacion;
	private AcuseNotificacion acuse;
	private NotificacionRecuperada notificacion;
	private List<NotificacionSSVO> listaNotificacionSS;

	public RespuestaNotificacionesSS() {
		super();
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public List<String> getMensajesError() {
		return mensajesError;
	}

	public void setMensajesError(List<String> mensajesError) {
		this.mensajesError = mensajesError;
	}

	public byte[] getXmlAcuseNotificacion() {
		return xmlAcuseNotificacion;
	}

	public void setXmlAcuseNotificacion(byte[] xmlAcuseNotificacion) {
		this.xmlAcuseNotificacion = xmlAcuseNotificacion;
	}

	public AcuseNotificacion getAcuse() {
		return acuse;
	}

	public void setAcuse(AcuseNotificacion acuse) {
		this.acuse = acuse;
	}

	public NotificacionRecuperada getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(NotificacionRecuperada notificacion) {
		this.notificacion = notificacion;
	}

	public List<NotificacionSSVO> getListaNotificacionSS() {
		return listaNotificacionSS;
	}

	public void setListaNotificacionSS(List<NotificacionSSVO> listaNotificacionSS) {
		this.listaNotificacionSS = listaNotificacionSS;
	}

}
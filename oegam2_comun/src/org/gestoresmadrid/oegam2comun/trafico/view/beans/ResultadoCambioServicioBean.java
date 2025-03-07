package org.gestoresmadrid.oegam2comun.trafico.view.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafCambioServicioDto;

public class ResultadoCambioServicioBean implements Serializable{

	private static final long serialVersionUID = -247777245072559994L;

	public static final String TIPO_PDF = "pdf";
	public static final String TIPO_FILE = "file";
	public static final String NOMBRE_FICHERO = "nombreFichero";
	private boolean error;
	private Exception excepcion;
	private String mensajeError;
	private List<String> listaMensajes;
	private EstadoTramiteTrafico estadoNuevo;
	private String resultadoConsuta;
	private String respuesta;
	private Map<String, Object>	attachments;
	private TramiteTrafCambioServicioDto tramiteCambioServicioDto;

	public ResultadoCambioServicioBean(){
		init();
	}
	public void init() {
		setError(Boolean.FALSE);
		setListaMensajes(new ArrayList<String>());
	}

	public ResultadoCambioServicioBean(boolean error, String mensajeError) {
		super();
		this.error = error;
		if(mensajeError != null) {
			addMensajeALista(mensajeError);
		}
		this.mensajeError = mensajeError;
	}

	public ResultadoCambioServicioBean(boolean error, List<String> listaMensajes) {
		super();
		this.error = error;
		this.listaMensajes = listaMensajes;
	}

	public ResultadoCambioServicioBean(boolean error, Exception excepcion, String mensajeError, List<String> listaMensajes) {
		super();
		this.mensajeError = mensajeError;
		this.listaMensajes = listaMensajes;
		this.error = error;
		this.excepcion = excepcion;
	}
	public void addMensaje(String mensaje) {
		if(mensajeError == null || mensajeError.isEmpty()) {
			mensajeError = mensaje;
		} else {
			mensajeError += mensaje;
		}
	}

	public ResultadoCambioServicioBean(boolean error, Exception excepcion) {
		super();
		this.error = error;
		this.excepcion = excepcion;
	}

	public ResultadoCambioServicioBean(boolean error) {
		super();
		this.error = error;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
		if(this.listaMensajes == null){
			listaMensajes = new ArrayList<String>();
		}
		this.listaMensajes.add(mensajeError);
	}

	public List<String> getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public void addMensajeALista(String mensajeError){
		if (listaMensajes== null){
			listaMensajes = new ArrayList<String>();
		}
		listaMensajes.add(mensajeError);
	}
	public void addListaMensajes(List<String> lista){
		if (listaMensajes== null){
			listaMensajes = new ArrayList<String>();
		}
		for (String msg : lista){
			this.listaMensajes.add(msg);
		}
	}

	public void addError(String error){
		this.error=Boolean.TRUE;
		addMensajeALista(error);
	}

	public void addAttachment(String key, Object value) {
		if (null == attachments){
			attachments = new HashMap<String, Object>();
		}
		attachments.put(key, value);
	}

	/**
	 * @return an attachment
	 */
	public Object getAttachment(String key) {
		if (null == attachments){
			return null;
		}
		else{
			return attachments.get(key);
		}
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Exception getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}

	public EstadoTramiteTrafico getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(EstadoTramiteTrafico estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
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

	public TramiteTrafCambioServicioDto getTramiteCambioServicioDto() {
		return tramiteCambioServicioDto;
	}

	public void setTramiteCambioServicioDto(TramiteTrafCambioServicioDto tramiteCambioServicioDto) {
		this.tramiteCambioServicioDto = tramiteCambioServicioDto;
	}

}
package org.gestoresmadrid.oegamImportacion.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;

public class ResultadoValidacionImporBean implements Serializable {

	private static final long serialVersionUID = 2145228241449355132L;

	private Boolean error;
	private String mensaje;
	private List<String> listaMensajeError;
	private List<String> listaMensajesAvisos;
	private Long numOK;
	private Long numError;
	private Boolean errorVehiculo;
	private Boolean errorDireccion;
	private Boolean errorInterviniente;
	private DireccionVO direccion;

	public void addListaMensajeError(String mensaje) {
		if (listaMensajeError == null) {
			listaMensajeError = new ArrayList<>();
		}
		listaMensajeError.add(mensaje);
	}

	public void addListaMensajeAvisos(String mensaje) {
		if (listaMensajesAvisos == null) {
			listaMensajesAvisos = new ArrayList<>();
		}
		listaMensajesAvisos.add(mensaje);
	}

	public void addAllListaMensajeAvisos(List<String> listaMensajes) {
		if (listaMensajesAvisos == null) {
			listaMensajesAvisos = new ArrayList<>();
		}
		listaMensajesAvisos.addAll(listaMensajes);
	}

	public void addNumOK() {
		if (numOK == null) {
			numOK = new Long(0);
		}
		numOK++;
	}

	public void addNumErro() {
		if (numError == null) {
			numError = 0L;
		}
		numError++;
	}

	public ResultadoValidacionImporBean(Boolean error) {
		super();
		this.error = error;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<String> getListaMensajeError() {
		return listaMensajeError;
	}

	public void setListaMensajeError(List<String> listaMensajeError) {
		this.listaMensajeError = listaMensajeError;
	}

	public List<String> getListaMensajesAvisos() {
		return listaMensajesAvisos;
	}

	public void setListaMensajesAvisos(List<String> listaMensajesAvisos) {
		this.listaMensajesAvisos = listaMensajesAvisos;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public Boolean getErrorVehiculo() {
		return errorVehiculo;
	}

	public void setErrorVehiculo(Boolean errorVehiculo) {
		this.errorVehiculo = errorVehiculo;
	}

	public Boolean getErrorDireccion() {
		return errorDireccion;
	}

	public void setErrorDireccion(Boolean errorDireccion) {
		this.errorDireccion = errorDireccion;
	}

	public Boolean getErrorInterviniente() {
		return errorInterviniente;
	}

	public void setErrorInterviniente(Boolean errorInterviniente) {
		this.errorInterviniente = errorInterviniente;
	}

	public DireccionVO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	public Long getNumOK() {
		return numOK;
	}

	public void setNumOK(Long numOK) {
		this.numOK = numOK;
	}

	public Long getNumError() {
		return numError;
	}

	public void setNumError(Long numError) {
		this.numError = numError;
	}
}
package org.gestoresmadrid.oegam2comun.cola.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.envioData.view.dto.EnvioDataDto;

public class ResultadoGestionColaBean implements Serializable {

	private static final long serialVersionUID = 825474399086567303L;

	private String mensajeError;
	private Boolean error;
	private List<String> listaMensajes;
	private List<String> listaError;
	private List<String> listaCorrectas;
	private Integer numError;
	private Integer numOk;
	private List<SolicitudesColaBean> listaSolicitudesCola;
	private int tamListaSolicitudes;
	private List<EnvioDataDto> listaEjecucionesProceso;
	private List<EnvioDataDto> listaEjecucionesProcesoPorCola;
	private List<EnvioDataDto> listaUltimaPeticionCorrecta;
	private int tamListaUltEjecuciones;

	public ResultadoGestionColaBean(Boolean error) {
		super();
		this.error = error;
	}

	public void addMensajeLista(String mensaje) {
		if (listaMensajes == null || listaMensajes.isEmpty()) {
			listaMensajes = new ArrayList<String>();
		}
		listaMensajes.add(mensaje);
	}

	public void addNumError() {
		if (numError == null) {
			numError = 0;
		}
		numError++;
	}

	public void addNumOk() {
		if (numOk == null) {
			numOk = 0;
		}
		numOk++;
	}

	public void addListaCorrectas(String mensajeOk) {
		if (listaCorrectas == null || listaCorrectas.isEmpty()) {
			listaCorrectas = new ArrayList<String>();
		}
		listaCorrectas.add(mensajeOk);
	}

	public void addListaErrores(String mensajeError) {
		if (listaError == null || listaError.isEmpty()) {
			listaError = new ArrayList<String>();
		}
		listaError.add(mensajeError);
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
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

	public List<SolicitudesColaBean> getListaSolicitudesCola() {
		return listaSolicitudesCola;
	}

	public void setListaSolicitudesCola(List<SolicitudesColaBean> listaSolicitudesCola) {
		this.listaSolicitudesCola = listaSolicitudesCola;
	}

	public List<EnvioDataDto> getListaEjecucionesProcesoPorCola() {
		return listaEjecucionesProcesoPorCola;
	}

	public void setListaEjecucionesProcesoPorCola(List<EnvioDataDto> listaEjecucionesProcesoPorCola) {
		this.listaEjecucionesProcesoPorCola = listaEjecucionesProcesoPorCola;
	}

	public List<EnvioDataDto> getListaEjecucionesProceso() {
		return listaEjecucionesProceso;
	}

	public void setListaEjecucionesProceso(List<EnvioDataDto> listaEjecucionesProceso) {
		this.listaEjecucionesProceso = listaEjecucionesProceso;
	}

	public List<EnvioDataDto> getListaUltimaPeticionCorrecta() {
		return listaUltimaPeticionCorrecta;
	}

	public void setListaUltimaPeticionCorrecta(List<EnvioDataDto> listaUltimaPeticionCorrecta) {
		this.listaUltimaPeticionCorrecta = listaUltimaPeticionCorrecta;
	}

	public int getTamListaSolicitudes() {
		return tamListaSolicitudes;
	}

	public void setTamListaSolicitudes(int tamListaSolicitudes) {
		this.tamListaSolicitudes = tamListaSolicitudes;
	}

	public int getTamListaUltEjecuciones() {
		return tamListaUltEjecuciones;
	}

	public void setTamListaUltEjecuciones(int tamListaUltEjecuciones) {
		this.tamListaUltEjecuciones = tamListaUltEjecuciones;
	}

	public List<String> getListaCorrectas() {
		return listaCorrectas;
	}

	public void setListaCorrectas(List<String> listaCorrectas) {
		this.listaCorrectas = listaCorrectas;
	}

	public Integer getNumError() {
		return numError;
	}

	public void setNumError(Integer numError) {
		this.numError = numError;
	}

	public Integer getNumOk() {
		return numOk;
	}

	public void setNumOk(Integer numOk) {
		this.numOk = numOk;
	}

	public List<String> getListaError() {
		return listaError;
	}

	public void setListaError(List<String> listaError) {
		this.listaError = listaError;
	}
}

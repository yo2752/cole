package org.gestoresmadrid.oegam2comun.ficheroSolicitud05.beans;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultadoFicheroSolicitud05Bean implements Serializable {

	private static final long serialVersionUID = 5121466913699963327L;

	private Boolean error;
	private String mensajeError;
	private Integer numOk;
	private Integer numError;
	private List<String> listaOk;
	private List<String> listaError;
	private String nombreFichero;
	private Date fechaInicio;
	private Date fechaFin;
	private File fichero;
	private String sFichero;
	private String tipoTramite;
	private BigDecimal nExp;

	public BigDecimal getnExp() {
		return nExp;
	}

	public void setnExp(BigDecimal nExp) {
		this.nExp = nExp;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public void addSfichero(String fichero) {
		if (sFichero == null || sFichero.isEmpty()) {
			sFichero = "";
		}
		sFichero += fichero;
	}

	public String getsFichero() {
		return sFichero;
	}

	public void setsFichero(String sFichero) {
		this.sFichero = sFichero;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public ResultadoFicheroSolicitud05Bean(Boolean error) {
		this.error = error;
		this.listaOk = new ArrayList<String>();
		this.listaError = new ArrayList<String>();
		this.numOk = 0;
		this.numError = 0;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public Integer getNumOk() {
		return numOk;
	}

	public void setNumOk(Integer numOk) {
		this.numOk = numOk;
	}

	public Integer getNumError() {
		return numError;
	}

	public void setNumError(Integer numError) {
		this.numError = numError;
	}

	public List<String> getListaOk() {
		return listaOk;
	}

	public void setListaOk(List<String> listaOk) {
		this.listaOk = listaOk;
	}

	public List<String> getListaError() {
		return listaError;
	}

	public void setListaError(List<String> listaError) {
		this.listaError = listaError;
	}

	public void aniadirMensajeListaOk(String mensaje) {
		this.listaOk.add(mensaje);
	}

	public void aniadirMensajeListaError(String mensaje) {
		if (listaError == null || listaError.isEmpty()) {
			listaError = new ArrayList<String>();
		}
		this.listaError.add(mensaje);
	}

	public void addOk() {
		this.numOk++;
	}

	public void addError() {
		this.numError++;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

}
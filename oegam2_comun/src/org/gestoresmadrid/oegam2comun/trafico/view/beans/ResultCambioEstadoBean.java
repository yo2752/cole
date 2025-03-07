package org.gestoresmadrid.oegam2comun.trafico.view.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ResultCambioEstadoBean implements Serializable {

	private static final long serialVersionUID = 1064275505864613805L;
	private BigDecimal numExpediente;
	public List<String> listaOK;
	public List<String> listaErrores;
	public String mensaje;
	public Boolean error;
	public Integer numOk;
	public Integer numError;
	public ArrayList<BigDecimal> numExpedientesOk;

	public ResultCambioEstadoBean(Boolean error) {
		this.error = error;
		setListaOK(new ArrayList<String>());
		setListaErrores(new ArrayList<String>());
		setNumExpedientesOk(new ArrayList<BigDecimal>());
		numOk = 0;
		numError = 0;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public void addListaOk(String mensaje) {
		if (listaOK == null) {
			listaOK = new ArrayList<String>();
		}
		listaOK.add(mensaje);
	}

	public List<String> getListaOK() {
		return listaOK;
	}

	public void setListaOK(List<String> listaOK) {
		this.listaOK = listaOK;
	}

	public List<String> getListaErrores() {
		return this.listaErrores;
	}

	public void setListaErrores(List<String> listaErrores) {
		this.listaErrores = listaErrores;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public Integer getNumOk() {
		return numOk;
	}

	public void setNumOk(Integer numOk) {
		this.numOk = numOk;
	}

	public void addOk() {
		if (numOk == null) {
			numOk = 0;
		}
		numOk++;
	}

	public Integer getNumError() {
		return numError;
	}

	public void setNumError(Integer numError) {
		this.numError = numError;
	}

	public void addError() {
		if (numError == null) {
			numError = 0;
		}
		numError++;
	}

	public void addListaError(String mensaje) {
		if (listaErrores == null) {
			listaErrores = new ArrayList<String>();
		}
		listaErrores.add(mensaje);
	}

	public ArrayList<BigDecimal> getNumExpedientesOk() {
		return numExpedientesOk;
	}

	public void setNumExpedientesOk(ArrayList<BigDecimal> numExpedientesOk) {
		this.numExpedientesOk = numExpedientesOk;
	}

	public void addNumExpedientesOk(BigDecimal numExpedienteOk) {
		if (numExpedientesOk == null) {
			numExpedientesOk = new ArrayList<BigDecimal>();
		}
		numExpedientesOk.add(numExpedienteOk);
	}

}
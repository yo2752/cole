package org.gestoresmadrid.oegamDocBase.view.bean;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;

public class IntervinienteBaseBean {
	
	/* INICIO ATRIBUTOS */
	
	private String nif;
	private TipoInterviniente tipoInterviniente;
	private String numColegiado;
	private BigDecimal poderesFicha;
	private String codigoMandato;
	
	/* FIN ATRIBUTOS */
	
	/* INICIO GETTERS Y SETTERS */
	
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public TipoInterviniente getTipoInterviniente() {
		return tipoInterviniente;
	}
	public void setTipoInterviniente(TipoInterviniente tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public BigDecimal getPoderesFicha() {
		return poderesFicha;
	}
	public void setPoderesFicha(BigDecimal poderesFicha) {
		this.poderesFicha = poderesFicha;
	}
	public String getCodigoMandato() {
		return codigoMandato;
	}
	public void setCodigoMandato(String codigoMandato) {
		this.codigoMandato = codigoMandato;
	}
	
	/* FIN GETTERS Y SETTERS */

}

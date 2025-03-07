package org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto;

import java.io.Serializable;

public class IncidenciaIntervalosDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4061102122332333649L;
	
	private String  numSerieIni;
	private String  numSerieFin;
	private boolean borrar;
	
	public IncidenciaIntervalosDto() { super(); }
	
	public IncidenciaIntervalosDto(String numSerieIni, String numSerieFin) {
		super();
		this.numSerieIni = numSerieIni;
		this.numSerieFin = numSerieFin;
	}

	public String getNumSerieIni() {
		return numSerieIni;
	}
	public void setNumSerieIni(String numSerieIni) {
		this.numSerieIni = numSerieIni;
	}
	public String getNumSerieFin() {
		return numSerieFin;
	}
	public void setNumSerieFin(String numSerieFin) {
		this.numSerieFin = numSerieFin;
	}

	public boolean isBorrar() {
		return borrar;
	}

	public void setBorrar(boolean borrar) {
		this.borrar = borrar;
	}
}

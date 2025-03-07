package org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto;

import java.io.Serializable;

public class IncidenciaSerialDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3843171585618079365L;
	
	private Long    incidenciaInve;
	private String  prefijo;
	private Long    serial;
	private String  numSerie;
	private boolean borrar;
	
	public IncidenciaSerialDto() { super(); }
	
	public IncidenciaSerialDto(String numSerie) {
		super();
		this.numSerie = numSerie;
	}

	public Long getIncidenciaInve() {
		return incidenciaInve;
	}
	public void setIncidenciaInve(Long incidenciaInve) {
		this.incidenciaInve = incidenciaInve;
	}
	public String getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public boolean isBorrar() {
		return borrar;
	}

	public void setBorrar(boolean borrar) {
		this.borrar = borrar;
	}

	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	public Long getSerial() {
		return serial;
	}

	public void setSerial(Long serial) {
		this.serial = serial;
	}	
}

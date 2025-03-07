package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;


public class PropiedadIntelectualRegistroDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3370790891235250396L;

	private long idPropiedadIntelectual;
	private String clase;
	private String descripcion;
	private String numRegAdm;
	private BigDecimal idPropiedad;

	public PropiedadIntelectualRegistroDto() {
	}

	public long getIdPropiedadIntelectual() {
		return this.idPropiedadIntelectual;
	}

	public void setIdPropiedadIntelectual(long idPropiedadIntelectual) {
		this.idPropiedadIntelectual = idPropiedadIntelectual;
	}

	public String getClase() {
		return this.clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNumRegAdm() {
		return this.numRegAdm;
	}

	public void setNumRegAdm(String numRegAdm) {
		this.numRegAdm = numRegAdm;
	}

	public BigDecimal getIdPropiedad() {
		return idPropiedad;
	}

	public void setIdPropiedad(BigDecimal idPropiedad) {
		this.idPropiedad = idPropiedad;
	}


}
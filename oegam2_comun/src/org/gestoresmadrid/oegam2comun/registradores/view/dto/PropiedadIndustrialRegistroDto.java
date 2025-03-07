package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;


public class PropiedadIndustrialRegistroDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2915090598614327075L;

	private long idPropiedadIndustrial;
	private String claseMarca;
	private String descripcion;
	private String nombreMarca;
	private String numRegAdm;
	private String subClaseMarca;
	private String tipoMarca;
	private BigDecimal idPropiedad;

	public PropiedadIndustrialRegistroDto() {
	}

	public long getIdPropiedadIndustrial() {
		return this.idPropiedadIndustrial;
	}

	public void setIdPropiedadIndustrial(long idPropiedadIndustrial) {
		this.idPropiedadIndustrial = idPropiedadIndustrial;
	}

	public String getClaseMarca() {
		return this.claseMarca;
	}

	public void setClaseMarca(String claseMarca) {
		this.claseMarca = claseMarca;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreMarca() {
		return this.nombreMarca;
	}

	public void setNombreMarca(String nombreMarca) {
		this.nombreMarca = nombreMarca;
	}

	public String getNumRegAdm() {
		return this.numRegAdm;
	}

	public void setNumRegAdm(String numRegAdm) {
		this.numRegAdm = numRegAdm;
	}

	public String getSubClaseMarca() {
		return this.subClaseMarca;
	}

	public void setSubClaseMarca(String subClaseMarca) {
		this.subClaseMarca = subClaseMarca;
	}

	public String getTipoMarca() {
		return this.tipoMarca;
	}

	public void setTipoMarca(String tipoMarca) {
		this.tipoMarca = tipoMarca;
	}

	public BigDecimal getIdPropiedad() {
		return idPropiedad;
	}

	public void setIdPropiedad(BigDecimal idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

}
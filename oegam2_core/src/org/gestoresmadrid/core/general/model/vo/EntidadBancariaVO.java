package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ENTIDADES_BANCARIAS")
public class EntidadBancariaVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2440268292451824090L;

	@Id
	@Column(name = "CODIGO_ENTIDAD")
	private String codigoEntidad;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	public String getCodigoEntidad() {
		return codigoEntidad;
	}

	public void setCodigoEntidad(String codigoEntidad) {
		this.codigoEntidad = codigoEntidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}

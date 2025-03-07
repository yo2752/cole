package org.gestoresmadrid.core.model.beans;

import java.io.Serializable;

/**
 * Bean empleado en listas/combos/etc en los que se requiera el par codigo/valor
 * @author erds
 */
public class DatoMaestroBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2370548936750816583L;

	private String codigo;

	private String descripcion;

	public DatoMaestroBean() {
		super();
	}

	public DatoMaestroBean(String codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "DatoMaestroBean [codigo=" + codigo + ", descripcion=" + descripcion + "]";
	}

}

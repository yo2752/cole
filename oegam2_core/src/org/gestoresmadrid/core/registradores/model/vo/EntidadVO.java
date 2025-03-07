package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ENTIDAD database table.
 * 
 */
@Entity
@Table(name="ENTIDAD")
public class EntidadVO implements Serializable {

	private static final long serialVersionUID = 8165821046716205817L;

	@Id
	private String codigo;

	private String asociacion;

	private String nombre;

	public EntidadVO() {
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getAsociacion() {
		return this.asociacion;
	}

	public void setAsociacion(String asociacion) {
		this.asociacion = asociacion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
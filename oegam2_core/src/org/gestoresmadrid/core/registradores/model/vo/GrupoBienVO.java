package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the GRUPO_BIEN database table.
 * 
 */
@Entity
@Table(name="GRUPO_BIEN")
public class GrupoBienVO implements Serializable {

	private static final long serialVersionUID = -3954068898697300480L;

	@Id
	private String codigo;

	private String nombre;

	private String tipo;

	public GrupoBienVO() {
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PAIS_REGISTRO database table.
 * 
 */
@Entity
@Table(name="PAIS_REGISTRO")
public class PaisRegistroVO implements Serializable {

	private static final long serialVersionUID = 6631771234361952487L;

	@Id
	private String codigo;

	private String nombre;

	public PaisRegistroVO() {
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

}
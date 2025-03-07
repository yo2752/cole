package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TIPO_INTERES_REFERENCIA database table.
 * 
 */
@Entity
@Table(name="TIPO_INTERES_REFERENCIA")
public class TipoInteresReferenciaVO implements Serializable {

	private static final long serialVersionUID = 2968909783726701540L;

	@Id
	private String codigo;

	private String nombre;

	public TipoInteresReferenciaVO() {
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
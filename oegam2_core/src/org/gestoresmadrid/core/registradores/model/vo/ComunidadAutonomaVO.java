package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the COMUNIDAD_AUTONOMA database table.
 * 
 */
@Entity
@Table(name="COMUNIDAD_AUTONOMA")
public class ComunidadAutonomaVO implements Serializable {


	private static final long serialVersionUID = 7374511464067147079L;

	@Id
	@Column(name="CODIGO_RBMC")
	private String codigoRbmc;

	private String nombre;

	public ComunidadAutonomaVO() {
	}

	public String getCodigoRbmc() {
		return this.codigoRbmc;
	}

	public void setCodigoRbmc(String codigoRbmc) {
		this.codigoRbmc = codigoRbmc;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
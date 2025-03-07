package org.gestoresmadrid.core.notario.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="NOTARIOS")
public class NotarioVO implements Serializable{

	private static final long serialVersionUID = -8481959425916625586L;
	
	@Id
	@Column(name="CODIGO_NOTARIO")
	private String codigoNotario;
	
	@Column(name="CODIGO_NOTARIA")
	private String codigoNotaria;
	
	@Column(name="NOMBRE")
	private String nombre;
	
	@Column(name="APELLIDOS")
	private String apellidos;
	
	public String getCodigoNotario() {
		return codigoNotario;
	}

	public void setCodigoNotario(String codigoNotario) {
		this.codigoNotario = codigoNotario;
	}

	public String getCodigoNotaria() {
		return codigoNotaria;
	}

	public void setCodigoNotaria(String codigoNotaria) {
		this.codigoNotaria = codigoNotaria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
}
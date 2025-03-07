package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the SEC_PERMISO database table.
 * 
 */
@Entity
@Table(name="SEC_PERMISO")
public class SecPermisoVO implements Serializable {

	private static final long serialVersionUID = -2252943464957639690L;

	@Id
	@SequenceGenerator(name="SEC_PERMISO_IDPERMISO_GENERATOR", sequenceName="SEC_PERMISO_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_PERMISO_IDPERMISO_GENERATOR")
	@Column(name="ID_PERMISO")
	private Long idPermiso;

	@Column(name="NOMBRE")
	private String nombre;

	@Column(name="DESCRIPCION")
	private String descripcion;

	@Column(name="INACTIVO")
	@Type(type="yes_no")
	private Boolean inactivo;


	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getInactivo() {
		return inactivo;
	}

	public void setInactivo(Boolean inactivo) {
		this.inactivo = inactivo;
	}

	public void setIdPermiso(Long idPermiso) {
		this.idPermiso = idPermiso;
	}

	public Long getIdPermiso() {
		return idPermiso;
	}

}
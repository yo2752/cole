package org.gestoresmadrid.core.direccion.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the PROVINCIA database table.
 */
@Entity
@Table(name = "PROVINCIA")
public class ProvinciaVO implements Serializable {

	private static final long serialVersionUID = 3201928763431278042L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PROVINCIA")
	private String idProvincia;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "DESCRIPCION_SITEX")
	private String descripcionSitex;

	public ProvinciaVO() {}

	public String getIdProvincia() {
		return this.idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcionSitex() {
		return descripcionSitex;
	}

	public void setDescripcionSitex(String descripcionSitex) {
		this.descripcionSitex = descripcionSitex;
	}
}
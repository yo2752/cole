package org.gestoresmadrid.core.direccion.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the MUNICIPIO_CAM database table.
 */
@Entity
@Table(name = "MUNICIPIO_CAM")
public class MunicipioCamVO implements Serializable {

	private static final long serialVersionUID = 8435202240857760982L;

	@EmbeddedId
	private MunicipioCamPK id;

	@Column(name = "ID_MUNICIPIO_CAM")
	private String idMunicipioCam;

	private String nombre;
	
	@ManyToOne
	@JoinColumn(name = "ID_PROVINCIA", insertable = false, updatable = false)
	private ProvinciaCamVO provincia;

	public MunicipioCamPK getId() {
		return id;
	}

	public void setId(MunicipioCamPK id) {
		this.id = id;
	}

	public String getIdMunicipioCam() {
		return idMunicipioCam;
	}

	public void setIdMunicipioCam(String idMunicipioCam) {
		this.idMunicipioCam = idMunicipioCam;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ProvinciaCamVO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaCamVO provincia) {
		this.provincia = provincia;
	}
	
}
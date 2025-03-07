package org.gestoresmadrid.core.direccion.model.vo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the UNIDAD_POBLACIONAL database table.
 * 
 */
@Entity
@Table(name="UNIDAD_POBLACIONAL")
@NamedQuery(name="UnidadPoblacionalVO.findAll", query="SELECT u FROM UnidadPoblacionalVO u")
public class UnidadPoblacionalVO implements Serializable {
	
	private static final long serialVersionUID = -3012118295295298658L;

	@EmbeddedId
	private UnidadPoblacionalPK id;
	
	@Column(name="ENTIDAD_COLECTIVA")
	private String entidadColectiva;

	@Column(name="ENTIDAD_SINGULAR")
	private String entidadSingular;

	private String municipio;

	private String nucleo;

	public UnidadPoblacionalPK getId() {
		return id;
	}

	public void setId(UnidadPoblacionalPK id) {
		this.id = id;
	}

	public String getEntidadColectiva() {
		return entidadColectiva;
	}

	public void setEntidadColectiva(String entidadColectiva) {
		this.entidadColectiva = entidadColectiva;
	}

	public String getEntidadSingular() {
		return entidadSingular;
	}

	public void setEntidadSingular(String entidadSingular) {
		this.entidadSingular = entidadSingular;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getNucleo() {
		return nucleo;
	}

	public void setNucleo(String nucleo) {
		this.nucleo = nucleo;
	}


}
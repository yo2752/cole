package org.gestoresmadrid.core.estacionITV.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ESTACION_ITV")
public class EstacionITVVO implements Serializable {

	private static final long serialVersionUID = -2902439848857936706L;

	@Id
	@Column (name = "ESTACION_ITV")
	private String id;
	
	@Column (name = "PROVINCIA")
	private String provincia;
	
	@Column (name = "MUNICIPIO")
	private String municipio;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	
}

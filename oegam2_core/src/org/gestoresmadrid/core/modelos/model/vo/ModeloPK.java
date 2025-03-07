package org.gestoresmadrid.core.modelos.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ModeloPK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="MODELO")
	private String modelo;
	
	@Column(name="VERSION")
	private String version;

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}

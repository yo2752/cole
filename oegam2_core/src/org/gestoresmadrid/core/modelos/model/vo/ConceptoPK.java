package org.gestoresmadrid.core.modelos.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ConceptoPK implements Serializable{

	private static final long serialVersionUID = -5426115591183474581L;

	@Column(name="MODELO")
	private String modelo;

	@Column(name="VERSION")
	private String version;

	@Column(name="CONCEPTO")
	private String concepto;

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

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
package com.matriculasIvtmWS.integracion.model.dto;

import java.io.Serializable;
import java.util.List;

public class PropertiesContextDto implements Serializable{

	private static final long serialVersionUID = 4947820617452891671L;

	private String identificador;

	private List<PropertiesDto> properties;

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public List<PropertiesDto> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertiesDto> properties) {
		this.properties = properties;
	}
}

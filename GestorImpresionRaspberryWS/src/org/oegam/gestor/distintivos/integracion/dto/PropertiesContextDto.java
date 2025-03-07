package org.oegam.gestor.distintivos.integracion.dto;

import java.io.Serializable;
import java.util.List;

public class PropertiesContextDto implements Serializable{

	private static final long serialVersionUID = 3069243279189271063L;

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

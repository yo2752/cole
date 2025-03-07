package org.gestoresmadrid.core.administracion.model.enumerados;

public enum EstadoRecargaCacheEnum {

	OK("OK"),
	INDETERMINADO("INDETERMINADO"),
	ERROR("ERROR");
	
	private final String estado;
	
	EstadoRecargaCacheEnum(String estado){
		this.estado = estado;
	}

	public String getEstado() {
		return estado;
	}
	
	
}

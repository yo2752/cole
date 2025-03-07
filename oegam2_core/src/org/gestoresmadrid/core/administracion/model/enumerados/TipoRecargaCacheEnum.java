package org.gestoresmadrid.core.administracion.model.enumerados;

public enum TipoRecargaCacheEnum {

	
	COMBOS("Combos"),
	DATOS_SENSIBLES("Datos Sensibles");
	
	private final String nombre;
	
	TipoRecargaCacheEnum(String nombre){
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
}

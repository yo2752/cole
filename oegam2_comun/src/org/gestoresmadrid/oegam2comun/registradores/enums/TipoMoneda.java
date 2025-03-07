package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de moneda
 */
public enum TipoMoneda {

	EUR("Euros");

	private String nombreEnum;

	private TipoMoneda(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	@Override
	public String toString() {
		return nombreEnum;
	}
	
	public String getKey(){
		return name();
	}

}

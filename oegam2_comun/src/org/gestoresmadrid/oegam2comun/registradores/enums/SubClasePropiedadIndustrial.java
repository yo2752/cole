package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum SubClasePropiedadIndustrial {

	D("Denominativa"),
	G("Gr�fica"),
	M("Mixta");

	private String nombreEnum;

	private SubClasePropiedadIndustrial(String nombreEnum) {
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

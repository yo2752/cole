package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum ExtensionDerecho {

	PD("Pleno Dominio"),
	U("Usufructo"),
	NP("Nuda Propiedad");


	private String nombreEnum;

	private ExtensionDerecho(String nombreEnum) {
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

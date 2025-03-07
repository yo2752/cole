package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de intervinientes
 */
public enum InvolvedPersonType {

	CESSIONARY("Cesionario"),
	BUYER("Comprador"),
	GUARANTOR("Fiador"),
	RENTER("Arrendador"),
	TENANT("Arrendatario");

	private String nombreEnum;

	private InvolvedPersonType(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	@Override
	public String toString() {
		return nombreEnum;
	}

}

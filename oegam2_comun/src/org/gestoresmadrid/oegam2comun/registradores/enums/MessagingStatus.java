package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles estados de la mensajería
 */
public enum MessagingStatus {

	NONE("NONE"),
	PENDING("PENDING"),
	OK("OK"),
	ERROR("ERROR");

	private String nombreEnum;

	private MessagingStatus(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	@Override
	public String toString() {
		return nombreEnum;
	}

}

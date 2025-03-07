package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de usuario
 */
public enum UserType {

	GESTOR("Gestor"),
	EMPLEADO("Empleado");

	private String nombreEnum;

	private UserType(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	@Override
	public String toString() {
		return nombreEnum;
	}

}

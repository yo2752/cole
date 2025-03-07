package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de cesión a terceros. El tipo de cedente
 */
public enum TipoCesion {

	CF("Cesión financiador"),
	CC("Cesión comprador"),
	CA("Cesión arrendador financiero"),
	CAA("Cesión arrendatario financiero");

	private String nombreEnum;

	private TipoCesion(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	public String getKey() {
		return name();
	}

	@Override
	public String toString() {
		return nombreEnum;
	}

}

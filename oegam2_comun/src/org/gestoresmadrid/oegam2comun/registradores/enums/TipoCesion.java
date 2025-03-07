package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de cesi�n a terceros. El tipo de cedente
 */
public enum TipoCesion {

	CF("Cesi�n financiador"),
	CC("Cesi�n comprador"),
	CA("Cesi�n arrendador financiero"),
	CAA("Cesi�n arrendatario financiero");

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

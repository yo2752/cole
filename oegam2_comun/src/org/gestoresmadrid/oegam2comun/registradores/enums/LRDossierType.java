package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de cuadro de expediente de LeasingRentingDossier
 */
public enum LRDossierType {

	LEASING("Leasing"),
	RENTING("Renting");

	private String nombreEnum;

	private LRDossierType(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	@Override
	public String toString() {
		return nombreEnum;
	}

	public String getKey() {
		return name();
	}

}

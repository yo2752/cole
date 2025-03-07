package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum TipoOperacionRegistral {

	ENT("Entrada"),
	PRE("Presentaci�n en el libro diario"),
	PRD("Denegaci�n del asiento de presentaci�n"),
	INT("Inscripci�n total"),
	INP("Inscripci�n parcial"),
	DEF("Calificaci�n con defectos total"),
	CAD("Cancelaci�n asiento por desistimiento");



	private String nombreEnum;

	private TipoOperacionRegistral(String nombreEnum) {
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

package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum TipoOperacionRegistral {

	ENT("Entrada"),
	PRE("Presentación en el libro diario"),
	PRD("Denegación del asiento de presentación"),
	INT("Inscripción total"),
	INP("Inscripción parcial"),
	DEF("Calificación con defectos total"),
	CAD("Cancelación asiento por desistimiento");



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

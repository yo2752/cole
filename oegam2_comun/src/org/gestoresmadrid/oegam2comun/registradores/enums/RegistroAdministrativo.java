package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum RegistroAdministrativo {

	AET("Anotación de embargo telemático"),
	CET("Cancelación embargo telemático"),
	MET("Modificación de embargo telemático"),
	PRE("Prorroga de embargo"),
	CEH("Cancelación Embargos Históricos DGT"),
	C("Contrato"),
	CAE("Ejercicio de opción de compra"),
	CA("Cancelación por pago total");

	private String nombreEnum;

	private RegistroAdministrativo(String nombreEnum) {
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

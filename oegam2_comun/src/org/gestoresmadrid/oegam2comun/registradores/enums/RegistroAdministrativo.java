package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum RegistroAdministrativo {

	AET("Anotaci�n de embargo telem�tico"),
	CET("Cancelaci�n embargo telem�tico"),
	MET("Modificaci�n de embargo telem�tico"),
	PRE("Prorroga de embargo"),
	CEH("Cancelaci�n Embargos Hist�ricos DGT"),
	C("Contrato"),
	CAE("Ejercicio de opci�n de compra"),
	CA("Cancelaci�n por pago total");

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

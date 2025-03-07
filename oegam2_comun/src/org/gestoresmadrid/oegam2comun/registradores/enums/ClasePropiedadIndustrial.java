package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum ClasePropiedadIndustrial {


	A("Marcas Comunitarias"),
	D("Dibujos Industriales"),
	E("Patentes europeas"),
	H("Marcas internacionales"),
	I("Modelos industriales"),
	M("Marcas nacionales"),
	N("Nombres comerciales"),
	P("Patentes de invención"),
	R("Rótulos de establecimiento"),
	U("Modelos de utilidad"),
	W("Patentes mundiales -PCT-");

	private String nombreEnum;

	private ClasePropiedadIndustrial(String nombreEnum) {
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

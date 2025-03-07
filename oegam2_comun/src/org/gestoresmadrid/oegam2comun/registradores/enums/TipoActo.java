package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum TipoActo {

	DGT("Dirección General de Tráfico"),
	PIN("Oficina Española de Patentes y Marcas"),
	PIT("Registro de la Propiedad Intelectual"),
	DGC("Dirección General del Catastro"),
	MME("Marina Mercante"),
	CNMV("Comisión Nacional del Mercado de Valores"),
	RAER("Registro de Aeronaves"),
	RSCO("Registro de Sociedades Cooperativas");

	private String nombreEnum;

	private TipoActo(String nombreEnum) {
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

package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum TipoActo {

	DGT("Direcci�n General de Tr�fico"),
	PIN("Oficina Espa�ola de Patentes y Marcas"),
	PIT("Registro de la Propiedad Intelectual"),
	DGC("Direcci�n General del Catastro"),
	MME("Marina Mercante"),
	CNMV("Comisi�n Nacional del Mercado de Valores"),
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

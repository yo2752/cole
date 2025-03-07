package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum ClasePropiedadIntelectual {

	AAR("Actuaci�n de artista, int�rprete o ejecutante"),
	ARI("Arquitectura e ingenier�a"),
	CMU("Composiciones musicales"),
	COM("Contenidos originales de multimedia"),
	COW("Contenidos originales de p�gina Web"),
	COP("Coreograf�a o pantomima"),
	DIP("Dibujo y pintura"),
	ENR("Entidades de radiodifusi�n"),
	ESC("Escultura"),
	EST("Estructura y disposici�n de base de datos"),
	FON("Fonogramas"),
	FOT("Fotograf�as"),
	GRL("Grabado y litograf�a"),
	MAQ("Maqueta"),
	MFO("Meras fotograf�as"),
	OCI("Obra cinematogr�fica y dem�s obras audiovisuales"),
	OFO("Obra fotogr�fica"),
	ODR("Obras dram�ticas"),
	OLI("Obras literarias o cient�ficas"),
	OPL("Obras pl�sticas"),
	OOP("Otras obras pl�sticas"),
	PGA("Producciones de grabaciones audiovisuales"),
	PED("Producciones editoriales"),
	PRF("Producciones fonogr�ficas"),
	PRO("Programas de ordenador"),
	TBO("Tebeo y c�mic"),
	TGC("Topograf�a, geograf�a, ciencia en general");

	private String nombreEnum;

	private ClasePropiedadIntelectual(String nombreEnum) {
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

package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum ClasePropiedadIntelectual {

	AAR("Actuación de artista, intérprete o ejecutante"),
	ARI("Arquitectura e ingeniería"),
	CMU("Composiciones musicales"),
	COM("Contenidos originales de multimedia"),
	COW("Contenidos originales de página Web"),
	COP("Coreografía o pantomima"),
	DIP("Dibujo y pintura"),
	ENR("Entidades de radiodifusión"),
	ESC("Escultura"),
	EST("Estructura y disposición de base de datos"),
	FON("Fonogramas"),
	FOT("Fotografías"),
	GRL("Grabado y litografía"),
	MAQ("Maqueta"),
	MFO("Meras fotografías"),
	OCI("Obra cinematográfica y demás obras audiovisuales"),
	OFO("Obra fotográfica"),
	ODR("Obras dramáticas"),
	OLI("Obras literarias o científicas"),
	OPL("Obras plásticas"),
	OOP("Otras obras plásticas"),
	PGA("Producciones de grabaciones audiovisuales"),
	PED("Producciones editoriales"),
	PRF("Producciones fonográficas"),
	PRO("Programas de ordenador"),
	TBO("Tebeo y cómic"),
	TGC("Topografía, geografía, ciencia en general");

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

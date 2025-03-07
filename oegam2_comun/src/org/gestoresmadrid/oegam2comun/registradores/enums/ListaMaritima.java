package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum ListaMaritima {

	L1("Se registrar�n las plataformas de extracci�n de productos del subsuelo marino, los remolcadores de altura, los buques de apoyo y los dedicados al suministro a dichas plataformas que no est�n registrados en otra lista"),
	L2("Se registrar�n los buques de construcci�n nacional o importados con arreglo a la legislaci�n vigente que se dediquen al transporte mar�timo de pasajeros, de mercanc�as o de ambos"),
	L3("Se registrar�n los buques de construcci�n nacional o importados con arreglo a la legislaci�n vigente destinados a la captura y extracci�n con fines comerciales de pescado y de otros recursos marinos vivos"),
	L4("Se registrar�n las embarcaciones auxiliares de pesca, las auxiliares de explotaciones de acuicultura y los artefactos dedicados al cultivo o estabulaci�n de especies marinas"),
	L5("Se registrar�n los remolcadores, embarcaciones y artefactos navales dedicados a los servicios de puertos, radas y bah�as"),
	L6("Se registrar�n las embarcaciones deportivas o de recreo que se exploten con fines lucrativos"),
	L7("Se registrar�n las embarcaciones de construcci�n nacional o debidamente importadas, de cualquier tipo y cuyo uso exclusivo sea la pr�ctica del deporte sin prop�sito lucrativo o la pesca no profesional"),
	L8("Se registrar�n los buques y embarcaciones pertenecientes a organismos de car�cter p�blico tanto de �mbito nacional como auton�mico o local"),
	L9("Registro provisional, se anotar�n con este car�cter los buques, embarcaciones o artefactos navales en construcci�n desde el momento que esta se autoriza, exceptu�ndose las embarcaciones deportivas construidas en serie, con la debida autorizaci�n");

	private String nombreEnum;

	private ListaMaritima(String nombreEnum) {
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

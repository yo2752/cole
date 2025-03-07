package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum ListaMaritima {

	L1("Se registrarán las plataformas de extracción de productos del subsuelo marino, los remolcadores de altura, los buques de apoyo y los dedicados al suministro a dichas plataformas que no estén registrados en otra lista"),
	L2("Se registrarán los buques de construcción nacional o importados con arreglo a la legislación vigente que se dediquen al transporte marítimo de pasajeros, de mercancías o de ambos"),
	L3("Se registrarán los buques de construcción nacional o importados con arreglo a la legislación vigente destinados a la captura y extracción con fines comerciales de pescado y de otros recursos marinos vivos"),
	L4("Se registrarán las embarcaciones auxiliares de pesca, las auxiliares de explotaciones de acuicultura y los artefactos dedicados al cultivo o estabulación de especies marinas"),
	L5("Se registrarán los remolcadores, embarcaciones y artefactos navales dedicados a los servicios de puertos, radas y bahías"),
	L6("Se registrarán las embarcaciones deportivas o de recreo que se exploten con fines lucrativos"),
	L7("Se registrarán las embarcaciones de construcción nacional o debidamente importadas, de cualquier tipo y cuyo uso exclusivo sea la práctica del deporte sin propósito lucrativo o la pesca no profesional"),
	L8("Se registrarán los buques y embarcaciones pertenecientes a organismos de carácter público tanto de ámbito nacional como autonómico o local"),
	L9("Registro provisional, se anotarán con este carácter los buques, embarcaciones o artefactos navales en construcción desde el momento que esta se autoriza, exceptuándose las embarcaciones deportivas construidas en serie, con la debida autorización");

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

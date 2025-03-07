package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de cuadro de amortización
 */
public enum TipoCuadroAmortizacion {

	GENERIC("Cuadro de amortización sin especificaciones"),
	ANTICIPATED_FINANCING("Cuadro de amortización de una financiación anticipada del impuesto"),
	INSURANCE_FINANCING("Cuadro de amortización de una financiación del seguro");

	private String nombreEnum;

	private TipoCuadroAmortizacion(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	@Override
	public String toString() {
		return nombreEnum;
	}

	public String getKey() {
		return name();
	}
	
	
	public static String convertirTexto(String valor) {    
		if(valor != null && !valor.isEmpty()){
			for(TipoCuadroAmortizacion elemento : TipoCuadroAmortizacion.values()){
				if(elemento.getKey().equals(valor)){
					return elemento.toString();
				}
			}
		}
		return null;
	}

}
